package es.uc3m.tiw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RestResource;

import es.uc3m.tiw.entity.Event;
import org.springframework.data.repository.query.Param;


public interface EventDAO extends CrudRepository<Event, Long>{

	public List<Event> findAll();

	public List<Event> findByCategory(String name);
	
	public Event findByName(String name);

	public List<Event> findByNameOrCategoryOrCityOrDateOrHall(String name,String category ,String city,String date,String hall);


}
