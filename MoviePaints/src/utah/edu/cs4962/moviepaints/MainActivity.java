package utah.edu.cs4962.moviepaints;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.stream.JsonWriter;

import utah.edu.cs4962.moviepaints.view.*;
import utah.edu.cs4962.moviepaints.model.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Point;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements PaintingViewAdapter
{
    private PaintingModel paintModel;
    private PaintingView paintView;
    
    // TODO: other buttons
    private ToggleButton handTool;
    private Button brushTool;
    private Button clearButton;
    private ToggleButton pauseButton;
    private Button viewMovieButton;
    private Button saveButton;
    private Button loadButton;
    
    // Layout components
    private LinearLayout toolBar;
    private LinearLayout recordBar;
    private LinearLayout fileBar;
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
    private OnClickListener clearButtonListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            // Alert the user what they're about to do!
            AlertDialog.Builder clearAlert = new AlertDialog.Builder(MainActivity.this);
            clearAlert.setTitle("Clear Painting");
            clearAlert.setMessage("Are you sure you want to delete your painting?");
            clearAlert.setPositiveButton("Yes",
                    // Listener inside a listener! :o
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            paintModel.restart();
                        }
                    });
            clearAlert.setNegativeButton("No",
                    // Listener inside a listener! :o
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            // Do nothing - they're cancelling
                        }
                    });
        }  
    };
    private OnClickListener pauseButtonListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (!pauseButton.isChecked())
            {
                paintModel.pauseTimer();
                paintView.pauseRecord();
                pauseButton.setText("Record");
            }
            else
            {
                pauseButton.setText("Pause");
                paintView.resumeRecord();
                paintModel.resumeTimer();
            }
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
    private OnClickListener saveButtonListener = new OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
          // Build a text input alert box
          AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
          alert.setTitle("Save File");
          alert.setMessage("Provide filepath to save to:");
          
          final EditText input = new EditText(MainActivity.this);
          alert.setView(input);
          alert.setPositiveButton("Save",
                  // Listener inside a listener! :o
                  new DialogInterface.OnClickListener()
                  {
                      @Override
                      public void onClick(DialogInterface dialog, int whichButton)
                      {
                          // Have the model save with the user's input
                          paintModel.saveTo(input.getText().toString(), MainActivity.this);
                      }
                  });
          alert.setNegativeButton("Cancel",
                  // Listener inside a listener! :o
                  new DialogInterface.OnClickListener()
                  {
                      @Override
                      public void onClick(DialogInterface dialog, int whichButton)
                      {
                          // Do nothing, because why?
                      }
                  });
          
          // Display the alert box!
          alert.show();
      }
    };
    private OnClickListener loadButtonListener = new OnClickListener()
    {
        // Value saved by the alert box
        private String selectedPath;
        
        @Override
        public void onClick(View v)
        {
            // Get file paths:
            File[] files = MainActivity.this.getFilesDir().listFiles();
            final String[] paths = new String[files.length];
            for (int i = 0; i < files.length; i++)
                paths[i] = files[i].getName();
            
            // Build up an alert box with a list of these files so we can pick one
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Load File");
            
            final ListView filelist = new ListView(MainActivity.this);
            filelist.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                                                         android.R.layout.simple_list_item_1,
                                                         paths));
            filelist.setOnItemClickListener(new ListView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    selectedPath = paths[position];
                }
            });
            
            alert.setView(filelist);
            alert.setPositiveButton("Load",
                    // Listener inside a listener! :o
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            // Have the model save with the user's input
                            paintModel.loadFrom(selectedPath, MainActivity.this);
                            
                            // Reset layout, since the timer will be fucked up
                            // TODO
                            paintView.postInvalidate();
                        }
                    });
            alert.setNegativeButton("Cancel",
                    // Listener inside a listener! :o
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            // Do nothing, because why?
                        }
                    });
            
            // Display the alert box!
            alert.show();
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		paintModel = PaintingModel.getInstance();
		paintView = new PaintingView(this, this);
		
		// Art tool buttons
		handTool = new ToggleButton(this);
		handTool.setText("Hand Tool");
        handTool.setOnClickListener(handButtonListener);
        brushTool = new Button(this);
        brushTool.setText("Brush tool");
        brushTool.setOnClickListener(brushButtonListener);
        clearButton = new Button(this);
        clearButton.setText("Clear");
        clearButton.setOnClickListener(clearButtonListener);
        
        // Recording buttons: not all laid out now, but all initialized now.
        pauseButton = new ToggleButton(this);
        pauseButton.setText("Record");
        pauseButton.setOnClickListener(pauseButtonListener);
        viewMovieButton = new Button(this);
        viewMovieButton.setText("View Movie");
        viewMovieButton.setOnClickListener(viewMovieButtonListener);
        
        // File management buttons
        saveButton = new Button(this);
        saveButton.setText("Save");
        saveButton.setOnClickListener(saveButtonListener);
        loadButton = new Button(this);
        loadButton.setText("Load");
        loadButton.setOnClickListener(loadButtonListener);
        
        // Button layout params
        LinearLayout.LayoutParams buttonParams = 
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f);
        
        handTool.setLayoutParams(buttonParams);
        brushTool.setLayoutParams(buttonParams);
        clearButton.setLayoutParams(buttonParams);
        viewMovieButton.setLayoutParams(buttonParams);
        saveButton.setLayoutParams(buttonParams);
        loadButton.setLayoutParams(buttonParams);
		
		mainLayout = new LinearLayout(this);
		toolBar = new LinearLayout(this);
		recordBar = new LinearLayout(this);
		fileBar = new LinearLayout(this);
		// Determine orientation
		if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            toolBar.setOrientation(LinearLayout.HORIZONTAL);
            recordBar.setOrientation(LinearLayout.HORIZONTAL);
            fileBar.setOrientation(LinearLayout.HORIZONTAL);
        }
        else
        {
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            toolBar.setOrientation(LinearLayout.VERTICAL);
            recordBar.setOrientation(LinearLayout.VERTICAL);
            fileBar.setOrientation(LinearLayout.VERTICAL);
        }
		
		toolBar.addView(handTool);
		toolBar.addView(brushTool);
		toolBar.addView(clearButton);
		
		recordBar.addView(pauseButton);
		recordBar.addView(viewMovieButton);
		        
        fileBar.addView(saveButton);
        fileBar.addView(loadButton);

        mainLayout.addView(toolBar);
        mainLayout.addView(recordBar);
        mainLayout.addView(fileBar);
		mainLayout.addView(paintView);
		
		setContentView(mainLayout);
		
		// Do this last, so we don't waste time with all the init garbage
        if (!paintModel.isStarted())
            paintModel.startTimer();
        paintModel.pauseTimer();
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
    protected void onPause()
    {
        // Save to tmp
        paintModel.saveTo("tmp.json", this);
        super.onPause();
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
