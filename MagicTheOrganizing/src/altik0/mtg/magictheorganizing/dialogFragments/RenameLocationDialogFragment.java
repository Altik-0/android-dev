package altik0.mtg.magictheorganizing.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class RenameLocationDialogFragment extends DialogFragment
{
    public static final String LOCATION_NAME_KEY = "locationName";
    
    // Used to track callback functions
    public interface RenameLocationHolder
    {
        public void renameLocationWithName(String oldName, String newName);
    }
    
    private RenameLocationHolder locHolder;
    private EditText dialogTextPrompt;
    public void setRenameLocationHolder (RenameLocationHolder _locHolder)
    {
        locHolder = _locHolder;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        if (!getArguments().containsKey(LOCATION_NAME_KEY))
            throw new IllegalArgumentException("This dialog needs a location name!");
        
        final String locationName = getArguments().getString(LOCATION_NAME_KEY);
        
        dialogTextPrompt = new EditText(getActivity());
        AlertDialog.Builder addLocDialog = new AlertDialog.Builder(getActivity());
        // TODO: use strings in resources
        addLocDialog.setTitle("Rename Location");
        addLocDialog.setView(dialogTextPrompt);
        addLocDialog.setMessage("New name for the location");
        addLocDialog.setPositiveButton("Rename",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // if locHolder is null, can't do anything anyway
                        if (locHolder == null)
                            return;
                        
                        String newName = dialogTextPrompt.getText().toString();
                        locHolder.renameLocationWithName(locationName, newName);
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
        
        return addLocDialog.create();
    }
}
