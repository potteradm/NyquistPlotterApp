package com.grupoeng.nyquistplotter;

import com.jjoe64.graphview.series.DataPoint;

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;

public class NyquistScatter {
    private ArrayList<Complex> numerador;
    private ArrayList<Complex> denominador;
    NyquistScatter(ArrayList<Complex> numerador, ArrayList<Complex> denominador){
        while(numerador.get(numerador.size()-1).abs()==0)numerador.remove(numerador.size()-1);
        this.numerador=numerador;
        while(denominador.get(denominador.size()-1).abs()==0)denominador.remove(denominador.size()-1);
        this.denominador=denominador;
    }

    public ArrayList<DataPoint> getScatter(){
        ArrayList<DataPoint> dataPoints=new ArrayList<>();
        for(double i=0;i<1;i+=0.01){//100 samples
            Complex aux=function(i);
            dataPoints.add(new DataPoint(aux.getReal(),aux.getImaginary()));
        }
        for(int i=1;i<100000;i++){//99999 samples
            Complex aux=function(i);
            dataPoints.add(new DataPoint(aux.getReal(),aux.getImaginary()));
        }
        Complex aux=function(Double.POSITIVE_INFINITY);//1 sample
        dataPoints.add(new DataPoint(aux.getReal(),aux.getImaginary()));
        return dataPoints;
    }

    private Complex function(double w){
        Complex resp;
        Complex jw=new Complex(0,w);
        Complex acumuladorNumerador=new Complex(0,0);
        for (int i = 0; i < numerador.size(); i++) {
            Complex aux;
            aux= numerador.get(i).multiply(pow(jw,i));
            acumuladorNumerador=acumuladorNumerador.add(aux);
        }


        Complex acumuladorDenominador=new Complex(0,0);
        for (int i = 0; i < denominador.size(); i++) {
            Complex aux;
                aux= denominador.get(i).multiply(pow(jw,i));

            acumuladorDenominador=acumuladorDenominador.add(aux);
        }

        if(acumuladorDenominador.isInfinite()){
            if(acumuladorNumerador.isInfinite()){
                if(numerador.size()>denominador.size()){
                    System.out.println("caso 1: numerador de grau maior que denominador");
                    return pow(Complex.INF,numerador.size()-1);
                }
                else if(numerador.size()<denominador.size()){
                    System.out.println("caso 2: denominador de grau maior");
                    return new Complex(0);
                }
                else {
                    System.out.println("caso 3: mesmo grau");
                    return numerador.get(numerador.size()-1).divide(denominador.get(denominador.size()-1));
                }
            }
            else {
                System.out.println("caso 4: somente denominador é infinito");
                return Complex.ZERO;
            }
        }
        else {
            resp=acumuladorNumerador.divide(acumuladorDenominador);
            return resp;
        }
    }


    private Complex pow(Complex v, double val){
        if(val==0)return new Complex(1);
        else if(v.isInfinite())return Complex.INF;
        else if(v.abs()==0)return new Complex(0);
        else return v.pow(val);
    }

    public int getGrauNumerador(){
        return numerador.size()-1;
    }
    public int getGrauDenominador(){
        return denominador.size()-1;
    }
}
