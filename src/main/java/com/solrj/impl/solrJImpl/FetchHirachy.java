package com.solrj.impl.solrJImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class FetchHirachy {
public static void main(String[] args) throws Exception {
		
	HttpSolrClient client = new HttpSolrClient("http://localhost:8888/solr/permission_shard1_replica1");
		 SolrQuery query = new SolrQuery();
		// query.set("q", "*:*");
		 query.set("q", "{!parent which=\"category:P\"}"); 
		 query.set("fl", "*,[child parentFilter=\"category:P\" childFilter=\"category:C\"]");  
		 
	 
	//	 "{!parent which=$parent_filter v=$child_filter}",                  // filter
     //    "*,[child parentFilter=$parent_filter childFilter=$child_filter]", // fields
	    // query.setQuery("*:*");
		// query.setFields("*","[child parentFilter=category:P childFilter=category:C]");
	        
	     //   query.setQuery( "{!parent which =category:P }" );
	        query.addFilterQuery("ctcId:8754");
	        
	      //  query.addFilterQuery("id:id5*");
	       query.setStart(0);
	       query.setRows(20);
	       
	     //   query.setFacet(true);
	     //   query.addFacetField("price_f");
	     //   query.addFacetQuery("id: [* TO -1] ");
	     //   query.addFacetQuery("id: [id0 TO id499]");
	    //    query.addFacetQuery("id: [id500 TO *] ");
	   //     query.addNumericRangeFacet("price_f", 0, 9, 5);
	   //     String intervalSet[]={"[0,4]","[5,9)"};
	   //     query.addIntervalFacets("price_f", intervalSet);
	     //  query.setShowDebugInfo(true);
	        QueryResponse rsp = null;
	        try {
	        	System.out.println(query.getQuery());
	        	System.out.println(query.toString());
	            rsp = client.query( query );
	        } catch (Exception e ) 
	        {
	            e.printStackTrace();
	          
	        }
	        System.out.println(rsp);
	       
	        int docCounter = 0;
	        SolrDocumentList docs = rsp.getResults();
	        for (SolrDocument doc : docs) {
	            docCounter++;
	            System.out.println( "Doc # " + docCounter );
	            for ( Entry<String, Object> field : doc.entrySet() ) {
	                String name = field.getKey();
	                Object value = field.getValue();
	                System.out.println( "\t" + name + "=" + value );
	            }
	            List<SolrDocument> childDocs = doc.getChildDocuments();
	            // TODO: make this recursive, for grandchildren, etc.
	            if ( null!=childDocs ) {
	                for ( SolrDocument child : childDocs ) {
	                    System.out.println( "\tChild doc:" );
	                    for ( Entry<String, Object> field : child.entrySet() ) {
	                        String name = field.getKey();
	                        Object value = field.getValue();
	                        System.out.println( "\t\t" + name + "=" + value );
	                    }
	                }
	            }
	        }
	}
}
