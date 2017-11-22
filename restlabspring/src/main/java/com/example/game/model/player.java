package com.example.game.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;



import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "player")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(allowGetters = true)
public class player implements Serializable {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	 	@Column(name="player_id", unique = true, nullable = false)
	    private Long id;
	 
	 	@Column(name="player_firstname", nullable = false)
	 	private String firstname;
	 	
	 	@Column(name="player_lastname", nullable = false)
	 	private String lastname;
	 	
	 	@Column(name="player_email", nullable = false, unique = true)
	 	private String email;
	 	
	 	@Column(name="player_description")
		private String description;
	 	
	 	@Column(name="player_street")
		private String street;
	 	
	 	@Column(name="player_city")
		private String city;
	 	
	 	@Column(name="player_state")
		private String state;
	 	
	 	@Column(name="player_zip")
		private String zip;
	 	
	 	
	 	@OneToOne(fetch = FetchType.EAGER)
	 	@JoinColumn(name="sponsor_id")
	 	private sponsor sponsor;
	 	
	 	@JsonIgnore
	 	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	 	@JoinTable(name ="opponents",
	 		joinColumns = {@JoinColumn(name="player_id")},
	 		inverseJoinColumns= {@JoinColumn(name="opponent_id")})
	 	private List<player> opponents = new ArrayList<player>();

		/**
		 * Default Constructor
		 */
		public player() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
		/**
		 * Parameterized Constructor
		 * @param firstname
		 * @param lastname
		 * @param email
		 * @param description
		 * @param street
		 * @param city
		 * @param state
		 * @param zip
		 * @param sponsor
		 */
		public player(String firstname, String lastname, String email, String description, String street, String city,
				String state, String zip) {
			super();
			this.firstname = firstname;
			this.lastname = lastname;
			this.email = email;
			this.description = description;
			this.street = street;
			this.city = city;
			this.state = state;
			this.zip = zip;
		}
		
	
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}

		public sponsor getSponsor() {
			return sponsor;
		}

		public void setSponsor(sponsor sponsor) {
			this.sponsor = sponsor;
		}
		

		public List<player> getOpponents() {
			return opponents;
		}

		
		public void setOpponents(List<player> opponents) {
			this.opponents = opponents;
		}
		
		@Override
		public String toString() {
			return "player [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
					+ ", description=" + description + ", street=" + street + ", city=" + city + ", state=" + state
					+ ", zip=" + zip + ", sponsor=" + sponsor + ", opponents=" + opponents + "]";
		}

	
}
