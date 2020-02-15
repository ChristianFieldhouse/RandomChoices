package com.example.randomchoices;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.randomchoices.R;

import java.util.ArrayList;
import java.util.Random;

public class Category {
    public ArrayList<String> options;
    public LinearLayout buttons;
    public Button category_button;
    public Button options_button;
    public int layout_id;
    public String name;
    public TextView response;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Category(String name, ArrayList<String> options, Context context){
        this.name = name;
        this.options = options;
        this.layout_id = View.generateViewId();

        buttons = new LinearLayout(context);
        buttons.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setId(layout_id);

        category_button = new Button(context);
        category_button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 5.0f));
        category_button.setText(name);

        options_button = new Button(context);
        options_button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        options_button.setText("...");

        buttons.addView(category_button);
        buttons.addView(options_button);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Category(String name, Context context){
        this(name, new ArrayList<String>(0), context);
    }

    public String getRandom(Random generator){
        return options.get(generator.nextInt(options.size()));
    }

    public void delete(){
        buttons.setVisibility(View.GONE);
    }
}
