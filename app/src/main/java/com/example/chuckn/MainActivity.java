package com.example.chuckn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chuckn.entities.Joke;
import com.example.chuckn.entities.JokeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //declare variables
    private TextView mJoke;
    private ImageView mRefresh;
    private Joke joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declare XML elements
        mJoke = findViewById(R.id.joke);
        mRefresh = findViewById(R.id.refresh);
        //Set OnClickListener to refresh quotes
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                getAPI();
            }
        });
        getAPI();
    }
//method to call API
    protected void getAPI(){
        //Try-catch used to retrieve API
        try {
            //Retrofit used to convert the JSON class
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.chucknorris.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JokeService service = retrofit.create(JokeService.class);
            Call<Joke> jokeCall = service.getJoke();
            //Enqueue used to resolve NetworkOnMainThreadException
            jokeCall.enqueue(new Callback<Joke>() {
                @Override
                public void onResponse(Call<Joke> call, Response<Joke> response) {
                    if (response.isSuccessful()) {
                        joke = response.body();
                        mJoke.setText(joke.getValue());

                    } else {
                    }
                }

                @Override
                public void onFailure(Call<Joke> call, Throwable t) {
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
