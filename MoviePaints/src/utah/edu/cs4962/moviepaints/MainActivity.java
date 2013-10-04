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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
    private Button recordButton;
    private ToggleButton pauseButton;
    private Button stopButton;
    private Button viewMovieButton;
    
    // Layout components
    private LinearLayout toolBar;
    private LinearLayout mainLayout;
    
    // Used to determine if unpausing is needed after returning from subactivity
    private boolean needsUnpause;
    
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
            // Before we go do that, let's not eat up the user's time - seems reasonable
            // However, we don't want to "double pause", nor unpause if we are presently,
            // so pass a bit of state along
            if (!paintModel.isPaused() && paintModel.isStarted())
            {
                paintModel.pauseTimer();
                paintView.pauseRecord();
                needsUnpause = true;
            }
            else
                needsUnpause = false;
            
            Intent paletteIntent = new Intent(MainActivity.this, PaletteActivity.class);
            startActivityForResult(paletteIntent, REQUEST_CODE);
        }
    };
    private OnClickListener recordButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // Re-layout:
            toolBar.removeAllViews();
            toolBar.addView(handTool);
            toolBar.addView(brushTool);
            toolBar.addView(pauseButton);
            toolBar.addView(stopButton);
            toolBar.addView(viewMovieButton);
            viewMovieButton.setEnabled(false);
            
            paintView.startRecord();
            paintModel.restart();
            paintModel.startTimer();
        }
    };
    private OnClickListener pauseButtonListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (pauseButton.isChecked())
            {
                paintModel.pauseTimer();
                paintView.pauseRecord();
                pauseButton.setText("Continue");
                viewMovieButton.setEnabled(true);
            }
            else
            {
                pauseButton.setText("Pause");
                viewMovieButton.setEnabled(false);
                paintView.resumeRecord();
                paintModel.resumeTimer();
            }
        }
    };
    private OnClickListener stopButtonListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            paintModel.stopTimer();
            paintView.stopRecord();
            
            // Layout again
            toolBar.removeAllViews();
            toolBar.addView(handTool);
            toolBar.addView(brushTool);
            toolBar.addView(recordButton);
        }
    };
    private OnClickListener viewMovieButtonListener = new Button.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // TODO: launch Movie activity to view it.
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
        
        // Recording buttons: not all laid out now, but all initialized now.
        recordButton = new Button(this);
        recordButton.setText("Record");
        recordButton.setOnClickListener(recordButtonListener );
        pauseButton = new ToggleButton(this);
        pauseButton.setText("Pause");
        pauseButton.setOnClickListener(pauseButtonListener);
        stopButton = new Button(this);
        stopButton.setText("Stop");
        stopButton.setOnClickListener(stopButtonListener);
        viewMovieButton = new Button(this);
        viewMovieButton.setText("View Movie");
        viewMovieButton.setOnClickListener(viewMovieButtonListener);
		
		mainLayout = new LinearLayout(this);
		toolBar = new LinearLayout(this);
		// Determine orientation
		if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            toolBar.setOrientation(LinearLayout.HORIZONTAL);
        }
        else
        {
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            toolBar.setOrientation(LinearLayout.VERTICAL);
        }
		
		toolBar.addView(handTool);
		toolBar.addView(brushTool);
		toolBar.addView(recordButton);
		
		mainLayout.addView(toolBar);
		mainLayout.addView(paintView);
		
		setContentView(mainLayout);
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
            
            // unpause if necessary
            if (needsUnpause)
            {
                paintView.resumeRecord();
                paintModel.resumeTimer();
            }
        }
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
    public void onDrawPoint(Point newPoint)
    {
        paintModel.drawPoint(newPoint);
    }

    @Override
    public void onHandMovement(Point move)
    {
        paintModel.createHandMovement(move);
    }
}
