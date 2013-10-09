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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements PaintingViewAdapter
{
    private PaintingModel paintModel;
    private PaintingView paintView;
    
    // TODO: other buttons
    private ToggleButton handTool;
    private Button brushTool;
    private Button recordButton;
    private ToggleButton pauseButton;
    private Button stopButton;
    private Button viewMovieButton;
    
    // Layout components
    private LinearLayout toolBar;
    private LinearLayout recordBar;
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
            startActivityForResult(paletteIntent, PaletteActivity.REQUEST_CODE);
        }
    };
    private OnClickListener recordButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // Re-layout:
            recordBar.removeAllViews();
            recordBar.addView(pauseButton);
            recordBar.addView(stopButton);
            recordBar.addView(viewMovieButton);
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
            recordBar.removeAllViews();
            recordBar.addView(recordButton);
        }
    };
    private OnClickListener viewMovieButtonListener = new Button.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent movieIntent = new Intent(MainActivity.this, MovieActivity.class);
            startActivity(movieIntent);
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		paintModel = PaintingModel.getInstance();
		paintView = new PaintingView(this, this);
		
		handTool = new ToggleButton(this);
		handTool.setText("Hand Tool");
        handTool.setOnClickListener(handButtonListener);
        brushTool = new Button(this);
        brushTool.setText("Brush tool");
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
        
        // Button layout params
        LinearLayout.LayoutParams buttonParams = 
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f);
        
        handTool.setLayoutParams(buttonParams);
        brushTool.setLayoutParams(buttonParams);
        recordButton.setLayoutParams(buttonParams);
        stopButton.setLayoutParams(buttonParams);
        viewMovieButton.setLayoutParams(buttonParams);
		
		mainLayout = new LinearLayout(this);
		toolBar = new LinearLayout(this);
		recordBar = new LinearLayout(this);
		// Determine orientation
		if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            toolBar.setOrientation(LinearLayout.HORIZONTAL);
            recordBar.setOrientation(LinearLayout.HORIZONTAL);
        }
        else
        {
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            toolBar.setOrientation(LinearLayout.VERTICAL);
            recordBar.setOrientation(LinearLayout.VERTICAL);
        }
		
		toolBar.addView(handTool);
		toolBar.addView(brushTool);
		
		recordBar.addView(recordButton);

        mainLayout.addView(toolBar);
        mainLayout.addView(recordBar);
		mainLayout.addView(paintView);
		
		setContentView(mainLayout);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PaletteActivity.REQUEST_CODE)
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
    public Point getHandOffset()
    {
        return paintModel.getHandOffset();
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
