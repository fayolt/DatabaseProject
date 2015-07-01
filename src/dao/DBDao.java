package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
			FileInputStream fileIn = new FileInputStream("init.db");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			List<Database> list = new ArrayList<Database>();
			list = (ArrayList<Database>) objectIn.readObject();
			for (int i = 0; i < list.size();i++)
			{
				dbList.add(list.get(i).getDBName());
				
			}
			fileIn.close();
			objectIn.close();
		}
		else
		{
			throw new AppException(("A file Operation failed!"));
		}
		
		
		return dbList;
		
	}
	@SuppressWarnings({ "resource", "unchecked" })
	public boolean dbExist(Database db) throws AppException, IOException, ClassNotFoundException{
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
	public boolean create(String db_name, Database db, boolean bAppend) throws IOException{
		bAppend = FileHelper.writeFile(("init.db"), db);
		
		String filePath;

		filePath = db.getDBFilePath()+"\\"+db_name+".db";
		FileHelper.createDBDirectory(filePath);
		isValidFile(filePath);
		createDBFile(filePath);
		bAppend = FileHelper.writeFile(filePath, db);
		
		return bAppend;
	}

	public boolean readDBBlock(String db_name, Database db) throws AppException, IOException, ClassNotFoundException{
		boolean res = false;
		File file = new File("init.db");
		if (file.exists())
		{
			Database dbInfo = null;
			FileInputStream fileIn = new FileInputStream("init.db");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			ArrayList<Object> list = new ArrayList<Object>();
			list = (ArrayList<Object>) objectIn.readObject();
			for (int i = 0; i<list.size();i++)
			{
				String str = "";
				dbInfo = (Database) list.get(i);
				str = dbInfo.getDBName();
				
				if (str == db.getDBName())
				{
					res = true;
					throw new AppException(("Database already exist!"));
				}
			}

			Database dbEntity = new Database(dbInfo);
			db.setDBName(dbEntity.getDBName());
			db.setDBFilePath(dbEntity.getDBFilePath());
			db.setDBType(dbEntity.getDBType());
			fileIn.close();
			objectIn.close();
		}
		else
		{
			throw new AppException(("File Operation failed! Database information cannot be read!"));
		}
		
		return res;
	}
	public boolean getDatabase(String db_name, Database db) throws ClassNotFoundException, AppException, IOException{
		boolean res = readDBBlock(db_name, db);
//		if (res)
//		{
//			FileHelper.MoveToDBDirectory("data");
//			//int i = _wchdir(db.getDBFilePath());
//		}
		return res;
	}
	

}
