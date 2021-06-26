package tseela.school.ap2_ex3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/*
 * Joystick view.
 * It's build like a target-> out circle and inner circle which the user can move.
 */
public class Joystick extends View {
    // paint to draw the circles
    private final Paint m_innerCirclePaint;
    private final Paint m_outCirclePaint;

    // x & y of the center of the inner circle
    private int m_centerX;
    private int m_centerY;

    public JoystickChangeListener onChange = null;

    // constructor
    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // initialize paints
        m_innerCirclePaint = new Paint();
        m_innerCirclePaint.setStyle(Style.FILL);
        m_outCirclePaint = new Paint();
        m_outCirclePaint.setStyle(Style.STROKE);

        // get attrs
        TypedArray attrsArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Joystick, 0, 0);

        // get colors from attrs
        m_outCirclePaint.setColor(attrsArray.getColor(R.styleable.Joystick_outCircleColor, 0));
        m_innerCirclePaint.setColor(attrsArray.getColor(R.styleable.Joystick_innerCircleColor, 0));

        // init x & y to -1 because we don't know our canvas size yet
        m_centerX = -1;
        m_centerY = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int outCircleRadius = getOutCircleRadius();
        int innerCircleRadius = getInnerCircleRadius();

        int cx = getRangeCenterX(), cy = getRangeCenterY();

        // if centerX or centerY is not on a valid size, change them to be the center of the view
        if (!isBetween(m_centerX, 0, this.getMeasuredWidth())
                || !isBetween(m_centerY, 0, this.getMeasuredHeight())) {
            m_centerX = cx;
            m_centerY = cy;
        }

        // draw joystick (just two circles)
        canvas.drawCircle(cx, cy, outCircleRadius, m_outCirclePaint);
        canvas.drawCircle(m_centerX, m_centerY, innerCircleRadius, m_innerCirclePaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        double x = event.getX(), y = event.getY();

        // if the user pressed the joystick or drugged it
        if (event.getAction() == MotionEvent.ACTION_MOVE
                || event.getAction() == MotionEvent.ACTION_DOWN) {
            // if it's in the big circle, change the center point to be the pressing point
            if (isInOutCircle(x, y)) {
                m_centerX = (int) x;
                m_centerY = (int) y;
            } else { // else, change it to be the closest point in the big circle
                int cx = getRangeCenterX(), cy = getRangeCenterY();

                // some math is required
                int side = 1;
                if (x < cx) {
                    side = -1;
                }
                double m = (y - cy) / (x - cx);
                m_centerX = (int) (side * getOutCircleRadius() / (Math.sqrt(1 + m * m)) + cx);
                m_centerY = (int) (getOutCircleRadius() * (y - cy)
                        / (Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy))) + cy);
            }
        // else, if the user releases the joystick, move it back to the middle
        }/* else if (event.getAction() == MotionEvent.ACTION_UP) {
            m_centerX = getRangeCenterX();
            m_centerY = getRangeCenterY();
        }*/

        if (onChange != null) {
            onChange.onJoystickChange((double)(m_centerX  - getRangeCenterX()) / getOutCircleRadius(),
                    (double)(m_centerY  - getRangeCenterY()) / getOutCircleRadius());
        }

        invalidate();

        return true;
    }

    /**
     * checks if a point is in the little circle
     * @param x - point x
     * @param y - point y
     * @return true if it's in the circle and false otherwise
     */
    private boolean isInInnerCircle(double x, double y) {
        return isInCircle(x, y, m_centerX, m_centerY, getInnerCircleRadius());
    }

    /**
     * checks if a point is in the big circle
     * @param x - point x
     * @param y - point y
     * @return true if it's in the circle and false otherwise
     */
    private boolean isInOutCircle(double x, double y) {
        return isInCircle(x, y, getRangeCenterX(), getRangeCenterY(), getOutCircleRadius());
    }

    /**
     * checks if a point is in a circle
     * @param x - point x
     * @param y - point y
     * @param circleX - middle circle x
     * @param circleY - middle circle y
     * @param r - circle radius
     * @return true if it's in the circle and false otherwise
     */
    private boolean isInCircle(double x, double y, double circleX, double circleY, double r) {
        return (x - circleX) * (x - circleX) + (y - circleY) * (y - circleY) <= r * r;
    }

    /**
     * checks if a number is between two other numbers
     * @param num - number
     * @param small - the smaller one on the range
     * @param large - the bigger one on the range
     * @return true if num is between small and large and false otherwise
     */
    private boolean isBetween(double num, double small, double large) {
        return small < num && num < large;
    }

    // return inner circle's radius
    public int getInnerCircleRadius() {
        if (this.getMeasuredWidth() > this.getMeasuredHeight()) {
            return this.getMeasuredHeight() / 5;
        }

        return this.getMeasuredWidth() / 5;
    }
    // return out circle's radius
    public int getOutCircleRadius() {
        if (this.getMeasuredWidth() > this.getMeasuredHeight()) {
            return this.getMeasuredHeight() / 2 - this.getMeasuredHeight() / 10;
        }

        return this.getMeasuredWidth() / 2 - this.getMeasuredWidth() / 10;
    }

    // getters for joystick center point and big circle's center point
    public int getJoystickCenterX() { return m_centerX; }
    public int getJoystickCenterY() { return m_centerY; }
    public int getRangeCenterX() { return this.getMeasuredWidth() / 2; }
    public int getRangeCenterY() { return this.getMeasuredHeight() / 2; }

    // setters for joystick's location
    public void setJoystickCenterX(int x) { m_centerX = x; }
    public void setJoystickCenterY(int y) { m_centerY = y; }

    public static interface JoystickChangeListener {
        void onJoystickChange(double aileron, double elevator);
    }
}
