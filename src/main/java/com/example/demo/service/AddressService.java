package com.example.demo.service;

import com.example.demo.entity.Address;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> getAllAddresses();
    Optional<Address> getAddressById(Integer id);
    Address saveAddress(Address address);
    void deleteAddress(Integer id);

    List<Address> saveAllAddresses(List<Address> addresses);
    List<Address> filterAddresses(Specification<Address> spec);

}
