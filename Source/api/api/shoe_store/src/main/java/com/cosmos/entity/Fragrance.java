package com.cosmos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="Fragrances")
public class Fragrance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name="description")
	private String description;


	@JsonIgnore
	//@JsonIgnoreProperties
	@ManyToMany(mappedBy = "fragrances")
	private List<Product> productList = new ArrayList<>();
}
