import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class TaskManager {

	/************** @Linmei M. ***************/

	public static void main(String[] args) {

		System.out.println("|  Welcome to The Task Manager  |");
		Scanner sc = new Scanner(System.in);

		LinkedList<Task> taskList = new LinkedList<Task>();
		// Just initializing 1 task for testing
		Task ts = new Task(1, "Linmei", "05/05/2019", "Incomplete", "Studying");
		taskList.add(ts);
		int userChoice = 0;
		boolean quit = false;
		do {
			ts.displayMenu();

			switch (getUserChoice(sc)) {
			case 1:
				displayList(taskList);
				break;
			case 2: // ask user enter each piece of data
				addTasks(sc, taskList);// with incomplete status and task numbers
				break;
			case 3:
				deleteTask(sc, taskList);// choose to delete task
				break;
			case 4:
				getTasksStatus(sc, taskList);// mark task complete
				break;
			case 5:
				editTask(sc, taskList);
				break;
			case 6:
				getTaskBeforeADate(sc, taskList);
				break;
			case 7:
				searchMember(sc, taskList);
				break;
			case 8:
				quit = Validator.getUserConfirm(sc, "Are you sure you want to quite?(y/n): ");
				userChoice = 8;
				break;
			default:
				System.out.println("Invalid enter.");
				break;
			}
			if(!(userChoice == 8)) {
				returnMainMenu(sc);
			}
		} while (!quit);
		System.out.println("Goodbye!");
	}

	private static int getUserChoice(Scanner sc) {
		int choice = Validator.getInt(sc, "Please select a number on the main menu (Enter 1-8): ", 1, 8);
		return choice;
	}

	public static void returnMainMenu(Scanner sc) {
		System.out.println("Press \"Enter\" to return to the main menu.");
		sc.nextLine();
	}

	public static void displayList(LinkedList<Task> taskList) {
		Validator.tableFormat();
		for (int i = 0; i < taskList.size(); i++) {
			System.out.format("  %-11d %-13s %-18s %-23s %s %n", taskList.get(i).getNum(), taskList.get(i).getName(),
					taskList.get(i).getDueDate(), taskList.get(i).getTaskStatus(), taskList.get(i).getDescription());
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
	}

	public static void addTasks(Scanner sc, LinkedList<Task> taskList) {
		boolean continueAdd = true;
		while (continueAdd) {
			taskList.add(new Task(getTaskNumber(taskList), getMemberName(sc), getDueDate(sc), "Incomplete",
					getTaksDescription(sc)));
			continueAdd = Validator.getUserConfirm(sc, "Would you like to add more task?(y/n): ");
		}
	}

	private static int getTaskNumber(LinkedList<Task> taskList) {
		int num;
		if (taskList.isEmpty()) {
			num = 1;
		} else
			num = taskList.indexOf(taskList.getLast()) + 2;
		return num;
	}

	private static String getDueDate(Scanner sc) {
		System.out.println("Enter the due date(dd/MM/yyyy): ");
		String d = Validator.validateDate(sc, sc.nextLine());
		return d;
	}

	private static String getTaksDescription(Scanner sc) {
		System.out.println("Enter task description: ");
		String descrpStr = sc.nextLine();
		return descrpStr;
	}

	private static String getMemberName(Scanner sc) {
		String nameStr = Validator.getStringMatchingRegex(sc, "Enter member's name: ");
		return nameStr;
	}

	private static void deleteTask(Scanner sc, LinkedList<Task> taskList) {
		displayList(taskList);
		int itemNum = Validator.getInt(sc, "Enter the task number that you wish to delete: ", 1, taskList.size());// prompt
		for (int i = 0; i < taskList.size(); i++) {
			getTaskDeleted(sc, taskList, itemNum, i);

		}
	}

	private static void getTaskDeleted(Scanner sc, LinkedList<Task> taskList, int itemNum, int i) {
		if (taskList.get(i).getNum() == itemNum) {
//			System.out.println("Are you sure you want to delete task " + itemNum + " (y/n): ");
//			String ensure = sc.nextLine().toLowerCase();
//
//			if (ensure.equalsIgnoreCase("y") || ensure.equalsIgnoreCase("yes")) {
			if(Validator.getUserConfirm(sc, "Are you sure you want to delete task " + itemNum + " (y/n): " )) {
				taskList.remove(taskList.indexOf(taskList.get(i)));// remove the task
				System.out.println("Delete completed.");
				resetTaskNumber(taskList);
			}
		}
	}

	private static void resetTaskNumber(LinkedList<Task> taskList) {
		for (int i = 0; i < taskList.size(); i++) {
			taskList.get(i).setNum(i + 1);
		}
	}

	public static void getTasksStatus(Scanner sc, LinkedList<Task> taskList) {
		displayList(taskList);
		int num = Validator.getInt(sc, "Enter the task number that you want it to be complete?");
		searchForTaskAndConfrmChangeItsStatus(sc, taskList, num);
	}

	private static void searchForTaskAndConfrmChangeItsStatus(Scanner sc, LinkedList<Task> taskList, int num) {
		if (num <= taskList.size() && num > 0 && taskList.get(num - 1).getTaskStatus().matches("Incomplete")) {
			if(Validator.getUserConfirm(sc, "Are you sure the task has completed? (y/n):"))	{
				taskList.get(num - 1).setTaskStatus("Complete");
				System.out.println("Marked task number " + num + " as Complete.");
			}
		} else
			System.out.println("This task is already completed or the task doesn't exist.");
	}

	public static void editTask(Scanner sc, LinkedList<Task> taskList) {
		displayList(taskList);
		int num = Validator.getInt(sc, "Enter the task number you wish to edit: ");
		if (num <= taskList.size()) {
			getSpecificTitleUpdated(sc, taskList, num);
		} else {
			System.out.println("Name is not found");
			System.out.println(
					"-----------------------------------------------------------------------------------------------");
		}
	}

	private static void getSpecificTitleUpdated(Scanner sc, LinkedList<Task> taskList, int num) {
		String selectContent;
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getNum() == num) {
				selectContent = Validator.getString(sc,
						"Would you like to edit name, due date or task description?");
				updateListContent(sc, taskList, num, selectContent);
			}
		}
	}

	private static void updateListContent(Scanner sc, LinkedList<Task> taskList, int num, String content) {
		if(Validator.getUserConfirm(sc, "Are you sure to edit the " + content + "? (y/n)")) {
				getUpdated(taskList, content, num, Validator.getString(sc, "Please update the " + content + ": "));
			} 
	}

	private static void getUpdated(LinkedList<Task> taskList, String content, int num, String update) {
		if (content.equalsIgnoreCase("name")) {
			taskList.get(num - 1).setName(update);
		} else if (content.toLowerCase().equals("due date")) {
			taskList.get(num - 1).setDueDate(update);
		} else if (content.toLowerCase().equals("task description")) {
			taskList.get(num - 1).setDescription(update);
		}
		System.out.println(content + " has been updated.");
	}

	public static void getTaskBeforeADate(Scanner sc, LinkedList<Task> taskList) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String userDateInput = Validator.getString(sc,
				"Enter the date to show tasks before " + "that date(dd/mm/yyyy, Exclusive): ");
		LocalDate date1 = LocalDate.parse(userDateInput, f);

		Validator.tableFormat();
		for (int i = 0; i < taskList.size(); i++) {
			getTasksDisplayed(taskList, f, date1, i);
		}

		System.out.println(
				"-----------------------------------------------------------------------------------------------");
	}

	private static void getTasksDisplayed(LinkedList<Task> taskList, DateTimeFormatter f, LocalDate date1, int i) {
		LocalDate date2 = LocalDate.parse(taskList.get(i).getDueDate(), f);
		if ((date2.isBefore(date1))) {
			System.out.format("  %-11d %-13s %-18s %-23s %s %n", taskList.get(i).getNum(), taskList.get(i).getName(),
					taskList.get(i).getDueDate(), taskList.get(i).getTaskStatus(), taskList.get(i).getDescription());
		}
	}

	public static void searchMember(Scanner sc, LinkedList<Task> taskList) {
		String name = Validator.getString(sc, "Which member would you like to see: ");
		Validator.tableFormat();
		if (!taskList.isEmpty()) {
			for (int i = 0; i < taskList.size(); i++) {
				getMememberDisplayed(taskList, name, i);
				continue;
			}
			System.out.println(
					"-----------------------------------------------------------------------------------------------");
		} else {
			System.out.println("Name is not found");
			System.out.println(
					"-----------------------------------------------------------------------------------------------");
		}
	}

	private static void getMememberDisplayed(LinkedList<Task> taskList, String name, int i) {
		if (taskList.get(i).getName().toLowerCase().matches(name)) {
			System.out.format("  %-11d %-13s %-18s %-23s %s %n", taskList.get(i).getNum(), taskList.get(i).getName(),
					taskList.get(i).getDueDate(), taskList.get(i).getTaskStatus(), taskList.get(i).getDescription());
		}
	}
}
