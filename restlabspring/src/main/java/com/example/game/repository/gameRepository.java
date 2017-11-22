package com.example.game.repository;

import com.example.game.model.sponsor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface gameRepository extends JpaRepository<sponsor, Long> {
//	@Query(value = "select * from 275lab.sponsor where sponsor_id in (select ps.player_id from 275lab.player p join 275lab.player_sponsor ps on p.player_id=ps.sponsor_sponsor_id where p.player_id=?)", nativeQuery = true)
//	List<Object> findSponsorId(Long sponsor_id);

}
