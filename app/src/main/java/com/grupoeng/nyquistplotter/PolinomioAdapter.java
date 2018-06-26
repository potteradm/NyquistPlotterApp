package com.grupoeng.nyquistplotter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;

public class PolinomioAdapter extends RecyclerView.Adapter {
    public final ArrayList<Complex> polinomio;
    private Context context;
    private RecyclerView recyclerView;

    public PolinomioAdapter(ArrayList<Complex> polinomio, Context context,RecyclerView recyclerView) {
        this.polinomio = polinomio==null?new ArrayList<Complex>():polinomio;
        this.context = context;
        this.recyclerView=recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.itemconstantepolinomio, parent, false);
        PolinomioViewHolder holder = new PolinomioViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final PolinomioViewHolder aux=(PolinomioViewHolder)holder;
        if(position==polinomio.size()){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    polinomio.add(new Complex(0));
                    reload();
                }
            });
            aux.k.setText("");
            aux.grau.setText("");
            aux.itemView.setBackgroundColor(Color.argb(180,211, 211, 211));
            aux.k.setClickable(false);
            aux.k.setFocusable(false);
            aux.k.setVisibility(View.INVISIBLE);
        }
        else{
            aux.k.setText(String.valueOf(polinomio.get(position).getReal()));
            aux.grau.setText(String.valueOf(polinomio.get(position).getReal()));
            if(position!=0)aux.grau.setText(Html.fromHtml("x S<sup>"+position+"</sup>"));
            else aux.grau.setText("x 1");
            aux.k.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Double var= Double.parseDouble(((EditText)v).getText().toString());
                    mudarConstanteEm(position,new Complex(var));
                    ((EntradaDePolinomio)context).AtualizaPolinomio();
                }
            });

            aux.k.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_NEXT){
                        //Clear focus here from edittext
                        aux.k.clearFocus();
                    }
                    return false;
                }
            });

        }
    }

    public void mudarConstanteEm(int posicao,Complex valor){
        ArrayList<Complex> aux=new ArrayList<>();
        int i=0;
        while(polinomio.size()>0){
            if(i!=posicao){
                aux.add(polinomio.remove(0));
            }else {
                aux.add(valor);
                polinomio.remove(0);
            }
            i++;
        }
        polinomio.addAll(aux);
    }
    public void reload(){
        recyclerView.setAdapter(this);
        ((LinearLayoutManager)recyclerView.getLayoutManager()).setReverseLayout(true);
    }

    @Override
    public int getItemCount() {
        return polinomio.size()+1;
    }
}
