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

public class Joystick extends View {
    private final Paint m_innerCirclePaint;
    private final Paint m_outCirclePaint;

    private int m_centerX;
    private int m_centerY;

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        m_innerCirclePaint = new Paint();
        m_innerCirclePaint.setStyle(Style.FILL);
        m_outCirclePaint = new Paint();
        m_outCirclePaint.setStyle(Style.STROKE);

        TypedArray attrsArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Joystick, 0, 0);

        // get radius and paint
        m_outCirclePaint.setColor(attrsArray.getColor(R.styleable.Joystick_outCircleColor, 0));
        m_innerCirclePaint.setColor(attrsArray.getColor(R.styleable.Joystick_innerCircleColor, 0));

        m_centerX = -1;
        m_centerY = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int outCircleRadius = getOutCircleRadius();
        int innerCircleRadius = getInnerCircleRadius();

        int cx = this.getMeasuredWidth() / 2, cy = this.getMeasuredHeight() / 2;

        if (!isBetween(m_centerX, 0, this.getMeasuredWidth())
                || !isBetween(m_centerY, 0, this.getMeasuredHeight())) {
            m_centerX = cx;
            m_centerY = cy;
        }

        canvas.drawCircle(cx, cy, outCircleRadius, m_outCirclePaint);
        canvas.drawCircle(m_centerX, m_centerY, innerCircleRadius, m_innerCirclePaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        double x = event.getX(), y = event.getY();

        if (isOnOutCircle(x, y)
                && (event.getAction() == MotionEvent.ACTION_MOVE
                || event.getAction() == MotionEvent.ACTION_DOWN)) {
            m_centerX = (int) x;
            m_centerY = (int) y;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            m_centerX = this.getMeasuredWidth() / 2;
            m_centerY = this.getMeasuredHeight() / 2;
        }

        invalidate();

        return true;
    }

    private boolean isOnInnerCircle(double x, double y) {
        int radius = getInnerCircleRadius();

        int centerX = this.getMeasuredWidth() / 2, centerY = this.getMeasuredHeight() / 2;

        return isOnCircle(x, y, centerX, centerY, radius);
    }

    private boolean isOnOutCircle(double x, double y) {
        int radius = getOutCircleRadius();

        int centerX = this.getMeasuredWidth() / 2, centerY = this.getMeasuredHeight() / 2;

        return isOnCircle(x, y, centerX, centerY, radius);
    }

    private boolean isOnCircle(double x, double y, double circleX, double circleY, double r) {
        return (x - circleX) * (x - circleX) + (y - circleY) * (y - circleY) <= r * r;
    }

    private boolean isBetween(double num, double small, double large) {
        return small <= num && num <= large;
    }

    public int getInnerCircleRadius() {
        if (this.getMeasuredWidth() > this.getMeasuredHeight()) {
            return this.getMeasuredHeight() / 4;
        }

        return this.getMeasuredWidth() / 4;
    }
    public int getOutCircleRadius() {
        if (this.getMeasuredWidth() > this.getMeasuredHeight()) {
            return this.getMeasuredHeight() / 2;
        }

        return this.getMeasuredWidth() / 2;
    }

    public int getJoystickCenterX() { return m_centerX; }
    public int getJoystickCenterY() { return m_centerY; }

    public void setJoystickCenterX(int x) { m_centerX = x; }
    public void setJoystickCenterY(int y) { m_centerY = y; }
}
