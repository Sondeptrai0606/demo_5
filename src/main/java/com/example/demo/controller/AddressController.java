package com.example.demo.controller;

import com.example.demo.entity.Address;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Lấy danh sách tất cả địa chỉ
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Address>> filterAddresses(@RequestParam(required = false) String streetName,
                                                         @RequestParam(required = false) String district,
                                                         @RequestParam(required = false) String province) {
        Specification<Address> spec = (root, query, cb) -> {
            Specification<Address> tempSpec = Specification.where(null);
            if (streetName != null) {
                tempSpec = tempSpec.and((root1, query1, cb1) -> cb1.like(root1.get("streetName"), "%" + streetName + "%"));
            }
            if (district != null) {
                tempSpec = tempSpec.and((root1, query1, cb1) -> cb1.like(root1.get("district"), "%" + district + "%"));
            }
            if (province != null) {
                tempSpec = tempSpec.and((root1, query1, cb1) -> cb1.like(root1.get("province"), "%" + province + "%"));
            }
            return tempSpec.toPredicate(root, query, cb);
        };
        return ResponseEntity.ok(addressService.filterAddresses(spec));
    }
    // Lấy thông tin địa chỉ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Optional<Address> addressOpt = addressService.getAddressById(id);
        return addressOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Thêm mới một địa chỉ
    @PostMapping("/add")
    public ResponseEntity<?> createMultipleAddresses(@RequestBody List<Address> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            return ResponseEntity.badRequest().body("Not empty!");
        }

        List<Address> savedAddresses = addressService.saveAllAddresses(addresses);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddresses);
    }


    // Cập nhật thông tin địa chỉ
    @PutMapping("/update/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address addressDetails) {
        Optional<Address> addressOpt = addressService.getAddressById(id);
        if (addressOpt.isPresent()) {
            Address address = addressOpt.get();
            address.setStreetName(addressDetails.getStreetName());
            address.setDistrict(addressDetails.getDistrict());
            address.setProvince(addressDetails.getProvince());
            return ResponseEntity.ok(addressService.saveAddress(address));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Xóa địa chỉ theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Deleted address with ID: " + id);
    }
}
