package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("121/12/1212")); // day field more than 2 numbers
        assertFalse(Birthday.isValidBirthday("12/121/1212")); // month field more than 2 numbers
        assertFalse(Birthday.isValidBirthday("12/12/121")); // year field not exactly 4 numbers
        assertFalse(Birthday.isValidBirthday("12/Jan/1212")); // alphabets within fields
        assertFalse(Birthday.isValidBirthday("12/1 2/1212")); // spaces within birthday

        // valid phone numbers
        assertTrue(Birthday.isValidBirthday("01/01/0101")); // exactly 2,2,4 numbers
        assertTrue(Birthday.isValidBirthday("01/1/0101")); // day and month fields can be 1 to 2 numbers long
        assertTrue(Birthday.isValidBirthday("1/1/1111"));
    }

}
