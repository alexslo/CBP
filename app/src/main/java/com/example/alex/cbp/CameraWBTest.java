package com.example.alex.cbp;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CameraWBTest extends Activity implements SurfaceHolder.Callback, View.OnClickListener
{
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView preview;
    private Button shotBtn;

    private int pictureCounter = 0;
    public static final String saveFolderPatch = "/sdcard/CBP/";
    public static final String testPictureName = "WB_TEST_PHOTO";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Портретная ориентация

        // Полноэкранность
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activiti_camera_wb_test);

        preview = (SurfaceView) findViewById(R.id.surfaceView);

        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
        // Для старых API
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        shotBtn = (Button) findViewById(R.id.shot_button);
        shotBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (camera != null)
        {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        try
        {
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(previewCallbacknew);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Size previewSize = camera.getParameters().getPreviewSize();
        float aspect = (float) previewSize.width / previewSize.height;

        int previewSurfaceWidth = preview.getWidth();
        int previewSurfaceHeight = preview.getHeight();

        LayoutParams lp = preview.getLayoutParams();

        // корректируем размер отображаемого preview, чтобы не было искажений
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
        {
            // портретный вид
            camera.setDisplayOrientation(90);
            lp.height = previewSurfaceHeight;
            lp.width = (int) (previewSurfaceHeight / aspect);

        }
        else
        {
            // ландшафтный
            camera.setDisplayOrientation(0);
            lp.width = previewSurfaceWidth;
            lp.height = (int) (previewSurfaceWidth / aspect);
        }

        preview.setLayoutParams(lp);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
    }

    @Override
    public void onClick(View v)
    {
        if (v == shotBtn) {
            pictureCounter = 0;
            camera.autoFocus(autoFocusCallback);
            camera.takePicture(null, null, null, jpegCallback);
        }
    }

   PictureCallback jpegCallback = new PictureCallback() {
       public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
       {
           // сохраняем полученные jpg в папке /sdcard/CameraExample/
           // имя файла - System.currentTimeMillis()

           try
           {
               File saveDir = new File(saveFolderPatch);

               if (!saveDir.exists())
               {
                   saveDir.mkdirs();
               }

               FileOutputStream os = new FileOutputStream(saveFolderPatch + testPictureName + pictureCounter + ".jpg");
               os.write(paramArrayOfByte);
               os.close();
           }
           catch (Exception e)
           {
           }

           // после того, как снимок сделан, показ превью отключается. необходимо включить его
           paramCamera.startPreview();
           // делаем по 3 фото за раз
           pictureCounter++;
           if (pictureCounter < 3)
           {
               camera.cancelAutoFocus();
               camera.autoFocus(autoFocusCallback);
               camera.takePicture(null, null, null, jpegCallback);
           }
           if (pictureCounter == 3)
           {
               Intent intent = new Intent(CameraWBTest.this, WBTestResult.class);
               startActivity(intent);
           }
       }

    };

    AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
        public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
        {
            /*
            if (paramBoolean)
            {
                // если удалось сфокусироваться, делаем снимок
                //paramCamera.takePicture(null, null, null, this);
            }
            */
        }

    };

    PreviewCallback previewCallbacknew = new PreviewCallback() {
        public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
        {
            // здесь можно обрабатывать изображение, показываемое в preview
        }

    };

    public int calculateTestResult() {

        return 0;
    }
}

