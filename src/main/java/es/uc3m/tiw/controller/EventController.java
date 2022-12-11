package es.uc3m.tiw.controller;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestController;

import es.uc3m.tiw.entity.*;
import es.uc3m.tiw.repository.EventDAO;

@Controller
@CrossOrigin
public class EventController {

	@Autowired
	EventDAO daous;


	// ----------------- GET ALL EVENTS / FILTRO ----------------------
	@RequestMapping(value="/events", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Event>> getEvents(){
		List<Event> eventList;
		eventList = daous.findAll();
		return new ResponseEntity<>(eventList, HttpStatus.OK);
	}

	// ----------------- GET EVENT ----------------------
	@RequestMapping(value="/events/{id}",  method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Event> getEventByName(@PathVariable Long id){
		 Event ev = daous.findById(id).orElse(null);
		 if (ev == null){
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }else{
			 return new ResponseEntity<>(ev, HttpStatus.OK);
		 }
	}

	@PostMapping("/events/filter")
	public ResponseEntity<List<Event>> filterEvent(@RequestBody Event pevent){
		List<Event> eventList;
		System.out.println(pevent.getName());
		eventList = daous.findByNameOrCategoryOrCityOrDateOrHall(pevent.getName(), pevent.getCategory(), pevent.getCity(), pevent.getDate(), pevent.getHall());
		return new ResponseEntity<>(eventList, HttpStatus.OK);
	}

	// ----------------- SAVE EVENT ----------------------
	@PostMapping("/events")
	public ResponseEntity<Event> saveEvent(@RequestBody Event pevent){

		ResponseEntity<Event> response;
		Event newEvent = daous.save(pevent);
		if (newEvent == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<>(newEvent, HttpStatus.CREATED);
		}
		return response;
	}

	// ----------------- UPDATE EVENT ----------------------
	@RequestMapping(value="/events/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Event> updateEvent(@PathVariable @Validated Long id, @RequestBody Event pEvent) {
		ResponseEntity<Event> response;
		Event ev = daous.findById(id).orElse(null);
		if (ev == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (pEvent.getName().equals("") == false) {
				ev.setName(pEvent.getName());
			}
			if (pEvent.getCategory().equals("") == false) {
				ev.setCategory(pEvent.getCategory());
			}
			if (pEvent.getDate().equals("") == false) {
				ev.setDate(pEvent.getDate());
			}
			if (pEvent.getCity().equals("") == false) {
				ev.setCity(pEvent.getCity());
			}
			if (pEvent.getHall().equals("") == false) {
				ev.setHall(pEvent.getHall());
			}
			if (pEvent.getImage().equals("") == false) {
				ev.setImage(pEvent.getImage());
			}
			response = new ResponseEntity<>(daous.save(ev), HttpStatus.OK);
		}
		return response;
	}

	// ----------------- DELETE EVENT ----------------------
	@RequestMapping(value="/events/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Event> deleteEvent(@PathVariable @Validated Long id) {
		ResponseEntity<Event> response;
		Event ev = daous.findById(id).orElse(null);
		if (ev == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daous.delete(ev);
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		return response;
	
	}
	
}
	

