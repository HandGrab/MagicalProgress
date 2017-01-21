package com.sandy.blog.magicalprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sandy Luo(HandGrab) on 2016/7/11.
 * <p/>
 * It's a magical progress bar that was drawn by Sandy Luo (HandGrab)  :-)
 */
public class MagicalProgressBar extends View {

    private static final String TAG = "MagicalProgress";

    private Paint mBgPaint;
    private Paint mFinishedPaint;
    private Paint mUnfinishedPaint;

    private float l;              //外切六边形的边长
    private float r;              //每段圆弧对应半径
    private int measureHeight;
    private int measureWidth;
    private int wrapWidth;

    private float mProgressWidth = dip2px(4) / 2;
    private float mProgress = 0;

    private double sin30 = Math.sin(30 * Math.PI / 180);
    private double cos30 = Math.cos(30 * Math.PI / 180);
    private double tan30 = Math.tan(30 * Math.PI / 180);
    private static final double PI = Math.PI;

    private float[] progressParts = new float[13];
    private PointView[] linePoints;
    private PointView[] arcPoints;
    private float mc;       //每段弧的长度
    private float ml;       //线段的长度
    private float c;        //图形的周长

    private int mCenterColor = Color.parseColor("#D32F2F");
    private int mFinishedColor = Color.parseColor("#1DDEBE");
    private int mUnfinishedColor = Color.parseColor("#B71C1C");

    public MagicalProgressBar(Context context) {
        super(context);
        init();
    }

    public MagicalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MagicalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 设置进度
     *
     * @param progress 当前进度
     */
    public void setProgress(float progress) {
        if(progress > 100){
            this.mProgress = 100;
        }else{
            this.mProgress = progress;
        }
        invalidate();
        requestLayout();
    }


    /**
     * 设置进度条的宽度
     *
     * @param width 进度条的宽度 dp值
     */
    public void setProgressWith(float width) {
        this.mProgressWidth = dip2px(width) / 2;
        mFinishedPaint.setStrokeWidth(dip2px(width));
        mUnfinishedPaint.setStrokeWidth(dip2px(width));
    }

    /**
     * 设置中央背景的颜色
     *
     * @param color 中央背景颜色
     */
    public void setCenterColor(int color) {
        mBgPaint.setColor(color);
    }


    /**
     * 设置已完成进度条的颜色
     *
     * @param color 已完成的进度条颜色
     */
    public void setFinishedColor(int color) {
        mFinishedPaint.setColor(color);
    }

    /**
     * 设置未完成进度条的颜色
     *
     * @param color 未完成的进度条的颜色
     */
    public void setUnfinishedColor(int color) {
        mUnfinishedPaint.setColor(color);
    }


    /**
     * 初始化控件
     */
    protected void init() {
        wrapWidth = (int) dip2px(100);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(mCenterColor);
        mBgPaint.setStrokeWidth(5);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);

        mFinishedPaint = new Paint();

        mUnfinishedPaint = new Paint();
        mUnfinishedPaint.setColor(mUnfinishedColor);
        mUnfinishedPaint.setAntiAlias(true);
        mUnfinishedPaint.setStrokeWidth(mProgressWidth * 2);
        mUnfinishedPaint.setStyle(Paint.Style.STROKE);
        mUnfinishedPaint.setStrokeCap(Paint.Cap.ROUND);

        mFinishedPaint = new Paint();
        mFinishedPaint.setColor(mFinishedColor);
        mFinishedPaint.setAntiAlias(true);
        mFinishedPaint.setStrokeWidth(mProgressWidth * 2);
        mFinishedPaint.setStyle(Paint.Style.STROKE);
        mFinishedPaint.setStrokeCap(Paint.Cap.SQUARE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);

        if (widthSpecMode == View.MeasureSpec.AT_MOST) {
            measureWidth = wrapWidth;
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST) {
            measureWidth = widthSpecSize;
        }

        measureHeight = (int) (measureWidth / cos30);
        l = (float) ((measureWidth / 2) / cos30);
        r = l / 5;

        initPoints();
        calculateProgress();

        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * 进度节点的计算
     */
    private void calculateProgress() {
        mc = (float) (PI * r * 2 / 6);
        ml = (float) (l - (r * tan30 * 2));
        c = (float) (PI * r * 2 + 6 * ml);

        progressParts[0] = ((mc / 2) / c) * 100;


        for (int i = 1; i < 12; i++) {
            if (i % 2 == 1) {
                progressParts[i] = progressParts[i - 1] + ml / c * 100;
            } else if (i % 2 == 0) {
                progressParts[i] = progressParts[i - 1] + mc / c * 100;
            }
        }
        progressParts[12] = 100;

    }

    /**
     * 初始化各个点
     */
    private void initPoints() {
        linePoints = new PointView[12];
        linePoints[0] = new PointView((float) (l * cos30 - r * tan30 * cos30 + r),
                (float) (r * tan30 * sin30 + mProgressWidth));
        linePoints[1] = new PointView((float) (l * cos30 * 2 - r * tan30 * cos30 - mProgressWidth),
                (float) (l * sin30 - r * tan30 * sin30));
        linePoints[2] = new PointView((float) (l * cos30 * 2 - mProgressWidth),
                (float) (l * sin30 + r * tan30));
        linePoints[3] = new PointView((float) (l * cos30 * 2 - mProgressWidth),
                (float) (l * sin30 + l - r * tan30));
        linePoints[4] = new PointView((float) (l * cos30 * 2 - r * tan30 * cos30 - mProgressWidth),
                (float) (l * sin30 + l + r * tan30 * sin30));
        linePoints[5] = new PointView((float) (l * cos30 + r * tan30 * cos30),
                (float) (l * sin30 * 2 + l - r * tan30 * sin30 - mProgressWidth));
        linePoints[6] = new PointView((float) (l * cos30 - r * tan30 * cos30),
                (float) (l * sin30 * 2 + l - r * tan30 * sin30 - mProgressWidth));
        linePoints[7] = new PointView((float) (r * sin30 + mProgressWidth),
                (float) (l * sin30 + l + r * tan30 * sin30));
        linePoints[8] = new PointView(0 + mProgressWidth,
                (float) (l * sin30 + l - r * tan30));
        linePoints[9] = new PointView(0 + mProgressWidth,
                (float) (l * sin30 + r * tan30));
        linePoints[10] = new PointView((float) (r * sin30 + mProgressWidth),
                (float) (l * sin30 - r * tan30 * sin30));
        linePoints[11] = new PointView((float) (l * cos30 - r * tan30 * cos30),
                (float) (r * tan30 * sin30 + mProgressWidth));

        arcPoints = new PointView[12];
        arcPoints[0] = new PointView((float) (l * cos30 - r),
                (float) (r / cos30 - r + mProgressWidth));
        arcPoints[1] = new PointView((float) (l * cos30 + r),
                (float) (r / cos30 - r + 2 * r + mProgressWidth));
        arcPoints[2] = new PointView((float) (l * cos30 * 2 - r * 2 - mProgressWidth),
                (float) (l * sin30 + r * tan30 - r));
        arcPoints[3] = new PointView((float) (l * cos30 * 2 - mProgressWidth),
                (float) (l * sin30 + r * tan30 + r));
        arcPoints[4] = new PointView((float) (l * cos30 * 2 - r * 2 - mProgressWidth),
                (float) (l * sin30 + l - r * tan30 - r));
        arcPoints[5] = new PointView((float) (l * cos30 * 2 - mProgressWidth),
                (float) (l * sin30 + l - r * tan30 + r));
        arcPoints[6] = new PointView((float) (l * cos30 - r),
                (float) (l * sin30 * 2 + l - r / cos30 - r - mProgressWidth));
        arcPoints[7] = new PointView((float) (l * cos30 + r),
                (float) (l * sin30 * 2 + l - r / cos30 + r - mProgressWidth));
        arcPoints[8] = new PointView(0 + mProgressWidth,
                (float) (l * sin30 + l - r * tan30 - r));
        arcPoints[9] = new PointView(r * 2 + mProgressWidth,
                (float) (l * sin30 + l - r * tan30 + r));
        arcPoints[10] = new PointView(0 + mProgressWidth,
                (float) (l * sin30 + r * tan30 - r));
        arcPoints[11] = new PointView(r * 2 + mProgressWidth,
                (float) (l * sin30 + r * tan30 + r));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        drawUnfinishedProgress(canvas);
        drawProgress(canvas);

    }

    /**
     * 画未完成的进度条
     */
    private void drawUnfinishedProgress(Canvas canvas) {

        for (int i = 0; i < 6; i++) {
            RectF rectF = new RectF(arcPoints[i * 2].x, arcPoints[i * 2].y,
                    arcPoints[i * 2 + 1].x, arcPoints[i * 2 + 1].y);
            canvas.drawArc(rectF, -120 + i * 60, 60, false, mUnfinishedPaint);
            canvas.drawLine(linePoints[i * 2].x, linePoints[i * 2].y,
                    linePoints[i * 2 + 1].x, linePoints[i * 2 + 1].y, mUnfinishedPaint);
        }
    }

    /**
     * 画背景
     */
    private void drawBg(Canvas canvas) {
        Path path = new Path();
        path.moveTo(linePoints[0].x, linePoints[0].y);
        for (int i = 1; i < 12; i++) {
            path.lineTo(linePoints[i].x, linePoints[i].y);
        }
        path.close();
        canvas.drawPath(path, mBgPaint);

        for (int i = 0; i < 6; i++) {
            RectF rectF = new RectF(arcPoints[i * 2].x, arcPoints[i * 2].y,
                    arcPoints[i * 2 + 1].x, arcPoints[i * 2 + 1].y);
            canvas.drawArc(rectF, -120 + i * 60, 60, true, mBgPaint);
        }
    }

    /**
     * 画进度条
     */
    private void drawProgress(Canvas canvas) {
        if (mProgress > 0 && mProgress <= progressParts[0]) {
            RectF rectFOne = new RectF(arcPoints[0].x, arcPoints[0].y,
                    arcPoints[1].x, arcPoints[1].y);
            float oval = mProgress / progressParts[0] * 30;
            canvas.drawArc(rectFOne, -90, oval, false, mFinishedPaint);
        }

        for (int i = 0; i < 11; i++) {
            if (mProgress > progressParts[i] && mProgress <= progressParts[i + 1]) {
                drawTrail(canvas, i + 1);
                float percent = (mProgress - progressParts[i]) / (progressParts[i + 1] - progressParts[i]);
                if (i % 2 == 0) {
                    canvas.drawLine(linePoints[i].x, linePoints[i].y,
                            linePoints[i].x + ((linePoints[i + 1].x - linePoints[i].x) * percent),
                            linePoints[i].y + ((linePoints[i + 1].y - linePoints[i].y) * percent), mFinishedPaint);
                } else if (i % 2 == 1) {

                    RectF rectF = new RectF(arcPoints[i + 1].x, arcPoints[i + 1].y,
                            arcPoints[i + 2].x, arcPoints[i + 2].y);
                    float oval = percent * 60;
                    canvas.drawArc(rectF, -120 + (i + 1) * 30, oval, false, mFinishedPaint);
                }
            }
        }

        if (mProgress > progressParts[11] && mProgress <= 100) {
            drawTrail(canvas, 12);
            float percent = (mProgress - progressParts[11]) / (100 - progressParts[11]);
            RectF rectFOne = new RectF(arcPoints[0].x, arcPoints[0].y,
                    arcPoints[1].x, arcPoints[1].y);
            float oval = percent * 30;
            canvas.drawArc(rectFOne, -120, oval, false, mFinishedPaint);
        }
    }

    /**
     * 绘制已完成的轨迹
     *
     * @param index 所处区间的上一个区间的索引
     */
    private void drawTrail(Canvas canvas, int index) {
        if (index < 0 || index > 13) {
            throw new IllegalArgumentException("the index must be   less than 14 and more than 0");
        }

        for (int i = 0; i < index; i++) {
            if (i == 0) {
                RectF rectF = new RectF(arcPoints[0].x, arcPoints[0].y,
                        arcPoints[1].x, arcPoints[1].y);
                canvas.drawArc(rectF, -90, 30, false, mFinishedPaint);
            } else if (i % 2 == 1) {
                canvas.drawLine(linePoints[i - 1].x, linePoints[i - 1].y,
                        linePoints[i].x, linePoints[i].y, mFinishedPaint);
            } else if (i != 0 && i != 12 && i % 2 == 0) {
                RectF rectF = new RectF(arcPoints[i].x, arcPoints[i].y,
                        arcPoints[i + 1].x, arcPoints[i + 1].y);
                canvas.drawArc(rectF, -120 + i * 30, 60, false, mFinishedPaint);
            } else if (i == 12) {
                RectF rectF = new RectF(arcPoints[0].x, arcPoints[0].y,
                        arcPoints[1].x, arcPoints[1].y);
                canvas.drawArc(rectF, -120, 30, false, mFinishedPaint);
            }

        }
    }

    /**
     * 将dp值转换成px
     *
     * @param dipValue 对应的dip的值
     */
    protected float dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return scale * dipValue;
    }

    class PointView {
        public float x;
        public float y;

        public PointView() {
        }

        public PointView(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }


}