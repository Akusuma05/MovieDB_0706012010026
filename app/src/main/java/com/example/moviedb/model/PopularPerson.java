package com.example.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

public class PopularPerson implements Parcelable {

    private int page;
    private List<Results> results;
    private int total_results;
    private int total_pages;

    protected PopularPerson(Parcel in) {
        page = in.readInt();
        total_results = in.readInt();
        total_pages = in.readInt();
    }

    public static final Creator<PopularPerson> CREATOR = new Creator<PopularPerson>() {
        @Override
        public PopularPerson createFromParcel(Parcel in) {
            return new PopularPerson(in);
        }

        @Override
        public PopularPerson[] newArray(int size) {
            return new PopularPerson[size];
        }
    };

    public static PopularPerson objectFromData(String str) {

        return new Gson().fromJson(str, PopularPerson.class);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeInt(total_results);
        parcel.writeInt(total_pages);
    }

    public static class Results {
        private String profile_path;
        private boolean adult;
        private int id;
        private List<KnownFor> known_for;
        private String name;
        private double popularity;

        public static Results objectFromData(String str) {

            return new Gson().fromJson(str, Results.class);
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<KnownFor> getKnown_for() {
            return known_for;
        }

        public void setKnown_for(List<KnownFor> known_for) {
            this.known_for = known_for;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public static class KnownFor {
            private String poster_path;
            private boolean adult;
            private String overview;
            private String release_date;
            private String original_title;
            private List<Integer> genre_ids;
            private int id;
            private String media_type;
            private String original_language;
            private String title;
            private String backdrop_path;
            private double popularity;
            private int vote_count;
            private boolean video;
            private double vote_average;

            public static KnownFor objectFromData(String str) {

                return new Gson().fromJson(str, KnownFor.class);
            }

            public String getPoster_path() {
                return poster_path;
            }

            public void setPoster_path(String poster_path) {
                this.poster_path = poster_path;
            }

            public boolean isAdult() {
                return adult;
            }

            public void setAdult(boolean adult) {
                this.adult = adult;
            }

            public String getOverview() {
                return overview;
            }

            public void setOverview(String overview) {
                this.overview = overview;
            }

            public String getRelease_date() {
                return release_date;
            }

            public void setRelease_date(String release_date) {
                this.release_date = release_date;
            }

            public String getOriginal_title() {
                return original_title;
            }

            public void setOriginal_title(String original_title) {
                this.original_title = original_title;
            }

            public List<Integer> getGenre_ids() {
                return genre_ids;
            }

            public void setGenre_ids(List<Integer> genre_ids) {
                this.genre_ids = genre_ids;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMedia_type() {
                return media_type;
            }

            public void setMedia_type(String media_type) {
                this.media_type = media_type;
            }

            public String getOriginal_language() {
                return original_language;
            }

            public void setOriginal_language(String original_language) {
                this.original_language = original_language;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBackdrop_path() {
                return backdrop_path;
            }

            public void setBackdrop_path(String backdrop_path) {
                this.backdrop_path = backdrop_path;
            }

            public double getPopularity() {
                return popularity;
            }

            public void setPopularity(double popularity) {
                this.popularity = popularity;
            }

            public int getVote_count() {
                return vote_count;
            }

            public void setVote_count(int vote_count) {
                this.vote_count = vote_count;
            }

            public boolean isVideo() {
                return video;
            }

            public void setVideo(boolean video) {
                this.video = video;
            }

            public double getVote_average() {
                return vote_average;
            }

            public void setVote_average(double vote_average) {
                this.vote_average = vote_average;
            }
        }
    }
}
