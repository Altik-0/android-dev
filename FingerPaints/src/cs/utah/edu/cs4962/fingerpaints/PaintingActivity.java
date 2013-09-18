package cs.utah.edu.cs4962.fingerpaints;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PaintingActivity extends Activity
{

    private PaintingView paintingView;
    private PaletteView paletteView;

    private int colorSelection;

    private View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        private boolean active = true;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            // Really, this is just to cover our bases: you shouldn't ever add this
            // as a touchListener for anything but a PaintView
            if (!(view instanceof PaintView))
                return false;

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                active = true;
            else if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
                active = false;
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP && active)
                colorSelection = ((PaintView)view).GetColor();
            return true;
        }
    };

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        colorSelection = 0xFF000000;

        paintingView = new PaintingView(this);
        paletteView = new PaletteView(this);

        LinearLayout layout = new LinearLayout(this);
        
        if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        	layout.setOrientation(LinearLayout.VERTICAL);
        else
        	layout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams paintingParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        																 		 LinearLayout.LayoutParams.MATCH_PARENT);
        paintingParams.weight = 1.0f;
        LinearLayout.LayoutParams paletteParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		 		 																LinearLayout.LayoutParams.MATCH_PARENT);
        paletteParams.weight = 1.0f;
        
        paintingView.setLayoutParams(paintingParams);
        paletteView.setLayoutParams(paletteParams);
        layout.addView(paletteView);
        layout.addView(paintingView);
        
        //paintingView.setBackgroundColor(0xFFFF0000);
        //paletteView.setBackgroundColor(0xFFccFFcc);
        
        PaintView red   = new PaintView(this, 0xFFFF0000);
        PaintView green = new PaintView(this, 0xFF00FF00);
        PaintView blue  = new PaintView(this, 0xFF0000FF);

        red.setOnTouchListener(touchListener);
        green.setOnTouchListener(touchListener);
        blue.setOnTouchListener(touchListener);

        paletteView.addView(red);
        paletteView.addView(green);
        paletteView.addView(blue);

        setContentView(layout);
    }
}