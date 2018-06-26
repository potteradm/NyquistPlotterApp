package com.grupoeng.nyquistplotter;

import org.apache.commons.math3.complex.Complex;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private ArrayList<Complex> numerador;
    private ArrayList<Complex> denominador;
    private NyquistScatter scatter;
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void initPolynomial(){
        //polinimio    (3S+7)/(s^2+7s+1)

        numerador=new ArrayList<>();
        denominador=new ArrayList<>();

        numerador.add(new Complex(7));//s^0
        numerador.add(new Complex(3));//s^1

        denominador.add(new Complex(1));//s^0
        denominador.add(new Complex(7));//s^1
        denominador.add(new Complex(1));//s^2

        scatter=new NyquistScatter(numerador,denominador);
    }
    @Test
    public void polynomialResultForW0() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //se w igual a 0 o polinomio deve retornar 7
        Method method = scatter.getClass().getDeclaredMethod("function", double.class);
        method.setAccessible(true);
        Complex result= (Complex) method.invoke(scatter, 0);
        assertTrue(result.abs()==7);
    }

    @Test
    public void polynomialResultForWinfinite() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //polinimio    (3jw+7)/(jw^2+7jw+1)
        //se w igual a 0 o polinomio deve retornar 7
        Method method = scatter.getClass().getDeclaredMethod("function", double.class);
        method.setAccessible(true);
        Complex result= (Complex) method.invoke(scatter, Double.POSITIVE_INFINITY);
        assertTrue(result.abs()==0);
    }

    @Test
    public void polynomialResultForWNegativeInfinite() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //polinimio    (3jw+7)/(jw^2+7jw+1)
        //se w igual a 0 o polinomio deve retornar 7
        Method method = scatter.getClass().getDeclaredMethod("function", double.class);
        method.setAccessible(true);
        Complex result= (Complex) method.invoke(scatter, Double.NEGATIVE_INFINITY);
        System.out.println(result);
        assertTrue(result.abs()==0);
    }

    @Test
    public void testNumberOfSamples(){
        int samples=scatter.calcScatter();
        assertEquals("Valor diferenciado",11892,samples);
    }


    @Before
    public void initPolynomialExtra0(){
        //polinimio    (3S+7)/(s^2+7s+1)

        numerador=new ArrayList<>();
        denominador=new ArrayList<>();

        numerador.add(new Complex(7));//s^0
        numerador.add(new Complex(3));//s^1

        denominador.add(new Complex(1));//s^0
        denominador.add(new Complex(7));//s^1
        denominador.add(new Complex(1));//s^2

        numerador.add(new Complex(0));//adiciona extra 0 para remover
        denominador.add(new Complex(0));
        denominador.add(new Complex(0));
        denominador.add(new Complex(0));
        scatter=new NyquistScatter(numerador,denominador);
    }

    @Test
    public void testPolynomialDegree(){
        assertTrue(scatter.getGrauNumerador()==1&&scatter.getGrauDenominador()==2);
    }


    @Test
    public void testComplexDivision(){
        Complex a=new Complex(1);
        assertTrue(a.divide(new Complex(0)).isNaN());
    }





}