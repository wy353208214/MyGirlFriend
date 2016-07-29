package cn.wangyang.www.mygirlfriend.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import cn.wangyang.www.mygirlfriend.R;

/**
 * Created by WangYang on 2016/7/21.
 */
public class Win10Launcher extends RelativeLayout {

    private AnimatorSet animatorSet = new AnimatorSet();

    public Win10Launcher(Context context) {
        this(context, null);
    }

    public Win10Launcher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Win10Launcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = null;
        for (int i = 0; i < 5; i++) {
            View view = View.inflate(getContext(), R.layout.circle_item, null);
            addView(view, i);

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 360);
            objectAnimator.setDuration(2000 + i * 150);
            objectAnimator.setStartDelay(i * 150);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            if (builder == null) {
                builder = animatorSet.play(objectAnimator);
            } else {
                builder.with(objectAnimator);
            }
        }
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    public void cancle() {
        if (animatorSet != null)
            if (animatorSet.isRunning())
                animatorSet.cancel();
            else
                animatorSet.start();

    }

}
