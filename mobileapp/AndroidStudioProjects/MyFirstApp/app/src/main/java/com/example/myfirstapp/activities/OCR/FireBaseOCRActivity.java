package com.example.myfirstapp.activities.OCR;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myfirstapp.R;
import com.example.myfirstapp.Storage.SharedPrefManager;
import com.example.myfirstapp.activities.Main.MainActivity;
import com.example.myfirstapp.activities.Main.POCHomeActivity;
import com.example.myfirstapp.activities.Main.POCSubmit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class FireBaseOCRActivity extends AppCompatActivity {

    Button captureImageBtn, detectTextBtn, greyscalebtn;
    ImageButton homeButton;
    ImageView imageView;
    TextView resultView, information, information2, information3;
    EditText timeView, sysView, diaView, heartView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap, greyBitmap, bmpBinary;
    int contrast = 2;
    int brightness = -50;
    float white = 0;
    float black = 0;



    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        timeView = findViewById(R.id.Time);
        sysView = findViewById(R.id.Systolic);
        diaView = findViewById(R.id.Diastolic);
        heartView = findViewById(R.id.HeartRate);
        captureImageBtn = findViewById(R.id.capture_image_btn);
        detectTextBtn = findViewById(R.id.detect_text_image_btn);
        homeButton = findViewById(R.id.homeButton2);
        resultView = findViewById(R.id.resultView);

//        greyscalebtn = findViewById(R.id.greyscale);
        imageView = findViewById(R.id.image_view);
        information = findViewById(R.id.textInfo);
        information2 = findViewById(R.id.textInfo2);
        information3 = findViewById(R.id.textInfo3);

        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //storage permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};



        captureImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });


        detectTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectTextFromImage(bmpBinary);

            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });

    }


    public void confirm()
    {


        android.app.AlertDialog.Builder termsDialogBuilder = new android.app.AlertDialog.Builder(this);
        termsDialogBuilder.setTitle("Tutorial");
        termsDialogBuilder.setMessage("Please ensure that you have read the information on how to correctly submit an image");
        termsDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                showImageImportDialog();

            }
        });


        android.app.AlertDialog alertDialog = termsDialogBuilder.create();
        alertDialog.show();

    }


    private void showImageImportDialog() {
        String[] items = {" Camera", " Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //camera option clicked
                    if (!checkCameraPermission()){
                        //camera permission not allowed, request it
                        requestCameraPermission();
                    }
                    else {
                        //permission allowed, take picture
                        pickCamera();
                    }
                }
                if (which == 1){
                    //gallery clicked
                    if (!checkStoragePermission()) {
                        //camera permission not allowed, request it
                        requestStoragePermission();
                    }
                    else {
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //got image from camera
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //got image form gallery now crop it
                assert data != null;
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }


            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //got image form camera now crop it
                assert data != null;
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }//get cropped image

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri resultUri = result.getUri(); //get image uri

                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    toGrayscale(imageBitmap);

                    toBinary(greyBitmap);
                    detectTextBtn.setVisibility(View.VISIBLE);
                    information.setVisibility(View.INVISIBLE);
                    information2.setVisibility(View.INVISIBLE);
                    information3.setVisibility(View.INVISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //if there is an error show it
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void detectTextFromImage(Bitmap bitmap) {
        if (bitmap != null){
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionTextDetector firebaseVisionTextRecognizer = FirebaseVision.getInstance().getVisionTextDetector();
            firebaseVisionTextRecognizer.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    displayTextFromImage(firebaseVisionText);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FireBaseOCRActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                    Log.d("Error ", e.getMessage());
                }
            });
         }
    }



    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {

        if (black/white > 0.7) {
            Toast.makeText(this, "Error with image, Please try adjusting the distance and/or lighting", Toast.LENGTH_SHORT).show();
        } else {
            List<FirebaseVisionText.Block> blocklist = firebaseVisionText.getBlocks();
            if (blocklist.size() == 0) {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            } else {
                StringBuilder sb = new StringBuilder();
                for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {
                    String text = block.getText();
                    sb.append(text);
                    sb.append(", ");
                }

                String string = sb.toString();
                String[] result = string.split(",|[\\r?\\n]");
                System.out.println(Arrays.toString(result));

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                byte[] byteArray = bStream.toByteArray();

                System.out.println(result.length);
                if (result.length <= 3){
                    Toast.makeText(this, "Unable to detect sufficient data", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(this, POCSubmit.class);
                    intent.putExtra("image", byteArray);
                    intent.putExtra("Time", result[0]);
                    intent.putExtra("Systolic", result[1]);
                    intent.putExtra("Diastolic", result[2]);
                    intent.putExtra("Heartrate", result[3]);
                    startActivity(intent);
                    finish();
                }


            }
        }




    }

    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        greyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(greyBitmap);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        imageView.setImageBitmap(greyBitmap);

        return greyBitmap;
    }



    public Bitmap toBinary(Bitmap bmpOriginal) {
        int width, height, threshold;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        threshold = 127;
        bmpBinary = Bitmap.createBitmap(bmpOriginal);



        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                int pixel = bmpOriginal.getPixel(x, y);
                int red = Color.red(pixel);


                //get binary value
                if(red < threshold){
                    black = black + 1;
                    bmpBinary.setPixel(x, y, 0xFF000000);
                } else{
                    white = white + 1;
                    bmpBinary.setPixel(x, y, 0xFFFFFFFF);
                }

            }
        }
        imageView.setImageBitmap(bmpBinary);
        System.out.println(white);
        System.out.println(black);
        DecimalFormat decimal = new DecimalFormat("0.00");
        System.out.println(decimal.format(black/white));

        return bmpBinary;
    }

    private void pickGallery() {
        //inent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        //intent to take image form camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Entry"); //title of picture
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image to submit"); // description
        image_uri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        /* check camera permission and return result
        in order to get hihg quality image have to save
        image to external storage first
        before inserting to image view
         */
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }

    //handle permissions


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    }
                    else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted){
                        pickGallery();
                    }
                    else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    public void home() {

        Intent intent = new Intent(this, POCHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }
}

