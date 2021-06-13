package tr.edu.yildiz.recepdemirci;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Closet {
    private String name;
    private int item_number;
    private ArrayList<Item> overheads;
    private ArrayList<Item> faces;
    private ArrayList<Item> tops;
    private ArrayList<Item> bottoms;
    private ArrayList<Item> shoes;


    public Closet(String name) {
        this.name = name;
        this.overheads = new ArrayList<>();
        this.faces = new ArrayList<>();
        this.tops = new ArrayList<>();
        this.bottoms = new ArrayList<>();
        this.shoes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setItemNumber() {
        this.item_number = overheads.size() + faces.size() +
                tops.size() + bottoms.size() + shoes.size() - 5;
    }

    public int getItemNumber() {
        setItemNumber();
        return item_number;
    }

    public ArrayList<Item> getOverheads() {
        return overheads;
    }

    public void setOverheads(ArrayList<Item> overheads) {
        this.overheads = overheads;
    }

    public ArrayList<Item> getFaces() {
        return faces;
    }

    public void setFaces(ArrayList<Item> faces) {
        this.faces = faces;
    }

    public ArrayList<Item> getTops() {
        return tops;
    }

    public void setTops(ArrayList<Item> tops) {
        this.tops = tops;
    }

    public ArrayList<Item> getBottoms() {
        return bottoms;
    }

    public void setBottoms(ArrayList<Item> bottoms) {
        this.bottoms = bottoms;
    }

    public ArrayList<Item> getShoes() {
        return shoes;
    }

    public void setShoes(ArrayList<Item> shoes) {
        this.shoes = shoes;
    }

    public void addEmptyItems() {
        Item overhead = new Item("", "", "", null, 0, null);
        Item face = new Item("", "", "", null, 0, null);
        Item top = new Item("", "", "", null, 0, null);
        Item bottom = new Item("", "", "", null , 0, null);
        Item shoe = new Item("", "", "", null , 0, null);

        this.overheads.add(overhead);
        this.faces.add(face);
        this.tops.add(top);
        this.bottoms.add(bottom);
        this.shoes.add(shoe);
    }
}
