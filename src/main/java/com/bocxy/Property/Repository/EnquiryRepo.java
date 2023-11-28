package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepo extends JpaRepository<Enquiry, Long> {
}
