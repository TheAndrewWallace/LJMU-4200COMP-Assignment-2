package coursework2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SeatBooker {

	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String choice = "";
		ArrayList<Seat> theSeats = fileSorter();

		// Prints menu until valid option selected
		do {
			
			printMenu();
			
			choice = input.next().toUpperCase();
			
			switch (choice) {
			// Allows user to reserve seat
			case "1": {
				theSeats = reserveSeat(theSeats);
				break;
			}
			// Allows user to cancel seat
			case "2": {
				theSeats = cancelSeat(theSeats);
				break;
			}
			
			// Allows user to view all reservations.
			case "3": {
				viewReservation(theSeats);
				break;
			}
			}			
		} while (!choice.equals("Q"));
		
		// Saves the file when program is quit
		savingFile(theSeats);

	}

	// Prints menu options
	private static void printMenu() {
		System.out.println("--Seat Booking System--\n");
		System.out.println("1 - Reserve Seat");
		System.out.println("2 - Cancel Seat");
		System.out.println("3 - View Seat Reservation");
		System.out.println("Q - Quit");
		System.out.print("Enter Option: ");
	}
	
	// Loads the file and creates ArrayList of objects
	private static ArrayList<Seat> fileSorter() throws FileNotFoundException {
		
		Boolean loadedCorrectly = false;
		
		// Will repeat until a file is found
		while(!loadedCorrectly)
		{
			try {
				Scanner file1 = new Scanner(new FileReader("seats.txt"));
				loadedCorrectly = true;
				file1.close();
			}
			catch(Exception obj1)
			{
				//Prompts user to check file and waits for them to repeat.
				System.out.println("File not found. Check file and press enter to try again.");
				input.nextLine();
			}
		}
		
		// Loads the file in
		Scanner file = new Scanner(new FileReader("seats.txt"));

		int index = 0;
		String lineData;
		String[] individualData;
		
		ArrayList<Seat> theSeats = new ArrayList<Seat>();
		
		// Gets information from file and creates objects
		//Will repeat depending on how many lines
		while(file.hasNextLine())
		{
			lineData = file.nextLine();
			individualData = lineData.split(" ");
			
			// Gets information from file on individual seat
			String seatNum = individualData[index++];
			String seatClass = individualData[index++];
			boolean isWindow = Boolean.parseBoolean(individualData[index++]);
			boolean isAisle = Boolean.parseBoolean(individualData[index++]);
			boolean isTable = Boolean.parseBoolean(individualData[index++]);
			double seatPrice = Double.parseDouble(individualData[index++]);
			String eMail = individualData[index];
			index = 0;
			
			// Creates an object
			Seat objt = new Seat(seatNum, seatClass, isWindow, isAisle, isTable, seatPrice, eMail);
			
			// Adds the object to the Array List
			theSeats.add(objt);

		}
		file.close();
		return theSeats;
	}
	
	
	private static ArrayList<Seat> reserveSeat(ArrayList<Seat> theSeats) {

		String option1;
		String option2;
		String option3;
		String option4;
		double option4Changed;
		
		// Gets all the options from user
		do {
			System.out.print("1: First Class\n2: Standard Class\nPlease enter number: ");
			option1 = input.next();
			System.out.println(option1);
		}while (!option1.equals("1") && !option1.equals("2"));
		
		do {
			System.out.print("\n1: Window\n2: Aisle\nPlease enter number: ");
			option2 = input.next();
		}while (!option2.equals("1") && !option2.equals("2"));
		
		do {
			System.out.print("\n1: With Table\n2: Without Table\nPlease enter number: ");
			option3 = input.next();
		}while (!option3.equals("1") && !option3.equals("2"));
		
		Boolean acceptedPrice = false;
		
		double price1 = 0;
		
		// Will repeat until a positive valid input is given.
		do {
			System.out.print("\nPlease enter maximum price you can afford: ");
			option4 = input.next();
			try {
				price1 = Double.parseDouble(option4);
				acceptedPrice = true;
			}
			catch (Exception obj2) {
				System.out.println("Invalid input. Please try again.");
			}
		} while (!acceptedPrice && price1 > 0);
		
		// Changes to double for calculations
		option4Changed = Double.parseDouble(option4);
		
		// Array list for ideal seats
		ArrayList<Integer> seatsFound = new ArrayList<Integer>();
		// Array list for next best fit (3/4 similarities)
		ArrayList<Integer> backUpSeats = new ArrayList<Integer>();
		
		// Counter which records how many similarities each object has to user options
		for(int i=0; i<theSeats.size(); i++)
		{
			int counter = 0;
			//System.out.println(theSeats.get(i).getSeatClass().equals("1ST"));
			System.out.println(option1.equals("1"));
			if(option1.equals("1") && theSeats.get(i).getSeatClass().equals("1ST"))
			{
				counter++;
				
			}

			if(option1.equals("2") && theSeats.get(i).getSeatClass().equals("STD"))
			{
				counter++;
				
			}
			
	
			if(option2.equals("1") && theSeats.get(i).getIsAisle())
			{
				counter++;
				
			}

			if(option2.equals("2") && !theSeats.get(i).getIsAisle())
			{
				counter++;
				
			}
			
			if(option3.equals("1") && theSeats.get(i).getIsTable())
			{
				counter++;
				
			}

			if(option3.equals("2") && !theSeats.get(i).getIsTable())
			{
				counter++;
				
			}
			if(option4Changed >= theSeats.get(i).getPrice())
			{
				counter++;
				
			}

			if(counter == 3 && theSeats.get(i).getEMail().equals("free"))
			{
				backUpSeats.add(i);
			}
			
			if(counter == 4 && theSeats.get(i).getEMail().equals("free"))
			{
				seatsFound.add(i);
			}
		}
		
		boolean foundPlace = false;
		boolean selectionFound = false;
		
		// Lists ideal seats if they are available
		if(seatsFound.size() > 0)
		{
			System.out.println("Seats found that match your request:");
			System.out.println("Num	Class	Window	Aisle	Table	Price	eMail");
			for(int i = 0; i< seatsFound.size(); i++)
			{
				
				
				System.out.println(theSeats.get(seatsFound.get(i)).formattedString());
				foundPlace = true;
			}
		}
		
		// Lists next best seats if the ideal are not available
		else if (backUpSeats.size() > 0)
		{		
			System.out.println("Seats found that match 3/4 of your requests:");
			System.out.println("Num	Class	Window	Aisle	Table	Price	eMail");
			for(int i = 0; i< seatsFound.size(); i++)
			{
				
				System.out.println(theSeats.get(backUpSeats.get(i)).formattedString());
				foundPlace = true;
			}
		}
		
		
		// Gets seat and email from user. If booking number is invalid then cancel booking.
		if(foundPlace)
		{
			System.out.print("Enter seat number to book: ");
			String userChoice = input.next().toUpperCase();
			for (int i=0; i<theSeats.size(); i++)
			{
				if(theSeats.get(i).getSeat().equals(userChoice))
				{
					System.out.print("Enter email address: ");
					String userChoice2 = input.next();
					theSeats.get(i).orderSeat(userChoice2);
					
					selectionFound = true;
				}
			}
			if (!selectionFound)
			{
				System.out.println("Invalid input. Cancelling booking.");
			}
		}
		
		// Notifies user that no seats close enough to request are available
		else
		{
			System.out.println("No seats matched your request close enough.");
		}
		
		
		
		return theSeats;
	}
	
	
	// Cancels selected seat
	private static ArrayList<Seat> cancelSeat(ArrayList<Seat> theSeats) {
		boolean selectionFound = false;
		
		// Gets input from user on which seat to cancel
		System.out.print("Please enter the seat number you want to cancel: ");
		String userChoice = input.next().toUpperCase();
		// Searches length of the ArrayList
		for (int i=0; i<theSeats.size(); i++)
		{
			if(theSeats.get(i).getSeat().equals(userChoice))
			{

				theSeats.get(i).cancelSeat();
				selectionFound = true;
			}
		}
		
		// Notifies user that input is invalid before returning to menu
		if (!selectionFound)
		{
			System.out.println("Invalid input. Returing to menu");
		}
				
		return theSeats;
	}

	// Prints menu
	private static void viewReservation(ArrayList<Seat> theSeats) {

		// Prints out formatted version of all data in current state
		System.out.println("Num	Class	Window	Aisle	Table	Price	eMail");
		for(int i=0; i<theSeats.size(); i++)
		{
			System.out.println(theSeats.get(i).formattedString());
		}
		System.out.println();
		
	}
	
	private static void savingFile(ArrayList<Seat> theSeats) throws FileNotFoundException {
		System.out.println("Saving File and Exiting");
		String endFile = "";
		
		// Writes to file seats.txt and overwrites previous data
		for(int i=0; i<theSeats.size(); i++)
		{
			endFile += (theSeats.get(i).toString() + "\n");
			
		}
		PrintWriter fileOut = new PrintWriter("seats.txt");
		fileOut.printf(endFile);
		fileOut.close();
			
	}
	
	

}
