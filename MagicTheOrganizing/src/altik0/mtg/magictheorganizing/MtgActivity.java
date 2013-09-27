package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.MtgDataTypes.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MtgActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ArrayList<CardColor> colors = new ArrayList<CardColor>();
		colors.add(CardColor.White);
		colors.add(CardColor.Black);
		ArrayList<CardType> types = new ArrayList<CardType>();
		types.add(CardType.Creature);
		types.add(CardType.Artifact);
		CardView cardView = new CardView(this, "Grizzly Bear", "GG", 2, 2, colors, types);
		cardView.setPadding(10, 10, 10, 10);
		
		ArrayList<CardColor> colors2 = new ArrayList<CardColor>();
		colors2.add(CardColor.Black);
		ArrayList<CardType> types2 = new ArrayList<CardType>();
		types2.add(CardType.Creature);
		CardView cardView2 = new CardView(this, "Storm Crow", "1U", 1, 2, colors2, types2);
		cardView2.setPadding(10, 10, 10, 10);
		
		ArrayList<CardColor> colors3 = new ArrayList<CardColor>();
		ArrayList<CardType> types3 = new ArrayList<CardType>();
		types3.add(CardType.Creature);
		CardView cardView3 = new CardView(this, "Elite Vanguard", "W", 2, 1, colors3, types3);
		cardView3.setPadding(10, 10, 10, 10);
		
		LinearLayout layout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
																		 LinearLayout.LayoutParams.WRAP_CONTENT);
		params.weight = 1.0f;
		
		cardView.setLayoutParams(params);
		cardView2.setLayoutParams(params);
		cardView3.setLayoutParams(params);
		
		layout.addView(cardView);
		layout.addView(cardView2);
		layout.addView(cardView3);
		setContentView(layout);
	}
}
