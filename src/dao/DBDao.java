package dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import util.AppException;
import util.FileHelper;
import entity.Database;

public class DBDao
{

	public boolean isValidFile(String filepath)
	{
		
		File file = new File(filepath);

		return file.exists();
		
	}
	
	public boolean createDBFile(String filename) throws IOException 
	{

		File file = new File(filename);	
		return file.createNewFile();

	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getDBList() throws AppException, ClassNotFoundException, IOException
	{
		List<String> dbList = new ArrayList<String>();
		File file = new File("init.db");
		if (file.exists())
		{
			InputStream fileIn = new FileInputStream("init.db");
		    InputStream buffer = new BufferedInputStream(fileIn);
		    ObjectInput input = new ObjectInputStream (buffer);
			List<Database> list = (List<Database>) input.readObject();
			for (int i = 0; i < list.size();i++)
			{
				dbList.add(list.get(i).getDBName());
				
			}
			input.close();
		}
		else
		{
			throw new AppException(("A file Operation failed!"));
		}
		
		return dbList;
		
	}
	
	@SuppressWarnings({ "resource", "unchecked" })
	public boolean dbExist(Database db) throws AppException, IOException, ClassNotFoundException
	{
		boolean res = false;
		File file = new File("init.db");
		if (file.exists())
		{
			Database dbInfo = null;
			FileInputStream fileIn = new FileInputStream("init.db");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			List<Database> list = new ArrayList<Database>();
			list = (ArrayList<Database>) objectIn.readObject();
			for (int i = 0; i<list.size();i++)
			{
				String str = "";
				dbInfo = (Database) list.get(i);
				str = dbInfo.getDBName();
				
				if (str.equalsIgnoreCase(db.getDBName()))
				{
					res = true;
					System.out.println(res);
					throw new AppException(("Database already exist!"));
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
	
	public boolean create(String db_name, Database db, boolean bAppend) throws IOException
	{
		bAppend = FileHelper.writeFile(("init.db"), db);
		
		String filePath;

		filePath = db.getDBFilePath()+"\\"+db_name+".db";
		FileHelper.createDBDirectory(filePath);
		isValidFile(filePath);
		createDBFile(filePath);
		bAppend = FileHelper.writeFile(filePath, db);
		
		return bAppend;
	}
	
	@SuppressWarnings("unchecked")
	public boolean readDBBlock(String db_name, Database db) throws AppException, IOException, ClassNotFoundException
	{
		boolean res = false;
		File file = new File("init.db");
		if (file.exists())
		{
			Database dbInfo = null;
			InputStream fileIn = new FileInputStream("init.db");
		    InputStream buffer = new BufferedInputStream(fileIn);
		    ObjectInput input = new ObjectInputStream (buffer);
			List<Database> list = (List<Database>) input.readObject();
			for (int i = 0; i<list.size();i++)
			{
				String str = "";
				dbInfo = (Database) list.get(i);
				str = dbInfo.getDBName();
				
				if (str.equalsIgnoreCase(db_name))
				{
					db.setDBName(dbInfo.getDBName());
					db.setDBFilePath(dbInfo.getDBFilePath());
					db.setDBType(dbInfo.getDBType());
					db.setDBCreationTime(dbInfo.getDBCreationTime());
					res = true;
					break;
					
				}
			}
			
			input.close();
		}
		else
		{
			throw new AppException(("File Operation failed! Database information cannot be read!"));
		}
		
		return res;
	}
	
	public boolean getDatabase(String db_name, Database db) throws ClassNotFoundException, AppException, IOException
	{
		
		return  readDBBlock(db_name, db);

	}
	
}
