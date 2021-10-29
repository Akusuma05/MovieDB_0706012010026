package com.example.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

public class Person implements Parcelable {

    private String birthday;
    private String known_for_department;
    private Object deathday;
    private int id;
    private String name;
    private List<String> also_known_as;
    private int gender;
    private String biography;
    private double popularity;
    private String place_of_birth;
    private String profile_path;
    private boolean adult;
    private String imdb_id;
    private Object homepage;

    protected Person(Parcel in) {
        birthday = in.readString();
        known_for_department = in.readString();
        id = in.readInt();
        name = in.readString();
        also_known_as = in.createStringArrayList();
        gender = in.readInt();
        biography = in.readString();
        popularity = in.readDouble();
        place_of_birth = in.readString();
        profile_path = in.readString();
        adult = in.readByte() != 0;
        imdb_id = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public static Person objectFromData(String str) {

        return new Gson().fromJson(str, Person.class);
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public Object getDeathday() {
        return deathday;
    }

    public void setDeathday(Object deathday) {
        this.deathday = deathday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(List<String> also_known_as) {
        this.also_known_as = also_known_as;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public Object getHomepage() {
        return homepage;
    }

    public void setHomepage(Object homepage) {
        this.homepage = homepage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(birthday);
        parcel.writeString(known_for_department);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeStringList(also_known_as);
        parcel.writeInt(gender);
        parcel.writeString(biography);
        parcel.writeDouble(popularity);
        parcel.writeString(place_of_birth);
        parcel.writeString(profile_path);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(imdb_id);
    }
}
