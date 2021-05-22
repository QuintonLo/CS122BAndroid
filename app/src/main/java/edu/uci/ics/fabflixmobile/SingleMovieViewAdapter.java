package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class SingleMovieViewAdapter extends ArrayAdapter<Movie> {
    private final ArrayList<Movie> movies;

    public SingleMovieViewAdapter(ArrayList<Movie> movies, Context context) {
        super(context, R.layout.singlemovie, movies);
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.singlemovie, parent, false);

        Movie movie = movies.get(0);

        TextView titleView = view.findViewById(R.id.title);
        TextView yearView = view.findViewById(R.id.year);
        TextView directorView = view.findViewById(R.id.director);
        TextView genreView = view.findViewById(R.id.genres);
        TextView starView = view.findViewById(R.id.stars);


        titleView.setText(movie.getName());
        // need to cast the year to a string to set the label
        yearView.setText("Year: " + movie.getYear() + "\n");
        directorView.setText("Director: " + movie.getDirector() + "\n");
        String genreString = "Genres:\n";
        ArrayList<String> genres = movie.getGenres();
        for(int i = 0; i < genres.size(); i++){
            genreString += "     " + genres.get(i) + "\n";
        }
        genreView.setText(genreString);
        String starString = "Stars:\n";
        ArrayList<String> stars = movie.getStars();
        for(int i = 0; i < stars.size(); i++){
            starString += "   " + stars.get(i) + "\n";
        }
        starView.setText(starString);

        return view;
    }
}