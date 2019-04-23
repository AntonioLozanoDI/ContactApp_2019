package application.controller;

import java.util.logging.Logger;

import com.sp.dialogs.DialogBuilder;
import com.sp.fxutils.validation.FXUtils;
import com.sp.validable.ValidableObject;

import application.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DateUtil;
import utils.logging.ApplicationLoggers;

public class PersonEditFormController extends ValidableObject<PersonEditFormController.Validator> {

	private Logger logger = ApplicationLoggers.controllerLogger;
	
    @FXML
    private TextField nombreTF;
    @FXML
    private TextField apellido1TF;
    @FXML
    private TextField apellido2TF;
    @FXML
    private TextField calleTF;
    @FXML
    private TextField codigoPostalTF;
    @FXML
    private TextField ciudadTF;
    @FXML
    private TextField fechaNacimientoTF;

    private Stage dialogStage;
    
    private Person person;
    
	private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	logger.finest(String.format("%s successfully initialized.", getClass().getSimpleName()));
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setFormStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        nombreTF.setText(person.getNombre());
        apellido1TF.setText(person.getApellido1());
        apellido2TF.setText(person.getApellido2());
        calleTF.setText(person.getCalle());
        codigoPostalTF.setText(Integer.toString(person.getCodigoPostal()));
        ciudadTF.setText(person.getCiudad());
        fechaNacimientoTF.setText(DateUtil.format(person.getFechaNacimiento()));
        fechaNacimientoTF.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
    	logger.fine("Accept button pressed. Executing form validation...");
    	executeValidation();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
    	logger.fine("Cancel button pressed. Closing form...");
        dialogStage.close();
    }
    
    class Validator extends ValidableObject<Validator>.AbstractValidator {

		public Validator(ValidableObject<?> validable) {
			super(validable);
		}

		public void validateNombreTF() {
			boolean valid = FXUtils.textfieldTextIsNotNullOrEmpty(nombreTF);
			builder.addValidationStatement("Text validation for nombreTF", valid);
		}
		
		public void validateApellido1TF() {
			boolean valid = FXUtils.textfieldTextIsNotNullOrEmpty(apellido1TF);
			builder.addValidationStatement("Text validation for apellido1TF", valid);
		}
		
		public void validateApellido2TF() {
			boolean valid = FXUtils.textfieldTextIsNotNullOrEmpty(apellido2TF);
			builder.addValidationStatement("Text validation for apellido2TF", valid);
		}
		
		public void validateCalleTF() {
			boolean valid = FXUtils.textfieldTextIsNotNullOrEmpty(calleTF);
			builder.addValidationStatement("Text validation for calleTF", valid);
		}
		
		public void validateCodigoPostalTF() {
			boolean valid = FXUtils.textfieldTextIsNotNullOrEmpty(codigoPostalTF);
			builder.addValidationStatement("Text validation for codigoPostalTF", valid);
			if(valid) {
				try {
		            Integer.parseInt(codigoPostalTF.getText());
		        } catch (NumberFormatException e) {
		        	valid = false;
		        }
				builder.addValidationStatement("CodigoPostal format validation", valid);
			}
		}
		
		public void validateCiudadTF() {
			boolean valid = FXUtils.textfieldTextIsNotNullOrEmpty(ciudadTF);
			builder.addValidationStatement("Text validation for ciudadTF", valid);
		}
		
		public void validateFechaNacimientoTF() {
			boolean valid = FXUtils.textfieldTextIsNotNullOrEmpty(fechaNacimientoTF);
			builder.addValidationStatement("Text validation for fechaNacimientoTF", valid);
			if(valid) {
				builder.addValidationStatement("FechaNacimiento format validation", DateUtil.validDate(fechaNacimientoTF.getText()));;
			}
		}
    }

	@Override
	protected PersonEditFormController.Validator validator() {
		return new PersonEditFormController.Validator(this);
	}

	@Override
	protected void onSuccessValidation() {
		if(person == null) // solo es verdadero en el caso de crear nuevo contacto
			person = new Person();
		
		person.setNombre(nombreTF.getText());
        person.setApellido1(apellido1TF.getText());
        person.setApellido2(apellido2TF.getText());
        person.setCalle(calleTF.getText());
        person.setCodigoPostal(Integer.parseInt(codigoPostalTF.getText()));
        person.setCiudad(ciudadTF.getText());
        person.setFechaNacimiento(DateUtil.parse(fechaNacimientoTF.getText()));
        
        logger.fine(String.format("Successfully validation for person %s. Closing form...", Person.nombreCompleto(person)));
        
        okClicked = true;
        dialogStage.close();
	}

	@Override
	protected void onFailedValidation() {
		DialogBuilder.error()
    	.title("Failed Validation")
    	.header("Please correct invalid fields")
    	.content(validator.response().getFormattedResponse())
    	.finish().alert().showAndWait();
		
		if(person != null) logger.fine(String.format("Failed validation for person %s.", Person.nombreCompleto(person)));
	}

	@Override
	protected void validateObject() {
		validator.validateNombreTF();
		validator.validateApellido1TF();
		validator.validateApellido2TF();
		validator.validateCalleTF();
		validator.validateCodigoPostalTF();
		validator.validateCiudadTF();
		validator.validateFechaNacimientoTF();
	}

	public Person getValidatedPerson() {
		return person;
	}	
}