package com.example.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Guest;
import com.example.repository.GuestRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class GuestController {
	
	
	
	@Autowired
	private GuestRepository guestRepository ;
	
	//get all guest rest api
	
	@GetMapping("/allguests")
	public List <Guest> getAllGuests(){
		return guestRepository.findAll();
	}

	//create guest rest api
	@PostMapping("/guests")
	public Guest createGuest(@RequestBody Guest guest) {
		return guestRepository.save(guest);
	}
		
	// get employee by id rest api (read)
	
	@GetMapping("/guests/{id}")
		public ResponseEntity<Guest> getGuestById(@PathVariable int id) {
			Guest guest = guestRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Guest not exist with id :" + id));
			return ResponseEntity.ok(guest);
		}
		
	// update employee rest api
	
		@PutMapping("/guests/{id}")
		public ResponseEntity<Guest> updateGuest(@PathVariable int id, @RequestBody Guest guestDetails){
			Guest guest = guestRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Guest not exist with id :" + id));
			
			guest.setFirstName(guestDetails.getFirstName());
			guest.setLastName(guestDetails.getLastName());
			guest.setEmail(guestDetails.getEmail());
			guest.setAddress(guestDetails.getAddress());
			
			Guest updatedGuest = guestRepository.save(guest);
			return ResponseEntity.ok(updatedGuest);
		}
		// delete employee rest api
		@DeleteMapping("/guests/{id}")
		
		public String deleteGuest(@PathVariable int id)
		{
			guestRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Guest not found"));
			System.out.println();
		    guestRepository.deleteById(id);
		    return "The guest with id: "+ id +" is removed from the database.";
		}

	}
	
