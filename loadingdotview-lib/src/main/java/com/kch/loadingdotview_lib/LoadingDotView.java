package com.kch.loadingdotview_lib;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * Created by kch on 2017/6/15.
 */

public class LoadingDotView extends View {
    private static final String TAG = "LoadingDotView";
    //所有的圆点
    private Dot[] mDots;
    //圆点的颜色
    private int mDotColor;
    //圆点的数量
    private int mDotNum;
    //圆点半径
    private float mDotRadius;
    //圆点放大后的半径
    private float mMaxDotRadius;
    //正在放缩的圆点的半径
    private float mScalingDotRadius;
    //圆点之间的间隔
    private float mDotSpace;
    //动画放缩的时间
    private int mSpeed;
    //当前放缩的位置
    private int mScalePosition=0;
    //上次放缩的位置
    private int mLastScalePosition=-1;
    //动画插值器
    private Interpolator mInterpolator;

    private Paint mPaint;

    //是否正在执行动画
    private boolean isScaling;
    //是否重置
    private boolean isReset;

    //上次的可见性
    private int mLastVisiblity;

    //默认参数
    private int deafultColor;
    private int defaultDotNum;
    private float defaultRadius;
    private float defaultDotSpace;
    private float defaultMaxRadius;
    private int defaultSpeed;

    public LoadingDotView(Context context) {
        this(context,null);
    }

    public LoadingDotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingDotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
        initPaint();
        initDots();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.LoadingDotView);
        deafultColor = Color.parseColor("#48D1CC");
        mDotColor = typedArray.getColor(R.styleable.LoadingDotView_dot_color,deafultColor);
        defaultDotNum = 3;
        mDotNum = typedArray.getInt(R.styleable.LoadingDotView_dot_num,defaultDotNum);
        defaultRadius = 30;
        mDotRadius = typedArray.getDimension(R.styleable.LoadingDotView_dot_radius,defaultRadius);
        defaultDotSpace = 16;
        mDotSpace = typedArray.getDimension(R.styleable.LoadingDotView_dot_space,defaultDotSpace);
        defaultMaxRadius = (float) (mDotRadius+mDotSpace/2*0.7);
        mMaxDotRadius =typedArray.getDimension(R.styleable.LoadingDotView_dot_scaled_radius,defaultMaxRadius);
        defaultSpeed = 250;
        mSpeed = typedArray.getInt(R.styleable.LoadingDotView_anim_speed,defaultSpeed);
        int interpolatorId = typedArray.getResourceId(R.styleable.LoadingDotView_scale_interpolator,android.R.anim.linear_interpolator);
        mInterpolator  = AnimationUtils.loadInterpolator(context, interpolatorId);
        typedArray.recycle();

        //限制参数
        if (mDotNum<defaultDotNum||mDotNum>10)
            mDotNum=defaultDotNum;

        if (mDotSpace<defaultDotSpace)
            mDotSpace=defaultDotSpace;

        if (mMaxDotRadius<mDotRadius||mMaxDotRadius>(mDotRadius+mDotSpace))
            mMaxDotRadius=mDotRadius+mDotSpace/2;

        if (mSpeed<0)
            mSpeed = defaultSpeed;
        mScalingDotRadius = mDotRadius;
        mLastVisiblity = getVisibility();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mDotColor);
    }

    private void initDots() {
        mDots = new Dot[mDotNum];
        for (int i=0;i<mDots.length;i++){
            Dot dot = new Dot();
            dot.position = i;
            //圆心的x坐标计算公式，需要加上圆点放大后的距离
            dot.x = mDotRadius*(2*i+1)+mDotSpace*i+(mMaxDotRadius-mDotRadius);
            dot.y=mMaxDotRadius;
            mDots[i]=dot;
        }
    }

    public void setDotColor(int color){
        this.mDotColor = color;
        mPaint.setColor(mDotColor);
    }

    public void setDotNum(int dotNum){
        this.mDotNum = dotNum;

        if (mDotNum<defaultDotNum||mDotNum>10)
            mDotNum=defaultDotNum;
        initDots();

    }

    public void setDotRadius(int dotRadius){
        this.mDotRadius = dotRadius;
        initDots();
    }

    public void setDotSpace(float dotSpace){
        this.mDotSpace = dotSpace;

        if (mDotSpace<defaultDotSpace)
            mDotSpace=defaultDotSpace;
        initDots();

    }

    public void setMaxDotRadius(float maxDotRadius){
        this.mMaxDotRadius = maxDotRadius;

        if (mMaxDotRadius<mDotRadius||mMaxDotRadius>(mDotRadius+mDotSpace))
            mMaxDotRadius=mDotRadius+mDotSpace/2;
        initDots();
    }

    public void setmSpeed(int speed){
        this.mSpeed = speed;

        if (mSpeed<0)
            mSpeed = defaultSpeed;
    }

    public void setInterpolator(Interpolator interpolator){
        this.mInterpolator = interpolator;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<mDotNum;i++){
            if (i==mScalePosition){
                canvas.drawCircle(mDots[i].x,mDots[i].y,mScalingDotRadius,mPaint);
            }else
                canvas.drawCircle(mDots[i].x,mDots[i].y,mDotRadius,mPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = (int) (2*mDotRadius*mDotNum+(mDotNum-1)*mDotSpace+(mMaxDotRadius-mDotRadius)*2);
        int height = (int) mMaxDotRadius*2;
        setMeasuredDimension(width,height);

    }


    /**
     * 圆点执行动画的方法
     */
    private void startRace(){
        if (isScaling){
            return;
        }
        ValueAnimator animator = new ValueAnimator().setDuration(mSpeed);
        float startValue = mDotRadius;
        float maxValue = mMaxDotRadius;
        PropertyValuesHolder value = PropertyValuesHolder.ofFloat("radius",startValue,maxValue,startValue);
        animator.setValues(value);
        animator.setInterpolator(mInterpolator);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScalingDotRadius = (float) animation.getAnimatedValue("radius");
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isScaling = true;
                Log.d(TAG,"onAnimationStart"+"isScaling:"+isScaling+";isReset"+isReset);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isScaling=false;
                Log.d(TAG,"onAnimationEnd:"+"isScaling:"+isScaling+";isReset"+isReset);
                mScalingDotRadius = mDotRadius;
                //计算圆点来回跑的算法
                if (mScalePosition == mDotNum - 1) {
                    mLastScalePosition = mScalePosition;
                    mScalePosition--;
                } else if (mScalePosition == 0) {
                    mLastScalePosition = mScalePosition;
                    mScalePosition++;
                } else if (mLastScalePosition < mScalePosition) {
                    mLastScalePosition = mScalePosition;
                    mScalePosition++;
                } else if (mLastScalePosition > mScalePosition) {
                    mLastScalePosition = mScalePosition;
                    mScalePosition--;
                }
                if (isReset){
                    isReset=false;
                    mScalePosition = 0;
                    mLastScalePosition = -1;
                    invalidate();
                }else {
                    invalidate();
                    startRace();
                }


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private static class Dot{
        //圆心的x,y坐标
        float x;
        float y;
        //第几个圆心
        int position;
    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility==VISIBLE) {
            startRace();
            mLastVisiblity = visibility;
        } else if (visibility!=VISIBLE&&mLastVisiblity==VISIBLE){
            reset();
            mLastVisiblity = visibility;
        }

    }

    //当前window销毁后如果动画还在执行，就进行重置，避免死循环
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility!=VISIBLE){
            reset();
        }
        Log.d(TAG,"onWindowVisibilityChanged:"+visibility);
    }

    private void reset() {
        if (isScaling)
        isReset = true;
    }

}
