package hu.ait.android.shoppinglist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import hu.ait.android.shoppinglist.ListActivity;
import hu.ait.android.shoppinglist.R;
import hu.ait.android.shoppinglist.data.ShoppingItem;

/**
 * Created by joe on 11/4/15.
 */
public class ShoppingItemRecyclerAdapter extends RecyclerView.Adapter<ShoppingItemRecyclerAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgIcon;
        public TextView tvTitle;
        public TextView tvPrice;
        public CheckBox cbPurchased;
        public Button btnDelete;
        public Button btnEdit;
        public Button btnDetails;
        public LinearLayout itemRow;


        public ViewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            cbPurchased = (CheckBox) itemView.findViewById(R.id.cbPurchased);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDetails = (Button) itemView.findViewById(R.id.btnDetails);
            itemRow = (LinearLayout) itemView.findViewById(R.id.itemRow);
        }
    }

    private List<ShoppingItem> shoppingItemsList;
    private Context context;
    private int lastPosition = -1;

    public ShoppingItemRecyclerAdapter(List<ShoppingItem> shoppingItemsList, Context context) {
        this.shoppingItemsList = shoppingItemsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(shoppingItemsList.get(i).getItemName());
        viewHolder.tvPrice.setText("" + shoppingItemsList.get(i).getPrice());
        viewHolder.imgIcon.setImageResource(shoppingItemsList.get(i).getItemType().getIconId());
        viewHolder.cbPurchased.setChecked(shoppingItemsList.get(i).isPurchased());
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeShoppingItem(i);
            }
        });
        viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation detailsAnim = AnimationUtils.loadAnimation(context, R.anim.details_anim);
                viewHolder.itemRow.startAnimation(detailsAnim);
                Toast.makeText(context, shoppingItemsList.get(i).getDescription(), Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListActivity) context).showEditItemActivity(shoppingItemsList.get(i), i);
            }
        });

        setAnimation(viewHolder.itemView, i);

    }

    @Override
    public int getItemCount() {
        return shoppingItemsList.size();
    }

    public void addShoppingItem(ShoppingItem item) {
        item.save();
        shoppingItemsList.add(item);
        notifyDataSetChanged();
    }

    public void updateItem(int index, ShoppingItem item) {
        shoppingItemsList.set(index, item);
        item.save();
        notifyItemChanged(index);
    }

    public void removeShoppingItem(int index) {
        // remove it from the DB
        shoppingItemsList.get(index).delete();
        // remove it from the list
        shoppingItemsList.remove(index);
        notifyDataSetChanged();
    }

    public void removeAll() {
        shoppingItemsList.clear();
        ShoppingItem.deleteAll(ShoppingItem.class);
        notifyDataSetChanged();
    }

    public void swapItems(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(shoppingItemsList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(shoppingItemsList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }


    public ShoppingItem getItem(int i) {
        return shoppingItemsList.get(i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}

