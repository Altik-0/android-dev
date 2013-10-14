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
    // ID values
    private static final int PAUSE_ID = 0x12345678;
    private static final int HAND_ID = 0x87654321;
    private static final int BRUSH_ID = 0x23456789;
    private static final int SAVE_ID = 0x98765432;
    private static final int VIEWMOVIE_ID = 0x34567890;
    private static final int LOAD_ID = 0x09876543;
    private static final int CLEAR_ID = 0x4567890A;
    private static final int DELETE_ID = 0xA0987654;
    private static final int PAINT_ID = 0xBADA55E5;
    
    private PaintingModel paintModel;
    private PaintingView paintView;
    
    // TODO: other buttons
    private ToggleButton handTool;
    private PaintButton brushTool;
    private Button clearButton;
    private ToggleButton pauseButton;
    private Button viewMovieButton;
    private Button saveButton;
    private Button loadButton;
    private Button deleteButton;
    
    // Layout components
    private LinearLayout toolBar;
    private LinearLayout recordBar;
    private LinearLayout toolPlusRecord;
    
    private LinearLayout fileBar;
    private LinearLayout drawLayout;
    private LinearLayout mainLayout;
    
    // Used to determine if unpausing is needed after returning from subactivity
    private boolean needsUnpause;
    
    // Button listeners
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
                            paintView.postInvalidate();
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
            
            clearAlert.show();
        }
    };
    private OnCheckedChangeListener pauseButtonListener = new OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean arg1)
        {
            if (!pauseButton.isChecked())
            {
                paintModel.pauseTimer();
            }
            else
            {
                paintModel.resumeTimer();
            }
            viewMovieButton.setEnabled(!pauseButton.isChecked());
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
          // Pause model
          paintModel.pauseTimer();
          
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
                          
                          // Resume if the pause button permits:
                          if (pauseButton.isChecked())
                              paintModel.resumeTimer();
                      }
                  });
          alert.setNegativeButton("Cancel",
                  // Listener inside a listener! :o
                  new DialogInterface.OnClickListener()
                  {
                      @Override
                      public void onClick(DialogInterface dialog, int whichButton)
                      {
                          // Resume if the pause button permits:
                          if (pauseButton.isChecked())
                              paintModel.resumeTimer();
                      }
                  });
          
          // Display the alert box!
          alert.show();
      }
    };
    private OnClickListener loadButtonListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // pause model
            paintModel.pauseTimer();
            
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
                    // Have the model save with the user's input
                    paintModel.loadFrom(paths[position], MainActivity.this);
                    
                    // Reset layout, since the timer will be fucked up
                    handTool.setChecked(false);
                    pauseButton.setChecked(false);
                    paintView.postInvalidate();
                }
            });
            
            alert.setView(filelist);
            alert.setNegativeButton("Cancel",
                    // Listener inside a listener! :o
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            // resume, if the pause button permits
                            if (pauseButton.isChecked())
                                paintModel.resumeTimer();
                        }
                    });
            
            // Display the alert box!
            alert.show();
        }
    };
    private OnClickListener deleteButtonListener = new OnClickListener()
    {
        // Used to track filenames
        private String[] paths;
        
        @Override
        public void onClick(View v)
        {
            // be sure to pause the recording:
            paintModel.pauseTimer();
            
            // Get file paths:
            File[] files = MainActivity.this.getFilesDir().listFiles();
            paths = new String[files.length];
            for (int i = 0; i < files.length; i++)
                paths[i] = files[i].getName();
            
            // Build up an alert box with a list of these files so we can pick one
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Delete Files");
            
            final ListView filelist = new ListView(MainActivity.this);
            filelist.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                                                         android.R.layout.simple_list_item_1,
                                                         paths));
            filelist.setOnItemClickListener(new ListView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    // Delete the selected file, then update our list of files
                    MainActivity.this.deleteFile(paths[position]);
                    File[] files = MainActivity.this.getFilesDir().listFiles();
                    paths = new String[files.length];
                    for (int i = 0; i < files.length; i++)
                        paths[i] = files[i].getName();
                    filelist.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            paths));
                }
            });
            
            alert.setView(filelist);
            alert.setNegativeButton("Done",
                    // Listener inside a listener! :o
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            // Resume, if pause button permits
                            if (pauseButton.isChecked())
                                paintModel.resumeTimer();
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
		// If tmp.json exists, this means that we have a presaved version
        if (getBaseContext().getFileStreamPath("tmp.json").exists())
            paintModel.loadFrom("tmp.json", this);
        
		paintView = new PaintingView(this, this);
		
		// Art tool buttons
		handTool = new ToggleButton(this);
		handTool.setTextOn("Hand Tool");
		handTool.setTextOff("Disable hand");
		handTool.setChecked(false);
        brushTool = new PaintButton(this);
        brushTool.setText("   ");
        brushTool.setOnClickListener(brushButtonListener);
        brushTool.setBrushColor(0xFF000000);
        brushTool.setBrushWidth(PaintingView.MAX_WIDTH / 2.0f);
        clearButton = new Button(this);
        clearButton.setText("Clear");
        clearButton.setOnClickListener(clearButtonListener);
        
        // Recording buttons: not all laid out now, but all initialized now.
        pauseButton = new ToggleButton(this);
        pauseButton.setTextOff("Record");
        pauseButton.setTextOn("Pause");
        pauseButton.setChecked(false);
        pauseButton.setOnCheckedChangeListener(pauseButtonListener);
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
        deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(deleteButtonListener);
        
        // Button IDs
        handTool.setId(HAND_ID);
        brushTool.setId(BRUSH_ID);
        clearButton.setId(CLEAR_ID);
        pauseButton.setId(PAUSE_ID);
        viewMovieButton.setId(VIEWMOVIE_ID);
        saveButton.setId(SAVE_ID);
        loadButton.setId(LOAD_ID);
        deleteButton.setId(DELETE_ID);
		
        // Button layout params
        LinearLayout.LayoutParams buttonParamsHoriz = 
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f);
        LinearLayout.LayoutParams buttonParamsVert = 
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f);
        
		mainLayout = new LinearLayout(this);
		toolBar = new LinearLayout(this);
		recordBar = new LinearLayout(this);
        toolPlusRecord = new LinearLayout(this);
		fileBar = new LinearLayout(this);
		drawLayout = new LinearLayout(this);
		// Determine orientation
		if (getWindowManager().getDefaultDisplay().getWidth() < getWindowManager().getDefaultDisplay().getHeight())
        {
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            toolBar.setOrientation(LinearLayout.HORIZONTAL);
            recordBar.setOrientation(LinearLayout.HORIZONTAL);
            toolPlusRecord.setOrientation(LinearLayout.VERTICAL);
            fileBar.setOrientation(LinearLayout.HORIZONTAL);
            drawLayout.setOrientation(LinearLayout.VERTICAL);
         
            handTool.setLayoutParams(buttonParamsHoriz);
            brushTool.setLayoutParams(buttonParamsHoriz);
            clearButton.setLayoutParams(buttonParamsHoriz);
            pauseButton.setLayoutParams(buttonParamsHoriz);
            viewMovieButton.setLayoutParams(buttonParamsHoriz);
            saveButton.setLayoutParams(buttonParamsHoriz);
            loadButton.setLayoutParams(buttonParamsHoriz);
            deleteButton.setLayoutParams(buttonParamsHoriz);
        }
        else
        {
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            toolBar.setOrientation(LinearLayout.VERTICAL);
            recordBar.setOrientation(LinearLayout.VERTICAL);
            toolPlusRecord.setOrientation(LinearLayout.VERTICAL);
            fileBar.setOrientation(LinearLayout.HORIZONTAL);
            drawLayout.setOrientation(LinearLayout.VERTICAL);
         
            handTool.setLayoutParams(buttonParamsVert);
            brushTool.setLayoutParams(buttonParamsVert);
            clearButton.setLayoutParams(buttonParamsVert);
            pauseButton.setLayoutParams(buttonParamsVert);
            viewMovieButton.setLayoutParams(buttonParamsVert);
            saveButton.setLayoutParams(buttonParamsHoriz);
            loadButton.setLayoutParams(buttonParamsHoriz);
            deleteButton.setLayoutParams(buttonParamsHoriz);
        }
        
        toolBar.addView(handTool);
        toolBar.addView(brushTool);
        toolBar.addView(clearButton);
        
        recordBar.addView(pauseButton);
        recordBar.addView(viewMovieButton);
        
        toolPlusRecord.addView(toolBar);
        toolPlusRecord.addView(recordBar);
                
        fileBar.addView(saveButton);
        fileBar.addView(loadButton);
        fileBar.addView(deleteButton);
        
        drawLayout.addView(fileBar);
        drawLayout.addView(paintView);

        mainLayout.addView(toolPlusRecord);
        mainLayout.addView(drawLayout);
		
		setContentView(mainLayout);
		
        viewMovieButton.setEnabled(!pauseButton.isChecked());
		
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
                brushTool.setBrushColor(bndl.getInt("color"));
                brushTool.setBrushWidth(bndl.getFloat("width"));
            }
            
            // unpause if necessary
            if (needsUnpause)
            {
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
    public int getCurColor()
    {
        return brushTool.getBrushColor();
    }

    @Override
    public float getCurWidth()
    {
        return brushTool.getBrushWidth();
    }
    
    @Override
    public boolean isHandTool()
    {
        return handTool.isChecked();
    }
    
    @Override
    public boolean isEnabled()
    {
        return pauseButton.isChecked();
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
