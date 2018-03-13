package com.example.asadfiaz.movieapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asad Fiaz on 2/14/2018.
 */

public class CustomLikedAdapter extends ArrayAdapter<MovieModel> {

    LayoutInflater inflater;
    MovieModel movieModel;

    public CustomLikedAdapter(Context context, int resource,ArrayList<MovieModel> movieModels) {
        super(context, R.layout.custom_row_liked, movieModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row_liked, parent, false);
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
        metaScore.setText("MetaScore: " + movieModel.getMetaScore());

        TextView imbdRating = (TextView) convertView.findViewById(R.id.imbdRating);
        imbdRating.setText("IMDB Rating: " + movieModel.getImbdRating());

        TextView year = (TextView) convertView.findViewById(R.id.year);
        year.setText("Year: " + movieModel.getYear());


        return convertView;
    }
}
