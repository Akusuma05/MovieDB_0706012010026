package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.PopularPerson;

import java.util.List;

public class PopularPersonAdapter extends RecyclerView.Adapter<PopularPersonAdapter.CardViewViewHolder> {

    private Context context;
    private List<PopularPerson.Results> listpopular;
    private List<PopularPerson.Results> getListpopular(){return listpopular;}

    public void setListpopular(List<PopularPerson.Results> listpopular){
        this.listpopular = listpopular;
    }

    public PopularPersonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PopularPersonAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_person, parent, false);
        return new PopularPersonAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPersonAdapter.CardViewViewHolder holder, int position) {
        final PopularPerson.Results results = getListpopular().get(position);
        holder.lbl_title.setText(results.getName());
        Glide.with(context)
                .load(Const.IMG_URL + results.getProfile_path())
                .into(holder.img_logo);
    }

    public void UpdateData(List<PopularPerson.Results> addPopular){
        listpopular.addAll(listpopular.size(), addPopular);
    }

    @Override
    public int getItemCount() {
        return getListpopular().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        ImageView img_logo;
        TextView lbl_title;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_logo = itemView.findViewById(R.id.img_poster_card_popular);
            lbl_title = itemView.findViewById(R.id.lbl_titile_card_popular);

        }
    }
}
