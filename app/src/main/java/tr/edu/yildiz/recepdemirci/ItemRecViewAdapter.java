package tr.edu.yildiz.recepdemirci;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRecViewAdapter extends RecyclerView.Adapter<ItemRecViewAdapter.ViewHolder> {
    final int layout;
    final ItemClickListener listener;
    ArrayList<Item> items = new ArrayList<>();

    public ItemRecViewAdapter(ItemClickListener listener, int layout) {
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        if (item.getImage() != null) {
            holder.item_image.setImageBitmap(item.getImage());
        }
        holder.item_text.setText(item.getType());

    }

    @Override
    public int getItemCount() {return items.size();}

    public void setItems(ArrayList<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView item_button;
        ImageView item_image;
        TextView item_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_button = itemView.findViewById(R.id.item_button);
            item_image = itemView.findViewById(R.id.item_image);
            item_text = itemView.findViewById(R.id.item_text);

            itemView.setOnClickListener(this);
            item_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (v.getId() == item_button.getId()) {
                if (position == getItemCount() - 1  ) {
                    listener.onAddItemClick(v, position);
                }
                else {
                    listener.onDisplayClick(v, position, items.get(position).getId());
                }
            }
        }

    }
}
