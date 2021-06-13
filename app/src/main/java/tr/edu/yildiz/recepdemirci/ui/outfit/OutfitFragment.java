package tr.edu.yildiz.recepdemirci.ui.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

import tr.edu.yildiz.recepdemirci.AddItemActivity;

import tr.edu.yildiz.recepdemirci.MainActivity;
import tr.edu.yildiz.recepdemirci.Outfit;
import tr.edu.yildiz.recepdemirci.OutfitClickListener;
import tr.edu.yildiz.recepdemirci.OutfitRecViewAdapter;
import tr.edu.yildiz.recepdemirci.R;
import tr.edu.yildiz.recepdemirci.database.OutfitDB;
import tr.edu.yildiz.recepdemirci.databinding.FragmentClosetBinding;
import tr.edu.yildiz.recepdemirci.databinding.FragmentOutfitBinding;
import tr.edu.yildiz.recepdemirci.ui.closet.ClosetViewModel;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OutfitFragment extends Fragment implements OutfitClickListener {
    private OutfitViewModel outfitViewModel;
    private FragmentOutfitBinding binding;
    private OutfitDB db_outfit;

    FloatingActionButton add_outfit_button;
    RecyclerView outfitRecView;
    OutfitRecViewAdapter adapter;


    public OutfitFragment() {

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        db_outfit = new OutfitDB(getContext());
        outfitViewModel = new ViewModelProvider(this).get(OutfitViewModel.class);
        binding = FragmentOutfitBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize view variables
        add_outfit_button = binding.addOutfitButton;
        outfitRecView = binding.outfitRecView;

        outfitViewModel.setOutfits(db_outfit);
        setRecView(root);
        setListener();
        return root;
    }


    public void setRecView(@NotNull View root) {
        adapter = new OutfitRecViewAdapter(this, R.layout.list_outfit);
        adapter.setOutfits(outfitViewModel.getOutfits());
        outfitRecView.setAdapter(adapter);
        outfitRecView.setLayoutManager(new LinearLayoutManager(
                root.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    public void setListener() {
        add_outfit_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("fragmentId", 1);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onSendClick(View view, int position) {

    }

    @Override
    public void onDeleteClick(View view, int position) {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_delete_item, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);
        CardView button_delete_ = popupView.findViewById(R.id.delete_);
        CardView button_cancel_ = popupView.findViewById(R.id.cancel_);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        long id_ = outfitViewModel.getOutfits().get(position).getId();
        button_delete_.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View v){

                if (db_outfit.remove(id_) > 0) {
                    popupWindow.dismiss();
                    Intent intent = new Intent(getContext() , MainActivity.class);
                    intent.putExtra("fragmentId", 2);
                    startActivity(intent);
                    Toast.makeText(getContext(),
                            "Outfit Deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),
                            "Error! Outfit couldn't deleted", Toast.LENGTH_SHORT).show();
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
}