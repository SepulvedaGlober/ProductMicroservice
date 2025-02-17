package GrandmasFoodProduct.GrandmasFoodProduct.domain.models;

import java.util.UUID;

public class Product {
    private String uuid;
    private String fantasyName;
    private Category category;
    private String description;
    private float price;
    private boolean available;

    public Product(String fantasyName, Category category, String description, float price, boolean available) {
        this.uuid = UUID.randomUUID().toString();
        this.fantasyName = fantasyName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public Product(){
        this.uuid = UUID.randomUUID().toString();
    }

    public Product(String uuid, String fantasyName, Category category, String description, float price, boolean available) {
        this.uuid = uuid != null ? uuid : UUID.randomUUID().toString();
        this.fantasyName = fantasyName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.available = available;
    }
    public String getUuid() {
        return uuid;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
