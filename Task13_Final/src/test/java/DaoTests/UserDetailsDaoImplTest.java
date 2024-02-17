package DaoTests;

import Task13.Dao.Impl.*;
import Task13.model.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsDaoImplTest {

    private UserDetailsDaoImpl userDetailsDaoImpl;

    @BeforeEach
    public void setUp() {
        userDetailsDaoImpl = new UserDetailsDaoImpl();
    }

    @Test
    public void getUserDetailsPositiveTest() {
        UserDetails detailsToCheck = new UserDetails();
        detailsToCheck.setAddress("St. Deribasivska 13");
        detailsToCheck.setJob("PM");
        detailsToCheck.setSalary(120000L);

        Optional<UserDetails> userDetails = userDetailsDaoImpl.get(2L);
		UserDetails userDetails2 = userDetails.orElse(null);

        assert userDetails2 != null;
        assertEquals(detailsToCheck.getAddress(), userDetails2.getAddress());
        assertEquals(detailsToCheck.getSalary(), userDetails2.getSalary());
        assertEquals(detailsToCheck.getJob(), userDetails2.getJob());
    }

    @Test
    public void saveAndDeleteUserDetailsPositiveTest() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(9L);
        userDetails.setAddress("St. Deribasivska 13");
        userDetails.setJob("PM");
        userDetails.setSalary(120000L);

        // Save UserDetails to the database
        userDetailsDaoImpl.save(userDetails);

        // Retrieve UserDetails from the database
        Optional<UserDetails> savedUserDetails = userDetailsDaoImpl.get(9L);
        UserDetails savedUserDetails2 = savedUserDetails.orElse(null);

        // Perform assertions
        assertNotNull(savedUserDetails2);
        assertEquals(userDetails.getUserId(), savedUserDetails2.getUserId());
        assertEquals(userDetails.getAddress(), savedUserDetails2.getAddress());
        assertEquals(userDetails.getSalary(), savedUserDetails2.getSalary());
        assertEquals(userDetails.getJob(), savedUserDetails2.getJob());

        userDetailsDaoImpl.delete(9L);
    }

    @Test
    public void updateUserDetailsPositiveTest() {
        // Create UserDetails object to update
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(1L);
        userDetails.setAddress("Bul. Academy Voltza 12");
        userDetails.setJob("Java Developer");
        userDetails.setSalary(6200L);

        // Update UserDetails in the database
        userDetailsDaoImpl.update(userDetails);

        // Retrieve UserDetails from the database
        Optional<UserDetails> updatedUserDetails = userDetailsDaoImpl.get(1L);
        UserDetails updatedUserDetails2 = updatedUserDetails.orElse(null);

        // Perform assertions
        assertNotNull(updatedUserDetails2);
        assertEquals(userDetails.getUserId(), updatedUserDetails2.getUserId());
        assertEquals(userDetails.getAddress(), updatedUserDetails2.getAddress());
        assertEquals(userDetails.getSalary(), updatedUserDetails2.getSalary());
        assertEquals(userDetails.getJob(), updatedUserDetails2.getJob());
    }

    @Test
    public void getAllUserDetailsPositiveTest() {
        // Retrieve all UserDetails from the database
        List<UserDetails> userDetailsList = userDetailsDaoImpl.getAll();

        // Perform assertions
        assertNotNull(userDetailsList);
        assertFalse(userDetailsList.isEmpty());

        for (UserDetails userDetails : userDetailsList) {
            System.out.println("User ID: " + userDetails.getUserId());
            System.out.println("Job: " + userDetails.getJob());
            System.out.println("Address: " + userDetails.getAddress());
            System.out.println("Salary: " + userDetails.getSalary());
            System.out.println("-------------------------");
        }
    }


}