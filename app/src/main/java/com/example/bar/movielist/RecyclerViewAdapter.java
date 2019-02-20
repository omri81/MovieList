package com.example.bar.movielist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public RecyclerViewAdapter(Context context, ArrayList<Movie> moveis){
        this.context = context;
        this.movies = moveis;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movie_list_row,viewGroup,false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.releaseYearTv.setText(movies.get(i).getReleaseYear()+"");
        viewHolder.titleTv.setText(movies.get(i).getTitle());
        Picasso.get().load(movies.get(i).getImageUrl()).into(viewHolder.movieIv);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context,MovieDescription.class);
                intent.putExtra("movie1",movies.get(position));
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
       return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTv;
        private TextView releaseYearTv;
        private ImageView movieIv;
        private ItemClickListener itemClickListener;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.movie_row_title);
            releaseYearTv = (TextView) itemView.findViewById(R.id.movie_row_release_year);
            movieIv = (ImageView) itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onClick(itemView, getAdapterPosition());
        }
    }
}
