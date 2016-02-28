package com.solrj.impl.solrJImpl;

import java.util.List;

public class SolrExampleTests {
	  
	public static void main (String ... args ){
		String solrURL = "http://192.168.99.100:8983/solr/demo";
		SolrDao<Item> solrDao = new SolrDao<Item> (solrURL);
		List<Item> itemList=solrDao.fetchQuery();
		
		
        for(Item it: itemList){
        	System.out.println(it.getId());
        	System.out.println(it.getName_en());
        	System.out.println(it.getName_fr());
        	System.out.println(it.getPrice());
         }
	}
}