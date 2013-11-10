package utah.edu.cs4962.collage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utah.edu.cs4962.collage.model.CollageModel;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class CollageActivity extends Activity
{
    private static final int LIBRARY_ID = 0x5138008;
    private static final int COLLAGE_ID = 0xB00B1E5;
    private static final int PHOTO_CODE = 0xF0705;
    private static final int FILES_CODE = 0xF11E5;
    private static final int CAMERA_MENU = 0x1234;
    private static final int FILE_SYS_MENU = 0x5678;
    
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
        
        // TODO: init fragments
        
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
        
        collageFragment = new CollageFragment();
        libraryFragment = new CollageLibraryFragment();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(collageFrame.getId(), collageFragment);
        trans.add(libraryFrame.getId(), libraryFragment);
        trans.commit();
        
        setContentView(splitView);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuItem cameraMenu = menu.add(Menu.NONE, CAMERA_MENU, Menu.NONE, "Add Camera Image");
        MenuItem fileMenu = menu.add(Menu.NONE, FILE_SYS_MENU, Menu.NONE, "Add Saved Image");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case CAMERA_MENU:
                Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraFile = createImageFile();
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
                startActivityForResult(photoIntent, PHOTO_CODE);
                break;
            // TODO:
            case FILE_SYS_MENU:
            default:
                break;
        }
        
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        Bundle extras;
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
                extras = data.getExtras();
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
}
