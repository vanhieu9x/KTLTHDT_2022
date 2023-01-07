package com.cosmos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Manufactures")
public class Manufacture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="name")
	private String name;

	@Column(name="email")
	private String email;

	@Column(name="phone")
	private String phone;

	@Column(name="address")
	private String address;

	@Column(name="description")
	private String descriptios;

	@JsonIgnore
	@OneToMany(mappedBy="manufacture",fetch=FetchType.EAGER)
	 List<Product> productsmanufacture;

}
