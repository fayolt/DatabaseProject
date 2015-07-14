package logic;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import util.AppException;
import dao.TableDao;
import entity.Database;
import entity.Field;
import entity.Table;

public class TableLogic 
{
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
	
	public static boolean addField(String path, Table tb, Field fd) throws AppException, IOException
	{
		TableDao tbDao = new TableDao();
		boolean res = true;
		
		String filepath;
		filepath = tb.getTdfPath();
		if (tbDao.isValidFile(filepath))
		{
			res = tbDao.addField(filepath, fd);
		}
		else
		{
			if (tbDao.createTbFile(filepath))
			{
				res = tbDao.addField(filepath, fd);
			}
			else
			{
				res = false;
			}
		}
		return res;
	}
	
	public static int getTables(Database db, List<Table> tbArray) throws AppException
	{								
		TableDao tableDao = new TableDao();
		String filepath;
		filepath = db.getDBFilePath() + "\\" +db.getDBName() + ".tb";
		return tableDao.getTables(filepath, tbArray);
	}

	public static boolean update(Table tb, Vector data)
	{
		TableDao tableDao = new TableDao();
		String filepath;
		filepath = tb.getTdfPath();
		
		boolean res = true;
		if (tableDao.isValidFile(filepath))
		{
			res = tableDao.update(tb, data);
		}
		else
		{
			new JOptionPane("the selected table has no fields! update aborted!");
			return false;
		}
		//int nRecordNum = te.getRecordNum()+1;
		//te.setRecordNum(nRecordNum);
	
		//String strTableFile = 
		return res;
	}

}
