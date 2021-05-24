package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Object;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.getConnection;

public class ListViewActivity extends Activity {
    /*
   In Android, localhost is the address of the device or the emulator.
   To connect to your machine, you need to use the below IP address
  */
    private final String host = "3.16.56.91";
    private final String port = "8443";
    private final String domain = "cs122b-spring21-project1-api-example";
    private final String baseURL = "https://" + host + ":" + port + "/" + domain;
    private Button next;
    private Button prev;
    private Button searchButton;
    private int page = 0;
    private String hasNext = "1";
    private EditText searchInput;




    private ListView mylist;
    //        private EditText password;
//        private TextView message;
//        private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        // TODO: this should be retrieved from the backend server
//        final ArrayList<Movie> movies = new ArrayList<>();
//        movies.add(new Movie("The Terminal", 2004, "Bob"));
//        movies.add(new Movie("The Final Season",2007, "Steve"));

        System.out.println("MADE IT");
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


        mylist = findViewById(R.id.list);

  //      next.setOnClickListener( view -> checkNext());

            // upon creation, inflate and initialize the layout
          //  setContentView(R.layout.login);


            //assign a listener to call a function to handle the user request when clicking a button
          //  loginButton.setOnClickListener(view -> login());


        getMovieList();

        next = findViewById(R.id.next);
        next.setOnClickListener(view -> checkNext());

        prev  = findViewById(R.id.previous);
        prev.setOnClickListener(view -> checkPrevious());

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(view -> ChangePageNonAutoFill());
    }

    public void getMovieList() {

        final ArrayList<Movie> movies = new ArrayList<>();
        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST
        final StringRequest getMovieRequest = new StringRequest(
                Request.Method.GET,

                baseURL + "/api/movie-list" +"?count=20" ,

                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("login.success", response);
                    JSONObject newResponse = null;
                    try {
                        newResponse = new JSONObject(response);
                        JSONArray newResponse2 = new JSONArray(newResponse.get("movieInfo").toString());
                        for(int i = 0; i < newResponse2.length(); i++){
                            JSONObject film = new JSONObject(newResponse2.get(i).toString());
                            Movie movie = new Movie(film.get("movieLink").toString(), film.get("movieTitle").toString(), Integer.parseInt(film.get("movieYear").toString()), film.get("movieDirector").toString());
                            JSONArray genres = new JSONArray(film.get("genres").toString());
                            for(int j = 0; j < genres.length(); j++){
                                movie.addGenre(genres.get(j).toString());
                            }

                            JSONArray stars = new JSONArray(film.get("stars").toString());
                            for(int j = 0; j < stars.length(); j++){
                                JSONObject starInfo = new JSONObject(stars.get(j).toString());
                                movie.addStar(starInfo.get("name").toString());
                            }

                            movies.add(movie);
                        }
                        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

                        ListView listView = findViewById(R.id.list);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener((parent, view, position, id) -> {
                            Movie movie = movies.get(position);
                            singleMovie(movie.getId());
//                            String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
//                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        });


                        System.out.println(newResponse.get("movieInfo"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(response);
                    // initialize the activity(page)/destination
                    //  Intent listPage = new Intent(Login.this, ListViewActivity.class);
                    // activate the list page.
                    //   startActivity(listPage);
                },
                error -> {
                    // error
                    Log.d("login.error", error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
                // POST request form data
                final Map<String, String> params = new HashMap<>();
                params.put("20", "count");
                params.put("0", "pageNumber");
                return params;
            }
        };

        // important: queue.add is where the login request is actually sent
        queue.add(getMovieRequest);
    }


    public void checkNext() {

        System.out.println("++++++++++++++++\n\n\n" +searchInput.getText().toString() + "\n\n\n\n\"++++++++++++++++\\n\\n\\n\" ");
        if(!searchInput.getText().toString().equals("") ){

            if(hasNext.equals("1")){
                page ++;
                ChangePageNonAutoFill();
            }
        }
        else{


        }
    }

    public void checkPrevious() {
        if(!searchInput.getText().toString().equals("")){
            if(page > 0){
                page--;
                ChangePageNonAutoFill();
            }
        }
        else{


        }
    }

    public void ChangePage() {
        final ArrayList<Movie> movies = new ArrayList<>();
        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST
        final StringRequest loginRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/search?genre=''&title=''&startChar=''&year=''&director=''&pNumber="+page +"&" +
                        "star=''&count=20&homePageType=0&sortByRating='1'&hasNext="+ hasNext +"&descending='1'&secondary=0",
                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("next.success", response);
                    JSONObject newResponse = null;
                    try {
                        newResponse = new JSONObject(response);
                        JSONArray newResponse2 = new JSONArray(newResponse.get("movieInfo").toString());
                        for (int i = 0; i < newResponse2.length(); i++) {
                            JSONObject film = new JSONObject(newResponse2.get(i).toString());
                            Movie movie = new Movie(film.get("movieLink").toString(), film.get("movieTitle").toString(), Integer.parseInt(film.get("movieYear").toString()), film.get("movieDirector").toString());
                            JSONArray genres = new JSONArray(film.get("genres").toString());
                            for (int j = 0; j < genres.length(); j++) {
                                movie.addGenre(genres.get(j).toString());
                            }

                            JSONArray stars = new JSONArray(film.get("stars").toString());
                            for (int j = 0; j < stars.length(); j++) {
                                JSONObject starInfo = new JSONObject(stars.get(j).toString());
                                movie.addStar(starInfo.get("name").toString());
                            }

                            movies.add(movie);
                        }
                        System.out.println("has next ------------: " + newResponse.getString("hasNext"));
                        hasNext = newResponse.getString("hasNext");
                        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

                        ListView listView = findViewById(R.id.list);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // error
                    Log.d("login.error", error.toString());
                });

        // important: queue.add is where the login request is actually sent
        queue.add(loginRequest);

    }

    public void ChangePageNonAutoFill() {

        final ArrayList<Movie> movies = new ArrayList<>();
        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST
        final StringRequest loginRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/nonautofill?title="+ searchInput.getText().toString() +"&pNumber="+ page +"&" +
                        "count=20&sortByRating=1&descending=1&secondary=0",
                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("next.success", response);
                    JSONObject newResponse = null;
                    try {
                        newResponse = new JSONObject(response);
                        JSONArray newResponse2 = new JSONArray(newResponse.get("movieInfo").toString());
                        for (int i = 0; i < newResponse2.length(); i++) {
                            JSONObject film = new JSONObject(newResponse2.get(i).toString());
                            Movie movie = new Movie(film.get("movieLink").toString(), film.get("movieTitle").toString(), Integer.parseInt(film.get("movieYear").toString()), film.get("movieDirector").toString());
                            JSONArray genres = new JSONArray(film.get("genres").toString());
                            for (int j = 0; j < genres.length(); j++) {
                                movie.addGenre(genres.get(j).toString());
                            }

                            JSONArray stars = new JSONArray(film.get("stars").toString());
                            for (int j = 0; j < stars.length(); j++) {
                                JSONObject starInfo = new JSONObject(stars.get(j).toString());
                                movie.addStar(starInfo.get("name").toString());
                            }

                            movies.add(movie);
                        }

                        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

                        ListView listView = findViewById(R.id.list);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener((parent, view, position, id) -> {
                                    Movie movie = movies.get(position);
                                    singleMovie(movie.getId());
                        });
                      //  System.out.println(newResponse.get("movieInfo"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // error
                    Log.d("login.error", error.toString());
                });

        // important: queue.add is where the login request is actually sent
        queue.add(loginRequest);

    }



    public void singleMovie(String movieId){
        System.out.println(movieId);
        final ArrayList<Movie> movies = new ArrayList<>();
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest getSingleMovie = new StringRequest(
                Request.Method.GET,

                baseURL + "/api/single-movie" + "?movieId=" + movieId,

                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("login.success", response);
                    JSONObject film = null;
                    try {
                        film = new JSONObject(response);
                        Movie movie = new Movie(film.get("movieLink").toString(), film.get("movieTitle").toString(), Integer.parseInt(film.get("movieYear").toString()), film.get("movieDirector").toString());
                        JSONArray genres = new JSONArray(film.get("genres").toString());
                        for (int j = 0; j < genres.length(); j++) {
                            movie.addGenre(genres.get(j).toString());
                        }

                        JSONArray stars = new JSONArray(film.get("actors").toString());
                        for (int j = 0; j < stars.length(); j++) {
                            JSONObject starInfo = new JSONObject(stars.get(j).toString());
                            movie.addStar(starInfo.get("starName").toString());
                        }
                        movies.add(movie);

                        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

                        ListView listView = findViewById(R.id.list);
                        listView.setAdapter(adapter);





                        System.out.println(film.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(response);
                    // initialize the activity(page)/destination
                    //  Intent listPage = new Intent(Login.this, ListViewActivity.class);
                    // activate the list page.
                    //   startActivity(listPage);

                },
                error -> {
                    // error
                    Log.d("login.error", error.toString());

                });
        queue.add(getSingleMovie);
    }


}