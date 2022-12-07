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


	// ----------------- GET ALL EVENTS ----------------------
	@RequestMapping(value="/events", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Event>> getEvents(@RequestParam(value="name",required=false) String name){
		List<Event> eventList;
		if (name == null) {
			eventList = daous.findAll();
		} else {
			eventList = daous.findByCategory(name);
		}
		return new ResponseEntity<>(eventList, HttpStatus.OK);
	}


	// ----------------- GET EVENT ----------------------
	@RequestMapping(value="/events/{name}",  method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Event> getEventByName(@PathVariable String name){
		//return daous.findByName(name);
		 Event ev = daous.findByName(name);
		 System.out.println(ev.getName());
		 return new ResponseEntity<>(ev, HttpStatus.OK);
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
	
	//???????????????
	/*
	@RequestMapping(method = RequestMethod.POST, value = "/userbis")
	public @ResponseBody User saveUser2(@RequestBody @Validated User puser){
		return daous.save(puser);
	}*/

	// ----------------- UPDATE EVENT ----------------------
	@RequestMapping(value="/events/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Event> updateEvent(@PathVariable @Validated Long id, @RequestBody Event pEvent) {
		ResponseEntity<Event> response;
		//Optional<User> us = daous.findById(id);
		Event ev = daous.findById(id).orElse(null);
		if (ev == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			ev.setName(pEvent.getName());
			ev.setCategory(pEvent.getCategory());
			ev.setDate(pEvent.getDate());
			ev.setCity(pEvent.getCity());
			ev.setHall(pEvent.getHall());
			ev.setImage(pEvent.getImage());
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
	

