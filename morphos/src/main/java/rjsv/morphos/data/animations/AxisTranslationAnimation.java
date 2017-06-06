package rjsv.morphos.data.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;

import rjsv.morphos.Morpho;
import rjsv.morphos.data.ViewDefault;
import rjsv.morphos.enumerators.AnimationTarget;

import static android.view.View.X;
import static android.view.View.Y;
import static android.view.View.Z;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class AxisTranslationAnimation extends Animation {

    private Axis axis;
    private AnimationTarget target;
    private float initialAxisValue;
    private float targetAxisValue;

    public AxisTranslationAnimation(Axis axis, AnimationTarget target, float axisTranslationValue, int duration, Interpolator interpolator) {
        this.duration = duration;
        this.interpolator = interpolator;
        this.axis = axis;
        this.target = target;
        this.initialAxisValue = axisTranslationValue;
        this.targetAxisValue = axisTranslationValue;
    }

    @Override
    public Animator buildAnimation(@NonNull ViewDefault viewDefault, @NonNull View viewToMorph, boolean isReversed) {
        float targetValue;
        PropertyValuesHolder[] parameters = new PropertyValuesHolder[1];
        switch (axis) {
            case X:
                if (target == AnimationTarget.TO) {
                    if (!isReversed) {
                        initialAxisValue = viewDefault.getX();
                    }
                    targetValue = isReversed ? initialAxisValue : targetAxisValue;
                    viewDefault.setX(targetValue);
                } else {
                    targetValue = viewDefault.getX() + (isReversed ? -targetAxisValue : targetAxisValue);
                    viewDefault.setX(targetValue);
                }
                parameters[0] = PropertyValuesHolder.ofFloat(X, targetValue);
                break;
            case Y:
                if (target == AnimationTarget.TO) {
                    if (!isReversed) {
                        initialAxisValue = viewDefault.getY();
                    }
                    targetValue = isReversed ? initialAxisValue : targetAxisValue;
                    viewDefault.setY(targetValue);
                } else {
                    targetValue = viewDefault.getY() + (isReversed ? -targetAxisValue : targetAxisValue);
                    viewDefault.setY(targetValue);
                }
                parameters[0] = PropertyValuesHolder.ofFloat(Y, targetValue);
                break;
            case Z:
                if (target == AnimationTarget.TO) {
                    if (!isReversed) {
                        initialAxisValue = viewDefault.getZ();
                    }
                    targetValue = isReversed ? initialAxisValue : targetAxisValue;
                    viewDefault.setZ(targetValue);
                } else {
                    targetValue = viewDefault.getZ() + (isReversed ? -targetAxisValue : targetAxisValue);
                    viewDefault.setZ(targetValue);
                }
                parameters[0] = Morpho.atLeastLollipop ? PropertyValuesHolder.ofFloat(Z, targetValue) : null;
                break;
        }
        if (parameters[0] == null) {
            return null;
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(viewToMorph, parameters);
        if (duration >= 0) {
            animator.setDuration(duration);
        }
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        return animator;
    }

    public enum Axis {
        X, Y, Z
    }

}
