package co.grandcircus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class TaskManager {

	
/************** Name: Linmei Mills ***************/
/*What this program can do:
 * 1. This program can list all members' tasks or display a specific members' tasks 
 * 2. It can delete tasks, add tasks, edit existed tasks, change task status,
 * 		 look up tasks before a specific data, and search tasks by name.
 * */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("||  Welcome to The Task Manager!  ||");
		LinkedList<Task> taskList = new LinkedList<Task>();

		Task ts = new Task(1, "Linmei", "05/05/2018", "Incomplete", "Grocery Shopping");
		taskList.add(ts);
		
		String quit = "n";
		do {
			ts.displayMenu();//display menu
			
			int	choice = Validator.getInt(sc, "Please select a number on the main menu (Enter 1-8): ");
			switch (choice) {
			case 1:
				displayList(taskList);
				System.out.println("Press \"Enter\" to return to the main menu.");
				sc.nextLine();
				System.out.println();
				break;
			case 2:						//ask user enter each piece of data
				addTasks(sc, taskList);// 	with incompleted status and task numbers
				System.out.println("Press \"Enter\" to return to the main menu.");
				sc.nextLine();
				break;
			case 3:
				deleteTask(sc, taskList);//choose to delete task
				System.out.println("Press \"Enter\" to return to the main menu.");
				sc.nextLine();
				break;
			case 4:
				tasksStatus(sc, taskList);//mark task complete 
				System.out.println("Press \"Enter\" to return to the main menu.");
				sc.nextLine();
				break;
			case 5:
				editTask(sc, taskList);
				System.out.println("Press \"Enter\" to return to the main menu.");
				sc.nextLine();
				break;
			case 6:
				getTaskBeforeADate(sc, taskList);
				System.out.println("Press \"Enter\" to return to the main menu.");
				sc.nextLine();
				break;
			case 7: 
				searchMember(sc,taskList);
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
	
	public static void displayList(LinkedList<Task> taskList){
		Validator.tableFormat();
		for(int i = 0; i < taskList.size(); i++) {
			System.out.format("  %-11d %-13s %-18s %-23s %s %n", taskList.get(i).getNum(), taskList.get(i).getName(),
					taskList.get(i).getDueDate(),taskList.get(i).getTaskStatus(), taskList.get(i).getDescription());
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
			String nameStr = Validator.getString(sc, "Enter member's name: ");
//			sc.nextLine();
			
			System.out.println("Enter task description: ");
			String descrpStr = sc.nextLine();
			
			System.out.println("Enter the due date(dd/dd/yyyy): ");
			String d =  sc.nextLine();
			
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
		int num = Validator.getInt(sc, "Enter the task number that you have complete?");//prompt
		System.out.println("Are you sure to change the status of number " + num + "? (y/n):");//make sure
		String ensure = sc.nextLine().toLowerCase();
		if(num <= taskList.size() && num > 0) {
			if(ensure.matches("y") || ensure.matches("yes")) {//if yes, change status from "Incomplete" to "complete"
				for(int i = 0; i < taskList.size(); i++) {
					if(taskList.get(i).getNum() == num) {//if item found
						taskList.set(taskList.indexOf(taskList.get(i)), new Task(taskList.get(i).getNum(),
								taskList.get(i).getName(),taskList.get(i).getDueDate(),
								 "Complete", taskList.get(i).getDescription()));
						System.out.println("Marked task number " + num + " as Complete.");
					}
				}
			}
		else System.out.println("Task number doesn't exist.");// if item not found
		}
	}
	

	public static void editTask(Scanner sc, LinkedList<Task> taskList){
		displayList(taskList);
		int num = Validator.getInt(sc, "Enter the task number you wish to edit its description: ");//prompt
		Validator.tableFormat();
		if(num <= taskList.size()){	
			for(int i = 0; i < taskList.size(); i++) {
				if(taskList.get(i).getNum() == num) {
					String ensure = Validator.getString(sc, "Are you sure to edit task number " + num + "? (y/n)");
					if(ensure.matches("y") || ensure.matches("yes")) {
						String updateD = Validator.getString(sc, "Please update the new description: ");
						taskList.set(taskList.indexOf(taskList.get(i)), new Task(taskList.get(i).getNum(),
								taskList.get(i).getName(),taskList.get(i).getDueDate(),
								taskList.get(i).getTaskStatus(), updateD));
						System.out.println("The description of task " + num + " has been updated");
					}
				}
			}
		}
		else {
			System.out.println("Name is not found");
			System.out.println("-----------------------------------------------------------------------------------------------");
		}
	}
	
	
	public static void getTaskBeforeADate(Scanner sc, LinkedList<Task> taskList) {
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
		String userDateInput = Validator.getString(sc,"Enter the date to show tasks before "
				+ "that date(dd/mm/yyyy, Exclusive): ");
		System.out.println(userDateInput);
		String taskDate = "";
		Validator.tableFormat();//table format
		
    	for(int i = 0; i < taskList.size(); i++) {
    		taskDate = taskList.get(i).getDueDate();
    		try {
				if((df.parse(taskDate).before(df.parse(userDateInput)))) {
					System.out.format("  %-11d %-13s %-18s %-23s %s %n", taskList.get(i).getNum(), taskList.get(i).getName(),
							taskList.get(i).getDueDate(),taskList.get(i).getTaskStatus(), taskList.get(i).getDescription());
				continue;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("No Task is due before \" "+ userDateInput + "\". ");
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
