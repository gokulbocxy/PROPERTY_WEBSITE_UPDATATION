package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.SequentialNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SequentialNumberRepository extends JpaRepository<SequentialNumber, Integer> {
}