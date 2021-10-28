package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;

import java.util.ArrayList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CardViewViewHolder> {

    private Context context;
    private List<Movies.ProductionCompanies> listCompanies;

    public CompanyAdapter(Context context, List<Movies.ProductionCompanies> listCompanies) {
        this.context = context;
        this.listCompanies = listCompanies;
    }

    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_companies, parent, false);
        return new CompanyAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        if (listCompanies.get(position).getLogo_path() == null) {
            holder.img_logo.setImageResource(R.drawable.no_image);
        } else {
            Glide.with(context)
                    .load(Const.IMG_URL + String.valueOf(listCompanies.get(position).getLogo_path()))
                    .into(holder.img_logo);
        }

    }

    @Override
    public int getItemCount() {
        return listCompanies.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_logo;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_logo = itemView.findViewById(R.id.img_company);
        }
    }
}
