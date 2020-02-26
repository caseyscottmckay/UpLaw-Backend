/*
package us.uplaw.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.postgresql.util.PGobject;

public class PostgresqlUtils {
    static SqlCommands sqlCommands=new SqlCommands();
    //35.237.210.157//35.229.58.248//
    static final String DATABASE_URL="jdbc:postgresql://localhost:5432/uplaw";
    static final String DATABASE_USER="postgres";
    static final String DATABASE_PASSWORD="postgres";
    static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
    static final String PG_JDBC_DRIVER = "org.postgresql.Driver";
    final String SQLITE_URL_TEMPLATE = "jdbc:sqlite:%s";
     final String PG_URL_TEMPLATE = "jdbc:postgresql://10.0.0.104:5432/%s";
    final int SQLITE_CACHE_SIZE = 20000;
    static final String DOCUMENT_FIELDS = "body";
    //final String DOCUMENT_PROPERTY_FIELDS = "id,event_id,key,value_int,value_string,value_date_time,value_boolean,\"index\"";

    public void connect() {
        Connection c = null;
        try {
            Class.forName(PG_JDBC_DRIVER);
            c = DriverManager
                    .getConnection(DATABASE_URL,
                            DATABASE_USER, DATABASE_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }


    public static List<ObjectNode> getFormattedResult(ResultSet rs) {
        List<ObjectNode> resList = new ArrayList<ObjectNode>();
        try {
            // get column names
            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCnt = rsMeta.getColumnCount();
            List<String> columnNames = new ArrayList<String>();
            for(int i=1;i<=columnCnt;i++) {
                columnNames.add(rsMeta.getColumnName(i).toUpperCase());
            }

            while(rs.next()) { // convert each object to an human readable JSON object
                ObjectNode obj = JsonNodeFactory.instance.objectNode();
                for(int i=1;i<=columnCnt;i++) {
                    String key = columnNames.get(i - 1);
                    String value = rs.getString(i);
                    obj.put(key, value);
                }
                resList.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }

    public static void createTable(String sql) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName(PG_JDBC_DRIVER);
            c = DriverManager
                    .getConnection(DATABASE_URL,
                            DATABASE_USER, DATABASE_PASSWORD);


            stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }


	public static ResultSet readTable() {
		Connection c = null;
		String SQL = "SELECT id,pid,type,body "
				+ "FROM document";
		try {
			Class.forName(PG_JDBC_DRIVER);
			c = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
			PreparedStatement pstmt = c.prepareStatement(SQL);
//			pstmt.setInt(1, 39);
			ResultSet rs = pstmt.executeQuery();
            return rs;
			*/
/*while (rs.next()) {
				//System.out.println(rs.getString("body") + "\t");
				ObjectMapper mapper = new ObjectMapper();

				JsonNode bodyNode = mapper.readTree(rs.getString("body"));
				System.out.println(bodyNode.get("title").asText());
				ArrayNode answers = (ArrayNode) bodyNode.get("answers");
				System.out.println(answers.get(0));

			}
*//*

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

        return null;
    }

		public static void insert(List<ObjectNode> jsonObjects) throws SQLException {
        createTable(sqlCommands.documentTable());
        Connection c =null;
        try {
            Class.forName(PG_JDBC_DRIVER);
            c = DriverManager
                    .getConnection(DATABASE_URL,
                            DATABASE_USER, DATABASE_PASSWORD);
            for(ObjectNode obj: jsonObjects){
                String sql = "INSERT INTO document("+DOCUMENT_FIELDS+") VALUES(?,?,?)";

                PreparedStatement pstmt = c.prepareStatement(sql);
                PGobject jsonObject = new PGobject();
                jsonObject.setType("json");
                System.out.println(jsonObject);
                jsonObject.setValue(obj.get("body").toString());
                pstmt.setString(1, obj.get("pid").textValue());
                pstmt.setObject(2,jsonObject);
                pstmt.setString(3, String.valueOf(obj.get("type").textValue()));
                try {
                    pstmt.executeUpdate();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

    }

    public static void insertRow(ObjectNode objectNode) throws SQLException {
        String sql = "INSERT INTO document("+DOCUMENT_FIELDS+") VALUES(?)";        Connection c =null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(DATABASE_URL,
                            DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement pstmt = c.prepareStatement(sql);
            PGobject jsonPGObject = new PGobject();
            jsonPGObject.setType("json");
            jsonPGObject.setValue(objectNode.get("body").toString());
            pstmt.setObject(1,jsonPGObject);
            pstmt.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        }




    public void insertRow(String sql) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(DATABASE_URL,
                            DATABASE_USER, DATABASE_PASSWORD);
            c.setAutoCommit(false);

            stmt = c.createStatement();

            stmt.executeUpdate(sql);

            //sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    //+ "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
           // stmt.executeUpdate(sql);


            //stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
    public void select(String sql) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(DATABASE_URL,
                            DATABASE_USER, DATABASE_PASSWORD);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "ADDRESS = " + address );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public void delete(String sql) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(DATABASE_URL,
                            DATABASE_USER, DATABASE_PASSWORD);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            sql = "DELETE from COMPANY where ID = 2;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "ADDRESS = " + address );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public void main(String sql) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/testdb",
                            "manisha", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "ADDRESS = " + address );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

}
*/
