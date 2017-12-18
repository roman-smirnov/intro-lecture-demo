package com.roman.lectureapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MemeActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;
    EditText editText;
    TextView topText;
    String img_url = "https://i.imgflip.com/o5yxu.jpg";
    String MY_IMAGE = "my_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_layout);
        imageView = findViewById(R.id.image);
        button = findViewById(R.id.button);
        topText = findViewById(R.id.top_text);
        editText = findViewById(R.id.edit_text);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                set the text
                topText.setText(editText.getText());
//                make the text visible
                topText.setVisibility(View.VISIBLE);

//                get the image from disk if it already exists
                if (isSaved()) {
                    Picasso.with(MemeActivity.this)
                            .load(MY_IMAGE)
                            .into(imageView);
                }else {
//                load the image
                    Picasso.with(MemeActivity.this)
                            .load(img_url)
                            .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                //                get the bitmap
                                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                                    Bitmap bitmap = drawable.getBitmap();
                                    saveImage(bitmap);
                                }

                                @Override
                                public void onError() {
                                    Toast.makeText(MemeActivity.this, "failed to get image", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private boolean isSaved() {
        File file = new File(MY_IMAGE);
        return file.exists();
    }

    private void saveImage(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(MY_IMAGE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.out.println("not saved");
            }
        }
    }

}
