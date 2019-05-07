import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class TaskManager {

	
/************** Name: Linmei Mills ***************/
/*What this program can do:
 * 1. This program can list all members' tasks or display a specific members' tasks 
 * 2. It can delete tasks, add tasks, edit existed tasks(name, due date and description), 
 * 		change task status, display tasks before a certain due date , and search tasks by name.
 * */
	public static void main(String[] args) {
		System.out.println("|  Welcome to The Task Manager!  |");
		Scanner sc = new Scanner(System.in);
		LinkedList<Task> taskList = new LinkedList<Task>();
		//Just initializing 1 task for testing
		Task ts = new Task(1, "Linmei", "05/05/2018", "Incomplete", "Studying");
		taskList.add(ts);
		
		String quit = "n";
		do {
			ts.displayMenu();//display menu
			
			int	choice = Validator.getInt(sc, "Please select a number on the main menu (Enter 1-8): ");
			switch (choice) {
			case 1:
				displayList(taskList);
				returnMainMenu(sc);
				break;
			case 2:						//ask user enter each piece of data
				addTasks(sc, taskList);// 	with incompleted status and task numbers
				returnMainMenu(sc);
				break;
			case 3:
				deleteTask(sc, taskList);//choose to delete task
				returnMainMenu(sc);
				break;
			case 4:
				tasksStatus(sc, taskList);//mark task complete 
				returnMainMenu(sc);
				break;
			case 5:
				editTask(sc, taskList);
				returnMainMenu(sc);
				break;
			case 6:
				getTaskBeforeADate(sc, taskList);
				returnMainMenu(sc);
				break;
			case 7: 
				searchMember(sc, taskList);
				returnMainMenu(sc);
				break;
			case 8:
				quit = userQuit(sc);// user choose quit, and ensure they want quit
				break;
			default:
				System.out.println("Invalid enter, please enter only 1-8.");
				break;
			}
			if(choice == 8 && quit == "y") {
				quit = "y";
			}
		}while(quit.equalsIgnoreCase("n"));
		System.out.println("Goodbye!");
	}
	
	public static void returnMainMenu(Scanner sc) {
		System.out.println("Press \"Enter\" to return to the main menu.");	
		sc.nextLine();
	}
	
	public static void displayList(LinkedList<Task> taskList){
		Validator.tableFormat();
		for(int i = 0; i < taskList.size(); i++) {
			System.out.format("  %-11d %-13s %-18s %-23s %s %n", taskList.get(i).getNum(), 
					taskList.get(i).getName(), taskList.get(i).getDueDate(),taskList.get(i).getTaskStatus(), taskList.get(i).getDescription());
		}	
		System.out.println("-----------------------------------------------------------------------------------------------");
 	}

	public static void addTasks(Scanner sc, LinkedList<Task> taskList) {
		String continueAdd = "y";
		int num = 0;
		while(continueAdd.equalsIgnoreCase("y")) {
			//in order to insert correct task#, first to find the last element in the current list, 
			//then find its index postion.
			//since index started 0, and we already have an initial task, so the next task# will be 2.
			if(taskList.isEmpty()) {
				num = 1;
			}
			else num = taskList.indexOf(taskList.getLast()) + 2; 
			String nameStr = Validator.getStringMatchingRegex(sc, "Enter member's name: ");
//			sc.nextLine();
			
			System.out.println("Enter task description: ");
			String descrpStr = sc.nextLine();
			
			System.out.println("Enter the due date(dd/MM/yyyy): ");
			String d =Validator.validateDate(sc, sc.nextLine());
			
			String dateStr = Validator.validateDate(sc, d);
			String taskStatuStr = "Incomplete"; 
			taskList.add(new Task(num, nameStr, dateStr, taskStatuStr, descrpStr));
			continueAdd = Validator.getString(sc,"Would you like to add more task?(y/n): ");
		}
	}
	
	private static void deleteTask(Scanner sc, LinkedList<Task> taskList) {
		displayList(taskList);
		int itemNum =Validator.getInt(sc, "Enter the task number that you wish to delete: ");//prompt
		if(itemNum <= taskList.size() && itemNum > 0) {
			for(int i = 0; i < taskList.size(); i++) {
				if(taskList.get(i).getNum() == itemNum) {//number must in the range.
					//display what number of the task and ask if they are sure to delete it
					System.out.println("Are you sure you want to delete task " + itemNum + " (y/n): ");
					String ensure = sc.nextLine().toLowerCase();
					if(ensure.equalsIgnoreCase("y") || ensure.equalsIgnoreCase("yes")) {
						taskList.remove(taskList.indexOf(taskList.get(i)));//remove the task
						System.out.println("Delete completed.");
						}
				}
			}
		}
		else System.out.println("Task number is not found");
	}
	
	
	public static void tasksStatus(Scanner sc, LinkedList<Task> taskList){
		displayList(taskList);
		int num = Validator.getInt(sc, "Enter the task number that you want it to be complete?");//prompt
		if(num <= taskList.size() && num > 0 && taskList.get(num - 1).getTaskStatus().matches("Incomplete")) {
			System.out.println("Are you sure to change the status of number " + num + "? (y/n):");//make sure
			String ensure = sc.nextLine().toLowerCase();
			if(ensure.matches("y") || ensure.matches("yes")) {//if yes, change status from "Incomplete" to "complete"
				taskList.get(num - 1).setTaskStatus("Complete");
				System.out.println("Marked task number " + num + " as Complete.");
			}
		}
		else System.out.println("This task is already completed or the task doesn't exist.");
	}
	
	

	public static void editTask(Scanner sc, LinkedList<Task> taskList){
		displayList(taskList);
		int num = Validator.getInt(sc, "Enter the task number you wish to edit: ");
//		Validator.tableFormat();
		String selectContent = "";
		if(num <= taskList.size()){	
			for(int i = 0; i < taskList.size(); i++) {
				if(taskList.get(i).getNum() == num) {
					selectContent = Validator.getString(sc,"Would you like to edit name, due date or task description?");
					updateListContent(sc, taskList, num, selectContent);
				}
			}
		}
		else {
			System.out.println("Name is not found");
			System.out.println("-----------------------------------------------------------------------------------------------");
		}
	}

	private static void updateListContent(Scanner sc, LinkedList<Task> taskList, int num, String content){
		String ensure = Validator.getString(sc, "Are you sure to edit the "+ content + "? (y/n)");
		if(ensure.toLowerCase().equalsIgnoreCase("y")) {
			if(ensure.matches("y") || ensure.matches("yes")) {
				String update= Validator.getString(sc, "Please update the " + content + ": ");
				if(content.equalsIgnoreCase("name")) {
					taskList.get(num - 1).setName(update);
				}else if(content.toLowerCase().matches("due date")){
					taskList.get(num - 1).setDueDate(update);
				}else if(content.toLowerCase().matches("Description")) {
					taskList.get(num - 1).setDescription(update);
				}
//				else System.out.println("Entry is invalid.");
				System.out.println(content + " has been updated.");
			}
		}else System.out.println("Entry is invalid");
	}
	
	//there is a bug. not sure how to fix it
	
	public static void getTaskBeforeADate(Scanner sc, LinkedList<Task> taskList) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String userDateInput = Validator.getString(sc,"Enter the date to show tasks before "
				+ "that date(dd/MM/yyyy, Exclusive): ");
		
		LocalDate date1 = LocalDate.parse(userDateInput, f);
		String taskDate = "";
		Validator.tableFormat();
		
    	for(int i = 0; i < taskList.size(); i++) {
    		LocalDate date2 = LocalDate.parse(taskList.get(i).getDueDate(), f);
			if((date2.isBefore(date1))) {
				System.out.format("  %-11d %-13s %-18s %-23s %s %n", taskList.get(i).getNum(), taskList.get(i).getName(),
						taskList.get(i).getDueDate(),taskList.get(i).getTaskStatus(), taskList.get(i).getDescription());
			}
    	}
    	
		System.out.println("-----------------------------------------------------------------------------------------------");
	}
	
	public static void searchMember(Scanner sc, LinkedList<Task> taskList) {
		String name = Validator.getString(sc, "Which member would you like to see: ");
		Validator.tableFormat();
		if(!taskList.isEmpty()){
			for(int i = 0; i < taskList.size(); i++) {
				if(taskList.get(i).getName().toLowerCase().matches(name)) {
					System.out.format("  %-11d %-13s %-18s %-23s %s %n", taskList.get(i).getNum(), taskList.get(i).getName(),
						taskList.get(i).getDueDate(),taskList.get(i).getTaskStatus(), taskList.get(i).getDescription());
				}
				continue;
			}
			System.out.println("-----------------------------------------------------------------------------------------------");
		}
		else {
			System.out.println("Name is not found");
			System.out.println("-----------------------------------------------------------------------------------------------");
		}
	}

	public static String userQuit(Scanner sc) {
		String quit;
		System.out.println("Are you sure to quit? (y/n)");
		quit = sc.nextLine().toLowerCase();
		if(quit.matches("y") || quit.matches("yes")){
			quit = "y";
		}
		else quit = "n";
		return quit;
	}
}
