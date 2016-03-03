package com.solrj.impl.solrJImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

public class AtomicUpdate {

	public static void main(String[] args) throws SolrServerException, IOException {
		// create the SolrJ client
		HttpSolrClient client = new HttpSolrClient("http://192.168.99.100:8983/solr/");
		 
		// create the document
		SolrInputDocument sdoc = new SolrInputDocument();
		sdoc.addField("id","book1");
		Map<String,Object> fieldModifier = new HashMap<>(1);
		fieldModifier.put("add","Cyberpunk");
		sdoc.addField("cat", fieldModifier);  // add the map as the field value
		 
		client.add( sdoc );  // send it to the solr server
		 
		client.close();  // shutdown client before we exit

	}

}
