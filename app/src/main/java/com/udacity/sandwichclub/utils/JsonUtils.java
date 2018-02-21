package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonUtils {

    private static String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        // method to parse Json string into Sandwich object

        String sandwichMainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String sandwichImage;
        List<String> ingredients;
        Sandwich selectedSandwich = null;

        try {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject sandwichJsonName = sandwichObject.getJSONObject("name");
            sandwichMainName = sandwichJsonName.getString("mainName");

            //needs sorting
            JSONArray alsoKnownAsArray = sandwichJsonName.getJSONArray("alsoKnownAs");
            alsoKnownAs = formatArrayList(alsoKnownAsArray);

            placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            description = sandwichObject.getString("description");
            sandwichImage = sandwichObject.getString("image");

            //needs sorting
            JSONArray ingredientsArray = sandwichObject.getJSONArray("ingredients");
            ingredients = formatArrayList(ingredientsArray);

            selectedSandwich = new Sandwich(sandwichMainName, alsoKnownAs, placeOfOrigin,
                    description, sandwichImage, ingredients);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON results", e);
        }
        return selectedSandwich;
    }

    //method to format arrays within Json
    private static List<String> formatArrayList(JSONArray jsonArray) throws JSONException {
        List<String> jsonArrayInList = null;
        if (jsonArray.length() == 0) {
            return null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonArrayInList.add(jsonArray.getString(i));
        }
        return jsonArrayInList;
    }
}
