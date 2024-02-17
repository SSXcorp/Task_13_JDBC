package Task13.model;

public class UserDetails {

    private Long userId;
    private String job;
    private String address;
    private Long salary;

    public UserDetails() {

    }

    public UserDetails(Long userId, String job, String address, Long salary) {
        this.userId = userId;
        this.job = job;
        this.address = address;
        this.salary = salary;
    }

    public Long getUserId() {
        return userId;
    }

    public String getJob() {
        return job;
    }

    public String getAddress() {
        return address;
    }

    public Long getSalary() {
        return salary;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
