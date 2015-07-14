package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

public class Record 
{
	private HashMap<String,Object> m_mapdata;
	private Vector<Object> valueArray = new Vector<Object>();

	public Record()
	{
		m_mapdata = new HashMap<String, Object>();
	}
	
	public Record(Record entity)
	{
		Set set = entity.m_mapdata.entrySet();
		Iterator i = set.iterator();
		while(i.hasNext())
		{
			Map.Entry me = (Map.Entry)i.next();
			m_mapdata.put((String)me.getKey(), me.getValue());
		}
	}

	public HashMap<String, Object> getM_mapdata() 
	{
		return m_mapdata;
	}
	
	public void put(String strKey, Object strValue)
	{
		m_mapdata.put(strKey, strValue);
	}
	
	public Object get(String strKey)
	{
		Object strValue = m_mapdata.get(strKey);
		return strValue;
	}
	
	public void testHashMap(List<Object> list) 
	{
		//		 m_mapdata = new HashMap<String, Object>();
		Set<Entry<String, Object>> set = this.m_mapdata.entrySet();
		Iterator<Entry<String, Object>> i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			//				m_mapdata.put((String)me.getKey(), me.getValue());
			String hashMapKey = (String)me.getKey();
			Object hashMapValue = me.getValue();
			System.out.println("The key is : " + hashMapKey + " and its value is " + hashMapValue);
			list.add(hashMapValue);
			
		}
		

	}
	
	public Vector<Object> getObjectTable() 
	{
		Set<Entry<String, Object>> set = this.m_mapdata.entrySet();
		Iterator<Entry<String, Object>> i = set.iterator();
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			//			m_mapdata.put((String)me.getKey(), me.getValue());
			/*
			 * String hashMapKey = (String)me.getKey();
			 */
//			if(set.size()%j ==)
			Object hashMapValue = me.getValue();
			//			System.out.println("The key is : " + hashMapKey + " and its value is " + hashMapValue);
			valueArray.add(hashMapValue);
			//			valueArray.
		}
		return valueArray;
	}

//	public Object getHashValue() 
//	{
//		return m_mapdata.get(key);
//	}
}
