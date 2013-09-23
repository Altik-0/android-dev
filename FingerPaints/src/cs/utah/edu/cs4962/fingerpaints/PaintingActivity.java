package cs.utah.edu.cs4962.fingerpaints;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

public class PaintingActivity extends Activity
{

    private PaintingView paintingView;
    private PaletteView paletteView;
    private ToggleButton mixButton;
    private ToggleButton handButton;
    private ToggleButton removeButton;
    private SeekBar widthSelector;

    private int colorSelection;

    private PaletteView.OnColorChangeListener colorChangeListener = new PaletteView.OnColorChangeListener()
    {
        @Override
        public void onColorChange(int newColor)
        {
        	if (mixButton.isSelected())
        		paletteView.AddColor(mixColors(newColor, paletteView.getSelectedColor()));
        	else
        		paintingView.setCurColor(newColor);
        }
    };
    
    private View.OnClickListener mixButtonListener = new View.OnClickListener()
    {
		@Override
		public void onClick(View v)
		{
			if (mixButton.isSelected())
			{
				mixButton.setChecked(false);
				mixButton.setText("Mix");
				mixButton.setSelected(false);
				removeButton.setEnabled(true);
				paletteView.isMixing = false;
			}
			else
			{
				mixButton.setChecked(true);
				mixButton.setText("Stop Mixing");
				mixButton.setSelected(true);
				removeButton.setEnabled(false);
				paletteView.isMixing = true;
			}
		}
    };
    
    private View.OnClickListener handButtonListener = new View.OnClickListener()
    {
		@Override
		public void onClick(View v)
		{
			if (handButton.isSelected())
			{
				handButton.setChecked(false);
				handButton.setText("Hand Tool");
				handButton.setSelected(false);
				paintingView.isHandTool = false;
			}
			else
			{
				handButton.setChecked(true);
				handButton.setText("Disable Hand");
				handButton.setSelected(true);
				paintingView.isHandTool = true;
			}
		}
    };
    
    private View.OnClickListener removeButtonListener = new View.OnClickListener()
    {
		@Override
		public void onClick(View v)
		{
			if (removeButton.isSelected())
			{
				removeButton.setChecked(false);
				removeButton.setText("Remove Color");
				removeButton.setSelected(false);
				mixButton.setEnabled(true);
				paletteView.isRemoving = false;
			}
			else
			{
				removeButton.setChecked(true);
				removeButton.setText("Stop Removing");
				removeButton.setSelected(true);
				mixButton.setEnabled(false);
				paletteView.isRemoving = true;
			}
		}
    };
    
    private OnSeekBarChangeListener widthChangedListener = new OnSeekBarChangeListener()
    {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean arg2) 
		{
			paintingView.setCurWidth(paintingView.MAX_WIDTH * (float)progress / 100.0f);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
			// this method intentionally left blank
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			// do nothing!
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

        LinearLayout outerLayout = new LinearLayout(this);
        LinearLayout innerLayout = new LinearLayout(this);
        LinearLayout buttonLayout = new LinearLayout(this);
        
        if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
        	outerLayout.setOrientation(LinearLayout.VERTICAL);
        	innerLayout.setOrientation(LinearLayout.VERTICAL);
        	buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
        else
        {
        	outerLayout.setOrientation(LinearLayout.HORIZONTAL);
        	innerLayout.setOrientation(LinearLayout.VERTICAL);
        	buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        }

        LinearLayout.LayoutParams paintingParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        																 		 LinearLayout.LayoutParams.MATCH_PARENT);
        paintingParams.weight = 1.0f;
        LinearLayout.LayoutParams paletteParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		 		 																LinearLayout.LayoutParams.MATCH_PARENT);
        paletteParams.weight = 1.0f;
        
        paintingView.setLayoutParams(paintingParams);
        paintingView.setId(0xBADC0C0A);
        paletteView.setId(0xFEEDF00D);
        paletteView.setOnColorChangeListener(colorChangeListener);
        paintingView.setCurColor(paletteView.getSelectedColor());

        mixButton = new ToggleButton(this);
        mixButton.setText("Mix");
        mixButton.setOnClickListener(mixButtonListener);
        
        handButton = new ToggleButton(this);
        handButton.setText("Hand Tool");
        handButton.setOnClickListener(handButtonListener);
        
        removeButton = new ToggleButton(this);
        removeButton.setText("Remove Color");
        removeButton.setOnClickListener(removeButtonListener);
        
        widthSelector = new SeekBar(this);
        widthSelector.setMax(100);
        widthSelector.setProgress(50);
        widthSelector.setId(0xCADEDEAD);
        widthSelector.setOnSeekBarChangeListener(widthChangedListener);
        paintingView.setCurWidth(paintingView.MAX_WIDTH * 50.0f / 100.0f);
        
        buttonLayout.addView(mixButton);
        buttonLayout.addView(handButton);
        buttonLayout.addView(removeButton);
        
        innerLayout.addView(buttonLayout);
        innerLayout.addView(widthSelector);
        innerLayout.addView(paletteView);
        innerLayout.setLayoutParams(paletteParams);
        
        outerLayout.addView(innerLayout);
        outerLayout.addView(paintingView);
        
        //paintingView.setBackgroundColor(0xFFFF0000);
        //paletteView.setBackgroundColor(0xFFccFFcc);

        setContentView(outerLayout);
    }
    
    private int mixColors(int color1, int color2)
    {
    	int r1 = (color1 >> 16) & 0xFF;
    	int g1 = (color1 >> 8 ) & 0xFF;
    	int b1 = (color1      ) & 0xFF;
    	
    	int r2 = (color2 >> 16) & 0xFF;
    	int g2 = (color2 >> 8 ) & 0xFF;
    	int b2 = (color2      ) & 0xFF;
    	
    	int r = ((r1 + r2) / 2) & 0xFF;
    	int g = ((g1 + g2) / 2) & 0xFF;
    	int b = ((b1 + b2) / 2) & 0xFF;
    	
    	return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}