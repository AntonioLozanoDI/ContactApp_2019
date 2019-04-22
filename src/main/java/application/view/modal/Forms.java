package application.view.modal;

import java.net.URL;

import javafx.stage.Modality;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;;

public enum Forms {//TODO refactorizar para aplicar interfaz
	PERSONEDITFORM(Modality.WINDOW_MODAL, Constants.Files.FXML.personEditForm);

	private Forms(Modality modality, String fxml) {
		this.modality = modality;
		this.fxml = ApplicationResourceProvider.getFXMLFile(fxml).toURL();
	}

	private Modality modality;
	private URL fxml;

	public Modality getModality() {
		return modality;
	}

	public URL getFxml() {
		return fxml;
	}

	@Override
	public String toString() {
		return String.format("\tFormName: %s%n\tModality: %s%n\tFileLocation: %s",name(), modality, fxml);
	}
}
