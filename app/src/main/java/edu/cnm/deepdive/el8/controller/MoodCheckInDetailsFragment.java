package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.databinding.FragmentMoodCheckInDetailsBinding;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.viewmodel.MoodViewModel;


public class MoodCheckInDetailsFragment extends BottomSheetDialogFragment {

  private FragmentMoodCheckInDetailsBinding binding;
  private MoodViewModel viewModel;
  private long moodId;
  private MoodCheckIn moodCheckIn;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MoodCheckInDetailsFragmentArgs args = MoodCheckInDetailsFragmentArgs.fromBundle(getArguments());
    moodId = args.getMoodId();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = FragmentMoodCheckInDetailsBinding.inflate(inflater, container, false);
    binding.save.setOnClickListener((v) -> {
      //TODO Set properties of Mood from Data entry Objects
      moodCheckIn.setUserId(1); //FIXME Use a real id here
      viewModel.save(moodCheckIn);
      dismiss();
    });
    binding.cancel.setOnClickListener((v) -> dismiss());

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    viewModel = new ViewModelProvider(getActivity()).get(MoodViewModel.class);
    viewModel
        .getMoodCheckIn()
        .observe(getViewLifecycleOwner(), (moodCheckin) -> {
          // TODO Populate view objects
        });
    if (moodId != 0) {
      viewModel.setMoodCheckInId(moodId);
    } else {
      moodCheckIn = new MoodCheckIn();
    }
  }
}