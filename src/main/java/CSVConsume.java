import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class CSVConsume {
    public static void main(String[] args) {
        String filePath = "/Users/newlife/Desktop/CSVtoDatabase/src/main/resources/data.csv";

        writeDataAndFetchFromDatabase(filePath);
    }

    /**
     * The main method in which most of the functionality of the code will be executed
     * NOTE: I need use refactoring for this method, but I hope it's ok in this time
     *
     * @param filePath path to read the file
     */
    public static void writeDataAndFetchFromDatabase(String filePath) {

        int batchSize = 20;
        String lineText = null;
        int count = 0;

        try {
            Connection connection = connectToDatabase();

            Statement mystatement = connection.createStatement();

            ResultSet codebase = mystatement.executeQuery("select * from employee");

            String sql1 = "insert into employee(ico, nazevfirmy, adresfirmy, email, jmeno, prijmeni, datum) values (?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql1);
            BufferedReader lineReader = new BufferedReader(new FileReader(filePath));

            lineReader.readLine();

            while ((lineText = lineReader.readLine()) != null) {
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

            while (codebase.next()) {
                System.out.println(codebase.getString("ico") + "  " + codebase.getString("nazevfirmy") +
                        "  " + codebase.getString("adresfirmy") + " " + codebase.getString("email") + " "
                        + codebase.getString("jmeno") + " " + codebase.getString("prijmeni") + " " + codebase.getString("datum"));

            }

            moveFile(filePath);

            lineReader.close();
            statement.executeBatch();
            connection.commit();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * The method will transfer the file to another directory
     *
     * @param filePath accepted file directory
     */
    public static void moveFile(String filePath) throws IOException {
        Path temp = Files.move(Paths.get(filePath),
                Paths.get("/Users/newlife/Desktop/CSVtoDatabase/src/main/data.csv"));

        System.out.println("File moved successfully");
    }

    /**
     * The method will connect to the database
     *
     * @return
     * @throws SQLException
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
}
