package utah.edu.cs4962.moviepaints.view;

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

import utah.edu.cs4962.moviepaints.R;

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
    public boolean isRemoving = false;
    
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
    		else if (isRemoving)
    		{
    			RemoveColor(v.getId());
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
		pv.setId(getChildCount());
		addView(pv);
	}
	
	public void RemoveColor(int viewId)
	{
		PaintView pv = (PaintView)findViewById(viewId);
		
		if (pv.isSelected())
		{
			if (viewId > 0)
			{
				PaintView newPv = (PaintView)findViewById(viewId-1);
				newPv.setSelected(true);
				if (onColorChangeListener != null)
					onColorChangeListener.onColorChange(newPv.GetColor());
			}
			else if (viewId < getChildCount() - 1)
			{
				PaintView newPv = (PaintView)findViewById(viewId+1);
				newPv.setSelected(true);
				if (onColorChangeListener != null)
					onColorChangeListener.onColorChange(newPv.GetColor());
			}
		}

		for (int i = viewId+1; i < getChildCount(); i++)
		{
			findViewById(i).setId(i-1);
		}
		
		removeView(pv);
	}
	
    @Override
    protected void onLayout(boolean b, int i1, int i2, int i3, int i4)
    {    	    	
    	// layout paint blobs
    	// Yeah, these are some pretty arbitrary constants - oh well, looks nice enough.
        int paintWidth = (getWidth() < getHeight()) ? (int)((float)getWidth() / 5.0f) :
        											  (int)((float)getHeight() / 5.0f);
        int paintHeight = paintWidth;
        int padding = paintWidth;
        
        bound.left = padding;
        bound.top  = padding;
        bound.right  = bound.left + getWidth()  - padding - padding;
        bound.bottom = bound.top  + getHeight() - padding - padding;

        for (int i = 0; i < getChildCount(); i++)
        {
            View v = getChildAt(i);
            
            //float radius = (bound.width() > bound.height()) ? bound.height() / 2.0f : bound.width() / 2.0f;
            float radiusX = bound.width() / 2.0f;
            float radiusY = bound.height() / 2.0f;
            
            // Sentinel values:
            // Yep, more trashy looking constants. Sorry - it worked out nicely, though!
            float gapAngle = (float)(34.0 * Math.PI / 64.0);
            float startAngle = (float)(-3.0 * Math.PI / 32.0) + gapAngle;
            int childCnt = getChildCount();
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
}
