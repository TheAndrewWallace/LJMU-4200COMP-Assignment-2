package coursework2;

public class Seat {
	
	private String seatNum;
	private String seatClass;
	private boolean isWindow;
	private boolean isAisle;
	private boolean isTable;
	private double seatPrice;
	private String eMail;
	
	public Seat(String seatNum, String seatClass, boolean isWindow, boolean isAisle, boolean isTable, double seatPrice, String eMail)
	{
		this.seatNum = seatNum;
		this.seatClass = seatClass;
		this.isWindow = isWindow;
		this.isAisle = isAisle;
		this.isTable = isTable;
		this.seatPrice = seatPrice;
		this.eMail = eMail;
	}
	

	// Creates string version of data for writing to file
	public String toString()
	{
		String result = seatNum + " " + seatClass + " " + isWindow + " " + isAisle + " " + isTable + " " + seatPrice + " " + eMail;
		return result;
	}
	
	// Creates neat version of data for printing to user
	public String formattedString()
	{
		String result = seatNum + "	" + seatClass + "	" + isWindow + "	" + isAisle + "	" + isTable + "	" + seatPrice + "	" + eMail;
		return result;
	}
	
	// Gets data
	public String getSeat()
	{
		return this.seatNum;
		//String result = seatNum;
		//return result;
	}
	
	public String getSeatClass()
	{
		return this.seatClass;
	}
	
	public boolean getIsWindow()
	{
		return this.isWindow;
	}
	
	public boolean getIsAisle()
	{
		return isAisle;
	}
	
	public boolean getIsTable()
	{
		return isWindow;
	}
	
	public double getPrice()
	{
		return seatPrice;
	}
	
	public String getEMail()
	{
		return eMail;
	}
	
	// Takes in the email the user entered and edits the email.
	public void orderSeat(String userChoice)
	{
		this.eMail = userChoice;
		
		
		
		return;
	}
	
	// Cancels the selected seat by changing back to free
	public void cancelSeat()
	{
		this.eMail = "free";
	}
}
