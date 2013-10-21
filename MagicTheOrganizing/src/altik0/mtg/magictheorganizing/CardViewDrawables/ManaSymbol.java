package altik0.mtg.magictheorganizing.CardViewDrawables;

import altik0.mtg.magictheorganizing.MtgDataTypes.CardColor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class ManaSymbol
{
    public static void draw(Canvas canvas, RectF bounds, String symbolType)
    {
        // Step 1: interpret symbolType
        ManaSymbolData data = interpManaType(symbolType);
        
        // Step 2: perform drawing
        float centerX = bounds.width() / 2.0f;
        float centerY = bounds.height() / 2.0f;
        float radius = bounds.width() > bounds.height() ? bounds.height() / 2.0f :
                                                          bounds.width()  / 2.0f;
        
        Paint mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setStyle(Paint.Style.FILL);
        Paint secondaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondaryPaint.setStyle(Paint.Style.FILL);
        
        switch(data.type)
        {
            case Standard:
                mainPaint.setColor(getColor(data.color1));
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                // TODO: color icon?
                break;
            case Colorless:
                mainPaint.setColor(getColor(null));
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                // TODO: write text of number
                break;
            case Hybrid:
                mainPaint.setColor(getColor(data.color1));
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                secondaryPaint.setColor(getColor(data.color2));
                // TODO: draw half circle on top of original
                break;
            case Phyrexian:
                mainPaint.setColor(getColor(data.color1));
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                // TODO: draw phyrexian symbol
                break;
            case Snow:
            default:
                mainPaint.setColor(0xFFcccccc);       // TODO: some color for snow
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                // TODO: snow symbol
                break;
        }
    }
    
    private static int getColor(CardColor color)
    {
        // TODO:
        return 0xFF000000;
    }
    
    private static ManaSymbolData interpManaType(String symbolType)
    {
        // TODO
        manaSymbolType type = manaSymbolType.Standard;
        ManaSymbolData data = new ManaSymbolData(type, CardColor.Green, CardColor.Green, 0);
        return data;
    }
    
    private static enum manaSymbolType
    {
        Standard, Colorless, Snow, Phyrexian, Hybrid
    }
    
    private static class ManaSymbolData
    {
        public manaSymbolType type;
        public CardColor color1;
        public CardColor color2;
        public int manaAmount;
        
        public ManaSymbolData(manaSymbolType _type, CardColor _color1,
                CardColor _color2, int _manaAmount)
        {
            type = _type;
            color1 = _color1;
            color2 = _color2;
            manaAmount = _manaAmount;
        }
    }
}
