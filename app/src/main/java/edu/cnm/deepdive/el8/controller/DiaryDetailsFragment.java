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
import edu.cnm.deepdive.el8.adapter.DiaryAdapter;
import edu.cnm.deepdive.el8.databinding.FragmentAdviceDetailsBinding;
import edu.cnm.deepdive.el8.databinding.FragmentDiaryDetailsBinding;
import edu.cnm.deepdive.el8.model.entity.Diary;
import edu.cnm.deepdive.el8.viewmodel.DiaryViewModel;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;
import java.util.Date;
/**
 * Defines, manages and inflates the {@code fragment_diary_details.xml} layout.
 * Handles its layout lifecycle and input events.
 */
public class DiaryDetailsFragment extends Fragment {

  private FragmentDiaryDetailsBinding binding;
  private LoginViewModel loginViewModel;
  private DiaryViewModel diaryViewModel;
  private long userId;
  private long diaryId;
  private Diary diary;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    diaryId = DiaryDetailsFragmentArgs
        .fromBundle(getArguments())
        .getDiaryId();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    binding = FragmentDiaryDetailsBinding.inflate(inflater, container, false);

    binding.saveDiaryEntry.setOnClickListener((v) -> {
      diary.setUserId(userId);
      diary.setCreated(new Date());
      diary.setEntry(binding.diaryEntry.getText().toString().trim());
      diaryViewModel.save(diary);
      Navigation
          .findNavController(binding.getRoot())
          .popBackStack();
    });

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
    diaryViewModel = new ViewModelProvider(getActivity()).get(DiaryViewModel.class);
    loginViewModel
        .getUser()
        .observe(getViewLifecycleOwner(), (user) -> userId = user.getId());

    if (diaryId != 0) {
      diaryViewModel.setDiaryId(diaryId);
      diaryViewModel
          .getDiary()
          .observe(getViewLifecycleOwner(), (diary) -> {
            this.diary = diary;
            binding.diaryEntry.setText(diary.getEntry());
            binding.diaryEntry.setEnabled(false);
            binding.saveDiaryEntry.setVisibility(View.GONE);
          });
    } else {
      diary = new Diary();
      binding.diaryEntry.setText("");
      binding.diaryEntry.setEnabled(true);
      binding.saveDiaryEntry.setVisibility(View.VISIBLE);
    }

  }
}

