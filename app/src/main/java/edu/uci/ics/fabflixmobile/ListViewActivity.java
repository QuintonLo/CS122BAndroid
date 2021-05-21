package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;

public class ListViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Connection dbcon;
        final ArrayList<Movie> movies = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        System.out.println("Here");
        try{
            dbcon = getConnection("jdbc:mysql://localhost:3306/moviedb");
            String requestedSearch = "app";
            String query = "SELECT id, title, year, director FROM movies WHERE EXISTS (SELECT id from fulltitle WHERE MATCH (title) AGAINST ( ? IN BOOLEAN MODE) and id = movies.id)";
            PreparedStatement search = dbcon.prepareStatement(query);
            search.setString(1, "+" + requestedSearch + "*");
            ResultSet results = search.executeQuery();
            // TODO: this should be retrieved from the backend server
            while(results.next()){
                String movieID = results.getString("id");
                String movieTitle = results.getString("Title");
                int movieYear = results.getInt("Year");
                String movieDirector = results.getString("Director");
                Movie newMovie = new Movie(movieTitle, movieYear, movieDirector);


                String query3 = "SELECT name FROM genres_in_movies INNER JOIN genres ON genres_in_movies.genreId = genres.id WHERE genres_in_movies.movieId = ? ORDER BY 1";
                PreparedStatement genreStatement = dbcon.prepareStatement(query3);
                genreStatement.setString(1, movieID);
                ResultSet genresSet = genreStatement.executeQuery();
//                System.out.println("works");
                while(genresSet.next()){
                    String movieGenre = genresSet.getString("Name");
                    newMovie.addGenre(movieGenre);
                }

                String query4 = "SELECT name, count(movieId) AS starredFilms FROM stars_in_movies INNER JOIN stars ON stars.id = stars_in_movies.starId WHERE EXISTS (SELECT id, name FROM stars_in_movies AS starId2 INNER JOIN stars ON stars_in_movies.starId = stars.id WHERE stars_in_movies.movieId = ? AND stars_in_movies.starId = starId2.starId) GROUP BY starId ORDER BY 3 DESC, 2 LIMIT 3";
                PreparedStatement starStatement = dbcon.prepareStatement(query4);
                starStatement.setString(1, movieID);
                ResultSet starSet = starStatement.executeQuery();
//                System.out.println("works");
                while(starSet.next()){
                    String movieStar = starSet.getString("Name");
                    newMovie.addStar(movieStar);
                }
                movies.add(newMovie);

            }


            MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

            ListView listView = findViewById(R.id.list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                Movie movie = movies.get(position);
                String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            });
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.listview);
//        // TODO: this should be retrieved from the backend server
////        final ArrayList<Movie> movies = new ArrayList<>();
//        movies.add(new Movie("The Terminal", 2004, "Bob"));
//        movies.add(new Movie("The Final Season",2007, "Steve"));
//
//        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);
//
//        ListView listView = findViewById(R.id.list);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Movie movie = movies.get(position);
//            String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//        });
    }
}