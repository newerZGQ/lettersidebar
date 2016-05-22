package com.spinnedlistview.zgq.lettersidebar.letterview;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.spinnedlistview.zgq.lettersidebar.R;

/**
 * Created by 37902 on 2016/5/22.
 */
public class SlipLetterView extends RelativeLayout {
    private Context context;
    private LetterView letterView;
    RelativeLayout.LayoutParams params;
    public SlipLetterView(Context context) {
        this(context,null);
    }

    public SlipLetterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlipLetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    public void initLetterView(String text,int letterSize,int letterColor,int shapeColor){
        letterView = new LetterView(context);
        letterView.setLetter(text);
        letterView.setLetterColor(letterColor);
        letterView.setShapeColor(shapeColor);
        letterView.setLetterSize(letterSize);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200,200);
        this.addView(letterView,params);
    }
    public void transLetterView(int y){
        params = (RelativeLayout.LayoutParams)letterView.getLayoutParams();
        params.topMargin = y;
        letterView.setLayoutParams(params);
    }


}
