package asuHelloWorldJavaFX;

import java.util.ArrayList;

public class User {
	public ArrayList<Project> projectList;
	public ArrayList<UserStory> favoriteUserStories;
	public String userId;
	public String password;
	
	
	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
		this.favoriteUserStories = new ArrayList<>();

	}
	public User(ArrayList<Project> projectList) {
		this.projectList= projectList;
		favoriteUserStories = new ArrayList<>();
	}
	public String getUserId() {
		return this.userId;
	}
	public String getPassword() {
		return this.password;
	}

}
