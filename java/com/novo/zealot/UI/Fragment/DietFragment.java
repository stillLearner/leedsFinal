package com.novo.zealot.UI.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.novo.zealot.Adapter.FoodAdapter;
import com.novo.zealot.Bean.Food;
import com.novo.zealot.R;

import java.util.ArrayList;
import java.util.List;

public class DietFragment extends Fragment {
    private List<Food> foodList = new ArrayList<>();
    private TextView bodyIndexText1, bodyIndexText2, bodyIndexText3, bodyIndexText4;
    private String bodyIndexText11, bodyIndexText22, bodyIndexText33, bodyIndexText44;

    String heightStr, weightStr, highBloodPressureStr, lowBloodPressureStr, bloodSugarStr, textToShow;
    int height, weight, highBloodPressure, lowBloodPressure, bodyIndex;
    float bloodSugar, bmiOfBody;

    /*食物清单*/
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diet_layout, container, false);
        bodyIndexText1 = (TextView) view.findViewById(R.id.body_index_1);
        bodyIndexText2 = (TextView) view.findViewById(R.id.body_index_2);
        bodyIndexText3 = (TextView) view.findViewById(R.id.body_index_3);
        bodyIndexText4 = (TextView) view.findViewById(R.id.body_index_4);

        bodyIndex = judgeBodyIndex();
        textToShow = getBmiOfBody(height, weight);

        switch (bodyIndex) {
            case 0:
                textToShow = textToShow + "Input for blood pressure or blood sugar is incorrect, please re-enter!!";
                bodyIndexText1.setText(textToShow);
                break;
            case 1:
                bodyIndexText33 = "Blood Pressure: Normal";
                bodyIndexText44 = "Blood Sugar: Normal";
                initFoodNormalNormal();
                break;
            case 2:
                bodyIndexText33 = "Blood Pressure: Normal";
                bodyIndexText44 = "Blood Sugar: Low";
                initFoodNormalLow();
                break;
            case 3:
                bodyIndexText33 = "Blood Pressure: Normal";
                bodyIndexText44 = "Blood Sugar: High";
                initFoodNormalHigh();
                break;
            case 4:
                bodyIndexText33 = "Blood Pressure: High";
                bodyIndexText44 = "Blood Sugar: Normal";
                initFoodHighNormal();
                break;
            case 5:
                bodyIndexText33 = "Blood Pressure: High";
                bodyIndexText44 = "Blood Sugar: Low";
                initFoodHighLow();
                break;
            case 6:
                bodyIndexText33 = "Blood Pressure: High";
                bodyIndexText44 = "Blood Sugar: High";
                initFoodHighHigh();
                break;
            case 7:
                bodyIndexText33 = "Blood Pressure: Low";
                bodyIndexText44 = "Blood Sugar: Normal";
                initFoodLowNormal();
                break;
            case 8:
                bodyIndexText33 = "Blood Pressure: Low";
                bodyIndexText44 = "Blood Sugar: Low";
                initFoodLowLow();
                break;
            case 9:
                bodyIndexText33 = "Blood Pressure: Low";
                bodyIndexText44 = "Blood Sugar: High";
                initFoodLowHigh();
                break;
            case 10:
                bodyIndexText33 = "Unable to recommend diet based on blood pressure and blood sugar data, please update!";
                break;
            default:
                break;
        }

        bodyIndexText3.setText(bodyIndexText33);
        bodyIndexText4.setText(bodyIndexText44);

        FoodAdapter adapter = new FoodAdapter(view.getContext(), R.layout.food_item, foodList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        return view;
    }


    private void initFoodNormalNormal() {  //正常血压 正常血糖
        foodList.clear();
        foodList.add(apple);
        foodList.add(banana);
        foodList.add(tomato);
        foodList.add(pineapple);
        foodList.add(shrimp);
        foodList.add(chineseYam);
        foodList.add(chineseCabbage);
        foodList.add(chicken);
        foodList.add(grape);
        foodList.add(carrot);
        foodList.add(rice);
        foodList.add(beef);
        foodList.add(strawberry);
        foodList.add(corn);
        foodList.add(steamedBuns);
        foodList.add(mutton);
        foodList.add(cherry);
        foodList.add(cucumber);
        foodList.add(bread);
        foodList.add(redWine);
        foodList.add(pear);
        foodList.add(eggplant);
        foodList.add(noodle);
        foodList.add(mike);
        foodList.add(watermalon);
        foodList.add(pepper);
        foodList.add(youtiao);
        foodList.add(greenTea);
        foodList.add(mango);
        foodList.add(mushroom);
        foodList.add(cake);
        foodList.add(porridge);
        foodList.add(hazelnut);
        foodList.add(pumpkin);
        foodList.add(pomfret);
        foodList.add(oatmeal);
        foodList.add(potato);
        foodList.add(cauliflower);
        foodList.add(pork);
        foodList.add(carp);
        foodList.add(egg);
    }

    private void initFoodNormalLow() {  //正常血压 低血糖
        foodList.clear();
        /*低血糖食物*/
        foodList.add(oatmeal);
        foodList.add(eggTart);
        foodList.add(cake);
        foodList.add(pepper);
        foodList.add(redWine);
        foodList.add(egg);
        foodList.add(mike);
        foodList.add(shrimp);
        foodList.add(carp);
        foodList.add(apple);
        foodList.add(pear);
        foodList.add(beef);
        foodList.add(cauliflower);
        foodList.add(carrot);
        foodList.add(cucumber);
        foodList.add(corn);
        foodList.add(watermalon);
        foodList.add(cherry);
        foodList.add(pomfret);
    }

    private void initFoodNormalHigh() {  //正常血压 高血糖
        foodList.clear();
        foodList.add(oatmeal);
        foodList.add(carrot);
        foodList.add(eggplant);
        foodList.add(mushroom);
        foodList.add(cauliflower);
        foodList.add(chineseCabbage);
        foodList.add(beef);
        foodList.add(potato);
        foodList.add(chicken);
        foodList.add(pumpkin);
        foodList.add(chineseYam);

    }

    private void initFoodHighNormal() {  //高血压 正常血糖
        foodList.clear();
        foodList.add(greenTea);
        foodList.add(apple);
        foodList.add(carrot);
        foodList.add(mike);
        foodList.add(corn);
        foodList.add(pomfret);
        foodList.add(potato);
        foodList.add(shrimp);
        foodList.add(eggplant);
        foodList.add(tomato);
        foodList.add(pear);
        foodList.add(pineapple);
        foodList.add(banana);
    }

    private void initFoodHighLow() {  //高血压 低血糖
        foodList.clear();
        foodList.add(oatmeal);
        foodList.add(cauliflower);
        foodList.add(mike);
        foodList.add(carrot);
        foodList.add(chineseCabbage);
    }

    private void initFoodHighHigh() {  //高血压 高血糖
        foodList.clear();
        foodList.add(greenTea);
        foodList.add(apple);
        foodList.add(carrot);
        foodList.add(mike);
        foodList.add(corn);
        foodList.add(pomfret);
        foodList.add(potato);
        foodList.add(shrimp);
        foodList.add(eggplant);
        foodList.add(tomato);
        foodList.add(pear);
        foodList.add(pineapple);
        foodList.add(banana);
        foodList.add(oatmeal);
        foodList.add(mushroom);
        foodList.add(cauliflower);
        foodList.add(chineseCabbage);
        foodList.add(beef);
        foodList.add(chicken);
        foodList.add(pumpkin);
        foodList.add(chineseYam);
    }

    private void initFoodLowNormal() {  //低血压 正常血糖
        foodList.clear();
        foodList.add(cherry);
        foodList.add(apple);
        foodList.add(mutton);
        foodList.add(chicken);
        foodList.add(mushroom);
        foodList.add(carp);
        foodList.add(mike);
        foodList.add(shrimp);
        foodList.add(cake);
        foodList.add(pepper);
        foodList.add(pomfret);
        foodList.add(egg);
    }

    private void initFoodLowLow() {  //低血压 低血糖
        foodList.clear();
        foodList.add(pepper);
        foodList.add(pomfret);
        foodList.add(watermalon);
        foodList.add(cherry);
        foodList.add(mushroom);
        foodList.add(carp);
        foodList.add(chicken);
        foodList.add(apple);
        foodList.add(mutton);
        foodList.add(mike);
        foodList.add(shrimp);
        foodList.add(cake);
        foodList.add(pumpkin);
        foodList.add(egg);
    }

    private void initFoodLowHigh() {  //低血压 高血糖
        foodList.clear();
        foodList.add(oatmeal);
        foodList.add(shrimp);
        foodList.add(egg);
        foodList.add(pomfret);
        foodList.add(carp);
    }

    private int judgeBodyIndex() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);

        String str_lowBloodPressure = sharedPreferences.getString("dy", "");
        String str_highBloodPressure = sharedPreferences.getString("gy", "");
        String str_height = sharedPreferences.getString("height", "");
        String str_weight = sharedPreferences.getString("weight", "");
        String str_bloodSugar = sharedPreferences.getString("xt", "");

        height = strToInt(str_height);
        weight = strToInt(str_weight);
        bloodSugar = strToFloat(str_bloodSugar);
        highBloodPressure = strToInt(str_highBloodPressure);
        lowBloodPressure = strToInt(str_lowBloodPressure);

        // Define health conditions
        boolean isHeightWeightValid = height != 0 && weight != 0;
        boolean isBloodSugarNormal = bloodSugar > 3.9 && bloodSugar < 6.1;
        boolean isBloodSugarLow = bloodSugar > 1 && bloodSugar <= 3.9;
        boolean isBloodSugarHigh = bloodSugar >= 6.1 && bloodSugar < 11;
        boolean isBloodPressureNormal = highBloodPressure > 90 && highBloodPressure < 140 && lowBloodPressure > 60 && lowBloodPressure < 90;
        boolean isHighBloodPressure = highBloodPressure >= 140 && lowBloodPressure >= 90 && lowBloodPressure < 140;
        boolean isLowBloodPressure = highBloodPressure > 60 && highBloodPressure <= 90 && lowBloodPressure > 30 && lowBloodPressure <= 60;

        if (!isHeightWeightValid || bloodSugar == 0 || highBloodPressure == 0 || lowBloodPressure == 0) {
            return 0; // Incomplete user data
        } else if (isBloodPressureNormal && isBloodSugarNormal) {
            return 1; // Normal blood pressure and blood sugar
        } else if (isBloodPressureNormal && isBloodSugarLow) {
            return 2; // Normal blood pressure, low blood sugar
        } else if (isBloodPressureNormal && isBloodSugarHigh) {
            return 3; // Normal blood pressure, high blood sugar
        } else if (isHighBloodPressure && isBloodSugarNormal) {
            return 4; // High blood pressure, normal blood sugar
        } else if (isHighBloodPressure && isBloodSugarLow) {
            return 5; // High blood pressure, low blood sugar
        } else if (isHighBloodPressure && isBloodSugarHigh) {
            return 6; // High blood pressure, high blood sugar
        } else if (isLowBloodPressure && isBloodSugarNormal) {
            return 7; // Low blood pressure, normal blood sugar
        } else if (isLowBloodPressure && isBloodSugarLow) {
            return 8; // Low blood pressure, low blood sugar
        } else if (isLowBloodPressure && isBloodSugarHigh) {
            return 9; // Low blood pressure, high blood sugar
        } else {
            return 10; // Bad data
        }
    }


    private String getBmiOfBody(int height, int weight) {
        if (height <= 0 || weight <= 0) {
            return "The input for height or weight is incorrect, cannot calculate your Body Mass Index (BMI)!\n";
        }

        float bmi = calculateBmi(height, weight);
        return categorizeBmi(bmi);
    }

    private float calculateBmi(int height, int weight) {
        float heightInMeters = (float) height / 100.0f;
        return weight / (heightInMeters * heightInMeters);
    }

    private String categorizeBmi(float bmi) {
        String category = "";
        String message = "";

        if (bmi < 18.5) {
            category = "Underweight";
            message = "Consider gaining weight.";
        } else if (bmi < 25) {
            category = "Normal";
            message = "Please maintain.";
        } else if (bmi < 30) {
            category = "Overweight";
            message = "Consider losing weight.";
        } else if (bmi < 35) {
            category = "Obesity";
            message = "Please lose weight.";
        } else if (bmi < 40) {
            category = "Severe Obesity";
            message = "Please lose weight!";
        } else {
            category = "Morbid Obesity";
            message = "Please lose weight!!";
        }

        bodyIndexText1.setText(String.format("BMI: %.2f", bmi));
        bodyIndexText2.setText(category);

        return String.format("Your BMI is %.2f, categorized as %s. %s\n", bmi, category, message);
    }

    private int strToInt(String str) {
        if (str == null || str.trim().equals("")) {
            return 0;
        }

        int result = 0;
        char[] chars = str.trim().toCharArray();

        for (char c : chars) {
            if (c > '9' || c < '0') {
                return 0;
            }
            result = result * 10 + (c - '0');
        }

        return result;
    }

    private float strToFloat(String str) {
        if (str == null || str.trim().equals("")) {
            return 0;
        }

        int resultInt = 0;
        float resultFloat = 0;
        char[] chars = str.trim().toCharArray();
        int i;

        for (i = 0; i < chars.length && chars[i] != '.'; i++) {
            if (chars[i] <= '9' && chars[i] >= '0') {
                resultInt = resultInt * 10 + (chars[i] - '0');
            } else {
                return 0;
            }
        }

        int cnt = 1;
        for (i++; i < chars.length; i++) {
            resultFloat += Math.pow(0.1, cnt) * (chars[i] - '0');
            cnt++;
        }


        return resultFloat + resultInt;
    }
}