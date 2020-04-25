package com.example.myfirstapp.activities;

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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myfirstapp.R;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.WriteFile;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.util.Arrays;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class TessOCRActivity extends AppCompatActivity {

    public static final class PageSegMode {
        @Retention(SOURCE)
        @IntDef({PSM_OSD_ONLY, PSM_AUTO_OSD, PSM_AUTO_ONLY, PSM_AUTO, PSM_SINGLE_COLUMN,
                PSM_SINGLE_BLOCK_VERT_TEXT, PSM_SINGLE_BLOCK, PSM_SINGLE_LINE, PSM_SINGLE_WORD,
                PSM_CIRCLE_WORD, PSM_SINGLE_CHAR, PSM_SPARSE_TEXT, PSM_SPARSE_TEXT_OSD, PSM_RAW_LINE})
        public @interface Mode {}

        /** Orientation and script detection only. */
        public static final int PSM_OSD_ONLY = 0;

        /** Automatic page segmentation with orientation and script detection. (OSD) */
        public static final int PSM_AUTO_OSD = 1;

        /** Fully automatic page segmentation, but no OSD, or OCR. */
        public static final int PSM_AUTO_ONLY = 2;

        /** Fully automatic page segmentation, but no OSD. */
        public static final int PSM_AUTO = 3;

        /** Assume a single column of text of variable sizes. */
        public static final int PSM_SINGLE_COLUMN = 4;

        /** Assume a single uniform block of vertically aligned text. */
        public static final int PSM_SINGLE_BLOCK_VERT_TEXT = 5;

        /** Assume a single uniform block of text. (Default.) */
        public static final int PSM_SINGLE_BLOCK = 6;

        /** Treat the image as a single text line. */
        public static final int PSM_SINGLE_LINE = 7;

        /** Treat the image as a single word. */
        public static final int PSM_SINGLE_WORD = 8;

        /** Treat the image as a single word in a circle. */
        public static final int PSM_CIRCLE_WORD = 9;

        /** Treat the image as a single character. */
        public static final int PSM_SINGLE_CHAR = 10;

        /** Find as much text as possible in no particular order. */
        public static final int PSM_SPARSE_TEXT = 11;

        /** Sparse text with orientation and script detection. */
        public static final int PSM_SPARSE_TEXT_OSD = 12;

        /** Treat the image as a single text line, bypassing hacks that are Tesseract-specific. */
        public static final int PSM_RAW_LINE = 13;
    }

    /** Whitelist of characters to recognize. */
    public static final String VAR_CHAR_WHITELIST = "tessedit_char_whitelist";

    /** Blacklist of characters to not recognize. */
    public static final String VAR_CHAR_BLACKLIST = "tessedit_char_blacklist";

    /** Save blob choices allowing us to get alternative results. */
    public static final String VAR_SAVE_BLOB_CHOICES = "save_blob_choices";

    /** String value used to assign a boolean variable to true. */
    public static final String VAR_TRUE = "T";

    /** String value used to assign a boolean variable to false. */
    public static final String VAR_FALSE = "F";

    @Retention(SOURCE)
    @IntDef({OEM_TESSERACT_ONLY, OEM_CUBE_ONLY, OEM_TESSERACT_CUBE_COMBINED, OEM_DEFAULT})
    public @interface OcrEngineMode {}

    /** Run Tesseract only - fastest */
    public static final int OEM_TESSERACT_ONLY = 0;

    /** Run Cube only - better accuracy, but slower */
    @Deprecated
    public static final int OEM_CUBE_ONLY = 1;

    /** Run both and combine results - best accuracy */
    @Deprecated
    public static final int OEM_TESSERACT_CUBE_COMBINED = 2;

    /** Default OCR engine mode. */
    public static final int OEM_DEFAULT = 3;

    Button captureImageBtn, detectTextBtn, greyscaleBtn;
    ImageView imageView;
    TextView textView;
    Bitmap imageBitmap, greyBitmap, conBitmap, bmpBinary;
    int contrast = 1;
    int brightness = -50;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;


    public static final String TESS_DATA = "/tessdata";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Tess";

    TessBaseAPI tessBaseAPI;
    Uri outputFileDir;

    String mCurrentPhotoPath;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);


        captureImageBtn = findViewById(R.id.capture_image_btn);
        detectTextBtn = findViewById(R.id.detect_text_image_btn);
//        greyscaleBtn = findViewById(R.id.greyscale);

        imageView = findViewById(R.id.image_view);
//        textView = findViewById(R.id.text_display);

        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //storage permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        captureImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }
        });


        detectTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                imageView.setImageBitmap(bmpBinary);
                getText(bmpBinary);


            }
        });

        greyscaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                imageView.setImageBitmap(imageBitmap);
                getText(imageBitmap);



            }
        });

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


//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }



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
                //set image to image view
//                imageView.setImageURI(resultUri);

//                Uri uri = data.getData();
//
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    toGrayscale(imageBitmap);
                    prepareTessData();
                    changeBitmapContrastBrightness(greyBitmap, contrast, brightness);
                    toBinary(greyBitmap);


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


    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        greyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
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
                    bmpBinary.setPixel(x, y, 0xFF000000);
                } else{
                    bmpBinary.setPixel(x, y, 0xFFFFFFFF);
                }

            }
        }imageView.setImageBitmap(bmpBinary);
        return bmpBinary;
    }

    public Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        conBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(conBitmap);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);
        imageView.setImageBitmap(conBitmap);

        return conBitmap;
    }

    public void pick_image(View v){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i,1);
    }


    private void prepareTessData(){
        try{
            File dir = getExternalFilesDir(TESS_DATA);
            if(!dir.exists()){
                if (!dir.mkdir()) {
                    Toast.makeText(getApplicationContext(), "The folder " + dir.getPath() + "was not created", Toast.LENGTH_SHORT).show();
                }
            }
            String fileList[] = getAssets().list("");
            for(String fileName : fileList){
                String pathToDataFile = dir + "/" + fileName;
                if(!(new File(pathToDataFile)).exists()){
                    InputStream in = getAssets().open(fileName);
                    OutputStream out = new FileOutputStream(pathToDataFile);
                    byte [] buff = new byte[1024];
                    int len ;
                    while(( len = in.read(buff)) > 0){
                        out.write(buff,0,len);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

//    private void startOCR(Uri imageUri){
//        try{
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = false;
//            options.inSampleSize = 6;
//            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, options);
//            String result = this.getText(bitmap);
//            textView.setText(result);
//        }catch (Exception e){
//            Log.e(TAG, e.getMessage());
//        }
//    }

    private void getText(Bitmap bitmap){
        try{
            tessBaseAPI = new TessBaseAPI();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        Pix image = ReadFile.readBitmap(bitmap);

        if (image == null) {
            throw new RuntimeException("Failed to read bitmap");
        } else {
            String dataPath = getExternalFilesDir("/").getPath() + "/";
            tessBaseAPI.init(dataPath, "seg008sc");

            tessBaseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_BLOCK_VERT_TEXT);

            tessBaseAPI.setImage(bitmap);
            tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789:");
//            Bitmap writeBitmap = WriteFile.writeBitmap(tessBaseAPI.getThresholdedImage());
            String retStr = "No result";


//            imageView.setImageBitmap(writeBitmap);
            try {
                retStr = tessBaseAPI.getUTF8Text();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            System.out.println(retStr);
            tessBaseAPI.end();
            textView.setText(retStr);
        }
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

}

