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
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.databinding.FragmentAdviceDetailsBinding;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.viewmodel.AdviceViewModel;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;
import java.util.Random;

public class AdviceDetailsFragment extends Fragment {

  private FragmentAdviceDetailsBinding binding;
  private AdviceViewModel adviceViewModel;
  private LoginViewModel loginViewModel;
  private long adviceId;
  private int moodRating;
  private long userId;
  private String adviceText;
  private boolean saved;
  private boolean favorite;
  private Advice advice;
  private int imageint;
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AdviceDetailsFragmentArgs args = AdviceDetailsFragmentArgs.fromBundle(getArguments());
    adviceId = args.getAdviceId();
    moodRating = args.getMoodRating();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentAdviceDetailsBinding.inflate(inflater, container,
        false);
    binding.openDiary.setOnClickListener((v) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showDiaryDetails()));

    binding.openAdvice.setOnClickListener((v) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showAdviceDetails()));

   binding.favorite.setOnClickListener((v) -> {


     if (adviceId == 0) {
       advice.setId(adviceId);
       advice.setFavorite(favorite);
     } else {

       advice.setFavorite(favorite);

     }

   });


    return binding.getRoot();

  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adviceViewModel = new ViewModelProvider(getActivity()).get(AdviceViewModel.class);
    loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
    loginViewModel
        .getUser()
        .observe(getViewLifecycleOwner(), (user) -> {
          adviceViewModel.setUserId(user.getId()); // TODO remove if not necessary
          userId = user.getId();
          saveAdvice();
          // TODO personalize user object
          // TODO based on userId and adviceID and moodRating, create and save new adviceobject if necessary.
        });
    if (adviceId != 0) {
      adviceViewModel.setAdviceId(adviceId);
      adviceViewModel
          .getAdvice()
          .observe(getViewLifecycleOwner(), (advice) -> {
            binding.advice.setText(advice.getAction());
          });
    } else {

      String[] advices = getContext()

          .getResources()
          .getStringArray(R.array.advices);
      Random rng = new Random();
      adviceText = advices[rng.nextInt(advices.length)];
      binding.advice.setText(adviceText);
      saveAdvice();
    }
    if (adviceId != 0) {
      adviceViewModel.setAdviceId(adviceId);
      adviceViewModel
          .getAdvice()
          .observe(getViewLifecycleOwner(), (image) -> {
            binding.image.setImageResource(imageint);
          });
    } else {
      int [] images = getContext()
          .getResources()
          .getIntArray(R.array.images);
      Random rng = new Random();
      imageint = images[rng.nextInt(images.length)];
      binding.image.setImageResource(imageint);
      saveAdvice();

    }
  }




  private synchronized void saveAdvice() {
    if (!saved && userId != 0 && adviceText != null) {
      Advice advice = new Advice();
      advice.setUserId(userId);
      advice.setAction(adviceText);
      adviceViewModel.save(advice);
      saved = true;
    }
  }
}
