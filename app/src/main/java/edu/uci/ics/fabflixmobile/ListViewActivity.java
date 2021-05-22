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
    private final String host = "10.0.2.2";
    private final String port = "8443";
    private final String domain = "cs122b_spring21_project1_api_example_war_exploded";
    private final String baseURL = "https://" + host + ":" + port + "/" + domain;



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

            // upon creation, inflate and initialize the layout
          //  setContentView(R.layout.login);


            //assign a listener to call a function to handle the user request when clicking a button
          //  loginButton.setOnClickListener(view -> login());


        getMovieList();
    }

    public void getMovieList() {


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
                    System.out.println(response);
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
                params.put("count", "20");
                params.put("pageNumber", "0");
                return params;
            }
        };

        // important: queue.add is where the login request is actually sent
        queue.add(getMovieRequest);
    }
}