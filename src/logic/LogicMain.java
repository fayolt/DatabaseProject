package logic;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import util.AppException;
import entity.*;


public class LogicMain 
{
	// LogicMain.java : implementation of the LogicMain class
	String rootDir;//program's root directory
	String strError;
	private String databaseName;
	String tableName;
	Database dbEntity;//Current database object
	Table tbEntity;
	List<Table> tbArray = null;
	List<Record> recArray;

	static LogicMain document = null;
	// Logicmain construction
	
	LogicMain()
	{

	}
	
	public static LogicMain getDocument()
	{
		if(document==null)
			document = new LogicMain();
		return document;
	}

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
			if (tbArray.get(i).getTableName().equals(tb_name))
			{
				tbEntity = tbArray.get(i);
				break;
			}
//			} else {
//				System.out.println("BUG");
//			}
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
		try
		{
			 TableLogic.getTables(dbEntity, tbArray);
		}
		catch (AppException e)
		{
			strError = e.getErrorMessage();
			e = null;
		}
	}

	public void insertRecord(Record rec)
	{
		try 
		{
			RecordLogic.insert(tbEntity , rec);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void loadRecord() throws SecurityException, IOException
	{
		if(recArray == null)
			recArray = new ArrayList<Record>();
		else
			recArray.clear();
		try 
		{
			RecordLogic.selectAll(tbEntity, recArray);
		
		} catch (AppException e) 
		{
			
			strError = e.getErrorMessage();
			e = null;
		}
	}
	
	public Table getTbEntity()
	{
		return tbEntity;
	}
	
	public List<Record> getRecArray() 
	{
		return recArray;
	}
	
	public void setRecArray(List<Record> recArray) 
	{
		this.recArray = recArray;
	}
	
	public void updateRecord(Vector data)
	{
		RecordLogic.update(tbEntity, data);
	}
	
	/*public List<Record> query(Record rec)
	{
		if(recArray==null)
		{
			try
			{
				loadRecord();
			}catch(SecurityException | IOException e)
			{
				e.printStackTrace();
			}
		}	
		List<Record> result = new ArrayList<Record>();
		for(int i=0; i<recArray.size();i++)
		{
		
		}
		return result;
	}*/
	
	public List<Record> query(Record record, int totalfield)
	{
		if(this.recArray == null)
		{
			try 
			{
				loadRecord();
			} catch (SecurityException | IOException e) 
			{
				e.printStackTrace();
			}
		}

		List<Record> list = new ArrayList<Record>();
		int index = 0;

		for(int i = 0; i<(recArray.size()/totalfield)-1;i++){
			for(int j=0; j < totalfield;j++){
				if(i == 0){
					Record r = recArray.get(j);
					list.add(r);
					for(final String key : r.getM_mapdata().keySet()){
						if(record.getM_mapdata().containsKey(key)){
							if(record.getM_mapdata().get(key).equals(r.getM_mapdata().get(key))){
								index=1;
							}
						}
						if(j == totalfield-1 && index ==0){
							for(int h = j; h >=0; h--){
								list.remove(h);
							}
						}
						else if(j==totalfield-1 && index == 1){
							index =0;
						}
					}}
				else{
					Record r = recArray.get(j+(totalfield)*i);
					list.add(r);
					for(final String key : r.getM_mapdata().keySet()){
						if(record.getM_mapdata().containsKey(key)){
							if(record.getM_mapdata().get(key).equals(r.getM_mapdata().get(key))){
								index=1;
							}
						}
						if(j == totalfield-1 && index ==0){
							int k = list.size();
							for(int h = k; h > (k-totalfield);h--){
								list.remove(h-1);
							}
						}
						else if(j==totalfield-1 && index == 1){
							index =0;
						}
					}
				}
			}
		}
		return list;
	}
	
	public void updateTable(Vector data)
	{
		TableLogic.update(tbEntity, data);
	}
}
