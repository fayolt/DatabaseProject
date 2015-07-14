package util;

public class AppException extends Exception 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_strError;		//Exception message
	private int m_nCode;			//Exception code
	
	AppException()
	{
		m_strError = "";
		m_nCode = 0;
	}
	
	public AppException(String strError)
	{
		m_strError = strError;
		m_nCode = 0;
	}
	
	public String getErrorMessage()
	{
		return m_strError;
	}
	
	public void setErrorMessage(String m_strError) 
	{
		this.m_strError = m_strError;
	}

	public int getErrorCode() 
	{
		return m_nCode;
	}

	public void setErrorCode(int m_nCode) 
	{
		this.m_nCode = m_nCode;
	}

}
