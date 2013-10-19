package utah.edu.cs4962.moviepaints;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import utah.edu.cs4962.moviepaints.model.PaintingModel;
import utah.edu.cs4962.moviepaints.view.PaintView;
import utah.edu.cs4962.moviepaints.view.PaintingView;
import utah.edu.cs4962.moviepaints.view.PaintingViewAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MovieActivity extends Activity implements PaintingViewAdapter
{
    public static final int REQUEST_CODE = 0x00CABB1E;
    public static final int SCRUBBER_ID = 0xABBAEDDE;
    
    private PaintingView movieView;
    private PaintingModel paintModel;
    
    // Timer to handle drawing
    private Timer movieTimer;
    private boolean isPaused;   // If true: movie timer won't animate
    private TimerTask animationTask = new TimerTask()
    {
        @Override
        public void run()
        {
            if (!isPaused)
            {
                scrubber.setProgress(scrubber.getProgress() + 1);
                movieView.postInvalidate();
            }
        }  
    };
    
    // Buttons
    private Button pauseButton;
    private Button doneButton;

    private OnClickListener pauseListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            if (pauseButton.isSelected())
            {
                pauseButton.setSelected(false);
                pauseButton.setText("Play");
                isPaused = true;
            }
            else
            {
                pauseButton.setSelected(true);
                pauseButton.setText("Pause");
                isPaused = false;
            }
        }  
    };

    private OnClickListener doneListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            finish();
        }  
    };
    
    // Scrubber
    private SeekBar scrubber;
    private OnSeekBarChangeListener scrubberListener = new OnSeekBarChangeListener()
    {

        @Override
        public void onProgressChanged(SeekBar v, int newValue, boolean arg2)
        {
            // Nothing? Don't think this really does anything, other than redraws when unslides
        }

        @Override
        public void onStartTrackingTouch(SeekBar v)
        {
            // Pause:
            isPaused = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar v)
        {
            isPaused = !pauseButton.isSelected();
            movieView.postInvalidate();
        }  
    };
    
    // ID consts
    private static final int MOVIE_VIEW_ID = 0xF00DFEED;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        movieView = new PaintingView(this, this);
        movieView.setId(MOVIE_VIEW_ID);
        
        paintModel = PaintingModel.getInstance();
        
        // Buttons
        pauseButton = new Button(this);
        pauseButton.setText("Pause");
        pauseButton.setSelected(true);
        pauseButton.setOnClickListener(pauseListener);
        
        doneButton = new Button(this);
        doneButton.setText("Done");
        doneButton.setOnClickListener(doneListener);
        
        // Scrubber
        scrubber = new SeekBar(this);
        Log.i("scrubber exp", Long.toString(paintModel.getEndTime()));
        scrubber.setMax((int)paintModel.getEndTime());
        Log.i("scrubber max", Integer.toString(scrubber.getMax()));
        scrubber.setProgress(0);
        scrubber.setOnSeekBarChangeListener(scrubberListener);
        scrubber.setId(SCRUBBER_ID);
        
        // Layout
        LinearLayout mainLayout = new LinearLayout(this);
        LinearLayout bottomLayout = new LinearLayout(this);
        LinearLayout buttonLayout = new LinearLayout(this);
        
        // Determine orientations
        if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            bottomLayout.setOrientation(LinearLayout.VERTICAL);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
        else
        {
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            bottomLayout.setOrientation(LinearLayout.VERTICAL);
            buttonLayout.setOrientation(LinearLayout.VERTICAL);
        }
        
        buttonLayout.addView(pauseButton);
        buttonLayout.addView(doneButton);

        bottomLayout.addView(scrubber);
        bottomLayout.addView(movieView);
        
        mainLayout.addView(buttonLayout);
        mainLayout.addView(bottomLayout);
        
        setContentView(mainLayout);
        
        // Setup timer - Needs to be after all the setup,
        // otherwise it may try to access elements that weren't initialized.
        isPaused = false;
        movieTimer = new Timer();
        movieTimer.scheduleAtFixedRate(animationTask, 0, 1);
    }

    @Override
    public int getCurveCount()
    {
        long time = scrubber.getProgress();
        return paintModel.getCurvesUpToTime(time).size();
    }

    @Override
    public LinkedList<Point> getCurve(int index)
    {
        long time = scrubber.getProgress();
        return paintModel.getCurvesUpToTime(time).get(index).getPoints();
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
        long time = scrubber.getProgress();
        return paintModel.getHandOffsetAtTime(time);
    }
    
    @Override
    public int getCurColor()
    {
        // We don't care, so just return transparent black
        return 0;
    }
    
    @Override
    public float getCurWidth()
    {
        // We don't care, so just return 0
        return 0.0f;
    }

    @Override
    public boolean isHandTool()
    {
        // We don't care, so just return false
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        // We are always disabled
        return false;
    }

    @Override
    public void onCreateCurve(int color, float width)
    {
        //Do nothing: this view is read only!
    }

    @Override
    public void onDrawPoint(Point newPoint)
    {
        // Do nothing: this view is read only!
    }

    @Override
    public void onHandMovement(Point move)
    {
        // Do nothing: this view is read only!
    }
}
