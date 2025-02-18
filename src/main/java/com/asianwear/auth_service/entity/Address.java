package com.asianwear.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID addressId;
    @Column(name = "user_id", insertable = false, updatable = false) // Important!
    private UUID userId;
    @Column(name = "street_address")

    private String street_address;
    private String city;
    @Column(name = "postal_code")

    private String postal_code;
    private String country;

}
