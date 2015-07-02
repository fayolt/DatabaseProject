package dao;

import java.io.File;
import java.sql.*;
import java.util.List;

import util.AppException;
import util.FileHelper;
import entity.Field;
import entity.Record;
import entity.Table;

/*
 * FINISH Record FIRST!
 * */

public class RecordDao 
{
	public boolean insert(Table te, Record re)
	{
		boolean res = true;
		String filepath;
		filepath = te.getTableName() + ".trd";
		File file = new File(filepath);
		if (file.exists())
		{

			for (int i = 0; i < te.fieldArray.size(); i++)
			{
				Field pField = te.fieldArray.get(i);
				String strFieldName = pField.getFieldName();
				String strValue = re.Get(strFieldName);
				char value;
				int intValue;
				boolean boolValue;
				Date tValue;
				FileHelper.writeFile(filepath, pField); // CONFIRM THIS!
			}
		}
		else
		{
			res = false;
		}

		return res;
	}
	/*
	 * Java can use sql data type by using package java.sql
	 * */
	public boolean Read(File file, Table te, Record re){
		boolean res = false;
		//		for (int i = 0; i < te.fieldArray.size(); i++)
		//		{
		//			Field pField = te.fieldArray.get(i);
		//			String strFieldName = pField.getFieldName();
		//			String strValue = re.Get(strFieldName);
		//			
		//			FileHelperTemplate fileHelper;
		//			if (pField.getFieldType() == "VARCHAR")
		//			{
		//				char value;
		//				if (file.Read(&value, sizeof(VARCHAR)))
		//				{
		//					String str = CharHelper.VarcharToCString(value);
		//					re.Put(strFieldName, str);
		//					res = true;
		//				}
		//				else
		//				{
		//					res = false;
		//				}
		//				
		//			}
		//			else if (pField.getFieldType() == "INTEGER")
		//			{
		//				int intValue;
		//				if (file.Read(intValue, sizeof(int)))
		//				{
		//					String str;
		//					str.Format(L"%d", intValue);
		//					re.Put(strFieldName, str);
		//					res = true;
		//				}
		//				else
		//				{
		//					res = false;
		//				}
		//				
		//			}
		//			else if (pField.getFieldType() == "BOOLEAN")
		//			{
		//				boolean boolValue;
		//				if (file.Read(&boolValue, sizeof(BOOLEAN)))
		//				{
		//					String str;
		//					if (boolValue)
		//					{
		//						str = "TRUE";
		//					}
		//					else
		//					{
		//						str = "FALSE";
		//					}
		//					re.Put(strFieldName, str);
		//					res = true;
		//				}
		//				else
		//				{
		//					res = false;
		//				}
		//			
		//			}
		//			else
		//			{
		//				DATETIME tValue;
		//				if (file.Read(&tValue, sizeof(DATETIME)))
		//				{
		//					String str;
		//					str.Format(_T("%u-%u-%u"),
		//						tValue.wYear, tValue.wMonth, tValue.wDay);
		//					re.Put(strFieldName, str);
		//					res = true;
		//				}
		//				else
		//				{
		//					res = false;
		//				}
		//				
		//			}
		//		}
		return res;
	}
	public int SelectAll(Table te, List<Record>data) throws AppException{
		String filepath;
		filepath = te.getTableName() + ".trd";
		File file = new File(filepath);
		if (file.exists())
		{

			while (true)
			{
				Record pRecEntity = new Record();
				if (Read(file, te, pRecEntity)==true)
				{
					data.add(pRecEntity);
				}
				else
				{
					pRecEntity = null;
					break;
				}
			}
		}
		else
		{
			throw new AppException("File Operation failed! Record information cannot be read!");
		}
		return 0;
	}
}
