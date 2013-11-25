package altik0.mtg.magictheorganizing;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CollectionManagementActivity extends Activity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_management);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collection_management, menu);
        return true;
    }
    
}
