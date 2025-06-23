package entity;

public class EquipmentRental {
    private int id;
    private int memberId;
    private int equipmentId;
    private String rentalDate;
    private String expectedReturnDate;
    private String actualReturnDate;
    private String status;
    private String notes;
    
    public EquipmentRental() {}
    
    public EquipmentRental(int memberId, int equipmentId, String rentalDate, String expectedReturnDate, String status, String notes) {
        this.memberId = memberId;
        this.equipmentId = equipmentId;
        this.rentalDate = rentalDate;
        this.expectedReturnDate = expectedReturnDate;
        this.status = status;
        this.notes = notes;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getMemberId() {
        return memberId;
    }
    
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
    public int getEquipmentId() {
        return equipmentId;
    }
    
    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    public String getRentalDate() {
        return rentalDate;
    }
    
    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    public String getExpectedReturnDate() {
        return expectedReturnDate;
    }
    
    public void setExpectedReturnDate(String expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }
    
    public String getActualReturnDate() {
        return actualReturnDate;
    }
    
    public void setActualReturnDate(String actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "EquipmentRental{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", equipmentId=" + equipmentId +
                ", rentalDate='" + rentalDate + '\'' +
                ", expectedReturnDate='" + expectedReturnDate + '\'' +
                ", actualReturnDate='" + actualReturnDate + '\'' +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
} 