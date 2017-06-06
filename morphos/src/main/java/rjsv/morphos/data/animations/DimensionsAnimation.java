package rjsv.morphos.data.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import rjsv.morphos.data.ViewDefault;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class DimensionsAnimation extends Animation {

    private float initialWidth;
    private float initialHeight;
    private float targetWidth;
    private float targetHeight;

    public DimensionsAnimation(float width, float height, int duration, Interpolator interpolator) {
        this.duration = duration;
        this.interpolator = interpolator;
        this.initialWidth = width;
        this.initialHeight = height;
        this.targetWidth = width;
        this.targetHeight = height;
    }

    @Override
    public Animator buildAnimation(@NonNull final ViewDefault viewDefault, @NonNull final View viewToMorph, final boolean isReversed) {
        // #1 - calculate anchor values
        final float startingPointX, startingPointY;
        if (!isReversed) {
            initialWidth = viewDefault.getWidth();
            initialHeight = viewDefault.getHeight();
            startingPointX = initialWidth;
            startingPointY = initialHeight;
        } else {
            startingPointX = targetWidth;
            startingPointY = targetHeight;
        }
        final float widthFactor = (targetWidth - initialWidth) / 1000;
        final float heightFactor = (targetHeight - initialHeight) / 1000;
        viewDefault.setWidth(isReversed ? initialWidth : targetWidth);
        viewDefault.setHeight(isReversed ? initialHeight : targetHeight);
        ValueAnimator animator = ValueAnimator.ofFloat(0, isReversed ? -1000 : 1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = ((Float) animation.getAnimatedValue());
                int widthValue = (int) (startingPointX + (widthFactor * value));
                int heightValue = (int) (startingPointY + (heightFactor * value));
                viewDefault.setWidth(widthValue);
                viewDefault.setHeight(heightValue);
                ViewGroup.LayoutParams params = viewToMorph.getLayoutParams();
                params.width = widthValue;
                params.height = heightValue;
                viewToMorph.setLayoutParams(params);
            }
        });
        if (duration >= 0) {
            animator.setDuration(duration);
        }
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        return animator;
    }

}
