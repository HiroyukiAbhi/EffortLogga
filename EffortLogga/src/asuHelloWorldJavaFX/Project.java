package asuHelloWorldJavaFX;

import java.util.ArrayList;

public class Project {
	public ArrayList<Task> tasks;
	public String projectName;
	public String projectID;
	
	public Project(String projectName, String projectID) {
		this.projectID = projectID;
		this.projectName = projectName;
	}
	
	
}
