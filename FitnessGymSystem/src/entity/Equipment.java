package entity;

import java.util.Date;

public class Equipment {
    private int id;
    private String name;
    private String brand;
    private String model;
    private Date purchaseDate;
    private double price;
    private String status;
    private String description;
    private String maintenanceRecord;
    private Date lastMaintenanceDate;
    
    // 构造函数
    public Equipment() {}
    
    public Equipment(String name, String model, Date purchaseDate, double price) {
        this.name = name;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.status = "available";
    }
    
    // Getter和Setter方法
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getMaintenanceRecord() {
        return maintenanceRecord;
    }
    
    public void setMaintenanceRecord(String maintenanceRecord) {
        this.maintenanceRecord = maintenanceRecord;
    }
    
    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }
    
    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
    
    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", maintenanceRecord='" + maintenanceRecord + '\'' +
                ", lastMaintenanceDate=" + lastMaintenanceDate +
                '}';
    }
} 