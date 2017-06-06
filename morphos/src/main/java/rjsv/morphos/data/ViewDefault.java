package rjsv.morphos.data;

import android.view.View;

import static rjsv.morphos.Morpho.atLeastLollipop;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class ViewDefault {

    private float alpha;
    private float x;
    private float y;
    private float z;
    private float width;
    private float height;
    private float dispositionAngle;
    private float expansionScaleX;
    private float expansionScaleY;
    private float dispositionAngleX;
    private float dispositionAngleY;

    public ViewDefault(View v) {
        updateView(v);
    }

    public void updateView(View v) {
        if (v != null) {
            this.alpha = v.getAlpha();
            this.x = v.getX();
            this.y = v.getY();
            this.z = atLeastLollipop ? v.getZ() : 0;
            this.width = v.getWidth();
            this.height = v.getHeight();
            this.expansionScaleX = v.getScaleX();
            this.expansionScaleY = v.getScaleY();
            this.dispositionAngle = v.getRotation();
            this.dispositionAngleX = v.getRotationX();
            this.dispositionAngleY = v.getRotationY();
        }
    }

    public float getAlpha() {
        return alpha;
    }

    public ViewDefault setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public float getX() {
        return x;
    }

    public ViewDefault setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public ViewDefault setY(float y) {
        this.y = y;
        return this;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getWidth() {
        return width;
    }

    public ViewDefault setWidth(float width) {
        this.width = width;
        return this;
    }

    public float getHeight() {
        return height;
    }

    public ViewDefault setHeight(float height) {
        this.height = height;
        return this;
    }

    public float getExpansionScaleX() {
        return expansionScaleX;
    }

    public void setExpansionScaleX(float expansionScaleX) {
        this.expansionScaleX = expansionScaleX;
    }

    public float getExpansionScaleY() {
        return expansionScaleY;
    }

    public void setExpansionScaleY(float expansionScaleY) {
        this.expansionScaleY = expansionScaleY;
    }

    public float getDispositionAngle() {
        return dispositionAngle;
    }

    public ViewDefault setDispositionAngle(float angle) {
        this.dispositionAngle = angle;
        return this;
    }

    public float getDispositionAngleX() {
        return dispositionAngleX;
    }

    public void setDispositionAngleX(float dispositionAngleX) {
        this.dispositionAngleX = dispositionAngleX;
    }

    public float getDispositionAngleY() {
        return dispositionAngleY;
    }

    public void setDispositionAngleY(float dispositionAngleY) {
        this.dispositionAngleY = dispositionAngleY;
    }
}
