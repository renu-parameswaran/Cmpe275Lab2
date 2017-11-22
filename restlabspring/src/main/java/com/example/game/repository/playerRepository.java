package com.example.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.game.model.player;


public interface playerRepository extends JpaRepository<player,Long>  {

}
