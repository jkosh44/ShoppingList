package hu.ait.android.shoppinglist.touch;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import hu.ait.android.shoppinglist.adapter.ShoppingItemRecyclerAdapter;

/**
 * Created by joe on 11/5/15.
 */
public class ShoppingItemListTouchHelper extends ItemTouchHelper.Callback {

    private ShoppingItemRecyclerAdapter adapter;

    public ShoppingItemListTouchHelper(ShoppingItemRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
       //messes up editing
       // adapter.swapItems(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.removeShoppingItem(viewHolder.getAdapterPosition());
    }
}
