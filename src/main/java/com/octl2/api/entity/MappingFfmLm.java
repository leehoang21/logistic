package com.octl2.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cf_mapping_ffm_lm")
@Getter
@Setter
@IdClass(MappingffmLmId.class)
public class MappingFfmLm {

    @Id
    @Column(nullable = false, name = "ffm_id")
    private Long ffmId;

    @Id
    @Column(nullable = false, name = "lastmile_id")
    private Long lastmileId;

}
