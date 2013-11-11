package utah.edu.cs4962.collage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utah.edu.cs4962.collage.model.CollageModel;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CollageActivity extends Activity
{
    // Default file location
    private static final String DEFAULT_FILE = "tmp.json";
    
    private static final int LIBRARY_ID = 0x5138008;
    private static final int COLLAGE_ID = 0xB00B1E5;
    private static final int PHOTO_CODE = 0xF0705;
    private static final int FILES_CODE = 0xF11E5;
    
    private static final int CAMERA_MENU = 0x1234;
    private static final int FILE_SYS_MENU = 0x5678;
    private static final int SAVE_MODEL_MENU = 0xdeadcab;
    private static final int SAVE_IMAGE_MENU = 0x1337;
    private static final int LOAD_MODEL_MENU = 0x3141592;
    private static final int DELETE_MODEL_MENU = 0x102938;
    
    private LinearLayout splitView;
    private FrameLayout libraryFrame;
    private FrameLayout collageFrame;
    
    private CollageFragment collageFragment;
    private CollageLibraryFragment libraryFragment;
    
    // Used to track the last camera image path
    private File cameraFile;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        splitView = new LinearLayout(this);
        
        libraryFrame = new FrameLayout(this);
        libraryFrame.setId(LIBRARY_ID);
        collageFrame = new FrameLayout(this);
        collageFrame.setBackgroundColor(Color.BLACK);
        collageFrame.setId(COLLAGE_ID);
        
        splitView.addView(libraryFrame,
                          new LinearLayout.LayoutParams(0,
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        1));
        splitView.addView(collageFrame,
                          new LinearLayout.LayoutParams(0,
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        2));
        
        // If tmp.json exists, we'll load that existing file
        if (getBaseContext().getFileStreamPath("tmp.json").exists())
            CollageModel.loadModel("tmp.json", this);
        
        collageFragment = new CollageFragment();
        libraryFragment = new CollageLibraryFragment();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(collageFrame.getId(), collageFragment);
        trans.add(libraryFrame.getId(), libraryFragment);
        trans.commit();
        
        setContentView(splitView);
    }
    
    @Override
    protected void onPause()
    {
        CollageModel.getInstance().saveModel("tmp.json", this);

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuItem cameraMenu = menu.add(Menu.NONE, CAMERA_MENU, Menu.NONE, "Add Camera Image");
        //MenuItem fileMenu = menu.add(Menu.NONE, FILE_SYS_MENU, Menu.NONE, "Add Image From File");
        MenuItem saveModelMenu = menu.add(Menu.NONE, SAVE_MODEL_MENU, Menu.NONE, "Save Collage");
        MenuItem loadModelMenu = menu.add(Menu.NONE, LOAD_MODEL_MENU, Menu.NONE, "Load Collage");
        MenuItem deleteModelMenu = menu.add(Menu.NONE, DELETE_MODEL_MENU, Menu.NONE, "Delete Collage");
        MenuItem saveImageMenu = menu.add(Menu.NONE, SAVE_IMAGE_MENU, Menu.NONE, "Export Image");
        
        return true;
    }


    // Annoying little variable hanging around for below code. Yeah,
    // this is not a good way of doing this, but TBPH I'm too short on
    // time to really give a damn right now.
    private String[] paths;
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        File[] files;
        final ListView filelist;
        final EditText input;
        AlertDialog.Builder saveAlert;
        switch (item.getItemId())
        {
            case CAMERA_MENU:
                Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraFile = createImageFile();
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
                startActivityForResult(photoIntent, PHOTO_CODE);
                break;
            case FILE_SYS_MENU:
                // TODO
                break;
            case SAVE_MODEL_MENU:
                saveAlert = new AlertDialog.Builder(this);
                saveAlert.setTitle("Save File");
                saveAlert.setMessage("Provide filepath to save to:");
                
                input = new EditText(this);
                saveAlert.setView(input);
                saveAlert.setPositiveButton("Save",
                        // Listener inside a listener! :o
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                // Have the model save with the user's input
                                CollageModel.getInstance().saveModel(input.getText().toString(),
                                                                     CollageActivity.this);
                            }
                        });
                saveAlert.setNegativeButton("Cancel",
                        // Listener inside a listener! :o
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                // Nothing, we're cancelling after all
                            }
                        });
                
                // Display the alert box!
                saveAlert.show();
                break;
            case SAVE_IMAGE_MENU:
                saveAlert = new AlertDialog.Builder(this);
                saveAlert.setTitle("Save Image");
                saveAlert.setMessage("Provide filepath to save to:");
                
                input = new EditText(this);
                saveAlert.setView(input);
                saveAlert.setPositiveButton("Save",
                        // Listener inside a listener! :o
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                // Have the model save with the user's input
                                String filename = createImageFile(input.getText().toString()).getAbsolutePath();
                                try
                                {
                                    FileOutputStream fs = new FileOutputStream(filename);
                                    CollageModel.getInstance().getRenderedCollage().compress(
                                                            Bitmap.CompressFormat.JPEG, 90, fs);
                                    fs.close();
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
                saveAlert.setNegativeButton("Cancel",
                        // Listener inside a listener! :o
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                // Nothing, we're cancelling after all
                            }
                        });
                
                // Display the alert box!
                saveAlert.show();
                break;
            case LOAD_MODEL_MENU:
                // Get file paths:
                files = getFilesDir().listFiles();
                paths = new String[files.length];
                for (int i = 0; i < files.length; i++)
                    paths[i] = files[i].getName();
                
                // Build up an alert box with a list of these files so we can pick one
                AlertDialog.Builder loadAlert = new AlertDialog.Builder(this);
                loadAlert.setTitle("Load File");
                
                filelist = new ListView(this);
                filelist.setAdapter(new ArrayAdapter<String>(this,
                                                             android.R.layout.simple_list_item_1,
                                                             paths));
                filelist.setOnItemClickListener(new ListView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        // Have the model save with the user's input
                        CollageModel.loadModel(paths[position], CollageActivity.this);
                    }
                });
                
                loadAlert.setView(filelist);
                loadAlert.setNegativeButton("Cancel",
                        // Listener inside a listener! :o
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                // nothing - we're cancelling, after all
                            }
                        });
                
                // Display the alert box!
                loadAlert.show();
                break;
            case DELETE_MODEL_MENU:
                // Get file paths:
                files = this.getFilesDir().listFiles();
                paths = new String[files.length];
                for (int i = 0; i < files.length; i++)
                    paths[i] = files[i].getName();
                
                // Build up an alert box with a list of these files so we can pick one
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Delete Files");
                
                filelist = new ListView(this);
                filelist.setAdapter(new ArrayAdapter<String>(this,
                                                             android.R.layout.simple_list_item_1,
                                                             paths));
                filelist.setOnItemClickListener(new ListView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        // Delete the selected file, then update our list of files
                        CollageActivity.this.deleteFile(paths[position]);
                        File[] files = CollageActivity.this.getFilesDir().listFiles();
                        paths = new String[files.length];
                        for (int i = 0; i < files.length; i++)
                            paths[i] = files[i].getName();
                        filelist.setAdapter(new ArrayAdapter<String>(CollageActivity.this,
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
                                // Nothing, we're cancelling after all
                            }
                        });
                
                // Display the alert box!
                alert.show();
            default:
                break;
        }
        
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        switch (requestCode)
        {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK)
                {
                    String path = new File(cameraFile.toString()).getPath();
                    Log.i("Path:", path);
                    libraryFragment.addPathToLibrary(cameraFile.getAbsolutePath());
                    //collageFragment.setImage(BitmapFactory.decodeFile(
                    //                    cameraFile.getAbsolutePath()));
                }
                //extras = data.getExtras();
                //collageFragment.setImage((Bitmap)extras.get("data"));
                break;
            case FILES_CODE:
                // TODO
                //collageFragment.setImage((Bitmap)extras.get("selectedPath"));
            default:
                break;
        }
    }
    
    // Helper designed to create a temp file to put an image into
    private File createImageFile()
    {
        // Locate / create public directory for our pictures
        File cameraStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(
                                                Environment.DIRECTORY_PICTURES), "CollageImgs");
        // If it doesn't exist, try to create it
        if (!cameraStorageDirectory.exists())
        {
            // We'll try to create it, but if we fail we can't do anything
            if (!cameraStorageDirectory.mkdirs())
            {
                Log.i("Public directory didn't work", "Sorry, couldn't make cameraStorageDirectory");
                return null;
            }
        }
        
        String tmpFileName = cameraStorageDirectory.getPath() + File.separator + 
                             "Camera_" + new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()) + ".jpg";
        File f = new File(tmpFileName);
        return f;
    }
    
    private File createImageFile(String filename)
    {
     // Locate / create public directory for our pictures
        File cameraStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(
                                                Environment.DIRECTORY_PICTURES), "CollageImgs");
        // If it doesn't exist, try to create it
        if (!cameraStorageDirectory.exists())
        {
            // We'll try to create it, but if we fail we can't do anything
            if (!cameraStorageDirectory.mkdirs())
            {
                Log.i("Public directory didn't work", "Sorry, couldn't make cameraStorageDirectory");
                return null;
            }
        }
        
        String tmpFileName = cameraStorageDirectory.getPath() + File.separator + filename + ".jpg";
        File f = new File(tmpFileName);
        return f;
    }
}
