package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.MtgDataTypes.*;
import altik0.mtg.magictheorganizing.views.CardView;
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

//		ArrayList<CardView> cardViews = new ArrayList<CardView>();
//		
//
//		// Colorless: artifact, land, eldrazi
//		CardData card = new CardData();
//		card.addType(CardType.Land);
//		card.setName("Land");
//		CardView cv4 = new CardView(this, card);
//		cardViews.add(cv4);
//		
//		card = new CardData();
//		card.addType(CardType.Artifact);
//		card.setName("Artifact");
//		CardView cv5 = new CardView(this, card);
//		cardViews.add(cv5);
//		
//		card = new CardData();
//		card.addType(CardType.Creature);
//		card.setName("Eldrazi");
//		card.setPower(10);
//		card.setPower(10);
//		CardView cv6 = new CardView(this, card);
//		cardViews.add(cv6);
//		
//		for (CardColor color : CardColor.values())
//		{
//			// Mono colored, creature
//			card = new CardData();
//			card.addColor(color);
//			card.addType(CardType.Creature);
//			card.setPower(0);
//			card.setPower(1);
//			card.setName(color.toString());
//			CardView cv0 = new CardView(this, card);
//			cardViews.add(cv0);
//			
//			// Mono colored, artifact
//			card = new CardData();
//			card.addColor(color);
//			card.addType(CardType.Artifact);
//			card.setName("Artifact " + color.toString());
//			CardView cv1 = new CardView(this, card);
//			cardViews.add(cv1);
//			
//			// 2-color variants
//			for (CardColor color2 : CardColor.values())
//			{
//				if (color2 == color)
//					continue;
//				// Creature
//				card = new CardData();
//				card.addColor(color);
//				card.addColor(color2);
//				card.setPower(0);
//				card.setToughness(1);
//				card.addType(CardType.Creature);
//				card.setName(color.toString());
//				CardView cv9 = new CardView(this, card);
//				cardViews.add(cv9);
//				
//				// Artifact
//				card = new CardData();
//				card.addColor(color);
//				card.addColor(color2);
//				card.addType(CardType.Artifact);
//				card.setName("Artifact " + color.toString());
//				CardView cv8 = new CardView(this, card);
//				cardViews.add(cv8);
//			}
//		}
//		
//		LinearLayout layout = new LinearLayout(this);
//		LinearLayout row1 = new LinearLayout(this);
//		LinearLayout row2 = new LinearLayout(this);
//		LinearLayout row3 = new LinearLayout(this);
//		LinearLayout row4 = new LinearLayout(this);
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//																		 LinearLayout.LayoutParams.WRAP_CONTENT);
//		params.weight = 1.0f;
//		
//		for (int i = 0; i < cardViews.size(); i++)
//		{
//			CardView cv = cardViews.get(i);
//			cv.setLayoutParams(params);
//
//			switch (i % 4)
//			{
//			case 0:
//				row1.addView(cv);
//				break;
//			case 1:
//				row2.addView(cv);
//				break;
//			case 2:
//				row3.addView(cv);
//				break;
//			default:
//				row4.addView(cv);
//				break;
//			}
//		}
//		row1.setLayoutParams(params);
//		row2.setLayoutParams(params);
//		row3.setLayoutParams(params);
//		row4.setLayoutParams(params);
//		layout.addView(row1);
//		layout.addView(row2);
//		layout.addView(row3);
//		layout.addView(row4);
//		layout.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout layout = new LinearLayout(this);
		CardData card = new CardData();
		card.setName("Cruel Ultimatum");
		card.setPower(99);
		card.setToughness(99);
		card.addColor(CardColor.Green);
		card.addType(CardType.Creature);
		card.addSubtype("Human");
		card.addSubtype("Soldier");
		card.setManaCost("{U/W}{2/G}{B/R}{B/W}{P/B}{S}{2}");
		
		
		CardView cv = new CardView(this, card);
		cv.setPadding(10, 10, 10, 10);
		layout.addView(cv);
		setContentView(layout);
	}
}
