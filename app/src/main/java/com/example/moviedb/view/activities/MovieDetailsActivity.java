package com.example.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private TextView lbl_judul, lbl_genre, lbl_durasi, lbl_tagline, lbl_producer, lbl_synopsis, lbl_rating, lbl_popularity, lbl_date, lbl_budget, lbl_country, lbl_votecount;
    private RatingBar rb_film;
    private int movie_id;
    private ImageView poster, backdrop;
    private List<Movies.Genres> genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

//        //Hide Navbar
//        getSupportActionBar().hide();

        //Program Inti
        initview();
        Ambildatamovies();

    }
    private void Ambildatamovies(){
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        viewModel.getMoviebyID(String.valueOf(movie_id));
        viewModel.getResultGetMovieByID().observe(MovieDetailsActivity.this, showResultMovie);
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            //Judul Film
            lbl_judul.setText(movies.getTitle());

            //Gambar Film
            Glide.with(getBaseContext())
                    .load(Const.IMG_URL + String.valueOf(movies.getPoster_path()))
                    .into(poster);

            //Genre Film
            genre = movies.getGenres();
            for (int i = 0; i<genre.size(); i++){
                String lblgenre = String.valueOf(lbl_genre.getText());
                if (lblgenre.equals("TextView")){
                    lbl_genre.setText(genre.get(i).getName());
                }else{
                    lbl_genre.setText(lblgenre + ",  " + genre.get(i).getName());
                }
            }

            //Durasi Film
            lbl_durasi.setText(String.valueOf(movies.getRuntime()) + " minutes");

            //Tagline Film
            lbl_tagline.setText(movies.getTagline());

            //Synopsis Film
            lbl_synopsis.setText(movies.getOverview());

            //Producer Film
            lbl_producer.setText(movies.getProduction_companies().get(0).getName());

            //Rating Film
            rb_film.setRating((float) movies.getVote_average()/2);
            lbl_rating.setText(String.valueOf(movies.getVote_average()));

            //Vote Rating Film
            lbl_votecount.setText(String.valueOf(movies.getVote_count()) + " people voted");

            //Popularity Film
            lbl_popularity.setText(String.valueOf(movies.getPopularity()));

            //Tanggal Rilis Film
            lbl_date.setText(movies.getRelease_date());

            //Budget Film
            lbl_budget.setText(String.valueOf(movies.getBudget()));

            //Asal Produksi Film
            if (movies.getProduction_countries().size() == 0) {
                lbl_country.setText("No Data");
            }else{
                lbl_country.setText(movies.getProduction_countries().get(0).getName());
            }

            //Backdrop
            Glide.with(getBaseContext())
                    .load(Const.IMG_URL + String.valueOf(movies.getBackdrop_path()))
                    .into(backdrop);


        }
    };

    private void initview (){
        lbl_judul = findViewById(R.id.lbl_judul_movie_details);

        lbl_genre = findViewById(R.id.lbl_genre_movie_detail);
        lbl_durasi = findViewById(R.id.lbl_duration_movie_details);
        lbl_tagline = findViewById(R.id.lbl_tagline_movie_details);
        lbl_synopsis = findViewById(R.id.lbl_synopsis_movie_details);
        lbl_producer = findViewById(R.id.lbl_producer_movie_details);
        lbl_rating = findViewById(R.id.lbl_rating_movie_details);
        lbl_popularity = findViewById(R.id.lbl_popularity_movie_details);
        lbl_date = findViewById(R.id.lbl_date_movie_details);
        lbl_budget = findViewById(R.id.lbl_budget_movie_details);
        lbl_country = findViewById(R.id.lbl_country_movie_details);
        poster = findViewById(R.id.iv_movie_details);
        backdrop = findViewById(R.id.iv_backdrop_movie_details);
        rb_film = findViewById(R.id.rb_movie_details);
        lbl_votecount = findViewById(R.id.lbl_votecount_movie_details);

        Intent intent = getIntent();
        movie_id = intent.getExtras().getInt("movie_id");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}