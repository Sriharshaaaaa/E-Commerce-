package com.zee.model;

import java.util.Set;

import com.zee.dto.HomeCatagorySection;
import com.zee.dto.PaymentMethod;
import com.zee.dto.PaymentOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeCatagory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String image;
	private String catagoryId;
	
	private HomeCatagorySection section;
}
