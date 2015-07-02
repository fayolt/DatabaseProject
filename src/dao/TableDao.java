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
import entity.Database;
import entity.FieldEntity;
import entity.Table;

public class TableDao {
	public boolean isValidFile(String filePath){
		File file = new File(filePath);

		return file.exists();
	}
	public boolean createTbFile(String filename) throws IOException{
		File file = new File(filename);	
		return file.createNewFile();
	}
	@SuppressWarnings({ "unchecked", "resource" })
	public boolean tbExist(String filePath, Table tb) throws AppException, IOException, ClassNotFoundException{
		boolean res = false;
		File file = new File(filePath);
		if (file.exists())
		{
			Table tbInfo = null;
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			List<Table> list = new ArrayList<Table>();
			list = (List<Table>) objectIn.readObject();
			for (int i = 0; i<list.size();i++)
			{
				String str = "";
				tbInfo = (Table) list.get(i);
				str = tbInfo.getTableName();
				
				if (str.equalsIgnoreCase(tb.getTableName()))
				{
					res = true;
					System.out.println(res);
					throw new AppException(("Table already exist!"));
				}
			}
			fileIn.close();
			objectIn.close();
		}
		else
		{
			throw new AppException(("A file Operation failed!"));
		}
		

		return res;
	}
	/*
	 * CFile Helper details
	 * afxmessagebox equivalent in java
	 * GetField function for?
	 * */
	public boolean create(String filePath, Table tb)
	{
		return FileHelper.WriteFile(filePath, tb);
	}
	
	public boolean AddField(String filePath, FieldEntity fe)
	{
		return FileHelper.WriteFile(filePath, fe);
	}
	
	public boolean GetFields(String filePath, Table te) throws AppException{
		boolean res = false;
		File file = new File(filePath);
		if (!(res = isValidFile(filePath)))
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
	
	public int GetTables(String filePath, List<Table> tbArray) throws AppException{
		int index = 0;
		if (!isValidFile(filePath))
		{
			
			JOptionPane.showMessageDialog(null, "The selected database has no tables, Add some tables!");
			return 0;
		} 
		ArrayList<Table> array = FileHelper.ReadFile(filePath);
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
