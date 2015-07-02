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
import entity.FieldEntity;
import entity.Table;

public class FileHelper {
	
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
	public static boolean WriteFile(String filePath, Table tb)
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

	public static boolean WriteFile(String filePath, FieldEntity fe){
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileOutputStream fileOut;
		ObjectOutputStream objectOut;
		FileInputStream fileIn;
		ObjectInputStream objectIn;

		try{
			fileIn = new FileInputStream(filePath);
			objectIn = new ObjectInputStream(fileIn);
			ArrayList<FieldEntity> array = new ArrayList<FieldEntity>();
			if(objectIn.readObject()!=null){
				array = (ArrayList<FieldEntity>) objectIn.readObject();
			}
			FieldEntity feEntry = new FieldEntity();

			feEntry.setFieldName(fe.getFieldName());
			feEntry.setFieldType(fe.getFieldType());
			feEntry.setDefaultValue(fe.getDefaultValue());
			feEntry.setFieldIntegrities(fe.getFieldIntegrities());
			feEntry.setFieldCreationTime(fe.getFieldCreationTime());
			feEntry.setFieldMTime(fe.getFieldMTime());

			array.add(feEntry);

			fileOut = new FileOutputStream(filePath, true);
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(array);

			fileIn.close();
			objectIn.close();
			fileOut.close();
			objectOut.close();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	

	public static ArrayList ReadFile(String filePath){

		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileInputStream fileIn;
		ObjectInputStream objectIn;
		try{
			fileIn = new FileInputStream(filePath);
			objectIn = new ObjectInputStream(fileIn);
			ArrayList array = new ArrayList();
			if(objectIn.readObject()!=null){
				array = (ArrayList) objectIn.readObject();
			}
			else{
				throw new Exception("The file does not contain anything");
			}
			fileIn.close();
			objectIn.close();
			return array;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
