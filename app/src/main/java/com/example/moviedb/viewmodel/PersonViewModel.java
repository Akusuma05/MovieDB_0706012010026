package com.example.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.model.Person;
import com.example.moviedb.model.PopularPerson;
import com.example.moviedb.repositories.PersonRepository;

public class PersonViewModel extends AndroidViewModel {

    private PersonRepository repository;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        repository = PersonRepository.getInstance();
    }

    //Person by ID
    private MutableLiveData<Person> resultGetPersonByID = new MutableLiveData<>();
    public void getPersonbyId(String personID){
        resultGetPersonByID = repository.getPersonData(personID);
    }
    public LiveData<Person> getResultPersonByID(){return resultGetPersonByID;}

    //Popular Person View Model
    private MutableLiveData<PopularPerson> resultGetPopularPerson = new MutableLiveData<>();
    public void getPopularPerson(String page){
        resultGetPopularPerson = repository.getPopularPersonData(page);
    }
    public LiveData<PopularPerson> getResultPopularPerson(){return resultGetPopularPerson;}
}
