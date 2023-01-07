package com.cosmos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Products")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name ="categoryid")
	private Category category;

	@Column(name="price")
	private float price;

	@Column(name="cost")
	private float cost;

	@Column(name="number")
	private int number;

	@Column(name="status")
	private int status;

	@Column(name="capacity")
	private Integer capacity;

	@Column(name= "description")
	private String description;

//	@Column(name= "imgmain")
//	private String imgmain;

	//@JsonIgnore
	@OneToMany(mappedBy = "product", fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	List<Image> images = new ArrayList<>();

	//@JsonProperty
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="manufactureid")
	private Manufacture manufacture;


	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "Product_Fragrance", joinColumns = @JoinColumn(name = "productid"), inverseJoinColumns = @JoinColumn(name = "fragranceid"))
	private List<Fragrance> fragrances = new ArrayList<>();

}

