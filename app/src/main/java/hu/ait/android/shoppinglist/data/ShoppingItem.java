package hu.ait.android.shoppinglist.data;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

import hu.ait.android.shoppinglist.R;

/**
 * Created by joe on 11/1/15.
 */
public class ShoppingItem extends SugarRecord<ShoppingItem> implements Serializable {


    public enum ItemType {
        CLOTHES(0, R.drawable.clothes),
        FOOD(1, R.drawable.food), APPLIANCES(2, R.drawable.appliances);

        private int value;
        private int iconId;

        private ItemType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static ItemType fromInt(int value) {
            for (ItemType p : ItemType.values()) {
                if (p.value == value) {
                    return p;
                }
            }
            return CLOTHES;
        }
    }

    private String itemName;
    private String description;
    private float price;
    private ItemType itemType;
    private boolean purchased;

    public ShoppingItem() {

    }

    public ShoppingItem(String itemName, String description, float price, ItemType itemType, boolean purchased) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.itemType = itemType;
        this.purchased = purchased;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}

