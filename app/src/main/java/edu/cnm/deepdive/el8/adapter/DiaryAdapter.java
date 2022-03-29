package edu.cnm.deepdive.el8.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.el8.adapter.DiaryAdapter.Holder;
import edu.cnm.deepdive.el8.databinding.ItemDiaryBinding;
import edu.cnm.deepdive.el8.model.entity.Diary;
import java.text.DateFormat;
import java.util.List;

/**
 * Provides access to a recycler view and creates a view for each item in the data set.
 */

public class DiaryAdapter extends RecyclerView.Adapter<Holder> {


  private final List<Diary> diaries;
  private final OnDiaryClickListener listener;
  private final LayoutInflater inflater;
  private final DateFormat dateFormat;

  /**
   * Initialize this instance of {@link DiaryAdapter} with the injected below parameters.
   * @param context
   * @param diaries
   * @param listener
   */
  public DiaryAdapter(Context context, List<Diary> diaries,
      OnDiaryClickListener listener) {
    this.diaries = diaries;
    inflater = LayoutInflater.from(context);
    dateFormat = android.text.format.DateFormat.getDateFormat(context);
    this.listener = listener;
  }


  @NonNull
  @Override
  public DiaryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemDiaryBinding binding = ItemDiaryBinding.inflate(inflater, parent,
        false);
    return new DiaryAdapter.Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull DiaryAdapter.Holder holder, int position) {
    holder.bind(position);

  }


  @Override
  public int getItemCount() {
    return diaries.size();
  }

  /**
   * Provides access to a recycler view and creates a view for each item in the data set.
   */
  class Holder extends RecyclerView.ViewHolder {

    private ItemDiaryBinding binding;

    /**
     * Wraps around the View that contains the {@code fragment_diary.xml} layout
     * @param binding Attaches to the root of the layout,
     */
    public Holder(@NonNull ItemDiaryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
    /**
     * Binds the item according to its position
     * @param position
     */
    public void bind(int position) {
      Diary diary = diaries.get(position);
      binding.dateEntry.setText(dateFormat.format(diary.getCreated()));
      binding.diary.setText(diary.getEntry());
      binding.getRoot().setOnClickListener((view) -> listener.onClick(position, view, diary));
    }
  }
  /**
   * Provides an updated onClick Listener
   */
  public interface OnDiaryClickListener {

    void onClick(int position, View view, Diary diary);
  }
}


