package Task13.Dao.Impl;



import Task13.ConnectionFactory;
import Task13.Dao.Dao;
import Task13.model.UserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDetailsDaoImpl implements Dao<UserDetails> {

    private static final Logger logger = LogManager.getLogger(UserDetailsDaoImpl.class);
    private final Connection connection;

    public UserDetailsDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public Optional<UserDetails> get(Long userId) {
        logger.info("Getting user_details where user_id: " + userId);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_details WHERE user_id = ?")) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UserDetails concreteUserDetails = new UserDetails();
                    concreteUserDetails.setUserId(resultSet.getLong("user_id"));
                    concreteUserDetails.setJob(resultSet.getString("job"));
                    concreteUserDetails.setAddress(resultSet.getString("address"));
                    concreteUserDetails.setSalary(resultSet.getLong("salary"));
                    return Optional.of(concreteUserDetails);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting user_details");
            throw new RuntimeException("Error getting user_details", e);
        }

        return Optional.empty();
    }

    @Override
    public List<UserDetails> getAll() {
        logger.info("Getting ALL user_details");
        List<UserDetails> userDetails = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_details ORDER BY user_id");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UserDetails concreteUserDetails = new UserDetails();
                concreteUserDetails.setUserId(resultSet.getLong("user_id"));
                concreteUserDetails.setJob(resultSet.getString("job"));
                concreteUserDetails.setAddress(resultSet.getString("address"));
                concreteUserDetails.setSalary(resultSet.getLong("salary"));
                userDetails.add(concreteUserDetails);
            }
        } catch (SQLException e) {
            logger.error("Error getting all user_details");
            throw new RuntimeException("Error getting all user_details", e);
        }
        return userDetails;
    }

    @Override
    public void save(UserDetails userDetails) {
        logger.info("Creating user_details");
        try {
            if (!userExistsAtUsersTable(userDetails.getUserId())) {  // Check if the user exists
                throw new RuntimeException("Cannot add user details. User does not exist.");
            } else if (userExistsAtUserDetailsTable(userDetails.getUserId())) {
                update(userDetails);
            } else {
                try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO user_details (user_id, job, address, salary) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                    insertStatement.setLong(1, userDetails.getUserId());
                    insertStatement.setString(2, userDetails.getJob());
                    insertStatement.setString(3, userDetails.getAddress());
                    insertStatement.setLong(4, userDetails.getSalary());

                    int affectedRows = insertStatement.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Creating user_details failed, no rows affected.");
                    }

                    try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                           userDetails.setUserId(generatedKeys.getLong(1));
                        } else {
                            throw new SQLException("Creating user_details failed, no ID obtained.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating user_details");
            throw new RuntimeException("Error creating user_details", e);
        }
    }

    // to check if a user exists
    private boolean userExistsAtUsersTable(Long userId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM users WHERE user_id = ?")) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if user exists, false otherwise
            }
        }
    }

    private boolean userExistsAtUserDetailsTable(Long userId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM user_details WHERE user_id = ?")) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if user exists, false otherwise
            }
        }
    }



    @Override
    public void update(UserDetails userDetails) {
        logger.info("Updating user_details");
        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE user_details SET job = ?, address = ?, salary = ? WHERE user_id = ?")) {
            updateStatement.setString(1, userDetails.getJob());
            updateStatement.setString(2, userDetails.getAddress());
            updateStatement.setLong(3, userDetails.getSalary());
            updateStatement.setLong(4, userDetails.getUserId());

            int affectedRows = updateStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating user_details failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error updating user_details");
            throw new RuntimeException("Error updating user_details", e);
        }
    }



    @Override
    public void delete(Long id) {
        logger.info("Deleting user_details");
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM user_details WHERE user_id = ?")) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user_details failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting user_details");
            throw new RuntimeException("Error deleting user_details", e);
        }
    }

}
