package com.cosmos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

//    @Column(name = "productid")
//    private int productid;

    @Column(name = "path")
    private String path;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="productid")
    private Product product;
}
