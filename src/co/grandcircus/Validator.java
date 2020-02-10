
import java.text.DateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;


public class Validator {
	public static String getString(Scanner sc, String prompt) {
		System.out.print(prompt);
		String s = sc.nextLine().toLowerCase(); 
		if(!s.isEmpty()) {
			return s;
		}
		else return "Entry is empty";
	}
	
	public static int getInt(Scanner sc, String prompt) {
		int i = 0;
		boolean isValid = false;
		
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextInt()) {
				i = sc.nextInt();
				isValid = true;
			} else {
				System.out.println("Error! Invalid integer value. Try again.");
			}
		sc.nextLine();
		}
		return i;
	}

	public static int getInt(Scanner sc, String prompt, int min, int max) {
		int i = 0;
		boolean isValid = false;
		while (isValid == false) {
			i = getInt(sc, prompt);
			if (i < min)
				System.out.println("Error! Number must be " + min + " or greater.");
			else if (i > max)
				System.out.println("Error! Number must be " + max + " or less.");
			else
				isValid = true;
		}
		return i;
	}

	public static double getDouble(Scanner sc, String prompt) {
		double d = 0;
		boolean isValid = false;
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextDouble()) {
				d = sc.nextDouble();
				isValid = true;
			} else {
				System.out.println("Error! Invalid decimal value. Try again.");
			}
			sc.nextLine(); 
		}
		return d;
	}

	public static double getDouble(Scanner sc, String prompt, double min, double max) {
		double d = 0;
		boolean isValid = false;
		while (isValid == false) {
			d = getDouble(sc, prompt);
			if (d < min)
				System.out.println("Error! Number must be " + min + " or greater.");
			else if (d > max)
				System.out.println("Error! Number must be " + max + " or less.");
			else
				isValid = true;
		}
		return d;
	}

	public static String getStringMatchingRegex(Scanner sc, String prompt) {
		boolean isValid = false;

		String input;

		do {
			System.out.println(prompt);
			input = sc.nextLine();
			if (input.matches("[a-zA-Z ]+")) {
				isValid = true;
			} else {
				System.out.println("Input must match the right format: ");
				
			}

		} while (!isValid);

		return input;
	}
	
	public static String getListStringMatchingRegex(Scanner sc, String prompt, LinkedList<Task> list) {
		boolean isValid = false;

		String input;

		do {
			input = getString(sc, prompt);
			for(int i = 0; i < list.size(); i++) {
				if (input.matches(list.get(i).getName())) {
					isValid = true;
				} else {
					System.out.println("Input must match the right format: ");	
				}
			}

		} while (!isValid);

		return input;
	}
	
public static String validateDate(Scanner sc, String date) {
		String regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		if(matcher.matches()) {
			return date; 
		} else {
			return "Sorry, invalid data. ";
		}
	}
//get current time and date
public static String runtimeDate() {
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	Date date = new Date();
	String currentDate = df.format(date); 
	return currentDate;
	/*getting current date time using calendar class 
     * An Alternative of above*/
//    Calendar calobj = Calendar.getInstance();
//    System.out.println(df.format(calobj.getTime()));
}

	public static void tableFormat() {
	System.out.printf("%53s %n", "***** Task List *****");
	System.out.println("===============================================================================================");
	System.out.format(" %-12s %-13s %-18s %s %28s\n", "Task#", "Name", "Due Date", "Status", "Description");
	System.out.println("===============================================================================================");
	}
	
	// String w/o numbers and also not an empty string
	public static String checkStringWONumEmpty(Scanner sc, String prompt) {
		String str = "";
		System.out.println(prompt);
		if(sc.hasNextLine()) {
			str =  sc.nextLine();
			if(prompt.matches("[a-zA-Z]")) {
				return str;
			}
		}
		return "Invalid entry.";
	}
	
	public static boolean getUserConfirm(Scanner sc, String s) {
		boolean yOrN = false;
		boolean strCheck = true;
		while(strCheck) {
			System.out.println(s);	
			String str = sc.nextLine().toLowerCase();
			if(str.charAt(0) == 'y' || str.equals("yes")) {
				strCheck = false;
				yOrN = true;
			}
			else if(str.charAt(0)=='n' || str.equals("no")) { 
				yOrN = false;
				strCheck =  false;
			}else {
				System.out.print("Please enter 'y' or 'n' only, ");
				strCheck = true;
		}
		}
		return yOrN;
	}
}

