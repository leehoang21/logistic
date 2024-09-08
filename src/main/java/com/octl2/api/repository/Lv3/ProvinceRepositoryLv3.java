package com.octl2.api.repository.Lv3;

import com.octl2.api.dto.LocationDTO;
import com.octl2.api.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepositoryLv3 extends JpaRepository<Province, Long> {


    @Query(value = "select DISTINCT  " +
            "   dd.ffm_id AS ffmId" +
            " , dd.lastmile_id AS lastmileId " +
            " , dd.warehouse_id AS warehouseId " +
            " , d.province_id" +
            " FROM lc_district as d " +
            " join lc_subdistrict as sd" +
            " on sd.district_id = d.district_id" +
            " join cf_default_delivery as dd" +
            " on dd.location_id = sd.subdistrict_id" +
            " WHERE d.province_id = :id " +
            " ORDER BY d.province_id"
            , nativeQuery = true)
    List<LocationDTO> getLogisticDtosById(@Param("id") Long id);

    @Query(value = "SELECT " +
            "   p.province_id AS id " +
            "   , p.name " +
            "   , p.code " +
            "   , p.shortname " +
            "   , p.dcsr AS description " +
            " FROM lc_province as p" +
            " where p.province_id = :id"
            , nativeQuery = true)
    LocationDTO getDtoById(@Param("id") Long id);

}
