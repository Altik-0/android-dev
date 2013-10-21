package altik0.mtg.magictheorganizing.CardViewDrawables;

import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class ArtBox
{
    public final static float ART_BOX_POS_X = 5.0f;
    public final static float ART_BOX_POS_Y = 10.0f;
    public final static float ART_BOX_HEIGHT = 41.0f;
    public final static float ART_BOX_WIDTH = 53.0f;
    public final static float STROKE_WIDTH = 1.0f;
    
	public static void draw(Canvas canvas, CardData card, RectF bounds,
	        int strokeColor, float scalar)
	{
	    Paint artBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        artBoxPaint.setStyle(Paint.Style.FILL);
        artBoxPaint.setColor(0xFF000000);
        
        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(STROKE_WIDTH * scalar);
        strokePaint.setColor(strokeColor);
        
        RectF artBox = new RectF(bounds);
        artBox.left   += ART_BOX_POS_X * scalar;
        artBox.top    += ART_BOX_POS_Y * scalar;
        artBox.right   = artBox.left + (ART_BOX_WIDTH * scalar);
        artBox.bottom  = artBox.top  + (ART_BOX_HEIGHT * scalar);
        canvas.drawRect(artBox, artBoxPaint);
        canvas.drawRect(artBox, strokePaint);
	}
}
