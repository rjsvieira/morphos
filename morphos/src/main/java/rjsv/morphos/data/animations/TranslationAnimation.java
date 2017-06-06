package rjsv.morphos.data.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;

import rjsv.morphos.data.ViewDefault;
import rjsv.morphos.enumerators.AnimationTarget;

import static rjsv.morphos.Morpho.atLeastLollipop;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class TranslationAnimation extends Animation {

    private float initialValueX;
    private float initialValueY;
    private float initialValueZ;
    private float translationValueX;
    private float translationValueY;
    private float translationValueZ;
    private AnimationTarget target;

    public TranslationAnimation(AnimationTarget target, float translationValueX, float translationValueY, float translationValueZ, int duration, Interpolator interpolator) {
        this.duration = duration;
        this.interpolator = interpolator;
        this.target = target;
        this.initialValueX = translationValueX;
        this.initialValueY = translationValueY;
        this.initialValueZ = translationValueZ;
        this.translationValueX = translationValueX;
        this.translationValueY = translationValueY;
        this.translationValueZ = translationValueZ;
    }

    @Override
    public Animator buildAnimation(@NonNull ViewDefault viewDefault, @NonNull View viewToMorph, boolean isReversed) {
        float moveToX = 0, moveToY = 0, moveToZ = 0;
        if (target == AnimationTarget.TO) {
            if (!isReversed) {
                initialValueX = viewDefault.getX();
                initialValueY = viewDefault.getY();
                initialValueZ = viewDefault.getZ();
            }
            moveToX = isReversed ? initialValueX : translationValueX;
            moveToY = isReversed ? initialValueY : translationValueY;
            moveToZ = isReversed ? initialValueZ : translationValueZ;
            viewDefault.setX(moveToX);
            viewDefault.setY(moveToY);
            viewDefault.setZ(moveToZ);
        } else if (target == AnimationTarget.BY) {
            moveToX = viewDefault.getX() + (isReversed ? -translationValueX : translationValueX);
            moveToY = viewDefault.getY() + (isReversed ? -translationValueY : translationValueY);
            moveToZ = viewDefault.getZ() + (isReversed ? -translationValueZ : translationValueZ);
            viewDefault.setX(moveToX);
            viewDefault.setY(moveToY);
            viewDefault.setZ(moveToZ);
        }
        PropertyValuesHolder[] parameters = new PropertyValuesHolder[atLeastLollipop ? 3 : 2];
        parameters[0] = PropertyValuesHolder.ofFloat(View.X, moveToX);
        parameters[1] = PropertyValuesHolder.ofFloat(View.Y, moveToY);
        if (atLeastLollipop) {
            parameters[2] = PropertyValuesHolder.ofFloat(View.Z, moveToZ);
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

}
