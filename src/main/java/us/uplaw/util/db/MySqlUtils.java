package us.uplaw.util.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.uplaw.model.Court;
import us.uplaw.model.Document;
import us.uplaw.util.AppConstants;

public class MySqlUtils {

  private static final Logger log = LoggerFactory.getLogger(MySqlUtils.class);

  public static Connection getConnection() {
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName(AppConstants.JDBC_DRIVER);
      log.info("Connecting to a selected database...");
      conn = DriverManager.getConnection(AppConstants.DATABASE_URL, AppConstants.DATABASE_USER, AppConstants.DATABASE_PASSWORD);
      log.info("Connected database successfully...");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public static void createTable() {
    Connection conn = getConnection();
    Statement stmt = null;
      try {
          Class.forName(AppConstants.JDBC_DRIVER);
          log.info("Connected database successfully...");
          log.info("Creating table in given database...");
          stmt = conn.createStatement();
          String sql = "CREATE TABLE REGISTR ATION " +
                  "(id INTEGER not NULL, " +
                  " first VARCHAR(255), " +
                  " last VARCHAR(255), " +
                  " age INTEGER, " +
                  " PRIMARY KEY ( id ))";
          stmt.executeUpdate(sql);
          log.info("Created table in given database...");
      } catch (SQLException se) {
          se.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try {
              if (stmt != null)
                  conn.close();
          } catch (SQLException se) {
          }
          try {
              if (conn != null)
                  conn.close();
          } catch (SQLException se) {
              se.printStackTrace();
          }
      }
      log.info("Goodbye!");
  }

    public static long insertDocument(Document document) {
        String SQL = "INSERT INTO document(title,uri,document_type,content,timestamp ) "
                + "VALUES(?,?,?,?,?)";
        long id = 0;
        try (Connection conn =getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, document.getTitle());
            pstmt.setString(2, document.getUri());
            pstmt.setString(3, document.getDocumentType());
            //pstmt.setString(4, document.getSource());
            pstmt.setString(4, document.getContent());
            pstmt.setDate(5, Date.valueOf(document.getTimestamp()));


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    log.info(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return id;
    }

  public static long insertCourt(Court court) {
    String SQL = "INSERT INTO court(court_abbreviation, court_citation_abbreviation, court_end_date, court_homepage, court_jurisdiction, court_name, court_start_date) "
            + "VALUES(?,?,?,?,?,?,?)";
    long id = 0;
    try (Connection conn =getConnection();
         PreparedStatement pstmt = conn.prepareStatement(SQL,
                 Statement.RETURN_GENERATED_KEYS)) {
      pstmt.setString(1, court.getCourtAbbreviation());
      pstmt.setString(2, court.getCourtCitationAbbreviation());
      //todo fix this toEpochDay nonsense to local date
      pstmt.setDate(3, new java.sql.Date(court.getCourtEndDate().toEpochDay()));
      pstmt.setString(4, court.getCourtHomepage());
      pstmt.setString(5, court.getCourtJurisdiction());
      pstmt.setString(6, court.getCourtName());
      pstmt.setDate(7, new java.sql.Date(court.getCourtStartDate().toEpochDay()));


      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
          if (rs.next()) {
            id = rs.getLong(1);
          }
        } catch (SQLException ex) {
          log.info(ex.getMessage());
        }
      }
    } catch (SQLException ex) {
      log.info(ex.getMessage());
    }
    return id;
  }

  public static void main(String[] args) {
    //createTable();

  }

}