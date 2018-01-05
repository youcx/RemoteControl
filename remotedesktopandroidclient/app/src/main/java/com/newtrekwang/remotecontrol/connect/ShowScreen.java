package com.newtrekwang.remotecontrol.connect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.newtrekwang.remotecontrol.CustomApplication;

import java.io.FileInputStream;

/**
 * Created by You on 2017/10/31.
 */

public class ShowScreen extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Canvas mCanvas;
    Paint mPaint;
    private Rect picRect,screenRect;
    Context mcontext;
    //定义屏幕宽高
    private int screenwidth;
    private int screenheight;
    //定义图片宽高
    int picwidth;
    int picheight;
    private boolean isDraw,isShow;
    //定义Bitmap和文件名
    //private Bitmap mBitmap;
    private String mFilePath;
    //定义pc坐标原点
    private PointF pcSPoint=new PointF();
    private boolean isPcSPMove;
    //pc坐标
    float pcx,pcy;
    //平移坐标偏移量
    private float dx,dy;
    //长按，双击
    private boolean isMoveM;
    //private boolean isLongPressM;
    private boolean isDoubleClickM;
    float downx,downy,movex,movey;
    //Time
    long down_time,up_time,last_down_time;

    // 縮放控制
    private Matrix matrix ;
    private Matrix savedMatrix = new Matrix();
    private float scale;
    // 不同状态的表示：
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    // 定义第一个按下的点，两只接触点的重点，以及出事的两指按下的距离：
    private PointF startPoint = new PointF();
    private PointF midPoint = new PointF();
    private float oriDis = 1f;

    public ShowScreen(Context context) {
        super(context);
        this.mcontext=context;
        mInit();
    }
    public ShowScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext=context;
        mInit();
        // mDrawThread=new DrawThread();
    }

    private void mInit()
    {
        mHolder=this.getHolder();
        mHolder.addCallback(this);
        mPaint=new Paint();
        matrix=new Matrix();
        scale=2f;
        matrix.setScale(scale,scale);
        this.isShow=false;
        //mBitmap= BitmapFactory.decodeFile("/storage/emulated/0/1.webp");
        pcSPoint.set(0f,0f);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                //缩放
                savedMatrix.set(matrix);
                startPoint.set(event.getX(), event.getY());
                mode = DRAG;
                //鼠标
                //获得触摸点坐标
                downx=event.getX();
                downy=event.getY();
                isMoveM=false;
                isDoubleClickM=false;
                down_time=event.getDownTime();
                if(down_time-last_down_time<300)
                {
                    //双击
                    isDoubleClickM=true;
                    Log.i("TAG","double click");
                    pcx=((startPoint.x-pcSPoint.x)/scale);
                    pcy=((startPoint.y-pcSPoint.y)/scale);
                    if(CustomApplication.getApplication().getSocket()!=null){
                        //触摸后发送触摸点坐标
                        //sendXY(x, y,(byte)4 ,CustomApplication.getApplication().getSocket());
                        SendXYThread send=new SendXYThread((int)pcx, (int)pcy,(byte)4 ,CustomApplication.getApplication().getSocket());
                        send.start();
                    }
                }
                last_down_time=event.getDownTime();

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //缩放相关
                oriDis = distance(event);
                if (oriDis > 10f) {
                    savedMatrix.set(matrix);
                    midPoint = middle(event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isPcSPMove) {
                    pcSPoint.offset(dx, dy);
                    Log.i("DX",dx+","+dy);
                    Log.w("pcSPoint", ":" + pcSPoint.x + "," + pcSPoint.y);
                    isPcSPMove=false;
                }
                up_time=event.getEventTime();
                //不属于移动，判断是长按还是点击
                if(!isMoveM)
                {
                    if(up_time-down_time>500)
                    {
                        //长按
                        Log.i("TAG","long press");
                        pcx=((startPoint.x-pcSPoint.x)/scale);
                        pcy=((startPoint.y-pcSPoint.y)/scale);
                        if(CustomApplication.getApplication().getSocket()!=null){
                            //触摸后发送触摸点坐标
                            //sendXY(x, y,(byte)3 ,CustomApplication.getApplication().getSocket());
                            SendXYThread send=new SendXYThread((int)pcx, (int)pcy,(byte)3 ,CustomApplication.getApplication().getSocket());
                            send.start();
                        }
                    }else{
                        //之前是否双击？
                        if(!isDoubleClickM)
                        {
                            //单击
                            Log.i("TAG","click");
                            pcx=((startPoint.x-pcSPoint.x)/scale);
                            pcy=((startPoint.y-pcSPoint.y)/scale);
                            Log.w("startPoint",startPoint.x+","+startPoint.y);
                            Log.w("pcSpoint",pcSPoint.x+","+pcSPoint.y);
                            Log.i("Point",pcx+","+pcy);
                            if(CustomApplication.getApplication().getSocket()!=null){
                                //触摸后发送触摸点坐标
                                //sendXY(x,y,(byte)2 ,CustomApplication.getApplication().getSocket());
                                SendXYThread send=new SendXYThread((int)pcx, (int)pcy,(byte)2 ,CustomApplication.getApplication().getSocket());
                                send.start();
                            }
                        }
                    }

                }
                isMoveM=false;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                //鼠标
                //获取移动点坐标
                movex=event.getX();
                movey=event.getY();
                if(Math.abs(movex-downx)>10f||Math.abs(movey-downy)>10f)
                {
                    //属于移动
                    isMoveM=true;
                    isShow=true;
                    //mode=DRAG;
                }
                //缩放相关
                if (mode == DRAG) {
                    // 是一个手指拖动
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                    //平移时更新PC坐标原点
                    dx=event.getX() - startPoint.x;
                    dy=event.getY() - startPoint.y;
                    isPcSPMove=true;
                } else if (mode == ZOOM) {
                    /*
                    // 两个手指滑动
                    float newDist = distance(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oriDis;
                        matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                    }
                    */
                }
                //缩放
                break;
        }

        return true;
    }

    class DrawThread extends Thread
    {
        @Override
        public void run() {
            while(isDraw)
            {
                if(isShow) {

                    try {
                        //picRect=new Rect(0,0,picwidth,picheight);
                        //screenRect=new Rect(0,0,screenwidth,screenheight);
                        //Log.i("tag",""+screenwidth+","+picwidth);
                        //Log.i("tag",""+screenheight+","+picheight);
                        //Log.i("tag",""+scalex+","+scaley);
                        //mMatrix.setScale(5,5);
                        FileInputStream fis=mcontext.openFileInput(mFilePath);
                        Bitmap mBitmap = BitmapFactory.decodeStream(fis);
                        mCanvas = mHolder.lockCanvas();
                        //清屏
                        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        //mCanvas.drawBitmap(mBitmap, picRect, screenRect, mPaint);
                        mCanvas.drawBitmap(mBitmap,matrix,mPaint);
                        mBitmap.recycle();
                    } catch (Exception e) {

                    } finally {
                        mHolder.unlockCanvasAndPost(mCanvas);
                        //屏幕移动时为确保流畅性，不断更新同一张位图
                        if(!isMoveM)
                        {
                            isShow=false;
                        }
                    }
                }
            }
        }
    }



    public void setmScale(float sscale)
    {
        this.scale=sscale;
        this.matrix.setScale(scale,scale);
        pcSPoint.set(0f,0f);
    }
    public void setStart(String get_path)
    {
        this.isShow=true;
        this.mFilePath=get_path;
    }
    // 计算两个触摸点之间的距离
    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 计算两个触摸点的中点
    private PointF middle(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
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
