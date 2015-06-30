package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AppException;
import dao.TableDao;
import entity.FieldEntity;
import entity.TableEntity;

public class TableLogic {

	public static boolean CreateTable(String db_name, TableEntity te) throws AppException, ClassNotFoundException, IOException
	{
		TableDao newTable = new TableDao() ;
	
		String filepath;
		filepath = db_name + ".tb";
		if (newTable.IsValidFile(filepath))
		{
			if (!newTable.TbExist(filepath,te))
			{
				return newTable.Create(filepath, te);
			}
			else
			{
				return false;
			}
			
		}
		else
		{
			if (newTable.CreateTbFile(filepath))
			{
				return newTable.Create(filepath, te);
			}
			else
			{
				return false;
			}
		}
		
	}
	
	public static boolean AddField(String db_name, TableEntity te, FieldEntity fe) throws AppException
	{
		TableDao newTable = new TableDao();
		boolean res = true;
		
		String filepath;
		filepath = te.getTableName() + ".tdf";
		if (newTable.IsValidFile(filepath))
		{
			res = newTable.AddField(filepath, fe);
		}
		else
		{
			if (newTable.CreateTbFile(filepath))
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
	
	static int GetTables(String db_name, List<TableEntity> tbArray) throws AppException
	{								
		TableDao tableDao = new TableDao();
		String filepath;
		filepath = db_name + ".tb";
		int n_count = tableDao.GetTables(filepath, tbArray);
		
		return n_count;
	}
}
