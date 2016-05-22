package com.spinnedlistview.zgq.lettersidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 37902 on 2016/5/22.
 */
public class SideBar extends View {

    //background line
    private Paint linePaint;
    private int lineColor;
    //sideBar
    private Context context;
    private final int SLOP = 15;
    private int sideBarLength;
    private int sideBarWidth;

    private int oneLetterLength;
    private int currentLetter;

    private int slipperColorTouched;
    private int slipperColorUntouch;
    private int currentX;
    private int currentY;
    private Paint mPaint;
    private RectF oval3;

    private final int TOUCHEDMODE = 2;
    private final int UNTOUCHMODE = 1;
    private final int SLIPINGMODE = 3;
    private int mode = UNTOUCHMODE;

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};


    //letterview
    private Paint shapePaint;
    private Paint letterPaint;
    private Paint defaultPaint;

    private int circleX;
    private int circleY;
    private int radius;

    private int letterSize;
    private int shapeColor;
    private int letterColor;
    private String letter;

    private RectF shapeRectF;

    private Rect textReft;
    private Paint.FontMetricsInt fontMetrics;
    private int baseline;


    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SideBar);
        //backgroung line
        lineColor = typedArray.getColor(R.styleable.SideBar_backgroundLineColor,Color.BLACK);
        //sidebar
        sideBarLength = typedArray.getInt(R.styleable.SideBar_sideBarLength, 100);
        sideBarWidth = typedArray.getInt(R.styleable.SideBar_sideBarWidth, 30);
        slipperColorTouched = typedArray.getColor(R.styleable.SideBar_slipperColorTouched, context.getResources().getColor(R.color.colorAccent));
        slipperColorUntouch = typedArray.getColor(R.styleable.SideBar_slipperColorUntouch, context.getResources().getColor(R.color.colorPrimary));
        //letterview
        letter = typedArray.getString(R.styleable.SideBar_letter);
        letterSize = typedArray.getInt(R.styleable.SideBar_letterSize, 30);
        shapeColor = typedArray.getColor(R.styleable.SideBar_shapeColor, Color.RED);
        letterColor = typedArray.getColor(R.styleable.SideBar_letterColor, Color.BLUE);

        initData();
    }

    private void initData() {
        //background line
        linePaint = new Paint();
        linePaint.setColor(lineColor);
        //sidebar
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(slipperColorUntouch);
        mPaint.setStyle(Paint.Style.FILL);
        //letterview
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

    //sidebar
    public int getSideBarLength() {
        return sideBarLength;
    }

    public void setSideBarLength(int sideBarLength) {
        this.sideBarLength = sideBarLength;
    }

    public int getSideBarWidth() {
        return sideBarWidth;
    }

    public void setSideBarWidth(int sideBarWidth) {
        this.sideBarWidth = sideBarWidth;
    }

    public int getSlipperColorUntouch() {
        return slipperColorUntouch;
    }

    public void setSlipperColorUntouch(int slipperColorUntouch) {
        this.slipperColorUntouch = slipperColorUntouch;
    }

    public int getSlipperColorTouched() {
        return slipperColorTouched;
    }

    public void setSlipperColorTouched(int slipperColorTouched) {
        this.slipperColorTouched = slipperColorTouched;
    }

    private void setTouchedMode() {
        mode = TOUCHEDMODE;
        mPaint.setColor(slipperColorTouched);
        invalidate();
    }

    private void setUntouchMode() {
        onTouchingLetterChangedListener.onNoTouch();
        mode = UNTOUCHMODE;
        mPaint.setColor(slipperColorUntouch);
        invalidate();
    }

    private void setSlipingMode() {
        mode = SLIPINGMODE;
        invalidate();
    }

    //letterview
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
        //sidebar


        currentX = (int) (getWidth() - sideBarWidth * 3);
        currentY = (int) sideBarLength / 2;
        oval3 = new RectF(currentX - sideBarWidth / 2, currentY - sideBarLength / 2, currentX + sideBarWidth / 2, currentY + sideBarLength / 2);
        //letterview
        circleX = (getWidth() - 6 * sideBarWidth) / 2;
        radius = circleX;
        circleY = currentY - radius;
        textReft = new Rect();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        oneLetterLength = (int) (getHeight() - sideBarLength) / letters.length;
        Log.d("----height", "" + getHeight());
        Log.d("---->>", "" + oneLetterLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(currentX,0,currentX,getHeight(),linePaint);
        switch (mode) {
            case UNTOUCHMODE:
            case TOUCHEDMODE:
                oval3.set(currentX - sideBarWidth / 2, currentY - sideBarLength / 2, currentX + sideBarWidth / 2, currentY + sideBarLength / 2);
                canvas.drawRoundRect(oval3, sideBarWidth / 2, sideBarWidth / 2, mPaint);
                break;
            case SLIPINGMODE:
                //sidebar
                oval3.set(currentX - sideBarWidth / 2, currentY - sideBarLength / 2, currentX + sideBarWidth / 2, currentY + sideBarLength / 2);
                canvas.drawRoundRect(oval3, sideBarWidth / 2, sideBarWidth / 2, mPaint);
                //letterview
                circleY = currentY - radius;
                if (circleY<= radius){
                    circleY = radius;
                }
                shapeRectF.set(circleX, circleY, circleX + radius, circleY + radius);
                canvas.drawRect(shapeRectF, shapePaint);
                canvas.drawCircle(circleX, circleY, radius, shapePaint);
//
//        textReft.set(circleX-radius, circleY-radius, circleX+radius, circleY+radius);
//        canvas.drawRect(textReft,defaultPaint);
//        fontMetrics = letterPaint.getFontMetricsInt();
//        baseline = (textReft.bottom - fontMetrics.bottom + fontMetrics.top) / 2- fontMetrics.top;
//        letterPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(letter, circleX - 30, circleY + 30, letterPaint);
        }

    }


    private int startX = currentX;
    private int startY = currentY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getX() > (circleX * 2) && event.getX() < getWidth()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (event.getX() > (circleX * 2) && event.getX() < getWidth()) {
                        startY = (int) event.getY();
                        setTouchedMode();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(event.getY() - startY) > SLOP) {
                        currentY = (int) event.getY();
                        if (currentY < sideBarLength / 2) {
                            currentY = sideBarLength / 2;
                        }
                        if (currentY > (getHeight() - sideBarLength / 2)) {
                            currentY = getHeight() - sideBarLength / 2;
                        }
                        if (currentLetter != (currentY - sideBarLength / 2) / oneLetterLength) {
                            currentLetter = (currentY - sideBarLength / 2) / oneLetterLength;
                            letter = letters[currentLetter];
                            onTouchingLetterChangedListener.onTouchingLetterChanged(letter);
                        }
                        setSlipingMode();
                        break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    setUntouchMode();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
//        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);

        public void onSideBarSliping(int y);

        public void onNoTouch();
    }
}
