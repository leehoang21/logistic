package com.octl2.api.repository.Lv1;

import com.octl2.api.dto.LocationDTO;
import com.octl2.api.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepositoryLv1 extends JpaRepository<District, Long> {

    @Query(value = "SELECT " +
            "   d.district_id AS id " +
            "   , d.name " +
            "   , d.code " +
            "   , d.shortname " +
            "   , d.dcsr AS description " +
            "  , d.province_id AS parentId " +
            " FROM lc_district as d" +
            " where d.province_id = :id" +
            " ORDER BY d.district_id"
            , nativeQuery = true)
    List<LocationDTO> getDtosByProvinceId(@Param("id") Long id);


}
