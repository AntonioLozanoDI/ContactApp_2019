package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import application.controller.PersonOverviewController;
import application.controller.RootLayoutController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.constants.Constants;
import utils.logging.LoggingUtils;
import utils.resources.ApplicationResourceProvider;

public class ContactAppView extends ApplicationView {

	private FXMLLoader rootLoader;
	private BorderPane rootLayout;
	private RootLayoutController rootController;

	private FXMLLoader detailsLoader;
	private AnchorPane detailsLayout;
	private PersonOverviewController detailsController;

	private Scene scene;
	private Stage primaryStage;

	/*
	 * Es necesario dejar un constructor por defecto visible, para que se pueda
	 * invocar en el metodo <T extends ApplicationView> T launchView(Class<T>
	 * viewClass, Stage primaryStage) la creacion de una instancia de la vista
	 * deseada T view = viewClass.newInstance() en el tipo ApplicationView.
	 */
	public ContactAppView() {}

	@Override
	protected void buildParentView() {
		URL resource = null;
		try {
			rootLoader = new FXMLLoader();
			resource = ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.rootLayout).toURL();
			rootLoader.setLocation(resource);
			rootLayout = (BorderPane) rootLoader.load();
			rootController = (RootLayoutController) rootLoader.getController();

			String trace = String.format("%s parent view successfully loaded. Resource loaded: %s",
					getClass().getSimpleName(), LoggingUtils.cleanFXMLPath(resource.getPath()));
			logger.finer(trace);
		} catch (IOException e) {
			String trace = String.format(
					"An error ocurred during %s parent view initialization.%n\t- resource: %s%n\t- loader: %s%n\t- layout: %s%n\t- controller: %s",
					getClass().getSimpleName(), resource, rootLoader, rootLayout, rootController);
			logger.log(Level.SEVERE, trace, e);
		}
	}

	@Override
	protected void buildChildrenViews() {
		URL resource = null;
		try {
			detailsLoader = new FXMLLoader();
			resource = ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.personOverview).toURL();
			detailsLoader.setLocation(resource);
			detailsLayout = (AnchorPane) detailsLoader.load();
			detailsController = (PersonOverviewController) detailsLoader.getController();

			String trace = String.format("%s child view successfully loaded. Resource loaded: %s",
					getClass().getSimpleName(), LoggingUtils.cleanFXMLPath(resource.getPath()));
			logger.finer(trace );
		} catch (IOException e) {
			String trace = String.format(
					"An error ocurred during %s child view initialization.%n\t- resource: %s%n\t- loader: %s%n\t- layout: %s%n\t- controller: %s",
					getClass().getSimpleName(), resource, detailsLoader, detailsLayout, detailsController);
			logger.log(Level.SEVERE, trace, e);
		}
	}

	@Override
	protected void setScene(Stage stage) {
		primaryStage = stage;
		rootController.setPersonOverviewController(detailsController);
		rootController.setPrimaryStage(stage);
		detailsController.setPrimaryStage(stage);
		rootLayout.setCenter(detailsLayout);
		scene = new Scene(rootLayout);
		scene.getStylesheets().add(ApplicationResourceProvider.getCSSFile(Constants.Files.CSS.darkTheme).toURL().toExternalForm());
		primaryStage.setTitle(Constants.Views.applicationTitle);
		primaryStage.getIcons().add(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.applicationIcon3).toImage());
		primaryStage.setScene(scene);
	}

}
