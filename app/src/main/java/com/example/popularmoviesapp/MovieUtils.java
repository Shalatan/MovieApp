package com.example.popularmoviesapp;

import android.util.Log;

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
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MovieUtils {
    private MovieUtils() {
    }

    public static ArrayList<Movie> fetchMovieDetails(String requestedUrl) {
        URL url = createUrl(requestedUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Movie> moviesData = fetchMovieDataFromJson(jsonResponse);
        return moviesData;
    }

    private static URL createUrl(String string) {
        URL url = null;
        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("HTTP ERROR RESPONSE CODE", String.valueOf(httpURLConnection.getResponseCode()));
            }
        } catch (IOException e) {
            Log.e("ERRORRR", "PORNLEM WHILE RETRIEVING DAATA");
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    public static ArrayList<Movie> fetchMovieDataFromJson(String stringJson) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        try {
            JSONObject baseJsonObject = new JSONObject(stringJson);
            JSONArray jsonArray = baseJsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String date = jsonObject.getString("release_date");
                String overview = jsonObject.getString("overview");
                String image = jsonObject.getString("poster_path");
                String rating = jsonObject.getString("vote_average");
                String id = jsonObject.getString("id");
                Movie movie = new Movie(title, rating, image, overview, date, id);
                arrayList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
