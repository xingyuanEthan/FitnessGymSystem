// src/entity/Member.java
package entity;

import java.util.Date;

public class Member {
    private int id;
    private String name;
    private String username;
    private String password;
    private String gender;
    private int age;
    private String phone;
    private String idCard;
    private String level;
    private Date registrationDate;
    private String status;
    
    // 构造函数
    public Member() {}
    
    public Member(String name, String gender, int age, String phone, String idCard, String level) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.idCard = idCard;
        this.level = level;
        this.status = "active";
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getLevel() {
        return level;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", level='" + level + '\'' +
                ", registrationDate=" + registrationDate +
                ", status='" + status + '\'' +
                '}';
    }

    // Getter & Setter（建议使用IDE自动生成）
}
