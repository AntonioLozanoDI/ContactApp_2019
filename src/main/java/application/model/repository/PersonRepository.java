package application.model.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import application.model.Person;
import utils.DateUtil;
import utils.database.DatabaseHelper;
import utils.database.SQLiteConnectionManager;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public class PersonRepository {

	private Logger logger = ApplicationLoggers.modelLogger;

	private final String insertStmt = "INSERT INTO persona (nombre,apellido1,apellido2,calle,codigo_postal,ciudad,fecha_nacimiento) VALUES (?,?,?,?,?,?,?);";

	private final String deleteStmt = "DELETE FROM persona WHERE nombre = ? AND apellido1 = ? AND apellido2 = ? AND calle = ? AND codigo_postal = ? AND ciudad = ? AND fecha_nacimiento = ? AND id = ?;";

	private final String selectAllStmt = "SELECT * FROM persona;";

	private final String lastIdStmt = "SELECT seq FROM sqlite_sequence WHERE name = ?;";

	private final String updateStmt = "UPDATE persona SET nombre = ?, apellido1 = ?, apellido2 = ?, calle = ?, codigo_postal = ?, ciudad = ?, fecha_nacimiento = ? WHERE id = ?;";

	private Connection con = null;

	public PersonRepository() {
		try {
			SQLiteConnectionManager man = new SQLiteConnectionManager(DatabaseHelper.getPropertiesToLoad());
			con = man.getConnection();
			logger.finer(String.format("%s successfully initialized.", getClass().getSimpleName()));
		} catch (IOException e) {
			String trace = String.format(
					"An error ocurred in %s during initialization. Unexpected error trying to initialize %s.%n%s",
					getClass().getSimpleName(), SQLiteConnectionManager.class.getSimpleName(),
					LoggingUtils.getStackTrace(e));
			logger.severe(trace);
			logger.severe("Exiting from application.");
			System.exit(-1);
		}
	}

	public List<Person> findAll() {
		List<Person> persons = null;
		try {
			PreparedStatement st = con.prepareStatement(selectAllStmt);
			ResultSet rs = st.executeQuery();
			persons = new ArrayList<>();
			while (rs.next()) {
				Person person = new Person();
				person.setId(rs.getInt(1));
				person.setNombre(rs.getString(2));
				person.setApellido1(rs.getString(3));
				person.setApellido2(rs.getString(4));
				person.setCalle(rs.getString(5));
				person.setCodigoPostal(rs.getInt(6));
				person.setCiudad(rs.getString(7));
				person.setFechaNacimiento(DateUtil.parse(rs.getString(8)));
				persons.add(person);
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into Persona table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return persons == null ? Collections.emptyList() : persons;
	}

	public int findLastPersonId() {
		int id = -1;
		try {
			PreparedStatement st = con.prepareStatement(lastIdStmt);
			st.setString(1, "persona");
			ResultSet rs = st.executeQuery();
			id = rs.getInt(1);
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement to sqlite_sequence table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return id;
	}

	public void insert(Person person) {
		try {
			PreparedStatement st = con.prepareStatement(insertStmt);
			st.setString(1, person.getNombre());
			st.setString(2, person.getApellido1());
			st.setString(3, person.getApellido2());
			st.setString(4, person.getCalle());
			st.setInt(5, person.getCodigoPostal());
			st.setString(6, person.getCiudad());
			st.setString(7, DateUtil.format(person.getFechaNacimiento()));
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute INSERT statement into Persona table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void delete(Person person) {
		try {
			PreparedStatement st = con.prepareStatement(deleteStmt);
			st.setString(1, person.getNombre());
			st.setString(2, person.getApellido1());
			st.setString(3, person.getApellido2());
			st.setString(4, person.getCalle());
			st.setInt(5, person.getCodigoPostal());
			st.setString(6, person.getCiudad());
			st.setString(7, DateUtil.format(person.getFechaNacimiento()));
			st.setInt(8, person.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute DELETE statement into Persona table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void update(Person person) {
		try {
			PreparedStatement st = con.prepareStatement(updateStmt);
			st.setString(1, person.getNombre());
			st.setString(2, person.getApellido1());
			st.setString(3, person.getApellido2());
			st.setString(4, person.getCalle());
			st.setInt(5, person.getCodigoPostal());
			st.setString(6, person.getCiudad());
			st.setString(7, DateUtil.format(person.getFechaNacimiento()));
			st.setInt(8, person.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute UPDATE statement into Persona table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
}
