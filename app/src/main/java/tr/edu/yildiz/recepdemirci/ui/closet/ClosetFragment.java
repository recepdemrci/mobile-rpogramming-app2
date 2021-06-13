package tr.edu.yildiz.recepdemirci.ui.closet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import tr.edu.yildiz.recepdemirci.AddItemActivity;
import tr.edu.yildiz.recepdemirci.DisplayItemActivity;
import tr.edu.yildiz.recepdemirci.ItemClickListener;
import tr.edu.yildiz.recepdemirci.ItemRecViewAdapter;
import tr.edu.yildiz.recepdemirci.MainActivity;
import tr.edu.yildiz.recepdemirci.R;
import tr.edu.yildiz.recepdemirci.database.ItemDB;
import tr.edu.yildiz.recepdemirci.databinding.FragmentClosetBinding;

public class ClosetFragment extends Fragment implements AdapterView.OnItemSelectedListener, ItemClickListener {
    private ItemDB db_item;
    private ClosetViewModel closetViewModel;
    private FragmentClosetBinding binding;

    private int closet_idx;
    Spinner spinner;
    TextView text_item_number;
    TextView text_closet_name;
    FloatingActionButton add_item;
    RecyclerView overheadRecView;
    RecyclerView faceRecView;
    RecyclerView topsRecView;
    RecyclerView bottomsRecView;
    RecyclerView shoesRecView;
    ItemRecViewAdapter adapter_overheads;
    ItemRecViewAdapter adapter_faces;
    ItemRecViewAdapter adapter_tops;
    ItemRecViewAdapter adapter_bottoms;
    ItemRecViewAdapter adapter_shoes;

    public ClosetFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        db_item = new ItemDB(getContext());
        closetViewModel = new ViewModelProvider(this).get(ClosetViewModel.class);
        binding = FragmentClosetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Initialize view variables
        spinner = binding.spinner;
        text_item_number = binding.textItemNumber;
        text_closet_name = binding.textClosetName;
        add_item = binding.addItemButton;
        overheadRecView = binding.overheadRecyclerView;
        faceRecView = binding.faceRecyclerView;
        topsRecView = binding.topsRecyclerView;
        bottomsRecView = binding.bottomsRecyclerView;
        shoesRecView = binding.shoesRecyclerView;

        if(getArguments() != null){
            closetViewModel.setClosetIdx(getArguments().getInt("closet_idx", 0));
        }

        closetViewModel.setClosets(db_item);
        closetViewModel.getClosetIdx().observe(getViewLifecycleOwner(), item -> {
            closet_idx = item;
            setTitle();
            // Assign spinner item and fill it
            setSpinner(root);
            // Assign RecyclerView
            setRecViews(root);
        });

        setListener();
        return root;
    }


    public void setTitle() {
        text_item_number.setText(closetViewModel.getItemNumberAsText(closet_idx));
        text_closet_name.setText(closetViewModel.getClosetName(closet_idx).toUpperCase());
    }

    public void setSpinner(@NotNull View root) {
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(
                root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, closetViewModel.getClosetsName());
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_spinner);
        spinner.setOnItemSelectedListener(this);
    }

    public void setRecViews(@NotNull View root) {
        adapter_overheads = new ItemRecViewAdapter(this, R.layout.list_item);
        adapter_overheads.setItems(closetViewModel.getCloset(closet_idx).getOverheads());
        overheadRecView.setAdapter(adapter_overheads);
        overheadRecView.setLayoutManager(new LinearLayoutManager(
                root.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        adapter_faces = new ItemRecViewAdapter(this, R.layout.list_item);
        adapter_faces.setItems(closetViewModel.getCloset(closet_idx).getFaces());
        faceRecView.setAdapter(adapter_faces);
        faceRecView.setLayoutManager(new LinearLayoutManager(
                root.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        adapter_tops = new ItemRecViewAdapter(this, R.layout.list_item);
        adapter_tops.setItems(closetViewModel.getCloset(closet_idx).getTops());
        topsRecView.setAdapter(adapter_tops);
        topsRecView.setLayoutManager(new LinearLayoutManager(
                root.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        adapter_bottoms = new ItemRecViewAdapter(this, R.layout.list_item);
        adapter_bottoms.setItems(closetViewModel.getCloset(closet_idx).getBottoms());
        bottomsRecView.setAdapter(adapter_bottoms);
        bottomsRecView.setLayoutManager(new LinearLayoutManager(
                root.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        adapter_shoes = new ItemRecViewAdapter(this, R.layout.list_item);
        adapter_shoes.setItems(closetViewModel.getCloset(closet_idx).getShoes());
        shoesRecView.setAdapter(adapter_shoes);
        shoesRecView.setLayoutManager(new LinearLayoutManager(
                root.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }


    public void setListener() {
        add_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                intent.putExtra("closet_idx", closet_idx);
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
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        closetViewModel.setClosetIdx(position - 1);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDisplayClick(View view, int position, long item_id) {
        Intent intent = new Intent(getActivity(), DisplayItemActivity.class);
        intent.putExtra("item_id", item_id);
        intent.putExtra("closet_idx", closet_idx);
        startActivity(intent);
    }

    @Override
    public void onAddItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), AddItemActivity.class);
        intent.putExtra("closet_idx", closet_idx);
        startActivity(intent);
    }
}