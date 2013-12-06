package altik0.mtg.magictheorganizing;

import altik0.mtg.magictheorganizing.Database.SearchParams;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AdvancedSearchActivity extends Activity
{
    public static final String RETURNED_CARD_KEY = CardListActivity.RETURNED_CARD_KEY;
    public static final int LIST_SELECTION_CODE = 0x800b1e5;
    public static final String RETURN_SELECTED_CARD_KEY = "searchToAdd";
    
    // This class may be called by another activity with startActivityForResult()
    // with the intent that this activity ultimately returns a single CardData that
    // was selected from the search. This bool tracks that intent
    private boolean isSearchToAdd = false;
    public static Intent buildAddIntent(Context requester)
    {
        Intent toRet = new Intent(requester, AdvancedSearchActivity.class);
        toRet.putExtra(RETURN_SELECTED_CARD_KEY, true);
        return toRet;
    }
    
    OnClickListener searchListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            // ALL THE CHECKBOXES! >:O
            // Colors:
            CheckBox whiteBox = (CheckBox)findViewById(R.id.whiteCheckBox);
            CheckBox blueBox = (CheckBox)findViewById(R.id.blueCheckBox);
            CheckBox blackBox = (CheckBox)findViewById(R.id.blackCheckBox);
            CheckBox redBox = (CheckBox)findViewById(R.id.redCheckBox);
            CheckBox greenBox = (CheckBox)findViewById(R.id.greenCheckBox);
            CheckBox colorlessBox = (CheckBox)findViewById(R.id.colorlessCheckBox);
            CheckBox reqMultiBox = (CheckBox)findViewById(R.id.forceMulticolorBox);
            CheckBox excUnselectedBox = (CheckBox)findViewById(R.id.excludeUnselectedBox);
            
            // Rarity:
            CheckBox commonBox = (CheckBox)findViewById(R.id.commonCheckBox);
            CheckBox uncommonBox = (CheckBox)findViewById(R.id.uncommonCheckBox);
            CheckBox rareBox = (CheckBox)findViewById(R.id.rareCheckBox);
            CheckBox mythicBox = (CheckBox)findViewById(R.id.mythicCheckBox);
            
            // TEXTBOXES TOO GRAWR
            EditText nameBox = (EditText)findViewById(R.id.nameTextBox);
            EditText typeBox = (EditText)findViewById(R.id.cardTypeTextBox);
            EditText expansionBox = (EditText)findViewById(R.id.expansionTextBox);
            EditText textBox = (EditText)findViewById(R.id.cardTextTextBox);
            
            // Build search params:
            // TODO: comma separated tags
            SearchParams params = new SearchParams();
            if (!nameBox.getText().toString().equals(""))
                params.NameSearch = nameBox.getText().toString();
            if (!typeBox.getText().toString().equals(""))
                params.TypeSearch = typeBox.getText().toString();
            if (!textBox.getText().toString().equals(""))
                params.TextSearch = textBox.getText().toString();
            if (!expansionBox.getText().toString().equals(""))
                params.ExpansionSearch = expansionBox.getText().toString();
            
            int colorSearch = 0;
            if (whiteBox.isChecked())
                colorSearch |= SearchParams.WHITE_FLAG;
            if (blueBox.isChecked())
                colorSearch |= SearchParams.BLUE_FLAG;
            if (blackBox.isChecked())
                colorSearch |= SearchParams.BLACK_FLAG;
            if (redBox.isChecked())
                colorSearch |= SearchParams.RED_FLAG;
            if (greenBox.isChecked())
                colorSearch |= SearchParams.GREEN_FLAG;
            if (colorlessBox.isChecked())
                colorSearch |= SearchParams.COLORLESS_FLAG;
            if (reqMultiBox.isChecked())
                colorSearch |= SearchParams.REQUIRE_MULTICOLOR_FLAG;
            if (excUnselectedBox.isChecked())
                colorSearch |= SearchParams.EXCLUDE_UNSELECTED_FLAG;
            if ((colorSearch & ~(SearchParams.EXCLUDE_UNSELECTED_FLAG | 
                                 SearchParams.REQUIRE_MULTICOLOR_FLAG)) != 0)
                params.ColorFilter = colorSearch;
            
            int raritySearch = 0;
            if (commonBox.isChecked())
                raritySearch |= SearchParams.COMMON_FLAG;
            if (uncommonBox.isChecked())
                raritySearch |= SearchParams.UNCOMMON_FLAG;
            if (rareBox.isChecked())
                raritySearch |= SearchParams.RARE_FLAG;
            if (mythicBox.isChecked())
                raritySearch |= SearchParams.MYTHIC_FLAG;
            if (raritySearch != 0)
                params.RarityFilter = raritySearch;
            
            // Build the intent, and launch that activity!
            Intent searchIntent = CardListActivity.buildSearchWithReurnIntent(
                    AdvancedSearchActivity.this, params, isSearchToAdd);
            
            // If we're searching for a result, the CardListActivity will return
            // the selected card to us!
            
            if (isSearchToAdd)
                startActivityForResult(searchIntent, LIST_SELECTION_CODE);
            else
                startActivity(searchIntent);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        
        Intent searchIntent = getIntent();
        isSearchToAdd = searchIntent.getBooleanExtra(RETURN_SELECTED_CARD_KEY, false);
        
        Button searchButton = (Button)findViewById(R.id.advancedSearchButton);
        searchButton.setOnClickListener(searchListener);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.advanced_search, menu);
        return true;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == LIST_SELECTION_CODE)
        {
            // We don't really care about what the data we got back was, we're
            // just going to forward it on one way or the other
            setResult(resultCode, data);
            finish();
        }
        // Otherwise, we didn't do it so presumably super class cares instead
        super.onActivityResult(requestCode, resultCode, data);
    }
}
