package altik0.mtg.magictheorganizing;

import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager.DatabaseListener;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.Database.SearchParams;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
public class CardListFragment extends ListFragment implements DatabaseListener
{
    
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final int ADD_CARD_REQUEST_CODE = 0x1337;
    
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
    private boolean allowAdd = false;
    
    // Button we'll add/remove depending on allowAdd status
    private Button addButton;
    
    private OnClickListener addCardListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // Call AdvancedSearchActivity with intent of getting a card back
            Intent getCardIntent = new Intent(getActivity(), AdvancedSearchActivity.class);
            startActivityForResult(getCardIntent, ADD_CARD_REQUEST_CODE);
        }  
    };
    
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
        public void onItemSelected(CardData card);
    }
    
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks()
    {
        @Override
        public void onItemSelected(CardData card)
        {
        }
    };
    
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
        
        // Setup list adapter:
        params = new SearchParams();
        listAdapter = new CardListAdapter(getActivity(), params);
        setListAdapter(listAdapter);
        
        // Assign ourselves as a listener to the database:
        MtgDatabaseManager.RegisterListener(this);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.fragment_card_list,
                                                  container,
                                                  false);
        addButton = (Button)v.findViewById(R.id.addCard);
        addButton.setOnClickListener(addCardListener);
        if (!allowAdd)
            v.removeView(addButton);
        
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
        if (requestCode == ADD_CARD_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                CardData card = (CardData)data.getSerializableExtra
                                                (AdvancedSearchActivity.CARD_RETURN_KEY);
                listAdapter.addCard(card);
            }
        }
        else
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
        mCallbacks.onItemSelected(card);
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
    
    @Override
    public void onDestroy()
    {
        // be certain we are unregistered before we are supposed to be destroyed
        MtgDatabaseManager.UnregisterListener(this);
        
        super.onDestroy();
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
    
    public void setSearchParams(SearchParams _params)
    {
        params = _params;
        listAdapter.setSearchParams(_params);
    }
    
    public void setCollectionMode(boolean _doesAllowAdd)
    {
        // Only want to remove/add if we actually change, to avoid errors and whatnot
        if (allowAdd != _doesAllowAdd)
        {
            allowAdd = _doesAllowAdd;
            if (allowAdd)
                ((ViewGroup)getView()).addView(addButton);
            else
                ((ViewGroup)getView()).removeView(addButton);
        }
        //listAdapter.setSearchParams(params);
    }

    @Override
    public void refresh()
    {
        listAdapter.refresh();
    }
}
