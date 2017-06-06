package rjsv.morphos.data.animations;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Interpolator;

import rjsv.morphos.data.ViewDefault;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public abstract class Animation {

    protected int duration;
    protected Interpolator interpolator;

    public abstract Animator buildAnimation(ViewDefault viewDefault, View view, boolean isReversed);

    public void dispose() {
        this.interpolator = null;
    }

    public int getDuration() {
        return this.duration;
    }

    public Interpolator getInterpolator() {
        return this.interpolator;
    }

}
