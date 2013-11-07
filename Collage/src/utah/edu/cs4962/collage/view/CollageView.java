package utah.edu.cs4962.collage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;

public class CollageView extends View
{
    CollageViewDataSource dataSource;
    
    public CollageView(Context context, CollageViewDataSource _dataSource)
    {
        super(context);
        dataSource = _dataSource;
    }
 
    @Override
    protected void onDraw(Canvas canvas)
    {
        int origWidth = dataSource.getWidth();
        int origHeight = dataSource.getHeight();
        
        int width = 0;
        int height = 0;
        int left = 0;
        int top = 0;
        // If too wide:
        Log.i("VIEW: Width", "" + getWidth());
        Log.i("VIEW: Height", "" + getHeight());
        Log.i("VIEW: origWidth", "" + origWidth);
        Log.i("VIEW: origHeight", "" + origHeight);
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
        Bitmap drawable = Bitmap.createScaledBitmap(dataSource.getCollage(), width, height, true);
        
        // Draw bitmap
        // null is the paint object - can't figure out why I'd need it, and docs say it may
        // be null, so that's what we're using
        canvas.drawBitmap(drawable, left, top, null);
        
        // If one of the images should be highlighted, let's do that:
        // TODO
    }
}
