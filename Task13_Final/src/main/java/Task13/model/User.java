package Task13.model;

import java.util.Objects;

public class User {

    private Long userId;
    private String username;
    private String usersurname;
    private String email;

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUsersurname() {
        return usersurname;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsersurname(String usersurname) {
        this.usersurname = usersurname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId == user.userId &&
                Objects.equals(username, user.username) &&
                Objects.equals(usersurname, user.usersurname) &&
                Objects.equals(email, user.email);
    }
}
