package altik0.mtg.magictheorganizing;

import altik0.mtg.magictheorganizing.Database.SearchParams;
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
        CardListFragment.Callbacks
{
    public static final String RETURNED_CARD_KEY = CardDetailFragment.RETURNED_CARD_KEY;
    public static final String WANT_RETURN_VALUE_KEY = "pleaseReturnMeSomethingSir";
    public static final int DETAIL_REQUEST_CODE = 0x1337;
    
    // Tracks whether we want to return a card from executing this activity or not
    private boolean doWantReturnPlz = false;
    
    public static Intent buildSearchIntent(Context requester, SearchParams params)
    {
        Intent toRet = new Intent(requester, CardListActivity.class);
        toRet.putExtra(CardListFragment.SEARCH_PARAMS_KEY, params);
        return toRet;
    }
    
    public static Intent buildSearchIntent(Context requester, Integer collectionId,
                                           boolean collectionMode, SearchParams params)
    {
        Intent toRet = new Intent(requester, CardListActivity.class);
        toRet.putExtra(CardListFragment.SEARCH_PARAMS_KEY, params);
        if (collectionId != null)
            toRet.putExtra(CardListFragment.COLLECTION_ID_KEY, collectionId);
        toRet.putExtra(CardListFragment.COLLECTION_MODE, collectionMode);
        
        return toRet;
    }
    
    public static Intent buildSearchWithReurnIntent(Context requester, SearchParams params,
                                                    boolean doWantReturnThx)
    {
        Intent toRet = new Intent(requester, CardListActivity.class);
        toRet.putExtra(CardListFragment.SEARCH_PARAMS_KEY, params);
        toRet.putExtra(WANT_RETURN_VALUE_KEY, doWantReturnThx);
        
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
        
        SearchParams params = (SearchParams)getIntent()
                .getExtras().getSerializable(CardListFragment.SEARCH_PARAMS_KEY);
        
        Intent extras = getIntent();
        doWantReturnPlz = extras.getBooleanExtra(RETURNED_CARD_KEY, false);
        boolean colMode = extras.getBooleanExtra(CardListFragment.COLLECTION_MODE, false);
        Integer collectionId = null;
        if (extras.hasExtra(CardListFragment.COLLECTION_ID_KEY))
            collectionId = extras.getIntExtra(CardListFragment.COLLECTION_ID_KEY, 0);

        Bundle listFragArgs = new Bundle();
        listFragArgs.putSerializable(CardListFragment.SEARCH_PARAMS_KEY, params);
        listFragArgs.putInt(CardListFragment.COLLECTION_ID_KEY, collectionId);
        listFragArgs.putBoolean(CardListFragment.COLLECTION_MODE, colMode);
        CardListFragment listFrag = (CardListFragment)getFragmentManager()
                .findFragmentById(R.id.card_list);
        listFrag.setArguments(listFragArgs);
        
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
    public void onItemSelected(int card_id)
    {
        if (mTwoPane)
        {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(CardDetailFragment.ARG_ITEM_ID, card_id);
            arguments.putBoolean(CardDetailFragment.RETURNED_CARD_KEY, doWantReturnPlz);
            CardDetailFragment fragment = new CardDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.card_detail_container, fragment).commit();
            
        } else
        {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, CardDetailActivity.class);
            detailIntent.putExtra(CardDetailFragment.ARG_ITEM_ID, Integer.toString(card_id));
            detailIntent.putExtra(CardDetailActivity.RETRNED_CARD_KEY, doWantReturnPlz);
            
            // If we've been called to return a result, call for result:
            if (doWantReturnPlz)
                startActivityForResult(detailIntent, DETAIL_REQUEST_CODE);
            else
                startActivity(detailIntent);
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == DETAIL_REQUEST_CODE)
        {
            // If we got an a-ok on the data, we're done and we'll pass it on!
            if (resultCode == RESULT_OK)
            {
                setResult(resultCode, data);
                finish();
            }
        }
        
        // If it wasn't the above, we'll assume the super class did it:
        super.onActivityResult(requestCode, resultCode, data);
    }
}
