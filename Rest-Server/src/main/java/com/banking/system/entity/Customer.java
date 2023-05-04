package com.banking.system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name",nullable = false)
  private String name;

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "customer",
      cascade = {CascadeType.ALL})
  private List<Account> accounts;
}
