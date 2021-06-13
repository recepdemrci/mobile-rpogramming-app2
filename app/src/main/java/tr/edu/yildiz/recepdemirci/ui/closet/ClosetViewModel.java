package tr.edu.yildiz.recepdemirci.ui.closet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import tr.edu.yildiz.recepdemirci.Closet;
import tr.edu.yildiz.recepdemirci.Item;
import tr.edu.yildiz.recepdemirci.database.ItemDB;

public class ClosetViewModel extends ViewModel {
    private ArrayList<Closet> closets = new ArrayList<>();
    private final MutableLiveData<Integer> closet_idx = new MutableLiveData<Integer>();
    private int closet_count;


    public ClosetViewModel() {
        closet_idx.setValue(0);
    }

    public void setClosets(ItemDB db_item) {
        closet_count = db_item.getClosetCount();
        for (int i = 0; i < closet_count; i++) {
            Closet closet = new Closet(String.valueOf(closet_count));
            closet.setOverheads(db_item.getItems(i, "overhead"));
            closet.setFaces(db_item.getItems(i, "face"));
            closet.setTops(db_item.getItems(i, "top"));
            closet.setBottoms(db_item.getItems(i, "bottom"));
            closet.setShoes(db_item.getItems(i, "shoe"));
            closet.addEmptyItems();
            closets.add(closet);
        }

        if (closet_count <= 0) {
            Closet closet = new Closet("0");
            closet.addEmptyItems();
            closets.add(closet);
        }
    }

    public int getClosetCount() {
        return closet_count;
    }
    public void setClosetIdx(int position) {
        if (position != -1) {
            closet_idx.setValue(position);
        }
    }

    public LiveData<Integer> getClosetIdx() {
        return closet_idx;
    }

    public Closet getCloset(int position) {
        return closets.get(position);
    }

    public int getItemNumber(int position) {
        return closets.get(position).getItemNumber();
    }
    public String getItemNumberAsText(int position) {
        return (closets.get(position).getItemNumber() + " items");
    }

    public String getClosetName(int position) {
        return "closet " + closets.get(position).getName();
    }

    public String[] getClosetsName() {
        String[] names = new String[closets.size() + 1];
        names[0] = "closets";
        int i = 1;
        for (Closet a_closet: closets) {
           names[i] = a_closet.getName();
           i ++;
        }
        return names;
    }


}