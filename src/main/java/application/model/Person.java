package application.model;

import java.time.LocalDate;

import com.sp.tostringformatter.ToStringFormatter;
import com.sp.tostringformatter.annotations.ToStringField;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	
	@ToStringField
	private int id;
	@ToStringField
	private final StringProperty nombre;
	@ToStringField
    private final StringProperty apellido1;
	@ToStringField
    private final StringProperty apellido2;
	@ToStringField
    private final StringProperty calle;
	@ToStringField
    private final IntegerProperty codigoPostal;
	@ToStringField
    private final StringProperty ciudad;
	@ToStringField
    private final ObjectProperty<LocalDate> fechaNacimiento;

    /**
     * Default constructor.
     */
    public Person() {
        this(-1, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Person(int id, String nombre, String apellido1, String apellido2) {
    	this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido1 = new SimpleStringProperty(apellido1);
        this.apellido2 = new SimpleStringProperty(apellido2);

        // Some initial dummy data, just for convenient testing.
        this.calle = new SimpleStringProperty();
        this.codigoPostal = new SimpleIntegerProperty();
        this.ciudad = new SimpleStringProperty();
        this.fechaNacimiento = new SimpleObjectProperty<LocalDate>();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public void setApellido1(String apellido1) {
		this.apellido1.set(apellido1);
	}

	public void setApellido2(String apellido2) {
		this.apellido2.set(apellido2);
	}

	public void setCalle(String calle) {
		this.calle.set(calle);
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal.set(codigoPostal);
	}

	public void setCiudad(String ciudad) {
		this.ciudad.set(ciudad);
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento.set(fechaNacimiento);
	}
	
	
	
	public String getNombre() {
		return nombre.get();
	}

	public String getApellido1() {
		return apellido1.get();
	}

	public String getApellido2() {
		return apellido2.get();
	}

	public String getCalle() {
		return calle.get();
	}

	public Integer getCodigoPostal() {
		return codigoPostal.get();
	}

	public String getCiudad() {
		return ciudad.get();
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento.get();
	}
	
	
	
	public StringProperty getNombreProperty() {
		return nombre;
	}

	public StringProperty getApellido1Property() {
		return apellido1;
	}

	public StringProperty getApellido2Property() {
		return apellido2;
	}

	public StringProperty getCalleProperty() {
		return calle;
	}

	public IntegerProperty getCodigoPostalProperty() {
		return codigoPostal;
	}

	public StringProperty getCiudadProperty() {
		return ciudad;
	}

	public ObjectProperty<LocalDate> getFechaNacimientoProperty() {
		return fechaNacimiento;
	}

	
	private static String formatString(String s) {
		return s == null ? "" : s;
	}
	
	public static String nombreCompleto(Person person) {
		String nombreStr = formatString(person.getNombre());
		String apellido1Str = formatString(person.getApellido1());
		String apellido2Str = formatString(person.getApellido2());
		return String.format("%s %s %s",nombreStr,apellido1Str,apellido2Str);
	}

	@Override
	public String toString() {
		return ToStringFormatter.buildToStringFor(this);
	}
}
