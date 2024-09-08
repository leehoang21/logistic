package com.octl2.api.repository.Lv1;

import com.octl2.api.dto.LocationDTO;
import com.octl2.api.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepositoryLv1 extends JpaRepository<Province, Long> {

    @Query(value = "SELECT " +
            "   p.province_id AS id " +
            "   , p.name " +
            "   , p.code " +
            "   , p.shortname " +
            "   , p.dcsr AS description " +
            "  , dd.ffm_id AS ffmId" +
            "  , dd.lastmile_id AS lastmileId " +
            "  , dd.warehouse_id AS warehouseId " +
            " FROM public.cf_default_delivery as dd" +
            " join lc_province as p " +
            " on dd.location_id = p.province_id " +
            " where dd.location_id = :id"
            , nativeQuery = true)
    LocationDTO getDtoById(@Param("id") Long id);

    @Query(value = "SELECT " +
            "   p.province_id AS id " +
            "   , p.name " +
            "   , p.code " +
            "   , p.shortname " +
            "   , p.dcsr AS description " +
            "  , dd.ffm_id AS ffmId" +
            "  , dd.lastmile_id AS lastmileId " +
            "  , dd.warehouse_id AS warehouseId " +
            " FROM public.cf_default_delivery as dd" +
            " join lc_province as p " +
            " on dd.location_id = p.province_id " +
            " ORDER BY p.province_id"
            , nativeQuery = true)
    List<LocationDTO> getAll();

}
