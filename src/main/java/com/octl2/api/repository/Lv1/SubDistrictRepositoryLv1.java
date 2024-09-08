package com.octl2.api.repository.Lv1;

import com.octl2.api.dto.LocationDTO;
import com.octl2.api.entity.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubDistrictRepositoryLv1 extends JpaRepository<SubDistrict, Long> {


    @Query(value = "SELECT " +
            "   sd.subdistrict_id AS id " +
            "   , sd.name " +
            "   , sd.code " +
            "   , sd.shortname " +
            "   , sd.dcsr AS description " +
            "  , sd.district_id AS parentId " +
            " FROM lc_subdistrict as sd" +
            " where sd.district_id = :id" +
            " ORDER BY sd.subdistrict_id"
            , nativeQuery = true)
    List<LocationDTO> getDtosByDistrictId(@Param("id") Long id);


}
