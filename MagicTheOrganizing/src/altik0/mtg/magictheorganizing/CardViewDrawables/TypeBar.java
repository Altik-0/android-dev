package altik0.mtg.magictheorganizing.CardViewDrawables;

import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

public class TypeBar
{
    public final static float TYPE_BAR_POS_X = 4.0f;
    public final static float TYPE_BAR_POS_Y = 49.0f;
    public final static float TYPE_BAR_RADIUS_X = 1.5f;
    public final static float TYPE_BAR_RADIUS_Y = 3.0f;
    public final static float TYPE_BAR_HEIGHT = 6.0f;
    public final static float TYPE_BAR_WIDTH = 55.0f;
    public final static float TYPE_TEXT_POS_X = 5.5f;
    public final static float TYPE_TEXT_POS_Y = 52.9f;
    public final static float TYPE_TEXT_WIDTH = TYPE_BAR_WIDTH - 10.0f;  // TODO: measure better
    public final static float TYPE_TEXT_FONT_SIZE = 2.7f;
    public final static float STROKE_WIDTH = 1.0f;
    
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
        nameBar.left   += TYPE_BAR_POS_X * scalar;
        nameBar.top    += TYPE_BAR_POS_Y * scalar;
        nameBar.right   = nameBar.left + (TYPE_BAR_WIDTH  * scalar);
        nameBar.bottom  = nameBar.top  + (TYPE_BAR_HEIGHT * scalar);
        
        // Step 1: draw box
        canvas.drawRoundRect(nameBar, TYPE_BAR_RADIUS_X * scalar, TYPE_BAR_RADIUS_Y * scalar, nameBarPaint);
        canvas.drawRoundRect(nameBar, TYPE_BAR_RADIUS_X * scalar, TYPE_BAR_RADIUS_Y * scalar, strokePaint);
        
        // Step 2: setup text
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(TYPE_TEXT_FONT_SIZE * scalar);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        Typeface tf = Typeface.create("Times New Roman", Typeface.BOLD);
        textPaint.setTypeface(tf);
        
        // Step 2.5: Confirm width of text. If too wide, trim and put in '...'
        String typeString = card.getTypeString();
        int charWidth = textPaint.breakText(typeString,
                                            true,
                                            TYPE_TEXT_WIDTH * scalar,
                                            null);
        if (charWidth < typeString.length())
        {
            typeString = typeString.substring(0, charWidth);
            String replaceText = "...";
            float replaceWidth = textPaint.measureText(replaceText);
            int trimChars = textPaint.breakText(typeString, false, replaceWidth, null);
            typeString = typeString.substring(0, typeString.length() - trimChars) + replaceText;
        }
        
        canvas.drawText(typeString,
                bounds.left + (TYPE_TEXT_POS_X * scalar),
                bounds.top + (TYPE_TEXT_POS_Y * scalar),
                textPaint);
    }
}
