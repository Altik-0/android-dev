package altik0.mtg.magictheorganizing.dialogFragments;

import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class RenameCollectionDialogFragment extends DialogFragment
{
public static final String COLLECTION_KEY = "collectionName";
    
    // Used to track callback functions
    public interface RenameCollectionHolder
    {
        public void renameCollectionWithName(Collection c, String newName);
    }
    
    private RenameCollectionHolder locHolder;
    private EditText dialogTextPrompt;
    public void setRenameCollectionHolder (RenameCollectionHolder _locHolder)
    {
        locHolder = _locHolder;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        if (!getArguments().containsKey(COLLECTION_KEY))
            throw new IllegalArgumentException("This dialog needs a collection name!");
        
        final Collection c = (Collection)getArguments().getSerializable(COLLECTION_KEY);
        
        dialogTextPrompt = new EditText(getActivity());
        AlertDialog.Builder addLocDialog = new AlertDialog.Builder(getActivity());
        // TODO: use strings in resources
        addLocDialog.setTitle("Rename Collection");
        addLocDialog.setView(dialogTextPrompt);
        addLocDialog.setMessage("New name for the collection");
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
                        locHolder.renameCollectionWithName(c, newName);
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
