package com.example.assassinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference refalive;
    private DatabaseReference refdead;
    private ArrayList<String> playersalive;
    private ArrayList<String> playersdead;
    private static final String TAG = MainActivity.class.getName();
    private String username;
    private TextView welcome;
    private int aliveserial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = "Player";
        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Welcome, " + username + "!");

        refalive = FirebaseDatabase.getInstance().getReference("players alive");
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
        refdead = FirebaseDatabase.getInstance().getReference("players dead");
        refdead.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playersdead = dataSnapshot.getValue(ArrayList.class);
                Log.d(TAG, "The current players dead are : " + playersdead);
            }

            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read players.", error.toException());
            }
        });

        aliveserial = playersalive.size();
        playersalive.add(username);
        refalive.setValue(playersalive);
        }
    public void changeUserName(View v){
        final EditText newname = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("What name do you want to change to?").setView(newname);
        inputDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       username = newname.getText().toString();
                       welcome.setText("Welcome, " + username + "!");
                       playersalive.set(aliveserial,username);
                       refalive.setValue(playersalive);
                    }
                }).show();
    }
    public void removeUser(View v){
        final EditText outkey = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("Please type 'out' below to ensure").setView(outkey);
        inputDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(outkey.getText().toString().equals("out")) {
                            playersalive.remove(aliveserial);
                            playersdead.add(username);
                            refalive.setValue(playersalive);
                            refdead.setValue(playersdead);
                        }
                    }
                }).show();
    }
    public void showPlayersDead(View v){
        Intent i = new Intent(MainActivity.this,DeadPlayers.class);
        startActivity(i);
    }
    public void showPlayersAlive(View v){
        Intent i = new Intent(MainActivity.this,AlivePlayers.class);
        startActivity(i);
    }
}