package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.Database.SearchParams;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class CollectionSearchActivity extends Activity
{
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
            
            // SPINNER! :O
            Spinner collectionSpinner = (Spinner)findViewById(R.id.collectionSpinner);
            
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
            if (colorSearch != 0 && excUnselectedBox.isChecked())
                colorSearch |= SearchParams.EXCLUDE_UNSELECTED_FLAG;
            if (reqMultiBox.isChecked())
                colorSearch |= SearchParams.REQUIRE_MULTICOLOR_FLAG;
            if (colorSearch != 0)
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
            
            params.searchOverCollection = true;
            params.CollectionId = null;
            int index = collectionSpinner.getSelectedItemPosition();
            // If index == 0, the collecitonSpinner selected dummy item, indicating we
            // want to select over all collections, rather than just one
            if (index != 0)
                params.CollectionId = collections.get(index).CollectionId;
            
            // Build the intent, and launch that activity!
            Intent searchIntent = CardListActivity.buildSearchIntent(
                    CollectionSearchActivity.this, params, false, true);
            startActivity(searchIntent);
        }
    };
    
    private ArrayList<Collection> collections;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_search);
        
        collections = MtgDatabaseManager.getInstance(this).GetCollections();
        Collection dummyCollection = new Collection();
        dummyCollection.Name = "All Collections";
        collections.add(0, dummyCollection);
        
        Spinner collectionSpinner = (Spinner)findViewById(R.id.collectionSpinner);
        ArrayAdapter<Collection> ad = new ArrayAdapter<Collection>(
                                        this, 
                                        android.R.layout.simple_spinner_item,
                                        collections);
        collectionSpinner.setAdapter(ad);
        
        Button searchButton = (Button)findViewById(R.id.advancedSearchButton);
        searchButton.setOnClickListener(searchListener);
    }
}
