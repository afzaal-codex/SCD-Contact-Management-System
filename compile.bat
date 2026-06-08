@echo off
rem Compile all Java files in src/ to bin/
if not exist bin mkdir bin
.jdk\bin\javac.exe -d bin src\Contact.java src\ContactForm.java src\Main.java
echo Compilation complete.
