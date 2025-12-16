package com.udacity.ecommerce.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {

	@JsonProperty
	@NotBlank(message = "Username cannot be blank")
	private String username;

	@JsonProperty
	@NotBlank(message = "Password cannot be blank")
	private String password;

	@JsonProperty
	@NotBlank(message = "Confirm Password cannot be blank")
	private String confirmPassword;

}
