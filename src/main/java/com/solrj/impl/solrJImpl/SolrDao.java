package com.solrj.impl.solrJImpl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
 

 
public class SolrDao <T>
{
 
	HttpSolrClient server = null;
    
    public SolrDao (String solrURL)
    {
        server = (HttpSolrClient) SolrServerFactory.getInstance().createServer(solrURL);
        configureSolr (server);
    }
    
    public void put (T dao)
    {
        put (createSingletonSet (dao));
    }
    
    public void put (Collection<T> dao)
    {
        try 
        {
            UpdateResponse rsp = server.addBeans (dao);
            System.out.println ("Added documents to solr. Time taken = " + rsp.getElapsedTime() + ". " + rsp.toString());
        }
        catch (SolrServerException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void putDoc (SolrInputDocument doc)
    {
        putDoc (createSingletonSet(doc));
    }
    
    public void putDoc (Collection<SolrInputDocument> docs)
    {
        try 
        {
            long startTime = System.currentTimeMillis();
            UpdateRequest req = new UpdateRequest();
            req.setAction( UpdateRequest.ACTION.COMMIT, false, false );
            req.add (docs);
            UpdateResponse rsp = req.process( server );
            System.out.print ("Added documents to solr. Time taken = " + rsp.getElapsedTime() + ". " + rsp.toString());
            long endTime = System.currentTimeMillis();
            System.out.println (" , time-taken=" + ((double)(endTime-startTime))/1000.00 + " seconds");
        }
        catch (SolrServerException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public QueryResponse readAll () 
    {
        SolrQuery query = new SolrQuery();
        query.setQuery( "*:*" );
        //query.addSortField( "price", SolrQuery.ORDER.asc );
        QueryResponse rsp = null;
        try 
        {
            rsp = server.query( query );
        }
        catch (SolrServerException |IOException e) 
        {
            e.printStackTrace();
        }
        return rsp;
    }
    
    public SolrDocumentList readAllDocs ()
    {
        SolrQuery query = new SolrQuery();
        
        query.setFields("id","name_en_fr","name_fr_s","price_f");
        
        query.setQuery( "*:*" );
        query.addFilterQuery("price_f:5");
        query.addFilterQuery("id:id5*");
        query.setStart(3);
        query.setRows(17);
        query.addSort( "id", ORDER.asc );
        query.setFacet(true);
        query.addFacetField("price_f");
        query.addFacetQuery("id: [* TO -1] ");
        query.addFacetQuery("id: [id0 TO id499]");
        query.addFacetQuery("id: [id500 TO *] ");
        query.addNumericRangeFacet("price_f", 0, 9, 5);
        String intervalSet[]={"[0,4]","[5,9)"};
        query.addIntervalFacets("price_f", intervalSet);
      // query.setShowDebugInfo(true);
        QueryResponse rsp = null;
        try {
            rsp = server.query( query );
        } catch (SolrServerException | IOException e ) 
        {
            e.printStackTrace();
            return null;
        }
        System.out.println(rsp);
        SolrDocumentList docs = rsp.getResults();
        //System.out.println(docs);
        List<Item> itemList=rsp.getBeans(Item.class);
        for(Item it: itemList){
        	System.out.println(it.getId());
        	System.out.println(it.getName_en());
        	System.out.println(it.getName_fr());
        	System.out.println(it.getPrice());
         }
        return docs;
    }
    
    private void configureSolr(HttpSolrClient server) 
    {
        server.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
        server.setConnectionTimeout(5000); // 5 seconds to establish TCP
        // The following settings are provided here for completeness.
        // They will not normally be required, and should only be used 
        // after consulting javadocs to know whether they are truly required.
        server.setSoTimeout(1000);  // socket read timeout
        server.setDefaultMaxConnectionsPerHost(100);
        server.setMaxTotalConnections(100);
        server.setFollowRedirects(false);  // defaults to false
        // allowCompression defaults to false.
        // Server side must support gzip or deflate for this to have any effect.
        server.setAllowCompression(false);
    }
    
    private <U> Collection<U> createSingletonSet(U dao) 
    {
        if (dao == null)
            return Collections.emptySet();
        return Collections.singleton(dao);
    }

	public List<Item> fetchQuery() {
		  SolrQuery query = new SolrQuery();
	        
	        query.setFields("id","name_en_s","name_fr_s");
	      //  query.setFields("price_f");
	        query.addField("price_f");
	        
	        query.setQuery( "id:id0 OR name_en_s:doc_en6" );	   
	      //  query.setQuery( "name_en_s:doc_en6" );	 
	        query.setShowDebugInfo(true);
	        QueryResponse rsp = null;
	        try {
	            rsp = server.query( query );
	        } catch (SolrServerException | IOException e ) 
	        {
	            e.printStackTrace();
	            return null;
	        }
	        System.out.println(rsp);
	        //SolrDocumentList docs = rsp.getResults();
	        //System.out.println(docs);
	        List<Item> itemList=rsp.getBeans(Item.class);
	        return itemList;
		
	}
 
}