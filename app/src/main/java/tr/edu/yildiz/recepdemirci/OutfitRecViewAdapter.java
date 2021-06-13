package tr.edu.yildiz.recepdemirci;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OutfitRecViewAdapter extends RecyclerView.Adapter<OutfitRecViewAdapter.ViewHolder> {
    final int layout;
    final OutfitClickListener listener;
    ArrayList<Outfit> outfits = new ArrayList<>();

    public OutfitRecViewAdapter(OutfitClickListener listener, int layout) {
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OutfitRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
       ViewHolder holder = new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Outfit outfit = outfits.get(position);

        if (outfit.getOverhead() != null && outfit.getOverhead().getImage()!=null) {
            holder.overhead_image.setImageBitmap(outfit.getOverhead().getImage());
        }
        if (outfit.getFace() != null && outfit.getFace().getImage() != null) {
            holder.face_image.setImageBitmap(outfit.getFace().getImage());
        }
        if (outfit.getTop() != null && outfit.getTop().getImage() != null) {
            holder.top_image.setImageBitmap(outfit.getTop().getImage());
        }
        if (outfit.getBottom() != null && outfit.getBottom().getImage() != null) {
            holder.bottom_image.setImageBitmap(outfit.getBottom().getImage());
        }
        if (outfit.getShoe() != null && outfit.getShoe().getImage() != null) {
            holder.shoe_image.setImageBitmap(outfit.getShoe().getImage());
        }
    }

    @Override
    public int getItemCount() {return outfits.size();}

    public void setOutfits(ArrayList<Outfit> outfits) {
        this.outfits = outfits;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView item_button;
        ExtendedFloatingActionButton delete_button;
        FloatingActionButton send_button;
        ImageView overhead_image;
        ImageView face_image;
        ImageView top_image;
        ImageView bottom_image;
        ImageView shoe_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_button = itemView.findViewById(R.id.item_button);
            delete_button = itemView.findViewById(R.id.delete_button);
            send_button = itemView.findViewById(R.id.send_button);
            overhead_image = itemView.findViewById(R.id.overhead_image);
            face_image = itemView.findViewById(R.id.face_image);
            top_image = itemView.findViewById(R.id.top_image);
            bottom_image = itemView.findViewById(R.id.bottom_image);
            shoe_image = itemView.findViewById(R.id.shoe_image);

            itemView.setOnClickListener(this);
            item_button.setOnClickListener(this);
            delete_button.setOnClickListener(this);
            send_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (v.getId() == delete_button.getId()) {
                listener.onDeleteClick(v, position);
            }
            else if (v.getId() == send_button.getId()) {
                listener.onSendClick(v, position);
            }
        }

    }
}
