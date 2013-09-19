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

public class PaintingActivity extends Activity
{

    private PaintingView paintingView;
    private PaletteView paletteView;
    private Button mixButton;

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
				mixButton.setText("Mix! :D");
				mixButton.setSelected(false);
				paletteView.isMixing = false;
			}
			else
			{
				mixButton.setText("Click another color to mix! :o");
				mixButton.setSelected(true);
				paletteView.isMixing = true;
			}
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
        
        if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
        	outerLayout.setOrientation(LinearLayout.VERTICAL);
        	innerLayout.setOrientation(LinearLayout.VERTICAL);
        }
        else
        {
        	outerLayout.setOrientation(LinearLayout.HORIZONTAL);
        	innerLayout.setOrientation(LinearLayout.VERTICAL);
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
        
        mixButton = new Button(this);
        mixButton.setText("Mix! :D");
        mixButton.setOnClickListener(mixButtonListener);
        
        innerLayout.addView(mixButton);
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