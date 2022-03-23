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
import edu.cnm.deepdive.el8.databinding.FragmentMoodBinding;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;
import edu.cnm.deepdive.el8.viewmodel.MoodViewModel;

public class MoodFragment extends Fragment {

  private FragmentMoodBinding binding;
  private MoodViewModel moodViewModel;
  private LoginViewModel loginViewModel;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentMoodBinding.inflate(inflater, container, false);

    binding.create.setOnClickListener((v) -> {
      Navigation
          .findNavController(binding.getRoot())
          .navigate(MoodFragmentDirections.openDetails());
    });

    return binding.getRoot();

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
    moodViewModel = new ViewModelProvider(this).get(MoodViewModel.class);
    loginViewModel
        .getUser()
        .observe(getViewLifecycleOwner(),(user) -> moodViewModel.setUserId(user.getId()));
    moodViewModel
        .getMoods()
        .observe(getViewLifecycleOwner(), (moods) -> {
          if (moods != null) {
            MoodCheckInAdapter adapter = new MoodCheckInAdapter(getContext(), moods);
            binding.checkIns.setAdapter(adapter);
          }
        });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
