package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Record {

	private HashMap<String,Object> m_mapdata = new HashMap<String, Object>();
	
	public Record()
	{
		
	}
	public Record(Record entity){
		Set set = entity.m_mapdata.entrySet();
		Iterator i = set.iterator();
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			m_mapdata.put((String)me.getKey(), me.getValue());
		}
	}
	
	public void Put(String strKey, Object strValue){
		m_mapdata.put(strKey, strValue);
	}
	/*
	public void Put(String strKey, int nValue);
	void Put(CString strKey, double dbValue);
	void Put(CString strKey, SYSTEMTIME t);
	*/
	public String Get(String strKey){
		String strFieldName =(String) m_mapdata.get(strKey);
		return strFieldName;
	}

}
