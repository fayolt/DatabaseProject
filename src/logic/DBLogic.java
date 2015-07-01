package logic;

import java.io.IOException;
import java.util.List;

import util.AppException;
import dao.DBDao;
import entity.Database;

public class DBLogic {
	
	public static boolean createDatabase(Database db) throws ClassNotFoundException, AppException, IOException
	{
		
		DBDao dbDao = new DBDao();
		boolean createRes = true;
		
		if (dbDao.isValidFile("init.db")) //public boolean IsValidFile(String filepath)
		{
			if (!dbDao.dbExist(db))
			{
				return dbDao.create(db.getDBName(), db, createRes); //create new database if doesn't exist
			} 		
			else
			{
				return false; //fail to create new database
			}
			
		}
		else
		{//when app is launched for the first time
			if (dbDao.createDBFile("init.db"))//create init.db file
			{
				return dbDao.create(db.getDBName(), db, createRes); //create first database 
			}
			else
			{
				return false; //fail to initialize our app
			}
		}

	}


	public static boolean getDatabase(String db_name, Database db) throws ClassNotFoundException, AppException, IOException
	{
		DBDao dbDao = new DBDao();
		return  dbDao.getDatabase(db_name, db);
	}
	
	public static List<String> getDBList() throws ClassNotFoundException, AppException, IOException
	{
		return DBDao.getDBList();
	}
	
}
