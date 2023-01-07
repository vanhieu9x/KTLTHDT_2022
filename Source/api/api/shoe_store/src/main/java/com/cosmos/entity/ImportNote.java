package com.cosmos.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Import_Note")
public class ImportNote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="createday")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createday;

	@ManyToOne
	@JoinColumn(name="employeeid")
	private Employee employee;

	@Column(name="totalprice")
	private Float totalprice;

	@Column(name="status")
	private int status;

	//@JsonIgnore
	@OneToMany(mappedBy = "importNote", fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	List<ImportNoteDetail> importNoteDetails = new ArrayList<>();
}
