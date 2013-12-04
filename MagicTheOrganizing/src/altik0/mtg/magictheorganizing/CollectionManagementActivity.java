package altik0.mtg.magictheorganizing;

import java.util.ArrayList;
import java.util.HashMap;

import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;


// MASSIVE TODO:
//  Need to refactor to use a more rigorous model than a basic HashMap,
//  because in practice I need to keep track of location and collection
//  ids for proper identification in the database

public class CollectionManagementActivity extends Activity implements ListAdapter
{
    // Used to track whether we're displaying edit data or not.
    private enum ActivityState
    {
        normal, editing,
    }
    private ActivityState state = ActivityState.normal;
    
    private HashMap<String, ArrayList<String>> collectionMap;
    
    private OnClickListener editListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            if (state == ActivityState.normal)
            {
                Button addLocButton = new Button(CollectionManagementActivity.this);
                // TODO: give this button's text something from resources
                addLocButton.setText("Add Location...");
                addLocButton.setOnClickListener(addLocationListener);
                addLocationButtonBox.addView(addLocButton);
                addLocationButtonBox.setLayoutParams(
                        new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                                                  ListView.LayoutParams.WRAP_CONTENT));
                ListView collectionList = (ListView)findViewById(R.id.contentManagementList);
                collectionList.setOnItemClickListener(editSelectedListener);
                state = ActivityState.editing;
            }
            else
            {
                addLocationButtonBox.setLayoutParams(
                        new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                                                  0));
                addLocationButtonBox.removeAllViews();
                ListView collectionList = (ListView)findViewById(R.id.contentManagementList);
                collectionList.setOnItemClickListener(normalSelectedListener);
                state = ActivityState.normal;
            }
            
            ListView lv = (ListView)findViewById(R.id.contentManagementList);
            lv.invalidateViews();
        }
    };
    
    private OnClickListener addLocationListener = new OnClickListener()
    {
        private EditText locationName;
        @Override
        public void onClick(View arg0)
        {
            // Put a new location in!
            // So, that means prompting the user...
            // blech...
            locationName = new EditText(CollectionManagementActivity.this);
            AlertDialog.Builder addLocDialog = new AlertDialog.Builder(CollectionManagementActivity.this);
            // TODO: use strings in resources
            addLocDialog.setTitle("Add Location");
            addLocDialog.setView(locationName);
            addLocDialog.setMessage("Name of the location");
            addLocDialog.setPositiveButton("Add",
                    // Listener inside a listener! :o
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            MtgDatabaseManager.getInstance(
                                    CollectionManagementActivity.this).AddLocation(
                                            locationName.getText().toString());
                            
                            CollectionManagementActivity.this.refresh();
                        }
                    });
            addLocDialog.setNegativeButton("Cancel",
                    // Listener inside a listener! :o
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            // Do nothing - they're cancelling
                        }
                    });
            
            addLocDialog.show();
        }  
    };
    
    private OnItemClickListener editSelectedListener = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View clicked, int index, long id)
        {
            String content = (String)CollectionManagementActivity.this.getItem(index);
            int type = CollectionManagementActivity.this.getItemViewType(index);
            
            switch (type)
            {
                // Location:
                case 0:
                    displayEditLocationPopup(
                            (String)CollectionManagementActivity.this.getItem(index));
                    break;
                // Collection:
                case 1:
                    // Options:
                    //   - delete
                    //   - rename
                    //   - copy
                    //   - move
                    break;
                // Add Location button:
                case 2:
                default:
                    // Add location
                    break;
            }
        }
    };
    
    private OnItemClickListener normalSelectedListener = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3)
        {
            // TODO Auto-generated method stub
            
        }
    };
    
    private FrameLayout addLocationButtonBox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_management);
        
        // TODO: hook up to this button
        Button editButton = (Button)findViewById(R.id.contentManagementEditButton);
        editButton.setOnClickListener(editListener);
        
        collectionMap = MtgDatabaseManager.getInstance(this).GetLocationsWithCollections();
        ListView collectionList = (ListView)findViewById(R.id.contentManagementList);
        
        // Sortof a hack?
        // If the system is in edit mode, we want to have an add button.
        // HOWEVER: you can't add/remove footer views dynamically.
        // SO: we put in an empty frame view, which we insert the button into dynamically
        addLocationButtonBox = new FrameLayout(this);
        collectionList.addFooterView(addLocationButtonBox);
        collectionList.setAdapter(this);
        collectionList.setOnItemClickListener(normalSelectedListener);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collection_management, menu);
        return true;
    }

    @Override
    public int getCount()
    {
        // Count is equal to the number of locations (for headers)
        // plus the number of total collections
        int cnt = collectionMap.keySet().size();
        
        for (String key : collectionMap.keySet())
        {
            cnt += collectionMap.get(key).size();
        }
        
        return cnt;
    }

    @Override
    public Object getItem(int index)
    {
        // Iterate through. We may end on a header, or we may end on a
        // collection - just depends.
        int pos = index;
        for (String key : collectionMap.keySet())
        {
            if (pos == 0)
                return key;
            pos--;
            
            ArrayList<String> collections = collectionMap.get(key);
            if (pos < collections.size())
                return collections.get(pos);
            pos -= collections.size();
        }
        
        // We shan't ever get here, but because java doesn't believe me:
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemViewType(int index)
    {
        // Iterate through. We may end on a header, or we may end on a
        // collection - just depends.
        int pos = index;
        for (String key : collectionMap.keySet())
        {
            if (pos == 0)
                return 0;
            pos--;
            
            ArrayList<String> collections = collectionMap.get(key);
            if (pos < collections.size())
                return 1;
            pos -= collections.size();
        }
        
        // We should never get here, but since java doesn't believe me:
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO: nicer views
        if (convertView == null || !(convertView instanceof TextView))
            convertView = new TextView(this);
        
        TextView tv = (TextView)convertView;
        tv.setText((String)getItem(position));
        
        // Settings based on type:
        switch (getItemViewType(position))
        {
            case 0:
                tv.setBackgroundColor(0x88000000);
                tv.setTextColor(0xFFFFFFFF);
                tv.setHeight(75);
                tv.setTextSize(20.0f);
                //tv.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                break;
            case 1:
            default:
                tv.setBackgroundColor(0x00000000);
                tv.setTextColor(0xFF000000);
                tv.setHeight(150);
                tv.setTextSize(20.0f);
                //tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                break;
        }
        
        return tv;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public boolean hasStableIds()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        // TODO
        return state == ActivityState.editing;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        // TODO
        return state == ActivityState.editing;
    }
    
    private void refresh()
    {
        collectionMap = MtgDatabaseManager.getInstance(this).GetLocationsWithCollections();
        ListView lv = (ListView)findViewById(R.id.contentManagementList);
        lv.invalidateViews();
    }
    
    private void displayEditLocationPopup(final String locationName)
    {
        AlertDialog.Builder editLocDialog = new AlertDialog.Builder(CollectionManagementActivity.this);
        // TODO: use strings in resources
        editLocDialog.setTitle("Edit Location");
        final RadioGroup v = (RadioGroup)View.inflate(this,
                                                      R.layout.edit_location_popup_view,
                                                      null);
        editLocDialog.setView(v);
        editLocDialog.setPositiveButton("Select",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // Figure out which is checked:
                        switch(v.getCheckedRadioButtonId())
                        {
                            case R.id.deleteLocationRadio:
                                // TODO: prompt again, probably. For now, just fuck
                                // the user and delete their shit with no regard for
                                // their feelings of accidents
                                MtgDatabaseManager.getInstance(CollectionManagementActivity.this)
                                        .DeleteLocation(locationName);
                                CollectionManagementActivity.this.refresh();
                                break;
                            case R.id.renameLocationRadio:
                                // TODO: another popup with text box to prompt for new name
                                break;
                            case R.id.addCollectionRadio:
                            default:
                                // TODO: another popup with text box to prompt for name
                                break;
                        }
                    }
                });
        editLocDialog.setNegativeButton("Cancel",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // Do nothing - they're cancelling
                    }
                });
        
        editLocDialog.show();
    }
}
