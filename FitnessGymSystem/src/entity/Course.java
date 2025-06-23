package entity;

import java.sql.Date;

public class Course {
    private int id;
    private String name;
    private String description;
    private String instructor; // 教练姓名
    private int instructorId;
    private String instructorName; // 用于显示教练姓名
    private int duration; // 课程时长（分钟）
    private double price;
    private int maxCapacity;
    private int maxStudents; // 最大学生数
    private String level; // 难度等级
    private String schedule;
    private String status;
    private Date startDate; // 开始日期
    
    // 构造函数
    public Course() {}
    
    public Course(String name, String description, int instructorId, int duration, double price, String schedule) {
        this.name = name;
        this.description = description;
        this.instructorId = instructorId;
        this.duration = duration;
        this.price = price;
        this.schedule = schedule;
        this.maxCapacity = 20;
        this.maxStudents = 20;
        this.status = "active";
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public int getInstructorId() {
        return instructorId;
    }
    
    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
    
    public String getInstructorName() {
        return instructorName;
    }
    
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getMaxCapacity() {
        return maxCapacity;
    }
    
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public int getMaxStudents() {
        return maxStudents;
    }
    
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
    
    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    public String getSchedule() {
        return schedule;
    }
    
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                ", instructorId=" + instructorId +
                ", instructorName='" + instructorName + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", maxCapacity=" + maxCapacity +
                ", maxStudents=" + maxStudents +
                ", level='" + level + '\'' +
                ", schedule='" + schedule + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                '}';
    }
} 