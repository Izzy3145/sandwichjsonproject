package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        //the place where parsing json method is called, returning a sandwich object
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView mainNameTv = findViewById(R.id.main_name_tv);
        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);

        mainNameTv.setText(sandwich.getMainName());
        alsoKnownAsTv.setText(buildStringFromList(sandwich.getAlsoKnownAs()));
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());
        ingredientsTv.setText(buildStringFromList(sandwich.getIngredients()));

    }

    private String buildStringFromList(List<String> jsonArrayValues){

        String seperator = " , ";
        StringBuilder stringBuilder = new StringBuilder();

        if(jsonArrayValues != null) {
            for (String value : jsonArrayValues) {
                stringBuilder.append(value);
                stringBuilder.append(seperator);
            }

            return stringBuilder.toString();
        } else {
            return "n/a";
        }
    }
}
