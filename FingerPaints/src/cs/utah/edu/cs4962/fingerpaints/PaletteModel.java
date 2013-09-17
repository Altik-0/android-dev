package cs.utah.edu.cs4962.fingerpaints;

import java.util.LinkedList;

/**
 * Stores the colors on the palette, their locations on the palette (in coordinates
 * from 0.0f to 1.0f), and also provides utilities for determining a color mixture.
 */
public class PaletteModel
{
    public class ColorSwatch
    {
        public Vector2 position;
        int color;

        public ColorSwatch(Vector2 _position, int _color)
        {
            position = _position;
            color = _color;
        }
    }

    // Public so I don't have to copy -- don't abuse the privilege!
    LinkedList<ColorSwatch> swatches;

    public PaletteModel()
    {
        swatches = new LinkedList<ColorSwatch>();
    }

    public void AddSwatch(int color, Vector2 position)
    {
        swatches.add(new ColorSwatch(position, color));
    }

    public static int MixColors (int color1, int color2, float ratio)
    {
        // TODO: ratio!

        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >>  8) & 0xFF;
        int b1 = (color1      ) & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >>  8) & 0xFF;
        int b2 = (color2      ) & 0xFF;

        int r = (r1 + r2) > 255 ? 255 : (r1 + r2);
        int g = (g1 + g2) > 255 ? 255 : (g1 + g2);
        int b = (b1 + b2) > 255 ? 255 : (b1 + b2);

        return 0xFF000000 & (r << 16) & (g << 8) & b;
    }

    // Below is potentially useful stuff for subtractive colors, but TBPH, I don't think that's going to happen
    /*
    public static class floatRGB
    {
        public float r;
        public float g;
        public float b;

        public floatRGB(int color)
        {
            int _r = (color >> 24) & 0xFF;
            int _g = (color >> 16) & 0xFF;
            int _b = (color      ) & 0xFF;

            r = ((float)_r) / 255.0f;
            g = ((float)_g) / 255.0f;
            b = ((float)_b) / 255.0f;
        }

        public floatRGB(floatCMYK color)
        {
            r = (1.0f - color.c) * (1.0f - color.k);
            g = (1.0f - color.m) * (1.0f - color.k);
            b = (1.0f - color.y) * (1.0f - color.k);
        }

        public static int toIntColor (floatRGB color)
        {
            int r = ((int)(color.r * 255.0f)) & 0xFF;
            int g = ((int)(color.g * 255.0f)) & 0xFF;
            int b = ((int)(color.b * 255.0f)) & 0xFF;

            return 0xFF000000 & (r << 24) & (g << 16) & b;
        }
    }

    public static class floatCMYK
    {
        public float c;
        public float m;
        public float y;
        public float k;

        public floatCMYK(float _c, float _m, float _y, float _k)
        {
            c = _c;
            m = _m;
            y = _y;
            k = _k;
        }

        public floatCMYK (floatRGB color)
        {
            k = 1.0f - Math.max(Math.max(color.r, color.g), color.b);
            c = (1.0f - color.r - k) / (1.0f - k);
            m = (1.0f - color.g - k) / (1.0f - k);
            y = (1.0f - color.b - k) / (1.0f - k);
        }

        public static floatCMYK mixColors (floatCMYK color1, floatCMYK color2, float ratio)
        {
            // TODO: ratio!

            float c = (color1.c - color2.c) < 0.0f ? 0.0f : (color1.c - color2.c);
            float m = (color1.m - color2.m) < 0.0f ? 0.0f : (color1.m - color2.m);
            float y = (color1.y - color2.y) < 0.0f ? 0.0f : (color1.y - color2.y);
            float k = (color1.k - color2.k) < 0.0f ? 0.0f : (color1.k - color2.k);

            return new floatCMYK(c, m, y, k);
        }
    }

    public static int MixColors(int color1, int color2, float ratio)
    {
        // Get CMYK colors for each:
        floatCMYK cmyk1 = new floatCMYK(new floatRGB(color1));
        floatCMYK cmyk2 = new floatCMYK(new floatRGB(color2));

        return floatRGB.toIntColor(new floatRGB(floatCMYK.mixColors(cmyk1, cmyk2, ratio)));
    }
    */
}
