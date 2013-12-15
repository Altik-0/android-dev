package altik0.mtg.magictheorganizing.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class PagedHorizontalScrollView extends HorizontalScrollView
{
    private LinearLayout pages;
    private LayoutParams pagesParams;
    
    public PagedHorizontalScrollView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        pages = new LinearLayout(context);
        pagesParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        pages = new LinearLayout(context);
        pages.setLayoutParams(pagesParams);
        this.addView(pages);
    }
    /*
    @Override
    public void addView(View v)
    {
        pages.addView(v);
        pagesParams.width = pages.getChildCount() * getWidth();
    }*/
}
