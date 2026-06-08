import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.IOException;

// contact form class that creates the GUI
public class ContactForm extends JFrame {

    // form text fields
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;

    // five buttons
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnClear;

    // menu bar items
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem itemSave;
    private JMenuItem itemLoad;
    private JMenuItem itemExit;

    // table to display contacts
    private JTable table;
    private DefaultTableModel tableModel;

    // in-memory list to store contacts
    private ArrayList<Contact> contactList;

    public ContactForm() {
        contactList = new ArrayList<>();

        // frame settings
        setTitle("Contact Management System");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // initialize gui components
        initComponents();

        // display the window
        setVisible(true);
    }

    private void initComponents() {
        // create menu bar
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");

        itemSave = new JMenuItem("Save");
        itemLoad = new JMenuItem("Load");
        itemExit = new JMenuItem("Exit");

        menuFile.add(itemSave);
        menuFile.add(itemLoad);
        menuFile.addSeparator();
        menuFile.add(itemExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        // create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create left panel for form inputs and buttons
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setPreferredSize(new Dimension(350, 400));

        // input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Contact Information"));

        inputPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        inputPanel.add(txtPhone);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        inputPanel.add(txtAddress);

        leftPanel.add(inputPanel, BorderLayout.CENTER);

        // buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnClear);

        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // create right panel for JTable
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Contact List"));

        // columns for table
        String[] columns = {"ID", "Name", "Phone", "Email", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // close application on exit click
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // save data action
        itemSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDataToFile();
            }
        });

        // load data action
        itemLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDataFromFile();
            }
        });

        // add contact action
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        // update contact action
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        // delete contact action
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        // search contact action
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });

        // clear form action
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }

    // method to add contact
    private void addContact() {
        // validate inputs first
        if (!validateInput()) {
            return;
        }

        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();

        // check if id already exists
        for (Contact c : contactList) {
            if (c.getId().equals(id)) {
                JOptionPane.showMessageDialog(this, "ID already exists!");
                return;
            }
        }

        // create a new contact object
        Contact contact = new Contact(id, name, phone, email, address);

        // add it to the list
        contactList.add(contact);

        // reload the table
        loadTable();

        // reset form inputs
        clearForm();

        // show success message
        JOptionPane.showMessageDialog(this, "Contact added successfully!");
    }

    // method to update contact
    private void updateContact() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID to update");
            return;
        }

        // validate other fields first
        if (!validateInput()) {
            return;
        }

        boolean found = false;
        for (Contact c : contactList) {
            if (c.getId().equals(id)) {
                c.setName(txtName.getText().trim());
                c.setPhone(txtPhone.getText().trim());
                c.setEmail(txtEmail.getText().trim());
                c.setAddress(txtAddress.getText().trim());
                found = true;
                break;
            }
        }

        if (found) {
            // reload table and clear inputs
            loadTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Contact updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!");
        }
    }

    // method to validate input fields
    private boolean validateInput() {
        // ID must contain digits only
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID is required");
            return false;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID must contain digits only");
            return false;
        }

        // Name must not exceed 3 words and each word must start with a capital letter
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required");
            return false;
        }
        String[] words = name.split("\\s+");
        if (words.length > 3) {
            JOptionPane.showMessageDialog(this, "Name must not be more than 3 words");
            return false;
        }
        for (String w : words) {
            if (w.isEmpty() || !Character.isUpperCase(w.charAt(0))) {
                JOptionPane.showMessageDialog(this, "First letter of each word in Name must be capital");
                return false;
            }
        }

        // Phone must contain digits only and must be at least 11 digits
        String phone = txtPhone.getText().trim();
        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone is required");
            return false;
        }
        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone must contain digits only");
            return false;
        }
        if (phone.length() < 11) {
            JOptionPane.showMessageDialog(this, "Phone must be at least 11 digits");
            return false;
        }

        // Email must contain @
        if (!txtEmail.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "Invalid Email");
            return false;
        }

        return true;
    }

    // method to delete contact
    private void deleteContact() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID to delete");
            return;
        }

        boolean found = false;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId().equals(id)) {
                contactList.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            loadTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Contact deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!");
        }
    }

    // method to search contact
    private void searchContact() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID to search");
            return;
        }

        Contact foundContact = null;
        for (Contact c : contactList) {
            if (c.getId().equals(id)) {
                foundContact = c;
                break;
            }
        }

        if (foundContact != null) {
            // populate text fields with found details
            txtName.setText(foundContact.getName());
            txtPhone.setText(foundContact.getPhone());
            txtEmail.setText(foundContact.getEmail());
            txtAddress.setText(foundContact.getAddress());
            JOptionPane.showMessageDialog(this, "Contact found!");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!");
        }
    }

    // method to load the list data into table
    private void loadTable() {
        tableModel.setRowCount(0);
        for (Contact c : contactList) {
            String[] row = {c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getAddress()};
            tableModel.addRow(row);
        }
    }

    // method to save data to file
    private void saveDataToFile() {
        try {
            ContactFile.saveData(contactList);
            JOptionPane.showMessageDialog(this, "Data saved successfully to contacts.txt!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to load data from file
    private void loadDataFromFile() {
        try {
            contactList = ContactFile.loadData();
            loadTable();
            JOptionPane.showMessageDialog(this, "Data loaded successfully from contacts.txt!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to clear all input fields
    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
    }
}
