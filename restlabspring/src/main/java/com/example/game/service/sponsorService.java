package com.example.game.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.game.repository.gameRepository;
import com.example.game.model.sponsor;


@Transactional
@Service
public class sponsorService {
		private final gameRepository gameRepository;
		
		public sponsorService(gameRepository gameRepository) {
		this.gameRepository = gameRepository;
		}

		public void saveSponsor(sponsor sponsor){
			gameRepository.save(sponsor);
		}
		
		public sponsor getSponsor(Long id){
			return gameRepository.findOne(id);
		}
		
	}

