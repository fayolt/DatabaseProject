package dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import util.AppException;
import util.FileHelper;
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
		if (!isValidFile(filePath))
		{
			JOptionPane.showMessageDialog(null, "Table with no fields, Add some fields!");
			return false;
		}
		List<Field> fdList =  (List<Field>) FileHelper.ReadFile(filePath);
		for(int i = 0; i<fdList.size();i++)
		{
			if(tb.fieldArray == null)
				tb.fieldArray = new ArrayList<Field>();
			tb.fieldArray.add(fdList.get(i));
		}
			
		
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

	@SuppressWarnings("unchecked")
	public boolean update(Table tb, Vector data)
	{
		boolean res = false;

		List<Field> fdList = new ArrayList<Field>();
		
		for (int i = 0; i < data.size(); i++)
		{
			List<Object> fdetails = (List<Object>) data.get(i);
			Field fdEntry = new Field();
			fdEntry.setFieldName(fdetails.get(0).toString());
			fdEntry.setFieldType(fdetails.get(1).toString());
			fdEntry.setDefaultValue(fdetails.get(4).toString());
			if(fdetails.get(3).toString().equalsIgnoreCase("Primary Key") || fdetails.get(3).toString().equalsIgnoreCase("PK"))
				fdEntry.setFieldIntegrities(1);
			else if(fdetails.get(2).toString().equalsIgnoreCase("Not Null"))
				fdEntry.setFieldIntegrities(0);
			else
				fdEntry.setFieldIntegrities(-1);
			
			fdList.add(fdEntry);
			
			System.out.println(fdList);
		}
		
		try(OutputStream fileOut = new FileOutputStream(tb.getTdfPath());
			OutputStream buffer = new BufferedOutputStream(fileOut);
			ObjectOutput output = new ObjectOutputStream(buffer);)
		{
			output.writeObject(fdList);
			res =  true;
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 
		return res;
		
	}

}
