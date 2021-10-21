# Java MySQL manipulation with CSV file

### 1 You can clone it from GitHub by running following command.

```
$ git clone https://github.com/ZionInMatrix/CSVtoDatabase.git
```
The code will allow us to read data from the CSV file and write it to the MySQL database, after which the data from the database will be displayed in the console and the file will be moved to another path.

### 2. Import project into Intellij IDEA Ultimate.

### 3. Please create an ems database in MySQL or of your choice, plus an employee table with fields: ico, nazevfirmy, adresfirmy, email, jmeno, prijmeni, datum.

### 4. Update MySQL connection configurations into connectToDatabase and put in to it current settings.

```
String jdbcURL = "jdbc:mysql://localhost:3306/ems";
String userName = "root";
String password = "yourpasswd";
```

### 5. The CSV file with which you will work is located, the file filled with sample data.

```
static final String pathToCSV = System.getProperty("user.dir") + "src/main/resources/data.csv";
```


