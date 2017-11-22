package com.example.game.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.game.repository.playerRepository;
import com.example.game.repository.gameRepository;
import com.example.game.model.player;
import com.example.game.model.sponsor;

@Transactional
@Service
public class playerService {
	@Autowired
	private gameRepository gameRepository;
	
	private final playerRepository playerRepository;
	public String tempString="";
	
	public playerService(playerRepository playerRepository) {
		this.playerRepository = playerRepository;
		}
	
	public player find(Long id){
		
		return playerRepository.findOne(id);
	}
	
	public List<player> findAll() {
		return playerRepository.findAll();
	}
	
	public void savePlayer(player player) {
		playerRepository.save(player);	
	}
	
	public void deletePlayer(player player) {
		List<player> tempOpponents = player.getOpponents();
		List<player> opponents = new ArrayList<player>();
		player.setOpponents(opponents);
		playerRepository.delete(player);
	}
	
	public void deleteOpponent(Long id1, Long id2) {
		player player1 = playerRepository.findOne(id1);
		player player2 = playerRepository.findOne(id2);
		List<player> opponents1 = player1.getOpponents();
		List<player> opponents2 = player2.getOpponents();
		opponents1.remove(player2);
		opponents2.remove(player1);
		playerRepository.save(player1);
		playerRepository.save(player2);
	}
}


