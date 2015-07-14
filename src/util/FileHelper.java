package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import entity.Database;
import entity.Field;
import entity.Table;

public class FileHelper 
{

	public static void createDBDirectory(String filePath)
	{
		File file = new File(filePath);
		file.getParentFile().mkdirs(); 
	}
	
	@SuppressWarnings("unchecked")
	public static boolean writeFile(String filePath, Database db)
	{
		boolean res = false;

		List<Database> dbList;

		try(InputStream fileIn = new FileInputStream(filePath);
				InputStream buffer = new BufferedInputStream(fileIn);
				ObjectInput input = new ObjectInputStream (buffer);) 
				{
			dbList = (List<Database>) input.readObject();
				} catch (IOException | ClassNotFoundException e) 
		{
					dbList = new ArrayList<Database>();	
		} 

		Database dbEntry = new Database();

		dbEntry.setDBName(db.getDBName());
		dbEntry.setDBFilePath(db.getDBFilePath());
		dbEntry.setDBType(db.getDBType());
		dbEntry.setDBCreationTime(db.getDBCreationTime());

		dbList.add(dbEntry);

		try(OutputStream fileOut = new FileOutputStream(filePath);
				OutputStream buffer = new BufferedOutputStream(fileOut);
				ObjectOutput output = new ObjectOutputStream(buffer);
				)
				{
			output.writeObject(dbList);
			res =  true;
				} catch (IOException e) 
		{
					e.printStackTrace();
		} 
		return res;

	}
	
	@SuppressWarnings("unchecked")
	public static boolean writeFile(String filePath, Table tb)
	{
		boolean res = false;

		List<Table> tbList;

		try(InputStream fileIn = new FileInputStream(filePath);
				InputStream buffer = new BufferedInputStream(fileIn);
				ObjectInput input = new ObjectInputStream (buffer);) 
				{
			tbList = (List<Table>) input.readObject();
				} catch (IOException | ClassNotFoundException e) 
		{
					tbList = new ArrayList<Table>();	
		} 

		Table tbEntry = new Table();

		tbEntry.setTableName(tb.getTableName());
		tbEntry.setTdfPath(tb.getTdfPath());
		tbEntry.setTrdPath(tb.getTrdPath());
		tbEntry.setTicPath(tb.getTicPath());
		tbEntry.setTidPath(tb.getTidPath());
		tbEntry.setFieldNum(tb.getFieldNum());
		tbEntry.setRecordNum(tb.getRecordNum());
		tbEntry.setTableCreationTime(tb.getTableCreationTime());
		tbEntry.setTableMTime(tb.getTableMTime());

		tbList.add(tbEntry);

		try(OutputStream fileOut = new FileOutputStream(filePath);
				OutputStream buffer = new BufferedOutputStream(fileOut);
				ObjectOutput output = new ObjectOutputStream(buffer);
				)
				{
			output.writeObject(tbList);
			res =  true;
				} catch (IOException e) 
		{
					e.printStackTrace();
		} 
		return res;

	}

	@SuppressWarnings("unchecked")
	public static boolean writeFile(String filePath, Field fd)
	{
		boolean res = false;

		List<Field> fdList;

		try(InputStream fileIn = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(fileIn);
			ObjectInput input = new ObjectInputStream (buffer);) 
		{
			fdList = (List<Field>) input.readObject();
		} catch (IOException | ClassNotFoundException e) 
		{
			fdList = new ArrayList<Field>();	
		} 
		Field fdEntry = new Field();

		fdEntry.setFieldName(fd.getFieldName());
		fdEntry.setFieldType(fd.getFieldType());
		fdEntry.setDefaultValue(fd.getDefaultValue());
		fdEntry.setFieldIntegrities(fd.getFieldIntegrities());
		fdEntry.setFieldCreationTime(fd.getFieldCreationTime());
		fdEntry.setFieldMTime(fd.getFieldMTime());

		fdList.add(fdEntry);

		try(OutputStream fileOut = new FileOutputStream(filePath);
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

	public static List<?> ReadFile(String filePath)
	{
		List<?> list;

		try(InputStream fileIn = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(fileIn);
			ObjectInput input = new ObjectInputStream (buffer);) 
		{
			list = (List<?>) input.readObject();
		} catch (IOException | ClassNotFoundException e) 
		{
			list = new ArrayList<>();	
		} 
		return list;
	}
}
