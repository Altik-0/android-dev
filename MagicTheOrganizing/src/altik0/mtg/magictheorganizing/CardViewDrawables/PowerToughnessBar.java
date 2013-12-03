package altik0.mtg.magictheorganizing.CardViewDrawables;

import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;

public class PowerToughnessBar
{
    public final static float PT_BOX_POS_X = 46.0f;
    public final static float PT_BOX_POS_Y = 77.0f;
    public final static float PT_BOX_HEIGHT = 6.0f;
    public final static float PT_BOX_WIDTH = 13.0f;
    public final static float PT_BOX_RADIUS_X = 1.5f;
    public final static float PT_BOX_RADIUS_Y = 3.0f;
    public final static float PT_TEXT_FONT_SIZE = 4.3f;
    public final static float PT_TEXT_POS_X = 52.5f;
    public final static float PT_TEXT_POS_Y = 81.5f;
    public final static float STROKE_WIDTH = 1.0f;
    
    public static void draw(Canvas canvas, CardData card, RectF bounds,
            int strokeColor, float scalar)
    {
        Paint ptBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ptBarPaint.setStyle(Paint.Style.FILL);
        ptBarPaint.setColor(0xFFaaaaaa);

        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(STROKE_WIDTH * scalar);
        strokePaint.setColor(strokeColor);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(PT_TEXT_FONT_SIZE * scalar);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        Typeface tf = Typeface.create("Times New Roman", Typeface.BOLD);
        textPaint.setTypeface(tf);

        RectF ptBar = new RectF(bounds);
        ptBar.left   += PT_BOX_POS_X * scalar;
        ptBar.top    += PT_BOX_POS_Y * scalar;
        ptBar.right   = ptBar.left + (PT_BOX_WIDTH  * scalar);
        ptBar.bottom  = ptBar.top  + (PT_BOX_HEIGHT * scalar);
        canvas.drawRoundRect(ptBar, PT_BOX_RADIUS_X * scalar, PT_BOX_RADIUS_Y * scalar, ptBarPaint);
        canvas.drawRoundRect(ptBar, PT_BOX_RADIUS_X * scalar, PT_BOX_RADIUS_Y * scalar, strokePaint);
        
        float fontSize = PT_TEXT_FONT_SIZE * scalar;
        // HACK: if the string is too long, lower font size manually:
        if (card.getPowerToughnessString().length() > 5)
            fontSize = 3.3f * scalar;
        textPaint.setTextSize(fontSize);
        
        textPaint.setTextAlign(Align.CENTER);
        canvas.drawText(card.getPowerToughnessString(),
                        bounds.left + (PT_TEXT_POS_X * scalar),
                        bounds.top  + (PT_TEXT_POS_Y * scalar),
                        textPaint);
    }
}
