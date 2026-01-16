package com.circuitbreaker.user.dto;

import lombok.Data;

@Data
public class MyOrderDTO {	
	
	 	private int id;
	    private String name;
	    private String category;
	    private String color;
	    private double price;
		public MyOrderDTO(int id, String name, String category, String color, double price) {
			super();
			this.id = id;
			this.name = name;
			this.category = category;
			this.color = color;
			this.price = price;
		}
}
