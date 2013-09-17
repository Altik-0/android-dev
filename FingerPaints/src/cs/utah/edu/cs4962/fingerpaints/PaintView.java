package cs.utah.edu.cs4962.fingerpaints;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class PaintView extends View
{
	private int color;

	public PaintView(Context context, int _color)
	{
		super(context);

		color = _color;
	}

	public void SetColor(int _color)
	{
		color = _color;
		invalidate();
	}
	
	public int GetColor()
	{
		return color;
	}
	
	public void onDraw(Canvas canvas)
	{
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		
		float centerX = getWidth() / 2.0f;
		float centerY = getHeight() / 2.0f;
		float radius = (getWidth() > getHeight()) ? getHeight() / 2.0f : getWidth() / 2.0f;
		
		canvas.drawCircle(centerX, centerY, radius, paint);
	}
}
