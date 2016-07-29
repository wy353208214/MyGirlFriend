package cn.wangyang.www.mygirlfriend.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by WangYang on 2016/7/21.
 */
public class PathTextView extends View {

    private Path textPath = new Path();
    private Paint textPaint = new Paint();
    private float tPos[] = new float[2];

    public PathTextView(Context context) {
        this(context, null);
    }

    public PathTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(3);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(80);
        textPaint.setAntiAlias(true);

        textPath.moveTo(50, 0);
        textPath.quadTo(200, 400, 50, 800);

        final PathMeasure tPathMesMeasure = new PathMeasure(textPath, false);
        ValueAnimator animator2 = ObjectAnimator.ofFloat(0, tPathMesMeasure.getLength());
        animator2.setDuration(2000);
        animator2.setRepeatCount(-1);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float distance = (Float) valueAnimator.getAnimatedValue();
                tPathMesMeasure.getPosTan(distance, tPos, null);
                invalidate();
            }
        });
        animator2.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawTextOnPath("菩提本无树", textPath, 10, 10, textPaint);
        textPath.setLastPoint(tPos[0], tPos[1]);
    }
}
