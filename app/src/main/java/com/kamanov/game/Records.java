package com.kamanov.game;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Records extends Fragment {

    ListView list_item;
    FirebaseListAdapter<User> adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_records, container, false);
        list_item = root.findViewById(R.id.list_item);

        Query databaseReference = FirebaseDatabase
                .getInstance("https://kamanovgame-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()
                .child("Users")
        .orderByChild("level").limitToLast(10);

        adapter = new FirebaseListAdapter<User>(getActivity(), User.class, R.layout.list_item, databaseReference) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void populateView(View v, User model, int position) {
                TextView user_name, user_age, user_level;
                user_name = v.findViewById(R.id.user_name);
                user_age = v.findViewById(R.id.user_age);
                user_level = v.findViewById(R.id.user_level);


                user_name.setText(model.getName());
                user_age.setText("Age: " + model.getAge());
                user_level.setText("Level " + model.getLevel());
            }
        };

        list_item.setAdapter(adapter);

        root.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Records.this)
                        .navigate(R.id.action_records2_to_FirstFragment);
            }
        });

        return root;
    }
}