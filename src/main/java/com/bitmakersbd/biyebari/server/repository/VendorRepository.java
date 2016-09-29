package com.bitmakersbd.biyebari.server.repository;

import com.bitmakersbd.biyebari.server.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    public Vendor findOneByEmailAndPassword(String email, String password);

    public Vendor findOneByContactNoAndPassword(String contactNo, String password);

    public Vendor findOneByIdAndPassword(Long id, String password);

    public Vendor findOneByEmail(String email);

    public Vendor findOneByContactNo(String contactNo);
}
