package logic;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import util.AppException;
import dao.RecordDao;
import entity.Field;
import entity.Record;
import entity.Table;


public class RecordLogic {

	public boolean IsValidFile(String filePath)
	{
		boolean res = false;
		File file = new File(filePath);
	
		if (file.exists())
		{
			res = true;
		}
		else
		{
			return res;
		}
		return res;
	}

	public boolean CreateTRDFile(String filePath)
	{
		boolean res;
		File file = new File(filePath);

		if (file.exists())
		{
			res = true;
		}
		
		else
		{
			res = false;
		}
	
		return res;
	}
	
	
	public boolean insert(Table te, Record re)
	{
		String filepath;
		filepath = te.getTableName() + ".trd" ;
		//filepath.Format(L"%s.trd", te.getTableName());
		RecordDao m_daoRecord = null;
		//CFileHelperTemplate fileHelper;
		boolean res = true;
		if (IsValidFile(filepath))
		{
			res = m_daoRecord.insert(te, re);
			/*if (fileHelper.RecordExist(filepath, te.getTableName()))
			{
				res = m_daoRecord.insert(te, re);
			}
			else
			{
				res = false;
			}*/
			
		}
		else
		{
			if (CreateTRDFile(filepath))
			{
				res = m_daoRecord.insert(te, re);
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
	
	public boolean SelectAll(Table te, List<Record> data) throws AppException
	{										//vector<CRecordEntity> &data
		RecordDao m_daoRec = null;
		//FINISH Record FIRST!
		m_daoRec.SelectAll(te, data);
		return true;
	}
	
}
