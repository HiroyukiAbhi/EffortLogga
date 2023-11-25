package asuHelloWorldJavaFX;

public class UserStory {
	public String userStoryName;
	public String userStoryContent;
	public int userStoryWeight;
	
	public UserStory(String userStoryName, String userStoryContent,int userStoryWeight) {
		this.userStoryWeight = userStoryWeight;
		this.userStoryName = userStoryName;
		this.userStoryContent = userStoryContent;
	}
	
}
