package ru.student.familyfinance_desktop.DTO;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class BasketDTO {
    private SimpleLongProperty id;
    private SimpleLongProperty product_id;
    private SimpleStringProperty productName;
    private SimpleLongProperty shop_id;
    private SimpleStringProperty shopName;

    // поля для формирования покупок
    private SimpleDoubleProperty summ;
    private SimpleBooleanProperty selectItem;

    public BasketDTO(long id, long product_id, String productName, long shop_id, String shopName) {
        this.id = new SimpleLongProperty(id);
        this.product_id = new SimpleLongProperty(product_id);
        this.productName = new SimpleStringProperty(productName);
        this.shop_id = new SimpleLongProperty(shop_id);
        this.shopName = new SimpleStringProperty(shopName);
        this.summ = new SimpleDoubleProperty(0.0);
        this.selectItem = new SimpleBooleanProperty(false);
    }

    public long getId() { return this.id.get(); }
    public void setId(long value) { this.id.set(value); }
    public long getProduct_id() { return this.product_id.get(); }
    public void setProduct_id(long value) { this.product_id.set(value); }
    public String getProductName() { return this.productName.get(); }
    public void setProductName(String value) { this.productName.set(value); }
    public long getShop_id() { return this.shop_id.get(); }
    public void setShop_id(long value) { this.shop_id.set(value); }
    public String getShopName() { return this.shopName.get(); }
    public void setShopName(String value) { this.shopName.set(value); }

    public String getSumm() { 
        return Double.toString(this.summ.get()); 
    }
    public void setSumm(String value) { 
        double res = Double.parseDouble(value); 
        this.summ.setValue(res); 
    }
    public boolean getSelectItem() { return this.selectItem.get(); }
    public void setSelectItem(boolean value) { this.selectItem.set(value); }
}
