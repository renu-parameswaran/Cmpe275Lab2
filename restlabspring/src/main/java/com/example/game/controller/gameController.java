package com.example.game.controller;

import com.example.game.model.sponsor;
import com.example.game.model.player;
import com.example.game.repository.gameRepository;
import com.example.game.service.playerService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * RestController annotation is used to map the class as RestFul web service controller.
 * RequestMapping annotation is used to map the base URI.
 */
@Transactional
@RestController
@RequestMapping("/api")
public class gameController extends Throwable  {

    /**
     * This Autowire the sponsor service interface to serve HTTP request of Sponsor URI.
     */
	@Autowired
    gameRepository gameRepository;
	
	@Autowired
    private playerService playerService;

   /**
     * Method to get a sponsor using HTTP URI as /sponsor/{id}
     * HTTP REQUEST - GET
     */
    @GetMapping("/sponsor/{id}")
    public ResponseEntity<sponsor> getSponsorById(@PathVariable(value = "id") Long sponsorId) {
        sponsor sponsor = gameRepository.findOne(sponsorId);
        if(sponsor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sponsor);
    }

    /**
     * Method to create a sponsor using HTTP URI as /sponsor?
     * HTTP REQUEST - POST
     */
    
    @PostMapping("/sponsor")
    @ResponseBody
    public sponsor createSponsor(@Valid
    			@RequestParam(value= "name", required = true) String name,
    			@RequestParam(value= "description", required = false)String description,
    			@RequestParam(value= "street", required = false)String street,
    			@RequestParam(value= "city", required = false)String city,
    			@RequestParam(value= "state", required = false)String state,
    			@RequestParam(value= "zip", required = false)String zip) throws IOException
    {
    		sponsor sponsor = new sponsor(name,description,street,city,state,zip);
        return gameRepository.save(sponsor);
    }
    
    /**
     * Method to Update Sponsor using HTTP URI as /sponsor/{id}?
     */
    @PostMapping("/sponsor/{id}")
    public ResponseEntity<sponsor> updateSponsor(@PathVariable(value = "id") Long sponsorId,@Valid
    			@RequestParam(value= "name", required = true) String name,
			@RequestParam(value= "description", required = false)String description,
			@RequestParam(value= "street", required = false)String street,
			@RequestParam(value= "city", required = false)String city,
			@RequestParam(value= "state", required = false)String state,
			@RequestParam(value= "zip", required = false)String zip) throws IOException
    		{
    			sponsor sponsor = gameRepository.findOne(sponsorId);
        if(sponsor == null) {
            return ResponseEntity.notFound().build();
        }
        if(name != null)
        		sponsor.setName(name);
        
        if(description != null)
        		sponsor.setDescription(description);
        
        if(street != null)
        		sponsor.setStreet(street);
        
        if(city != null)
        		sponsor.setCity(city);
        
        if(state != null)
        		sponsor.setState(state);
        
        if(zip != null)
        		sponsor.setZip(zip);

        sponsor updatedsponsor = gameRepository.save(sponsor);
        return ResponseEntity.ok(updatedsponsor);
       
    }

   /**
     * Method to Delete Sponsor using HTTP URI as /sponsor/{id}
     */
    @DeleteMapping("/sponsor/{id}")
    public ResponseEntity deleteSponsor(@PathVariable(value = "id") Long sponsorId) {
    	sponsor sponsor = gameRepository.findOne(sponsorId);
    	List<player> allPlayers;
        if(sponsor == null) {
        	return ResponseEntity.notFound().build();
        }
        allPlayers = playerService.findAll();
        for(int i=0;i<allPlayers.size();i++)
        {
        	if(allPlayers.get(i).getSponsor().getId()==sponsorId)
        	{
        		 return ResponseEntity.badRequest().build();
        	}
        }
        
        gameRepository.delete(sponsor);
        return ResponseEntity.ok().body(sponsor);
        
    }
    
    
}
