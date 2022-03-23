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
import edu.cnm.deepdive.el8.adapter.AdviceAdapter;
import edu.cnm.deepdive.el8.databinding.FragmentAdviceBinding;
import edu.cnm.deepdive.el8.viewmodel.AdviceViewModel;
import edu.cnm.deepdive.el8.viewmodel.LoginViewModel;

public class AdviceFragment extends Fragment {

  private FragmentAdviceBinding binding;
  private AdviceViewModel adviceViewModel;
  private LoginViewModel loginViewModel;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentAdviceBinding.inflate(inflater, container, false);
/*
    binding.openDiary.setOnClickListener((v) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showDiary()));
*/
    return binding.getRoot();

  }

  // TODO override onviewcreated and connect to a viewmodel.


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
    adviceViewModel = new ViewModelProvider(this).get(AdviceViewModel.class);
    adviceViewModel
        .getAdvices()
        .observe(getViewLifecycleOwner(), (advices) -> {
              AdviceAdapter adapter = new AdviceAdapter(getContext(), advices,
                  (position, v, advice) -> {
                    Log.d(getClass().getSimpleName(),
                        String.format("Advice clicked: position = %d; adviceId= %d", position,
                            advice.getId()));
                  },
                  (position, v, advice, favorite) -> {
                    Log.d(getClass().getSimpleName(),
                        String.format("Advice favorited: position = %d; adviceId = %d; favorite = %b",
                            position, advice.getId(), favorite));
                  });
              binding.advices.setAdapter(adapter);
              // TODO populate a recycler view

            }
        );
    loginViewModel
        .getUser()
        .observe(getViewLifecycleOwner(), (user) -> adviceViewModel.setUserId(user.getId()));

  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
