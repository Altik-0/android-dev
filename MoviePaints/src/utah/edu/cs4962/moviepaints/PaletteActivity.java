package utah.edu.cs4962.moviepaints;

import utah.edu.cs4962.moviepaints.view.PaintingView;
import utah.edu.cs4962.moviepaints.view.PaletteView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PaletteActivity extends Activity
{
    public static final int REQUEST_CODE = 0x00BADA55;
    
    private PaletteView paletteView;
    private int curColor;
    private float curWidth;
    
    // TODO: other buttons?
    private ToggleButton mixButton;
    private ToggleButton deleteButton;
    private Button okButton;
    private Button cancelButton;
    
    // Width slider
    private SeekBar widthSelector;
    
    // IDs for views that need to persist
    private final int PALETTE_ID = 0xBADC0C0A;
    private final int MIX_BUTTON_ID = 0xDEADBEEF;
    private final int DELETE_BUTTON_ID = 0x90210000;
    private final int WIDTH_SELECTOR_ID = 0x02933418;
    
    // Button click listeners
    private View.OnClickListener mixListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (mixButton.isChecked())
            {
                paletteView.isMixing = true;
                deleteButton.setEnabled(false);
            }
            else
            {
                paletteView.isMixing = false;
                deleteButton.setEnabled(true);
            }
        }
    };
    private View.OnClickListener deleteListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (deleteButton.isChecked())
            {
                paletteView.isRemoving = true;
                mixButton.setEnabled(false);
            }
            else
            {
                paletteView.isRemoving = false;
                mixButton.setEnabled(true);
            }
        }
    };
    private View.OnClickListener okClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent okIntent = new Intent();
            okIntent.putExtra("color", curColor);
            okIntent.putExtra("width", curWidth);
            setResult(RESULT_OK, okIntent);
            finish();
        }
    };
    private View.OnClickListener cancelClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent cancelIntent = new Intent();
            setResult(RESULT_CANCELED, cancelIntent);
            finish();
        }
    };
    
    PaletteView.OnColorChangeListener colorChangeListener = new PaletteView.OnColorChangeListener()
    {
        @Override
        public void onColorChange(int newColor)
        {
            curColor = newColor;
        }
    };
    
    private OnSeekBarChangeListener widthChangedListener = new OnSeekBarChangeListener()
    {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean arg2) 
        {
            curWidth = PaintingView.MAX_WIDTH * (float)progress / 100.0f;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        paletteView = new PaletteView(this);
        paletteView.setId(PALETTE_ID);
        paletteView.setOnColorChangeListener(colorChangeListener);
        curColor = paletteView.getSelectedColor();
        
        mixButton = new ToggleButton(this);
        mixButton.setTextOn("Mix Colors");
        mixButton.setTextOff("Mix Colors");
        mixButton.setChecked(false);
        mixButton.setId(MIX_BUTTON_ID);
        mixButton.setOnClickListener(mixListener);
        deleteButton = new ToggleButton(this);
        deleteButton.setTextOn("Delete Colors");
        deleteButton.setTextOff("Delete Colors");
        deleteButton.setChecked(false);
        deleteButton.setId(DELETE_BUTTON_ID);
        deleteButton.setOnClickListener(deleteListener);
        
        okButton = new Button(this);
        okButton.setText("OK");
        okButton.setOnClickListener(okClickListener);
        cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(cancelClickListener);
        
        widthSelector = new SeekBar(this);
        widthSelector.setMax(100);
        widthSelector.setProgress(50);
        widthSelector.setOnSeekBarChangeListener(widthChangedListener);
        widthSelector.setId(WIDTH_SELECTOR_ID);
        curWidth = PaintingView.MAX_WIDTH * (float)widthSelector.getProgress() / 100.0f;
        
        LinearLayout mainLayout = new LinearLayout(this);
        LinearLayout buttonLayout = new LinearLayout(this);
        LinearLayout bottomLayout = new LinearLayout(this);
        
        // Determine orientation
        if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            bottomLayout.setOrientation(LinearLayout.VERTICAL);
        }
        else
        {
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setOrientation(LinearLayout.VERTICAL);
            bottomLayout.setOrientation(LinearLayout.VERTICAL);
        }
        
        // Setup layouts
        buttonLayout.addView(okButton);
        buttonLayout.addView(cancelButton);
        buttonLayout.addView(mixButton);
        buttonLayout.addView(deleteButton);

        bottomLayout.addView(widthSelector);
        bottomLayout.addView(paletteView);

        mainLayout.addView(buttonLayout);
        mainLayout.addView(bottomLayout);
        
        setContentView(mainLayout);
    }
    
    @Override
    public void finish()
    {
        // TODO: ?
        super.finish();
    }
}
