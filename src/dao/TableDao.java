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
import entity.Field;
import entity.Table;

public class TableDao 
{
	public boolean isValidFile(String filePath)
	{
		File file = new File(filePath);

		return file.exists();
	}
	
	public boolean createTbFile(String filename) throws IOException
	{
		File file = new File(filename);	
		return file.createNewFile();
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	public boolean tbExist(String filePath, Table tb) throws AppException, IOException, ClassNotFoundException
	{
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
	
	public boolean create(String filePath, Table tb)
	{
		return FileHelper.writeFile(filePath, tb);
	}
	
	public boolean addField(String filePath, Field fd)
	{
		return FileHelper.writeFile(filePath, fd);
	}
	
	@SuppressWarnings("unchecked")
	public int getTables(String filePath, List<Table> tbArray) throws AppException
	{
		int index = 0;
		if (!isValidFile(filePath))
		{
			JOptionPane.showMessageDialog(null, "The selected database has no tables, Add some tables!");
			return 0;
		} 
		List<Table> tbList = (List<Table>) FileHelper.ReadFile(filePath);
		for(int i = 0; i<tbList.size();i++)
		{
			tbArray.add(tbList.get(i));
			getFields(tbList.get(i).getTdfPath(), tbArray.get(i));
		}
		if(tbArray!=null)
		{
			index = tbArray.size();
		}
		else
		{
			throw new AppException(("File Operation failed! Tables information cannot be read!"));
			
		}
		
		return index;
	}
	
	@SuppressWarnings("unchecked")
	public boolean getFields(String filePath, Table tb) throws AppException
	{
		boolean res = false;
		File file = new File(filePath);
		if (isValidFile(filePath))
		{
			JOptionPane.showMessageDialog(null, "Table with no fields, Add some fields!");
		}
		List<Field> fdList =  (List<Field>) FileHelper.ReadFile(filePath);
		for(int i = 0; i<fdList.size();i++)
			tb.fieldArray.add(fdList.get(i));
		
		if(tb.fieldArray!=null)
		{
			res=true;
		}
		else
		{
			throw new AppException(("File Operation failed! Fields information cannot be read!"));
			
		}

		return res;
	}

}
