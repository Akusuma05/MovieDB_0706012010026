package com.example.moviedb.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Person;
import com.example.moviedb.model.PopularPerson;
import com.example.moviedb.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonRepository {

    private static PersonRepository repository;

    public PersonRepository() {
    }

    public static PersonRepository getInstance(){
        if (repository == null){
            repository = new PersonRepository();
        }
        return repository;
    }

    //Repository Person Details
    public MutableLiveData<Person> getPersonData(String personId){
        final MutableLiveData<Person> result = new MutableLiveData<Person>();

        ApiService.endPoint().getPersonByID(personId, Const.API_KEY).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
        return result;
    }

    //Repository Popular Person
    public MutableLiveData<PopularPerson> getPopularPersonData(String page){
        final MutableLiveData<PopularPerson> result = new MutableLiveData<>();

        ApiService.endPoint().getPopularPerson(Const.API_KEY, page).enqueue(new Callback<PopularPerson>() {
            @Override
            public void onResponse(Call<PopularPerson> call, Response<PopularPerson> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PopularPerson> call, Throwable t) {

            }
        });
        return result;
    }
}
