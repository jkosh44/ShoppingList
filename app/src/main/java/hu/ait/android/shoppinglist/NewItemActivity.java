package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import hu.ait.android.shoppinglist.data.ShoppingItem;

/**
 * Created by joe on 11/1/15.
 */
public class NewItemActivity extends AppCompatActivity {
    public static final String KEY_ITEM = "KEY_ITEM";
    private EditText etItemName;
    private Spinner spinnerItemType;
    private EditText etPrice;
    private EditText etItemDescrption;
    private CheckBox cbAlreadyPurchased;
    private ShoppingItem itemToEdit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        if(getIntent().getSerializableExtra(ListActivity.KEY_EDIT) != null) {
            itemToEdit = (ShoppingItem) getIntent().getSerializableExtra(ListActivity.KEY_EDIT);
        }

        spinnerItemType = (Spinner) findViewById(R.id.spinnerItemType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemType.setAdapter(adapter);

        etItemName = (EditText) findViewById(R.id.etItemName);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etItemDescrption = (EditText) findViewById(R.id.etItemDescription);
        cbAlreadyPurchased = (CheckBox) findViewById(R.id.cbAlreadyPurchased);

        Button btnAddItem = (Button) findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });

        if (itemToEdit != null) {
            etItemName.setText(itemToEdit.getItemName());
            //spinnerItemType.setSelected(itemToEdit.getItemType().getValue());
            etPrice.setText(Float.toString(itemToEdit.getPrice()));
            etItemDescrption.setText(itemToEdit.getDescription());
            cbAlreadyPurchased.setChecked(itemToEdit.isPurchased());
        }
    }

    private void saveItem() {
        Intent intentResult = new Intent();
        ShoppingItem itemResult = null;
        if (itemToEdit != null) {
            itemResult = itemToEdit;
        } else {
            itemResult = new ShoppingItem();
        }

        itemResult.setItemName(etItemName.getText().toString());
        itemResult.setDescription(etItemDescrption.getText().toString());
        itemResult.setPrice(Float.valueOf(etPrice.getText().toString()));
        itemResult.setPurchased(cbAlreadyPurchased.isChecked());
        itemResult.setItemType(ShoppingItem.ItemType.fromInt(spinnerItemType.getSelectedItemPosition()));

        intentResult.putExtra(KEY_ITEM, itemResult);
        setResult(RESULT_OK, intentResult);
        finish();
    }

}

