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
		Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		fillPaint.setStyle(Paint.Style.FILL);
		fillPaint.setColor(color);
		Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		strokePaint.setStyle(Paint.Style.STROKE);
		strokePaint.setStrokeWidth(10.0f);
		
		if (isSelected())
			strokePaint.setColor(0xFFFFFFFF);
		else
			strokePaint.setColor(0xFF000000);
		
		float centerX = getWidth() / 2.0f;
		float centerY = getHeight() / 2.0f;
		float radius = (getWidth() > getHeight()) ? (getHeight() - 10.0f) / 2.0f :
													(getWidth() - 10.0f) / 2.0f;
		
		canvas.drawCircle(centerX, centerY, radius, fillPaint);
		canvas.drawCircle(centerX, centerY, radius, strokePaint);
	}
}
