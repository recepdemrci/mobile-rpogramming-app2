package tr.edu.yildiz.recepdemirci.ui.outfit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import tr.edu.yildiz.recepdemirci.Closet;
import tr.edu.yildiz.recepdemirci.Item;
import tr.edu.yildiz.recepdemirci.Outfit;
import tr.edu.yildiz.recepdemirci.database.ItemDB;
import tr.edu.yildiz.recepdemirci.database.OutfitDB;

public class OutfitViewModel extends ViewModel {
    private ArrayList<Outfit> outfits = new ArrayList<>();


    public OutfitViewModel() {

    }

    public void setOutfits(OutfitDB db_outfit) {
        outfits = db_outfit.getOutfits();
    }


    public ArrayList<Outfit> getOutfits() {
        return outfits;
    }
    public Outfit getOutfit(int position) {
        return outfits.get(position);
    }


}