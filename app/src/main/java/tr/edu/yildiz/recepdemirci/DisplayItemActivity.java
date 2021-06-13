package tr.edu.yildiz.recepdemirci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
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
import tr.edu.yildiz.recepdemirci.database.OutfitDB;

public class DisplayItemActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 100;
    int closet_idx;
    long item_id;
    long outfit_id;

    ItemDB db_item;
    OutfitDB db_outfit;
    String type;
    String image_path;
    LocalDate purchase_date;

    RadioButton overheadR;
    RadioButton faceR;
    RadioButton topR;
    RadioButton bottomR;
    RadioButton shoeR;

    EditText text_outfit_id;
    EditText color;
    EditText pattern;
    EditText price;
    EditText text_purchase_date;
    ImageButton image_item;
    CardView done_button;
    CardView delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);
        outfit_id = 0;

        db_item = new ItemDB(getApplicationContext());
        db_outfit = new OutfitDB(getApplicationContext());
        closet_idx = getIntent().getExtras().getInt("closet_idx");
        item_id = getIntent().getExtras().getLong("item_id");

        overheadR = (RadioButton) findViewById(R.id.checkBoxOverhead);
        faceR = (RadioButton) findViewById(R.id.checkBoxFace);
        topR = (RadioButton) findViewById(R.id.checkBoxTop);
        bottomR = (RadioButton) findViewById(R.id.checkBoxBottom);
        shoeR = (RadioButton) findViewById(R.id.checkBoxShoe);
        color = (EditText) findViewById(R.id.text_color);
        pattern = (EditText) findViewById(R.id.text_pattern);
        price = (EditText) findViewById(R.id.text_price);
        text_purchase_date = (EditText) findViewById(R.id.purchase_date);
        text_outfit_id = (EditText) findViewById(R.id.text_outfit_id);
        image_item = (ImageButton) findViewById(R.id.image_item);
        done_button = (CardView) findViewById(R.id.done);
        delete_button = (CardView) findViewById(R.id.delete);

        setViewValues();
    }

    public void setViewValues() {
        try {
            Item item = db_item.findById(item_id);
            type = item.getType();
            switch (type) {
                case "overhead":
                    overheadR.setChecked(true);
                    break;
                case "face":
                    faceR.setChecked(true);
                    break;
                case "top":
                    topR.setChecked(true);
                    break;
                case "bottom":
                    bottomR.setChecked(true);
                    break;
                case "shoe":
                    shoeR.setChecked(true);
                    break;
            }

            color.setText(item.getColor());
            pattern.setText(item.getPattern());
            price.setText(String.valueOf(item.getPrice()));
            if (item.getPurchaseDateString() != null) {
                text_purchase_date.setText(item.getPurchaseDateString());
                purchase_date = item.getPurchaseDate();
            }
            if (item.getImage() != null) {
                image_item.setImageBitmap(item.getImage());
                image_path = item.getImagePath();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
            item.setId(item_id);

            if (db_item.update(item, closet_idx) >= 0) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("closet_idx", closet_idx);
                intent.putExtra("fragmentId", 1);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "Item updated", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Error! Item couldn't updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onAddToOutfitClick(View view) {
        try {
            outfit_id = Long.parseLong(text_outfit_id.getText().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_add_outfit, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);
        CardView button_add_ = popupView.findViewById(R.id.add_);
        CardView button_cancel_ = popupView.findViewById(R.id.cancel_);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        button_add_.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View v){
                if (outfit_id > -1){
                    Outfit outfit = db_outfit.findById(outfit_id);
                    if (outfit == null) {
                        db_outfit.add(outfit_id);
                    }
                    db_outfit.setOutfit(outfit_id, type.toLowerCase(), item_id);
                    popupWindow.dismiss();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("closet_idx", closet_idx);
                    intent.putExtra("fragmentId", 1);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),
                            "Item added into Outfit: " + outfit_id, Toast.LENGTH_SHORT).show();
                } else {
                    popupWindow.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_cancel_.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    public void onDeleteClick(View view) {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_delete_item, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);
        CardView button_delete_ = popupView.findViewById(R.id.delete_);
        CardView button_cancel_ = popupView.findViewById(R.id.cancel_);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        button_delete_.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View v){
                if (db_item.remove(item_id) > 0) {
                    popupWindow.dismiss();
                    Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                    intent.putExtra("closet_idx", closet_idx);
                    intent.putExtra("fragmentId", 1);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),
                            "Item Deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Error! Item couldn't deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_cancel_.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
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
                Toast.makeText(DisplayItemActivity.this, "Image added", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(DisplayItemActivity.this, "Error! Image couldn't added", Toast.LENGTH_SHORT).show();
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

        datePicker = new DatePickerDialog(DisplayItemActivity.this,
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