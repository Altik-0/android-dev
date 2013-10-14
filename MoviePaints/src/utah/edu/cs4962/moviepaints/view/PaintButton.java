package utah.edu.cs4962.moviepaints.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;

public class PaintButton extends Button
{
    private int brushColor;
    private float brushWidth;
    
    public PaintButton(Context context)
    {
        super(context);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(brushColor);
        paint.setStyle(Paint.Style.FILL);
        
        float centerX = getWidth() / 2.0f;
        float centerY = getHeight() / 2.0f;
        canvas.drawCircle(centerX, centerY, brushWidth, paint);
    }

    public int getBrushColor()
    {
        return brushColor;
    }

    public void setBrushColor(int brushColor)
    {
        this.brushColor = brushColor;
    }

    public float getBrushWidth()
    {
        return brushWidth;
    }

    public void setBrushWidth(float brushWidth)
    {
        this.brushWidth = brushWidth;
    }
    
    @Override
    public void onRestoreInstanceState(Parcelable parcel)
    {
        // Sanity check
        if (parcel instanceof Bundle)
        {
            Bundle bndl = (Bundle)parcel;
            brushColor = bndl.getInt("color");
            brushWidth = bndl.getFloat("width");
            super.onRestoreInstanceState(bndl.getParcelable("super"));
        }
    }
    
    @Override
    public Parcelable onSaveInstanceState()
    {
        Parcelable superParcel = super.onSaveInstanceState();
        Bundle toRet = new Bundle();
        toRet.putParcelable("super", superParcel);
        toRet.putInt("color", brushColor);
        toRet.putFloat("width", brushWidth);
        
        return toRet;
    }
}
