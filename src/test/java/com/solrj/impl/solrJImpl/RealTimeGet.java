package com.solrj.impl.solrJImpl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;

public class RealTimeGet {

	public static void main(String[] args) throws SolrServerException, IOException {
		
		HttpSolrClient client = new HttpSolrClient("http://192.168.99.100:8983/solr/demo");
		 
		SolrDocument sdoc = client.getById("id0");
		System.out.println( sdoc);
		 
		client.close(); // shut down the client when we are done
	}

}
