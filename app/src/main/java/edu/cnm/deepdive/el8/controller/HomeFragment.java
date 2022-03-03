package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.el8.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater,container,false);
    binding.create.setOnClickListener((v) -> {
    Navigation
        .findNavController(binding.getRoot())
        .navigate(HomeFragmentDirections.openDetails());
    });
  return binding.getRoot();
  }
}
