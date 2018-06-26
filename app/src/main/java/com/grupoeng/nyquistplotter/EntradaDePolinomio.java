package com.grupoeng.nyquistplotter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;

public class EntradaDePolinomio extends AppCompatActivity {
    RecyclerView numerador;
    RecyclerView denominador;
    TextView viewDenominador;
    TextView viewNumerador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_de_polinomio);
        numerador=findViewById(R.id.numerador);
        denominador=findViewById(R.id.denominador);
        viewDenominador=findViewById(R.id.polinomioinferior);
        viewNumerador=findViewById(R.id.polinomiosuperior);



        numerador.setAdapter(new PolinomioAdapter(null,this,numerador));

        denominador.setAdapter(new PolinomioAdapter(null,this,denominador));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        numerador.setLayoutManager(layout);

        RecyclerView.LayoutManager layout2 = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        denominador.setLayoutManager(layout2);
    }


    public void AtualizaPolinomio(){
        PolinomioAdapter denoadapter= (PolinomioAdapter) denominador.getAdapter();
        PolinomioAdapter numeroadapter= (PolinomioAdapter) numerador.getAdapter();

        ArrayList<Complex> inf=denoadapter.polinomio;

        String infHTML="";
        for(int i=inf.size()-1;i>=0;i--){

            if(inf.get(i).getReal()>0)infHTML+="+ ";
            else if(inf.get(i).getReal()<0) infHTML+="- ";
            else continue;
            if(i>1){
                infHTML+=inf.get(i).getReal()+"S<sup>"+i+"</sup> ";
            }
            if(i==1)infHTML+=inf.get(i).getReal()+"S ";
            if(i==0)infHTML+=inf.get(i).getReal();
        }
        viewDenominador.setText(Html.fromHtml(infHTML));

        ArrayList<Complex> sup=numeroadapter.polinomio;

        String supHTML="";
        for(int i=sup.size()-1;i>=0;i--){

            if(sup.get(i).getReal()>0)supHTML+="+ ";
            else if(sup.get(i).getReal()<0) supHTML+="- ";
            else continue;
            if(i>1){
                supHTML+=sup.get(i).getReal()+"S<sup>"+i+"</sup> ";
            }
            if(i==1)supHTML+=sup.get(i).getReal()+"S ";
            if(i==0)supHTML+=sup.get(i).getReal();
        }
        viewNumerador.setText(Html.fromHtml(supHTML));



    }

    public void openActivity(View view) {
        Intent myIntent = new Intent(this, TelaGrafico.class);
        myIntent.putExtra("numerador", ((PolinomioAdapter)numerador.getAdapter()).polinomio); //Optional parameters
        myIntent.putExtra("denominador", ((PolinomioAdapter)denominador.getAdapter()).polinomio);
        startActivity(myIntent);


    }
}
