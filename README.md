# Contact Management System

This is a simple Contact Management System made in Java Swing for the SCD Semester Project. It helps users manage contacts by storing details like ID, Name, Phone, Email, and Address.

## Features

* Add new contacts (with input validation)
* Update contact details using ID
* Delete contacts using ID
* Search contacts using ID
* Clear the input form
* Save contacts to `contacts.txt`
* Load contacts from `contacts.txt`
* Exit application

## Tools Used

* Java JDK 17 (Swing library for GUI)
* JUnit 4 (for unit testing)
* Git and GitHub

## How to Run

Follow these steps to compile and run the application:

1. **Setup Environment**: Run the setup script in PowerShell to download the required JDK and JUnit libraries.
   ```powershell
   powershell -ExecutionPolicy Bypass -File .\setup.ps1
   ```
2. **Compile the Project**: Compile the Java source files.
   ```cmd
   .\compile.bat
   ```
3. **Run the Application**: Run the compiled Swing GUI.
   ```cmd
   .\run.bat
   ```
4. **Run Unit Tests**: Run the JUnit unit tests.
   ```cmd
   .\test.bat
   ```

## Project Structure

```text
├── src/
│   ├── Contact.java          (Data model)
│   ├── ContactFile.java      (File save and load logic)
│   ├── ContactForm.java      (Swing GUI and event handling)
│   ├── ContactTest.java      (JUnit test cases)
│   └── Main.java             (Main entry point)
├── lib/                      (JUnit and Hamcrest JARs - downloaded by setup)
├── compile.bat               (Compile script)
├── run.bat                   (Run script)
├── test.bat                  (Test script)
├── setup.ps1                 (Setup environment script)
├── .gitignore                (Git ignore rules)
└── README.md                 (Project documentation)
```

## Screenshots

*(Screenshots will be added here)*
