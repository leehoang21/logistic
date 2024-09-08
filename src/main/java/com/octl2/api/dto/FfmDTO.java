package com.octl2.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FfmDTO {
    private final PartnerDTO ffm;
    private final List<PartnerDTO> lastmiles;
    private final List<WarehouseDTO> warehouses;

}


