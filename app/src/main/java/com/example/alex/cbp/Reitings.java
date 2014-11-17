package com.example.alex.cbp;

import android.app.Activity;
import android.os.Bundle;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

public class Reitings extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reitings);

        // Линейный график
        /*
        GraphViewSeries exampleSeries = new GraphViewSeries(
                new GraphViewData[]{new GraphViewData(1, 600.0d),
                        new GraphViewData(2, 500.0d),
                        new GraphViewData(3, 400.0d),
                        new GraphViewData(4, 300.0d),
                        new GraphViewData(5, 100.0d)});
        */
        GraphView graphView = new BarGraphView(this, "Статический рейтинг устройств:");
        formCoordAxis(graphView);
        //graphView.addSeries(exampleSeries);
        //graphView.setHorizontalLabels(new String[]{"Samsung\nGalaxy S4", "LG G3", "Xiaomi MI3", "Sony \nXperia Z3", "Nexus 5"});
        //graphView.setVerticalLabels(new String[]{"600", "500", "400", "300", "200", "100", "0"});
        //graphView.setManualYAxisBounds(600, 0);

        LinearLayout layout = (LinearLayout) findViewById(R.id.ratingsL);
        layout.addView(graphView);


    }

    protected class DeviceRating {
        String deviceName;
        int devicePoints;

        DeviceRating(String deviceName, int devicePoints) {
            this.deviceName = deviceName;
            this.devicePoints = devicePoints;
        }
        public String getDeviceName() {return deviceName;}
        public int getDevicePoints() {return devicePoints;}
        public double getDevicePointsD() {return (double) devicePoints;}

    }

    private DeviceRating[] form4FakeDevRating() {
        DeviceRating[] deviceRating = new DeviceRating[4];
        deviceRating[0] = new DeviceRating("Galaxy S4",28713);
        deviceRating[1] = new DeviceRating("LG G2",26900);
        deviceRating[2] = new DeviceRating("Galaxy S5",35713);
        deviceRating[3] = new DeviceRating("Sony Z3",41500);
        return deviceRating;
    }

    private DeviceRating getCurrentDevRating() {
        String name = "You Device";
        StaticTestModel mStaticTestModel = new StaticTestModel();
        mStaticTestModel.RunStaticTests(1);
        int testPointBuf = mStaticTestModel.GetFinalPoints();
        mStaticTestModel.RunStaticTests(2);
        testPointBuf += mStaticTestModel.GetFinalPoints();
        return new DeviceRating(name,testPointBuf);
    }

    private void formCoordAxis (GraphView graphView) {
        ArrayList<DeviceRating> sortedDevices = new ArrayList <DeviceRating>();
        DeviceRating deviceRatingBuf = getCurrentDevRating();
        DeviceRating[] fakeDeviceRatings = form4FakeDevRating();
        sortedDevices.add(deviceRatingBuf);
        sortedDevices.addAll(Arrays.asList(fakeDeviceRatings).subList(0, 4));
        //Sorting
        Collections.sort(sortedDevices, new Comparator <DeviceRating>() {
            @Override
            public int compare(DeviceRating fruite1, DeviceRating fruite2) {
                return fruite1.getDevicePoints() - fruite2.getDevicePoints();
            }
        });

        GraphViewSeries exampleSeries = new GraphViewSeries(
                new GraphViewData[]{new GraphViewData(1, sortedDevices.get(4).getDevicePointsD()),
                        new GraphViewData(2, sortedDevices.get(3).getDevicePointsD()),
                        new GraphViewData(3, sortedDevices.get(2).getDevicePointsD()),
                        new GraphViewData(4, sortedDevices.get(1).getDevicePointsD()),
                        new GraphViewData(5, sortedDevices.get(0).getDevicePointsD())});


        graphView.addSeries(exampleSeries);
        graphView.setHorizontalLabels(new String[]{
                sortedDevices.get(4).deviceName,
                sortedDevices.get(3).deviceName,
                sortedDevices.get(2).deviceName,
                sortedDevices.get(1).deviceName,
                sortedDevices.get(0).deviceName});
        //graphView.setVerticalLabels(new String[]{"600", "500", "400", "300", "200", "100", "0"});
        graphView.setManualYAxisBounds(sortedDevices.get(4).getDevicePointsD() + 5000, 0);



    }

}
