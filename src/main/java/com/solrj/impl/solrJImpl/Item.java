package com.solrj.impl.solrJImpl;

import org.apache.solr.client.solrj.beans.Field;

public class Item 
{
    @Field("id")
    String id;
    @Field("name_en_s")
    String name_en;
    @Field("name_fr_s")
    String name_fr;
    @Field("price_f")
    Float price;
    
    
    public Item() {} 
    
    public Item(String id, String name_en,String name_fr, Float price)
    {
        super();
        this.id = id;
        this.name_en = name_en;
        this.name_fr = name_fr;
        this.price = price;
    }
    
    // Getter Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
   
    public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_fr() {
		return name_fr;
	}

	public void setName_fr(String name_fr) {
		this.name_fr = name_fr;
	}

	public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
}

