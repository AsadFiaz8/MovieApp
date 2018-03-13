package com.example.asadfiaz.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.asadfiaz.movieapp.Login.userEmail;


public class FavouriteFragment extends Fragment {

    TextView txtResponce;
    ArrayList<MovieModel> arrayList;
    ListView listLikedMovie;
    ProgressView progressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);


        listLikedMovie = (ListView) view.findViewById(R.id.listLikedMovie);
        progressView= (ProgressView) view.findViewById(R.id.progressViewFavourite);
        progressView.setVisibility(View.INVISIBLE);

        arrayList = new ArrayList<>();

        progressView.setVisibility(View.VISIBLE);
        loadjson();

        return view;
    }

    public void loadjson() {



        String url = "https://universityfyp2017.000webhostapp.com/encode_likes.php";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        if (object.getString("title").equals(userEmail)) {
//                            txtResponce.append("Movietitle is: " + object.getString("movieTitle") + "\n");

                            String poster = object.getString("poster");
                            String title = object.getString("movieTitle");
                            String director = object.getString("director");
                            String genre = object.getString("genre");
                            String metaScore = object.getString("metaScore");
                            String imbdRating = object.getString("imdbRating");
                            String year = object.getString("year");

                            arrayList.add(new MovieModel(poster, title, director, genre, metaScore, imbdRating, year));

                        } else {
                            i++;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    CustomLikedAdapter adapter = new CustomLikedAdapter(getContext(), R.layout.custom_row_liked, arrayList);
                    listLikedMovie.setAdapter(adapter);

                    progressView.stop();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }

}
