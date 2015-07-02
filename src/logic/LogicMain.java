package logic;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import util.AppException;
import entity.*;


public class LogicMain {
	

	// LogicMain.java : implementation of the LogicMain class
	//
	
	/*CRKDBMSView* displayView;
	CDBView* dbTreeView;
	CTableView* tableView;*/
	String rootDir;//program's root directory
	String strError;
	private String databaseName;
	String tableName;
	Database dbEntity;//CDBEntity object
	Table tbEntity;
	List<Table> tbArray = null;
	List<Record> recArray;
	
	static LogicMain document = null;


	// Logicmain construction/destruction

	public static LogicMain getDocument() {
		if(document==null)
			document = new LogicMain();
		return document;
	}


	LogicMain()
	{

	}


	/*boolean OnNewDocument()
	{
		if (!CDocument::OnNewDocument())
			return FALSE;

		// TODO: add reinitialization code here
		// (SDI documents will reuse this document)
		SetTitle(L"no database");
		TCHAR currentDir[MAX_PATH];
		GetCurrentDirectory(MAX_PATH, currentDir);
		m_pRootDir = currentDir;
		m_dbEntity = new CDBEntity(L"Ruanko");
		try
		{
			bool createDBRes = CDBLogic::CreateDatabase(*m_dbEntity);
		}
		catch (CAppException* e)
		{
			m_strError = e->getErrorMessage();
			delete e;
		}

		return TRUE;
	}*/


	public String getError()
	{
		return strError;
	}

	public void setError(String error)
	{
		strError = error;
	}


	public String getDatabaseName()
	{
		return databaseName;
	}

	public List<Table> getTbArray() 
	{
		return tbArray;
	}


	public void setTbArray(List<Table> tbArray) 
	{
		this.tbArray = tbArray;
	}


	public void setDatabaseName(String m_nDatabaseName) 
	{
		databaseName = m_nDatabaseName;
	}


	@SuppressWarnings("unused")
	public void createDatabase()
	{

		dbEntity = new Database(getDatabaseName());
		Path directory = Paths.get("");
		String currentDir = directory.toAbsolutePath().toString();
		rootDir = currentDir;
		try
		{
			//SetCurrentDirectory(m_pRootDir);
			dbEntity.setDBFilePath(currentDir+"\\data\\"+dbEntity.getDBName());
			try
			{
				boolean createDBRes = DBLogic.createDatabase(dbEntity);
			
			} catch (ClassNotFoundException | IOException e) {
				
				e.printStackTrace();
			}
		}
		catch (AppException e)
		{
			strError = e.getErrorMessage();
			e = null;
			//SetCurrentDirectory(currentDir);
		}

		

	}

	public List<String> getDBList()
	{
		List<String> dbList = null;
		try 
		{
			dbList = DBLogic.getDBList();
		} catch (ClassNotFoundException | IOException | AppException  e) {
			if(e instanceof AppException)
				strError = ((AppException) e).getErrorMessage();
			e.printStackTrace();
		}
		return dbList;
	}
	
	public void getDatabase()
	{
		dbEntity = new Database();
//		Path directory = Paths.get("");
//		String currentDir = directory.toAbsolutePath().toString();
		try
		{
			try 
			{
				DBLogic.getDatabase(getDatabaseName(), dbEntity);
			
			} catch (ClassNotFoundException | IOException e) {
				
				e.printStackTrace();
			}
		}
		catch (AppException e)
		{
			strError = e.getErrorMessage();
			e = null;
		}
		
	}

	public Table createTable(String tableName)
	{
		Table pTable = new Table(tableName);
		
		pTable.setTdfPath(dbEntity.getDBFilePath() + "\\" + tableName + ".tdf"); ;//set table definition file path
		pTable.setTrdPath(dbEntity.getDBFilePath() + "\\" + tableName + ".trd");//set records file path;
		pTable.setTicPath(dbEntity.getDBFilePath() + "\\" + tableName + ".tic");//set integrity description file path;
		pTable.setTidPath(dbEntity.getDBFilePath() + "\\" + tableName + ".tid");//set index description file path;
		
		boolean res = true;
		try
		{
			try 
			{
				res = TableLogic.createTable(dbEntity, pTable);
			
			} catch (ClassNotFoundException | IOException e) {
			
				e.printStackTrace();
			}
		}
		catch (AppException e)
		{
			strError = e.getErrorMessage();
			e = null;
		}

		if (res==true)
		{
			if(tbArray == null)
			{
				tbArray = new ArrayList<Table>();
			}
			
			tbArray.add(pTable);
		}
		else
		{
			pTable = null;
		}


		return pTable;
	}

	public void setEditTable(String tb_name)
	{
		for (int i = 0; i < tbArray.size(); i++)
		{
			if (tbArray.get(i).getTableName() == tb_name)
			{
				tbEntity = tbArray.get(i);
				break;
			}
		}
	}

	public Field addField(Field field)
	{
		boolean res = false;
		try
		{
			res = TableLogic.addField(dbEntity.getDBFilePath(), tbEntity, field);
		}
		catch (AppException | IOException e)
		{
			if(e instanceof AppException)
				strError = ((AppException) e).getErrorMessage();
				
			e.printStackTrace();
		}

		if ( res == true)
		{
			if(tbEntity.fieldArray == null)
			{
				tbEntity.fieldArray = new ArrayList<Field>();
			}
				
			tbEntity.fieldArray.add(field);
		}
		else
		{
			field = null;
		}


		return field;
	}

	public void loadTables()
	{
		if(tbArray == null)
			tbArray = new ArrayList<Table>();
			
		else
			tbArray.clear();
//		Table pTable = new Table();
		try
		{
			 TableLogic.getTables(dbEntity, tbArray);
		}
		catch (AppException e)
		{
			strError = e.getErrorMessage();
			e = null;
		}
		System.out.println("we are here");
	}

	void insertRecord(Record re)
	{
		RecordLogic m_LogicRec = null;
		m_LogicRec.insert(tbEntity , re);
	}

	void loadRecord()
	{
		recArray.clear();
		RecordLogic recLogic = null;
		try 
		{
			recLogic.SelectAll(tbEntity, recArray);
		
		} catch (AppException e) 
		{
			
			strError = e.getErrorMessage();
			e = null;
		}
	}
	
	
	
}
