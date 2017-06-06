package rjsv.morphos.data.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;

import rjsv.morphos.data.ViewDefault;
import rjsv.morphos.enumerators.AnimationTarget;

import static rjsv.morphos.enumerators.AnimationTarget.BY;


/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class RotationXYAnimation extends Animation {

    private float initialDegreesX;
    private float initialDegreesY;
    private float targetDegreesX;
    private float targetDegreesY;
    private AnimationTarget animationType;

    public RotationXYAnimation(AnimationTarget animationType, float targetDegreesX, float targetDegreesY, int duration, Interpolator interpolator) {
        this.duration = duration;
        this.interpolator = interpolator;
        this.initialDegreesX = targetDegreesX;
        this.initialDegreesY = targetDegreesY;
        this.targetDegreesX = targetDegreesX;
        this.targetDegreesY = targetDegreesY;
        this.animationType = animationType;
    }

    @Override
    public Animator buildAnimation(@NonNull ViewDefault viewDefault, @NonNull final View viewToMorph, boolean isReversed) {
        float fromDegreesX, fromDegreesY, toDegreesX, toDegreesY;
        fromDegreesX = viewDefault.getDispositionAngleX();
        fromDegreesY = viewDefault.getDispositionAngleY();
        if (animationType == BY) {
            toDegreesX = fromDegreesX + (isReversed ? -targetDegreesX : targetDegreesX);
            toDegreesY = fromDegreesY + (isReversed ? -targetDegreesY : targetDegreesY);
        } else {
            if (!isReversed) {
                initialDegreesX = fromDegreesX;
                initialDegreesY = fromDegreesY;
            }
            toDegreesX = isReversed ? initialDegreesX : targetDegreesX;
            toDegreesY = isReversed ? initialDegreesY : targetDegreesY;
        }
        viewDefault.setDispositionAngleX(toDegreesX);
        viewDefault.setDispositionAngleY(toDegreesY);
        PropertyValuesHolder[] parameters = new PropertyValuesHolder[2];
        parameters[0] = PropertyValuesHolder.ofFloat(View.ROTATION_X, fromDegreesX, toDegreesX);
        parameters[1] = PropertyValuesHolder.ofFloat(View.ROTATION_Y, fromDegreesY, toDegreesY);
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
