package edu.uci.ics.fabflixmobile;

import java.util.ArrayList;

public class Movie {
    private final String name;
    private final int year;
    private final String director;
    private final ArrayList<String> stars;
    private final ArrayList<String> genres;
    private final String id;

    public Movie(String id, String name, int year, String director) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.director = director;
        this.stars = new ArrayList<String>();
        this.genres = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getId(){return id;}

    public String getDirector() {return director;}

    public ArrayList<String> getStars() {return stars;}

    public ArrayList<String> getGenres() {return genres;}

//    public ArrayList<String> getThreeStars() {
//        ArrayList<String> temp = new ArrayList<String>();
//        for(int i = 0; i < stars.size(); i++){
//            temp.add(stars.get(i));
//        }
//        return temp;
//    }
//
//    public ArrayList<String> getThreeGenres() {
//        ArrayList<String> temp = new ArrayList<String>();
//        for(int i = 0; i < genres.size(); i++){
//            temp.add(genres.get(i));
//        }
//        return temp;
//    }

    public void addGenre(String genre) {this.genres.add(genre);}

    public void addStar(String star) {this.stars.add(star);}
}