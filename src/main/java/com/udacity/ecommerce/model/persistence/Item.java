package com.udacity.ecommerce.model.persistence;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column(nullable = false)
    @JsonProperty
    private String name;

    @Column(nullable = false)
    @JsonProperty
    private BigDecimal price;

    @Column(nullable = false)
    @JsonProperty
    private String description;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Item item = (Item) o;
		return Objects.equals(id, item.id)
				&& Objects.equals(name, item.name)
				&& Objects.equals(price, item.price)
				&& Objects.equals(description, item.description);
	}
}
