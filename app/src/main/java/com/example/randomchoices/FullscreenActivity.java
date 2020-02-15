package com.example.randomchoices;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
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

    private ArrayList<Category> categories;
    private ArrayList<String> category_list;

    public boolean saveCategory(Category category, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(
                getString(R.string.preference_file_key),  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(category.name +"_size", category.options.size());
        for(int i = 0; i < category.options.size(); i++)
            editor.putString(category.name + "_" + i, category.options.get(i));
        return editor.commit();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Category loadCategory(String category_name, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(
                getString(R.string.preference_file_key),  Context.MODE_PRIVATE);
        int size = prefs.getInt(category_name + "_size", 0);
        ArrayList<String> options = new ArrayList<String>(0);
        for(int i = 0; i < size; i++)
            options.add(prefs.getString(category_name + "_" + i, null));
        return new Category(category_name, options, mContext);
    }

    public boolean saveCategoryList(ArrayList<String> list, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(
                getString(R.string.preference_file_key),  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("category_count", list.size());
        for(int i = 0; i < list.size(); i++)
            editor.putString("category_" + i, list.get(i));
        return editor.commit();
    }
    public ArrayList<String> loadCategoryList(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(
                getString(R.string.preference_file_key),  Context.MODE_PRIVATE);
        int size = prefs.getInt("category_count", 0);
        ArrayList<String> category_list = new ArrayList<String>(size);
        for(int i = 0; i < size; i++)
            category_list.add(prefs.getString("category_" + i, null));
        return category_list;
    }


    private void setResponse(String category_name){
        mResponseText.setText(categories.get(category_list.indexOf(category_name)).getRandom(generator));
    }

    private void addOption(String option){
        categories.get(category_list.indexOf(options_category)).options.add(option);
    }

    private void saveOptions(Context context){
        saveCategory(categories.get(category_list.indexOf(options_category)), context);
    }

    private void deleteCategory(){
        categories.get(category_list.indexOf(options_category)).delete();
        categories.remove(category_list.indexOf(options_category));
        category_list.remove(options_category);
    }

    private void saveAllCategories(Context context) {
        for (Category category : categories) {
            saveCategory(category, context);
        }
        saveCategoryList(category_list, context);
    }

    private View mResponseFrame;
    private View mChoiceFrame;
    private View mOptionsFrame;
    private View mAddCategoryFrame;

    private String options_category;
    private TextView mResponseText;
    private Random generator;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        final Context context = getApplicationContext();

        ArrayList<String> saved_categories = loadCategoryList(context);
        if (saved_categories.size() > 0){
            category_list = saved_categories;
            categories = new ArrayList<>(0);
            for(int i = 0; i < saved_categories.size(); i++){
                categories.add(loadCategory(category_list.get(i), context));
            }
        }
        else{
            category_list = new ArrayList<String>(Arrays.asList(
                    "fruit",
                    "veg"
                    ));
            categories = new ArrayList<>(0);
            categories.add(new Category("fruit", fruit_array, context));
            categories.add(new Category("veg", veg_array, context));
        }
        addCategoriesToLayout();

        generator = new Random();

        mChoiceFrame = findViewById(R.id.category_choice_frame);
        mResponseFrame = findViewById(R.id.response_frame);
        mOptionsFrame = findViewById(R.id.options_frame);
        mAddCategoryFrame = findViewById(R.id.category_options_frame);

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

        for(Category category: categories){
            makeCategoryResponseButtonWork(category);
            makeCategoryOptionButtonWork(category);
        }

        final Button acknowledge_button = findViewById(R.id.acknowledge_button);
        acknowledge_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showChoices();
            }
        });

        final EditText add_option_text_input = findViewById(R.id.add_option_text_input);
        add_option_text_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    addOption(add_option_text_input.getText().toString());
                    add_option_text_input.getText().clear();
                    return true;
                }
                return false;
            }
        });

        final Button save_options_button = findViewById(R.id.save_options_button);
        save_options_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveOptions(context);
                showChoices();
            }
        });

        final Button delete_category_button = findViewById(R.id.delete_category_button);
        delete_category_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteCategory();
                showChoices();
            }
        });

        final Button add_category_button = findViewById(R.id.add_category_button);
        add_category_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAddCategoryFrame();
            }
        });

        final EditText add_category_text_input = findViewById(R.id.add_category_text_input);
        add_category_text_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String input = add_category_text_input.getText().toString();
                    categories.add(new Category(input, context));
                    category_list.add(input);
                    add_category_text_input.getText().clear();
                    return true;
                }
                return false;
            }
        });

        final Button save_categories_button = findViewById(R.id.save_categories_button);
        save_categories_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveAllCategories(context);
                addCategoriesToLayout();
                for (Category category: categories){
                    makeCategoryResponseButtonWork(category);
                    makeCategoryOptionButtonWork(category);
                }
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

    private void makeCategoryResponseButtonWork(Category category) {
        final String category_name = category.name;
        category.category_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResponse(category_name);
                showResponse();
            }
        });
    }

    private void makeCategoryOptionButtonWork(Category category) {
        final String category_name = category.name; // name shouldn't change
        category.options_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showOptions(category_name);
            }
        });
    }

    private void addCategoriesToLayout(){
        LinearLayout buttons_layout = findViewById(R.id.category_choice_linear_layout);
        for (Category category: categories){
            if(findViewById(category.layout_id) == null){
                buttons_layout.addView(category.buttons, 0);
            }
        }
    }

    protected void showResponse(){
        mResponseFrame.setVisibility(View.VISIBLE);
        mChoiceFrame.setVisibility(View.GONE);
        //mOptionsFrame.setVisibility(View.VISIBLE);
    }

    protected void showChoices(){
        mOptionsFrame.setVisibility(View.GONE);
        mResponseFrame.setVisibility(View.GONE);
        mAddCategoryFrame.setVisibility(View.GONE);
        mChoiceFrame.setVisibility(View.VISIBLE);
    }

    protected void showOptions(String category){
        options_category = category;
        mChoiceFrame.setVisibility(View.GONE);
        mOptionsFrame.setVisibility(View.VISIBLE);
    }

    protected void showAddCategoryFrame(){
        mChoiceFrame.setVisibility(View.GONE);
        mAddCategoryFrame.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
