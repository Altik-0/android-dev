package utah.edu.cs4962.moviepaints.view;


import java.util.LinkedList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PaintingView extends View
{
    public static final float MAX_WIDTH = 20.0f;
    
    private PaintingViewAdapter adapter;    // Gives us the painting data
    
    private Point prevTouch;    // used for hand tool

    public PaintingView(Context context, PaintingViewAdapter _adapter)
    {
        super(context);
        
        adapter = _adapter;
        prevTouch = new Point(0, 0);
        setEnabled(false);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        for (int i = 0; i < adapter.getCurveCount(); i++)
        {
            LinkedList<Point> curve = adapter.getCurve(i);
            int color = adapter.getCurveColor(i);
            float width = adapter.getCurveWidth(i);
            
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(color);
            paint.setStrokeWidth(width);
            paint.setStyle(Paint.Style.STROKE);

            Point offset = adapter.getHandOffset();
            
            Path path = new Path();
            Point initPoint;
            if (curve.size() > 0)
            {
                initPoint = new Point(curve.get(0));
                initPoint.x += offset.x;
                initPoint.y += offset.y;
                
                path.moveTo(initPoint.x, initPoint.y);
            }
            
            for (Point point : curve)
            {
                Point nextPoint = new Point(point);
                nextPoint.x += offset.x;
                nextPoint.y += offset.y;
                
                path.lineTo(nextPoint.x, nextPoint.y);
            }

            canvas.drawPath(path, paint);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        // Do nothing if not enabled
        if (!adapter.isEnabled())
            return false;
        
        if (adapter.isHandTool())
        {
            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE)
            {
                Point curTouch = new Point((int)motionEvent.getAxisValue(MotionEvent.AXIS_X),
                                           (int)motionEvent.getAxisValue(MotionEvent.AXIS_Y));
                Point offset = new Point(curTouch.x - prevTouch.x,
                                        curTouch.y - prevTouch.y);
                
                adapter.onHandMovement(offset);
                
                invalidate();
            }
            prevTouch = new Point((int)motionEvent.getAxisValue(MotionEvent.AXIS_X),
                                  (int)motionEvent.getAxisValue(MotionEvent.AXIS_Y));
        }
        else
        {
            Point offset = adapter.getHandOffset();
            Point newPoint = new Point((int)motionEvent.getAxisValue(MotionEvent.AXIS_X) - offset.x,
                                       (int)motionEvent.getAxisValue(MotionEvent.AXIS_Y) - offset.y);
            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    
                    adapter.onCreateCurve(adapter.getCurColor(), adapter.getCurWidth());
                    adapter.onDrawPoint(newPoint);
                    
                    invalidate();
                    break;
    
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Do nothing, because they stopped
                    break;
                default:
                    adapter.onDrawPoint(newPoint);
                    
                    invalidate();
                    break;
            }
        }

        return true;
    }
    
    // TODO: save state
    /*
    @Override
    protected Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("curves", curves);
        bundle.putSerializable("colors", curveColors);
        bundle.putSerializable("widths", curveWidths);
        bundle.putInt("curColor", curColor);
        bundle.putFloat("curWidth", curWidth);

        bundle.putInt("viewWidth", getWidth());
        bundle.putInt("viewHeight", getHeight());
        bundle.putInt("offsetX", offset.x);
        bundle.putInt("offsetY", offset.y);
        
        bundle.putParcelable("super", super.onSaveInstanceState());
        
        return bundle;
    }
    
    @Override
    protected void onRestoreInstanceState(Parcelable parcel)
    {
        // Sanity check
        if (parcel instanceof Bundle)
        {
            Bundle bundle = (Bundle)parcel;
            curves = (LinkedList<LinkedList<Point>>)bundle.getSerializable("curves");
            curveColors = (LinkedList<Integer>)bundle.getSerializable("colors");
            curveWidths = (LinkedList<Float>)bundle.getSerializable("widths");
            curColor = bundle.getInt("curColor");
            curWidth = bundle.getFloat("curWidth");

            int prevWidth = bundle.getInt("viewWidth");
            int prevHeight = bundle.getInt("viewHeight");
            offset = new Point(bundle.getInt("offsetX"), bundle.getInt("offsetY"));
            
            super.onRestoreInstanceState(bundle.getParcelable("super"));
        }
    }
    */

    public PaintingViewAdapter getAdapter()
    {
        return adapter;
    }

    public void setAdapter(PaintingViewAdapter adapter)
    {
        this.adapter = adapter;
    }
}
