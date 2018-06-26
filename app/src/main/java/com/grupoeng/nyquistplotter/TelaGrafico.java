package com.grupoeng.nyquistplotter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.FastLineAndPointRenderer;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.apache.commons.math3.complex.Complex;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class TelaGrafico extends AppCompatActivity {

        GraphView graph;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tela_principal);
            ArrayList<Complex> numerador = new ArrayList<>();
            ArrayList<Complex>denominador=new ArrayList<>();
            Intent intent = getIntent();

            numerador= (ArrayList<Complex>)intent.getSerializableExtra("numerador");
            denominador= (ArrayList<Complex>)intent.getSerializableExtra("denominador");
            NyquistScatter scatter = new NyquistScatter(numerador, denominador);
            scatter.calcScatter();


            // initialize our XYPlot reference:
            XYPlot plot = (XYPlot) findViewById(R.id.plot);


            XYSeries series1 = new SimpleXYSeries(
                   scatter.getPontosRE(), scatter.getPontosIM(), "Series1");

            XYSeries series2 = new SimpleXYSeries(
                    scatter.getPontosRE(), scatter.getPontosIMConj(), "Series2");


            LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.YELLOW, Color.GREEN, Color.TRANSPARENT, null);

            series1Format.setInterpolationParams(
                    new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

//            series12Format.setInterpolationParams(
//                    new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

            // add a new series' to the xyplot:
            plot.addSeries(series1, series1Format);
            plot.addSeries(series2, series1Format);

//            plot.addSeries(series2, series12Format);



        }
}