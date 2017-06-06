package rjsv.morphos.data.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;

import rjsv.morphos.data.ViewDefault;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class ScaleAnimation extends Animation {

    private float initialScaleX;
    private float initialScaleY;
    private float targetScaleX;
    private float targetScaleY;

    public ScaleAnimation(float initialScaleX, float initialScaleY, float targetScaleX, float targetScaleY, int duration, Interpolator interpolator) {
        this.duration = duration;
        this.interpolator = interpolator;
        this.initialScaleX = initialScaleX;
        this.initialScaleY = initialScaleY;
        this.targetScaleX = targetScaleX;
        this.targetScaleY = targetScaleY;
    }

    @Override
    public Animator buildAnimation(@NonNull ViewDefault viewDefault, @NonNull View viewToMorph, boolean isReversed) {
        float x = isReversed ? initialScaleX : targetScaleX;
        float y = isReversed ? initialScaleY : targetScaleY;
        viewDefault.setExpansionScaleX(x);
        viewDefault.setExpansionScaleY(y);
        PropertyValuesHolder[] parameters = new PropertyValuesHolder[2];
        parameters[0] = PropertyValuesHolder.ofFloat(View.SCALE_X, x);
        parameters[1] = PropertyValuesHolder.ofFloat(View.SCALE_Y, y);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(viewToMorph, parameters);
        if (duration >= 0) {
            animator.setDuration(duration);
        }
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        return animator;
    }

}
