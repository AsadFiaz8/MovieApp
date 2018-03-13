package com.example.asadfiaz.movieapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.asadfiaz.movieapp.HomeFragment.API_KEY;
import static com.example.asadfiaz.movieapp.Login.userEmail;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue queue;
    TextView txtClick;
    TextView txtNavHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie Box");


        txtClick= (TextView) findViewById(R.id.txtClick);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        TextView userEmailAddress= (TextView) headerView.findViewById(R.id.txtNavHeader);
        userEmailAddress.setText(userEmail.toUpperCase());



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//
//
//        MenuItem searchBar = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchBar.getActionView();
//
//        searchView.setOnQueryTextListener(
//                new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//
//                        String URL = "http://www.omdbapi.com/?t=" + query + API_KEY;
//
//                        JsonObjectRequest request = new JsonObjectRequest(
//                                Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                try {
//                                    JSONArray array = response.getJSONArray("Ratings");
//
//                                    JSONObject childObject = array.getJSONObject(0);
//
//                                    String source = childObject.getString("Source");
//                                    String rating = childObject.getString("Value");
//
//                                    txtResponce.setText("Search SOurce: " + source + " Rating: " + rating);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                error.printStackTrace();
//                                Toast.makeText(Home.this, error.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        );
//
//                        queue = Volley.newRequestQueue(Home.this);
//                        queue.add(request);
//
//
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        return false;
//                    }
//                }
//        );
//
//
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {

            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_layout, new HomeFragment());
            transaction.commit();


        } else if (id == R.id.nav_favourite) {

            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            txtClick.setVisibility(View.INVISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_layout, new FavouriteFragment());
            transaction.commit();

        } else if (id == R.id.nav_about) {

            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            txtClick.setVisibility(View.INVISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_layout, new About());
            transaction.commit();


        }
        else if (id == R.id.nav_about) {

            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            txtClick.setVisibility(View.INVISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_layout, new About());
            transaction.commit();


        } else if(id==R.id.nav_Rate){


            txtClick.setVisibility(View.INVISIBLE);
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData( Uri.parse("https://www.facebook.com/asad.fiaz.142"));
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
