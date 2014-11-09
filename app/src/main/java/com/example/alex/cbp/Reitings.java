package com.example.alex.cbp;

import android.app.Activity;
import android.os.Bundle;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.widget.LinearLayout;

public class Reitings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reitings);

        // Линейный график
        GraphViewSeries exampleSeries = new GraphViewSeries(
                new GraphViewData[]{new GraphViewData(1, 600.0d),
                        new GraphViewData(2, 500.0d),
                        new GraphViewData(3, 400.0d),
                        new GraphViewData(4, 300.0d),
                        new GraphViewData(5, 100.0d)});

        GraphView graphView = new BarGraphView(this, "Рейтинг устройств:");
        graphView.addSeries(exampleSeries);
        graphView.setHorizontalLabels(new String[]{"Samsung", "LG", "Xaomi", "Sony", "Moto"});
        graphView.setVerticalLabels(new String[]{"600", "500", "400", "300", "200", "100", "0"});
        graphView.setManualYAxisBounds(600, 0);

        LinearLayout layout = (LinearLayout) findViewById(R.id.ratingsL);
        layout.addView(graphView);


    }
}
