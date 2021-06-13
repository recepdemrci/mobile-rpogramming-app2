package tr.edu.yildiz.recepdemirci;

import android.view.View;

public interface ItemClickListener {
    void onDisplayClick(View view, int position, long item_id);
    void onAddItemClick(View view, int position);
}
