package altik0.mtg.magictheorganizing;

import altik0.mtg.magictheorganizing.Database.SearchParams;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity
{
    
    private OnClickListener mainMenuListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            if (arg0 == findViewById(R.id.searchAllCardsButton))
            {
                // TODO: add intent extra specifying 'ALL CARDS'
                Intent searchIntent = new Intent(MainMenuActivity.this, AdvancedSearchActivity.class);
                startActivity(searchIntent);
            }
            else if (arg0 == findViewById(R.id.searchYourCollectionsButton))
            {
                // TODO: add intent extra specifying 'YOUR COLLECTION'
                Intent searchIntent = new Intent(MainMenuActivity.this, AdvancedSearchActivity.class);
                startActivity(searchIntent);
            }
            else if (arg0 == findViewById(R.id.manageYourCollectionsButton))
            {
                // TODO: add intent extra specifying 'ALL CARDS'
                Intent manageIntent = new Intent(MainMenuActivity.this, CollectionManagementActivity.class);
                startActivity(manageIntent);
            }
            else if (arg0 == findViewById(R.id.quickSearchButton))
            {
                // launch straight to SearchResultsActivity, using a search intent
                // filled out based on the quick-search data
                // TODO: build params properly
                SearchParams params = new SearchParams();
                Intent searchIntent = CardListActivity.buildSearchIntent(MainMenuActivity.this, params);
                startActivity(searchIntent);
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        Button searchAllCardsButton = (Button)findViewById(R.id.searchAllCardsButton);
        searchAllCardsButton.setOnClickListener(mainMenuListener);
        
        Button searchYourCollectionsButton = (Button)findViewById(R.id.searchYourCollectionsButton);
        searchYourCollectionsButton.setOnClickListener(mainMenuListener);
        
        Button manageYourCollectionsButton = (Button)findViewById(R.id.manageYourCollectionsButton);
        manageYourCollectionsButton.setOnClickListener(mainMenuListener);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
}
