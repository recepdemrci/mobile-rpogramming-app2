package tr.edu.yildiz.recepdemirci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Calendar;

import tr.edu.yildiz.recepdemirci.database.ItemDB;

public class AddItemActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 100;

    int closet_idx;
    ItemDB db_item;
    String type;
    String image_path;
    LocalDate purchase_date;

    EditText color;
    EditText pattern;
    EditText price;
    EditText text_purchase_date;
    ImageButton image_item;
    CardView done_button;
    CardView cancel_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        db_item = new ItemDB(getApplicationContext());
        closet_idx = getIntent().getExtras().getInt("closet_idx");

        color = (EditText) findViewById(R.id.text_color);
        pattern = (EditText) findViewById(R.id.text_pattern);
        price = (EditText) findViewById(R.id.text_price);
        text_purchase_date = (EditText) findViewById(R.id.purchase_date);
        image_item = (ImageButton) findViewById(R.id.image_item);
        done_button = (CardView) findViewById(R.id.done);
        cancel_button = (CardView) findViewById(R.id.cancel);
    }


    public void onDoneClick(View view) {
        if(controlInput()) {
             Item item = new Item(
                     type.toLowerCase(),
                     color.getText().toString().toLowerCase(),
                     pattern.getText().toString().toLowerCase(),
                     purchase_date,
                     Float.parseFloat(price.getText().toString()),
                     image_path);

             if (db_item.add(item, closet_idx) >= 0) {
                 Intent intent = new Intent(this, MainActivity.class);
                 intent.putExtra("closet_idx", closet_idx);
                 intent.putExtra("fragmentId", 1);
                 startActivity(intent);
                 Toast.makeText(getApplicationContext(),
                         "Item saved into your closet", Toast.LENGTH_SHORT).show();
             }
             else {
                 Toast.makeText(getApplicationContext(),
                         "Error! Item couldn't save", Toast.LENGTH_SHORT).show();
             }
        }
    }

    public void onCancelClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("closet_idx", closet_idx);
        intent.putExtra("fragmentId", 1);
        startActivity(intent);
    }

    public void onAddImageClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode== GALLERY_REQUEST && data != null) {

            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                Bitmap image = BitmapFactory.decodeStream(imageStream);
                saveImage(image, selectedImage.getPath().substring(selectedImage.getPath().lastIndexOf( '/')+1));
                image_item.setImageBitmap(image);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void saveImage(Bitmap image, String imageFileName) {
        String dirPath = getFilesDir().getAbsolutePath() + "/images/";
        final File storageDir = new File(dirPath);

        boolean successDirCreated = true;
        if (!storageDir.exists()) {
            successDirCreated = storageDir.mkdir();
        }
        if (successDirCreated) {
            File imageFile = new File(storageDir, imageFileName);
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
                image_path = imageFile.getAbsolutePath();
                Toast.makeText(AddItemActivity.this, "Image added", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(AddItemActivity.this, "Error! Image couldn't added", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "Failed to make folder!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean controlInput() {
        if (color.getText().toString().isEmpty() ||
            pattern.getText().toString().isEmpty() ||
            price.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Error! Enter all required field", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Float.parseFloat(price.getText().toString());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error! Price must be only digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (type == null) {
            Toast.makeText(getApplicationContext(), "Error! Chose item type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBoxOverhead:
                if (checked)
                    type = "overhead";
                break;
            case R.id.checkBoxFace:
                if (checked)
                    type = "face";
                break;
            case R.id.checkBoxTop:
                if (checked)
                    type = "top";
                break;
            case R.id.checkBoxBottom:
                if (checked)
                    type = "bottom";
                break;
            case R.id.checkBoxShoe:
                if (checked)
                    type = "shoe";
                break;
        }
    }

    public void onPurchaseDateClick(View view) {
        DatePickerDialog datePicker;
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(AddItemActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        purchase_date = LocalDate.of(year, monthOfYear+1, dayOfMonth);
                        text_purchase_date.setText(purchase_date.toString());
                    }
                }, year, month, day);
        datePicker.show();
    }
}