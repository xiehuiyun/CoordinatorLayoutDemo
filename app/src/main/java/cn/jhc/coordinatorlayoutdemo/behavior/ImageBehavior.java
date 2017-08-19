package cn.jhc.coordinatorlayoutdemo.behavior;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/8/19.
 */

public class ImageBehavior extends CoordinatorLayout.Behavior<ImageView> {

    private Context context;
    private boolean isAnimation;
    private float height = 0;

    public ImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View directTargetChild, View target, int nestedScrollAxes) {
        if (child.getVisibility() == View.VISIBLE && height == 0) {
            height = child.getHeight() * (context.getResources().getDisplayMetrics().density) + 0.5f;
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target, int dx, int dy, int[] consumed) {
        //dy大于0是向上滚动 小于0是向下滚动
//        if (dy > 0 && !isAnimation && child.getVisibility() == View.VISIBLE) {
//            hideView(child);
//        } else if (dy < 0 && !isAnimation && child.getVisibility() == View.GONE) {
//            showView(child);
//        }

    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target, int dxConsumed, int dy, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dy, dxUnconsumed, dyUnconsumed);
        if (dy >= 0 && !isAnimation && child.getVisibility() == View.VISIBLE) {
            hideView(child);
        } else if (dy < 0 && !isAnimation && child.getVisibility() == View.GONE) {
            showView(child);
        }
    }

    private void hideView(final ImageView child) {
        ViewPropertyAnimator animator = child.animate().setDuration(800).setInterpolator(new DecelerateInterpolator()).translationY(-height);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimation = false;
                child.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                showView(child);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void showView(final ImageView child) {
        ViewPropertyAnimator animator = child.animate().setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .translationY(0);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimation = false;
                child.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                hideView(child);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
}


