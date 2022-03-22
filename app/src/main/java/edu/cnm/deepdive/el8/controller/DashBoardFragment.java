package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.el8.databinding.FragmentDashboardBinding;
import java.util.Set;

public class DashBoardFragment extends Fragment {


  private FragmentDashboardBinding binding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
 /*   binding.moodDashboard.setOnClickListener((view) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(FragmentDirections.showMoodsDashboard()));
    binding.favoritesDashboard.setOnClickListener((view) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showFavoritesDashboard()));
*/
    return binding.getRoot();
  }



  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
