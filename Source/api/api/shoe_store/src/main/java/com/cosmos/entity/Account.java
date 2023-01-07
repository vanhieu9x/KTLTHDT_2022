package com.cosmos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account {

  @Id
  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @ManyToOne
  @JoinColumn(name = "roleid")
  private Role role;

  @JsonIgnore
  @OneToOne(mappedBy = "account")
  private Employee employee;

  @JsonIgnore
  @OneToOne(mappedBy = "account")
  private Customers customer;

}
