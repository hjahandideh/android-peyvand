package com.payvand.jahandideh.payvand;

/**
 * Created by Jahandideh on 19/07/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 11/9/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {



    //List of superHeroes
    List<SuperHeroes> superHeroes;

    public CardAdapter(List<SuperHeroes> superHeroes){
        super();
        //Getting all the superheroes
        this.superHeroes = superHeroes;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SuperHeroes superHero =  superHeroes.get(position);
        holder.mopayam.setText(superHero.getMopayam());
        holder.ersal.setText(superHero.getErsal());

    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mopayam;
        public TextView ersal;


        public ViewHolder(View itemView) {
            super(itemView);
            mopayam = (TextView) itemView.findViewById(R.id.mopayam);
            ersal = (TextView) itemView.findViewById(R.id.ersalp);

        }
    }
}
