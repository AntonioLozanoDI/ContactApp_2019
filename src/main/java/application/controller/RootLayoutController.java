package application.controller;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import utils.logging.ApplicationLoggers;

public class RootLayoutController {

	private Logger logger = ApplicationLoggers.controllerLogger;
	
	private PersonOverviewController personOverviewController;

	@FXML
	private MenuBar menuBar;

	private Stage primaryStage;
	
	public RootLayoutController() {}
	
	@FXML
	private void initialize() {
		logger.finest(String.format("%s successfully initialized.", getClass().getSimpleName()));
	}

	public void setPersonOverviewController(PersonOverviewController personOverviewController) {
		this.personOverviewController = personOverviewController;
	}
	
	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}

	public void newPerson() {
		personOverviewController.newPerson();
	}
	
	public void editPerson() {
		personOverviewController.editPerson();
	}
	
	public void deletePerson() {
		personOverviewController.deletePerson();
	}

	public void showStatistics() {
		personOverviewController.showStatistics();
	}
	
	@FXML
	public void setFullScreen() {
		primaryStage.setFullScreen(true);
	}

	@FXML
	public void maximize() {
		primaryStage.setFullScreen(false);
		primaryStage.setMaximized(true);
	}
}
