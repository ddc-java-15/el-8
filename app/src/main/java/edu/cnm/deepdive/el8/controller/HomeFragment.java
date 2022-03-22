package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.el8.adapter.MoodCheckInAdapter;
import edu.cnm.deepdive.el8.databinding.FragmentHomeBinding;
import edu.cnm.deepdive.el8.viewmodel.MoodViewModel;

public class HomeFragment extends Fragment {


  private FragmentHomeBinding binding;
 // private MoodViewModel viewModel;




  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    binding.moodDashboard.setOnClickListener((view) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showMoodsDashboard()));
    binding.favoritesDashboard.setOnClickListener((view) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showFavoritesDashboard()));


  //  binding.create.setOnClickListener((v) -> {
   //   Navigation
    //      .findNavController(binding.getRoot())
        // .navigate(HomeFragmentDirections.openDetails());
            //.navigate(HomeFragmentDirections.showAdvice());
   //       .navigate(HomeFragmentDirections.test());

 //   });
    return binding.getRoot();
  }

 /* @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel = new ViewModelProvider(this).get(MoodViewModel.class);
    viewModel
        .getMoods()
        .observe(getViewLifecycleOwner(), (moods) -> {
          MoodCheckInAdapter adapter = new MoodCheckInAdapter(getContext(), moods);
          binding.checkIns.setAdapter(adapter);
        });
  }

  */
}
