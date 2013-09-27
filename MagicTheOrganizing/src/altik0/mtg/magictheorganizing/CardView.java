package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

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
	public final float CARD_WIDTH  = 63.0f;
	public final float CARD_HEIGHT = 88.0f;
	public final float BORDER_WIDTH = 3.0f;
	public final float CORNER_RADIUS = 2.0f;
	public final float NAME_BAR_POS_X = 4.0f;
	public final float NAME_BAR_POS_Y = 4.0f;
	public final float NAME_BAR_RADIUS_X = 1.5f;
	public final float NAME_BAR_RADIUS_Y = 3.0f;
	public final float NAME_BAR_HEIGHT = 6.0f;
	public final float NAME_BAR_WIDTH = 55.0f;
	public final float NAME_TEXT_POS_X = 6.0f;
	public final float NAME_TEXT_POS_Y = 7.9f;
	public final float NAME_TEXT_FONT_SIZE = 2.7f;
	public final float TYPE_BAR_POS_X = 4.0f;
	public final float TYPE_BAR_POS_Y = 49.0f;
	public final float TYPE_BAR_RADIUS_X = 1.5f;
	public final float TYPE_BAR_RADIUS_Y = 3.0f;
	public final float TYPE_BAR_HEIGHT = 6.0f;
	public final float TYPE_BAR_WIDTH = 55.0f;
	public final float TYPE_TEXT_POS_X = 5.5f;
	public final float TYPE_TEXT_POS_Y = 52.9f;
	public final float TYPE_TEXT_FONT_SIZE = 2.7f;
	public final float TEXT_BOX_POS_X = 5.0f;
	public final float TEXT_BOX_POS_Y = 55.0f;
	public final float TEXT_BOX_HEIGHT = 24.0f;
	public final float TEXT_BOX_WIDTH = 53.0f;
	public final float ART_BOX_POS_X = 5.0f;
	public final float ART_BOX_POS_Y = 10.0f;
	public final float ART_BOX_HEIGHT = 41.0f;
	public final float ART_BOX_WIDTH = 53.0f;
	public final float PT_BOX_POS_X = 46.0f;
	public final float PT_BOX_POS_Y = 77.0f;
	public final float PT_BOX_HEIGHT = 6.0f;
	public final float PT_BOX_WIDTH = 13.0f;
	public final float PT_BOX_RADIUS_X = 1.5f;
	public final float PT_BOX_RADIUS_Y = 3.0f;
	public final float PT_TEXT_FONT_SIZE = 4.3f;
	public final float PT_TEXT_POS_X = 52.5f;
	public final float PT_TEXT_POS_Y = 81.5f;
	public final float STROKE_WIDTH = 1.0f;
	
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
		Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		strokePaint.setStyle(Paint.Style.STROKE);
		strokePaint.setStrokeWidth(STROKE_WIDTH * scalar);
		strokePaint.setColor(strokeColor);
		
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
		Paint artBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		artBoxPaint.setStyle(Paint.Style.FILL);
		artBoxPaint.setColor(0xFF000000);
		
		RectF artBox = new RectF(bounds);
		artBox.left   += ART_BOX_POS_X * scalar;
		artBox.top    += ART_BOX_POS_Y * scalar;
		artBox.right   = artBox.left + (ART_BOX_WIDTH * scalar);
		artBox.bottom  = artBox.top  + (ART_BOX_HEIGHT * scalar);
		canvas.drawRect(artBox, artBoxPaint);
		canvas.drawRect(artBox, strokePaint);
		
		Paint textBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textBoxPaint.setStyle(Paint.Style.FILL);
		textBoxPaint.setColor(0xFFcccccc);
		
		RectF textBox = new RectF(bounds);
		textBox.left   += TEXT_BOX_POS_X * scalar;
		textBox.top    += TEXT_BOX_POS_Y * scalar;
		textBox.right   = textBox.left + (TEXT_BOX_WIDTH * scalar);
		textBox.bottom  = textBox.top  + (TEXT_BOX_HEIGHT * scalar);
		canvas.drawRect(textBox, textBoxPaint);
		canvas.drawRect(textBox, strokePaint);
		
		// Draw type and name bars
		Paint nameBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		nameBarPaint.setStyle(Paint.Style.FILL);
		nameBarPaint.setColor(0xFFaaaaaa);
		
		RectF nameBar = new RectF(bounds);
		nameBar.left   += NAME_BAR_POS_X * scalar;
		nameBar.top    += NAME_BAR_POS_Y * scalar;
		nameBar.right   = nameBar.left + (NAME_BAR_WIDTH  * scalar);
		nameBar.bottom  = nameBar.top  + (NAME_BAR_HEIGHT * scalar);
		canvas.drawRoundRect(nameBar, NAME_BAR_RADIUS_X * scalar, NAME_BAR_RADIUS_Y * scalar, nameBarPaint);
		canvas.drawRoundRect(nameBar, NAME_BAR_RADIUS_X * scalar, NAME_BAR_RADIUS_Y * scalar, strokePaint);
				
		Paint typeBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		typeBarPaint.setStyle(Paint.Style.FILL);
		typeBarPaint.setColor(0xFFaaaaaa);
		
		RectF typeBar = new RectF(bounds);
		typeBar.left   += TYPE_BAR_POS_X * scalar;
		typeBar.top    += TYPE_BAR_POS_Y * scalar;
		typeBar.right   = typeBar.left + (TYPE_BAR_WIDTH * scalar);
		typeBar.bottom  = typeBar.top  + (TYPE_BAR_HEIGHT * scalar);
		canvas.drawRoundRect(typeBar, TYPE_BAR_RADIUS_X * scalar, TYPE_BAR_RADIUS_Y * scalar, typeBarPaint);
		canvas.drawRoundRect(typeBar, TYPE_BAR_RADIUS_X * scalar, TYPE_BAR_RADIUS_Y * scalar, strokePaint);
			
		// Next up: draw the text elements
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(NAME_TEXT_FONT_SIZE * scalar);
		textPaint.setColor(Color.BLACK);
		textPaint.setStyle(Paint.Style.FILL);
		Typeface tf = Typeface.create("Times New Roman", Typeface.BOLD);
		textPaint.setTypeface(tf);
		
		canvas.drawText(card.getName(),
						bounds.left + (NAME_TEXT_POS_X * scalar),
						bounds.top  + (NAME_TEXT_POS_Y * scalar),
						textPaint);
		
		textPaint.setTextSize(TYPE_TEXT_FONT_SIZE * scalar);
		canvas.drawText(card.getTypeString(),
						bounds.left + (TYPE_TEXT_POS_X * scalar),
						bounds.top + (TYPE_TEXT_POS_Y * scalar),
						textPaint);

		// Draw PT only if a creature
		if (card.getTypes().contains(CardType.Creature))
		{
			RectF ptBar = new RectF(bounds);
			ptBar.left   += PT_BOX_POS_X * scalar;
			ptBar.top    += PT_BOX_POS_Y * scalar;
			ptBar.right   = ptBar.left + (PT_BOX_WIDTH  * scalar);
			ptBar.bottom  = ptBar.top  + (PT_BOX_HEIGHT * scalar);
			canvas.drawRoundRect(ptBar, PT_BOX_RADIUS_X * scalar, PT_BOX_RADIUS_Y * scalar, typeBarPaint);
			canvas.drawRoundRect(ptBar, PT_BOX_RADIUS_X * scalar, PT_BOX_RADIUS_Y * scalar, strokePaint);
			
			textPaint.setTextSize(PT_TEXT_FONT_SIZE * scalar);
			textPaint.setTextAlign(Align.CENTER);
			canvas.drawText(card.getPowerToughnessString(),
							bounds.left + (PT_TEXT_POS_X * scalar),
							bounds.top  + (PT_TEXT_POS_Y * scalar),
							textPaint);
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
