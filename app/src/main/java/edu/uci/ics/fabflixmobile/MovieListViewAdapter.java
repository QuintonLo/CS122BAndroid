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

public class MovieListViewAdapter extends ArrayAdapter<Movie> {
    private final ArrayList<Movie> movies;

    public MovieListViewAdapter(ArrayList<Movie> movies, Context context) {
        super(context, R.layout.row, movies);
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.row, parent, false);

        Movie movie = movies.get(position);

        TextView titleView = view.findViewById(R.id.title);
        TextView subtitleView = view.findViewById(R.id.subtitle);

        titleView.setText(movie.getName());
        // need to cast the year to a string to set the label
        String subtitleString = "Year: " + movie.getYear() + "\n";
        subtitleString += "Director: " + movie.getDirector() + "\n";
        subtitleString += "Genres:\n";
        ArrayList<String> genres = movie.getGenres();
        for(int i = 0; i < genres.size(); i++){
            subtitleString += "   " + genres.get(i) + "\n";
        }
        subtitleString += "Stars:\n";
        ArrayList<String> stars = movie.getStars();
        for(int i = 0; i < stars.size(); i++){
            subtitleString += "   " + stars.get(i) + "\n";
        }
        System.out.println(subtitleString);
        subtitleView.setText(subtitleString);

        return view;
    }
}