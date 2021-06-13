package tr.edu.yildiz.recepdemirci;

public class Outfit {
    private long id;
    private Item overhead;
    private Item face;
    private Item top;
    private Item bottom;
    private Item shoe;


    public Outfit() {

    }

    public Outfit(Item overhead, Item face, Item top, Item bottom, Item shoe) {
        this.overhead = overhead;
        this.face = face;
        this.top = top;
        this.bottom = bottom;
        this.shoe = shoe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item getOverhead() {
        return overhead;
    }

    public void setOverhead(Item overhead) {
        this.overhead = overhead;
    }

    public Item getFace() {
        return face;
    }

    public void setFace(Item face) {
        this.face = face;
    }

    public Item getTop() {
        return top;
    }

    public void setTop(Item top) {
        this.top = top;
    }

    public Item getBottom() {
        return bottom;
    }

    public void setBottom(Item bottom) {
        this.bottom = bottom;
    }

    public Item getShoe() {
        return shoe;
    }

    public void setShoe(Item shoe) {
        this.shoe = shoe;
    }
}
