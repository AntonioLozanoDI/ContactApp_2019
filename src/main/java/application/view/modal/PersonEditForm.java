package application.view.modal;

import java.io.IOException;
import java.net.URL;

import com.sp.dialogs.DialogBuilder;

import application.controller.PersonEditFormController;
import application.model.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.logging.LoggingUtils;

public class PersonEditForm extends ApplicationModal {// TODO refactorizar

	private Forms form = Forms.PERSONEDITFORM;
	private PersonEditFormController controller;
	private Stage formStage;

	public PersonEditForm() {}

	@Override
	protected void buildModal(Stage owner) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			URL resource = form.getFxml();
			loader.setLocation(resource);
			AnchorPane page = loader.load();

			// Create the dialog Stage.
			formStage = new Stage();
			formStage.initModality(form.getModality());
			formStage.initOwner(owner);
			Scene scene = new Scene(page);
			formStage.setScene(scene);

			// Set the stage into the controller.
			controller = (PersonEditFormController) loader.getController();
			controller.setFormStage(formStage);

			logger.finer(String.format("%s successfully loaded. Resource loaded: %s", getClass().getSimpleName(), LoggingUtils.cleanFXMLPath(resource.getPath())));

		} catch (IOException e) {
			DialogBuilder.error()
			.title(String.format("FXML view load error", e.getClass().getSimpleName()))
			.header(String.format("An error ocurred while attempting to load \'.fxml\' form file: %n%s", form))
			.content(e.getLocalizedMessage())
			.finish().alert().showAndWait();

			logger.severe(String.format("FXMLLoader couldn\'t load view from %s", form.getFxml().toExternalForm()));
		}
	}

	@Override
	public void showView() {
		// Show the dialog and wait until the user closes it
		formStage.showAndWait();
	}

	public PersonEditFormController controller() {
		return controller;
	}

	public void setPerson(Person person) {
		controller.setPerson(person);
	}

	public void setTitle(String title) {
		formStage.setTitle(title);
	}

	public void setIcon(Image image) {
		formStage.getIcons().add(image);
	}
}
