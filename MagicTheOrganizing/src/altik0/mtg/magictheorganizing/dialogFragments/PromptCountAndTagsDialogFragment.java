package altik0.mtg.magictheorganizing.dialogFragments;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class PromptCountAndTagsDialogFragment extends DialogFragment
{
    public static final String COUNT_KEY = "this is a count key! :D";
    public static final String TAGS_KEY = "this is a tags key... ._.";
    public static final String TITLE_KEY = "Dialog Title :o";
    public static final String MESSAGE_KEY = "ZOMFG this is a message! @__@";
    
    // Used to track callback functions
    public interface CountAndTagAccepter
    {
        public void returnValues(int cnt, ArrayList<String> tags);
    }
    
    private CountAndTagAccepter accepter;
    public void setCountAndTagAccepter (CountAndTagAccepter _accepter)
    {
        accepter = _accepter;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        int count = 0;
        ArrayList<String> tags = new ArrayList<String>();
        if (args.containsKey(COUNT_KEY))
            count = args.getInt(COUNT_KEY);
        if (args.containsKey(TAGS_KEY))
            tags = (ArrayList<String>)args.getSerializable(TAGS_KEY);
        
        AlertDialog.Builder editLocDialog = new AlertDialog.Builder(getActivity());
        
        // Only set the title if we were given one:
        if (args.containsKey(TITLE_KEY))
            editLocDialog.setTitle(args.getString(TITLE_KEY));
        if (args.containsKey(MESSAGE_KEY))
            editLocDialog.setMessage(args.getString(MESSAGE_KEY));
        
        final ViewGroup v = (ViewGroup)View.inflate(getActivity(),
                                                    R.layout.prompt_count_and_tags_view,
                                                    null);
        final EditText cntBox = (EditText)v.findViewById(R.id.countBox);
        cntBox.setText(Integer.toString(count));
        // TODO: tags
        editLocDialog.setView(v);
        
        // TODO: use strings in resources
        editLocDialog.setPositiveButton("Done",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // If our accepter is null, we can't do anything anyway
                        if (accepter == null)
                            return;
                        
                        int cnt = 0;
                        try
                        {
                            cnt = Integer.parseInt(cntBox.getText().toString());
                        }
                        // If we couldn't parse the int, just assume that it's 0
                        catch (NumberFormatException e)
                        {
                            cnt = 0;
                        }

                        // TODO: tags
                        accepter.returnValues(cnt, new ArrayList<String>());
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
