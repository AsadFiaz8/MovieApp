package com.example.asadfiaz.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.example.asadfiaz.movieapp.Login.userEmail;

/**
 * Created by Asad Fiaz on 2/14/2018.
 */

public class CustomAdapter extends ArrayAdapter<MovieModel> {

    MovieModel movieModel;
    LikeButton likeButton;

    public CustomAdapter(Context context, int resource, ArrayList<MovieModel> modelArrayList) {
        super(context, R.layout.custom_list_row, modelArrayList);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_row, parent, false);
        }
        movieModel = getItem(position);

        ImageView poster = (ImageView) convertView.findViewById(R.id.poster);
        Picasso.with(getContext()).load(movieModel.getPoster()).into(poster);

        //TextView in custom row layout
        TextView movieTitle = (TextView) convertView.findViewById(R.id.title);
        movieTitle.setText("Title: " + movieModel.getTitle());

        TextView movieDirector = (TextView) convertView.findViewById(R.id.director);
        movieDirector.setText("Director: " + movieModel.getDirectedBy());

        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        genre.setText("Genre: " + movieModel.getGenre());

        TextView metaScore = (TextView) convertView.findViewById(R.id.metaScore);
        metaScore.setText("Meta Score: " + movieModel.getMetaScore());

        TextView imbdRating = (TextView) convertView.findViewById(R.id.imbdRating);
        imbdRating.setText("IMDB Rating: " + movieModel.getImbdRating());


        TextView year = (TextView) convertView.findViewById(R.id.year);
        year.setText("Year: " + movieModel.getYear());

        //Listener for Like Dislike Movie
        likeButton = (LikeButton) convertView.findViewById(R.id.btnLike);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                submitLike();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toasty.info(getContext(), "Dislike", Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;


    }

    public void submitLike() {

        String LIKE_MOVIEURL = "https://universityfyp2017.000webhostapp.com/like_post.php";
        StringRequest request = new StringRequest(
                Request.Method.POST, LIKE_MOVIEURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Like")) {
                    Toasty.info(getContext(), "Like", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toasty.error(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("useremail", userEmail);
                params.put("movieposter", movieModel.getPoster());
                params.put("movietitle", movieModel.getTitle());
                params.put("director",movieModel.getDirectedBy());
                params.put("genre",movieModel.getGenre());
                params.put("metascore",movieModel.getMetaScore());
                params.put("imdbrating", movieModel.getImbdRating());
                params.put("year",movieModel.getYear());

                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);

    } //Submit Like ends

} //Class Ends















