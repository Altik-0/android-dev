package utah.edu.cs4962.collage;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class CollageActivity extends Activity
{
    private LinearLayout splitView;
    private FrameLayout libraryFrame;
    private FrameLayout collageFrame;
    
    private ListFragment libraryFragment;
    private Fragment collageFragment;       // TODO: make this a custom fragment
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        splitView = new LinearLayout(this);
        
        // TODO: init fragments
        
        libraryFrame = new FrameLayout(this);
        libraryFrame.addView(libraryFragment.getView());
        collageFrame = new FrameLayout(this);
        collageFrame.addView(collageFragment.getView());
        
        splitView.addView(libraryFrame,
                          new LinearLayout.LayoutParams(0,
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        1));
        splitView.addView(collageFrame,
                          new LinearLayout.LayoutParams(0,
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        2));
        
        setContentView(splitView);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collage, menu);
        return true;
    }
    
}
