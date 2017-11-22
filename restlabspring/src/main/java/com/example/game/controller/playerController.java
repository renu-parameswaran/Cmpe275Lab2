package com.example.game.controller;

import com.example.game.model.player;
import com.example.game.model.sponsor;
import com.example.game.repository.playerRepository;
import com.example.game.service.playerService;
import com.example.game.service.sponsorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
@RestController
@RequestMapping("/api")
public class playerController extends Throwable{

	    @Autowired
	    private playerRepository playerRepository;
	    
	    @Autowired
	    private sponsorService sponsorService;
	    
	    @Autowired
	    private playerService playerService;

	    @GetMapping("/player/{id}")
	    public @ResponseBody ModelAndView getPlayerById(
	    		HttpServletResponse response,
	    		@PathVariable(value = "id") Long playerId) {
	    		ModelMap map = new ModelMap();
	        player player = playerService.find(playerId);
	        if((Long)playerId == null || player == null) {
		        	map.addAttribute("error","Player Id does not Exist");
		        	response.setStatus(404);
		        	return new ModelAndView(new MappingJackson2JsonView(),map);
	        }
	        else {
	        		response.setStatus(200);
	        		map.addAttribute("player",player);
	        		map.addAttribute("opponents",player.getOpponents());
	        		return new ModelAndView(new MappingJackson2JsonView(),map);
	        }
	    }

	    /**
	     * Method to create a player using HTTP URI as /player?
	     * HTTP REQUEST - POST
	     */
	    
	    @PostMapping("/player")
	    @ResponseBody
	    public ResponseEntity createPlayer(
	    			@RequestParam(value= "firstname", required = true) String firstname,
	    			@RequestParam(value= "lastname", required = true) String lastname,
	    			@RequestParam(value= "email", required = true) String email,
	    			@RequestParam(value= "description", required = false)String description,
	    			@RequestParam(value= "street", required = false)String street,
	    			@RequestParam(value= "city", required = false)String city,
	    			@RequestParam(value= "state", required = false)String state,
	    			@RequestParam(value= "zip", required = false)String zip,
	    			@RequestParam(value="sponsor",required = false)Long sponsorId) throws IOException
	    {
	    			 player createdPlayer = new player(firstname,lastname,email,description,street,city,state,zip);
		    			if(sponsorId !=null) {
		    				sponsor sponsor = sponsorService.getSponsor(sponsorId);    
		    				
		    				if(sponsor != null) {
		    					createdPlayer.setSponsor(sponsor);
		    				}
		    				else
		    					return new ResponseEntity<>("Specified Sponsor ID does not exist. Please check your request or try later.", HttpStatus.BAD_REQUEST);
		    			}
		    			List opponentsList = createdPlayer.getOpponents();
		    			createdPlayer.setOpponents(opponentsList);
		    			playerService.savePlayer(createdPlayer);
		    			return new ResponseEntity<>(createdPlayer, HttpStatus.OK);
		    		
	    }
	    

	    /**
	     * Method to update a sponsor using HTTP URI as /player/{id}?
	     * HTTP REQUEST - POST
	     */
	    @PostMapping("/player/{id}")
	    public ResponseEntity updatePlayer(@PathVariable(value = "id") Long playerId,@Valid 
	    		@RequestParam(value= "firstname", required = true) String firstname,
    			@RequestParam(value= "lastname", required = true) String lastname,
    			@RequestParam(value= "email", required = true) String email,
    			@RequestParam(value= "description", required = false)String description,
    			@RequestParam(value= "street", required = false)String street,
    			@RequestParam(value= "city", required = false)String city,
    			@RequestParam(value= "state", required = false)String state,
    			@RequestParam(value= "zip", required = false)String zip,
    			@RequestParam(value="sponsor",required = false)Long sponsorId) throws IOException
	    		{
	    			player player = playerRepository.findOne(playerId);
	    			if(player == null) {
	    				return new ResponseEntity<>("Specified Player ID does not exist. Please check your request or try later.", HttpStatus.NOT_FOUND);
	    			}
	    			
	    			else if(firstname == null || lastname == null || email == null){
	    				return new ResponseEntity<>("Specified Player ID does not exist. Please check your request or try later.", HttpStatus.NOT_FOUND);
	    			}
	    			
	    			/*
	    			 * Check if the Parameter value given in the URL is not null, then update it, else retain the old one
	    			 */
	    			else {
	    				player.setFirstname(firstname);
	    				player.setLastname(lastname);
	    				player.setEmail(email);
	    				player.setDescription(description);
	    				player.setStreet(street);
	    				player.setCity(city);
	    				player.setState(state);
	    				player.setZip(zip);
	    			if(sponsorId != null) {
	    				sponsor sponsor = sponsorService.getSponsor(sponsorId);
	    				if(sponsor != null) {
		    				player.setSponsor(sponsor);
		    			}	
	    			}
	    		List opponentsList = player.getOpponents();
	    		player.setOpponents(opponentsList);
	        playerService.savePlayer(player);
	        return ResponseEntity.ok(player);
	    			}
	    }


	    @DeleteMapping("/player/{id}")
	    public @ResponseBody ModelAndView deletePlayer(HttpServletResponse response,
	    		@PathVariable(value = "id") Long playerId) {
	    	ModelMap map = new ModelMap();
	    	player player = playerService.find(playerId);
	        if((Long) playerId == null || player == null) {
	        		map.addAttribute("error","Player Id does not Exist");
	        		response.setStatus(404);
	            return new ModelAndView(new MappingJackson2JsonView(),map);
	        }
	        else {
	        	List<player> opponents = player.getOpponents();
	        	for(int i=0;i<opponents.size();i++)
		        {
	        		System.out.println("Has opponents");
		        	playerService.deleteOpponent(playerId, opponents.get(i).getId());
		        }
	        	response.setStatus(200);
	        	map.addAttribute("player",player);
		        playerService.deletePlayer(player);
		        return new ModelAndView(new MappingJackson2JsonView(), map);
	    } 
	}
}



