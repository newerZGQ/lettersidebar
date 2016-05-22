package com.spinnedlistview.zgq.lettersidebar.letterview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.spinnedlistview.zgq.lettersidebar.R;

/**
 * Created by 37902 on 2016/5/22.
 */
public class LetterView extends View {
    //sideBar
    private Context context;
    private Paint shapePaint;
    private Paint letterPaint;
    private Paint defaultPaint;

    private int circleX;
    private int circleY;

    private int letterSize;
    private int shapeColor;
    private int letterColor;
    private String letter;

    private RectF shapeRectF;

    private Rect textReft;
    private Paint.FontMetricsInt fontMetrics;
    private int baseline;


    //

    public LetterView(Context context) {
        this(context,null);
    }

    public LetterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SideBar);
//        letter = typedArray.getString(R.styleable.LetterView_letter);
//        letterSize = typedArray.getInt(R.styleable.LetterView_letterSize, 30);
//        shapeColor = typedArray.getColor(R.styleable.LetterView_shapeColor, Color.RED);
//        letterColor = typedArray.getColor(R.styleable.LetterView_letterColor, Color.BLUE);
        init();
    }
    private void init(){
        shapePaint = new Paint();
        shapePaint.setAntiAlias(true);
        shapePaint.setColor(shapeColor);

        letterPaint = new Paint();
        letterPaint.setTextSize(100);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColor);

        defaultPaint = new Paint();
        defaultPaint.setAlpha(0);

        shapeRectF = new RectF();

        letter = "A";
    }


    public int getLetterSize() {
        return letterSize;
    }

    public void setLetterSize(int letterSize) {
        this.letterSize = letterSize;
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
    }

    public int getLetterColor() {
        return letterColor;
    }

    public void setLetterColor(int letterColor) {
        this.letterColor = letterColor;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        circleX = getWidth()/2;
        circleY = getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shapeRectF.set(circleX,circleY,getWidth(),circleY+getWidth()/2);
        canvas.drawRect(shapeRectF,shapePaint);

        canvas.drawCircle(circleX,circleY,circleX,shapePaint);

        textReft = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawRect(textReft,defaultPaint);
        fontMetrics = letterPaint.getFontMetricsInt();
        baseline = (textReft.bottom - fontMetrics.bottom + fontMetrics.top) / 2- fontMetrics.top;
        letterPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(letter, textReft.centerX(), baseline, letterPaint);
    }
}
