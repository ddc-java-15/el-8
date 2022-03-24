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
import edu.cnm.deepdive.el8.model.entity.Diary;
import edu.cnm.deepdive.el8.viewmodel.DiaryViewModel;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;

public class DiaryFragment extends Fragment {


  private FragmentDiaryBinding binding;
  private DiaryViewModel diaryViewModel;
  private LoginViewModel loginViewModel;
  private long userId;
  private long diaryId;
  private Diary diary;



  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    binding = FragmentDiaryBinding.inflate(inflater, container, false);
    // TODO Attach listeners to the controls.
    binding.save.setOnClickListener((v) -> {
      diaryViewModel.setUserId(userId);
      diaryViewModel.setDiaryId(diaryId);;
      diaryViewModel.save(diary);
    });
    return  binding.getRoot();

  }

  // TODO override on view created and attach view model.

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);
    diaryViewModel = new ViewModelProvider(this).get(DiaryViewModel.class);
    loginViewModel
        .getUser()
        .observe(getViewLifecycleOwner(),(user) -> diaryViewModel.setUserId(user.getId()));
    diaryViewModel
        .getDiary()
        .observe(getViewLifecycleOwner(), (diary)-> {
       //   binding.diaryInput.getText();
        });
    if (diaryId != 0) {
      diaryViewModel.setDiaryId(diaryId);
    } else {
      diary = new Diary();
      if (diaryId != 0) {
        binding.diaryInput.getText();
      }
    }



  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
