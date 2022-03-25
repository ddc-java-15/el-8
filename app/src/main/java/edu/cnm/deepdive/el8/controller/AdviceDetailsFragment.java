package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.el8.databinding.FragmentAdviceDetailsBinding;
import edu.cnm.deepdive.el8.viewmodel.AdviceViewModel;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;

public class AdviceDetailsFragment extends Fragment {

  private FragmentAdviceDetailsBinding binding;
  private AdviceViewModel adviceViewModel;
  private LoginViewModel loginViewModel;
  private long adviceId;
  private int moodRating;
  private long userId;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AdviceDetailsFragmentArgs args = AdviceDetailsFragmentArgs.fromBundle(getArguments());
    adviceId = args.getAdviceId();
    moodRating = args.getMoodRating();
    Log.d(getClass().getSimpleName(), String.format("AdviceId = %d", adviceId));
    Log.d(getClass().getSimpleName(), String.format("Moodrating= %d", moodRating));
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    FragmentAdviceDetailsBinding binding = FragmentAdviceDetailsBinding.inflate(inflater, container,
        false);
    binding.openDiary.setOnClickListener((v) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showDiaryDetails()));

    binding.openAdvice.setOnClickListener((v) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showAdviceDetails()));

    return binding.getRoot();

  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adviceViewModel = new ViewModelProvider(getActivity()).get(AdviceViewModel.class);
    loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
    loginViewModel
        .getUser()
        .observe(getViewLifecycleOwner(),(user) -> {
          adviceViewModel.setUserId(user.getId()); // TODO remove if not necessary
          userId = user.getId();
          // TODO personalize user object
          // TODO based on userId and adviceID and moodRating, create and save new adviceobject if necessary.
        });
    if (adviceId != 0 ) {
      adviceViewModel.setAdviceId(adviceId);
      adviceViewModel
          .getAdvice()
          .observe(getViewLifecycleOwner(), (advice)->  {
            // TODO display the content of this advice object.
          });
    }
  }
}
