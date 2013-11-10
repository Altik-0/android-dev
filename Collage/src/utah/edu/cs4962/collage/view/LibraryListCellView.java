package utah.edu.cs4962.collage.view;

import java.util.Date;

import utah.edu.cs4962.collage.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LibraryListCellView extends ViewGroup
{
    private static final int THUMB_ID = 0x666;
    private static final int WIDTH_HEIGHT_ID = 0xfa7a55;
    private static final int TIMESTAMP_ID = 0xcabb1e;
    private static final int PLUS_MINUS_ID = 0xbadcab;
    
    private Bitmap thumbnail;
    private int imgWidth;
    private int imgHeight;
    private Date timestamp;
    private Boolean isInCollage;
    
    public LibraryListCellView(Context context, Bitmap _thumbnail, int _imgWidth,
                               int _imgHeight, Date _timestamp, Boolean _isInCollage)
    {
        super(context);
        
        thumbnail = _thumbnail;
        imgWidth = _imgWidth;
        imgHeight = _imgHeight;
        timestamp = _timestamp;
        isInCollage = _isInCollage;
        
        ImageView thumbView = new ImageView(context);
        thumbView.setImageBitmap(thumbnail);
        thumbView.setId(THUMB_ID);
        
        TextView widthHeightView = new TextView(context);
        widthHeightView.setText(imgWidth + " x " + imgHeight);
        widthHeightView.setId(WIDTH_HEIGHT_ID);
        
        TextView timestampView = new TextView(context);
        timestampView.setText(DateFormat.format("MMM/dd/yyyy kk:mm:ss", timestamp));
        timestampView.setId(TIMESTAMP_ID);
        
        ToggleButton plusMinusButton = new ToggleButton(context);
        plusMinusButton.setTextOn("-");
        plusMinusButton.setTextOff("+");
        plusMinusButton.setChecked(isInCollage);
        plusMinusButton.setFocusable(false);
        plusMinusButton.setId(PLUS_MINUS_ID);

        addView(thumbView);
        addView(widthHeightView);
        addView(timestampView);
        addView(plusMinusButton);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        ImageView thumbView = (ImageView)findViewById(THUMB_ID);
        TextView widthHeightView = (TextView)findViewById(WIDTH_HEIGHT_ID);
        TextView timestampView = (TextView)findViewById(TIMESTAMP_ID);
        ToggleButton plusMinusButton = (ToggleButton)findViewById(PLUS_MINUS_ID);
        
        RectF bounds = new RectF(0, 0, getWidth(), getHeight());
        Log.i("Bounds:", bounds.left + ", " + bounds.top + ", " + bounds.right + ", " + bounds.bottom);
        float widthUnit = getWidth() / 5.0f;
        
        RectF thumbBounds = new RectF(bounds);
        thumbBounds.right = thumbBounds.left + 
                            ((thumbBounds.height() > widthUnit) ? 
                                    widthUnit : thumbBounds.height());
        
        RectF widthHeightBounds = new RectF(bounds);
        widthHeightBounds.left = thumbBounds.right;
        widthHeightBounds.right -= widthUnit;
        RectF timestampBounds = new RectF(widthHeightBounds);
        
        widthHeightBounds.bottom *= 0.3333f;
        timestampBounds.top = widthHeightBounds.bottom;
        
        RectF plusMinusBounds = new RectF(bounds);
        plusMinusBounds.left = widthHeightBounds.right;
        
        // Actually lay them childrens out!
        thumbView.layout((int)thumbBounds.left,
                         (int)thumbBounds.top,
                         (int)thumbBounds.right,
                         (int)thumbBounds.right);
        widthHeightView.layout((int)widthHeightBounds.left,
                               (int)widthHeightBounds.top,
                               (int)widthHeightBounds.right,
                               (int)widthHeightBounds.right);
        timestampView.layout((int)timestampBounds.left,
                             (int)timestampBounds.top,
                             (int)timestampBounds.right,
                             (int)timestampBounds.right);
        plusMinusButton.layout((int)plusMinusBounds.left,
                               (int)plusMinusBounds.top,
                               (int)plusMinusBounds.right,
                               (int)plusMinusBounds.bottom);
    }

    public Bitmap getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail)
    {
        this.thumbnail = thumbnail;
        ImageView thumbView = (ImageView)findViewById(THUMB_ID);
        thumbView.setImageBitmap(this.thumbnail);
    }

    public int getImgWidth()
    {
        return imgWidth;
    }

    public void setImgWidth(int width)
    {
        this.imgWidth = width;
        TextView widthHeightView = (TextView)findViewById(WIDTH_HEIGHT_ID);
        widthHeightView.setText(imgWidth + " x " + imgHeight);
    }

    public int getImgHeight()
    {
        return imgHeight;
    }

    public void setImgHeight(int height)
    {
        this.imgHeight = height;
        TextView widthHeightView = (TextView)findViewById(WIDTH_HEIGHT_ID);
        widthHeightView.setText(imgWidth + " x " + imgHeight);
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
        TextView timestampView = (TextView)findViewById(TIMESTAMP_ID);
        timestampView.setText(DateFormat.format("MMM/dd/yyyy kk:mm:ss", timestamp));
    }

    public Boolean getIsInCollage()
    {
        return isInCollage;
    }

    public void setIsInCollage(Boolean isInCollage)
    {
        this.isInCollage = isInCollage;
        ToggleButton plusMinusButton = (ToggleButton)findViewById(PLUS_MINUS_ID);
        plusMinusButton.setChecked(isInCollage);
    }
    
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = Math.max(MeasureSpec.getSize(widthMeasureSpec), 150);
        int height = Math.max(MeasureSpec.getSize(heightMeasureSpec), 150);
        
        setMeasuredDimension(width, height);
    }
}
