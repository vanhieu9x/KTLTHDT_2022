package com.cosmos.security.service;

import com.cosmos.entity.Account;
import com.cosmos.entity.Customers;
import com.cosmos.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails {

  private static final long serialVersionUID = 1L;

  private final String username;

  private final Customers customer;

  private final Employee employee;

  @JsonIgnore
  private final String password;

  private final Collection<? extends GrantedAuthority> authorities;

  public UserPrinciple(String email, String password, Customers customer, Employee employee,
      Collection<? extends GrantedAuthority> authorities) {
    this.username = email;
    this.password = password;
    this.authorities = authorities;
    this.customer = customer;
    this.employee = employee;
  }

  public static UserPrinciple build(Account account) {

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
    return new UserPrinciple(
        account.getEmail(),
        account.getPassword(),
        account.getCustomer(),
        account.getEmployee(),
        authorities
    );
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }


  public Customers getCustomer() {
    return customer;
  }

  public Employee getEmployee() {
    return employee;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserPrinciple user = (UserPrinciple) o;
    return Objects.equals(username, user.username);
  }

}
