package com.solrj.impl.solrJImpl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;

public class RealTimeGet {

	public static void main(String[] args) throws SolrServerException, IOException {
		
		HttpSolrClient client = new HttpSolrClient("http://localhost:8983/solr/nestedDoc");
		 UpdateRequest upr=new UpdateRequest();
		 upr.deleteByQuery("id: \"456:54\" OR _root_:\"456:54\"");
		//SolrDocument sdoc = client.getById("id0");
		//System.out.println( sdoc);
		 System.out.println(upr.getXML());
		// System.out.println(upr.);
		 
		 UpdateResponse rp= upr.process(client);
		 client.commit();
		 
		 System.out.println(rp.getRequestUrl()+ rp.getStatus());
		client.close(); // shut down the client when we are done
	}

}
