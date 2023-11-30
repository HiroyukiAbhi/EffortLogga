package asuHelloWorldJavaFX;

public class UserStory {
	public int usID;
	public String userStoryName;
	public String userStoryContent;
	public double userStoryWeight;
	public int userStoryEstimation;
	
	public UserStory(String userStoryName, String userStoryContent,double userStoryWeight, int userStoryEstimation, int usID) {
		this.userStoryWeight = userStoryWeight;
		this.userStoryName = userStoryName;
		this.userStoryContent = userStoryContent;
		this.userStoryEstimation = userStoryEstimation;
		this.usID= usID;
	}
	
}
