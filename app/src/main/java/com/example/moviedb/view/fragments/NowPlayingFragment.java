package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.view.activities.NowPlayingActivity;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    private RecyclerView rv_now_playing;
    private MovieViewModel view_model;
    private ProgressBar pb_nowplaying;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = false;

    boolean ispressed = false;//Untuk IF apakah Item Sudah di tekan atau belum
    int x = 1; //Untuk Manggil Data Dari Halaman yang dipilih
    int posisi = 0; //Untuk IF apakah Item Sudah di tekan atau belum
    int halamannow = 0; //Untuk mengetahui letak halaman Item yang ditekan

    NowPlayingAdapter adapter;
    View view;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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
        view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        //Deklarasi
        rv_now_playing = view.findViewById(R.id.rv_now_playing_fragment);
        pb_nowplaying = view.findViewById(R.id.pb_nowplaying);

        //Munculin Data Halaman Pertama
        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getNowPlaying("1");
        view_model.getResultNowPlaying().observe(getActivity(), showNowPlaying);

        //Mereset Variable
        ispressed = false;
        x = 1;
        posisi = 0;
        halamannow = 0;

        //Function Ngecek Apa sudah scroll sampai Bawah
        onscrolllistener();

        return view;
    }

    //Observer buat halaman pertama
    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            rv_now_playing.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new NowPlayingAdapter(getActivity());
            adapter.setListNowPlaying(nowPlaying.getResults());
            rv_now_playing.setAdapter(adapter);

            ItemClickSupport.addTo(rv_now_playing).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieID", "" + nowPlaying.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_moviedetailsfragment, bundle);
                }
            });
        }
    };

    //Function Setelah Scroll Sampai bawah
    private void onscrolllistener() {
        rv_now_playing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    visibleItemCount = rv_now_playing.getLayoutManager().getChildCount();
                    totalItemCount = rv_now_playing.getLayoutManager().getItemCount();
                    pastVisiblesItems = ((LinearLayoutManager) rv_now_playing.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Handler handler = new Handler();
                            showProgressView();
                            isLoading = true;

                            //Delay 1 detik biar ada loading
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    x++;

                                    //View Model halaman setelah pertama
                                    view_model.getNowPlaying(String.valueOf(x));
                                    view_model.getResultNowPlaying().observe(getActivity(), loadmore);
                                    isLoading = false;
                                    hideProgressView();
                                }
                            }, 1000);
                        }
                    }
                }
            }
        });
    }

    //Function Buat Loading Data setelah Halaman 1
    private Observer<NowPlaying> loadmore = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {

            if (ispressed) {
                Bundle bundle = new Bundle();
                bundle.putString("movieID", "" + nowPlaying.getResults().get(posisi - (20 * (halamannow - 1))).getId());
                Navigation.findNavController(view).navigate(R.id.action_nowPlayingFragment_to_moviedetailsfragment, bundle);
                ispressed = false;
            } else {
                adapter.TambahData(nowPlaying.getResults());
                adapter.notifyItemRangeChanged(totalItemCount, nowPlaying.getResults().size());
            }

            ItemClickSupport.addTo(rv_now_playing).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    posisi = position;
                    halamannow = (position / 20) + 1;
                    ispressed = true;
                    kirimbundle();
                }
            });
        }
    };

    private void showProgressView() {
        pb_nowplaying.setVisibility(View.VISIBLE);
    }

    private void hideProgressView() {
        pb_nowplaying.setVisibility(View.INVISIBLE);
    }

    //Function Untuk Mengambil Upcoming.Result dari Item Halaman yang ditekan
    public void kirimbundle() {
        if (ispressed) {
            view_model.getNowPlaying(String.valueOf(halamannow));
            view_model.getResultNowPlaying().observe(getActivity(), loadmore);
            isLoading = false;
        }
    }
}