package com.example.bunny.variablesecurity_1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowRecentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    private static final String TAG = "ShowRecentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recents);
        recyclerView =  findViewById(R.id.recyclerView);
        progressBar =  findViewById(R.id.progressBar);

        JSONObject jsonObject = null;

        GetData getData = new GetData();
        getData.execute();
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
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                JSONArray array = jsonObject.getJSONArray("Items");

//                for(int i=0 ;i<array.length(); i++){
//
//                    JSONObject object = array.getJSONObject(i);
//                    if(object.get("FullName") != null){
//                        temp.put(object.toString());
//                    }
//                }

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(array, ShowRecentsActivity.this);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(ShowRecentsActivity.this , LinearLayoutManager.VERTICAL, true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
