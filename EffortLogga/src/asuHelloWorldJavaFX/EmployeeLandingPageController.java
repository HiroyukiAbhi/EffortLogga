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
import javafx.scene.control.ComboBox;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;
import javafx.scene.input.MouseEvent;

public class EmployeeLandingPageController {
	@FXML
	Label consoleMessage;

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
	TextField userStoryWeightText1;
	@FXML
	TextField ProjectNameText;
	@FXML
	TextArea userStoryText;
	@FXML
	TextArea userStoryText1;
	@FXML
	TextArea historicalDataContext;
	@FXML
	ListView<String> userStoryListView;
	@FXML
	ListView<String> historicalDataListView;
	@FXML
	Button favoriteUSButton;
	
	@FXML
	TextField userStoryEstimate;
	
	@FXML
	ListView<String> userStoryListView1;

	@FXML
	ComboBox<String> projectComboBox;

	@FXML
	ComboBox<String> taskNameComboBox;

	@FXML
	TextField lifecyclesteptextfieldpp;

	@FXML
	TextField effortcategorytextfieldpp;

	@FXML
	TextField deliverabletextfieldpp;

	@FXML
	TextField EstimationText;

	@FXML
	ImageView ace;

	@FXML
	ImageView king;

	@FXML
	ImageView queen;

	@FXML
	ImageView jack;

	@FXML
	ImageView eight;

	@FXML
	ImageView five;

	@FXML
	ImageView three;

	@FXML
	ImageView two;

	@FXML
	ImageView one;

	@FXML
	ImageView zero;

	@FXML
	TextField currentEstimate;

	@FXML
	ListView<String> activeUsers;
	@FXML

	public Parent root;
	double xOffset;
	double yOffset;
	Stage stage;
	Task sT;
	String taskID;
	// Application Varaibles
	public User currentUser;
	public ArrayList<Project> projectList;
	public DatabaseConnection connection;
	public HashMap<String, Integer> values;
	public int storyEstimate;
	public String username, password;
	boolean joinPPSession;

	public void initialize() {
	    // Add more here for when the window is initialized
	    
	    // Initializing variables for story estimation and session participation
	    storyEstimate = -1;
	    joinPPSession = false;
	    
	    // Creating a root node for the project tree view
	    TreeItem<String> project = new TreeItem<>("Projects");
	    treeviewer.setRoot(project);
	    
	    // Initializing a list to store project information
	    projectList = new ArrayList<Project>();
	    
	    // Creating a user object with the project list
	    currentUser = new User(projectList);

	    // Establishing a connection to the database
	    connection = new DatabaseConnection();

	    // Setting up the initial divider configuration
	    initialDividerSetup();
	    
	    // Populating the project tree view with project data
	    // and setting up a selection listener for handling project selection
	    populateProjects(project);
	    selectionListener(project);

	    // Start of planning poker scene methods
	    
	    // Initializing values for planning poker session
	    initValues();
	    
	    // Loading the Planning Poker view
	    loadPlanningPokerView();
	    
	    // Setting up system listener for additional functionality
	    SystemListener();
	}


	public void getUserStoryEffortEstimation(UserStory currentUserStory) {
		try {
			
			
			String query = "SELECT * FROM PlanningPokerLog WHERE userStoryID = '" + currentUserStory.usID + "'";
			Connection connector = connection.getConnection();

			Statement x = connector.createStatement();
			ResultSet r = x.executeQuery(query);
			int estimation = 0;
			while (r.next()) {
				if(r.getInt(4) > estimation) {
					estimation =(r.getInt(4));
				}
			}
			currentUserStory.userStoryEstimation = estimation;
			r.close();
			x.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void onRefreshPressed(ActionEvent e) {
		activeUsers();
	}

	public void activeUsers() {
		if (sT != null) {
			try {
				activeUsers.getItems().clear();
				String query = "SELECT * FROM CurrentPlayers WHERE taskID = '" + findTaskID(sT) + "'";
				Connection connector = connection.getConnection();

				Statement x = connector.createStatement();
				ResultSet r = x.executeQuery(query);
				while (r.next()) {
					activeUsers.getItems().add("USER " + r.getInt(2));
				}
				r.close();
				x.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
	}

	@FXML
	public void onSendEstimate(ActionEvent e) {
		if (joinPPSession) {

			findUID(this.username);

			if (findTaskID(sT) != null && this.currentUser.userId != null && this.storyEstimate >= 0
					&& this.userStoryListView1.getSelectionModel().getSelectedItem() != null) {
				try {
					UserStory SUS = findUS();
					String q = "INSERT INTO PlanningPokerLog (taskID, employeeID, effortEstimation, userStoryID) VALUES ("
							+ this.taskID + "," + this.currentUser.userId + "," + this.storyEstimate + ", " + SUS.usID
							+ ")";
					Connection connector = connection.getConnection();

					PreparedStatement x = connector.prepareStatement(q);
					x.executeUpdate();
					x.close();
					connector.close();
					consoleMessage.setTextFill(Color.WHITE);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (findTaskID(sT) == null) {

				consoleMessage.setTextFill(Color.RED);
				consoleMessage.setText("Task NOT FOUND!");
			} else if (this.currentUser.userId == null) {

				consoleMessage.setTextFill(Color.RED);
				consoleMessage.setText("USER NOT FOUND!");
			} else if (this.storyEstimate >= 0) {

				consoleMessage.setTextFill(Color.RED);
				consoleMessage.setText("PICK A CARD!");
			} else if (this.userStoryListView1.getSelectionModel().getSelectedItem() == null) {

				consoleMessage.setTextFill(Color.RED);
				consoleMessage.setText("SELECT A USER STORY!");
			}
		} else {
			consoleMessage.setTextFill(Color.RED);
			consoleMessage.setText("Join a Planning Poker session!");
		}
	}

	public UserStory findUS() {
		if (sT != null && this.userStoryListView1.getSelectionModel().getSelectedItem() != null) {
			for (UserStory i : sT.userStories) {
				if (i.userStoryName.equals(this.userStoryListView1.getSelectionModel().getSelectedItem())) {
					return i;
				}
			}
		}
		consoleMessage.setTextFill(Color.RED);
		;
		consoleMessage.setText("Cannot Find User Story");
		return null;

	}

	@FXML
	public void onExitButtonPressed(ActionEvent e) {
		exitSession();

	}

	public void SystemListener() {
		consoleMessage.textFillProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.equals(Color.RED)) {
				consoleMessage.setOpacity(1);
				System.out.println("Label text fill color is RED.");
			} else if (newValue.equals(Color.WHITE)) {
				consoleMessage.setOpacity(0);
				System.out.println("Label text fill color is WHITE.");
			} else {
				System.out.println("Label text fill color is neither RED nor WHITE.");
			}
		});
	}

	public void exitSession() {
		if (joinPPSession) {
			try {
				findUID(this.username);
				String q = "DELETE FROM CurrentPlayers WHERE playerID = " + this.currentUser.userId + "";
				Connection connector = connection.getConnection();

				PreparedStatement x;

				x = connector.prepareStatement(q);

				x.executeUpdate();
				x.close();
				connector.close();
				joinPPSession = false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			consoleMessage.setTextFill(Color.RED);
			consoleMessage.setText("DID NOT JOIN A Planning Poker Session yet!");
		}
	}

	@FXML
	public void onJoinPressed(ActionEvent e) {
		if (sT != null && this.userStoryListView1.getSelectionModel().getSelectedItem() != null) {
			findUID(this.username);
			taskID = findTaskID(sT);
			joinSession(taskID);
			joinPPSession = true;
			consoleMessage.setTextFill(Color.WHITE);
		} else {
			consoleMessage.setTextFill(Color.RED);
			consoleMessage.setText("SELECT A  USER STORY!");
			System.out.println("SELECT A  TASK");
		}

	}

	private String findTaskID(Task task) {
		String id = "";
		try {
			String query = "SELECT * FROM tasks_data WHERE taskName = '" + task.taskName + "'";
			Connection connector = connection.getConnection();

			Statement x = connector.createStatement();
			ResultSet r = x.executeQuery(query);
			while (r.next()) {
				id = r.getInt(1) + "";
			}
			r.close();
			x.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		this.taskID = id;
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String joinSession(String taskid) {
		try {
			String q = "INSERT INTO CurrentPlayers (playerID, taskID) VALUES (" + this.currentUser.userId + "," + taskid
					+ ")";
			Connection connector = connection.getConnection();

			PreparedStatement x = connector.prepareStatement(q);
			x.executeUpdate();
			x.close();
			connector.close();
			activeUsers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void findUID(String username) {
		try {
			String query = "SELECT * FROM userAccounts WHERE username = '" + username + "'";
			Connection connector = connection.getConnection();

			Statement x = connector.createStatement();
			ResultSet r = x.executeQuery(query);
			while (r.next()) {
				this.currentUser.userId = r.getInt(1) + "";
			}
			r.close();
			x.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadPlanningPokerView() {
		for (Project i : projectList) {
			projectComboBox.getItems().add(i.projectName);
		}
		projectComboListener();
		taskComboListener();
		userStoryListener();

	}

	private void populateUserStories(Task task) {
		userStoryListView1.getItems().clear();
		for (UserStory i : task.userStories) {
			userStoryListView1.getItems().add(i.userStoryName);
		}

	}

	private void userStoryListener() {

		// TODO Auto-generated method stub
		userStoryListView1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			String selectedItemText = userStoryListView1.getSelectionModel().getSelectedItem();
			if (newValue != null) {
				// userStoryText.setText(userStoryListView.getSelectionModel().getSelectedItem().userStoryContent);
				for (UserStory i : sT.userStories) {
					if (i.userStoryName.equals(selectedItemText)) {
						userStoryText1.setText(i.userStoryContent);
						userStoryWeightText1.setText(i.userStoryWeight + "");
					}
				}
			}
		});

	}

	private void taskComboListener() {
		taskNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			Task selectedTask = findTask(
					findProject(projectList, projectComboBox.getSelectionModel().getSelectedItem()).tasks, newValue);
			sT = selectedTask;
			if (selectedTask != null) {
				lifecyclesteptextfieldpp.setText(selectedTask.lifeCycleStep);
				effortcategorytextfieldpp.setText(selectedTask.effortCategory);
				deliverabletextfieldpp.setText(selectedTask.delivarable);
				populateUserStories(selectedTask);
			} else {
				lifecyclesteptextfieldpp.setText("SELECT TASK");
				effortcategorytextfieldpp.setText("SELECT TASK");
				deliverabletextfieldpp.setText("SELECT TASK");
			}
		});

	}

	public void projectComboListener() {
		projectComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Project selectedProject = findProject(projectList, newValue);
			taskNameComboBox.getItems().clear();
			for (Task i : selectedProject.tasks) {
				taskNameComboBox.getItems().add(i.taskName);
			}

		});
	}

	public void initValues() {
		values = new HashMap<>();
		values.put("zero", 0);
		values.put("one", 1);
		values.put("two", 2);
		values.put("three", 3);
		values.put("five", 5);
		values.put("eight", 8);
		values.put("jack", 13);
		values.put("queen", 20);
		values.put("king", 40);
		values.put("ace", 100);
	}

	@FXML
	public void press(MouseEvent e) {
		Node source = (Node) e.getSource();
		storyEstimate = values.get(source.getId());
		currentEstimate.setText(storyEstimate + "");
	}

	private void selectionListener(TreeItem<String> project) {
		try {
			Divider divider1 = task_project_split.getDividers().get(0);
			treeviewer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				if (!treeviewer.getSelectionModel().getSelectedItem().equals(project)) {

					if (newValue != null && treeviewer.getSelectionModel().getSelectedItem().getParent().getValue()
							.equals("Projects")) {
						showProjectView(treeviewer.getSelectionModel().getSelectedItem().getValue());

						divider1.setPosition(0.0033);
					} else if (newValue != null && treeviewer.getSelectionModel().getSelectedItem().getParent()
							.getValue().equals("Tasks")) {
						userStoryText.clear();
						userStoryWeightText.clear();
						divider1.setPosition(0.9967);
						String selectedTask = treeviewer.getSelectionModel().getSelectedItem().getValue();
						String taskParent = treeviewer.getSelectionModel().getSelectedItem().getParent().getParent()
								.getValue();
						showTaskView(selectedTask, taskParent);
					}
				}

			});
		} catch (Exception e) {
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
		// Populate task effort estimation
		double estimation = 0;
		double weightSum = 0;
		for(UserStory i: selectedTask.userStories) {
			getUserStoryEffortEstimation(i);
		}
		if (selectedTask.userStories != null) {
			for (UserStory i : selectedTask.userStories) {
				estimation += (i.userStoryEstimation) * (i.userStoryWeight);
				weightSum += i.userStoryWeight;
			}
			estimation = estimation / weightSum;
			EstimationText.setText(estimation + "");

		} else {
			EstimationText.setText("N/A");

		}

	}

	private void showUserStories(Task selectedTask) {
		// TODO Auto-generated method stub
		userStoryListView.getItems().clear();
		userStoryEstimate.setText("Select User Story");
		userStoryWeightText.setText("Select User Story");
		for (UserStory i : selectedTask.userStories) {
			userStoryListView.getItems().add(i.userStoryName);
		}

		userStoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			String selectedItemText = userStoryListView.getSelectionModel().getSelectedItem();
			if (newValue != null) {
				// userStoryText.setText(userStoryListView.getSelectionModel().getSelectedItem().userStoryContent);
				for (UserStory i : selectedTask.userStories) {
					if (i.userStoryName.equals(selectedItemText)) {
						userStoryText.setText(i.userStoryContent);
						userStoryWeightText.setText(i.userStoryWeight + "");
						userStoryEstimate.setText(i.userStoryEstimation+"");
					}
				}
			}
		});

	}

	private Project findProject(ArrayList<Project> list, String name) {
		Project selection = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).projectName.equals(name)) {
				selection = list.get(i);
			}

		}

		return selection;

	}

	private Task findTask(ArrayList<Task> list, String name) {
		Task selection = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).taskName.equals(name)) {
				selection = list.get(i);
			}

		}

		return selection;

	}

	private void showProjectView(String projectName) {
		// TODO Auto-generated method stub
		ProjectNameText.setText(projectName);
		historicalDataListView.getItems().clear();
		Project selection = findProject(projectList, projectName);
		for (HistoricalData i : selection.historicalData) {
			historicalDataListView.getItems().add(i.historicalDataName);
		}
		historicalDataListView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					String selectedItemText = historicalDataListView.getSelectionModel().getSelectedItem();
					if (newValue != null) {
						// userStoryText.setText(userStoryListView.getSelectionModel().getSelectedItem().userStoryContent);
						for (HistoricalData i : selection.historicalData) {
							if (i.historicalDataName.equals(selectedItemText)) {
								historicalDataContext.setText(i.historicalDataContent);
								System.out.println(i.historicalDataContent);

							}
						}
					}
				});

	}

	// Populates project tree view with the current projects in the database
	private void populateProjects(TreeItem<String> x) {
		// TODO Auto-generated method stub
		String fetchProject = "SELECT * FROM projects";
		try {

			Connection connector = connection.getConnection();
			Statement statement = connector.createStatement();
			ResultSet results = statement.executeQuery(fetchProject);
			// results.next();
			int i = 0;
			while (results.next()) {
				TreeItem<String> tasks = new TreeItem<>("Tasks");
				projectList.add(new Project(results.getString(2), results.getInt(1)));

				projectList.get(i).projectTreeItem.getChildren().add(tasks);
				x.getChildren().add(projectList.get(i).projectTreeItem);
				i++;
			}
			fetchProject = "SELECT projectID,projectname,userID,taskName,LifeCycleStep,EffortCategory,Deliverable FROM tasks_data LEFT JOIN  projects ON projects.projectsid = tasks_data.projectID";

			results = statement.executeQuery(fetchProject);
			while (results.next()) {
				for (int j = 0; j < i; j++) {
					if (x.getChildren().get(j).getValue().equals(results.getString(2))) { // If project == query project

						Task temp = new Task(results.getString(4), results.getString(5), results.getString(6),
								results.getString(7));
						projectList.get(j).tasks.add(temp);
						x.getChildren().get(j).getChildren().get(0).getChildren().add(temp.taskTreeItem);
						j = i;

					}
				}
			}
			fetchUserStories(projectList, connector);
			fetchHistoricalData(projectList, connector);

			connector.close();
			statement.close();
		} catch (Exception E) {
			E.printStackTrace();
		}

	}

	private void fetchHistoricalData(ArrayList<Project> projectList2, Connection connector) {
		// TODO Auto-generated method stub

		try {
			String fetchHD = "SELECT projectname, hdName, hdContent FROM projects INNER JOIN historicalD ON projects.projectsid = historicalD.pID";
			Statement statement = connector.createStatement();
			ResultSet results = statement.executeQuery(fetchHD);
			while (results.next()) {
				for (int i = 0; i < projectList.size(); i++) {

					if (projectList.get(i).projectName.equals(results.getString(1))) {
						projectList.get(i).historicalData
								.add(new HistoricalData(results.getString(2), results.getString(3)));
					}

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void fetchUserStories(ArrayList<Project> projectList, Connection connector) {

		String fetchUS = "SELECT idtask, taskName, storypoint_name,storypoint_content,storypoint_weight,storypoint_estimation,iduser_storypoints FROM tasks_data INNER JOIN user_storypoints ON tasks_data.idtask = user_storypoints.taskID";
		try {
			Statement statement = connector.createStatement();
			ResultSet results = statement.executeQuery(fetchUS);
			while (results.next()) {
				for (int i = 0; i < projectList.size(); i++) {
					for (int j = 0; j < projectList.get(i).tasks.size(); j++) {
						if (projectList.get(i).tasks.get(j).taskName.equals(results.getString(2))) {
							projectList.get(i).tasks.get(j).userStories.add(new UserStory(results.getString(3),
									results.getString(4), results.getInt(5), results.getInt(6), results.getInt(7)));
						}
					}

				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void setConnection(DatabaseConnection connection) {
		this.connection = connection;
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

	public void onFavButtonPressed(ActionEvent e) {
		String selectedItemText = userStoryListView.getSelectionModel().getSelectedItem();
		TreeItem<String> task = treeviewer.getSelectionModel().getSelectedItem();
		TreeItem<String> project = treeviewer.getSelectionModel().getSelectedItem().getParent().getParent();
		Task selection = findTask(findProject(projectList, project.getValue()).tasks, task.getValue());
		for (UserStory i : selection.userStories) {
			if (i.userStoryName.equals(selectedItemText) && !currentUser.favoriteUserStories.contains(i)) {
				currentUser.favoriteUserStories.add(i);
				System.out.println("FAVORITED: " + i.userStoryName);
			} else {
				System.out.println("NOT FAVORITED: " + i.userStoryName);
			}
		}

	}

	// CUSTOMIZED UI SETTINGS -- DRAG TAB -- CLOSE BUTTON -- ADD MORE UI FEATURES
	// UNDER THIS COMMENT IF NEEDED
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
		exitSession();
		Stage stage = (Stage) closebutton.getScene().getWindow();
		stage.close();
	}

}
