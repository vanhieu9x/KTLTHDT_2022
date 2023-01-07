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
@Table(name = "Role")
public class Role {

	@Id
	@Column(name = "id")
	private int role;

	@Column(name="name")
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "role")
	List<Account> account;

	public Role(int role) {
		this.role = role;
	}
}
