package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import hu.ait.android.shoppinglist.adapter.ShoppingItemRecyclerAdapter;
import hu.ait.android.shoppinglist.data.ShoppingItem;
import hu.ait.android.shoppinglist.touch.ShoppingItemListTouchHelper;

/**
 * Created by joe on 11/1/15.
 */
public class ListActivity extends AppCompatActivity {

    public static final int REQUEST_NEW_CODE = 101;
    public static final int REQUEST_EDIT_CODE = 102;
    public static final String KEY_EDIT = "KEY_EDIT";
    private ShoppingItemRecyclerAdapter shoppingAdapter;
    private ShoppingItem itemToEditHolder;
    private int itemToEditPostion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<ShoppingItem> shoppingItemList = ShoppingItem.listAll(ShoppingItem.class);


        shoppingAdapter = new ShoppingItemRecyclerAdapter(shoppingItemList, this);
        RecyclerView recyclerViewShoppingItems = (RecyclerView) findViewById(
                R.id.recycler_view);
        recyclerViewShoppingItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewShoppingItems.setAdapter(shoppingAdapter);

        ShoppingItemListTouchHelper touchHelperCallback = new ShoppingItemListTouchHelper(
                shoppingAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerViewShoppingItems);


    }

    public void showCreateItemActivity() {
        Intent intentStart = new Intent(ListActivity.this, NewItemActivity.class);
        startActivityForResult(intentStart, REQUEST_NEW_CODE);
    }

    public void showEditItemActivity(ShoppingItem itemToEdit, int position) {
        Intent intentStart = new Intent(ListActivity.this, NewItemActivity.class);
        itemToEditHolder = itemToEdit;
        itemToEditPostion = position;

        intentStart.putExtra(KEY_EDIT, itemToEdit);
        startActivityForResult(intentStart, REQUEST_EDIT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if(requestCode == REQUEST_NEW_CODE) {
                    ShoppingItem item = (ShoppingItem) data.getSerializableExtra(NewItemActivity.KEY_ITEM);

                    shoppingAdapter.addShoppingItem(item);
                    Toast.makeText(ListActivity.this, R.string.new_item, Toast.LENGTH_SHORT);
                }

                else if(requestCode == REQUEST_EDIT_CODE) {
                    ShoppingItem itemTemp = (ShoppingItem) data.getSerializableExtra(NewItemActivity.KEY_ITEM);

                    itemToEditHolder.setItemName(itemTemp.getItemName());
                    itemToEditHolder.setPrice(itemTemp.getPrice());
                    itemToEditHolder.setDescription(itemTemp.getDescription());
                    itemToEditHolder.setPurchased(itemTemp.isPurchased());
                    itemToEditHolder.setItemType(itemTemp.getItemType());

                    if(itemToEditPostion != -1) {
                        shoppingAdapter.updateItem(itemToEditPostion, itemToEditHolder);
                        itemToEditPostion = -1;
                    } else {
                        shoppingAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(ListActivity.this, R.string.item_edit, Toast.LENGTH_SHORT);

                }
                break;
            case RESULT_CANCELED:
                Toast.makeText(ListActivity.this, R.string.item_cancelled, Toast.LENGTH_SHORT);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_item:
                showCreateItemActivity();
                return true;
            case R.id.action_delete_list:
                shoppingAdapter.removeAll();
                return true;
            default:
                showCreateItemActivity();
                return true;
        }
    }
}
