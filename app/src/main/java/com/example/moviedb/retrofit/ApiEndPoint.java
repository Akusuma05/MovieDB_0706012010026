package com.example.moviedb.retrofit;

import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Person;
import com.example.moviedb.model.PopularPerson;
import com.example.moviedb.model.UpComing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @GET("movie/{movie_id}")
    Call<Movies> getMovieByID(
            @Path("movie_id") String movieid,
            @Query("api_key") String apiKey
    );

    @GET("movie/now_playing")
    Call<NowPlaying> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("page") String page
    );

    @GET("movie/upcoming")
    Call<UpComing> getUpcoming(
            @Query("api_key") String apiKey,
            @Query("page") String page
    );

    @GET("person/popular")
    Call<PopularPerson> getPopularPerson(
            @Query("api_key") String apiKey,
            @Query("page") String page
    );

    @GET("person/{person_id}")
    Call<Person> getPersonByID(
            @Path("person_id") String person_id,
            @Query("api_key") String apiKey
    );
}
