package edu.cnm.deepdive.el8.controller;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.el8.databinding.FragmentAdviceBinding;

public class AdviceFragment extends Fragment {

  private FragmentAdviceBinding binding;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentAdviceBinding.inflate(inflater, container, false);
    return binding.getRoot();

  }

  // TODO override onviewcreated and connect to a viewmodel.


  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}