package utah.edu.cs4962.moviepaints;

import java.util.LinkedList;

import utah.edu.cs4962.moviepaints.view.*;
import utah.edu.cs4962.moviepaints.model.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Point;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements PaintingViewAdapter
{
    private PaintingModel paintModel;
    private PaintingView paintView;
    
    // IDs and etc.
    private final int REQUEST_CODE = 0x00BADA55;
    
    // TODO: other buttons
    private ToggleButton handTool;
    private Button brushTool;
    private ToggleButton placeHolder;
    
    // Button listeners
    private View.OnClickListener handButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
    {
            if (handTool.isChecked())
            {
                handTool.setText("Disable Hand");
                paintView.isHandTool = true;   
            }
            else
            {
                handTool.setText("Hand Tool");
                paintView.isHandTool = false;
            }
        }
    };
    
    private View.OnClickListener brushButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent paletteIntent = new Intent(MainActivity.this, PaletteActivity.class);
            startActivityForResult(paletteIntent, REQUEST_CODE);
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		paintModel = new PaintingModel();
		paintView = new PaintingView(this, this);
		
		handTool = new ToggleButton(this);
		handTool.setText("Hand Tool");
        handTool.setOnClickListener(handButtonListener);
        brushTool = new Button(this);
        brushTool.setText("Brush Tool");
        brushTool.setOnClickListener(brushButtonListener);
		placeHolder = new ToggleButton(this);
		placeHolder.setText("TODO");
		
		LinearLayout layout = new LinearLayout(this);
		LinearLayout buttonLayout = new LinearLayout(this);
		// Determine orientation
		if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
            layout.setOrientation(LinearLayout.VERTICAL);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
        else
        {
            layout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setOrientation(LinearLayout.VERTICAL);
        }
		
		buttonLayout.addView(handTool);
		buttonLayout.addView(brushTool);
		buttonLayout.addView(placeHolder);
		
		layout.addView(buttonLayout);
		layout.addView(paintView);
		
		setContentView(layout);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Bundle bndl = data.getExtras();
                paintView.setCurColor(bndl.getInt("color"));
                paintView.setCurWidth(bndl.getFloat("width"));
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public int getCurveCount()
    {
        return paintModel.getCurveCount();
    }

    @Override
    public LinkedList<Point> getCurve(int index)
    {
        return paintModel.getCurve(index).getPoints();
    }

    @Override
    public int getCurveColor(int index)
    {
        return paintModel.getCurve(index).getColor();
    }

    @Override
    public float getCurveWidth(int index)
    {
        return paintModel.getCurve(index).getWidth();
    }

    @Override
    public void onCreateCurve(int color, float width)
    {
        paintModel.createNewCurve(color, width);
    }

    @Override
    public void onDrawPoint(Point newPoint, long time)
    {
        paintModel.drawPoint(newPoint, time);
    }

    @Override
    public void onHandMovement(Point move, long time)
    {
        paintModel.createHandMovement(move, time);
    }
}
