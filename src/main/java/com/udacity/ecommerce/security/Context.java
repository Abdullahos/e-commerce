package com.udacity.ecommerce.security;

import com.udacity.ecommerce.model.persistence.User;

import java.io.Serializable;

public interface Context extends Serializable {
    User getUser();
}
