package co.grandcircus;

public class Task {

	private String name;
	private String description;
	private String dueDate;
	private String taskStatus;
	private int num;
	
	public Task(int n,String na, String date, String status, String descrip) {
		this.name = na;
		this.description = descrip;
		this.dueDate = date;
		this.taskStatus = status;
		this.num = n;
	
	}
	public Task() {
		
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public void displayMenu() {
		System.out.println("************ MENU **************");
		System.out.println("           1. List tasks \n           2. Add task \n           3. Delete task \n"
				+ "           4. Mark task complete\n           5. Edit Task\n           6. Search Tasks before A Date\n           "
				+ "7. Search Tasks by Name\n           8. Quit");
		System.out.println("********************************");
	}
	
}

