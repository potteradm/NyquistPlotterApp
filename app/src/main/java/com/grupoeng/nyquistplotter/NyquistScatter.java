package com.grupoeng.nyquistplotter;

import com.jjoe64.graphview.series.DataPoint;

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;

public class NyquistScatter {
    private ArrayList<Complex> numerador;
    private ArrayList<Complex> denominador;
    private ArrayList<Number> pontosRE;
    private ArrayList<Number> pontosIM;
    private ArrayList<Number> pontosIMConj;

    NyquistScatter(ArrayList<Complex> numerador, ArrayList<Complex> denominador){
        while(numerador.get(numerador.size()-1).abs()==0)numerador.remove(numerador.size()-1);
        this.numerador=numerador;
        while(denominador.get(denominador.size()-1).abs()==0)denominador.remove(denominador.size()-1);
        this.denominador=denominador;
    }

    public ArrayList<Number> getPontosIMConj() {
        return pontosIMConj;
    }

    public int calcScatter(){
        pontosRE=new ArrayList<>();
        pontosIM=new ArrayList<>();
        pontosIMConj=new ArrayList<>();

        int counter=0;
        for(double i=0;i<10;i+=0.01){//100 samples
            Complex aux=function(i);
            if(i==0&aux.isNaN()){
                aux=function(i+0.0000001);
                pontosRE.add(aux.getReal());
                pontosIM.add(aux.getImaginary());
                pontosIMConj.add(-aux.getImaginary());
                aux=function(i-0.0000001);
                pontosRE.add(aux.getReal());
                pontosIM.add(aux.getImaginary());
                pontosIMConj.add(-aux.getImaginary());
            }
            pontosRE.add(aux.getReal());
            pontosIM.add(aux.getImaginary());
            pontosIMConj.add(-aux.getImaginary());
            counter++;
        }
        for(int i=10;i<1000;i++){//990 samples
            Complex aux=function(i);
            pontosRE.add(aux.getReal());
            pontosIM.add(aux.getImaginary());
            pontosIMConj.add(-aux.getImaginary());
            counter++;
        }
        for(int i=1000;i<100000;i+=10){//990 samples
            Complex aux=function(i);
            pontosRE.add(aux.getReal());
            pontosIM.add(aux.getImaginary());
            pontosIMConj.add(-aux.getImaginary());
            counter++;
        }

        Complex aux=function(Double.POSITIVE_INFINITY);//1 sample
        pontosRE.add(aux.getReal());
        pontosIM.add(aux.getImaginary());
        pontosIMConj.add(-aux.getImaginary());
        counter++;
        return counter;
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


    public ArrayList<Number> getPontosRE() {
        return pontosRE;
    }

    public ArrayList<Number> getPontosIM() {
        return pontosIM;
    }


    public ArrayList<DataPoint> getDataPoints(){
        ArrayList<DataPoint> dp=new ArrayList<>(pontosIM.size());
        for(int i=0;i<pontosIM.size();i++){
            dp.add(new DataPoint(pontosRE.get(i).doubleValue(),pontosIM.get(i).doubleValue()));
        }
        return dp;
    }
}
