package com.example.zachet1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button chooseCity;
    private TextView email;
    private TextView id;
    private TextView body;
    private TextView postID;
    private TextView name;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        body = findViewById(R.id.body);
        postID = findViewById(R.id.postID);
        name = findViewById(R.id.name);
        String url = "https://jsonplaceholder.typicode.com/comments";

        new GetUrlData().execute(url);

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                new GetUrlData().execute(url);
            }
        });
    }

    private class GetUrlData extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line).append("\n");
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONArray obj = new JSONArray(result);
                Random random = new Random();
                int numb = random.nextInt(500);
                email.setText(obj.getJSONObject(numb).getString("email"));
                body.setText("body: "  + obj.getJSONObject(numb).getString("body"));
                postID.setText("postID: "  + obj.getJSONObject(numb).getString("postId"));
                name.setText("name: "  + obj.getJSONObject(numb).getString("name"));
                id.setText("ID: " + obj.getJSONObject(numb).getString("id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
