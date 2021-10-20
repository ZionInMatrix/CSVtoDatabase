import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class CSVConsume {
    static final String pathToCSV = System.getProperty("user.dir") + "src/main/resources/data.csv";

    public static void main(String[] args) throws SQLException {
        writeDataFromCSVToDatabase();
        fetchDataFromDatabase();
    }

    /**
     * The main method in which most of the functionality of the code will be executed
     */
    public static void writeDataFromCSVToDatabase() {
        int batchSize = 20;
        String lineText = null;
        int count = 0;

        try {
            Connection connection = connectToDatabase();
            String sql1 = "insert into employee(ico, nazevfirmy, adresfirmy, email, jmeno, prijmeni, datum) values (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql1);
            BufferedReader bufferedReader = readPath();
            bufferedReader.readLine();

            while ((lineText = bufferedReader.readLine()) != null) {
                String[] data = lineText.split(",");

                String ico = data[0];
                String nazevFirmy = data[1];
                String adresFirmy = data[2];
                String email = data[3];
                String jmeno = data[4];
                String prijmeni = data[5];
                String datum = data[6];

                statement.setInt(1, parseInt(ico));
                statement.setString(2, nazevFirmy);
                statement.setString(3, adresFirmy);
                statement.setString(4, email);
                statement.setString(5, jmeno);
                statement.setString(6, prijmeni);
                statement.setString(7, datum);
                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            bufferedReader.close();
            statement.executeBatch();
            connection.commit();
            connection.close();

            moveFileToAnotherPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method will transfer the file to another directory
     */
    public static void moveFileToAnotherPath() throws IOException {
        Path temp = Files.move(Paths.get(pathToCSV),
                Paths.get("/Users/newlife/Desktop/CSVtoDatabase/src/main/data.csv"));

        System.out.println("File moved successfully");
    }

    /**
     * The method will connect to the database
     *
     * @throws SQLException if something goes wrong
     */
    public static Connection connectToDatabase() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/ems";
        String userName = "root";
        String password = "yourpasswd";

        Connection connection = null;

        connection = DriverManager.getConnection(jdbcURL, userName, password);
        connection.setAutoCommit(false);

        return connection;
    }

    /**
     * the method will be read CSV file from filePath
     *
     * @return BufferedReader
     * @throws FileNotFoundException if something goes wrong
     */
    public static BufferedReader readPath() throws FileNotFoundException {
        return new BufferedReader(new FileReader(pathToCSV));
    }

    /**
     * The method will be fetch data from database
     *
     * @throws SQLException if something goes wrong
     */
    public static void fetchDataFromDatabase() throws SQLException {
        Connection connection = connectToDatabase();
        Statement mystatement = connection.createStatement();

        ResultSet codebase = mystatement.executeQuery("select * from employee");

        while (codebase.next()) {
            System.out.println(codebase.getString("ico")
                    + " " + codebase.getString("nazevfirmy")
                    + " " + codebase.getString("adresfirmy")
                    + " " + codebase.getString("email")
                    + " " + codebase.getString("jmeno")
                    + " " + codebase.getString("prijmeni")
                    + " " + codebase.getString("datum"));
        }

        connection.commit();
        connection.close();
    }
}
