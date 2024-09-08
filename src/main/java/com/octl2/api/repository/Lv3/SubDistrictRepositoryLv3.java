package com.octl2.api.repository.Lv3;

import com.octl2.api.dto.LocationDTO;
import com.octl2.api.entity.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubDistrictRepositoryLv3 extends JpaRepository<SubDistrict, Long> {

    @Query(value = "select " +
            "   sd.subdistrict_id AS id " +
            "   , sd.name " +
            "   , sd.code " +
            "   , sd.shortname " +
            "   , sd.dcsr AS description " +
            "  , sd.district_id AS parentId " +
            " ,dd.ffm_id AS ffmId" +
            " , dd.lastmile_id AS lastmileId " +
            " , dd.warehouse_id AS warehouseId " +
            " FROM public.cf_default_delivery as dd " +
            " join lc_subdistrict as sd" +
            " on dd.location_id = sd.subdistrict_id " +
            " where sd.district_id = :id " +
            " ORDER BY sd.subdistrict_id"
            , nativeQuery = true)
    List<LocationDTO> getLogisticsByDistrictId(@Param("id") Long id);

    @Query(value = "SELECT " +
            "   sd.subdistrict_id AS id " +
            "   , sd.name " +
            "   , sd.code " +
            "   , sd.shortname " +
            "   , sd.dcsr AS description " +
            "  , sd.district_id AS parentId " +
            " ,dd.ffm_id AS ffmId" +
            " , dd.lastmile_id AS lastmileId " +
            " , dd.warehouse_id AS warehouseId " +
            " FROM public.cf_default_delivery as dd " +
            " join lc_subdistrict as sd" +
            " on dd.location_id = sd.subdistrict_id " +
            " where sd.subdistrict_id = :id "
            , nativeQuery = true)
    LocationDTO getDtoById(Long id);


}
