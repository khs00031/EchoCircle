package com.luna.echocircle.government.repository;

import com.luna.echocircle.government.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OfficeRepository extends JpaRepository<Office, Long> { //JpaRepository<Entity클래스, PK타입>
    @Query("SELECT DISTINCT o.doStr FROM Office o")
    List<String> findAllDo();

    @Query("SELECT DISTINCT o.si FROM Office o WHERE o.doStr = :doStr ORDER BY o.si")
    List<String> findAllSi(String doStr);

    @Query("SELECT DISTINCT o.gu FROM Office o WHERE o.si = :si ORDER BY o.gu")
    List<String> findAllGu(String si);

    @Query("SELECT DISTINCT o.dong FROM Office o WHERE o.si = :si AND o.gu = :gu ORDER BY o.dong")
    List<String> findAllDong(String si, String gu);
}
