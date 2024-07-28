package com.luna.echocircle.government.repository;

import com.luna.echocircle.government.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfficeRepository extends JpaRepository<Office, Long> { //JpaRepository<Entity클래스, PK타입>


}
