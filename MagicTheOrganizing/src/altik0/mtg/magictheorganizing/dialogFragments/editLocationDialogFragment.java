package altik0.mtg.magictheorganizing.dialogFragments;

import altik0.mtg.magictheorganizing.R;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Location;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class EditLocationDialogFragment extends DialogFragment
{
    public static final String LOCATION_KEY = "locationName";
    
    // Used to track callback functions
    public interface EditLocationHolder
    {
        public void deleteLocation(Location l);
        public void renameLocation(Location l);
        public void addCollection(Location l);
    }
    
    private EditLocationHolder locHolder;
    public void setEditLocationHolder (EditLocationHolder _locHolder)
    {
        locHolder = _locHolder;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        if (!getArguments().containsKey(LOCATION_KEY))
            throw new IllegalArgumentException("This dialog needs a location name!");
        
        final Location l = (Location)getArguments().getSerializable(LOCATION_KEY);
        
        AlertDialog.Builder editLocDialog = new AlertDialog.Builder(getActivity());
        // TODO: use strings in resources
        editLocDialog.setTitle("Edit Location");
        final RadioGroup v = (RadioGroup)View.inflate(getActivity(),
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
                        // If our locHolder is null, we can't do anything anyway
                        if (locHolder == null)
                            return;
                        
                        // Figure out which is checked:
                        switch(v.getCheckedRadioButtonId())
                        {
                            case R.id.deleteLocationRadio:
                                locHolder.deleteLocation(l);
                                break;
                            case R.id.renameLocationRadio:
                                locHolder.renameLocation(l);
                                break;
                            case R.id.addCollectionRadio:
                            default:
                                locHolder.addCollection(l);
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
        
        return editLocDialog.create();
    }
}
