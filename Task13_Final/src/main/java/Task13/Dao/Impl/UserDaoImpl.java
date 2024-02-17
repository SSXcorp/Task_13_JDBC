package Task13.Dao.Impl;


import Task13.ConnectionFactory;
import Task13.Dao.Dao;
import Task13.Exceptions.SameUserEmailException;
import Task13.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements Dao<User> {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final Connection connection;

    public UserDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public Optional<User> get(Long userId) {
        logger.info("Getting user with user_id: " + userId);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?")) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getLong("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setUsersurname(resultSet.getString("usersurname"));
                    user.setEmail(resultSet.getString("email"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting user");
            throw new RuntimeException("Error getting user", e);
        }

        return Optional.empty();
    }

    

    @Override
    public List<User> getAll() {
        logger.info("Getting all users");
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users ORDER BY user_id");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setUsersurname(resultSet.getString("usersurname"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error getting all users");
            throw new RuntimeException("Error getting all users", e);
        }
        return users;
    }

    @Override
    public void save(User user) {
        logger.info("Creating user");
        try { // Check if the email already exists
            try (PreparedStatement checkStatement = connection.prepareStatement("SELECT email FROM users WHERE email = ?")) {
                checkStatement.setString(1, user.getEmail());
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        throw new SameUserEmailException("User with the same email already exists.");
                    }
                }
            }

            try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users(username, usersurname, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                insertStatement.setString(1, user.getUsername());
                insertStatement.setString(2, user.getUsersurname());
                insertStatement.setString(3, user.getEmail());

                int affectedRows = insertStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException | SameUserEmailException e) {
            logger.error("Error creating user");
            throw new RuntimeException("Error creating user", e);
        }
    }


    @Override
    public void update(User user) {
        logger.info("Updating user");
        try {
            // Check if the email already exists for other users
            try (PreparedStatement checkEmailStatement = connection.prepareStatement("SELECT user_id FROM users WHERE email = ? AND user_id != ?")) {
                checkEmailStatement.setString(1, user.getEmail());
                checkEmailStatement.setLong(2, user.getUserId());

                try (ResultSet resultSet = checkEmailStatement.executeQuery()) {
                    if (resultSet.next()) {
                        throw new SameUserEmailException("Another user with the same email already exists.");
                    }
                }
            }

            // If email doesn't exist for other users, proceed with the update
            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET username = ?, usersurname = ?, email = ? WHERE user_id = ?")) {
                updateStatement.setString(1, user.getUsername());
                updateStatement.setString(2, user.getUsersurname());
                updateStatement.setString(3, user.getEmail());
                updateStatement.setLong(4, user.getUserId());

                int affectedRows = updateStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating user failed, no rows affected.");
                }
            }
        } catch (SQLException | SameUserEmailException e) {
            logger.error("Error when updating user");
            throw new RuntimeException("Error updating user", e);
        }
    }



    @Override
    public void delete(Long id) { // was long, but database was already created
        logger.info("Deleting user");
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE user_id = ?")) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting user");
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public boolean doesUserExist(long userId) {
        logger.info("Check if user exists");
        boolean exists;
        try (PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM Users WHERE user_id = ?")) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                exists = resultSet.next();
            }
        } catch (SQLException e) {
            logger.error("Error checking if user exists");
            throw new RuntimeException("Error checking if user exists", e);
        }
        return exists;
    }


}
