package application.model.dao;

import java.util.List;
import java.util.logging.Logger;

import application.model.Person;
import application.model.repository.PersonRepository;
import utils.logging.ApplicationLoggers;

public class PersonDAO {
	
	private Logger logger = ApplicationLoggers.modelLogger;

	private static PersonDAO instance;

	private PersonRepository personRepository;
	
	public static PersonDAO getInstance() {
		return instance == null ? instance = new PersonDAO() : instance; 
	}
	
	private PersonDAO() {
		personRepository = new PersonRepository();
		logger.finer(String.format("%s successfully initialized.", getClass().getSimpleName()));
	}

	public void savePerson(Person person) {
		personRepository.insert(person);
		person.setId(personRepository.findLastPersonId());
		logger.finer(String.format("Saved person: %s", Person.nombreCompleto(person)));
	}
	
	public void updatePerson(Person person) {
		personRepository.update(person);
		logger.finer(String.format("Updated person: %s", Person.nombreCompleto(person)));
	}
	
	public void deletePerson(Person person) {
		personRepository.delete(person);
		logger.finer(String.format("Deleted person: %s", Person.nombreCompleto(person)));
	}
	
	public List<Person> findAll(){
		List<Person> persons = personRepository.findAll();
		logger.finer(String.format("Retrieved data from %d person(s)",persons.size()));
		return persons;
	}
}
