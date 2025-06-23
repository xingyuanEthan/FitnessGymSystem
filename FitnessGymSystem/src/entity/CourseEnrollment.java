package entity;

public class CourseEnrollment {
    private int id;
    private int memberId;
    private int courseId;
    private String enrollmentDate;
    private String status;
    private String notes;
    
    public CourseEnrollment() {}
    
    public CourseEnrollment(int memberId, int courseId, String enrollmentDate, String status, String notes) {
        this.memberId = memberId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
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
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
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
        return "CourseEnrollment{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", courseId=" + courseId +
                ", enrollmentDate='" + enrollmentDate + '\'' +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
} 