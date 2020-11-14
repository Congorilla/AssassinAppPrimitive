package com.example.assassinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
   /* private DatabaseReference refalive;
    private DatabaseReference refdead; */
    private ArrayList<String> playersalive;
    private ArrayList<String> playersdead;
    private static final String TAG = MainActivity.class.getName();
    private Set<String> alive;
    private Set<String> dead;
    private SharedPreferences.Editor edit;
    private String username;
    private TextView welcome;
    private int aliveserial;
    private int ifdead;

    @Override
    //includeing initial settings and a welcome message
    //receive Firebase datas
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playersalive = new ArrayList<String>();
        playersalive.add("Joe");
        playersalive.add("Bill");
        playersalive.add(username);
        playersdead = new ArrayList<String>();
        playersdead.add("Kay");
        ifdead = 0;
        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Welcome, " + username + "!");
        SharedPreferences mainsp = getSharedPreferences("MainSP",MODE_PRIVATE);
        edit = mainsp.edit();


        alive = new HashSet<>();
        alive.addAll(playersalive);
        dead = new HashSet<>();
        dead.addAll(playersdead);

        edit.putStringSet("alive",alive);
        edit.putStringSet("dead",dead);
        edit.commit();

        /*refalive = FirebaseDatabase.getInstance().getReference("players alive");
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
        });*/

        aliveserial = playersalive.size();
        playersalive.add(username);
        //refalive.setValue(playersalive);
        }
    //There will be a pop-up that allows the user to change his name
    //The change will be reurned to the online database
    public void changeUserName(View v){
        if (ifdead==0) {
            AlertDialog.Builder inputDialog = new AlertDialog.Builder(MainActivity.this);
            final EditText newname = new EditText(MainActivity.this);
            inputDialog.setTitle("What name do you want to change to?").setView(newname);
            inputDialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            username = newname.getText().toString();
                            welcome.setText("Welcome, " + username + "!");
                            playersalive.set(aliveserial, username);
                            alive = new HashSet<>();
                            alive.addAll(playersalive);
                            edit.putStringSet("alive", alive);
                            edit.commit();
                            //refalive.setValue(playersalive);
                        }
                    }).show();
        }
    }
    //When the player is knocked out, he uses this button to remove himself from players alive
    //The change will be reurned to the online database
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
                            ifdead = 1;
                            welcome.setText("You are Dead");
                            playersalive.remove(aliveserial);
                            playersdead.add(username);
                            alive = new HashSet<>();
                            alive.addAll(playersalive);
                            dead = new HashSet<>();
                            dead.addAll(playersdead);
                            edit.putStringSet("alive",alive);
                            edit.putStringSet("dead",dead);
                            edit.commit();
                            //refalive.setValue(playersalive);
                           //refdead.setValue(playersdead);
                        }
                    }
                }).show();
    }
    //This point to another activity where the name of dead players are shown
    public void showPlayersDead(View v){
        Intent i = new Intent(MainActivity.this,DeadPlayers.class);
        startActivity(i);
    }
    //This point to another activity where the name of players alive are shown
    public void showPlayersAlive(View v){
        Intent i = new Intent(MainActivity.this,AlivePlayers.class);
        startActivity(i);
    }
}