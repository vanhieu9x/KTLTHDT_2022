package com.cosmos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name= "name")
	private String name;

	@Column(name ="description")
	private String description;


	@JsonIgnore
	@OneToMany(mappedBy = "category", fetch=FetchType.EAGER)
	List<Product> productscategory = new ArrayList<>();



}
