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

import com.example.moviedb.R;
import com.example.moviedb.adapter.PopularPersonAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.PopularPerson;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.example.moviedb.viewmodel.PersonViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PopularPersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularPersonFragment extends Fragment {

    private RecyclerView rv_popular;
    private PersonViewModel viewModel;
    private ProgressBar pb_popular;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = false;

    boolean ispressed = false;//Untuk IF apakah Item Sudah di tekan atau belum
    int x = 1; //Untuk Manggil Data Dari Halaman yang dipilih
    int posisi = 0; //Untuk IF apakah Item Sudah di tekan atau belum
    int halamannow = 0; //Untuk mengetahui letak halaman Item yang ditekan

    PopularPersonAdapter adapter;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PopularPersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularPersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularPersonFragment newInstance(String param1, String param2) {
        PopularPersonFragment fragment = new PopularPersonFragment();
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
        view = inflater.inflate(R.layout.fragment_popular_person, container, false);

        //Deklarasi
        rv_popular = view.findViewById(R.id.rv_popularperson);
        pb_popular = view.findViewById(R.id.pb_popular);

        //Munculin Data Halaman Pertama
        viewModel = new ViewModelProvider(getActivity()).get(PersonViewModel.class);
        viewModel.getPopularPerson("1");
        viewModel.getResultPopularPerson().observe(getActivity(), showPopularPerson);

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
    private Observer<PopularPerson> showPopularPerson = new Observer<PopularPerson>() {
        @Override
        public void onChanged(PopularPerson popularPerson) {
            rv_popular.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new PopularPersonAdapter(getActivity());
            adapter.setListpopular(popularPerson.getResults());
            rv_popular.setAdapter(adapter);

            ItemClickSupport.addTo(rv_popular).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("personId", ""+popularPerson.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_popularPersonFragment_to_fragment_person_details, bundle);
                }
            });
        }
    };

    private void onscrolllistener() {
        rv_popular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    visibleItemCount = rv_popular.getLayoutManager().getChildCount();
                    totalItemCount = rv_popular.getLayoutManager().getItemCount()-5;
                    pastVisiblesItems = ((LinearLayoutManager) rv_popular.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!isLoading){
                        if ((visibleItemCount + pastVisiblesItems) == totalItemCount){
                            Handler handler = new Handler();
                            showProgressView();
                            isLoading = true;

                            //Delay 0.5 detik biar ada loading
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    x++;

                                    //View Model setelah halaman pertama
                                    viewModel.getPopularPerson(String.valueOf(x));
                                    viewModel.getResultPopularPerson().observe(getActivity(), loadmore);
                                    isLoading = false;
                                    hideProgressView();
                                }
                            }, 500);
                        }
                    }
                }
            }
        });
    }
    //Function Buat Loading Data setelah Halaman 1
    private Observer<PopularPerson> loadmore = new Observer<PopularPerson>() {
        @Override
        public void onChanged(PopularPerson popularPerson) {
            if (ispressed){
                Bundle bundle = new Bundle();
                bundle.putString("personId", "" + popularPerson.getResults().get(posisi - (20 * (halamannow - 1))).getId());
                Navigation.findNavController(view).navigate(R.id.action_popularPersonFragment_to_fragment_person_details, bundle);
                ispressed = false;
            }else{
                adapter.UpdateData(popularPerson.getResults());
                adapter.notifyItemRangeChanged(totalItemCount+5, popularPerson.getResults().size());
            }

            ItemClickSupport.addTo(rv_popular).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
        pb_popular.setVisibility(View.VISIBLE);
    }

    private void hideProgressView() {
        pb_popular.setVisibility(View.INVISIBLE);
    }

    //Function Untuk Mengambil Upcoming.Result dari Item Halaman yang ditekan
    public void kirimbundle() {
        if (ispressed) {
            viewModel.getPopularPerson(String.valueOf(halamannow));
            viewModel.getResultPopularPerson().observe(getActivity(), loadmore);
            isLoading = false;
        }
    }
}