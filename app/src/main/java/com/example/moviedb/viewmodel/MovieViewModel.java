package com.example.moviedb.viewmodel;

import android.app.Application;
import android.graphics.Movie;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = MovieRepository.getInstance();
    }

    //== Begin of View model get Movie by Id
    private MutableLiveData<Movies> resultGetMovieByID = new MutableLiveData<>();

    public void getMoviebyID(String movieID){
        resultGetMovieByID = repository.getMovieData(movieID);
    }

    public LiveData<Movies> getResultGetMovieByID(){
        return resultGetMovieByID;
    }
    //== End of View model get Movie by Id

    //==Begin of viewmodel get now playing

    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    public void getNowPlaying(){
        resultGetNowPlaying = repository.getNowPlayingData();
    }
    public LiveData<NowPlaying> getResultNowPlaying(){
        return resultGetNowPlaying;
    }

    //==End of viewmodel get now playing

}
