package com.payvand.jahandideh.payvand;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SCardAdapter extends RecyclerView.Adapter<SCardAdapter.ViewHolder> {
    private SuperHeroes superHero;
    private Context context;
    List<SuperHeroes> superHeroes;

    public SCardAdapter(List<SuperHeroes> superHeroes,Context context){
        super();
        this.superHeroes = superHeroes;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ersal, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        superHero=  superHeroes.get(position);
        holder.mopayam.setText(superHero.getMopayam());
        holder.name.setText(superHero.getName());
        holder.lname.setText(superHero.getLName());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(context.getApplicationContext(), Payam.class);
                Bundle bd=new Bundle();
                superHero =  superHeroes.get(position);
                bd.putString("id",superHero.getid());
                viewActivity.putExtras(bd);
                context.startActivity(viewActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mopayam;
        public TextView name;
        public TextView lname;
        public CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            mopayam = (TextView) itemView.findViewById(R.id.mopayam);
            name = (TextView) itemView.findViewById(R.id.ersalp);
            lname = (TextView) itemView.findViewById(R.id.textView16);
            card_view=(CardView)itemView.findViewById(R.id.card_viewpayam);
        }
    }
}
