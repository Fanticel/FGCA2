import Database.FileManger;
import Database.SQLFileManager;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class mainTest
{
  public static void main(String[] args) throws SQLException {
    SQLFileManager sqlFileManager = new SQLFileManager();
    System.out.println(sqlFileManager.getEventsFromFile());
  }
}
