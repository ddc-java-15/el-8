package edu.cnm.deepdive.el8.controller;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.databinding.FragmentAdviceBinding;
import edu.cnm.deepdive.el8.viewmodel.AdviceViewModel;

public class AdviceFragment extends Fragment {

  private FragmentAdviceBinding binding;
  private AdviceViewModel viewModel;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentAdviceBinding.inflate(inflater, container, false);
    binding.completed.setOnClickListener((v) ->
        Navigation
            .findNavController(binding.getRoot())
            .navigate(AdviceFragmentDirections.showDiary()));





    return binding.getRoot();

  }

  // TODO override onviewcreated and connect to a viewmodel.


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
     viewModel = new ViewModelProvider(this).get(AdviceViewModel.class);


  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
