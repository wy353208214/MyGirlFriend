package cn.wangyang.www.mygirlfriend.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import cn.wangyang.www.mygirlfriend.R;

/**
 * Created by WangYang on 2016/7/19.
 */
public class BezierView extends View {

    private Path mPath = new Path();
    private Paint paint = new Paint();
    private float mPos[] = new float[2];


    private int orientation = 0;
    private int degree = 0;
    private int viewWidth = 0;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.bezierview);
        orientation = typedArray.getInt(R.styleable.bezierview_orientation, 0);
        degree = typedArray.getInt(R.styleable.bezierview_degree, 0);
        typedArray.recycle();

        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(60);
        paint.setAntiAlias(true);
    }

    private void pathCalculate() {
        //横向排版
        if (orientation == 0) {
            mPath.moveTo(0, 0);
            mPath.cubicTo(200, 200, 400, 0, 600, 200);
        } else {
            if (degree == 0) {
                mPath.moveTo(0, 0);
                mPath.cubicTo(viewWidth, 200, 0, 400, viewWidth, 600);
            } else {
                mPath.moveTo(viewWidth, 0);
                mPath.cubicTo(0, 200, viewWidth, 400, 0, 600);
            }
        }

        AnimatorSet animatorSet = new AnimatorSet();

        final PathMeasure pathMeasure = new PathMeasure(mPath, false);
        ValueAnimator animator1 = ObjectAnimator.ofFloat(0, pathMeasure.getLength());
        animator1.setDuration(2000);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float distance = (Float) valueAnimator.getAnimatedValue();
                pathMeasure.getPosTan(distance, mPos, null);
                invalidate();
            }
        });

        ValueAnimator animator2 = ObjectAnimator.ofFloat(-viewWidth / 2, viewWidth / 2);
        animator2.setDuration(3000);
        animator2.setRepeatCount(-1);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float distance = (Float) valueAnimator.getAnimatedValue();
                mPath.reset();
                if (degree == 0) {
                    mPath.moveTo(0, 0);
                    mPath.cubicTo(viewWidth, 200, distance, 400, viewWidth, 600);
                } else {
                    mPath.moveTo(viewWidth, 0);
                    mPath.cubicTo(0, 200, viewWidth - distance, 400, 0, 600);
                }
                invalidate();
            }
        });

        animatorSet.playSequentially(animator1, animator2);
        animatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (viewWidth == 0) {
            viewWidth = getMeasuredWidth();
            pathCalculate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, paint);
        mPath.setLastPoint(mPos[0], mPos[1]);
    }

}
