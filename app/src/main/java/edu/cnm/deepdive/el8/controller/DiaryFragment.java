package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.el8.databinding.FragmentDiaryBinding;
import edu.cnm.deepdive.el8.viewmodel.AdviceViewModel;
import edu.cnm.deepdive.el8.viewmodel.DiaryViewModel;

public class DiaryFragment extends Fragment {


  private FragmentDiaryBinding binding;
  private DiaryViewModel viewModel;


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
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(this).get(DiaryViewModel.class);
    viewModel
        .getDiary()
        .observe(getViewLifecycleOwner(), (diary)-> {
          binding.diaryInput.getText();
        });


  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
