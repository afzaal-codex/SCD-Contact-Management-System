@echo off
rem Compile all Java files in src/ to bin/
if not exist bin mkdir bin
.jdk\bin\javac.exe -d bin src\*.java
echo Compilation complete.
