package com.payvand.jahandideh.payvand;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class ErjahAdapteredit extends RecyclerView.Adapter<ErjahAdapteredit.ViewHolder> {
    private static int selectedItem = -1;
    private ImageLoader imageLoader;
    private Context context;
    List<ErjahList> erjahLists;

    public ErjahAdapteredit(List<ErjahList> erjahLists) {
        super();
        this.erjahLists = erjahLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.erjahlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ErjahList erjahList = erjahLists.get(position);

        holder.erjah.setText(erjahList.geterjah());
        holder.erjah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErjahList erjahList = erjahLists.get(position);
                EditNameh.tv.setText(erjahList.geterjah());

            }
        });
    }

    @Override
    public int getItemCount() {
        return erjahLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView erjah;

        public ViewHolder(View itemView) {
            super(itemView);
            erjah = (TextView) itemView.findViewById(R.id.erjahtxt);
        }
    }
}
