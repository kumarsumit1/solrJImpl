package com.solrj.impl.solrJImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) 
    {
      String str="{\"searchTerm\":\"\\\"active\\\"\",\"contactId\":\"400603051\",\"marketCode\":\"006\",\"corpId\":\"418409~700340\",\"userType\":\"E\"}"   ;
      System.out.println(str);
		
		
    }
    
private static String constructCardStatus(Item cardSummary,String member)  {
		
	//	if( LOGGER.isDebugEnabled() )
	//	LOGGER.debug(" PopulateCardStatus method called for  " + member + " and value " + value);
	Object retValue=null;
		try {			
			//Class cls = Class.forName("com.aexp.ims.atworks.beans.CardSummary");
			Class cls = Class.forName(cardSummary.getClass().getCanonicalName());
			Method method = cls.getDeclaredMethod(member);
			retValue=method.invoke(cardSummary);
		//	if( LOGGER.isDebugEnabled() )
		//	LOGGER.debug("PopulateCardStatus method ends ");

		} catch (ClassNotFoundException |  NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException  e) {
		//	LOGGER.info("Exception occured in populateCardStatus()  " , e);
		}
		return (String) retValue;
	}  
    
}
