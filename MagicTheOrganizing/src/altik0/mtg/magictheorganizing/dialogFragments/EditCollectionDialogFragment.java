package altik0.mtg.magictheorganizing.dialogFragments;

import altik0.mtg.magictheorganizing.R;
import altik0.mtg.magictheorganizing.dialogFragments.EditLocationDialogFragment.EditLocationHolder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class EditCollectionDialogFragment extends DialogFragment
{
public static final String COLLECTION_NAME_KEY = "collectionName";
    
    // Used to track callback functions
    public interface EditCollectionHolder
    {
        public void deleteCollection(String collectionName);
        public void renameCollection(String collectionName);
        public void copyCollection(String collectionName);
        public void moveCollection(String collectionName);
    }
    
    private EditCollectionHolder locHolder;
    public void setEditCollectionHolder (EditCollectionHolder _locHolder)
    {
        locHolder = _locHolder;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        if (!getArguments().containsKey(COLLECTION_NAME_KEY))
            throw new IllegalArgumentException("This dialog needs a collection name!");
        
        final String collectionName = getArguments().getString(COLLECTION_NAME_KEY);
        
        AlertDialog.Builder editLocDialog = new AlertDialog.Builder(getActivity());
        // TODO: use strings in resources
        editLocDialog.setTitle("Edit Collection");
        final RadioGroup v = (RadioGroup)View.inflate(getActivity(),
                                                      R.layout.edit_collection_popup_view,
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
                            case R.id.deleteCollectionRadio:
                                locHolder.deleteCollection(collectionName);
                                break;
                            case R.id.renameCollectionRadio:
                                locHolder.renameCollection(collectionName);
                                break;
                            case R.id.copyCollectionRadio:
                                locHolder.copyCollection(collectionName);
                                break;
                            case R.id.moveCollectionRadio:
                            default:
                                locHolder.moveCollection(collectionName);
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
