package com.example.alex.cbp;

import java.util.HashMap;


public class StaticTestModel {
    HashMap<String, Integer> PointsMap = new HashMap<String, Integer>();
    public void RunStaticTests(int _CameraNum) {
        CameraModel mCameraModel = new CameraModel(_CameraNum);
        String BackCam = mCameraModel.cameraAllParams;
        String ParBuffer;
        Integer BufferPoints = 0;


        ParBuffer = FindParam("iso-speed-values",BackCam);
        if (ParBuffer.equals("None"))
        {
            ParBuffer = FindParam("iso-values",BackCam);
        }
        if (ParBuffer.equals("None"))
        {
            ParBuffer = FindParam("iso-mode-values",BackCam);
        }


        for (int i = 1; i < 100; i++)
        {
            int ISO = 100*i;
            if ( (ParBuffer.contains(String.valueOf(ISO)+","))|| (ParBuffer.contains(String.valueOf(ISO)+";")) )
            {
                BufferPoints +=  ISO;
            }
        }
        PointsMap.put("iso-(speed)-values",BufferPoints);

        //focal-length
        ParBuffer = FindParam("focal-length",BackCam);
        ParBuffer = ParBuffer.split(";")[0];
        Double FocalBuf = Double.parseDouble(ParBuffer);
        BufferPoints = FocalBuf.intValue()*1000;
        PointsMap.put("focal-length",BufferPoints);

        //histogram-values
        ParBuffer = FindParam("histogram-values",BackCam);
        if (ParBuffer.contains("enable") )
        {
            BufferPoints = 100;
        }
        else
        {
            BufferPoints = 0;
        }
        PointsMap.put("histogram-values",BufferPoints);
        //jpeg-quality
        //whitebalance-values
        BufferPoints =0;
        ParBuffer = FindParam("whitebalance-values",BackCam);
        if (ParBuffer.contains("auto") )
        {
            BufferPoints += 75;
        }

        if (ParBuffer.contains("cloudy-daylight") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("daylight") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("fluorescent") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("incandescent") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("shade") )
        {
            BufferPoints += 60;
        }

        if (ParBuffer.contains("twilight") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("warm-fluorescent") )
        {
            BufferPoints += 50;
        }

        PointsMap.put("whitebalance-values",BufferPoints);


        //lensshade-values
        ParBuffer = FindParam("lensshade-values",BackCam);
        if (ParBuffer.contains("enable") )
        {
            BufferPoints = 150;
        }
        else
        {
            BufferPoints = 0;
        }
        PointsMap.put("lensshade-values",BufferPoints);

        //scene-mode-values
        BufferPoints = 0;
        ParBuffer = FindParam("scene-mode-values",BackCam);
        if (ParBuffer.contains("off") )
        {
            BufferPoints += 20;
        }

        if (ParBuffer.contains("auto") )
        {
            BufferPoints += 70;
        }

        if (ParBuffer.contains("action") )
        {
            BufferPoints += 25;
        }

        if (ParBuffer.contains("portrait") )
        {
            BufferPoints += 25;
        }

        if (ParBuffer.contains("landscape") )
        {
            BufferPoints += 25;
        }

        if (ParBuffer.contains("night") )
        {
            BufferPoints += 75;
        }

        if (ParBuffer.contains("night-portrait") )
        {
            BufferPoints += 75;
        }

        if (ParBuffer.contains("theatre") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("beach") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("snow") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("sunset") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("warm-fluorescent") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("twilight") )
        {
            BufferPoints += 50;
        }


        if (ParBuffer.contains("shade") )
        {
            BufferPoints += 50;
        }


        if (ParBuffer.contains("incandescent") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("fluorescent") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("daylight") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("cloudy-daylight") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("steadyphoto") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("sports") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("hdr") )
        {
            BufferPoints += 75;
        }

        if (ParBuffer.contains("fireworks") )
        {
            BufferPoints += 55;
        }

        if (ParBuffer.contains("candlelight") )
        {
            BufferPoints += 50;
        }

        if (ParBuffer.contains("barcode") )
        {
            BufferPoints += 50;
        }

        PointsMap.put("scene-mode-values",BufferPoints);

        //flash-mode-values  off,auto,on,red-eye,torch
        BufferPoints = 0;
        ParBuffer = FindParam("flash-mode-values",BackCam);
        if (ParBuffer.contains("off") )
        {
            BufferPoints += 25;
        }

        if (ParBuffer.contains("auto") )
        {
            BufferPoints += 40;
        }

        if (ParBuffer.contains("on") )
        {
            BufferPoints += 40;
        }

        if (ParBuffer.contains("red-eye") )
        {
            BufferPoints += 75;
        }

        if (ParBuffer.contains("torch") )
        {
            BufferPoints += 25;
        }
        PointsMap.put("flash-mode-values",BufferPoints);

        //auto-exposure-values  auto-exposure-values=frame-average,center-weighted,spot-metering,smart-metering;
        //picture-format-values picture-format-values=jpeg,raw;
        ParBuffer = FindParam("picture-format-values",BackCam);
        if (ParBuffer.contains("raw") )
        {
            BufferPoints = 75;
        }
        else
        {
            BufferPoints = 0;
        }
        PointsMap.put("picture-format-values",BufferPoints);
        //focus-mode-values  focus-mode-values=auto,infinity,fixed,macro,continuous-video,continuous-picture;
        //picture-size-values  picture-size-values=2688x1520,2592x1456,2048x1520,2048x1216,2048x1152,1920x1088,1600x1200,1600x896,1520x1520,1456x1088,1456x880,1456x832,1440x1088,1280x960,1280x768,1280x720,1024x768,1088x1088,800x600,800x480,720x720,640x480,640x384,640x368,352x288,320x240,176x144;
        ParBuffer = FindParam("picture-size-values",BackCam);
        ParBuffer = ParBuffer.split(",")[0];
        BufferPoints = ( Integer.valueOf(ParBuffer.split("x")[0]) * Integer.valueOf(ParBuffer.split("x")[1]) )/1000;

        PointsMap.put("picture-size-values",BufferPoints);

        //skinToneEnhancement-values enable,disable; 0
        ParBuffer = FindParam("skinToneEnhancement-values",BackCam);
        if (ParBuffer.contains("enable") )
        {
            BufferPoints = 35;
        }
        else
        {
            BufferPoints = 0;
        }
        PointsMap.put("skinToneEnhancement-values",BufferPoints);
        //effect-values  vivid,still-sky-blue,still-grass-green,still-skin-whiten-low,still-skin-whiten-medium,still-skin-whiten-high;
        BufferPoints = 0;
        ParBuffer = FindParam("scene-mode-values",BackCam);
        if (ParBuffer.contains("none") )
        {
            BufferPoints += 25;
        }

        if (ParBuffer.contains("AQUA") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("BLACKBOARD") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("MONO") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("NEGATIVE") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("POSTERIZE") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("SEPIA") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("SOLARIZE") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("WHITEBOARD") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("auto") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("asd") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("action") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("portrait") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("landscape") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("night") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("night-portrait") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("theatre") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("beach") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("snow") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("sunset") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("steadyphoto") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("fireworks") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("sports") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("party") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("candlelight") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("back-light") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("flowers") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("AR") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("text") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("fall-color") )
        {
            BufferPoints += 45;
        }

        if (ParBuffer.contains("dusk-dawn") )
        {
            BufferPoints += 45;
        }

        PointsMap.put("scene-mode-values",BufferPoints);


        //antibanding-values
        BufferPoints = 0;
        ParBuffer = FindParam("antibanding-values",BackCam);
        if (ParBuffer.contains("off") )
        {
            BufferPoints += 10;
        }

        if (ParBuffer.contains("50") )
        {
            BufferPoints += 30;
        }

        if (ParBuffer.contains("60") )
        {
            BufferPoints += 30;
        }

        if (ParBuffer.contains("auto") )
        {
            BufferPoints += 75;
        }

        PointsMap.put("antibanding-values",BufferPoints);

    }

    public String FindParam( String _headline, String _mainString) {
        if ( !_mainString.contains(_headline) )
        {
            return "None";
        }
        String[] Buf_1 = _mainString.split(_headline + "=");
        String[] Buf_2 = Buf_1[1].split(";");
        return Buf_2[0] + ";";
    }

    public int GetFinalPoints() {
        //Final Points
        Integer FinalPoints = 0;
        for (HashMap.Entry<String, Integer> entry: PointsMap.entrySet())
        {
            FinalPoints += entry.getValue();
        }
        return  FinalPoints;
    }

    public String GetFinalPointsLog() {
        //Final Points
        String FinalPointsLog = "Log: \n";

        for (HashMap.Entry<String, Integer> entry: PointsMap.entrySet())
        {
            FinalPointsLog += entry.getKey();
            FinalPointsLog += (" = ");
            FinalPointsLog += entry.getValue().toString();
            FinalPointsLog += "\n";
        }
        return FinalPointsLog;
    }
}
