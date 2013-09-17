package cs.utah.edu.cs4962.fingerpaints;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Altik_0 on 9/11/13.
 */
public class PaintingView extends View
{
    private PainterModel model;
    private Vector2 offset;     // Denotes offset of points in model with points on view

    public PaintingView(Context context)
    {
        super(context);

        model = new PainterModel();
        offset = new Vector2((float)getWidth() / 2.0f, (float)getHeight() / 2.0f);
    }

    public PaintingView(Context context, PainterModel _model)
    {
        super(context);

        model = _model;
        offset = new Vector2((float) getWidth() / 2.0f, (float)getHeight() / 2.0f);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Log.d("Model check:", Integer.toString(model.curves.size()));
        for (PainterModel.Curve curve : model.curves)
        {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(curve.color);
            paint.setStrokeWidth(curve.width);
            paint.setStyle(Paint.Style.STROKE);

            Path path = new Path();
            Vector2 initPoint = Vector2.add(curve.points.get(0), offset);
            path.moveTo(initPoint.x, initPoint.y);
            for (Vector2 point : curve.points)
            {
                Vector2 nextPoint = Vector2.add(point, offset);
                path.lineTo(nextPoint.x, nextPoint.y);
            }

            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                model.BeginNewCurve(0xFF000000, 5.0f);      //TODO: make not static values
                model.AddPoint(new Vector2(motionEvent.getAxisValue(MotionEvent.AXIS_X),
                                           motionEvent.getAxisValue(MotionEvent.AXIS_Y)));
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Do nothing, because they stopped
                break;
            default:
                model.AddPoint(new Vector2(motionEvent.getAxisValue(MotionEvent.AXIS_X),
                        motionEvent.getAxisValue(MotionEvent.AXIS_Y)));
                invalidate();
                break;
        }

        return true;
    }
}
