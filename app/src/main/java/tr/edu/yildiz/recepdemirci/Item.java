package tr.edu.yildiz.recepdemirci;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;


public class Item {
    private long id;
    private String type;
    private String color;
    private String pattern;
    private LocalDate purchase_date;
    private float price;
    private String image_path;


    public Item(String type, String color, String pattern, LocalDate purchase_date, float price, String image_path) {
        this.type = type;
        this.color = color;
        this.pattern = pattern;
        this.purchase_date = purchase_date;
        this.price = price;
        this.image_path = image_path;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getPattern() {
        return pattern;
    }

    public LocalDate getPurchaseDate() {
        return purchase_date;
    }

    public String getPurchaseDateString () {
        if (purchase_date != null) {
            return purchase_date.getDayOfMonth() +
                    "-" + purchase_date.getMonthValue() +
                    "-" + purchase_date.getYear();
        }
        else {
            return null;
        }
    }

    public float getPrice() {
        return price;
    }

    public String getImagePath() {
        return image_path;
    }

    public Bitmap getImage() {
        try {
            InputStream fileIn = new FileInputStream(image_path);
            Bitmap image = BitmapFactory.decodeStream(fileIn);
            fileIn.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        Item item = (Item) o;
        return item.type.equals(this.type) &&
                item.color.equals(this.color) &&
                item.pattern.equals(this.pattern) &&
                item.purchase_date.equals(this.purchase_date) &&
                item.price == this.price;
    }
}
