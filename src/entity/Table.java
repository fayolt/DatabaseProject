package entity;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Table implements Serializable {

	private String m_strName;
	private int m_nFieldNum;
	private int m_nRecordNum;
	private String m_strTdfPath;
	private String m_strTrdPath;
	private String m_strTicPath;
	private String m_strTidPath;
	private Date m_tCrTime;
	private Date m_tMTime;
	
	public List<Field> fieldArray; //list of fields in a table
	public Table()
	{
	}
	public Table(String tb_name)
	{
		m_strName = tb_name;
		m_nFieldNum = 0;
		m_nRecordNum = 0;
		Path current = Paths.get("");
		String currentDir = current.toAbsolutePath().toString();
		m_strTdfPath = currentDir + "\\" + tb_name + ".tdf";//set table definition file path
		m_strTrdPath = currentDir + "\\" + tb_name + ".trd";//set records file path;
		m_strTicPath = currentDir + "\\" + tb_name + ".tic";//set integrity description file path;
		m_strTidPath = currentDir + "\\" + tb_name + ".tid";//set index description file path;
		
		long millis = System.currentTimeMillis();
		
	    m_tCrTime = new Date(millis);//set creation time
		m_tMTime = this.getTableCreationTime();//set last modification time to creation time

		fieldArray = new ArrayList<Field>();
	}
	public Table(Table tb)
	{
		m_strName = tb.getTableName();
		m_nFieldNum = tb.getFieldNum();
		m_nRecordNum = tb.getRecordNum();
		m_strTdfPath = tb.getTdfPath();
		m_strTrdPath = tb.getTrdPath();
		m_strTicPath = tb.getTicPath();
		m_strTidPath = tb.getTidPath();
		m_tCrTime = tb.getTableCreationTime();
		m_tMTime = tb.getTableMTime();
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
	public List<Field> getFieldArray() {
		return fieldArray;
	}
	public void setFieldArray(List<Field> fieldArray) {
		this.fieldArray = fieldArray;
	}
	
	//public void AddField(CFieldEntity&);
	//CFieldEntity* getField(CString);

}
