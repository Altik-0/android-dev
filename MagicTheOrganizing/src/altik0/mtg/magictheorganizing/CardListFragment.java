package altik0.mtg.magictheorganizing;

import altik0.mtg.magictheorganizing.Database.SearchParams;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * A list fragment representing a list of Cards. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link CardDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class CardListFragment extends ListFragment
{
    public static final String SEARCH_PARAMS_KEY = "Search";
    public static final String COLLECTION_ID_KEY = "CollectionId";
    public static final String COLLECTION_MODE = "CollectionMode";
    public static final int SEARCH_FOR_CARD_CODE = 0x666;
    
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;
    
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    
    // Used to determine which cards should be obtained from the database
    private SearchParams params;
    private CardListAdapter listAdapter;
    
    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks
    {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(int card_id);
    }
    
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks()
    {
        @Override
        public void onItemSelected(int card_id)
        {
        }
    };
    
    private OnClickListener addCardListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            Intent searchToAddIntent = AdvancedSearchActivity.buildAddIntent(getActivity());
            startActivityForResult(searchToAddIntent, SEARCH_FOR_CARD_CODE);
        }
    };
    
    // If this fragment is being used in COLLECTION_MODE, this will not be null,
    // and has the ID for the collection we're displaying.
    private Integer collectionId = null;
    
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CardListFragment()
    {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        // Check if we were given a collectionId (meaning we're searching a collection,
        // rather than all cards)
        if (getArguments() != null &&
            getArguments().containsKey(COLLECTION_ID_KEY))
            collectionId = getArguments().getInt(COLLECTION_ID_KEY);
        
        params = new SearchParams();
        listAdapter = new CardListAdapter(getActivity(), params, collectionId);
        setListAdapter(listAdapter);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        LinearLayout v = (LinearLayout)inflater.inflate(R.layout.fragment_card_list, container, false);
        Button addCard = (Button)v.findViewById(R.id.addCard);
        addCard.setOnClickListener(addCardListener);
        
        // Remove the button if we are not in a collection:
        if (getArguments() != null &&
            getArguments().containsKey(COLLECTION_MODE) &&
            getArguments().getBoolean(COLLECTION_MODE))
            v.removeView(addCard);
        
        return v;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION))
        {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
    }
    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        
        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks))
        {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }
        
        mCallbacks = (Callbacks) activity;
    }
    
    @Override
    public void onDetach()
    {
        super.onDetach();
        
        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == SEARCH_FOR_CARD_CODE)
        {
            // If the resultCode was cancel, guess we don't do anything. :(
            if (resultCode == Activity.RESULT_CANCELED)
                return;
            
            // One more sanity check: the data needs to actually be in the intent,
            // or we still can't do anything. D:
            if (data.hasExtra(AdvancedSearchActivity.RETURNED_CARD_KEY))
            {
                CardData card = (CardData)data.getSerializableExtra(AdvancedSearchActivity.RETURNED_CARD_KEY);
                listAdapter.addCardToCollection(collectionId, card);
            }
        }
        
        // Anything else and we didn't make the request - assume the super class did it
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public void onListItemClick(ListView listView, View view, int position,
            long id)
    {
        super.onListItemClick(listView, view, position, id);
        
        // TODO: figure out how to do this?
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        CardData card = (CardData)listAdapter.getItem(position);
        mCallbacks.onItemSelected(card.getCardId());
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION)
        {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }
    
    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick)
    {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }
    
    private void setActivatedPosition(int position)
    {
        if (position == ListView.INVALID_POSITION)
        {
            getListView().setItemChecked(mActivatedPosition, false);
        } else
        {
            getListView().setItemChecked(position, true);
        }
        
        mActivatedPosition = position;
    }
}
