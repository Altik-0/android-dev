package cs.utah.edu.cs4962.fingerpaints;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
    private Rect bound;			// Used in drawing to decide the actual bounds for paint blobs
    private final int PALETTE_IMG_ID = 0x00BADCAB;
    
    private int baseWidth;
    private int baseHeight;

    private OnColorChangeListener onColorChangeListener = null;
    
    public boolean isMixing = false;
    
    private View.OnTouchListener clickListener = new View.OnTouchListener()
    {
    	@Override
		public boolean onTouch(View v, MotionEvent motionEvent)
    	{
    		if (motionEvent.getAction() != MotionEvent.ACTION_DOWN)
    			return false;
    		
    		if (isMixing)
    		{
    			if (onColorChangeListener != null)
					onColorChangeListener.onColorChange(((PaintView)v).GetColor());
    		}
    		else
    		{
				for (int i = 0; i < getChildCount(); i++)
				{
					getChildAt(i).setSelected(false);
				}
				v.setSelected(true);
				if (onColorChangeListener != null)
					onColorChangeListener.onColorChange(((PaintView)v).GetColor());
    		}
			
			return true;
		}
    };
    
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public PaletteView(Context context)
    {
        super(context);
        bound = new Rect();
        
        Bitmap paletteBmp = BitmapFactory.decodeResource(getResources(), R.drawable.palette);
        setBackground(getResources().getDrawable(R.drawable.palette));
        baseWidth  = paletteBmp.getWidth();
        baseHeight = paletteBmp.getHeight();
        
        // Setup initial paint swatches
        //AddColor(0xFFFF0000);
        //AddColor(0xFF00FF00);
        //AddColor(0xFF0000FF);
        
        AddColor(0xFF00FFFF);	// CYAN
        AddColor(0xFF0000FF);	// BLUE
        AddColor(0xFFFF00FF);	// MAGENTA
        AddColor(0xFFFF0000);	// RED
        AddColor(0xFFFFFF00);	// YELLOW
        AddColor(0xFF00FF00);	// GREEN
        AddColor(0xFF000000);	// BLACK
        AddColor(0xFFFFFFFF);	// WHITE
        
        getChildAt(0).setSelected(true);
    }

	public void AddColor(int color)
	{
		PaintView pv = new PaintView(getContext(), color);
		pv.setOnTouchListener(clickListener);
		addView(pv);
	}
	
    @Override
    protected void onLayout(boolean b, int i1, int i2, int i3, int i4)
    {    	    	
    	// layout paint blobs
    	// Yeah, these are some pretty arbitrary constants - oh well, looks nice enough.
        int paintWidth = (getWidth() < getHeight()) ? (int)((float)getWidth() / 6.0f) :
        											  (int)((float)getHeight() / 6.0f);
        int paintHeight = paintWidth;
        int padding = paintWidth;
        
        bound.left = padding;
        bound.top  = padding;
        bound.right  = bound.left + getWidth()  - padding - padding;
        bound.bottom = bound.top  + getHeight() - padding - padding;

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
    
    public int getSelectedColor()
    {
    	for (int i = 0; i < getChildCount(); i++)
    	{
    		if (getChildAt(i).isSelected())
    			return ((PaintView)getChildAt(i)).GetColor();
    	}
    	// I guess if there is no selection, we'll go with a default?
    	return 0xFF000000;
    }
    
    public void setOnColorChangeListener(OnColorChangeListener _onColorChangeListener)
    {
    	onColorChangeListener = _onColorChangeListener;
    }
    
    public interface OnColorChangeListener
    {
    	public void onColorChange(int newColor);
    }
    
    @Override
    protected Parcelable onSaveInstanceState()
    {
    	Bundle bundle = new Bundle();
    	
    	LinkedList<Integer> colors = new LinkedList<Integer>();
    	for (int i = 0; i < getChildCount(); i++)
    	{
    		PaintView pv = (PaintView)getChildAt(i);
    		colors.add(pv.GetColor());
    		
    		if (pv.isSelected())
    			bundle.putInt("selected", i);
    	}
    	bundle.putSerializable("colors", colors);
    	
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
    		
    		// Get old colors. Note: since we're replacing the colors, we need to clear
    		// out the colors we put in from the constructor
    		removeAllViews();
    		LinkedList<Integer> colors = (LinkedList<Integer>)bundle.getSerializable("colors");
    		for (int color : colors)
    		{
    			AddColor(color);
    		}
    		
    		int selected = bundle.getInt("selected");
    		getChildAt(selected).setSelected(true);
    		
    		super.onRestoreInstanceState(bundle.getParcelable("super"));
    	}
    }
    
    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	
    	int padding = (getWidth() < getHeight()) ? (int)((float)getWidth() / 6.0f) :
			  									   (int)((float)getHeight() / 6.0f);
    	bound.left = padding;
        bound.top  = padding;
        bound.right  = bound.left + getWidth()  - padding - padding;
        bound.bottom = bound.top  + getHeight() - padding - padding;
    	
    	int width  = MeasureSpec.getSize(widthMeasureSpec);
    	int height = MeasureSpec.getSize(heightMeasureSpec);
    	int widthMode  = MeasureSpec.getMode(widthMeasureSpec);
    	int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    	
    	float scalar;
    	
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
		
		// Arbitrary minimum imposed: if our system is too small, we'll just request more space
		if (scaledWidth < 300)
		{
			scalar = 300.0f / (float)bound.width();
			scaledWidth = bound.width() * scalar;
			scaledHeight = bound.height() * scalar;
			resolveSize((int) scaledWidth, widthMeasureSpec);
		}
		if (scaledHeight < 150)
		{
			scalar = 150.0f / (float)bound.height();
			scaledWidth = bound.width() * scalar;
			scaledHeight = bound.height() * scalar;
			resolveSize((int) scaledHeight, heightMeasureSpec);
		}
		setMeasuredDimension((int)scaledWidth, (int)scaledHeight);
    }
    */

    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
    	int width  = MeasureSpec.getSize(widthMeasureSpec);
    	int height = MeasureSpec.getSize(heightMeasureSpec);
    	//int widthSpec  = MeasureSpec.getMode(widthMeasureSpec);
    	//int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
    	
    	float scalar = (width < height) ? (float)width / (float)baseWidth :
    									  (float)height / (float)baseHeight;
    	
    	setMeasuredDimension((int)(baseWidth * scalar), (int)(baseHeight * scalar));
    }
    */
}
