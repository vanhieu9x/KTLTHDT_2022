package com.cosmos.entity;

import com.cosmos.entity.keyID.CTPhieuNhap_ID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.EmbeddedId;
import javax.persistence.*;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name= "Import_Note_Detail")
public class ImportNoteDetail  {

   @EmbeddedId
   CTPhieuNhap_ID id;

	@Column(name="number")
	private int number;

	@Column(name="price")
	private float price;


	@ManyToOne
	@MapsId("productid")
	@JoinColumn(name = "productid")
     Product product;

	@JsonIgnore
	@ManyToOne
	@MapsId("importnoteid")
	@JoinColumn(name = "importnoteid")
    ImportNote importNote;
}
