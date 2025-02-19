package com.example.demo.service.impl;

import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Integer id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public List<Address> saveAllAddresses(List<Address> addresses) {
        return addressRepository.saveAll(addresses);

    }

    @Override
    public List<Address> filterAddresses(Specification<Address> spec) {
        return addressRepository.findAll(spec);
    }
}
