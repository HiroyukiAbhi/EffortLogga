package asuHelloWorldJavaFX;

public class UserStory {
	public String userStoryName;
	public String userStoryContent;
	public double userStoryWeight;
	public int userStoryEstimation;
	
	public UserStory(String userStoryName, String userStoryContent,double userStoryWeight, int userStoryEstimation) {
		this.userStoryWeight = userStoryWeight;
		this.userStoryName = userStoryName;
		this.userStoryContent = userStoryContent;
		this.userStoryEstimation = userStoryEstimation;
	}
	
}
