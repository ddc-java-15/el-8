package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.el8.databinding.FragmentDiaryBinding;

public class DiaryFragment extends Fragment {


  private FragmentDiaryBinding binding;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentDiaryBinding.inflate(inflater, container, false);
    // TODO Attach listeners to the controls.
    return  binding.getRoot();

  }

  // TODO override on view created and attach view model.

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
