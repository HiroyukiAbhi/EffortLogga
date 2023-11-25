package asuHelloWorldJavaFX;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.TreeItem;

public class Task {
	public String taskName;
	public String lifeCycleStep;
	public String effortCategory;
	public String delivarable;
	TreeItem<String> taskTreeItem;
	ArrayList<UserStory> userStories;

	
	
	public Task(String taskName,String lifeCycleStep,String effortCategory,String delivarable){
		this.taskName = taskName;
		this.lifeCycleStep = lifeCycleStep;
		this.effortCategory = effortCategory;
		this.delivarable = delivarable;
		this.taskTreeItem = new TreeItem<>(taskName);
		this.userStories = new ArrayList<>();
	}

}
