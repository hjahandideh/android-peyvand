package com.payvand.jahandideh.payvand;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static int selectedItem = -1;
    private ImageLoader imageLoader;
    private Context context;
    List<Chatlist> chatlists;

    public ChatAdapter(List<Chatlist> chatlists) {
        super();
        this.chatlists = chatlists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chatlist chatlist = chatlists.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(chatlist.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        holder.imageView.setImageUrl(chatlist.getImageUrl(), imageLoader);
        holder.mapayam.setText(chatlist.getMapayam());
    }

    @Override
    public int getItemCount() {
        return chatlists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mapayam;
        public NetworkImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mapayam = (TextView) itemView.findViewById(R.id.txtviewchat);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewchat);
        }
    }
}
