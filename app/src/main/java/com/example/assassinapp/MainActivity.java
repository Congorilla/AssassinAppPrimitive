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
    private DatabaseReference reference;
    private ArrayList<Player> playerList;
    private ArrayList<Player> dataShot;
    private static final String TAG = MainActivity.class.getName();
    private Player player;
    private int userId;
    private TextView welcome;
    @Override
    //includeing initial settings and a welcome message
    //receive Firebase datas
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataShot = new ArrayList<Player>();

        Player player1 = new Player("John",0);
        Player player2 = new Player("Sam",1);
        Player player3 = new Player("Wick",2);
        dataShot.add(player1);
        dataShot.add(player2);
        dataShot.add(player3);

        reference = FirebaseDatabase.getInstance().getReference("players alive");

        reference.setValue(dataShot);//add

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataShot = dataSnapshot.getValue(ArrayList.class);
                Log.d(TAG, "The current players are : " + dataShot);
            }

            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read players.", error.toException());
            }
        });

        playerList = new ArrayList<Player>();
        playerList.addAll(dataShot);

        player = new Player();
        if(playerList.size()==0)
            userId = 0;
        else
            userId = playerList.get(playerList.size()-1).getUid()+1;
        player.setUid(userId);
        playerList.add(player);

        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Welcome, " + player.getName() + "!");

        reference.setValue(playerList);
        }
    //There will be a pop-up that allows the user to change his name
    //The change will be reurned to the online database
    public void changeUserName(View v){
        if (player.getStatus().equals("alive")) {
            AlertDialog.Builder inputDialog = new AlertDialog.Builder(MainActivity.this);
            final EditText newname = new EditText(MainActivity.this);
            inputDialog.setTitle("What name do you want to change to?").setView(newname);
            inputDialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            player.setName(newname.getText().toString());
                            welcome.setText("Welcome, " + player.getName() + "!");
                            for(int i=0; i<playerList.size();i++){
                                if (userId==playerList.get(i).getUid())
                                    playerList.set(i,player);
                            }
                            reference.setValue(playerList);
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
                            player.setStatus("dead");
                            welcome.setText("You are Dead");
                            for(int i=0; i<playerList.size();i++){
                                if (userId==playerList.get(i).getUid())
                                    playerList.set(i,player);
                            }
                            reference.setValue(playerList);
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