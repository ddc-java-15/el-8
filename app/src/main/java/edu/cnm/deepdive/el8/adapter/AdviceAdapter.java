package edu.cnm.deepdive.el8.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.adapter.AdviceAdapter.Holder;
import edu.cnm.deepdive.el8.databinding.ItemAdviceBinding;
import edu.cnm.deepdive.el8.model.entity.Advice;
import java.text.DateFormat;
import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<Holder> {


  private final List<Advice> advices;
  private final OnAdviceClickListener adviceClickListener;
  private final OnFavoriteClickListener favoriteClickListener;
  private final LayoutInflater inflater;
  private final DateFormat dateFormat;
  @ColorInt
  private final int favoriteColor;

  public AdviceAdapter(Context context, List<Advice> advices,
      OnAdviceClickListener adviceClickListener,
      OnFavoriteClickListener favoriteClickListener) {
    this.advices = advices;
    inflater = LayoutInflater.from(context);
    dateFormat = android.text.format.DateFormat.getDateFormat(context);
    favoriteColor = context.getColor(R.color.favorite);
    this.adviceClickListener = adviceClickListener;
    this.favoriteClickListener = favoriteClickListener;
  }


  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemAdviceBinding binding = ItemAdviceBinding.inflate(inflater, parent,
        false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);

  }


  @Override
  public int getItemCount() {
    return advices.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    private ItemAdviceBinding binding;

    public Holder(@NonNull ItemAdviceBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(int position) {
      Advice advice = advices.get(position);
      binding.action.setText(advice.getAction());
      binding.date.setText(dateFormat.format(advice.getCreated()));
      if (advice.isFavorite()) {
        binding.favorite.setColorFilter(favoriteColor);
      } else {
        binding.favorite.setColorFilter(0);
      }
      binding.getRoot().setOnClickListener((v) -> adviceClickListener.onClick(position, v, advice));
      binding.favorite.setOnClickListener(
          (v) -> favoriteClickListener.onClick(position, v, advice, !advice.isFavorite()));

    }
  }

  @FunctionalInterface
  public interface OnAdviceClickListener {

    void onClick(int position, View view, Advice advice);
  }

  @FunctionalInterface
  public interface OnFavoriteClickListener {

    void onClick(int position, View view, Advice advice, boolean favorite);
  }
}
