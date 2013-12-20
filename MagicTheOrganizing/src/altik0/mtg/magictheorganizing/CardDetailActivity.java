package altik0.mtg.magictheorganizing;

import altik0.mtg.magictheorganizing.CardDetailFragment.ContainerCallbacks;
import altik0.mtg.magictheorganizing.CardDetailFragment.DetailFragmentState;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * An activity representing a single Card detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link CardListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link CardDetailFragment}.
 */
public class CardDetailActivity extends Activity implements ContainerCallbacks
{
    public static final String COLLECTION_MODE = "Collection mode";
    boolean collectionMode = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        
        Intent intent = getIntent();
        collectionMode = intent.getBooleanExtra(COLLECTION_MODE, false);
        
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null)
        {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            String idStr = getIntent().getStringExtra(CardDetailFragment.ARG_ITEM_ID);
            int id = Integer.parseInt(idStr);
            arguments.putInt(CardDetailFragment.ARG_ITEM_ID, id);
            arguments.putBoolean(CardDetailFragment.COLLECTION_MODE, collectionMode);
            CardDetailFragment fragment = new CardDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.card_detail_container, fragment).commit();
            fragment.setContainerCallbacks(this);

            if (this.getCallingActivity() != null)
                fragment.setState(DetailFragmentState.Return);
            else if (collectionMode)
                fragment.setState(DetailFragmentState.Collection);
            else
                fragment.setState(DetailFragmentState.Normal);
        }

        collectionMode = intent.getBooleanExtra(COLLECTION_MODE, false);
        collectionMode = intent.getBooleanExtra(COLLECTION_MODE, false);
        collectionMode = intent.getBooleanExtra(COLLECTION_MODE, false);
        collectionMode = intent.getBooleanExtra(COLLECTION_MODE, false);
        collectionMode = intent.getBooleanExtra(COLLECTION_MODE, false);
        collectionMode = intent.getBooleanExtra(COLLECTION_MODE, false);
    }

    @Override
    public void detailContentDeleted()
    {
        finish();
    }
}
