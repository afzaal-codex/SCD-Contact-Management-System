import java.io.*;
import java.util.ArrayList;

// class to handle file operations for contacts
public class ContactFile {

    private static final String FILE_NAME = "contacts.txt";

    // save contact list data to file
    public static void saveData(ArrayList<Contact> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));

        for (Contact c : list) {
            writer.write(
                    c.getId() + "," +
                    c.getName() + "," +
                    c.getPhone() + "," +
                    c.getEmail() + "," +
                    c.getAddress()
            );
            writer.newLine();
        }

        writer.close();
    }

    // load contact list data from file
    public static ArrayList<Contact> loadData() throws IOException {
        ArrayList<Contact> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        // check if the file exists
        if (!file.exists()) {
            return list;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            // verify line has all 5 fields
            if (data.length == 5) {
                Contact contact = new Contact(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );
                list.add(contact);
            }
        }

        reader.close();
        return list;
    }
}
