# SpeedTyping

SpeedTyping is a competitive typing test targeted for ages 12-21 that provides new vocabulary and compelling stories in an engaging learning enviroment.
SpeedTyping utilizes SpringBoot and JPA on the backend and Bootstrap on the frontend.

<img width="500" alt="image (2)" src="https://github.com/user-attachments/assets/1fd9a809-baec-4f8a-8799-619df77197bc">
<img width="500" height="300" alt="Image 11-18-24 at 11 29 AM" src="https://github.com/user-attachments/assets/80c23fdc-e171-4216-9197-46851ec5a1b0">

## Application Setup

### Install Your Database

1. Install MySQL on your computer if you haven't already. 

```bash
https://dev.mysql.com/downloads/mysql/
```

Select the proper download file for your operating system using the dropdown menu. For macOS , make sure to select the (ARM, 64-bit) download. For Intel-based Macs, make sure to select (x84, 64-bit).

2. Download Java 21 on your computer if you haven't already.

```bash
https://www.oracle.com/java/technologies/downloads/
```
3. Clone our GitHub Repository onto your machine.

```bash
git clone https://github.com/jackaholy/SpeedTyping
```

4. Use an IDE:

Eclipse:
```bash
Windows: https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2023-09/R/eclipse-java-2023-09-R-win32-x86_64.zip
MacOS (Silicon): https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2023-09/R/eclipse-java-2023-09-R-macosx-cocoa-aarch64.dmg
MacOS (Intel): https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2023-09/R/eclipse-java-2023-09-R-macosx-cocoa-x86_64.dmg
Linux (x64): https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2023-09/R/eclipse-java-2023-09-R-linux-gtk-x86_64.tar.gz
```
Visual Studio Code:
```bash
https://code.visualstudio.com/download
```

IntelliJ IDEA:
```bash
Windows: https://www.jetbrains.com/idea/download/?section=windows
MacOS: https://www.jetbrains.com/idea/download/?section=mac
Linux: https://www.jetbrains.com/idea/download/?section=linux
```

### Database Setup

1. **Create a database** in MySQL (adjust name, user, and password as needed):
```sql
CREATE DATABASE SpeedTypingDB;
USE SpeedTypingDB;
```

2. Source the population file from the project’s root directory:

```bash
mysql -u root -p SpeedTypingDB < levelData/CreateLevels.sql
```

Or within the MySQL prompt:

```sql
SOURCE src/main/resources/levelData/CreateLevels.sql;
```

3. Verify the tables and data by running

```sql
SELECT * FROM level;
```

### Run the Application

Now that you have everything installed, you are ready to run our program.

1. Navigate to the root directory where your project is stored.
2. Run the program through the terminal using the following command:

```bash
./gradlew bootrun
```
3. Navigate to the browser of your choice and open the following website:

```bash
http://localhost:8080/
```
4. You're all set, get typing!

## Developer Guide

### Running Unit Tests

1. Open up the Terminal Command Prompt within your IDE.
2. Run:
```bash
./gradlew test
```
All of the tests created for our program will run and display whether they are successful or not.

### Accessing External Documentation

In the top level directory you will find the 'externalDocumentation' folder. Within it you will find our plans, gantt chart, ERD, and flowchart.
