package altik0.mtg.magictheorganizing.CardViewDrawables;

import altik0.mtg.magictheorganizing.MtgDataTypes.CardColor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;

public class ManaSymbol
{
    // TODO: we will probably want to switch to icons rather than letters, long term
    private static final float MANA_SYMBOL_FONT_SIZE = 2.7f;
    private static final float HYBRID_SCALE_FACTOR = 0.5f;
    private static final float HYBRID_RELOC_X = 0.33f;
    private static final float HYBRID_RELOC_Y = 0.33f;
    
    public static void draw(Canvas canvas, RectF bounds, String symbolType, float scalar)
    {
        // Step 1: interpret symbolType
        ManaSymbolData data = interpManaType(symbolType);
        
        // Step 2: perform drawing
        float centerX = (bounds.width() / 2.0f) + bounds.left;
        float centerY = (bounds.height() / 2.0f) + bounds.top;
        float radius = bounds.width() > bounds.height() ? bounds.height() / 2.0f :
                                                          bounds.width()  / 2.0f;
        
        Paint mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setStyle(Paint.Style.FILL);
        Paint secondaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondaryPaint.setStyle(Paint.Style.FILL);
        
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(MANA_SYMBOL_FONT_SIZE * scalar);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Align.CENTER);
        Typeface tf = Typeface.create("roboto", Typeface.BOLD);
        textPaint.setTypeface(tf);
        
        // Text box origins
        float textOrigin1X = bounds.left + (bounds.width() * 0.5f);
        float textOrigin1Y = bounds.bottom - 
                            ((bounds.height() + textPaint.ascent() + textPaint.descent()) * 0.5f);
        float textOrigin2X = bounds.left + (bounds.width() * 0.5f);
        float textOrigin2Y = bounds.bottom - 
                            ((bounds.height() + textPaint.ascent() + textPaint.descent()) * 0.5f);
        
        switch(data.type)
        {
            case Standard:
                mainPaint.setColor(getColor(data.color1));
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                
                // Draw text
                canvas.drawText(data.text1, textOrigin1X, textOrigin1Y, textPaint);
                break;
            case Colorless:
                mainPaint.setColor(getColor(null));
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                
                // Draw text
                canvas.drawText(data.text1, textOrigin1X, textOrigin1Y, textPaint);
                break;
            case Hybrid:
                mainPaint.setColor(getColor(data.color1));
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                secondaryPaint.setColor(getColor(data.color2));
                RectF circle = new RectF(centerX - radius, centerY - radius,
                                         centerX + radius, centerY + radius);
                canvas.drawArc(circle, 315.0f, 180.0f, true, secondaryPaint);
                
                // Adjust font size for smaller symbols
                textPaint.setTextSize(MANA_SYMBOL_FONT_SIZE * scalar * HYBRID_SCALE_FACTOR);
                
                // Fix origins for hybrid symbols
                textOrigin1X = bounds.left + (bounds.width() * 0.25f);
                textOrigin1Y = bounds.bottom - (bounds.height() * 0.25f) -
                               ((bounds.height() * 0.5f) +
                                textPaint.ascent() + textPaint.descent());
                textOrigin2X = bounds.left + (bounds.width() * 0.75f);
                textOrigin2Y = bounds.bottom -
                               ((bounds.height() * 0.5f) +
                                textPaint.ascent() + textPaint.descent());
                
                canvas.drawText(data.text1, textOrigin1X, textOrigin1Y, textPaint);
                canvas.drawText(data.text2, textOrigin2X, textOrigin2Y, textPaint);
                break;
            case Phyrexian:
                mainPaint.setColor(getColor(data.color1));
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                
                // Draw text
                canvas.drawText(data.text1, textOrigin1X, textOrigin1Y, textPaint);
                break;
            case Snow:
            default:
                mainPaint.setColor(0xFFffffff);       // TODO: some color for snow
                canvas.drawCircle(centerX, centerY, radius, mainPaint);
                
                // Draw text
                canvas.drawText(data.text1, textOrigin1X, textOrigin1Y, textPaint);
                break;
        }
    }
    
    private static int getColor(CardColor color)
    {
        if (color == null)
            return 0xFFcccccc;
        
        switch (color)
        {
            case White:
                return 0xFFffffaa;
            case Blue:
                return 0xFFaaaaff;
            case Black:
                return 0xFFbbbbbb;
            case Red:
                return 0xFFffaaaa;
            case Green:
            default:
                return 0xFFaaffaa;
        }
    }
    
    private static ManaSymbolData interpManaType(String symbolType)
    {
        // Trim braces
        symbolType = symbolType.substring(1, symbolType.length()-1);
        
        // Check type:
        if (symbolType.length() == 1)
        {
            manaSymbolType type = manaSymbolType.Standard;
            ManaSymbolData data = null;
            switch(symbolType.charAt(0))
            {
                case 'W':
                    data = new ManaSymbolData(type, CardColor.White, CardColor.White, "W", "");
                    break;
                case 'U':
                    data = new ManaSymbolData(type, CardColor.Blue, CardColor.Blue, "U", "");
                    break;
                case 'B':
                    data = new ManaSymbolData(type, CardColor.Black, CardColor.Black, "B", "");
                    break;
                case 'R':
                    data = new ManaSymbolData(type, CardColor.Red, CardColor.Red, "R", "");
                    break;
                case 'G':
                    data = new ManaSymbolData(type, CardColor.Green, CardColor.Green, "G", "");
                    break;
                case 'S':
                    type = manaSymbolType.Snow;
                    data = new ManaSymbolData(type, null, null, "S", "");
                    break;
                default:
                    type = manaSymbolType.Colorless;
                    data = new ManaSymbolData(type, null, null, symbolType, "");
                    break;
            }
            return data;
        }
        // Hybrid / Phyrexian case
        else if (symbolType.contains("/"))
        {
            manaSymbolType type = manaSymbolType.Hybrid;
            String[] symbols = symbolType.split("/");
            String symbol1 = symbols[0], symbol2 = symbols[1];
            CardColor color1 = null;
            CardColor color2 = null;
            String[] types = symbolType.split("/");
            String text1 = "";
            String text2 = "";
            switch(types[1].charAt(0))
            {
                case 'W':
                    color2 = CardColor.White;
                    text2 = "W";
                    break;
                case 'U':
                    color2 = CardColor.Blue;
                    text2 = "U";
                    break;
                case 'B':
                    color2 = CardColor.Black;
                    text2 = "B";
                    break;
                case 'R':
                    color2 = CardColor.Red;
                    text2 = "R";
                    break;
                case 'G':
                    color2 = CardColor.Green;
                    text2 = "G";
                    break;
                // Presumably this will not happen
                default:
                    color2 = null;
                    text2 = symbol2;
                    break;
            }
            switch(types[0].charAt(0))
            {
                case 'W':
                    color1 = CardColor.White;
                    text1 = "W";
                    break;
                case 'U':
                    color1 = CardColor.Blue;
                    text1 = "U";
                    break;
                case 'B':
                    color1 = CardColor.Black;
                    text1 = "B";
                    break;
                case 'R':
                    color1 = CardColor.Red;
                    text1 = "R";
                    break;
                case 'G':
                    color1 = CardColor.Green;
                    text1 = "G";
                    break;
                case 'P':
                    type = manaSymbolType.Phyrexian;
                    color1 = color2;
                    text1 = "\u03d5";
                    break;
                default:
                    color1 = null;
                    text1 = symbol1;
                    break;
            }
            return new ManaSymbolData(type, color1, color2, text1, text2);
        }
        // Otherwise, we'll just assume it's a colorless number
        else
        {
            return new ManaSymbolData(manaSymbolType.Colorless, null, null, symbolType, "");
        }
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
        // Used to store symbol to write for mana type - number for colorless,
        // WUBRG for color, S for snow, etc. Two values to handle hybrid
        public String text1;
        public String text2;
        
        public ManaSymbolData(manaSymbolType _type, CardColor _color1,
                CardColor _color2, String _text1, String _text2)
        {
            type = _type;
            color1 = _color1;
            color2 = _color2;
            text1 = _text1;
            text2 = _text2;
        }
    }
}
