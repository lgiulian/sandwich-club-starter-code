package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
//            {"name":{"mainName":"Ham and cheese sandwich",
//                    "alsoKnownAs":[]
//                    },
//             "placeOfOrigin":"",
//             "description":"A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham
//                between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables
//                like lettuce, tomato, onion or pickle slices can also be included. Various kinds of
//                mustard and mayonnaise are also common.",
//             "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG",
//             "ingredients":["Sliced bread","Cheese","Ham"]
//            }
            
            JSONObject sandwichJsonObj = new JSONObject(json);
            JSONObject name = sandwichJsonObj.getJSONObject("name");
            sandwich.setMainName(name.optString("mainName"));
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            if (alsoKnownAsArray != null) {
                List<String> alsoKnownAsList = getJsonArrayAsList(alsoKnownAsArray);
                sandwich.setAlsoKnownAs(alsoKnownAsList);
            }
            sandwich.setPlaceOfOrigin(sandwichJsonObj.optString("placeOfOrigin"));
            sandwich.setDescription(sandwichJsonObj.optString("description"));
            sandwich.setImage(sandwichJsonObj.optString("image"));
            JSONArray ingredientsArray = sandwichJsonObj.getJSONArray("ingredients");
            if (ingredientsArray != null) {
                List<String> ingredientsList = getJsonArrayAsList(ingredientsArray);
                sandwich.setIngredients(ingredientsList);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing json", e);
            sandwich = null;
        }
        return sandwich;
    }

    /**
     * Construct a list of strings with the values taken from JSON array
     */
    private static List<String> getJsonArrayAsList(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.optString(i));
        }
        return list;
    }
}
