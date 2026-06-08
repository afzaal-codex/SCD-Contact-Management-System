@echo off
rem Compile all files including tests
if not exist bin mkdir bin
.jdk\bin\javac.exe -cp "lib\*" -d bin src\*.java

rem Run JUnit tests
.jdk\bin\java.exe -cp "bin;lib\*" org.junit.runner.JUnitCore ContactTest
