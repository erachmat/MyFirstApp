package com.example.myfirstapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.Model.Photos;
import com.example.myfirstapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListPhoto extends RecyclerView.Adapter<AdapterListPhoto.ViewHolder> {

    Context context;
    List<Photos> photosList;

    public AdapterListPhoto(Context context, List<Photos> photosList) {
        this.context = context;
        this.photosList = photosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_photos_layout, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String thumbnailUrl = photosList.get(position).getThumbnailUrl();
        Picasso.with(context).load(thumbnailUrl).fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.logoo_amikom)
                .into(holder.photos);

        String id = photosList.get(position).getId().toString();
        holder.txtId.setText(id);
        holder.txtName.setText(photosList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photos;
        TextView txtName, txtId;

        public ViewHolder(View itemView) {
            super(itemView);

            photos = itemView.findViewById(R.id.icon);
            txtId = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
