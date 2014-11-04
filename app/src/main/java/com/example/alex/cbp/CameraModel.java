package com.example.alex.cbp;

import android.hardware.Camera;

/**
 * Created by alex on 04.11.2014.
 */
public class CameraModel {
    public String cameraAllParams;
    /**
     *  1 - BACK Camera will be used
     *  2 - FRONT Camera will be used
     */
    public CameraModel (int _camNum) {
        cameraAllParams = getAllCameraParams(_camNum);
    }

    public double getCameraMP() {
        String allMP = FindParam("picture-size-values",cameraAllParams);
        String maxMP[] = allMP.split(",")[0].split("x");
        Integer allPixelsR =  Integer.parseInt(maxMP[0])*Integer.parseInt(maxMP[1])/10000;
        double allMegaPixel = (double) allPixelsR/100;
        return allMegaPixel;
    }

    public String getCameraFocusSize() {
        return FindParam("focal-length",cameraAllParams);
    }

    public String getAllCameraParams(int CurrentCamera ) {

        if (CurrentCamera > Camera.getNumberOfCameras())
        {
            CurrentCamera = -1;
            return "Devices not have this cameras number";
        }

        if (CurrentCamera ==1)
        {
            CurrentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        else if(CurrentCamera ==2)
        {
            CurrentCamera = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else
        {
            CurrentCamera = -1;
            return "Not Current Camera.CameraInfo Number ";
        }
        int cameraCount = 0;
        Camera mCamera = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for ( int camIdx = 0; camIdx < Camera.getNumberOfCameras(); camIdx++ ) {
            Camera.getCameraInfo( camIdx, cameraInfo );
            if ( cameraInfo.facing == CurrentCamera  ) {
                try {
                    mCamera = Camera.open( camIdx );
                } catch (RuntimeException e) {
                    //Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                    return "Camera.open error: " + e.getLocalizedMessage();
                }
            }
        }

        Camera.Parameters CamParams = mCamera.getParameters();
        String AllCamParams= CamParams.flatten();
        mCamera.release();
        return AllCamParams;

    }

    public String FindParam( String _headline, String _mainString) {
        if ( !_mainString.contains(_headline) )
        {
            return "None";
        }
        String[] Buf_1 = _mainString.split(_headline + "=");
        String[] Buf_2 = Buf_1[1].split(";");
        return Buf_2[0];
    }
}
