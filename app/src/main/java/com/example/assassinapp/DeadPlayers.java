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

//Displaying all players dead, with button to go back to the main menu
public class DeadPlayers extends Activity {
    private ArrayList<String> playersdead;
    private String output;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadplayers);
        final String TAG = MainActivity.class.getName();
        SharedPreferences sh = getSharedPreferences("MainSP",MODE_PRIVATE);
        Set s = new HashSet<>();
        s = sh.getStringSet("dead",null);
        playersdead = new ArrayList<String>();
        playersdead.addAll(s);
        output = "";
        /*DatabaseReference refdead= FirebaseDatabase.getInstance().getReference("players dead");;
        refdead.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playersdead = dataSnapshot.getValue(ArrayList.class);
                Log.d(TAG, "The current players dead are : " + playersdead);
            }

            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read players.", error.toException());
            }
        });*/
        for(int i=0;i<playersdead.size();i++) {
            if(i!=playersdead.size()-1)
                output+=playersdead.get(i)+", ";
            else
                output+=playersdead.get(i);
        }
        TextView outputext = (TextView)findViewById(R.id.deadoutputext);
        outputext.setText(output);
    }
    //Pointing to the mainmenu
    public void backToMainMenu(View v){
        Intent i = new Intent(DeadPlayers.this,MainActivity.class);
        startActivity(i);
    }
}
