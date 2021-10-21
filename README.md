# Java MySQL manipulation with CSV file

### 1 You can clone it from GitHub by running following command.

```
$ git clone https://github.com/ZionInMatrix/CSVtoDatabase.git
```
The code will allow us to read data from the CSV file and write it to the MySQL database, after which the data from the database will be displayed in the console and the file will be moved to another path.

### 2. Import project into Intellij IDEA Ultimate.

### 3. Please create an ems database in MySQL or of your choice, plus an employee table with fields: 

CREATE TABLE `employee` (
  `ico` int NOT NULL,
  `nazevfirmy` varchar(45) COLLATE utf8_bin NOT NULL,
  `adresfirmy` varchar(45) COLLATE utf8_bin NOT NULL,
  `email` varchar(45) COLLATE utf8_bin NOT NULL,
  `jmeno` varchar(45) COLLATE utf8_bin NOT NULL,
  `prijmeni` varchar(45) COLLATE utf8_bin NOT NULL,
  `datum` varchar(45) COLLATE utf8_bin NOT NULL,
  UNIQUE KEY `ico_UNIQUE` (`ico`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin

### 4. Update MySQL connection configurations into connectToDatabase and put in to it current settings.

```
String jdbcURL = "jdbc:mysql://localhost:3306/ems";
String userName = "root";
String password = "yourpasswd";
```

### 5. When the data is transferred to the database, the file will be moved to the specified path

