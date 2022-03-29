package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.databinding.FragmentMoodCheckInDetailsBinding;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;
import edu.cnm.deepdive.el8.viewmodel.MoodViewModel;

/**
 * Defines and manages and inflates the {@code fragment_mood_check_in_details.xml} layout.
 * Handles its layout lifecycle amd  input events.
 */
public class MoodCheckInDetailsFragment extends BottomSheetDialogFragment implements
    OnSeekBarChangeListener {

  private FragmentMoodCheckInDetailsBinding binding;
  private MoodViewModel moodViewModel;
  private long moodId;
  private long userId;
  private MoodCheckIn moodCheckIn;
  private LoginViewModel loginViewModel;

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
      moodCheckIn.setRating(binding.rating.getProgress());
      moodCheckIn.setUserId(userId);
      moodViewModel.save(moodCheckIn);
  //    dismiss();
      Navigation
          .findNavController(getActivity(),R.id.nav_host_fragment)
          .navigate(MoodCheckInDetailsFragmentDirections.showRelatedAdvice(moodCheckIn.getRating()));
    });
    binding.cancel.setOnClickListener((v) -> dismiss());
    binding.rating.setOnSeekBarChangeListener(this);
    return binding.getRoot();
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
    moodViewModel = new ViewModelProvider(getActivity()).get(MoodViewModel.class);
    moodViewModel
        .getMoodCheckIn()
        .observe(getViewLifecycleOwner(), (moodCheckin) -> {
          // TODO Populate view objects
        });
    if (moodId != 0) {
      moodViewModel.setMoodCheckInId(moodId);
    } else {
      moodCheckIn = new MoodCheckIn();
      binding.ratingValue.setText(String.valueOf(binding.rating.getProgress()));
    }
    loginViewModel
        .getUser()
        .observe(getViewLifecycleOwner(), (user) -> userId = user.getId());
  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int value, boolean byUser) {
    binding.ratingValue.setText(String.valueOf(value));
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
// intentionally ignored
  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
// intentionally ignored
  }
}