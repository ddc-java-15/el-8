package edu.cnm.deepdive.el8.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import edu.cnm.deepdive.el8.NavigationMapDirections;
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.databinding.ActivityMainBinding;
import edu.cnm.deepdive.el8.viewmodel.MoodViewModel;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private MoodViewModel viewModel;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    viewModel = new ViewModelProvider(this).get(MoodViewModel.class);
    setContentView(binding.getRoot());
    NavController navController =((NavHostFragment) getSupportFragmentManager()
        .findFragmentById(R.id.nav_host_fragment))
        .getNavController();

    binding.create.setOnClickListener((v) -> {
      navController.navigate(NavigationMapDirections.openDetails());
    });
  }
}