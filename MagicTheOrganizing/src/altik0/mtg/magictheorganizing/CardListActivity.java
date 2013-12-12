package altik0.mtg.magictheorganizing;

import altik0.mtg.magictheorganizing.CardDetailFragment.DetailFragmentState;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import altik0.mtg.magictheorganizing.Database.SearchParams;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * An activity representing a list of Cards. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link CardDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CardListFragment} and the item details (if present) is a
 * {@link CardDetailFragment}.
 * <p>
 * This activity also implements the required {@link CardListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class CardListActivity extends Activity implements
        CardListFragment.Callbacks, CardDetailFragment.ContainerCallbacks
{
    public static final String SEARCH_PARAMS_KEY = "Search";
    public static final String ALLOW_ADDITION_KEY = "Allow Addition";
    public static final String COLLECTION_MODE = "Collection mode";
    public static final String CARD_RETURN_KEY = CardDetailFragment.CARD_RETURN_KEY;
    public static final int CARD_RETURN_CODE = 0x2299;
    private boolean collectionMode = false;
    public static Intent buildSearchIntent(Context requester, SearchParams params)
    {
        Intent toRet = new Intent(requester, CardListActivity.class);
        toRet.putExtra(SEARCH_PARAMS_KEY, params);
        return toRet;
    }
    
    public static Intent buildSearchIntent(Context requester, SearchParams params,
                                           boolean allowAddition, boolean collectionMode)
    {
        Intent toRet = new Intent(requester, CardListActivity.class);
        toRet.putExtra(SEARCH_PARAMS_KEY, params);
        toRet.putExtra(ALLOW_ADDITION_KEY, allowAddition);
        toRet.putExtra(COLLECTION_MODE, collectionMode);
        
        return toRet;
    }
    
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        
        Intent intent = getIntent();
        SearchParams params = (SearchParams)intent.getSerializableExtra(SEARCH_PARAMS_KEY);
        boolean doesAllowAdd = intent.getBooleanExtra(ALLOW_ADDITION_KEY, false);
        collectionMode = intent.getBooleanExtra(COLLECTION_MODE, false);
        
        
        CardListFragment listFrag = (CardListFragment)getFragmentManager().findFragmentById(R.id.card_list);
        listFrag.setSearchParams(params);
        listFrag.setCollectionMode(doesAllowAdd);
        
        if (findViewById(R.id.card_detail_container) != null)
        {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            listFrag.setActivateOnItemClick(true);
        }
        
        // TODO: If exposing deep links into your app, handle intents here.
    }
    
    /**
     * Callback method from {@link CardListFragment.Callbacks} indicating that
     * the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(CardData card)
    {
        int card_id = collectionMode ? card.getCollectedId() : card.getCardId();
        if (mTwoPane)
        {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(CardDetailFragment.ARG_ITEM_ID, card_id);
            arguments.putBoolean(CardDetailFragment.COLLECTION_MODE, collectionMode);
            CardDetailFragment fragment = new CardDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.card_detail_container, fragment).commit();

            if (this.getCallingActivity() != null)
                fragment.setState(DetailFragmentState.Return);
            else if (collectionMode)
                fragment.setState(DetailFragmentState.Collection);
            else
                fragment.setState(DetailFragmentState.Normal);
            
        } else
        {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, CardDetailActivity.class);
            detailIntent.putExtra(CardDetailFragment.ARG_ITEM_ID, Integer.toString(card_id));
            detailIntent.putExtra(CardDetailActivity.COLLECTION_MODE, collectionMode);
            
            // If we were called for a result, we want this activity to give it to us:
            if (getCallingActivity() != null)
                startActivityForResult(detailIntent, CARD_RETURN_CODE);
            else
                startActivity(detailIntent);
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CARD_RETURN_CODE)
        {
            // If cancelled, we're continue on our way. (User probably wanted to
            // come back to this screen if they pushed <--). Otherwise, forward on
            // them results!
            if (resultCode == RESULT_CANCELED)
                return;
            setResult(resultCode, data);
            finish();
        }
        
        // If it wasn't our activity that called it, we'll assume the super class did:
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void detailContentDeleted()
    {
        // TODO: something smarter. For now, just tell list fragment to select top element
        CardListFragment listFrag = (CardListFragment)getFragmentManager().findFragmentById(R.id.card_list);
        int curPos = listFrag.getSelectedItemPosition();
        if (listFrag.getListAdapter().getCount() == 0)
        {
            // ???
        }
        else
        {
            curPos = (curPos == 0) ? curPos + 1 : curPos - 1;
        }
        listFrag.setSelection(curPos);
    }
}
