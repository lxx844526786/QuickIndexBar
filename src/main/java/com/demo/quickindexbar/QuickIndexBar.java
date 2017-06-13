package com.demo.quickindexbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/5/27.
 */
public class QuickIndexBar extends View {
    private Paint mPaint;
    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private float mCellHeight;
    private float mCellWidth;

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置文字的大小,颜色
        int textsize = getResources().getDimensionPixelSize(R.dimen.textsize);
        mPaint.setTextSize(textsize);
        mPaint.setColor(Color.WHITE);

        //文字默认的绘制起点是左下角,这里设置为文字底边的中心
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //可以拿到每个格子的高度
        mCellHeight = getMeasuredHeight() * 1f / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //每个文字的X坐标都是一定的,就是当前view宽度的一半
        mCellWidth = getMeasuredWidth()*1f/2;
        //在这里需要把26个字母绘制上去,我们要拿到文字的起点坐标,x,y
        for (int i = 0; i < indexArr.length; i++) {
            //得到y的坐标,y的坐标等于 格子高度的一半 + 文字高度的一半 + i * 格子的高度
            float x = mCellWidth;
            float y = mCellHeight / 2 + getTextHeight(indexArr[i])/2 + i * mCellHeight;
            //判断当前i 是否等于 touchIndex,改变颜色
            mPaint.setColor( i == touchIndex? Color.RED:Color.WHITE);
            canvas.drawText(indexArr[i],x,y,mPaint);
        }
    }

    /**获取文字的高度
     * @param
     * @return
     */
    private int getTextHeight(String s){
        Rect bounds = new Rect();
        //代码执行完毕后,bounds就有值了
        mPaint.getTextBounds(s,0,s.length(),bounds);
        return bounds.height();
    }
    //初始化触摸点的索引
    private int touchIndex = -1;
    /**重写这个方法是为了判断手指落下时的字母的索引
     * 就是拿落下的y坐标除以格子的高度,就是当前字母的索引
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //拿到触摸点的索引
               int index = (int) (event.getY() / mCellHeight);
                //进行安全监测
                if(index > 0 && index < indexArr.length){
                    if(mChangerListener != null){
                        mChangerListener.onLetterChange(indexArr[index]);
                    }
                }
                touchIndex = index;
            break;
            case MotionEvent.ACTION_UP:
                //手指抬起的时候重置
                touchIndex = -1;
                break;
            default:
            break;
        }
        //重新绘制
        invalidate();
        return true;
    }
    private onTouchLetterChangerListener mChangerListener;
    public void onTouchChange(onTouchLetterChangerListener listener){
        this.mChangerListener = listener;
    }
    /**
     * 定义一个监听器
     */
    public interface onTouchLetterChangerListener{
        void onLetterChange(String letter);
    }

}
