package com.example.asadfiaz.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class About extends Fragment {


    ListView listView;
    String[] data={"My Movie App","Version 1.0","asadfiaz8@gmail.com","Rate","About"};
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about, container, false);

        listView= (ListView) view.findViewById(R.id.listView);

        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Long itemPosition=parent.getItemIdAtPosition(position);
                Long pos=3L;
                if (itemPosition==pos){
                    Toast.makeText(getActivity(), "Rate", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }


}
