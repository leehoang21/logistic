package com.octl2.api.repository;

import com.octl2.api.dto.UpdateWarehouseDTO;
import com.octl2.api.dto.WarehouseDTO;
import com.octl2.api.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Partner, Long> {

    @Query(value = "SELECT " +
            " w.warehouse_id as id" +
            ", w.warehouse_name as name" +
            ", w.warehouse_shortname as shortname" +
            ", w.contact_name as contactName" +
            ", w.contact_phone as contactPhone" +
            ", w.address" +
            ", w.full_address as fullAddress" +
            " FROM bp_warehouse AS w " +
            " WHERE " +
            "  w.warehouse_id IN (:ids) "
            , nativeQuery = true)
    List<WarehouseDTO> getDtosByIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT " +
            " w.warehouse_id as id" +
            ", w.warehouse_name as name" +
            ", w.warehouse_shortname as shortname" +
            ", w.contact_name as contactName" +
            ", w.contact_phone as contactPhone" +
            ", w.address" +
            ", w.full_address as fullAddress" +
            " FROM bp_warehouse AS w " +
            " WHERE " +
            " w.ffm_id = :ffmId "
            , nativeQuery = true)
    List<WarehouseDTO> getDtosByFfmId(@Param("ffmId") Long ffmId);

    @Query(value = "UPDATE bp_warehouse " +
            "SET warehouse_name = :#{#warehouseDTO.name}, " +
            "warehouse_shortname = :#{#warehouseDTO.shortname}, " +
            "contact_name = :#{#warehouseDTO.contactName}, " +
            "contact_phone = :#{#warehouseDTO.contactPhone}, " +
            "address = :#{#warehouseDTO.address}, " +
            "full_address = :#{#warehouseDTO.fullAddress} " +
            "WHERE warehouse_id = :id " +
            "RETURNING warehouse_id as id" +
            ", warehouse_name as name" +
            ", warehouse_shortname as shortname" +
            ", contact_name as contactName" +
            ", contact_phone as contactPhone" +
            ", address" +
            ", full_address as fullAddress"
            , nativeQuery = true)
    WarehouseDTO update(UpdateWarehouseDTO warehouseDTO, Long id);

}
