package us.uplaw.util.db;
/*

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;

*/
/*
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
*//*


public class SqliteUtils {


    private final String DATABASE_URL="jdbc:sqlite:/home/casey/projects/up/uplaw_frontend/app.db";

    private Connection connect() {
        // SQLite connection string
        String url = DATABASE_URL;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable(String sql) {
        String url = DATABASE_URL;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(String id,String absolute_url, String body, String date_created, String did, String resource_type, String resource_uri, String uri, String url) {
       String timestamp=String.valueOf(DateTime.now());
        String sql = "INSERT INTO document(id,absolute_url, body, date_created, did, resource_type,resource_uri,timestamp,uri,url) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, absolute_url);
            pstmt.setString(3, body);
            pstmt.setString(4, date_created);
            pstmt.setString(5, did);
            pstmt.setString(6, resource_type);
            pstmt.setString(7, resource_uri);
            pstmt.setString(8, timestamp);
            pstmt.setString(9, uri);
            pstmt.setString(10, url);


            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void selectAll(){
        String sql = "SELECT id,absolute_url,date_created,resource_uri,body FROM document";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("absolute_url")+"\t" +
                        rs.getString("did")+"\t" +
                        rs.getString("resource_uri")+"\t" +

                        rs.getString("body"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    */
/*
    public void insert(String body, String absolute_url, String date_created, String date_modified, String did, String resource_uri, String sha256) {

        String sql = "INSERT INTO document(body, absolute_url, date_created, date_modified, did, resource_uri, sha256) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, absolute_url);
            pstmt.setString(2, body);
            pstmt.setString(3, date_created);
            pstmt.setString(4, date_modified);
            pstmt.setString(5, did);
            pstmt.setString(6, resource_uri);
            pstmt.setString(7, sha256);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void selectAll(){
        String sql = "SELECT id,body, absolute_url, date_created, date_modified, did, resource_uri, sha256 FROM document";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("absolute_url")+"\t" +
                        rs.getString("date_created")+"\t" +
                        rs.getString("date_modified")+"\t" +
                        rs.getString("did")+"\t" +
                        rs.getString("resource_uri")+"\t" +
                        rs.getString("sha256")+"\t" +
                        rs.getString("body"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    *//*


    public void delete(int id) {
        String sql = "DELETE FROM warehouses WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute(String sql) {

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


   */
/* public SearchResponse getSearchResponseHitsFromElastic(String index, String type, String host, int port, int size) throws
            UnknownHostException {
        //Settings settings = Settings.builder()
        //.put("client.transport.sniff", true)
        //.put("client.transport.ignore_cluster_name",true).build();
		*//*
*/
/*TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));*//*
*/
/*
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
        MatchAllQueryBuilder query= QueryBuilders.matchAllQuery();
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                //.setQuery(query)
                .setSize(size)
                .setQuery(QueryBuilders.matchAllQuery())                 // Query
                //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                //.setFrom(0).setSize(60).setExplain(true)
                .get();
        client.close();

        System.out.println("\nTotal hits in search response: "+response.getHits().getHits().length);

        return response;
    }
*//*

    */
/*
    @parameters String index, String type, String host, int port, int size
    @returns Collection<Map<String, Object>>
     *//*

    public static String documentTable(){
        String documentPsql="-- auto-generated definition\n" + "create table document\n" + "(\n"
                + "  id        serial not null\n" + "    constraint document_pkey\n" + "    primary key,\n"
                + "  body      json,\n" + "  timestamp varchar(99) default CURRENT_TIME\n" + ");\n" + "\n"
                + "alter table document\n" + "  owner to postgres;\n" + "\n" + "create index ix_document_timestamp\n"
                + "  on document (timestamp);\n" + "\n";
        return documentPsql;
    }



}
*/
