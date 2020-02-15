package com.example.randomchoices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private ArrayList<String> categories = new ArrayList<>(Arrays.asList(
            "fruit",
            "veg",
            "protein",
            "lunch"
    ));

    // default options
    private ArrayList<ArrayList<String>> data = new ArrayList<>(Arrays.asList(
            fruit_array,
            veg_array,
            protein_array,
            lunch_array
    ));

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(
                getString(R.string.preference_file_key),  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }
    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(
                getString(R.string.preference_file_key),  Context.MODE_PRIVATE);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

    private void setResponse(String category){
        int index = categories.indexOf(category);
        mResponseText.setText(data.get(index).get(generator.nextInt(data.get(index).size())));
    }

    private View mResponseFrame;
    private View mChoiceFrame;
    private View mOptionsFrame;
    private String options_category;
    private TextView mResponseText;
    private Random generator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        final Context context = getApplicationContext();

        ArrayList<String> saved_categories = new ArrayList<>(Arrays.asList(loadArray("categories", context)));
        if (saved_categories.size() > 0){
            categories = saved_categories;
            data = new ArrayList<>(categories.size());
        }

        for (String category: categories){
            ArrayList<String> saved_list =  new ArrayList<>(Arrays.asList(loadArray(category, context)));
            if(saved_list.size() > 0){
                data.set(categories.indexOf(category), saved_list);
            }
        }

        generator = new Random();

        mChoiceFrame = findViewById(R.id.product_choice_frame);
        mResponseFrame = findViewById(R.id.response_frame);
        mOptionsFrame = findViewById(R.id.options_frame);
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
                setResponse("fruit");
                showResponse();
            }
        });
        final Button veg_button = findViewById(R.id.veg_button);
        veg_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResponse("veg");
                showResponse();
            }
        });
        final Button lunch_button = findViewById(R.id.lunch_button);
        lunch_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResponse("lunch");
                showResponse();
            }
        });
        final Button protein_button = findViewById(R.id.protein_button);
        protein_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResponse("protein");
                showResponse();
            }
        });

        final Button acknowledge_button = findViewById(R.id.acknowledge_button);
        acknowledge_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showChoices();
            }
        });

        final Button fruit_options_button = findViewById(R.id.fruit_options);
        fruit_options_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showOptions("fruit");
            }
        });

        final EditText add_option_text_input = (EditText) findViewById(R.id.add_option_text_input);
        add_option_text_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    data.get(categories.indexOf(options_category)).add(add_option_text_input.getText().toString());
                    saveArray(data.get(categories.indexOf(options_category)).toArray(new String[0]), options_category, context);
                    return true;
                }

                return false;
            }
        });

        final Button save_options_button = findViewById(R.id.save_options_button);
        save_options_button.setOnClickListener(new View.OnClickListener() {
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
        //mOptionsFrame.setVisibility(View.VISIBLE);
    }

    protected void showChoices(){
        mOptionsFrame.setVisibility(View.GONE);
        mResponseFrame.setVisibility(View.GONE);
        mChoiceFrame.setVisibility(View.VISIBLE);
    }

    protected void showOptions(String category){
        options_category = category;
        mChoiceFrame.setVisibility(View.GONE);
        mOptionsFrame.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
