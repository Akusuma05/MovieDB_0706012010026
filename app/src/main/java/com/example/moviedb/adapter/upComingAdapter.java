package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.UpComing;

import java.util.List;

public class upComingAdapter extends RecyclerView.Adapter<upComingAdapter.CardViewViewHolder> {

    private Context context;
    private List<UpComing.Results> listUpcoming;

    private List<UpComing.Results> getListUpcoming() {
        return listUpcoming;
    }

    private List<UpComing.Results> Temp;

    public void setListUpcoming(List<UpComing.Results> listUpcoming) {
        this.listUpcoming = listUpcoming;
    }

    public upComingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public upComingAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent, false);
        return new upComingAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull upComingAdapter.CardViewViewHolder holder, int position) {
        final UpComing.Results results = getListUpcoming().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_overview.setText(results.getOverview());
        holder.lbl_release_date.setText(results.getRelease_date());
        Glide.with(context)
                .load(Const.IMG_URL + results.getPoster_path())
                .into(holder.img_poster);
    }

    public void updateData(List<UpComing.Results> addUpcoming) {
        Temp = addUpcoming;
        listUpcoming.addAll(listUpcoming.size(), Temp);
    }

    @Override
    public int getItemCount() {
        return getListUpcoming().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_date;
        CardView cv;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_card_popular);
            img_poster = itemView.findViewById(R.id.img_poster_card_popular);
            lbl_title = itemView.findViewById(R.id.lbl_titile_card_popular);
            lbl_overview = itemView.findViewById(R.id.lbl_subtitle_card_popular);
            lbl_release_date = itemView.findViewById(R.id.lbl_releasedate_card_popular);
        }
    }
}
