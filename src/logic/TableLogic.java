package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AppException;
import dao.TableDao;
import entity.Database;
import entity.FieldEntity;
import entity.Table;

public class TableLogic {

	public static boolean createTable(Database db, Table tb) throws AppException, ClassNotFoundException, IOException
	{
		TableDao newTable = new TableDao() ;
	
		String filepath;
		filepath = db.getDBFilePath() + "\\" +db.getDBName() + ".tb";
		if (newTable.isValidFile(filepath))
		{
			if (!newTable.tbExist(filepath,tb))
			{
				return newTable.create(filepath, tb);
			}
			else
			{
				return false;
			}
			
		}
		else
		{
			if (newTable.createTbFile(filepath))
			{
				return newTable.create(filepath, tb);
			}
			else
			{
				return false;
			}
		}
		
	}
	
	public static boolean AddField(String db_name, Table te, FieldEntity fe) throws AppException, IOException
	{
		TableDao newTable = new TableDao();
		boolean res = true;
		
		String filepath;
		filepath = te.getTableName() + ".tdf";
		if (newTable.isValidFile(filepath))
		{
			res = newTable.AddField(filepath, fe);
		}
		else
		{
			if (newTable.createTbFile(filepath))
			{
				res = newTable.AddField(filepath, fe);
			}
			else
			{
				res = false;
			}
		}
		return res;
	}
	
	static int GetTables(String db_name, List<Table> tbArray) throws AppException
	{								
		TableDao tableDao = new TableDao();
		String filepath;
		filepath = db_name + ".tb";
		int n_count = tableDao.GetTables(filepath, tbArray);
		
		return n_count;
	}
}
