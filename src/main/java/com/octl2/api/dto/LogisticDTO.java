package com.octl2.api.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class LogisticDTO {
    private final List<PartnerDTO> fulfilments;
    private final List<PartnerDTO> lastmiles;
    private final List<WarehouseDTO> warehouses;

}


