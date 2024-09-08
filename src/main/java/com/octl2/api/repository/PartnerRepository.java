package com.octl2.api.repository;

import com.octl2.api.dto.PartnerDTO;
import com.octl2.api.dto.UpdatePartnerDTO;
import com.octl2.api.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    @Query(value = "SELECT " +
            "   pn.partner_id AS id " +
            "   , pn.name " +
            "   , pn.shortname " +
            "   , pn.partner_type AS type " +
            " FROM bp_partner as pn" +
            " where pn.partner_id IN (:ids) "
            , nativeQuery = true)
    List<PartnerDTO> getDtosByIds(@Param("ids") List<Long> ids);

    @Query(
            value = "UPDATE bp_partner " +
                    "SET name = :#{#partnerDTO.name}, " +
                    "shortname = :#{#partnerDTO.shortname} " +
                    "WHERE partner_id = :id " +
                    "RETURNING partner_id AS id, name, shortname, partner_type AS type"
            , nativeQuery = true)
    PartnerDTO update(UpdatePartnerDTO partnerDTO, Long id);

    @Query(value = "SELECT " +
            "  pn.partner_id AS id " +
            ", pn.name " +
            ", pn.shortname " +
            ", pn.partner_type AS type" +
            " FROM bp_partner as pn" +
            " where pn.partner_id = :id "
            , nativeQuery = true)
    PartnerDTO getFfmDtoByFfmId(@Param("id") long id);

    @Query(value = "SELECT " +
            "  pn.partner_id AS id " +
            ", pn.name " +
            ", pn.shortname " +
            ", pn.partner_type AS type" +
            " FROM bp_partner as pn" +
            " JOIN cf_mapping_ffm_lm as cmfl" +
            " ON pn.partner_id = cmfl.lastmile_id" +
            " where cmfl.ffm_id = :id "
            , nativeQuery = true)
    List<PartnerDTO> getLastmileDtosByFfmId(@Param("id") long id);
}
