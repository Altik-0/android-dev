package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.MtgDataTypes.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class MtgActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ArrayList<CardColor> colors = new ArrayList<CardColor>();
		colors.add(CardColor.Green);
		ArrayList<CardType> types = new ArrayList<CardType>();
		types.add(CardType.Creature);
		CardView cardView = new CardView(this, "Grizzly Bear", "GG", 2, 2, colors, types);
		cardView.setPadding(10, 200, 10, 200);
		
		LinearLayout layout = new LinearLayout(this);
		layout.addView(cardView);
		setContentView(layout);
	}
}
