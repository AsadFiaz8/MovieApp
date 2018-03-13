package com.example.asadfiaz.movieapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {

    public static String API_URl = "http://www.omdbapi.com/?t=";
    public static String API_KEY = "&apikey=b46ba02a";
    RequestQueue queue;
    ListView listMovie;
    ArrayList<MovieModel> arrayList;
    TextView txtSearch;
    ProgressView progressView;


    public HomeFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        txtSearch = (TextView) view.findViewById(R.id.txtSearch);
        //Changing the font
//        Typeface font=Typeface.createFromAsset(getContext().getAssets(),"font/Raleway-Bold.ttf");
//        txtSearch.setTypeface(font);


        progressView= (ProgressView) view.findViewById(R.id.progressView);
        progressView.setVisibility(View.INVISIBLE);


        listMovie = (ListView) view.findViewById(R.id.listMovie);

        arrayList = new ArrayList<>();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);


        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                txtSearch.setVisibility(View.INVISIBLE);
                progressView.setVisibility(View.VISIBLE);

                query = query.replaceAll("\\s+", "%20");
                String URL = API_URl + query + API_KEY;

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String poster = response.getString("Poster");
                            String title = response.getString("Title");
                            String director = response.getString("Director");
                            String genre = response.getString("Genre");
                            String metaScore = response.getString("Metascore");
                            String imbdRating = response.getString("imdbRating");
                            String year = response.getString("Year");


                            arrayList.add(new MovieModel(poster, title, director, genre, metaScore, imbdRating, year));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        CustomAdapter adapter = new CustomAdapter(getContext(), R.layout.custom_list_row, arrayList);
                        listMovie.setAdapter(adapter);

                        progressView.stop();


//                        //Listener for Custom ListItem
//                        listMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                                String item=String.valueOf(parent.getItemAtPosition(position));
//                                Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();
//                            }
//                        });


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                );
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(request);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//
//            case R.id.action_search:
//
//                Toast.makeText(getContext(), "Menu", Toast.LENGTH_SHORT).show();
//                return true;
////        }
//
//
//
//
//        return true;
//
//    }

//    public void responce() {
//
//
//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONArray array = response.getJSONArray("Ratings");
//
//                    JSONObject childObject = array.getJSONObject(0);
//
//                    String source = childObject.getString("Source");
//                    String rating = childObject.getString("Value");
//
//                    txtResponce.setText("Source: " + source + " Rating: " + rating);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }
//        );
//
//        queue = Volley.newRequestQueue(getActivity());
//        queue.add(request);
//
//    }

}
