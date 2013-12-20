package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.Database.SearchParams;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;

public class MainMenuActivity extends Activity
{
    private AutoCompleteTextView quickSearchBox;
    private ArrayAdapter<String> adapter;
    
    private OnClickListener mainMenuListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            if (arg0 == findViewById(R.id.searchAllCardsButton))
            {
                Intent searchIntent = new Intent(MainMenuActivity.this, AdvancedSearchActivity.class);
                startActivity(searchIntent);
            }
            else if (arg0 == findViewById(R.id.searchYourCollectionsButton))
            {
                Intent searchIntent = new Intent(MainMenuActivity.this, CollectionSearchActivity.class);
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
                
                // Build search params - based on checked boxes
                String quickSearchVal = null;
                if (!(quickSearchBox.getText().toString().equals("")))
                    quickSearchVal = quickSearchBox.getText().toString();
                
                Log.i("quicksearchbox: ", " " + quickSearchBox.getText().toString());
                
                SearchParams params = new SearchParams();
                CheckBox nameCheck = (CheckBox)findViewById(R.id.quickSearchNameCheck);
                CheckBox textCheck = (CheckBox)findViewById(R.id.quickSearchTextCheck);
                CheckBox typeCheck = (CheckBox)findViewById(R.id.quickSearchTypeCheck);
                if (nameCheck.isChecked())
                    params.NameSearch = quickSearchVal;
                if (textCheck.isChecked())
                    params.TextSearch = quickSearchVal;
                if (typeCheck.isChecked())
                    params.TypeSearch = quickSearchVal;
                
                Intent searchIntent = CardListActivity.buildSearchIntent(MainMenuActivity.this, params);
                startActivity(searchIntent);
            }
        }
    };
    
    private OnCheckedChangeListener nameCheckListener = new OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton view, boolean checked)
        {
            if (checked)
                quickSearchBox.setAdapter(adapter);
            else
                quickSearchBox.setAdapter(null);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        ArrayList<String> cardNames = MtgDatabaseManager.getInstance(this).GetAllCardNames();
        adapter = new ArrayAdapter<String>(
                                this,
                                android.R.layout.select_dialog_item,
                                cardNames);
        
        quickSearchBox = (AutoCompleteTextView)findViewById(R.id.quickSearch);
        //quickSearchBox.setAdapter(adapter);
        quickSearchBox.setAdapter(null);
        
        Button searchAllCardsButton = (Button)findViewById(R.id.searchAllCardsButton);
        searchAllCardsButton.setOnClickListener(mainMenuListener);
        
        Button searchYourCollectionsButton = (Button)findViewById(R.id.searchYourCollectionsButton);
        searchYourCollectionsButton.setOnClickListener(mainMenuListener);
        
        Button manageYourCollectionsButton = (Button)findViewById(R.id.manageYourCollectionsButton);
        manageYourCollectionsButton.setOnClickListener(mainMenuListener);
        
        Button quickSearchButton = (Button)findViewById(R.id.quickSearchButton);
        quickSearchButton.setOnClickListener(mainMenuListener);
        
        CheckBox nameBox = (CheckBox)findViewById(R.id.quickSearchNameCheck);
        nameBox.setOnCheckedChangeListener(nameCheckListener);
    }
}