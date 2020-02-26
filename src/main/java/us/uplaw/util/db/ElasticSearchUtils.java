package us.uplaw.util.db;
/*

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticSearchUtils {

  private static final String ELASTICSEARCH_INDEX="document";
  private static final String ELASTICSEARCH_DOCUMENT_TYPE="document";
  private static final String ELASTICSEARCH_HOST= utils.ConfigurationOptions.getElasticsearchHost(); //35.229.58.248 //35.229.58.248
  private static final int ELASTICSEARCH_PORT=9300;
  private static final int ELASTICSEARCH_SIZE= utils.ConfigurationOptions.getElasticSearchBulkIndexDocumentLimit();

  private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUtils.class);

  static TransportClient client =null;


  public ElasticSearchUtils() throws IOException {
    this.client=getTransportClient();
  }

  public static TransportClient getTransportClient()
          throws IOException {
    TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
            .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9200));
    return client;
  }

  public static SearchResponse getSearchResponseHitsFromElastic()
          throws IOException {
    TransportClient client=getTransportClient();
    MatchAllQueryBuilder query= QueryBuilders.matchAllQuery();
    SearchResponse response = client.prepareSearch(ELASTICSEARCH_INDEX)
            .setTypes(ELASTICSEARCH_DOCUMENT_TYPE)
            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
            //.setQuery(query)
            .setSize(ELASTICSEARCH_SIZE)
            .setQuery(QueryBuilders.matchAllQuery())                 // Query
            //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
            //.setFrom(0).setSize(60).setExplain(true)
            .get();
    client.close();
    System.out.println("\nTotal hits in search response: "+response.getHits().getHits().length);
    return response;
  }

  public static void bulkInsertJsonObjects(List<ObjectNode> jsonObjects)
          throws IOException {
    TransportClient client = getTransportClient();
    BulkRequestBuilder bulk = client.prepareBulk().setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
    for(ObjectNode jsonObject:jsonObjects){
      //String id = UUIDs.base64UUID();
      bulk.add(client.prepareIndex(ELASTICSEARCH_INDEX,ELASTICSEARCH_DOCUMENT_TYPE).setSource(jsonObject.toString(), XContentType.JSON));
    }
    BulkResponse bulkResponse = bulk.get();
    if (bulkResponse.hasFailures()) {
      logger.warn("Elasticsearch bulk insert failed.\n"+bulkResponse.buildFailureMessage());
    }
    else{
      logger.info("Elasticsearch bulk insert successful.");
    }
    client.close();
  }

  */
/*public static long delete( String indexName, String key, String value ) {
    BulkByScrollResponse response =
            DeleteByQueryAction.INSTANCE.newRequestBuilder( client )
                    .filter( QueryBuilders.matchQuery( key, value ) )
                    .source( indexName )
                    .refresh( true )
                    .get();

    logger.info( "Deleted " + response.getDeleted() + " element(s)!" );

    return response.getDeleted();
  }*//*

}
*/
