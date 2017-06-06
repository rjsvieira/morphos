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

public class AlphaAnimation extends Animation {

    private float initialAlpha;
    private float targetAlpha;

    public AlphaAnimation(float toValue, int duration, Interpolator interpolator) {
        this.duration = duration;
        this.interpolator = interpolator;
        this.initialAlpha = toValue;
        this.targetAlpha = toValue;
    }

    @Override
    public Animator buildAnimation(@NonNull ViewDefault viewDefault, @NonNull View viewToMorph, boolean isReversed) {
        if (!isReversed) {
            initialAlpha = viewDefault.getAlpha();
        }
        float alphaValue = isReversed ? initialAlpha : targetAlpha;
        alphaValue = alphaValue < 0 ? 0 : alphaValue;
        alphaValue = alphaValue > 1 ? 1 : alphaValue;
        viewDefault.setAlpha(alphaValue);
        PropertyValuesHolder[] parameters = new PropertyValuesHolder[1];
        parameters[0] = PropertyValuesHolder.ofFloat(View.ALPHA, alphaValue);
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
