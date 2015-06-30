package entity;

import java.sql.Date;

public class FieldEntity {

	private String m_strName;
	//int m_nType;
	private String m_nType;
	private int m_nParam;
	private Date m_tMTime;
	private Date m_tCrTime;
	private int m_nIntegrities;
	private String defaultValue;

	public FieldEntity(){
		m_tCrTime.setTime(System.currentTimeMillis());
		m_tMTime.setTime(System.currentTimeMillis());
	}
	/*public FieldEntity(FieldEntity fe){
		m_tCrTime.setTime(System.currentTimeMillis());
		m_tMTime.setTime(System.currentTimeMillis());
	}*/
	public FieldEntity(String x, String y, int z){
		m_strName = x;
		m_nType = y;
		m_nIntegrities = z;
		m_tCrTime.setTime(System.currentTimeMillis());
		m_tMTime.setTime(System.currentTimeMillis());
	}
	public FieldEntity(FieldEntity fe){
		m_strName = fe.m_strName;
		m_nType = fe.m_nType;
		m_nParam = fe.m_nParam;
		m_nIntegrities = fe.m_nIntegrities;
		m_tMTime = fe.m_tMTime;
		m_tCrTime = fe.m_tCrTime;
		defaultValue = fe.defaultValue;
	}
	
	public String getFieldName(){
		return this.m_strName;
	}
	public String getFieldType(){
		return this.m_nType;
	}
	public String getDefaultValue(){
		return this.defaultValue;
	}
	public int getFieldParam(){
		return this.m_nParam;
	}
	public int getFieldIntegrities(){
		return this.m_nIntegrities;
	}
	public Date getFieldMTime(){
		return this.m_tMTime;
	}
	public Date getFieldCreationTime(){
		return this.m_tCrTime;
	}
	public void setFieldName(String f_name){
		this.m_strName = f_name;
	}
	public void setFieldType(String f_type){
		this.m_nType = f_type;
	}
	public void setDefaultValue(String de){
		this.defaultValue = de;
	}
	public void setFieldMTime(Date f_mtime){
		this.m_tMTime = f_mtime;
	}
	public void setFieldCreationTime(Date f_crtime){
		this.m_tCrTime = f_crtime;
	}
	public void setFieldParam(int f_param){
		this.m_nParam = f_param;
	}
	public void setFieldIntegrities(int f_integrity){
		this.m_nIntegrities = f_integrity;
	}
}
