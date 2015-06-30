package entity;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TableEntity {

	private String m_strName;
	private int m_nFieldNum;
	private int m_nRecordNum;
	private String m_strTdfPath;
	private String m_strTrdPath;
	private String m_strTicPath;
	private String m_strTidPath;
	private Date m_tCrTime;
	private Date m_tMTime;
	
	public ArrayList<FieldEntity> fieldArray; //list of fields in a table
	public TableEntity(){
	}
	
	public TableEntity(String tb_name){
		m_strName = tb_name;
		m_nFieldNum = 0;
		m_nRecordNum = 0;
		Path current = Paths.get("");
		String currentDir = current.toAbsolutePath().toString();
		m_strTdfPath = currentDir + "\\" + tb_name + ".tdf";//set table definition file path
		m_strTrdPath = currentDir + "\\" + tb_name + ".trd";//set record file path;
		m_strTicPath = currentDir + "\\" + tb_name + ".tic";//set integrity description file path;
		m_strTidPath = currentDir + "\\" + tb_name + ".tid";//set index description file path;
		
	    m_tCrTime.setTime(System.currentTimeMillis());//set creation time
		m_tMTime.setTime(System.currentTimeMillis());//set last modification time to creation time

		fieldArray.clear();
	}
	public TableEntity(TableEntity tb){
		m_strName = tb.m_strName;
		m_strTdfPath = tb.m_strTdfPath;
		m_strTrdPath = tb.m_strTrdPath;
		m_tCrTime = tb.m_tCrTime;
		m_tMTime = tb.m_tMTime;
	}
	
	public String getTableName(){
		return this.m_strName;
	}
	public int getFieldNum(){
		return this.m_nFieldNum;
	}
	public int getRecordNum(){
		return this.m_nRecordNum; 
	}
	public String getTdfPath(){
		return this.m_strTdfPath; 
	}
	public String getTrdPath(){
		return this.m_strTrdPath; 
	}
	public String getTicPath(){
		return this.m_strTicPath; 
	}
	public String getTidPath(){
		return this.m_strTidPath; 
	}
	public Date getTableCreationTime(){
		return this.m_tCrTime;
	}
	
	public Date getTableMTime(){
		return this.m_tMTime;
	}
	public void setTableName(String tb_name){
		this.m_strName = tb_name;
	}
	public void setRecordNum(int rec_num){
		this.m_nRecordNum = rec_num;
	}
	public void setFieldNum(int field_num){
		this.m_nFieldNum = field_num;
	}
	public void setTdfPath(String tb_tdfPath){
		this.m_strTdfPath = tb_tdfPath;
	}
	public void setTrdPath(String tb_trdPath){
		this.m_strTrdPath = tb_trdPath;
	}
	public void setTicPath(String tb_ticPath){
		this.m_strTicPath = tb_ticPath;
	}
	public void setTidPath(String tb_tidPath){
		this.m_strTidPath = tb_tidPath;
	}
	public	void setTableCreationTime(Date tb_ctime){
		this.m_tCrTime = tb_ctime;
	}
	public void setTableMTime(Date tb_mtime){
		this.m_tMTime = tb_mtime;
	}

	//public void AddField(CFieldEntity&);
	//CFieldEntity* getField(CString);

}
