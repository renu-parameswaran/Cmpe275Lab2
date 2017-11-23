package com.example.game.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.game.repository.gameRepository;
import com.example.game.model.sponsor;

/** 
* @Transactional-which means if any method fails, 
* the container will always rollback the ongoing transaction.
*/
@Transactional
@Service
public class sponsorService {
		private final gameRepository gameRepository;
		
		public sponsorService(gameRepository gameRepository) {
		this.gameRepository = gameRepository;
		}
	/**
	     * Function definition in service class to save a sponsor in database
	     */

		public void saveSponsor(sponsor sponsor){
			gameRepository.save(sponsor);
		}
		
	/**
	     * Function definition in sponsor service class to fetch details of a sponsor using unique field-id
	     */
		public sponsor getSponsor(Long id){
			return gameRepository.findOne(id);
		}
		
	}

