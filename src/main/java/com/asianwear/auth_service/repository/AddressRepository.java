package com.asianwear.auth_service.repository;

import com.asianwear.auth_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByAddressId(UUID addressId);
    Optional<List<Address>> findByUserId(UUID userId);
}
