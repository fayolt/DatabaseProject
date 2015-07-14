package dao;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;

import util.AppException;
import entity.Field;
import entity.Record;
import entity.Table;


public class RecordDao 
{
	public boolean isValidFile(String filePath)
	{
		File file = new File(filePath);

		return file.exists();
	}
	
	public boolean createTRDFile(String filename) throws IOException
	{
		File file = new File(filename);	
		return file.createNewFile();
	}
	
	@SuppressWarnings("unchecked")
	public boolean insert(Table tb, Record rec)
	{
		boolean res = false;
		Set<List<?>> recSet = (Set<List<?>>) read(tb);//read trd file
		
		String pk_field = null;//primary key constraint check from here
		for (int i = 0; i < tb.getFieldArray().size(); i++)
		{
			Field pField = tb.getFieldArray().get(i);
			if(pField.getFieldIntegrities()==1)
			{
				pk_field = pField.getFieldName();
				break;
			}	
		}
		Object[] array = recSet.toArray();
		for(int i=0; i<array.length;i++)
		{
			List<Object> list = (List<Object>) array[i];
			Record p_rec = new Record();
			for(int j=0; j<tb.getFieldArray().size();j++)
			{
				p_rec.put(tb.getFieldArray().get(j).getFieldName(), list.get(j));
			}
			
			if((rec.get(pk_field)).toString().equalsIgnoreCase((p_rec.get(pk_field)).toString()))
			{
				JOptionPane.showMessageDialog(null, "primary key constraint violated!");
				return false;
			}
				
		}//primary key constraint check to here
		List<Object> recList = new ArrayList<Object>();
		for (int i = 0; i < tb.getFieldArray().size(); i++)
		{
			Field pField = tb.getFieldArray().get(i);
			String strFieldName = pField.getFieldName();
			Object recEntry = rec.get(strFieldName);
			recList.add(recEntry);
		}
		if(!recSet.add(recList))
		{
			JOptionPane.showMessageDialog(null, "duplicated row, insert operation aborted!");
			return false;
		}
		try(FileOutputStream fileOut = new FileOutputStream(tb.getTrdPath(),false);
			OutputStream buffer = new BufferedOutputStream(fileOut);
			ObjectOutput output = new ObjectOutputStream(buffer);)
		{
			output.writeObject(recSet);
			res =  true;
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Set<?> read(Table tb)
	{
		Set<List<?>> recSet;

		try(InputStream fileIn = new FileInputStream(tb.getTrdPath());
				InputStream buffer = new BufferedInputStream(fileIn);
				ObjectInput input = new ObjectInputStream (buffer);) 
		{
			recSet = (Set<List<?>>) input.readObject();
		} catch (IOException | ClassNotFoundException e) 
		{	
			recSet = new HashSet<List<?>>();
		}
		return recSet; 
	}
	
	public int selectAll(Table tb, List<Record>data) throws AppException
	{
		Set<?> recSet = read(tb);
		Object[] array = recSet.toArray();
		for(int i=0; i<array.length;i++)
		{
			List<Object> list = (List<Object>) array[i];
			for(int j=0; j<tb.getFieldArray().size();j++)
			{
				Record rec = new Record();
				rec.put(tb.getFieldArray().get(j).getFieldName(), list.get(j));
				data.add(rec);
			}
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public boolean update(Table tb, Vector data)
	{
		boolean res = false;

		Set<List<?>> recSet = new HashSet<List<?>>();
		List<Object> recList = new ArrayList<Object>();
		
		
		int pos = -1;//primary key constraint check start
		for (int i = 0; i < tb.getFieldArray().size(); i++)
		{
			Field pField = tb.getFieldArray().get(i);
			if(pField.getFieldIntegrities()==1)
			{
				pos = i;
				break;
			}	
		}
		if(pos!=-1)
		{
			List<Object> pk_value_list = new ArrayList<Object>();
			for(int i=0; i<data.size();i++)
			{
				recList = (List<Object>) data.get(i);
				pk_value_list.add(recList.get(pos));
			}
			Set<Object> pk_value_set = new HashSet<Object>(pk_value_list);
			if(pk_value_set.size() < pk_value_list.size())
			{
				JOptionPane.showMessageDialog(null, "primary key constraint violated!");
				return false;
			}
		}//primary key constraint check end
		
		for (int i = 0; i < data.size(); i++)
		{
			recList = (List<Object>) data.get(i);
			if(!recSet.add(recList))
			{
				JOptionPane.showMessageDialog(null, "duplicated row, update operation aborted!");
				return false;
			}
			System.out.println(recSet);
		}
		try(FileOutputStream fileOut = new FileOutputStream(tb.getTrdPath(),false);
			OutputStream buffer = new BufferedOutputStream(fileOut);
			ObjectOutput output = new ObjectOutputStream(buffer);)
		{
			output.writeObject(recSet);
			res =  true;
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 
		return res;
	}
}
