package com.grupoeng.nyquistplotter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidplot.xy.BoundaryMode;

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

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.zoom_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText maxim = (EditText) promptsView
                .findViewById(R.id.etmaxim);
        final EditText minim = (EditText) promptsView
                .findViewById(R.id.etminim);
        final EditText maxre = (EditText) promptsView
                .findViewById(R.id.etmaxre);
        final EditText minre = (EditText) promptsView
                .findViewById(R.id.etminre);



        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                if(!maxim.getText().toString().isEmpty()&&!maxre.getText().toString().isEmpty()&&
                                        !minim.getText().toString().isEmpty()&&!minre.getText().toString().isEmpty()){
                                    double mini=Double.valueOf(minim.getText().toString());
                                    double minr=Double.valueOf(minre.getText().toString());
                                    double maxi=Double.valueOf(maxim.getText().toString());
                                    double maxr=Double.valueOf(maxre.getText().toString());
                                    Intent myIntent = new Intent(EntradaDePolinomio.this, TelaGrafico.class);
                                    myIntent.putExtra("numerador", ((PolinomioAdapter)numerador.getAdapter()).polinomio); //Optional parameters
                                    myIntent.putExtra("denominador", ((PolinomioAdapter)denominador.getAdapter()).polinomio);
                                    myIntent.putExtra("mini",mini);
                                    myIntent.putExtra("minr",minr);
                                    myIntent.putExtra("maxi",maxi);
                                    myIntent.putExtra("maxr",maxr);
                                    startActivity(myIntent);

                                }
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();



    }
}
