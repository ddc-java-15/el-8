package edu.cnm.deepdive.el8.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.databinding.ActivityMainBinding;
import edu.cnm.deepdive.el8.viewmodel.AdviceViewModel;
import edu.cnm.deepdive.el8.viewmodel.DiaryViewModel;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;
import edu.cnm.deepdive.el8.viewmodel.MoodViewModel;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private MoodViewModel viewModel;
  private AdviceViewModel adviceViewModel;
  private DiaryViewModel diaryViewModel;
  private LoginViewModel loginViewModel;
  private NavController navController;
  private AppBarConfiguration appBarConfiguration;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
 //   viewModel = new ViewModelProvider(this).get(MoodViewModel.class);

    setContentView(binding.getRoot());
    navController = ((NavHostFragment) getSupportFragmentManager()
        .findFragmentById(R.id.nav_host_fragment))
        .getNavController();
    appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_mood, R.id.navigation_favorite)
        .build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(binding.navView, navController);

    // attach the loginviewmodel
    loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    loginViewModel
        .getAccount()
        .observe(this, (account) -> {
          if (account == null) {
            Intent intent = new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
          }
        });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu); // SIGN OUT FROM MAIN OPTIONS
     getMenuInflater().inflate(R.menu.main_options, menu);
     return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled;  // SIGN OUT FROM MAIN OPTIONS

    if (item.getItemId() == R.id.sign_out) {

      loginViewModel.signOut();

      handled = true;
    }else{
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
  }
}