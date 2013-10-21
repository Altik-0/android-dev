package altik0.mtg.magictheorganizing.views;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.CardViewDrawables.ArtBox;
import altik0.mtg.magictheorganizing.CardViewDrawables.NameBar;
import altik0.mtg.magictheorganizing.CardViewDrawables.PowerToughnessBar;
import altik0.mtg.magictheorganizing.CardViewDrawables.TextBox;
import altik0.mtg.magictheorganizing.CardViewDrawables.TypeBar;
import altik0.mtg.magictheorganizing.MtgDataTypes.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.View;

// Represents a generic card.
//
// Depending on various fields, draws different modes. Would use subclasses, except
// the ultimate objective is to allow these views to be recyclable in a ListView style
// layout.
public class CardView extends View
{	
	// TODO: these may or may not get used long term, just writing them out for potential future use.
//	public enum FrameFormat
//	{
//		// Standard Frames
//		OriginalFrame, EighthEditionFrame,
//		// Full Art Basics
//		UngluedLand, UnhingedLand, ZendikarLand,
//		// Atypical
//		PlanarShift, FutureShift, FutureshiftFullart,
//		Miracle, NyxEnchantment, Eldrazi, FlipCard,
//		DoubleSided, 
//	}
	
	// Dimensions used for maintaining aspect ratio and other dimensions
	public final static float CARD_WIDTH  = 63.0f;
	public final static float CARD_HEIGHT = 88.0f;
	public final static float BORDER_WIDTH = 3.0f;
	public final static float CORNER_RADIUS = 2.0f;
	
	private CardData card;
	
	// Constructor that assumes you are using a creature
	public CardView(Context context, CardData _card)
	{
		super(context);
		card = new CardData(_card);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// Setup bounds rectangle based on current size
		RectF bounds = new RectF();
		bounds.left = getPaddingLeft();
		bounds.top = getPaddingTop();
		bounds.right = getWidth() - getPaddingRight();
		bounds.bottom = getHeight() - getPaddingBottom();
		float aspectRatio = (float)bounds.width() / (float)bounds.height();
		float expectedAspectRatio = CARD_WIDTH / CARD_HEIGHT;
		
		float scalar;
		
		// If too wide:
		if (aspectRatio > expectedAspectRatio)
		{
			scalar = (float)bounds.height() / CARD_HEIGHT;
			
			// Pad width
			float widthPadding = (bounds.width() - (CARD_WIDTH * scalar)) / 2.0f;
			bounds.left += widthPadding;
			bounds.right -= widthPadding;
		}
		// If too tall:
		else
		{
			scalar = (float)bounds.width() / CARD_WIDTH;
			
			// Pad width
			float heightPadding = (bounds.height() - (CARD_HEIGHT * scalar)) / 2.0f;
			bounds.top += heightPadding;
			bounds.bottom -= heightPadding;
		}

		int strokeColor = getOutlineColor();

		// First objective: draw black border		
		Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		borderPaint.setColor(Color.BLACK);
		borderPaint.setStyle(Paint.Style.FILL);
		canvas.drawRoundRect(bounds, CORNER_RADIUS * scalar, CORNER_RADIUS * scalar, borderPaint);
		
		// Second objective: draw card's color bg
		Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setStyle(Paint.Style.FILL);
		backgroundPaint.setColor(getBackgroundColor());		
		
		RectF backgroundBounds = new RectF(bounds);
		backgroundBounds.inset(BORDER_WIDTH * scalar, BORDER_WIDTH * scalar);
		
		canvas.drawRect(backgroundBounds, backgroundPaint);
		
		// Draw text box and picture box
		ArtBox.draw(canvas, card, bounds, strokeColor, scalar);
		TextBox.draw(canvas, card, bounds, strokeColor, scalar);
		
		// Draw type and name bars
		NameBar.draw(canvas, card, bounds, strokeColor, scalar);
		TypeBar.draw(canvas, card, bounds, strokeColor, scalar);

		// Draw PT only if a creature
		if (card.getTypes().contains(CardType.Creature))
		{
			PowerToughnessBar.draw(canvas, card, bounds, strokeColor, scalar);
		}
		
		Log.i("M:TG card type", card.getTypeString());
	}
	
	// Used to determine the frame's color based on card type, color, etc.
	private int getBackgroundColor()
	{
		// Land always brown:
		if (card.getTypes().contains(CardType.Land))
			return 0xFF662a0e;
		// Artifacts always grey:
		else if (card.getTypes().contains(CardType.Artifact))
			return Color.GRAY;
		// Eldrazi / other colorless options
		else if (card.getColors().size() == 0)
		{
			return Color.DKGRAY;	// ??? Eldrazi and friends are just going to be dark grey for now
		}
		// Single color options:
		else if (card.getColors().size() == 1)
		{
			switch(card.getColors().get(0))
			{
			case White:
				return Color.WHITE;
			case Blue:
				return 0xFF000088;
			case Black:
				return 0xFF333333;
			case Red:
				return 0xFF880000;
			case Green:
			default:
				return 0xFF008800;
			}
		}
		// Multicolor options:
		// For now, just gold
		else
			return 0xFFE0C100;
	}
	
	// Used to determine outline card.getColors() based on card type, color, etc.
	private int getOutlineColor()
	{
		// Land always brown:
		if (card.getTypes().contains(CardType.Land))
			return 0xFF421B09;			// Dark brown
		// Colorless options, based on type:
		else if (card.getColors().size() == 0)
		{
			if (card.getTypes().contains(CardType.Artifact))
				return 0xFFbbbbbb;
			else
				return Color.GRAY;		// ??? Eldrazi and friends are just going to be dark grey for now
		}
		// Single color options:
		if (card.getColors().size() == 1)
		{
			// If a colored artifact, do a greyed tint color:
			if (card.getTypes().contains(CardType.Artifact))
			{
				switch(card.getColors().get(0))
				{
				case White:
					return 0xFFdddddd;
				case Blue:
					return 0xFF6D6DA0;
				case Black:
					return 0xFF707070;
				case Red:
					return 0xFFA06D6D;
				case Green:
				default:
					return 0xFF6DA06D;
				}
			}
			else
			{
				switch(card.getColors().get(0))
				{
				case White:
					return 0xFFdddddd;
				case Blue:
					return 0xFF000044;
				case Black:
					return 0xFF111111;
				case Red:
					return 0xFF660000;
				case Green:
				default:
					return 0xFF006600;
				}
			}
		}
		// Multicolor options:
		// For now, just gold
		else
			return 0xFFC78F00;
	}
}
