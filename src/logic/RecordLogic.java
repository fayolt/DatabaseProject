package logic;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import util.AppException;
import dao.RecordDao;
import entity.Record;
import entity.Table;
import java.util.Vector;


public class RecordLogic 
{
	public static boolean insert(Table tb, Record rec) throws IOException
	{
		RecordDao recordDao = new RecordDao();
		String filepath;
		filepath = tb.getTrdPath();
		
		boolean res = true;
		if (recordDao.isValidFile(filepath))
		{
			res = recordDao.insert(tb, rec);
		}
		else
		{
			if (recordDao.createTRDFile(filepath))
			{
				res = recordDao.insert(tb, rec);
			}
			else
			{
				res = false;
			}
		}
		//int nRecordNum = te.getRecordNum()+1;
		//te.setRecordNum(nRecordNum);
	
		//String strTableFile = 
		return res;
	}
	
	public static boolean selectAll(Table te, List<Record> data) throws AppException, SecurityException, IOException
	{	
		RecordDao m_daoRec = new RecordDao();
		if(m_daoRec.selectAll(te, data)>0)
			return true;
		else
			return false;
	}
	
	public static boolean update(Table tb, Vector data)
	{
		RecordDao recordDao = new RecordDao();
		String filepath;
		filepath = tb.getTrdPath();
		
		boolean res = true;
		if (recordDao.isValidFile(filepath))
		{
			res = recordDao.update(tb, data);
		}
		else
		{
			new JOptionPane("the selected table has no records! update aborted!");
			return false;
		}
		//int nRecordNum = te.getRecordNum()+1;
		//te.setRecordNum(nRecordNum);
	
		//String strTableFile = 
		return res;
	}

}
