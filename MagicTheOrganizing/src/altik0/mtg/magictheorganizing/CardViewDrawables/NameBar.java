package altik0.mtg.magictheorganizing.CardViewDrawables;

import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import altik0.mtg.magictheorganizing.MtgDataTypes.*;
import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class NameBar
{
    public final static float NAME_BAR_POS_X = 4.0f;
    public final static float NAME_BAR_POS_Y = 4.0f;
    public final static float NAME_BAR_HEIGHT = 6.0f;
    public final static float NAME_BAR_WIDTH = 55.0f;
    public final static float NAME_TEXT_POS_X = 6.0f;
    public final static float NAME_TEXT_POS_Y = 7.9f;
    public final static float NAME_TEXT_FONT_SIZE = 2.7f;
    public final static float STROKE_WIDTH = 1.0f;
    public final static float NAME_BAR_RADIUS_X = 1.5f;
    public final static float NAME_BAR_RADIUS_Y = 3.0f;
    
    // Definitions for mana symbols
    public final static float MANA_SYMBOL_RADIUS = 1.65f;
    
	public static void draw(Canvas canvas, CardData card, RectF bounds,
	        int strokeColor, float scalar)
	{
        Paint nameBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        nameBarPaint.setStyle(Paint.Style.FILL);
        nameBarPaint.setColor(0xFFaaaaaa);

        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(STROKE_WIDTH * scalar);
        strokePaint.setColor(strokeColor);
	    

        RectF nameBar = new RectF(bounds);
        nameBar.left   += NAME_BAR_POS_X * scalar;
        nameBar.top    += NAME_BAR_POS_Y * scalar;
        nameBar.right   = nameBar.left + (NAME_BAR_WIDTH  * scalar);
        nameBar.bottom  = nameBar.top  + (NAME_BAR_HEIGHT * scalar);
        
		// Step 1: draw box
	    canvas.drawRoundRect(nameBar, NAME_BAR_RADIUS_X * scalar, NAME_BAR_RADIUS_Y * scalar, nameBarPaint);
        canvas.drawRoundRect(nameBar, NAME_BAR_RADIUS_X * scalar, NAME_BAR_RADIUS_Y * scalar, strokePaint);
        
	    // Step 2: draw text
        // TODO: dynamic sizing: if length of string is too long, make smaller
        // (within reason) to fit in the bar. This will likely be tricky.
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
        
	    // Step 3: draw mana symbols
        // regex split the symbols out
        // WAIT: don't do this if we don't have a cost!
        if (card.getManaCost() == null)
            return;
        Pattern regex = Pattern.compile("\\{[^}]*\\}");
        Matcher matcher = regex.matcher(card.getManaCost());
        Stack<String> manaSymbols = new Stack<String>();
        while (matcher.find())
        {
            manaSymbols.push(matcher.group());
        }
        
        // TODO: draw each symbol, starting from the right-most
        float radius = MANA_SYMBOL_RADIUS * scalar;
        RectF symbolBounds = new RectF(nameBar);
        symbolBounds.right -= scalar;
        symbolBounds.bottom -= (symbolBounds.height() - (2.0f * radius)) * 0.5f;
        symbolBounds.left = symbolBounds.right - (2.0f * radius);
        symbolBounds.top = symbolBounds.bottom - (2.0f * radius);
        //symbolBounds.inset((nameBar.width() - (2.0f * radius)) / 2.0f,
        //                   (nameBar.height() - (2.0f * radius)) / 2.0f);
                
        while (manaSymbols.size() > 0)
        {
            String symbol = manaSymbols.pop();
            ManaSymbol.draw(canvas, symbolBounds, symbol, scalar);
            symbolBounds.left -= (2.0f * radius) + (scalar / 2.0f);
            symbolBounds.right -= (2.0f * radius) + (scalar / 2.0f);
        }
	}
}
