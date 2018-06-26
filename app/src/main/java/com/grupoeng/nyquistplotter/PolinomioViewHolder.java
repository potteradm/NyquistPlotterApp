package com.grupoeng.nyquistplotter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PolinomioViewHolder extends RecyclerView.ViewHolder {

    public EditText k;
    public TextView grau;
    public PolinomioViewHolder(View itemView){
        super(itemView);
        k=itemView.findViewById(R.id.Kedtext);
        grau=itemView.findViewById(R.id.grauTxt);
    }
}
