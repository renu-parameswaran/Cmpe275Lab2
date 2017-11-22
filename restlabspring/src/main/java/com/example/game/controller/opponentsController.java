package com.example.game.controller;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import com.example.game.model.player;
import com.example.game.repository.playerRepository;
import com.example.game.service.playerService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;



@RestController
@RequestMapping("/api")
public class opponentsController {
	
			@Autowired
		    private playerRepository playerRepository;
		    
		    @Autowired
		    private playerService playerService;
		    
		    player player1,player2;

		    /**
		     * Method to create a opponent using HTTP URI as /opponent?
		     * HTTP REQUEST - PUT
		     */
		    @PutMapping("/opponents/{id1}/{id2}")
		    public @ResponseBody ModelAndView  updateOpponents(HttpServletResponse response,
		    		@PathVariable(value = "id1") Long player1Id, 
		    		@PathVariable(value = "id2") Long player2Id)
		    		{
		    			ModelMap map = new ModelMap();
		    			player1 = playerService.find(player1Id);
		    			player2 = playerService.find(player2Id);
		    			
		    			if(player1 == null || player2 == null){
		    				map.addAttribute("error","Player ID does not Exist");
		    				response.setStatus(404);
		    				return new ModelAndView(new MappingJackson2JsonView(), map);
		    				}
		    			else {
		    				List<player> players = player1.getOpponents();
		    				if(players==null){
		    					players = new ArrayList<>();
		    				}
		    				for(int i=0;i<players.size();i++)
		    		        {
		    		        	if(players.get(i).getId()==player2Id)
		    		        	{
		    		        		response.setStatus(200);
				    				map.addAttribute("Already Opponents. Not doing anything");
				    				return new ModelAndView(new MappingJackson2JsonView(), map);
		    		        	}
		    		        }
		    				players.add(player2);
		    				player1.setOpponents(players);
		    				playerService.savePlayer(player1);
		    				
		    				List<player> playersInverse = player2.getOpponents();
		    				if(playersInverse==null){
		    					playersInverse =  new ArrayList<>();
		    				}
		    				playersInverse.add(player1);
		    				player2.setOpponents(playersInverse);
		    				playerService.savePlayer(player2);
		    				response.setStatus(200);
		    				map.addAttribute("message","Opponent added.");
		    				return new ModelAndView(new MappingJackson2JsonView(), map);
		    				
		    			}
		    		}
		    

		    @DeleteMapping("/opponents/{id}/{id2}")
		    public @ResponseBody ModelAndView deleteOpponents(
		    		HttpServletResponse response,
		    		@PathVariable(value = "id") Long player1Id,
		    		@PathVariable(value = "id2") Long player2Id) 
		    {
		    	ModelMap map = new ModelMap();
		    	player player1 = playerService.find(player1Id);
    			player player2 = playerService.find(player2Id);
    			
    			if(player1 == null || player2 == null){
    				map.addAttribute("error","Player ID does not exist");
    				response.setStatus(404);
    				return new ModelAndView(new MappingJackson2JsonView(),map);
    				}
    			//Logic for checking opponents relationship
    			boolean contains = false;
    			List<player> opponents = player1.getOpponents();
    			for(player player : opponents) {
    				if(player==player2)
    					contains= true;
    			}
    			
    			if(!contains) {
    				map.addAttribute("error","Opponents relationship does not exist");
    				response.setStatus(404);
    				return new ModelAndView(new MappingJackson2JsonView(),map);
    			}
    			else {
    				response.setStatus(200);
    				playerService.deleteOpponent(player1Id, player2Id);
    				map.addAttribute("message","Opponent Relationship deleted!");
    				return new ModelAndView(new MappingJackson2JsonView(),map);
    			}
		    }
}







