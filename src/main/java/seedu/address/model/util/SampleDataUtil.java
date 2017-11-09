package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Remark(""), new Avatar(""),
                    getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark("family of mouse"),
                    new Avatar("https://i.imgur.com/xPHOeWL.png"), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""), new Avatar(""),
                    getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark("cousin"),
                    new Avatar(""), getTagSet("family")),
                new Person(new Name("Evie May"), new Phone("90013214"), new Email("eviemay@example.com"),
                    new Address("Blk 101 North Street 10, #21-02"), new Remark("my sister"),
                    new Avatar(""), getTagSet("family")),
                new Person(new Name("Fernan Do"), new Phone("87412454"), new Email("fernando@example.com"),
                    new Address("Blk 5 Bukit Merah Street 50, #01-05"), new Remark("my brother"),
                    new Avatar(""), getTagSet("family")),
                new Person(new Name("Geremy Hit"), new Phone("99210789"), new Email("geremyhit@example.com"),
                    new Address("Blk 32 Upper Lower Street 90, #09-05"), new Remark("salesman"),
                    new Avatar(""), getTagSet("sales")),
                new Person(new Name("Hit To"), new Phone("99345678"), new Email("hitto@example.com"),
                    new Address("Blk 5 South Tima Street 58, #05-11"), new Remark("salesman"),
                    new Avatar(""), getTagSet("sales")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""),
                    new Avatar(""), getTagSet("classmates")),
                new Person(new Name("Jerkin Meat"), new Phone("87513546"), new Email("jerkinmeat@example.com"),
                    new Address("Blk 99 Boon Lay 88, #05-05"), new Remark(""),
                    new Avatar(""), getTagSet("sales")),
                new Person(new Name("Kerlin Mon"), new Phone("91235874"), new Email("kerlinmon@example.com"),
                        new Address("Blk 01 Strong Man Street 01, #01-01"), new Remark("kungfu master"),
                        new Avatar(""), getTagSet("teacher")),
                new Person(new Name("Lima Tey"), new Phone("91462550"), new Email("limatey@example.com"),
                        new Address("Blk 99 English Man Street 09, #09-09"), new Remark("english teacher"),
                        new Avatar(""), getTagSet("teacher")),
                new Person(new Name("Mothe Rtongue"), new Phone("91235874"), new Email("mothertongue@example.com"),
                        new Address("Blk 02 China Town Street 09, #02-02"), new Remark("mandarin teacher"),
                        new Avatar(""), getTagSet("teacher")),
                new Person(new Name("Nina Ohya"), new Phone("91235874"), new Email("nina@example.com"),
                        new Address("Blk 11 Upper Serangoon Street 18, #03-03"), new Remark(""),
                        new Avatar(""), getTagSet("friends")),
                new Person(new Name("Ohno Yee"), new Phone("80458742"), new Email("ohnoyee@example.com"),
                        new Address("Blk 333 Sembawang Close, #16-429"), new Remark(""),
                        new Avatar(""), getTagSet("friends")),
                new Person(new Name("Phantagon Mis"), new Phone("91235874"), new Email("phantagonmis@example.com"),
                        new Address("Blk 05 Mapletree 02, #01-05"), new Remark("HR lady"),
                        new Avatar(""), getTagSet("office")),
                new Person(new Name("Miss Mon"), new Phone("91235874"), new Email("missmon@example.com"),
                        new Address("Blk 01 Sheng Siong Market, #01-01"), new Remark("supermarket aunty"),
                        new Avatar(""), getTagSet("market")),
                new Person(new Name("Rido Kuma"), new Phone("91235874"), new Email("ridokuma@example.com"),
                        new Address("Blk 51 Indian Street 09, #21-11"), new Remark("my best friend"),
                        new Avatar(""), getTagSet("friends")),
                new Person(new Name("Seri Lanka"), new Phone("91235874"), new Email("serilanka@example.com"),
                        new Address("Blk 01 NotSingapore Street 01, #01-01"), new Remark("doesn't stay in malaysia"),
                        new Avatar(""), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""),
                    new Avatar(""), getTagSet("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
