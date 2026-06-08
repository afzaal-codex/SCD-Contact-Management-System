import static org.junit.Assert.*;
import org.junit.Test;

// class to test contact functions
public class ContactTest {

    // test contact object creation
    @Test
    public void testContactCreation() {
        Contact c = new Contact(
                "1",
                "Ali",
                "03001234567",
                "ali@gmail.com",
                "Lahore"
        );

        assertEquals("Ali", c.getName());
    }

    // test contact phone number getter
    @Test
    public void testPhone() {
        Contact c = new Contact(
                "1",
                "Ali",
                "03001234567",
                "ali@gmail.com",
                "Lahore"
        );

        assertEquals("03001234567", c.getPhone());
    }
}
