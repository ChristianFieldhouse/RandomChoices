package com.example.randomchoices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private View mChoiceFrame;
    private ArrayList<String> fruit_array = new ArrayList<>(Arrays.asList(
            "Apples",
            "Oranges",
            "Bananas",
            "Mangoes",
            "Kiwis",
            "Grapes",
            "Pineapple",
            "Pears",
            "Strawberries",
            "Watermelon",
            "Blueberries",
            "Peaches",
            "Nectarines",
            "Avocado",
            "Cantaloupe",
            "Cherries",
            "Grapefruits",
            "Tangerines"
    ));
    private ArrayList<String> veg_array = new ArrayList<>(Arrays.asList(
            "Cucumbers",
            "Bell Peppers",
            "Beansprouts",
            "Carrots",
            "Baby Corn",
            "Mangetouts",
            "Cauliflower",
            "Broccoli",
            "Tomatoes",
            "Mushrooms",
            "Corn",
            "Peas",
            "Brussels Sprouts",
            "Asparagus",
            "Green Beans"
    ));
    private ArrayList<String> protein_array = new ArrayList<>(Arrays.asList(
            "Pork",
            "Chicken",
            "Beef",
            "Lamb",
            "Fish",
            "Eggs"
    ));
    private ArrayList<String> lunch_array = new ArrayList<>(Arrays.asList(
            "Gourmet House",
            "Nivedyam",
            "Chinese Canteen",
            "HK Fusion",
            "Market",
            "Tortilla"
    ));

    private View mResponseFrame;
    private TextView mResponseText;
    private Random generator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        generator = new Random();

        mChoiceFrame = findViewById(R.id.product_choice_frame);
        mResponseFrame = findViewById(R.id.response_frame);
        mResponseText = findViewById(R.id.response_text);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        mChoiceFrame.setVisibility(View.VISIBLE);

        final Button fruit_button = findViewById(R.id.fruit_button);
        fruit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mResponseText.setText(fruit_array.get(generator.nextInt(fruit_array.size())));
                showResponse();
            }
        });
        final Button veg_button = findViewById(R.id.veg_button);
        veg_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mResponseText.setText(veg_array.get(generator.nextInt(veg_array.size())));
                showResponse();
            }
        });
        final Button lunch_button = findViewById(R.id.lunch_button);
        lunch_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mResponseText.setText(lunch_array.get(generator.nextInt(lunch_array.size())));
                showResponse();
            }
        });
        final Button protein_button = findViewById(R.id.protein_button);
        protein_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mResponseText.setText(protein_array.get(generator.nextInt(protein_array.size())));
                showResponse();
            }
        });

        final Button acknowledge_button = findViewById(R.id.acknowledge_button);
        acknowledge_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showChoices();
            }
        });

        // Set up the user interaction to manually show or hide the system UI.
        mChoiceFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    protected void showResponse(){
        mResponseFrame.setVisibility(View.VISIBLE);
        mChoiceFrame.setVisibility(View.GONE);
    }

    protected void showChoices(){
        mResponseFrame.setVisibility(View.GONE);
        mChoiceFrame.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
