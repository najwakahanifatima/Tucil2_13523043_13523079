@echo off

:: Create bin directory if it doesn't exist
if not exist bin mkdir bin

:: Compile all Java files from src directory into bin
javac -d bin src\errorMeasurement\*.java src\tools\*.java src\imageHandler\*.java src\*.java

:: Run the main class
java -cp bin Main

pause