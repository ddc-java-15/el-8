package edu.cnm.deepdive.el8.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.el8.R;
import edu.cnm.deepdive.el8.adapter.DashboardAdapter.Holder;

/**
 * Provides access to a recycler view and creates a view for each item in the data set.
 */
public class DashboardAdapter extends RecyclerView.Adapter<Holder> {



  private String[] titles = {
      "Chapter one",
      "Chapter two",
      "Chapter three",
      "Chapter four",
      "Chapter five",
      "Chapter six",
      "Chapter seven",
      "Chapter eight",
  };

  private String[] details = {
      "Item one details",
      "Item two details",
      "Item three details",
      "Item four details",
      "Item five details",
      "Item six details",
      "Item seven details",

  };

  private int[] images = {
      R.drawable.outline_favorite_24,
      R.drawable.outline_mood_24,
      R.drawable.outline_home_24,
      R.drawable.outline_person_24,
      R.drawable.outline_travel_explore_20,
      R.drawable.outline_self_improvement_24,
      R.drawable.outline_rate_review_24,

  };


  @Override
  public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.fragment_home, viewGroup, false);
    return new Holder(view);
  }



  @Override
  public void onBindViewHolder(Holder holder, int position) {
    holder.itemTitle.setText(titles[position]);
    holder.itemDetail.setText(details[position]);
    holder.itemImage.setImageResource(images[position]);
  }

  @Override
  public int getItemCount() {
    return titles.length;
  }

  class Holder extends RecyclerView.ViewHolder {

    ImageView itemImage;
    TextView itemTitle;
    TextView itemDetail;

    Holder(View itemView) {
      super(itemView);


    }
  }

}
