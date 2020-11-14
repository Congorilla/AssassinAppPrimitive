package com.example.assassinapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.Set;

public class AlivePlayers extends Activity {
    private ArrayList<String> playersalive;
    private String output;
    @Override
    //Displaying all players alive, with button to go back to the main menu
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aliveplayers);
        final String TAG = MainActivity.class.getName();
        SharedPreferences sh = getSharedPreferences("MainSP",MODE_PRIVATE);
        Set s = new HashSet<>();
        s = sh.getStringSet("alive",null);
        playersalive = new ArrayList<String>();
        playersalive.addAll(s);
        output = "";
        /*DatabaseReference refalive= FirebaseDatabase.getInstance().getReference("players alive");;
        refalive.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playersalive = dataSnapshot.getValue(ArrayList.class);
                Log.d(TAG, "The current players alive are : " + playersalive);
            }

            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read players.", error.toException());
            }
        });*/
        for(int i=0;i<playersalive.size();i++) {
            if(i!=playersalive.size()-1)
                output+=playersalive.get(i)+", ";
            else
                output+=playersalive.get(i);
        }
        TextView outputext = (TextView)findViewById(R.id.aliveoutputext);
        outputext.setText(output);
    }
    //Pointing to the mainmenu
    public void backToMainMenu(View v){
        Intent i = new Intent(AlivePlayers.this,MainActivity.class);
        startActivity(i);
    }
}
