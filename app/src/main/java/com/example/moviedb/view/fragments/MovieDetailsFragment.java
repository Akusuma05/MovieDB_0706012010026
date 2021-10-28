package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.CompanyAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    private MovieViewModel viewModel;
    private TextView lbl_judul, lbl_genre, lbl_durasi, lbl_tagline, lbl_synopsis, lbl_rating, lbl_popularity, lbl_date, lbl_budget, lbl_country, lbl_votecount;
    private RatingBar rb_film;
    private String movie_id;
    private int company_id;
    private ImageView poster, backdrop;
    private List<Movies.Genres> genre;
    private List<Movies.ProductionCompanies> listcompany;
    private RecyclerView rv_companies;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        //Deklarasi
        lbl_judul = view.findViewById(R.id.lbl_judul_movie_details);

        lbl_genre = view.findViewById(R.id.lbl_genre_movie_detail);
        lbl_durasi = view.findViewById(R.id.lbl_duration_movie_details);
        lbl_tagline = view.findViewById(R.id.lbl_tagline_movie_details);
        lbl_synopsis = view.findViewById(R.id.lbl_synopsis_movie_details);
        lbl_rating = view.findViewById(R.id.lbl_rating_movie_details);
        lbl_popularity = view.findViewById(R.id.lbl_popularity_movie_details);
        lbl_date = view.findViewById(R.id.lbl_date_movie_details);
        lbl_budget = view.findViewById(R.id.lbl_budget_movie_details);
        lbl_country = view.findViewById(R.id.lbl_producer_movie_details);
        poster = view.findViewById(R.id.iv_movie_details);
        backdrop = view.findViewById(R.id.iv_backdrop_movie_details6);
        rb_film = view.findViewById(R.id.rb_movie_details);
        lbl_votecount = view.findViewById(R.id.lbl_votecount_movie_details);

        rv_companies = view.findViewById(R.id.rv_companies);

        movie_id = getArguments().getString("movieID");

        //--End Deklarasi

        Ambildatamovies();

        return view;
    }

    private void Ambildatamovies() {
        viewModel = new ViewModelProvider(MovieDetailsFragment.this).get(MovieViewModel.class);
        viewModel.getMoviebyID(movie_id);
        viewModel.getResultGetMovieByID().observe(getViewLifecycleOwner(), showResultMovie);
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            //Judul Film
            lbl_judul.setText(movies.getTitle());

            //Gambar Film
            Glide.with(getContext())
                    .load(Const.IMG_URL + String.valueOf(movies.getPoster_path()))
                    .into(poster);

            //Genre Film
            genre = movies.getGenres();
            for (int i = 0; i < genre.size(); i++) {
                String lblgenre = String.valueOf(lbl_genre.getText());
                if (lblgenre.equals("TextView")) {
                    lbl_genre.setText(genre.get(i).getName());
                } else {
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
            listcompany = movies.getProduction_companies();
            CompanyAdapter adapter = new CompanyAdapter(getContext(), listcompany);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            rv_companies.setLayoutManager(manager);
            rv_companies.setAdapter(adapter);
            ItemClickSupport.addTo(rv_companies).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(getContext(), listcompany.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });

            //Rating Film
            rb_film.setRating((float) movies.getVote_average() / 2);
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
            } else {
                lbl_country.setText(movies.getProduction_countries().get(0).getName());
            }

            //Backdrop
            Glide.with(getContext())
                    .load(Const.IMG_URL + String.valueOf(movies.getBackdrop_path()))
                    .into(backdrop);


        }
    };

}