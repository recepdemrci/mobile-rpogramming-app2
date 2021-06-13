package tr.edu.yildiz.recepdemirci;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import tr.edu.yildiz.recepdemirci.databinding.ActivityMainBinding;
import tr.edu.yildiz.recepdemirci.ui.closet.ClosetFragment;
import tr.edu.yildiz.recepdemirci.ui.home.HomeFragment;
import tr.edu.yildiz.recepdemirci.ui.outfit.OutfitFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int fragmentId;
        try {
            fragmentId = getIntent().getExtras().getInt("fragmentId", 0);

        } catch (Exception e) {
            e.printStackTrace();
            fragmentId = 0;
        }


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.activity_home, R.id.activity_closet, R.id.activity_outfit)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        switch (fragmentId) {
            case 0:
                navController.navigate(R.id.activity_home, getIntent().getExtras());
                break;
            case 1:
                navController.navigate(R.id.activity_closet, getIntent().getExtras());
                break;
            case 2:
                navController.navigate(R.id.activity_outfit, getIntent().getExtras());
                break;

            default:
                break;
        }
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}


//
//
//    getSupportFragmentManager()
//                        .beginTransaction()
//                                .add(R.id.nav_host_fragment_activity_main, fragment)
//                                .commit();
//                                }
//                                else if (getIntent().getExtras().getInt("fragmentId", 0) == 1) {
//                                ClosetFragment fragment = new ClosetFragment();
//                                fragment.setArguments(getIntent().getExtras());
//                                getSupportFragmentManager()
//                                .beginTransaction()
//                                .add(R.id.nav_host_fragment_activity_main, fragment)
//                                .commit();
//                                }
//                                else if (getIntent().getExtras().getInt("fragmentId", 0) == 2) {
//                                OutfitFragment fragment = new OutfitFragment();
//                                fragment.setArguments(getIntent().getExtras());
//                                getSupportFragmentManager()
//                                .beginTransaction()
//                                .add(R.id.nav_host_fragment_activity_main, fragment)
//                                .commit();
//                                }