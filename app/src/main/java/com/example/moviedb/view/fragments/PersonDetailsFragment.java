package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Person;
import com.example.moviedb.viewmodel.PersonViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonDetailsFragment extends Fragment {

    private TextView lbl_nama, lbl_DOB, lbl_knownfor, lbl_popularity, lbl_placeofBirth, lbl_biography;
    private ImageView iv_person;
    private PersonViewModel viewModel;
    private String person_id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_person_details.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonDetailsFragment newInstance(String param1, String param2) {
        PersonDetailsFragment fragment = new PersonDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_person_details, container, false);

        //Deklarasi
        lbl_nama = view.findViewById(R.id.lbl_nama_person);
        lbl_DOB = view.findViewById(R.id.lbl_birthday_person);
        lbl_biography = view.findViewById(R.id.lbl_biography_person);
        lbl_knownfor = view.findViewById(R.id.lbl_knownfor_person);
        lbl_popularity = view.findViewById(R.id.lbl_popularity_person);
        lbl_placeofBirth = view.findViewById(R.id.lbl_placeofbirth_person);
        iv_person = view.findViewById(R.id.iv_person);

        person_id = getArguments().getString("personId");

        Ambildataperson();

        return view;
    }

    public void Ambildataperson(){
        viewModel = new ViewModelProvider(PersonDetailsFragment.this).get(PersonViewModel.class);
        viewModel.getPersonbyId(person_id);
        viewModel.getResultPersonByID().observe(getViewLifecycleOwner(), showResultPerson);
    }

    private Observer<Person> showResultPerson = new Observer<Person>() {
        @Override
        public void onChanged(Person person) {
            //Nama Pemain
            lbl_nama.setText(person.getName());

            //DOB
            lbl_DOB.setText(person.getBirthday());

            //Biography
            lbl_biography.setText(person.getBiography());

            //Known For
            lbl_knownfor.setText(person.getKnown_for_department());

            //Popularity
            lbl_popularity.setText(person.getPopularity() + " People");

            //Place Of Birth
            lbl_placeofBirth.setText(person.getPlace_of_birth());

            //image
            Glide.with(getContext())
                    .load(Const.IMG_URL + String.valueOf(person.getProfile_path()))
                    .into(iv_person);
        }
    };
}