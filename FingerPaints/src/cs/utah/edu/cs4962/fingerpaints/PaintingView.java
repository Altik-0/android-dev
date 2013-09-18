package cs.utah.edu.cs4962.fingerpaints;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Altik_0 on 9/11/13.
 */
public class PaintingView extends View
{
	private LinkedList<LinkedList<Point>> curves;
	private LinkedList<Integer> curveColors;
	private LinkedList<Float> curveWidths;
	
    private Point offset;     // Denotes offset of points in model with points on view

    public PaintingView(Context context)
    {
        super(context);

        curves = new LinkedList<LinkedList<Point>>();
		curveColors = new LinkedList<Integer>();
		curveWidths = new LinkedList<Float>();
        offset = new Point(getWidth() / 2, getHeight() / 2);
    }
    
    private void newCurve(int color, float width)
	{
		curves.addFirst(new LinkedList<Point>());
		curveColors.addFirst(color);
		curveWidths.addFirst(width);
	}
    
    private void addPoint(int x, int y)
	{
		curves.get(0).add(new Point(x, y));
	}

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Log.d("Model check:", Integer.toString(curves.size()));
        for (int i = 0; i < curves.size(); i++)
        {
        	LinkedList<Point> curve = curves.get(i);
        	int color = curveColors.get(i);
        	float width = curveWidths.get(i);
        	
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(color);
            paint.setStrokeWidth(width);
            paint.setStyle(Paint.Style.STROKE);

            Path path = new Path();
            Point initPoint = new Point(curve.get(0));
            initPoint.x += offset.x;
            initPoint.y += offset.y;
            
            path.moveTo(initPoint.x, initPoint.y);
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
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                newCurve(0xFF000000, 5.0f);      //TODO: make not static values
                addPoint((int)motionEvent.getAxisValue(MotionEvent.AXIS_X),
                         (int)motionEvent.getAxisValue(MotionEvent.AXIS_Y));
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Do nothing, because they stopped
                break;
            default:
                addPoint((int)motionEvent.getAxisValue(MotionEvent.AXIS_X),
                        	   (int)motionEvent.getAxisValue(MotionEvent.AXIS_Y));
                invalidate();
                break;
        }

        return true;
    }
}
