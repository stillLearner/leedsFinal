package com.novo.zealot.UI.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.novo.zealot.Bean.Food;
import com.novo.zealot.R;

public class CustomActivity extends AppCompatActivity {
    private TextView txtCalories, txtEquivalentFood;
    private Button btnFinish;

    Food banana = new Food("Banana", R.drawable.diet_banana_icon, "Calories", "93", "kcal (per 100g)");
    Food apple = new Food("Apple", R.drawable.diet_apple_icon, "Calories", "53", "kcal (per 100g)");
    Food tomato = new Food("Tomato", R.drawable.diet_tomato_icon, "Calories", "15", "kcal (per 100g)");
    Food pineapple = new Food("Pineapple", R.drawable.diet_pineapple_icon, "Calories", "44", "kcal (per 100g)");
    Food grape = new Food("Grape", R.drawable.diet_grape_icon, "Calories", "45", "kcal (per 100g)");
    Food strawberry = new Food("Strawberry", R.drawable.diet_strawberry_icon, "Calories", "32", "kcal (per 100g)");
    Food cherry = new Food("Cherry", R.drawable.diet_cherry_icon, "Calories", "46", "kcal (per 100g)");
    Food pear = new Food("Pear", R.drawable.diet_pear_icon, "Calories", "51", "kcal (per 100g)");
    Food watermalon = new Food("Watermelon", R.drawable.diet_watermelon_icon, "Calories", "31", "kcal (per 100g)");
    Food mango = new Food("Mango", R.drawable.diet_mango_icon, "Calories", "35", "kcal (per 100g)");
    Food hazelnut = new Food("Hazelnut", R.drawable.diet_hazelnut_icon, "Calories", "611", "kcal (per 100g)");
    Food potato = new Food("Potato (Boiled)", R.drawable.diet_potato_icon, "Calories", "65", "kcal (per 100g)");
    Food chineseYam = new Food("Chinese Yam", R.drawable.diet_chinese_yam_icon, "Calories", "57", "kcal (per 100g)");
    Food carrot = new Food("Carrot", R.drawable.diet_carrot_icon, "Calories", "39", "kcal (per 100g)");
    Food corn = new Food("Corn", R.drawable.diet_corn_icon, "Calories", "112", "kcal (per 100g)");
    Food cucumber = new Food("Cucumber", R.drawable.diet_cucumber_icon, "Calories", "16", "kcal (per 100g)");
    Food eggplant = new Food("Eggplant", R.drawable.diet_eggplant_icon, "Calories", "23", "kcal (per 100g)");
    Food pepper = new Food("Pepper", R.drawable.diet_pepper_icon, "Calories", "38", "kcal (per 100g)");
    Food mushroom = new Food("Mushroom", R.drawable.diet_mushroom_icon, "Calories", "24", "kcal (per 100g)");
    Food pumpkin = new Food("Pumpkin", R.drawable.diet_pumpkin_icon, "Calories", "23", "kcal (per 100g)");
    Food cauliflower = new Food("Cauliflower", R.drawable.diet_cauliflower_icon, "Calories", "20", "kcal (per 100g)");
    Food chineseCabbage = new Food("Chinese Cabbage", R.drawable.diet_chinese_cabbage_icon, "Calories", "20", "kcal (per 100g)");
    Food rice = new Food("Rice", R.drawable.diet_rice_icon, "Calories", "116", "kcal (per 100g)");
    Food steamedBuns = new Food("Steamed Buns", R.drawable.diet_steamed_buns_icon, "Calories", "223", "kcal (per 100g)");
    Food bread = new Food("Bread", R.drawable.diet_bread_icon, "Calories", "313", "kcal (per 100g)");
    Food noodle = new Food("Noodle", R.drawable.diet_noodle_icon, "Calories", "301", "kcal (per 100g)");
    Food youtiao = new Food("Youtiao", R.drawable.diet_youtiao_icon, "Calories", "388", "kcal (per 100g)");
    Food cake = new Food("Cake", R.drawable.diet_cake_icon, "Calories", "379", "kcal (per 100g)");
    Food eggTart = new Food("Egg Tart", R.drawable.diet_egg_tart_icon, "Calories", "93", "kcal (per 100g)");
    Food shrimp = new Food("Shrimp", R.drawable.diet_shrimp_icon, "Calories", "92", "kcal (per 100g)");
    Food carp = new Food("Carp", R.drawable.diet_carp_icon, "Calories", "109", "kcal (per 100g)");
    Food pomfret = new Food("Pomfret", R.drawable.diet_pomfret_icon, "Calories", "140", "kcal (per 100g)");
    Food pork = new Food("Pork", R.drawable.diet_pork_icon, "Calories", "395", "kcal (per 100g)");
    Food chicken = new Food("Chicken", R.drawable.diet_chicken_icon, "Calories", "131", "kcal (per 100g)");
    Food beef = new Food("Beef", R.drawable.diet_beef_icon, "Calories", "106", "kcal (per 100g)");
    Food mutton = new Food("Mutton", R.drawable.diet_mutton_icon, "Calories", "203", "kcal (per 100g)");
    Food egg = new Food("Egg", R.drawable.diet_egg_icon, "Calories", "147", "kcal (per 100g)");
    Food redWine = new Food("Red Wine", R.drawable.diet_red_wine_icon, "Calories", "96", "kcal (per 100g)");
    Food mike = new Food("Milk", R.drawable.diet_mike_icon, "Calories", "54", "kcal (per 100g)");
    Food greenTea = new Food("Green Tea", R.drawable.diet_green_tea_icon, "Calories", "16", "kcal (per 100g)");
    Food porridge = new Food("Porridge", R.drawable.diet_porridge_icon, "Calories", "59", "kcal (per 100g)");
    Food oatmeal = new Food("Oatmeal", R.drawable.diet_oatmeal_icon, "Calories", "68", "kcal (per 100g)");

    // Constants
    private static final String TAG = "CustomActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume);

        // Find TextView elements
        txtCalories = findViewById(R.id.xh);
        txtEquivalentFood = findViewById(R.id.xh_result);

        // Get the passed 'caloriesRounded' value
        double caloriesBurned = getIntent().getDoubleExtra("caloriesRounded", 0.0);

        // Set the value of 'caloriesBurned' to TextView txtCalories
        txtCalories.setText(String.valueOf(caloriesBurned));

        // Call the compareCaloriesWithFood function and set its result to TextView txtEquivalentFood
        txtEquivalentFood.setText(compareCaloriesWithFood(caloriesBurned));

        // Set up the action for the 'Finish' button to return to the main screen
        btnFinish = findViewById(R.id.btn_resultFinish1);
        btnFinish.setOnClickListener(v -> finish());
    }


    public String compareCaloriesWithFood(double caloriesBurned) {
        Food[] foodList = {
                banana, apple, tomato, pineapple, grape, strawberry, cherry, pear, watermalon, mango,
                hazelnut, potato, chineseYam, carrot, corn, cucumber, eggplant, pepper, mushroom, pumpkin,
                cauliflower, chineseCabbage, rice, steamedBuns, bread, noodle, youtiao, cake, eggTart,
                shrimp, carp, pomfret, pork, chicken, beef, mutton, egg, redWine, mike, greenTea,
                porridge, oatmeal
        };

        Food maxMultipleFood = null;
        double maxMultiple = Double.MIN_VALUE;

        for (Food food : foodList) {
            double foodCalories = Double.parseDouble(food.getInclusion_content());
            double multiple = (caloriesBurned / foodCalories) * 100;

            if (multiple > maxMultiple) {
                maxMultiple = multiple;
                maxMultipleFood = food;
            }
        }

        if (maxMultipleFood != null) {
            return "Equivalent to " + maxMultiple + " times of " + maxMultipleFood.getName();
        } else {
            return "No consumption";
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }



}
