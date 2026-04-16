#!/bin/bash
cd ~/LibraryManagementSystem
javac -cp lib/mysql-connector-j-8.0.33.jar src/**/*.java src/*.java -d out
java -cp out:lib/mysql-connector-j-8.0.33.jar Main
