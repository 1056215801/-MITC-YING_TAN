package com.mit.admin.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name="test_person")
@Data
public class TestPerson implements Serializable{
	
	@Id
	private Integer id;
	
	@Column(name="name")
	private String name;
	
}
