package edu.cnm.deepdive.el8.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.el8.adapter.MoodCheckInAdapter.Holder;
import edu.cnm.deepdive.el8.databinding.ItemMoodCheckInBinding;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Provides access to a recycler view and creates a view for each item in the data set.
 */

public class MoodCheckInAdapter extends RecyclerView.Adapter<Holder> {

  private final List<MoodCheckIn> moodCheckIns;
  private final LayoutInflater inflater;
  private final DateFormat dateFormat;

  /**
   * Initialize this instance of {@link MoodCheckInAdapter}
   * @param context
   * @param moodCheckIns
   */
  public MoodCheckInAdapter(Context context,
      List<MoodCheckIn> moodCheckIns) {
    this.moodCheckIns = moodCheckIns;
    inflater = LayoutInflater.from(context);
    dateFormat = android.text.format.DateFormat.getDateFormat(context);

  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemMoodCheckInBinding binding = ItemMoodCheckInBinding.inflate(inflater,parent,false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {

   holder.bind(position);

  }

  @Override
  public int getItemCount() {
    return moodCheckIns.size();
  }


  class Holder extends RecyclerView.ViewHolder {
    private final ItemMoodCheckInBinding binding;


    public Holder(@NonNull ItemMoodCheckInBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
    public void bind (int position) {
      MoodCheckIn item = moodCheckIns.get(position) ;
      Date created = item.getCreated();
      binding.created.setText(dateFormat.format(created));
      binding.rating.setText(String.valueOf(item.getRating()));

    }
  }
}
