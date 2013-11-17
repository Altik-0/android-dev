package utah.edu.cs4962.collage.view;

import utah.edu.cs4962.collage.model.CollageModel;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CollageView extends View
{
    CollageViewDataSource dataSource;
    
    public CollageView(Context context, CollageViewDataSource _dataSource)
    {
        super(context);
        dataSource = _dataSource;
    }
 
    private Point oldTouch1;
    private Point oldTouch2;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // Take the touch coordinates, and convert into coordinates for the 
        // dataSource to use
        Rect bounds = getScaledBounds();
        float scalar = (float)dataSource.getWidth() / (float)bounds.width();
        
        // First and foremost, we need to see how many touches are happening.
        // If there is only 1, we'll drag+drop with that pointer.
        // If there are 2 or more, we'll scale, using the first two pointers only.
        if (event.getPointerCount() == 1)
        {
            Point touch = new Point((int)((event.getX(0) - bounds.left) * scalar),
                                    (int)((event.getY(0) - bounds.top) * scalar));

            // If the touch is out of bounds, ignore it:
            if (touch.x < 0 || touch.x > dataSource.getWidth() ||
                touch.y < 0 || touch.y > dataSource.getHeight())
            {
                return false;
            }
            
            // Next, we care about whether the event just started, or if we are dragging
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                // Inform the dataSouce that you'd like to select an entry
                if (dataSource.tryTouchPoint(touch))
                {
                    oldTouch1 = touch;
                    return true;
                }
                return false;
            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE)
            {
                if (oldTouch1 == null)
                    return false;
                
                // Compute change:
                Point change = new Point(touch.x - oldTouch1.x,
                                         touch.y - oldTouch1.y);
                
                // Tell the dataSource we're trying to move something
                if (dataSource.tryMove(change))
                {
                    oldTouch1 = touch;
                    return true;
                }
                return false;
            }
            else
            {
                oldTouch1 = null;
            }
        }
        else
        {
            Point touch = new Point((int)((event.getX(1) - bounds.left) * scalar),
                                    (int)((event.getY(1) - bounds.top) * scalar));

            // If the touch is out of bounds, ignore it:
            if (touch.x < 0 || touch.x > dataSource.getWidth() ||
                touch.y < 0 || touch.y > dataSource.getHeight())
            {
                return false;
            }
            
            if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)
            {
                oldTouch2 = touch;
                return true;
            }
            else if (event.getActionMasked() == MotionEvent.ACTION_MOVE)
            {
                if (oldTouch1 == null || oldTouch2 == null)
                    return false;
                
                // Get distance between old touches:
                float oldDist = (float)Math.sqrt(((oldTouch1.x - oldTouch2.x) * 
                                                  (oldTouch1.x - oldTouch2.x)) +
                                                 ((oldTouch1.y - oldTouch2.y) *
                                                  (oldTouch1.y - oldTouch2.y)));
                
                // Acquire present touches, and distance between them:
                Point curTouch1 = new Point((int)((event.getX(0) - bounds.left) * scalar),
                                            (int)((event.getY(0) - bounds.top) * scalar));
                Point curTouch2 = new Point((int)((event.getX(1) - bounds.left) * scalar),
                                            (int)((event.getY(1) - bounds.top) * scalar));
                
                float newDist = (float)Math.sqrt(((curTouch1.x - curTouch2.x) * 
                                                  (curTouch1.x - curTouch2.x)) +
                                                 ((curTouch1.y - curTouch2.y) *
                                                  (curTouch1.y - curTouch2.y)));
                
                // Scale factor is the scalar necessary to convert oldDist to newDist:
                float scaleFactor = newDist / oldDist;
                
                // Get scale center
                Point center = new Point(oldTouch1.x + ((oldTouch2.x - oldTouch1.x) / 2),
                                         oldTouch1.y + ((oldTouch2.y - oldTouch1.y) / 2));
                
                // Tell the data source we'd like to scale
                dataSource.tryScaleBy(center, scaleFactor);
                
                // Reset old points:
                oldTouch1 = curTouch1;
                oldTouch2 = curTouch2;
                
                return true;
            }
            else
            {
                oldTouch2 = null;
                return false;
            }
        }
        
        return true;
    }

    private Rect getScaledBounds()
    {
        int origWidth = dataSource.getWidth();
        int origHeight = dataSource.getHeight();
        
        int width = 0;
        int height = 0;
        int left = 0;
        int top = 0;
        // If too wide:
        if (((float)origWidth / (float)origHeight) < ((float)getWidth() / (float)getHeight()))
        {
            height = getHeight();
            width = (int)(((float)height / (float)origHeight) * origWidth);
            left = (getWidth() - width) / 2;
        }
        // Otherwise:
        else
        {
            width = getWidth();
            height = (int)(((float)width / (float)origWidth) * origHeight);
            top = (getHeight() - height) / 2;
        }
        
        return new Rect(left, top, left+width, top+height);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        Rect bounds = getScaledBounds();
        Bitmap drawable = Bitmap.createScaledBitmap(dataSource.getCollage(),
                                                    bounds.width(),
                                                    bounds.height(),
                                                    true);
        
        // Draw bitmap
        // null is the paint object - can't figure out why I'd need it, and docs say it may
        // be null, so that's what we're using
        canvas.drawBitmap(drawable, bounds.left, bounds.top, null);
        
        // If one of the images should be highlighted, let's do that:
        Rect highlightBounds = dataSource.getSelectedRegion();
        
        // Scale the highlight box to match our scaling above
        if (highlightBounds != null)
        {
            float scalar = (float)bounds.width() / (float)dataSource.getWidth();
            int newWidth = (int)(highlightBounds.width() * scalar);
            int newHeight = (int)(highlightBounds.height() * scalar);
            
            highlightBounds.left = bounds.left + (int)(highlightBounds.left * scalar);
            highlightBounds.top = bounds.top  + (int)(highlightBounds.top * scalar);
            highlightBounds.right = highlightBounds.left + newWidth;
            highlightBounds.bottom = highlightBounds.top + newHeight;
            
            // Actually draw the thing
            Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            highlightPaint.setColor(Color.RED);
            highlightPaint.setStyle(Paint.Style.STROKE);
            highlightPaint.setStrokeWidth(10.0f);
            if (highlightBounds != null)
                canvas.drawRect(highlightBounds, highlightPaint);
        }
    }
}
