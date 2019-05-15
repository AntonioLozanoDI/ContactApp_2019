package application.view.modal;

import java.io.IOException;
import java.util.List;

import application.controller.BirthdayStatisticsController;
import application.model.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class BirthdayStatisticsWindow extends ApplicationModal {

	private BirthdayStatisticsController controller;
	private Stage windowStage;

	public BirthdayStatisticsWindow() {}

	@Override
	protected void buildModal(Stage owner) {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.birthdayStatistics).toURL());
			AnchorPane page = (AnchorPane) loader.load();
			windowStage = new Stage();
			windowStage.setTitle("Birthday Statistics");
			windowStage.initModality(Modality.WINDOW_MODAL);
			windowStage.initOwner(owner);
			Scene scene = new Scene(page);
			scene.getStylesheets().add(ApplicationResourceProvider.getCSSFile(Constants.Files.CSS.darkTheme).toURL().toExternalForm());
			windowStage.setScene(scene);

			// Set the persons into the controller.
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPersonData(List<Person> personData) {
		controller.setPersonData(personData);
	}

	@Override
	public void showView() {
		windowStage.show();
	}
}
