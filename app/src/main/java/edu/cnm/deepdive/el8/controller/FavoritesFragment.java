package edu.cnm.deepdive.el8.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.el8.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends Fragment {


  private FragmentFavoritesBinding binding;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentFavoritesBinding.inflate(inflater, container, false);
    // TODO attach listeners to controls
    return  binding.getRoot();
  }

  // TODO override onviewcreated and attach to viewmodels


  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
