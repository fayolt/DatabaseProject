package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import util.AppException;
import util.FileHelper;
import entity.FieldEntity;
import entity.TableEntity;

public class TableDao {
	public boolean IsValidFile(String filePath){
		boolean res;
		File file = new File(filePath);

		if (file.exists())
		{
			res = true;
		}
		else
		{
			res = false;
			return res;
		}

		return res;
	}
	public boolean CreateTbFile(String filename){
		boolean res;
		File file = new File(filename);

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
	public boolean TbExist(String filePath, TableEntity te) throws AppException, IOException, ClassNotFoundException{
		boolean res = false;
		File file = new File(filePath);
		FileNotFoundException fileException;
		if (file.exists())
		{
			TableEntity tbInfo = null;
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			ArrayList<Object> list = new ArrayList();
			list = (ArrayList) objectIn.readObject();
			for (int i = 0; i<list.size();i++)
			{
				String str = "";
				tbInfo =(TableEntity) list.get(i);
				str = tbInfo.getTableName();
				if (str == te.getTableName())
				{
					res = true;
					throw new AppException("table already exists!");
				}
			}
			fileIn.close();
			objectIn.close();
		}
		else
		{
			//get description for fileException 
			String strErrorMsg = "Can't open file " + filePath + " , error : " /*+ fileException.getLocalizedMessage()*/;
			throw new AppException(strErrorMsg);
		}
		return res;
	}
	/*
	 * CFile Helper details
	 * afxmessagebox equivalent in java
	 * GetField function for?
	 * */
	public boolean Create(String filePath, TableEntity te)
	{
		return FileHelper.WriteFile(filePath, te);
	}
	
	public boolean AddField(String filePath, FieldEntity fe)
	{
		return FileHelper.WriteFile(filePath, fe);
	}
	
	public boolean GetFields(String filePath, TableEntity te) throws AppException{
		boolean res = false;
		File file = new File(filePath);
		if (!(res = IsValidFile(filePath)))
		{
			JOptionPane.showMessageDialog(null, "Table with no fields, Add some fields!");
			return res;
		}

		ArrayList<FieldEntity> array =  FileHelper.ReadFile(filePath);
		for(int i = 0; i<array.size();i++){
			te.fieldArray.add(array.get(i));
		}
		if(te.fieldArray!=null){
			res=true;
		}
		else
		{
			throw new AppException(("File Operation failed! Fields information cannot be read!"));
			
		}

		return res;
	}
	
	public int GetTables(String filePath, List<TableEntity> tbArray) throws AppException{
		int index = 0;
		if (!IsValidFile(filePath))
		{
			
			JOptionPane.showMessageDialog(null, "The selected database has no tables, Add some tables!");
			return 0;
		} 
		ArrayList<TableEntity> array = FileHelper.ReadFile(filePath);
		for(int i = 0; i<array.size();i++){
			tbArray.add(array.get(i));
			GetFields(array.get(i).getTdfPath(), tbArray.get(i));
		}
		if(tbArray!=null){
			index = tbArray.size();
		}
		else
		{
			throw new AppException(("File Operation failed! Tables information cannot be read!"));
			
		}
		
		return index;
	}
}
