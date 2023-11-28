package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.CollectionDateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepo extends JpaRepository<CollectionDateData, Long> {
}

