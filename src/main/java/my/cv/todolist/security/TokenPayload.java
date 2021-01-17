package my.cv.todolist.security;

import java.util.Date;

public class TokenPayload {
    private Long userId;
    private String email;
    private Date cretionTime;

    public TokenPayload(Long userId, String email, Date cretionTime) {
        this.userId = userId;
        this.email = email;
        this.cretionTime = cretionTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCretionTime() {
        return cretionTime;
    }

    public void setCretionTime(Date cretionTime) {
        this.cretionTime = cretionTime;
    }

    @Override
    public String toString() {
        return "TokenPayload{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", cretionDate=" + cretionTime +
                '}';
    }
}
