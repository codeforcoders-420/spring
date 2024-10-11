import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadDataFromExcel {

    // Method to upload the Excel file and validate data
    public static void UploadFileAndValidate(String filelocAddr) throws IOException, SQLException {

        String filePath = filelocAddr;
        String url = "JDBC:MySql://${MYSQL_HOST:LOCALHOST}:3306/mySQL_Learning/Student";
        String userName = "root";
        String userPassword = "BeStrong23!";

        Connection connect = null;
        Statement stmnt = null;
        ResultSet rs = null;

        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        try {
            connect = JDBCUtility.getDBConnection();
            if (connect != null) {
                stmnt = connect.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Reading the first cell of each row to check if they are present in the database
        Iterator<Row> row = sheet.iterator();
        Row headerRow = row.next();

        // Add a new header for validation results
        Cell newHeaderCell = headerRow.createCell(headerRow.getLastCellNum(), CellType.STRING);
        newHeaderCell.setCellValue("VALIDATION");

        while (row.hasNext()) {
            Row nextRow = row.next();

            String cellValue = dataFormatter.formatCellValue(nextRow.getCell(0));
            System.out.println("CELL VALUE " + cellValue);
            if (!cellValue.isEmpty()) {
                if (stmnt != null) {
                    String query = "SELECT COUNT(*) FROM Student WHERE StudentName = ?";
                    PreparedStatement preparedStatement = connect.prepareStatement(query);
                    preparedStatement.setString(1, cellValue);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Create a new cell in the row to store validation results
                    int lastCellNum = nextRow.getLastCellNum();
                    Cell newCell = nextRow.createCell(lastCellNum, CellType.STRING);

                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("Value " + cellValue + " exists in the database.");
                        newCell.setCellValue("exists in the database.");
                    } else {
                        System.out.println("Value " + cellValue + " does not exist in the database.");
                        newCell.setCellValue("does not exist in the database.");
                    }
                }
            }
        }

        // Write the output to a new Excel file
        try (FileOutputStream fos = new FileOutputStream("/shared/path/StudentValidation.xlsx")) {
            workbook.write(fos);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            workbook.close();
        }
    }
}
