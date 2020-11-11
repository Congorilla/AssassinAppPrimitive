package com.example.assassinapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlivePlayers extends Activity {
    private ArrayList<String> playersalive;
    private String output;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aliveplayers);
        final String TAG = MainActivity.class.getName();
        output = "No players";
        DatabaseReference refalive= FirebaseDatabase.getInstance().getReference("players alive");;
        refalive.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playersalive = dataSnapshot.getValue(ArrayList.class);
                Log.d(TAG, "The current players alive are : " + playersalive);
            }

            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read players.", error.toException());
            }
        });
        for(int i=0;i<playersalive.size();i++) {
            output+=","+playersalive.get(i);
        }
        TextView outputext = (TextView)findViewById(R.id.aliveoutputext);
        outputext.setText(output);
    }
    public void backToMainMenu(View v){
        Intent i = new Intent(AlivePlayers.this,MainActivity.class);
        startActivity(i);
    }
}
