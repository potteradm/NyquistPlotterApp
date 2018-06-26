package com.grupoeng.nyquistplotter;

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

public class telaPrincipal extends AppCompatActivity {

        GraphView graph;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tela_principal);
            ArrayList<Complex> numerador = new ArrayList<>();
            ArrayList<Complex>denominador=new ArrayList<>();

            numerador.add(new Complex(1));//s^0
            numerador.add(new Complex(0));//s^1

            denominador.add(new Complex(0));//s^0
            denominador.add(new Complex(1));//s^1
            denominador.add(new Complex(0));//s^2

            numerador.add(new Complex(0));//adiciona extra 0 para remover
            denominador.add(new Complex(0));
            denominador.add(new Complex(0));
            denominador.add(new Complex(0));
            NyquistScatter scatter = new NyquistScatter(numerador, denominador);
            scatter.calcScatter();


            // initialize our XYPlot reference:
            XYPlot plot = (XYPlot) findViewById(R.id.plot);

            // create a couple arrays of y-values to plot:
            final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14};
            Number[] series1Numbers = {1, 4, 2, 8, 4, 16, 8, 32, 16, 64};
            Number[] series2Numbers = {5, 2, 10, 5, 20, 10, 40, 20, 80, 40};

            // turn the above arrays into XYSeries':
            // (Y_VALS_ONLY means use the element index as the x value)
//            XYSeries series1 = new SimpleXYSeries(
//                    Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");

            XYSeries series1 = new SimpleXYSeries(
                   scatter.getPontosRE(), scatter.getPontosIM(), "Series1");

            XYSeries series2 = new SimpleXYSeries(
                    scatter.getPontosRE(), scatter.getPontosIMConj(), "Series2");




            // create formatters to use for drawing a series using LineAndPointRenderer
            // and configure them from xml:
            LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.YELLOW, Color.GREEN, Color.TRANSPARENT, null);


//            LineAndPointFormatter series12Format = new LineAndPointFormatter(Color.YELLOW, Color.GREEN, Color.TRANSPARENT, null);

            // add an "dash" effect to the series2 line:
//            series12Format.getLinePaint().setPathEffect(new DashPathEffect(new float[] {

                    // always use DP when specifying pixel sizes, to keep things consistent across devices:
//                    PixelUtils.dpToPix(20),
//                    PixelUtils.dpToPix(15)}, 0));

            // just for fun, add some smoothing to the lines:
            // see: http://androidplot.com/smooth-curves-and-androidplot/
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