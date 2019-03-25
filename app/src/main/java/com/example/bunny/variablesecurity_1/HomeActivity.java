package com.example.bunny.variablesecurity_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Switch doorToggleSwitch;
    TextView doorLockText;
    ProgressBar progressBar;
    TextView result,timestamp,crimnal;
    ImageView imageView;
    RelativeLayout cardView;
    com.example.myappname.TinyDB tinydb;
    private static final String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doorToggleSwitch = findViewById(R.id.openGateSwitch);
        doorLockText = findViewById(R.id.doorLockText);
        progressBar = findViewById(R.id.progressBar);

        result =  findViewById(R.id.cardResult);
        timestamp = findViewById(R.id.cardTime);
        imageView = findViewById(R.id.cardImage);
        crimnal = findViewById(R.id.criminalText);
        cardView = findViewById(R.id.parentCard);
        cardView.setVisibility(View.GONE);
        tinydb = new com.example.myappname.TinyDB(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Toast.makeText(HomeActivity.this, "This is the Panic Button", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        doorToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("UNLOCK DOOR!");
                    builder.setMessage("Do you really want to proceed ?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();

                            GetData1 getData1 =  new GetData1();
                            getData1.execute();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
                            doorToggleSwitch.setChecked(false);
                        }
                    });

                    builder.show();


                }else{
                    GetData1 getData1 =  new GetData1();
                    getData1.execute();
                    doorLockText.setText("DOOR LOCKED");
                }
            }
        });

        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Toast.makeText(this, "Signing Out", Toast.LENGTH_SHORT).show();
            tinydb.putBoolean("loginState",false);
            Intent intent = new Intent(HomeActivity.this , LoginActivity.class);
            startActivity(intent);

            return true;
        }else if( id ==  R.id.action_refresh){
            GetData getData =  new GetData();
            getData.execute();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            if (getApplicationContext() != HomeActivity.this) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_addPeople) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_showRecent) {

            Intent intent = new Intent(getApplicationContext(), ShowRecentsActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_threats) {

            Intent intent = new Intent(HomeActivity.this , MapActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "To be implemented", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "To be implemented", Toast.LENGTH_SHORT).show();

        }else if( id == R.id.action_settings){
            Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class GetData extends AsyncTask<Void , Integer , JSONObject> {

        @Override
        protected JSONObject doInBackground(Void ... voids) {


            try {

                HttpURLConnection urlConnection = null;
                URL url = new URL("https://0et141vsf0.execute-api.us-east-1.amazonaws.com/default/Notify_History");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setDoOutput(true);
                urlConnection.connect();


                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                reader.close();
//                 jsonObject = new JSONObject(stringBuilder.toString());
                JSONObject jsonArray =  new JSONObject(stringBuilder.toString());
                Log.d(TAG, "doInBackground: " + jsonArray.getJSONArray("Items").toString());

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Done Adding to database", Toast.LENGTH_SHORT).show();
//
//                    }
//                });

                return jsonArray;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cardView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            crimnal.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                JSONArray array = jsonObject.getJSONArray("Items");
                progressBar.setVisibility(View.GONE);

                for(int i=0;i<array.length();i++) {
                    JSONObject object = array.getJSONObject(i);
                    if(object.has("FullName")) {
                        cardView.setVisibility(View.VISIBLE);
                        result.setText(object.get("FullName").toString());
                        if (object.get("FullName").toString().equals("No humans detected") || object.get("FullName").toString().equals("Unknown person") || object.get("FullName").toString().equals("no match found"))
                            result.setTextColor(getResources().getColor(R.color.black));
                        else result.setTextColor(getResources().getColor(R.color.green));
                        timestamp.setText(object.get("Timestamp").toString());
                        if (object.has("Criminal"))
                            if (object.get("Criminal").toString().equals("1")) {

                            result.setTextColor(getResources().getColor(R.color.blue_primary));
                            crimnal.setVisibility(View.VISIBLE);
                        }
                        //        https://s3.amazonaws.com/multiple-face-rekog-test/images/2018-10-13181738.jpg

                        String url = "https://s3.amazonaws.com/multiple-face-rekog-test/" + object.get("Key");


                        Glide.with(HomeActivity.this).load(Uri.parse(url)).into(imageView);
                        break;
                    }
                }








            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public class GetData1 extends AsyncTask<Void , Integer , Void>{

        @Override
        protected Void doInBackground(Void ... voids) {


            try {


                HttpURLConnection urlConnection = null;
                URL url = new URL("https://8npf77c8zf.execute-api.us-east-1.amazonaws.com/dev/Door_update");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setDoOutput(true);
                urlConnection.connect();


                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                reader.close();
//
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
//
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();
            if(doorToggleSwitch.isChecked()){
                doorLockText.setText("Door Unlocked");
            }else
                doorLockText.setText("Door Locked");

//
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);

        }


    }


}
