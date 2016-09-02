package com.solrj.impl.solrJImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class BlockJoin {
	static final String SOLR_URL = "http://localhost:8983/solr/nestedDoc";

	public static void main(String[] args) throws Exception {
	
		HttpSolrClient client = new HttpSolrClient(SOLR_URL);
		/*Collection<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();
		SolrInputDocument perm = new SolrInputDocument();
		perm.addField("id", "bca1:ctcId1");
		perm.addField("bca_t", "bca1");
		perm.addField("ctcId_t", "ctcId1");
		perm.addField("mca_t", "mca1");

		// child docs
		SolrInputDocument cbca1 = new SolrInputDocument();
		cbca1.addField("id", "cbca1:ctcId1");
		cbca1.addField("cbca_db_name_t", "bca1.global");
		cbca1.addField("cbca_name_t", "bca's name");

		SolrInputDocument cmca1 = new SolrInputDocument();
		cmca1.addField("id", "cmca1:ctcId1");
		cmca1.addField("cmca_db_name_t", "cmca1.global");
		cmca1.addField("cmca_name_t", "mca's name");

		SolrInputDocument cica1 = new SolrInputDocument();
		cica1.addField("id", "cica1:ctcId1");
		cica1.addField("cica_db_name_t", "cica1.global");
		cica1.addField("cica_name_t", "ica1's name");

		SolrInputDocument cica2 = new SolrInputDocument();
		cica2.addField("id", "cica2:ctcId1");
		cica2.addField("cica_db_name_t", "cica2.global");
		cica2.addField("cica_name_t", "ica2's name");

		SolrInputDocument cica3 = new SolrInputDocument();
		cica3.addField("id", "cica3:ctcId1");
		cica3.addField("cica_db_name_t", "cica3.global");
		cica3.addField("cica_name_t", "ica3's name");

		perm.addChildDocument(cbca1);

		perm.addChildDocument(cmca1);

		perm.addChildDocument(cica1);
		perm.addChildDocument(cica2);
		perm.addChildDocument(cica3);

		batch.add(perm);
		client.add(batch);
		client.commit();*/
		
		
		List<Child> temp=new ArrayList<Child>();
		
		List<Child> temp1=new ArrayList<Child>();
		
		for(int i=0; i< 18; i++){
			Child chi1=new Child();
			chi1.setId("678"+i);
			chi1.setName("chi1"+i);
			chi1.setCategory("C");
			temp.add(chi1);
		}
		
/*		Child chi1=new Child();
		chi1.setId("678");
		chi1.setName("chi1");
		chi1.setCategory("C");
		Child chi2=new Child();
		chi2.setId("6783");
		chi2.setName("chi2");
		chi2.setCategory("C");
		temp.add(chi1);
		temp.add(chi2);*/
		
		Item ite=new Item();
		ite.setId("123:123");
		ite.setName_en("check");
		ite.setName_fr("check_123");
		ite.setPrice(123f);
		ite.setCategory("P");
		ite.setChild(temp);
		
		//2nd doc
		Item ite1=new Item();
		ite1.setId("456:54");
		ite1.setName_en("check");
		ite1.setName_fr("check_456");
		ite1.setPrice(456f);
		ite1.setCategory("P");
		ite1.setChild(temp1);
		
		
		
		
		DocumentObjectBinder binder = new DocumentObjectBinder();
	    SolrInputDocument doc = binder.toSolrInputDocument(ite);
	    SolrInputDocument doc1 = binder.toSolrInputDocument(ite1);
	    SolrDocumentList docs = new SolrDocumentList();
	    docs.add(ClientUtils.toSolrDocument(doc));
	    Item out = binder.getBeans(Item.class, docs).get(0);
	    Item singleOut = binder.getBean(Item.class, ClientUtils.toSolrDocument(doc));
	    
	   // System.out.println(out.getName_en());
	    //System.out.println(out.getCat().get("one"));
	    
		
		
		
		UpdateRequest upr=new UpdateRequest();
		upr.add(doc);
		upr.add(doc1);
		org.apache.solr.client.solrj.response.UpdateResponse ups=upr.process(client);
		
	   client.commit();
		System.out.println(ups.getStatus());
		
		//Update docs :
		/*SolrInputDocument upPerm = new SolrInputDocument();
		upPerm.addField("id", "bca1:ctcId1");
		SolrInputDocument upcica3 = new SolrInputDocument();
		upcica3.addField("id", "cica3:ctcId1");
		upcica3.addField("cica_db_name_t", "cica3.global.updated");
		upcica3.addField("cica_name_t", "ica3's name updated");
		upPerm.addChildDocument(upcica3);
		client.add(upPerm);
		client.commit();*/
	
		//	All children of a parent document must be indexed together with the parent document. 
		//One cannot update any document (parent or child) individually. The entire block needs to be re-indexed of any changes need to be made.
		
		/*SolrInputDocument upPerm = new SolrInputDocument();
		upPerm.addField("id", "bca1:ctcId1");
		SolrInputDocument sdoc = new SolrInputDocument();
		sdoc.addField("id", "cica3:ctcId1");
		Map<String,Object> fieldModifier = new HashMap<>(1);
		fieldModifier.put("set","cica3.global.updated");
		sdoc.addField("cica_db_name_t", fieldModifier);  // add the map as the field value
		upPerm.addChildDocument(sdoc) ;
		client.add( upPerm );
		client.commit();*/
		/*
		 * https://dzone.com/articles/using-solr-49-new
		 * 
		 * [child] - ChildDocTransformerFactory

This transformer returns all descendant documents of each parent document matching your query in a flat list nested inside the matching parent document. This is useful when you have indexed nested child documents and want to retrieve the child documents for the relevant parent documents for any type of search query.
fl=id,[child parentFilter=doc_type:book childFilter=doc_type:chapter limit=100]

Note that this transformer can be used even though the query itself is not a Block Join query.

When using this transformer, the parentFilter parameter must be specified, and works the same as in all Block Join Queries, additional optional parameters are:

    childFilter - query to filter which child documents should be included, this can be particularly useful when you have multiple levels of hierarchical documents (default: all children)
    limit - the maximum number of child documents to be returned per parent document (default: 10)
		 * 
		 */
		
		
		doQuery(client, "fetch all child docs", "name_en_s:check", null, "*,[child parentFilter=category_t:P childFilter=category_t:C]", null);
		
	//	doQuery(client, "fetch all child docs", "*:*", "{!child of=\"marketCode_t:006\"}ctcId_t:126545", null, null);
		
	}

	static void doQuery(HttpSolrClient solr, String description,
			String queryStr, String optFilter, String optFields,
			Map<String, String> extraParams) throws Exception {
		// Setup Query
		SolrQuery q = new SolrQuery(queryStr);
		System.out.println();
		System.out.println("Test: " + description);
		System.out.println("\tSearch: " + queryStr);
		if (null != optFilter) {
			q.addFilterQuery(optFilter);
			System.out.println("\tFilter: " + optFilter);
		}
		if (null != optFields) {
			// Use setParam instead of addField
			q.setParam("fl", optFields); // childFilter=doc_type:chapter
											// limit=100
			System.out.println("\tFields: " + optFields);
		} else {
			q.addField("*"); // childFilter=doc_type:chapter limit=100
		}
		if (null != extraParams) {
			for (Entry<String, String> param : extraParams.entrySet()) {
				// Could use q.setParam which allows you to pass in multiple
				// strings
				q.set(param.getKey(), param.getValue());
				System.out.println("\tParam: " + param.getKey() + "="
						+ param.getValue());
			}
		}

		// Run and show results
		QueryResponse rsp = solr.query(q);
		List<Item> itemList =rsp.getBeans(Item.class);
		for(Item it:itemList){
			System.out.println(it.getId());
			System.out.println(it.getCategory());
			System.out.println(it.getName_en());
			System.out.println(it.getName_fr());
			System.out.println(it.getPrice());
			for(Child cd:it.getChild()){
				System.out.println("the child doc value :");
				System.out.println(cd.getCategory());
				System.out.println(cd.getId());
				System.out.println(cd.getName());
			}
		}
		SolrDocumentList docs = rsp.getResults();
		long numFound = docs.getNumFound();
		System.out.println("Matched: " + numFound);
		int docCounter = 0;
		for (SolrDocument doc : docs) {
			docCounter++;
			System.out.println("Doc # " + docCounter);
			for (Entry<String, Object> field : doc.entrySet()) {
				String name = field.getKey();
				Object value = field.getValue();
				System.out.println("\t" + name + "=" + value);
			}
			List<SolrDocument> childDocs = doc.getChildDocuments();
			// TODO: make this recursive, for grandchildren, etc.
			if (null != childDocs) {
				for (SolrDocument child : childDocs) {
					System.out.println("\tChild doc:");
					for (Entry<String, Object> field : child.entrySet()) {
						String name = field.getKey();
						Object value = field.getValue();
						System.out.println("\t\t" + name + "=" + value);
					}
				}
			}
		}
		System.out.println("Query URL:");
		// TODO: should check URL for existing trailing /, and allow for
		// different query handler
		System.out.println(SOLR_URL + "/select?" + q);
	}

}
