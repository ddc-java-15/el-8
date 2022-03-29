package edu.cnm.deepdive.el8.controller;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.databinding.FragmentAdviceDetailsBinding;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.viewmodel.AdviceViewModel;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;
import java.util.Random;

/**
 * Defines and manages and inflates the {@code fragment_advice_details.xml} layout.
 * Handles its layout lifecycle and input events.
 */
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
  private int imageInt;

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
            .navigate(AdviceDetailsFragmentDirections.showDiaryDetails()));

    binding.openAdvice.setOnClickListener((v) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceDetailsFragmentDirections.showAdviceDetails()));

    binding.favorite.setOnClickListener((v) -> {
      adviceViewModel.setFavorite(advice, !advice.isFavorite());
    });

    return binding.getRoot();

  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    FragmentActivity activity = getActivity();
    Resources resources = activity.getResources();

    adviceViewModel = new ViewModelProvider(activity).get(AdviceViewModel.class);
    loginViewModel = new ViewModelProvider(activity).get(LoginViewModel.class);
    loginViewModel
        .getUser()
        .observe(getViewLifecycleOwner(), (user) -> {
          adviceViewModel.setUserId(user.getId()); // TODO remove if not necessary
          userId = user.getId();
          saveAdvice();
          // TODO personalize user object
          // TODO based on userId and adviceID and moodRating, create and save new adviceobject if necessary.
        });
    adviceViewModel
        .getAdvice()
        .observe(getViewLifecycleOwner(), (advice) -> {
          this.advice = advice;
          binding.advice.setText(HtmlCompat.fromHtml(advice.getAction(),HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING));

          displayImage(activity,resources, advice.getImage() );
          binding.favorite.setColorFilter(advice.isFavorite() ? 0XFFFF0000: 0X80000000);
        });
    if (adviceId != 0) {
      adviceViewModel.setAdviceId(adviceId);
    } else {
      advice = new Advice();
      String[] advices = resources.getStringArray(R.array.advices);
      Random rng = new Random();
      adviceText = advices[rng.nextInt(advices.length)];
      advice.setAction(adviceText);
      String[] images = resources.getStringArray(R.array.images);
      String image = images[rng.nextInt(images.length)];
      displayImage(activity, resources, image);
      advice.setImage(image);
      saveAdvice();
    }
  }

  private void displayImage(FragmentActivity activity, Resources resources, String image) {
    int imageId = resources.getIdentifier(image,"drawable", activity.getPackageName());
    binding.image.setImageResource(imageId);
  }


  private synchronized void saveAdvice() {
    if (!saved && userId != 0 && adviceText != null) {
      advice.setUserId(userId);
      adviceViewModel.save(advice);
      saved = true;
    }
  }
}
