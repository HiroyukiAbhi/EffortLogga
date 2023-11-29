package asuHelloWorldJavaFX;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;

public class Project {
	public ArrayList<Task> tasks;
	public ArrayList<HistoricalData> historicalData;
	public ArrayList<Task> defects;
	public String projectName;
	public int projectID;
	TreeItem<String> projectTreeItem;

	public Project(String projectName, int projectID) {
		this.projectID = projectID;
		this.projectName = projectName;
		this.tasks = new ArrayList<Task>();
		this.historicalData = new ArrayList<HistoricalData>();
		this.defects = new ArrayList<Task>();
		this.projectTreeItem = new TreeItem<>(projectName);
	}

	public void addTasks(Task task) {
		tasks.add(task);
	}

	public void addHistoricalData(HistoricalData historicalData) {
		this.historicalData.add(historicalData);
	}

	public void addDefects(Task defects) {
		this.defects.add(defects);
	}
	
	
}
