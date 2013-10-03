package utah.edu.cs4962.moviepaints;

import utah.edu.cs4962.moviepaints.view.PaletteView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class PaletteActivity extends Activity
{
    private PaletteView paletteView;
    private int curColor;
    private float curWidth;
    
    // TODO: other buttons?
    private ToggleButton mixButton;
    private ToggleButton deleteButton;
    private Button okButton;
    private Button cancelButton;
    
    // IDs for views that need to persist
    private final int PALETTE_ID = 0xBADC0C0A;
    private final int MIX_BUTTON_ID = 0xDEADBEEF;
    private final int DELETE_BUTTON_ID = 0x90210000;
    
    // Button click listeners
    private View.OnClickListener mixListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (mixButton.isChecked())
            {
                mixButton.setText("Mix Colors");
                paletteView.isMixing = true;
                deleteButton.setEnabled(false);
            }
            else
            {
                mixButton.setText("Mix Colors");
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
                deleteButton.setText("Delete Colors");
                paletteView.isRemoving = true;
                mixButton.setEnabled(false);
            }
            else
            {
                deleteButton.setText("Delete Colors");
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
        }
    };
    private View.OnClickListener cancelClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent cancelIntent = new Intent();
            setResult(RESULT_CANCELED, cancelIntent);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        paletteView = new PaletteView(this);
        paletteView.setId(PALETTE_ID);
        
        mixButton = new ToggleButton(this);
        mixButton.setText("Mix Colors");
        mixButton.setId(MIX_BUTTON_ID);
        mixButton.setOnClickListener(mixListener);
        deleteButton = new ToggleButton(this);
        deleteButton.setText("Delete Colors");
        deleteButton.setId(DELETE_BUTTON_ID);
        deleteButton.setOnClickListener(deleteListener);
        
        okButton = new Button(this);
        okButton.setText("OK");
        okButton.setOnClickListener(okClickListener);
        cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(cancelClickListener);
        
        LinearLayout mainLayout = new LinearLayout(this);
        LinearLayout bottomLayout = new LinearLayout(this);
        LinearLayout topLayout = new LinearLayout(this);
        
        // Determine orientation
        if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            bottomLayout.setOrientation(LinearLayout.HORIZONTAL);
            topLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
        else
        {
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            bottomLayout.setOrientation(LinearLayout.VERTICAL);
            topLayout.setOrientation(LinearLayout.VERTICAL);
        }
        
        // Setup layouts
        bottomLayout.addView(okButton);
        bottomLayout.addView(cancelButton);
        
        topLayout.addView(mixButton);
        topLayout.addView(deleteButton);
        
        mainLayout.addView(topLayout);
        mainLayout.addView(bottomLayout);
        mainLayout.addView(paletteView);
        
        setContentView(mainLayout);
    }
    
    @Override
    public void finish()
    {
        // TODO: ?
        super.finish();
    }
}
