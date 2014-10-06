package com.example.alex.cbp;

import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;

/**
 * Created by alex on 05.10.2014.
 */
public class CameraTestModel {

    public long runWhiteBalanceTest()
    {
        Log.d("mPicture.onPictureTaken","start runWhiteBalanceTest");
        /*
        int cameraId = 0;
        Camera.CameraInfo info = new Camera.CameraInfo();

        for (cameraId = 0; cameraId < Camera.getNumberOfCameras(); cameraId++) {
            Camera.getCameraInfo(1, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
                break;
        }

        mCamera = Camera.open(cameraId);
        */
        Camera mCamera = Camera.open(1);
        mCamera.setDisplayOrientation(90);
        mCamera.takePicture(null, null, mPicture);
        mCamera.release();
        return 0;
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException ex) {
                Log.d("mPicture.onPictureTaken","error when try to createImageFile()");
            }
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("mPicture.onPictureTaken","error: file not found");

            } catch (IOException e) {
                Log.d("mPicture.onPictureTaken","error: IO error");
            }
        }
    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
}
