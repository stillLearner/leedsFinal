package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.novo.zealot.R;
import com.novo.zealot.Utils.DataUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;

public class firstUseActivity extends Activity {
    // profile photo
    ImageButton ib_avatarFirstTime;
    private Bitmap avatar;
    private static String path = "/sdcard/myAvatar/";
    String fileName = "personalData";
    TextView et_name, et_age, et_height, et_weight;
    private EditText et_nn, gy, dy, xt;
    Button btn_infoConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_use);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        et_nn = findViewById(R.id.et_nn);
        gy = findViewById(R.id.gy);
        dy = findViewById(R.id.dy);
        xt = findViewById(R.id.xt);
        // Get photo
        ib_avatarFirstTime = findViewById(R.id.ib_avatarFirstTime);
        ib_avatarFirstTime.setImageDrawable(getResources().getDrawable(R.drawable.icon_avatar_black_48dp));

        //Confirmation
        btn_infoConfirm = findViewById(R.id.btn_infoConfirm);
        btn_infoConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean datasAreCorrect = false;

                SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();
                String et_nn1 = et_nn.getText().toString();
                String gy1 = gy.getText().toString();
                String dy1 = dy.getText().toString();
                String xt1 = xt.getText().toString();
                editor.putString("name", name);
                editor.putString("age", age);
                editor.putString("height", height);
                editor.putString("weight", weight);
                editor.putString("et_nn", et_nn1);
                editor.putString("gy", gy1);
                editor.putString("dy", dy1);
                editor.putString("xt", xt1);
                editor.apply();


                if (name.length() == 0 || age.length() == 0 || height.length() == 0 || weight.length() == 0) {
                    Toast.makeText(firstUseActivity.this, "Data cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (name.length() > 8) {
                    Toast.makeText(firstUseActivity.this, "Nickname cannot exceed 8 bytes!", Toast.LENGTH_SHORT).show();
                } else if (!DataUtil.isNumeric(age) || !DataUtil.isNumeric(height) || !DataUtil.isNumeric(weight)
                        || Integer.parseInt(age) > 100 || Integer.parseInt(age) < 0
                        || Integer.parseInt(height) > 300 || Integer.parseInt(height) < 0
                        || Integer.parseInt(weight) > 200 || Integer.parseInt(weight) < 0) {
                    Toast.makeText(firstUseActivity.this, "Invalid input for age, height, or weight!", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });

        // Touch the photo to change
        ib_avatarFirstTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });


        et_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(firstUseActivity.this);
                picker.setOffset(2); // Offset
                picker.setRange(10, 100); // Number range
                picker.setSelectedItem(20);
                picker.setLabel("years");
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        et_age.setText(option);
                    }
                });
                picker.show();
            }
        });

        et_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(firstUseActivity.this);
                picker.setOffset(2); // Offset
                picker.setRange(145, 200); // Number range
                picker.setSelectedItem(170);
                picker.setLabel("cm");
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        et_height.setText(option);
                    }
                });
                picker.show();
            }
        });

        et_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(firstUseActivity.this);
                picker.setOffset(2); // Offset
                picker.setRange(40, 200); // Number range
                picker.setSelectedItem(60);
                picker.setLabel("kg");
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        et_weight.setText(option);
                    }
                });
                picker.show();
            }
        });

    }

    // Choose your photos from local file
    private void showTypeDialog() {
        // Display dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(firstUseActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(firstUseActivity.this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() { // Select from gallery
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                // Open file
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() { // Use camera
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "avatar.jpg")));
                startActivityForResult(intent2, 2); // Open with startActivityForResult
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
                        ib_avatarFirstTime.setImageBitmap(avatar);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Calling the system's cropping function
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        // aspect ratio
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // index of cropped image
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // Check if SD card is available
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs(); // Create directory
        String fileName = path + "avatar.jpg"; // Image name
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b); // Write data to file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close stream
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save personal information locally
     */
    private void savePersonalData() {
        OutputStream outputStream = null;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            String name = et_name.getText().toString() + "\n";
            String age = et_age.getText().toString() + "\n";
            String height = et_height.getText().toString() + "\n";
            String weight = et_weight.getText().toString() + "\n";
            String nn = et_nn.getText().toString() + "\n";
            bufferedWriter.write(name);
            bufferedWriter.write(age);
            bufferedWriter.write(height);
            bufferedWriter.write(weight);
            bufferedWriter.write(nn);
            bufferedWriter.close();

            // Mark as not first use
            String isFirstUse = "false";
            OutputStream firstUseOS = openFileOutput("config", Context.MODE_PRIVATE);

            // This section was copied and pasted without changing, causing a bug that took three hours to find. Commemorating May 30, 2019, 18:50:18
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(firstUseOS));
            bw.write(isFirstUse);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Save data on exit
        savePersonalData();
    }
}
