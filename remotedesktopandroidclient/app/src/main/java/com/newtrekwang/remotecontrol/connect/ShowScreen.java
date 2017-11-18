package com.newtrekwang.remotecontrol.connect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by You on 2017/10/31.
 */

public class ShowScreen extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Canvas mCanvas;
    Paint mPaint;
    private Rect picRect,screenRect;
    //定义屏幕宽高
    private int screenwidth;
    private int screenheight;
    //定义图片宽高
    int picwidth;
    int picheight;
    private boolean isDraw,isShow;
    //定义Bitmap和文件名
    private Bitmap mBitmap;
    private String mFilePath;
    public ShowScreen(Context context) {
        super(context);
        mInit();
    }
    public ShowScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInit();
        // mDrawThread=new DrawThread();
    }

    private void mInit()
    {
        mHolder=this.getHolder();
        mHolder.addCallback(this);
        mPaint=new Paint();
        //mBitmap= BitmapFactory.decodeFile("/storage/emulated/0/1.webp");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
            isDraw=true;
        new DrawThread().start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
            isDraw=false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    class DrawThread extends Thread
    {
        @Override
        public void run() {
            while(isDraw)
            {
                if(isShow) {

                    try {
                        picRect=new Rect(0,0,picwidth,picheight);
                        screenRect=new Rect(0,0,screenwidth,screenheight);
                        mBitmap = BitmapFactory.decodeFile(mFilePath);
                        mCanvas = mHolder.lockCanvas();
                        mCanvas.drawBitmap(mBitmap, picRect, screenRect, mPaint);
                    } catch (Exception e) {

                    } finally {
                        mHolder.unlockCanvasAndPost(mCanvas);
                        isShow=false;
                    }
                }
            }
        }
    }
    public void setStart(String get_path)
    {
        this.isShow=true;
        this.mFilePath=get_path;
    }
    //设置宽高
    public void setPicwidth(int pw)
    {
        this.picwidth=pw;
    }
    public void setPicheight(int ph)
    {
        this.picheight=ph;
    }
    public void setScreenwidth(int sw)
    {
        this.screenwidth=sw;
    }
    public void setScreenheight(int sh)
    {
        this.screenheight=sh;
    }

}
