package cs.utah.edu.cs4962.fingerpaints;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;

/**
 * Created by Altik_0 on 9/11/13.
 */
public class PaletteView extends ViewGroup
{
    private PaletteModel model;
    private Rect bound;			// Used in drawing to decide the actual bounds for paint blobs
    private final int PALETTE_IMG_ID = 0x00BADCAB;

    public PaletteView(Context context, PaletteModel _model)
    {
        super(context);

        model = _model;
        bound = new Rect();
        
        ImageView paletteImg = new ImageView(context);
        Bitmap paletteBmp = BitmapFactory.decodeResource(getResources(), R.drawable.palette);
        paletteImg.setImageBitmap(paletteBmp);
        paletteImg.setId(PALETTE_IMG_ID);
        
        addView(paletteImg);
    }

    @Override
    protected void onLayout(boolean b, int i1, int i2, int i3, int i4)
    {
    	// First: layout palette image
    	ImageView paletteImg = (ImageView)this.findViewById(PALETTE_IMG_ID);
    	paletteImg.layout(0, 0, getWidth(), getHeight());
    	paletteImg.getDrawingRect(bound);
    	
    	// Next: layout paint blobs
    	// Yeah, these are some pretty arbitrary constants - oh well, looks nice enough.
        int paintWidth = (int)((float)bound.width() / 9.0f);
        int paintHeight = paintWidth;
        int padding = (int)((float)paintWidth * (4.0f / 5.0f));
        
        bound.left += padding;
        bound.top  += padding;
        bound.right  -= padding;
        bound.bottom -= padding;

        for (int i = 0; i < getChildCount(); i++)
        {
            View v = getChildAt(i);
            if (v.getId() == PALETTE_IMG_ID)
            	continue;
            
            //float radius = (bound.width() > bound.height()) ? bound.height() / 2.0f : bound.width() / 2.0f;
            float radiusX = bound.width() / 2.0f;
            float radiusY = bound.height() / 2.0f;
            
            // Sentinel values:
            // Yep, more trashy looking constants. Sorry - it worked out nicely, though!
            float gapAngle = (float)(Math.PI / 2.0);
            float startAngle = (float)(-Math.PI / 12.0) + gapAngle;
            int childCnt = getChildCount() - 1;	// we don't count the palette image
            float angleItr = ((float)i / (float)childCnt);
            float angle = ((float)((Math.PI * 2.0) - gapAngle) * angleItr) + startAngle;
            float left = ((float)(Math.cos(angle) + 1.0) * radiusX) - (paintWidth  / 2.0f) + bound.left;
            float top =  ((float)(Math.sin(angle) + 1.0) * radiusY) - (paintHeight / 2.0f) + bound.top;
            float right = left + paintWidth;
            float bottom = top + paintHeight;


            v.layout((int)left, (int)top, (int)right, (int)bottom);
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	
    	ImageView paletteImg = (ImageView)this.findViewById(PALETTE_IMG_ID);
    	paletteImg.getDrawable().copyBounds(bound);
    	
    	int width  = MeasureSpec.getSize(widthMeasureSpec);
    	int height = MeasureSpec.getSize(heightMeasureSpec);
    	int widthMode  = MeasureSpec.getMode(widthMeasureSpec);
    	int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    	
    	float scalar;
    	
    	/*
    	if (widthMode == MeasureSpec.EXACTLY)
    		scalar = (float)width / (float)bound.width();
    	else if (heightMode == MeasureSpec.EXACTLY)
    		scalar = (float)height / (float)bound.height();
    	else if (widthMode == MeasureSpec.UNSPECIFIED)
    		scalar = (float)height / (float)bound.height();
    	else if (heightMode == MeasureSpec.UNSPECIFIED)
    		scalar = (float)width / (float)bound.width();
    	else
    	{
    		scalar = (width < height) ? 
    				(float)width / (float)bound.width() :
    				(float)height / (float)bound.height();
    	}
    	*/
    	
    	switch (widthMode)
    	{
    	// We will treat these as the same, even though they aren't
    	case MeasureSpec.EXACTLY:
    	case MeasureSpec.AT_MOST:
    		switch (heightMode)
    		{
    		case MeasureSpec.EXACTLY:
    		case MeasureSpec.AT_MOST:
    			scalar = (width > height) ? (float)height / (float)bound.height() :
    										(float)width / (float)bound.width();
    			break;
    			
    		case MeasureSpec.UNSPECIFIED:
			default:
				scalar = (float)width / (float)bound.width();
				break;
    		}
    		break;
    	
    	// These cases are the same - just specifying to be explicit
    	case MeasureSpec.UNSPECIFIED:
    	default:
    		switch (heightMode)
    		{
    		case MeasureSpec.EXACTLY:
    		case MeasureSpec.AT_MOST:
    			scalar = (float)height / (float)bound.height();
    			break;
    			
    		case MeasureSpec.UNSPECIFIED:
			default:
				scalar = (width > height) ? (float)height / (float)bound.height() :
					(float)width / (float)bound.width();
				break;
    		}
    		break;
    	}

    	Log.i("Width", MeasureSpec.toString(widthMeasureSpec));
    	Log.i("Height", MeasureSpec.toString(heightMeasureSpec));

		float scaledWidth = bound.width() * scalar;
		float scaledHeight = bound.height() * scalar;
		setMeasuredDimension((int)scaledWidth, (int)scaledHeight);
    }
}
