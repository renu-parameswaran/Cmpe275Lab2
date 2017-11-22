package com.example.game.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 *  Entity annotation to mark class as persistence entity
 *  JsonInclude annotation to avoid null value in JSON representation
 */
@Entity
@Table(name = "sponsor")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(allowGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class sponsor implements Serializable {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="sponsor_id", unique = true, nullable = false)
    private Long id;

    @Column(name="sponsor_name", nullable = false)
    private String name;
    
    @Column(name="sponsor_description")
	private String description;
    
    @Column(name="sponsor_street")
	private String street;
    
    @Column(name="sponsor_city")
	private String city;
    
    @Column(name="sponsor_state")
	private String state;
    
    @Column(name="sponsor_zip")
	private String zip;
	
	
	/**
	 * Default Constructor
	 */
    public sponsor() {
		super();
	}

	/**
	 * Initialize sponsor
	 * @param name
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 */
    public sponsor(String name, String description, String street, String city, String state, String zip) {
		super();
		this.name = name;
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

    public String getName() {
	    return name;
	  }
	  
	  public void setName(String name) {
	    this.name = name;
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

}
