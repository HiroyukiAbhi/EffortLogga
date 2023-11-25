package asuHelloWorldJavaFX;
// Importing common libraries
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.lang.String;
import javafx.scene.input.MouseEvent;

public class EmployeeLandingPageController {
	@FXML
	private VBox landingpageroot;
	@FXML
	private Button closebutton;
	@FXML
	private AnchorPane ProjectViewPane;

	@FXML
	private TreeView<String> treeviewer;
	@FXML
	SplitPane splitPane;
	@FXML
	TableColumn<User, String> c1;
	@FXML
	TableColumn<User, Integer> c3;
	@FXML
	TableColumn<User, String> c2;
	@FXML
	TableView<User> tableView;

	@FXML
	SplitPane task_project_split;
	
	@FXML
	TextField tasknametextfield;
	@FXML
	TextField lifecyclesteptextfield;
	@FXML
	TextField effortcategorytextfield;
	@FXML
	TextField deliverabletextfield;
	@FXML
	TextField userStoryWeightText;
	@FXML
	TextField ProjectNameText;
	@FXML
	TextArea userStoryText;
	@FXML
	ListView<String> userStoryListView;
	
	public Parent root;
	double xOffset;
	double yOffset;
	Stage stage;
	
	//Application Varaibles
	public User currentUser;
	public ArrayList<Project> projectList;
	public DatabaseConnection connection;
	

	public void initialize() {
		// Add more here for when the window is initialized
		//stage = (Stage) ((Node) landingpageroot).getScene().getWindow();
		
		TreeItem<String> project = new TreeItem<>("Projects");
		treeviewer.setRoot(project);
		projectList = new ArrayList<Project>();
		currentUser = new User(projectList);
		connection = new DatabaseConnection();
		
		initialDividerSetup();
		//EffortLogger Historical Data view
		populateProjects(project);
		selectionListener(project);
		
		
		
	}
	private void selectionListener(TreeItem<String> project) {
		try {
			treeviewer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	            if(!treeviewer.getSelectionModel().getSelectedItem().equals(project)) {
	            	
	            
				
				if (newValue != null && treeviewer.getSelectionModel().getSelectedItem().getParent().getValue().equals("Projects")) {
					showProjectView(treeviewer.getSelectionModel().getSelectedItem().getValue());
	            }else if(newValue != null &&treeviewer.getSelectionModel().getSelectedItem().getParent().getValue().equals("Tasks")) {
	            	userStoryText.clear();
        			userStoryWeightText.clear();
	            	String selectedTask = treeviewer.getSelectionModel().getSelectedItem().getValue();
	            	String taskParent = treeviewer.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
	            	showTaskView(selectedTask, taskParent);
	            }
	            }
	            
	        });}
			catch(Exception e){
				e.printStackTrace();
			}
	}

	
	

	private void showTaskView(String selectedTaskName, String selectedTaskParentName) {
		// TODO Auto-generated method stub
		Project selection = findProject(projectList, selectedTaskParentName);
		Task selectedTask = findTask(selection.tasks, selectedTaskName);
		tasknametextfield.setText(selectedTask.taskName);
		lifecyclesteptextfield.setText(selectedTask.lifeCycleStep);
		effortcategorytextfield.setText(selectedTask.effortCategory);
		deliverabletextfield.setText(selectedTask.delivarable);
		showUserStories(selectedTask);
		
		
		
	}
	private void showUserStories(Task selectedTask) {
		// TODO Auto-generated method stub
		userStoryListView.getItems().clear();
		for(UserStory i:selectedTask.userStories) {
			userStoryListView.getItems().add(i.userStoryName);
		}
		
		userStoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			String selectedItemText = userStoryListView.getSelectionModel().getSelectedItem();
			if (newValue != null) {
	            	//userStoryText.setText(userStoryListView.getSelectionModel().getSelectedItem().userStoryContent);
	            	for(UserStory i: selectedTask.userStories) {
	            		if(i.userStoryName.equals(selectedItemText)){
	            			userStoryText.setText(i.userStoryContent);
	            			userStoryWeightText.setText(i.userStoryWeight + "");
	            		}
	            	}
	            }
	        });
		
	}
	private Project findProject(ArrayList<Project> list, String name) {
		Project selection = null;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).projectName.equals(name)) {
				selection = list.get(i);
			}
			
		}
		
		
		return selection;
		
	}private Task findTask(ArrayList<Task> list, String name) {
		Task selection = null;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).taskName.equals(name)) {
				selection = list.get(i);
			}
			
		}
		
		
		return selection;
		
	}



	private void showProjectView(String string) {
		// TODO Auto-generated method stub
		
	}




	//Populates project  tree view with the current projects in the database
	private void populateProjects(TreeItem<String> x) {
		// TODO Auto-generated method stub
		String fetchProject = "SELECT * FROM projects";
		try {
			
			Connection connector = connection.getConnection();
			Statement statement = connector.createStatement();
			ResultSet results = statement.executeQuery(fetchProject);
			//results.next();
			int i = 0;
			while(results.next()) {
				TreeItem<String> tasks = new TreeItem<>("Tasks");
				projectList.add(new Project(results.getString(2),results.getInt(1)));
				
				projectList.get(i).projectTreeItem.getChildren().add(tasks);
				x.getChildren().add(projectList.get(i).projectTreeItem);
				i++;
			}
			fetchProject = "SELECT projectID,projectname,userID,historicalDataID,taskName,LifeCycleStep,EffortCategory,Deliverable FROM tasks_data LEFT JOIN  projects ON projects.projectsid = tasks_data.projectID";
		
			
			results = statement.executeQuery(fetchProject);
			while(results.next()) {
				for(int j = 0; j < i;j++) {
				if(x.getChildren().get(j).getValue().equals(results.getString(2))) { //If project == query project
					
					Task temp = new Task(results.getString(5), results.getString(6), results.getString(7), results.getString(8));
					projectList.get(j).tasks.add(new Task(results.getString(5), results.getString(6), results.getString(7), results.getString(8)));
					x.getChildren().get(j).getChildren().get(0).getChildren().add(temp.taskTreeItem);
					j =i;
					
				}
				}
			}
			fetchUserStories(projectList, connector);
			connector.close();
			statement.close();
		}catch(Exception E) {
			E.printStackTrace();
		}
	
	}
	private void fetchUserStories(ArrayList<Project> projectList, Connection connector) {
		
		String fetchUS = "SELECT idtask, taskName, storypoint_name,storypoint_content,storypoint_weight FROM tasks_data INNER JOIN user_storypoints ON tasks_data.idtask = user_storypoints.taskID";
		try {
			Statement statement = connector.createStatement();
			ResultSet results = statement.executeQuery(fetchUS);
			while(results.next()) {
				for(int i = 0; i < projectList.size(); i++) {
					for(int j =0; j < projectList.get(i).tasks.size();j++) {
						if(projectList.get(i).tasks.get(j).taskName.equals(results.getString(2))) {
							projectList.get(i).tasks.get(j).userStories.add(new UserStory(results.getString(3),results.getString(4),results.getInt(5)));
						}
					}
					
					
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}




	public void setConnection(DatabaseConnection connection) {
		this.connection=  connection;
	}
	public void initialDividerSetup() {
		Divider divider1 = task_project_split.getDividers().get(0);
		divider1.setPosition(1);

		Divider divider = splitPane.getDividers().get(0);
		divider.positionProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
				divider.setPosition(0.2914);
			}
		});
	}


	// CUSTOMIZED UI SETTINGS -- DRAG TAB -- CLOSE BUTTON -- ADD MORE UI FEATURES UNDER THIS COMMENT IF NEEDED
	@FXML
	public void setOnMouseDragged(MouseEvent e) {
		stage.setX(e.getScreenX() - xOffset);
		stage.setY(e.getScreenY() - yOffset);
	}

	@FXML
	public void setOnMousePressed(MouseEvent event) {
		xOffset = event.getSceneX();
		yOffset = event.getSceneY();
	}

	@FXML
	public void onClose(ActionEvent event) {
		Stage stage = (Stage) closebutton.getScene().getWindow();
		stage.close();
	}
}
