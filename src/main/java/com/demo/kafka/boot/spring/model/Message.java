package com.demo.kafka.boot.spring.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Message {

    @NotNull
    private long uuid;

    @NotBlank
    private String message;

	public Message(@NotBlank String message) {
		super();
		this.uuid = System.currentTimeMillis();
		this.message = message;
	}  
}
