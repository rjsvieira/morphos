package rjsv.morphos.data.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;

import rjsv.morphos.data.ViewDefault;
import rjsv.morphos.enumerators.AnimationTarget;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class RotationAnimation extends Animation {

    private float targetDegrees;
    private float initialDegrees;
    private AnimationTarget animationType;

    public RotationAnimation(AnimationTarget animationType, float targetDegrees, int duration, Interpolator interpolator) {
        this.duration = duration;
        this.interpolator = interpolator;
        this.initialDegrees = targetDegrees;
        this.targetDegrees = targetDegrees;
        this.animationType = animationType;
    }

    @Override
    public Animator buildAnimation(@NonNull ViewDefault viewDefault, @NonNull final View viewToMorph, boolean isReversed) {
        float fromDegrees, toDegrees;
        fromDegrees = viewDefault.getDispositionAngle();
        switch (animationType) {
            case BY:
                toDegrees = fromDegrees + (isReversed ? -targetDegrees : targetDegrees);
                break;
            case TO:
            default:
                if (isReversed) {
                    toDegrees = fromDegrees != targetDegrees ? fromDegrees : initialDegrees;
                } else {
                    initialDegrees = fromDegrees;
                    toDegrees = targetDegrees;
                }
                break;
        }
        viewDefault.setDispositionAngle(toDegrees);
        PropertyValuesHolder[] parameters = new PropertyValuesHolder[1];
        parameters[0] = PropertyValuesHolder.ofFloat(View.ROTATION, fromDegrees, toDegrees);
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
