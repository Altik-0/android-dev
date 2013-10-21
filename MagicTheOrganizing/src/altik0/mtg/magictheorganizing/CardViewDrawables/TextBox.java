package altik0.mtg.magictheorganizing.CardViewDrawables;

import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class TextBox
{
    public final static float TEXT_BOX_POS_X = 5.0f;
    public final static float TEXT_BOX_POS_Y = 55.0f;
    public final static float TEXT_BOX_HEIGHT = 24.0f;
    public final static float TEXT_BOX_WIDTH = 53.0f;
    public final static float STROKE_WIDTH = 1.0f;
    public final static int TEXT_BOX_COLOR = 0xFFcccccc;
    
    public static void draw(Canvas canvas, CardData card, RectF bounds,
            int strokeColor, float scalar)
    {
        Paint textBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textBoxPaint.setStyle(Paint.Style.FILL);
        textBoxPaint.setColor(TEXT_BOX_COLOR);

        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(STROKE_WIDTH * scalar);
        strokePaint.setColor(strokeColor);

        RectF textBox = new RectF(bounds);
        textBox.left   += TEXT_BOX_POS_X * scalar;
        textBox.top    += TEXT_BOX_POS_Y * scalar;
        textBox.right   = textBox.left + (TEXT_BOX_WIDTH  * scalar);
        textBox.bottom  = textBox.top  + (TEXT_BOX_HEIGHT * scalar);
        canvas.drawRect(textBox, textBoxPaint);
        canvas.drawRect(textBox, strokePaint);
        
        // TODO: something with text? I dunno
    }
}
