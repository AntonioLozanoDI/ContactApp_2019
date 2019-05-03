package application.controller;


import java.util.Optional;
import java.util.logging.Logger;

import com.sp.dialogs.DialogBuilder;

import application.model.Person;
import application.model.dao.PersonDAO;
import application.view.modal.ApplicationModal;
import application.view.modal.BirthdayStatisticsWindow;
import application.view.modal.PersonEditForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.DateUtil;
import utils.constants.Constants;
import utils.logging.ApplicationLoggers;
import utils.resources.ApplicationResourceProvider;

public class PersonOverviewController {

	private Logger logger = ApplicationLoggers.controllerLogger;
	
	@FXML
    private TableView<Person> personTV;
    @FXML
    private TableColumn<Person, String> nombreTC;
    @FXML
    private TableColumn<Person, String> primerApellidoTC;
    @FXML
    private TableColumn<Person, String> segundoApellidoTC;

    @FXML
    private Label nombreLb;
    @FXML
    private Label primerApellidoLb;
    @FXML
    private Label segundoApellidoLb;
    @FXML
    private Label calleLb;
    @FXML
    private Label ciudadLb;
    @FXML
    private Label codigoPostalLb;
    @FXML
    private Label fechaNacimientoLb;
    
    /**
	 * The data as an observable list of Persons.
	 */
	private ObservableList<Person> personData = FXCollections.<Person>observableArrayList();
	
	private PersonDAO personDAO;
	
	private Stage primaryStage;
	
	public PersonOverviewController() {}
	
	/*
	 * A este metodo se le invoca despues de haber cargado el fichero fxml
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
        nombreTC.setCellValueFactory(
                cellData -> cellData.getValue().getNombreProperty());
        primerApellidoTC.setCellValueFactory(
                cellData -> cellData.getValue().getApellido1Property());
        segundoApellidoTC.setCellValueFactory(
                cellData -> cellData.getValue().getApellido2Property());
       
        logger.finest("Configured table columns.");
        
        // Clear person details.
        showPersonDetails(null);

        personTV.addEventHandler(MouseEvent.ANY, (event) -> {
        	if (event.getButton().equals(MouseButton.SECONDARY)) 
        		personTV.getSelectionModel().clearSelection();
        	});
        
        // Listen for selection changes and show the person details when changed (including clear selection).
        personTV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> { 
                	showPersonDetails(newValue);
                	if(newValue != null) logger.finest(String.format("Person selected: %s", Person.nombreCompleto(newValue)));
                });
        
		personDAO = PersonDAO.getInstance();
		personData.addAll(personDAO.findAll());
		personTV.setItems(personData);
		personTV.getSelectionModel().clearSelection();
		
		logger.finest(String.format("%s successfully initialized.", getClass().getSimpleName()));
	}
	

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@FXML
	public void newPerson() {
		PersonEditForm personEditForm = ApplicationModal.build(PersonEditForm.class, null);
		personEditForm.setTitle(Constants.Views.personEditFormTitleNewPerson);
		personEditForm.setIcon(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.applicationIcon3New).toImage());
		personEditForm.showView();
		PersonEditFormController controller = personEditForm.controller();
		if (controller.isOkClicked()) {
			Person created = controller.getValidatedPerson();
			personDAO.savePerson(created);
			personData.add(created);
			
			logger.finer("New person created successfully.");
			
			showPersonDetails(created);
		}
	}

	@FXML
	public void editPerson() {
		if(personTV.getSelectionModel().getSelectedIndex() > -1) {
			Person person = personTV.getSelectionModel().getSelectedItem();
			PersonEditForm personEditForm = ApplicationModal.build(PersonEditForm.class, null);
			personEditForm.setTitle(Constants.Views.personEditFormTitleEditPerson);
			personEditForm.setIcon(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.applicationIcon3Edit).toImage());
			personEditForm.setPerson(person);
			personEditForm.showView();
			PersonEditFormController controller = personEditForm.controller();
			if (controller.isOkClicked()) {
				Person edited = controller.getValidatedPerson();
				personDAO.updatePerson(edited);

				logger.finer("Person updated successfully.");
				
				showPersonDetails(edited);
			}
		}
	}

	@FXML
	public void deletePerson() {
		if(personTV.getSelectionModel().getSelectedIndex() > -1) {
			Person person = personTV.getSelectionModel().getSelectedItem();
			Optional<ButtonType> btn = DialogBuilder.confirmation()
			.title(Constants.Views.personEditFormTitleDeletePerson)
			.header("Selected person will be deleted: \n" + Person.nombreCompleto(person) + ".")
			.finish()
			.icon(ApplicationResourceProvider.getCSSFile(Constants.Files.Images.applicationIcon3Delete).toImage())
			.alert().showAndWait();
			if(btn.isPresent() && btn.get().equals(ButtonType.OK)) {
				personDAO.deletePerson(person);
				personData.remove(person);

				logger.finer("Person deleted successfully.");
			}
			
		}
	}
	
	/**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
        	nombreLb.setText(person.getNombre());
        	primerApellidoLb.setText(person.getApellido1());
        	segundoApellidoLb.setText(person.getApellido2());
        	calleLb.setText(person.getCalle());
        	ciudadLb.setText(person.getCiudad());
        	codigoPostalLb.setText(Integer.toString(person.getCodigoPostal()));
        	fechaNacimientoLb.setText(DateUtil.format(person.getFechaNacimiento()));
        } else {
            // Person is null, remove all the text.
            nombreLb.setText("");
            primerApellidoLb.setText("");
            segundoApellidoLb.setText("");
            calleLb.setText("");
            ciudadLb.setText("");
            codigoPostalLb.setText("");
            fechaNacimientoLb.setText("");
        }
    }

	public void showStatistics() {
		BirthdayStatisticsWindow statisticsWindow = ApplicationModal.build(BirthdayStatisticsWindow.class, primaryStage);
		statisticsWindow.setPersonData(personData);
		statisticsWindow.showView();
	}
}
