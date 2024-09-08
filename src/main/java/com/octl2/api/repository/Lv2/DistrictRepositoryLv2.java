package com.octl2.api.repository.Lv2;

import com.octl2.api.dto.LocationDTO;
import com.octl2.api.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepositoryLv2 extends JpaRepository<District, Long> {

    @Query(value = "select " +
            "   d.district_id AS id " +
            "   , d.name " +
            "   , d.code " +
            "   , d.shortname " +
            "   , d.dcsr AS description " +
            "  , d.province_id AS parentId " +
            " ,dd.ffm_id AS ffmId" +
            " , dd.lastmile_id AS lastmileId " +
            " , dd.warehouse_id AS warehouseId " +
            " FROM public.cf_default_delivery as dd " +
            " join lc_district as d" +
            " on dd.location_id = d.district_id" +
            " where d.province_id = :id" +
            " ORDER BY d.district_id"
            , nativeQuery = true)
    List<LocationDTO> getLogisticDtosByProvinceId(@Param("id") Long id);

    @Query(value = "select " +
            "   d.district_id AS id " +
            "   , d.name " +
            "   , d.code " +
            "   , d.shortname " +
            "   , d.dcsr AS description " +
            "   , d.province_id AS parentId " +
            " ,dd.ffm_id AS ffmId" +
            " , dd.lastmile_id AS lastmileId " +
            " , dd.warehouse_id AS warehouseId " +

            " FROM public.cf_default_delivery as dd " +
            " join lc_district as d" +
            " on dd.location_id = d.district_id" +
            " where d.district_id = :id"
            , nativeQuery = true)
    LocationDTO getDtoById(@Param("id") Long id);
}
