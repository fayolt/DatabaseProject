package entity;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


public class Database implements Serializable 
{

	private static final long serialVersionUID = 4509207785258874608L;
	private String m_strName;
	private boolean m_bType;
	private String m_strFilePath;
	private Date m_tCrTime;
	
	public Database()
	{
		
	}
	
	public Database(Database db)
	{
		m_strName = db.m_strName;
		m_bType = db.m_bType;
		m_strFilePath = db.m_strFilePath;
		m_tCrTime = db.m_tCrTime;
	}
	
	public Database(String db_name)
	{
		m_strName = db_name;
		Path directory = Paths.get("");
		String currentDir = directory.toAbsolutePath().toString();
		m_strFilePath = currentDir + "\\data\\" + db_name + "\\"; //set database path
		m_bType = true;
		long millis = System.currentTimeMillis();
		m_tCrTime = new Date(millis);
	}
	
	public String getDBName()
	{
		return this.m_strName;
	}
	
	public boolean getDBType()
	{
		return this.m_bType;
	}
	
	public String getDBFilePath()
	{
		return this.m_strFilePath;
	}
	
	public Date getDBCreationTime()
	{
		return this.m_tCrTime;
	}
	
	public void setDBName(String db_name)
	{
		this.m_strName = db_name;
	}
	
	public void setDBType(boolean db_type)
	{
		this.m_bType = db_type;
	}
	
	public void setDBFilePath(String db_filepath)
	{
		this.m_strFilePath = db_filepath;
	}
	
	public void setDBCreationTime(Date db_ctime)
	{
		this.m_tCrTime = db_ctime;
	}

}
