package utah.edu.cs4962.collage.view;

import utah.edu.cs4962.collage.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.Button;
import android.widget.ToggleButton;

public class AddRemoveButton extends Button
{
    public AddRemoveButton(Context context)
    {
        super(context);
    }
    
    @Override
    public void onDraw(Canvas canvas)
    {
        Bitmap image = BitmapFactory.decodeResource(getResources(),
                isSelected() ? R.drawable.minus : R.drawable.plus);
        int origWidth = image.getWidth();
        int origHeight = image.getHeight();
        
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
        Bitmap drawable = Bitmap.createScaledBitmap(image, width, height, true);
        
        canvas.drawBitmap(image, left, top, null);
    }
}
