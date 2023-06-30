package com.busanit.ex12_paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class PaintBoard extends View {
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private int lastX;
    private int lastY;

    public PaintBoard(Context context) {
        super(context);
        init(context);
    }

    public PaintBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3.0f);
        lastX = -1;
        lastY = -1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Bitmap img = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.WHITE);
        mBitmap = img;
        mCanvas = canvas;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap!=null){
            canvas.drawBitmap(mBitmap, 0,0,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_UP:
                lastX = -1;
                lastY = -1;
                break;
                case MotionEvent.ACTION_DOWN:
                    if (lastX != -1) {
                        if (X != lastX || Y != lastY){
                            mCanvas.drawLine(lastX,lastY,X,Y,mPaint);
                        }
                    }
                    lastX = X;
                    lastY = Y;
                    break;
                    case MotionEvent.ACTION_MOVE:
                        if (lastX!=-1){
                            mCanvas.drawLine(lastX,lastY,X,Y,mPaint);
                        }
                        lastX = X;
                        lastY = Y;
                        break;
        }
        invalidate();
        return true;
    }
}
