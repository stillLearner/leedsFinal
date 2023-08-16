package com.novo.zealot.UI.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.novo.zealot.DB.SPSaveLogin;
import com.novo.zealot.R;
//import com.novo.zealot.UI.Activity.DietActivity;
import com.novo.zealot.UI.Activity.SettingActivity;
import com.novo.zealot.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Map;

import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    Context context;
    TickerView tv_sumOfDistance, tv_sumOfTimes;
    TextView bmr1;
    int sumOfDistance = 0, sumOfTimes = 0;
    ImageButton ib_avatar;
    ImageButton ib_editName, ib_editAge, ib_editHeight, ib_editWeight, ib_editgy, ib_editdy, ib_editxt;
    private Bitmap avatar;

    TextView tv_name, tv_age, tv_height, tv_weight, tdee,  tv_gy,  tv_dy, tv_xt;
    private static String path = "/sdcard/myAvatar/";
    String fileName = "personalData";
    RelativeLayout rl_setting, ystj;


    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ProfileFragment Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        context = view.getContext();

        //Initialize
        tv_sumOfDistance = view.findViewById(R.id.tv_sumOfDistance);
        tv_sumOfDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_sumOfDistance.setAnimationDuration(1000);


        tv_sumOfTimes = view.findViewById(R.id.tv_sumOfTimes);
        tv_sumOfTimes.setCharacterLists(TickerUtils.provideNumberList());
        tv_sumOfTimes.setAnimationDuration(1000);
//        ystj = view.findViewById(R.id.ystj);

        tv_name = view.findViewById(R.id.tv_name);
        tv_age = view.findViewById(R.id.tv_age);
        tv_height = view.findViewById(R.id.tv_height);
        tv_weight = view.findViewById(R.id.tv_weight);
        tv_gy = view.findViewById(R.id.ib_gy);
        tv_dy = view.findViewById(R.id.ib_dy);
        tv_xt = view.findViewById(R.id.ib_xt);
        bmr1 = view.findViewById(R.id.bmr);
        tdee = view.findViewById(R.id.tdee);
        calculateBMR();

        ib_avatar = view.findViewById(R.id.ib_avatar);

        ib_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });

//        ystj.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, DietActivity.class));
//            }
//        });

        ib_editName = view.findViewById(R.id.ib_editName);
        ib_editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(view.getContext()).builder()
                        .setTitle("Please enter")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("Confirm", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = myAlertInputDialog.getResult();
                        if (name.length() == 0) {
                            Toast.makeText(view.getContext(), "Nickname cannot be empty!", Toast.LENGTH_SHORT).show();
                        } else if (name.length() > 8) {
                            Toast.makeText(view.getContext(), "Nickname cannot exceed 8 bytes!", Toast.LENGTH_SHORT).show();
                        } else {
                            tv_name.setText(name);
                            Toast.makeText(view.getContext(), "Modified successfully", Toast.LENGTH_SHORT).show();
                        }
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });

        Map<String, String> userInfo = SPSaveLogin.getUserInfo(getContext());
        ib_editAge = view.findViewById(R.id.ib_editAge);
        ib_editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(getActivity());
                picker.setOffset(2); // Offset
                picker.setRange(10, 100); // Number range
                int age = Integer.parseInt(userInfo.get(SPSaveLogin.KEY_AGE));
                Log.d("DEBUG", "Retrieved age from SharedPreferences: " + age);
                picker.setSelectedItem(age);

                picker.setLabel("years");
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_age.setText(option);
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SPSaveLogin.PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SPSaveLogin.KEY_AGE, option.trim());
                        editor.apply();
                    }
                });
                picker.show();
            }
        });


        ib_editHeight = view.findViewById(R.id.ib_editHeight);
        ib_editHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(getActivity());
                picker.setOffset(2);
                picker.setRange(145, 200);
                picker.setSelectedItem(170);
                picker.setLabel("cm");
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_height.setText(option);
                    }
                });
                picker.show();
            }
        });

        ib_editgy = view.findViewById(R.id.ib_editgy);
        ib_editgy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(getActivity());
                picker.setOffset(2);
                picker.setRange(50, 200);
                picker.setSelectedItem(120);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_gy.setText(option);
                    }
                });
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("gy", tv_gy.toString().trim());
                editor.apply();
                picker.show();

            }
        });

        //修改dy
        ib_editdy = view.findViewById(R.id.ib_edidy);
        ib_editdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(getActivity());
                picker.setOffset(2);
                picker.setRange(0, 100);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_dy.setText(option);
                    }
                });
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("dt", tv_dy.toString().trim());
                editor.apply();
                picker.show();
            }
        });

        //修改xt
        ib_editxt = view.findViewById(R.id.ib_editxt);
        ib_editxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(getActivity());
                picker.setOffset(2);
                picker.setRange(0, 100);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_xt.setText(option);
                    }
                });
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("xt", tv_xt.toString().trim());
                editor.apply();
                picker.show();
            }
        });

        ib_editWeight = view.findViewById(R.id.ib_editWeight);
        ib_editWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(getActivity());
                picker.setOffset(2);//偏移量
                picker.setRange(40, 200);//数字范围
                picker.setSelectedItem(60);
                picker.setLabel("kg");
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_weight.setText(option);
                    }
                });
                picker.show();
            }
        });

        initView();

        rl_setting = view.findViewById(R.id.rl_setting);
        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return view;
    }

    private void initView() {
        Bitmap bt = BitmapFactory.decodeFile(path + "avatar.jpg");
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            ib_avatar.setImageDrawable(drawable);
        } else {
            ib_avatar.setImageDrawable(getResources().getDrawable(R.drawable.icon_avatar_black_48dp));
        }

        readPersonalData();
    }

    // Get user information from SharedPreferences and calculate BMR
    private void calculateBMR() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "");
        String ageStr = sharedPreferences.getString("age", "");
        String heightStr = sharedPreferences.getString("height", "");
        String weightStr = sharedPreferences.getString("weight", "");
        String genderStr = sharedPreferences.getString("et_nn", "");

        if (ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || genderStr.isEmpty()) {
            return;
        }

        int age = Integer.parseInt(ageStr);
        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);

        double bmr = 17.31;
        if (genderStr.equals("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String formattedBmr = decimalFormat.format(bmr);
        bmr1.setText(formattedBmr);

        // Calculate TDEE and daily energy requirement based on different activity levels
        // Total number of queries
        sumOfTimes = GlobalUtil.getInstance().databaseHelper.queryNumOfTimes();
        // Activity level based on total number of times
        ActivityLevel activityLevel;
        if (sumOfTimes >= 6) {
            activityLevel = ActivityLevel.EXTRA_ACTIVE;
        } else if (sumOfTimes >= 3) {
            activityLevel = ActivityLevel.VERY_ACTIVE;
        } else if (sumOfTimes >= 1) {
            activityLevel = ActivityLevel.MODERATELY_ACTIVE;
        } else {
            activityLevel = ActivityLevel.LIGHTLY_ACTIVE;
        }

        // Calculation of TDEE and daily energy requirements based on activity level
        double tdee1 = calculateTDEE(bmr, activityLevel);
        double dailyCalories = tdee1 - 500; // Reduce 500 calories per day to achieve 0.5kg weight loss per week

        String formattedtdee1 = decimalFormat.format(tdee1);
        tdee.setText(String.valueOf(formattedtdee1));

    }

    private double calculateTDEE(double bmr, ActivityLevel activityLevel) {
        double tdee;
        switch (activityLevel) {
            case LIGHTLY_ACTIVE:
                tdee = bmr * 1.375;
                break;
            case MODERATELY_ACTIVE:
                tdee = bmr * 1.55;
                break;
            case VERY_ACTIVE:
                tdee = bmr * 1.725;
                break;
            case EXTRA_ACTIVE:
                tdee = bmr * 1.9;
                break;
            default:
                tdee = bmr;
        }
        return tdee;
    }

    private enum ActivityLevel {
        LIGHTLY_ACTIVE,
        MODERATELY_ACTIVE,
        VERY_ACTIVE,
        EXTRA_ACTIVE
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "avatar.jpg")));
                startActivityForResult(intent2, 2);
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/avatar.jpg");
                    cropPhoto(Uri.fromFile(temp));
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    avatar = extras.getParcelable("data");
                    if (avatar != null) {
                        setPicToView(avatar);
                        ib_avatar.setImageBitmap(avatar);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();
        String fileName = path + "avatar.jpg";
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 存储个人信息至本地
     */
    private void savePersonalData() {
        OutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            String name = tv_name.getText().toString() + "\n";
            String age = tv_age.getText().toString() + "\n";
            String height = tv_height.getText().toString() + "\n";
            String weight = tv_weight.getText().toString() + "\n";

            bufferedWriter.write(name);
            bufferedWriter.write(age);
            bufferedWriter.write(height);
            bufferedWriter.write(weight);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取个人信息
     */
    private void readPersonalData() {
        StringBuilder sBuilder = new StringBuilder();

        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String name, age, height, weight;

            name = bufferedReader.readLine();
            age = bufferedReader.readLine();
            height = bufferedReader.readLine();
            weight = bufferedReader.readLine();

            tv_name.setText(name);
            tv_age.setText(age);
            tv_height.setText(height);
            tv_weight.setText(weight);

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Query total distance and set to the control
        sumOfDistance = GlobalUtil.getInstance().databaseHelper.queryAllDistance();
        // Format XX.X
        sumOfDistance /= 100;
        double sumOfDistanceInKM = sumOfDistance / 10.0;
        tv_sumOfDistance.setText(sumOfDistanceInKM + "");

        // Query total times
        sumOfTimes = GlobalUtil.getInstance().databaseHelper.queryNumOfTimes();
        tv_sumOfTimes.setText(sumOfTimes + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Save data when exiting
        savePersonalData();
    }
}
