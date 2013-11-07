package utah.edu.cs4962.collage;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class CollageActivity extends Activity
{
    private static final int LIBRARY_ID = 0x5138008;
    private static final int COLLAGE_ID = 0xB00B1E5;
    
    private LinearLayout splitView;
    private FrameLayout libraryFrame;
    private FrameLayout collageFrame;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        splitView = new LinearLayout(this);
        
        // TODO: init fragments
        
        libraryFrame = new FrameLayout(this);
        libraryFrame.setId(LIBRARY_ID);
        collageFrame = new FrameLayout(this);
        collageFrame.setId(COLLAGE_ID);
        
        splitView.addView(libraryFrame,
                          new LinearLayout.LayoutParams(0,
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        1));
        splitView.addView(collageFrame,
                          new LinearLayout.LayoutParams(0,
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        2));
        
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(collageFrame.getId(), new CollageFragment());
        // TODO: list fragment
        trans.commit();
        
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
