package com.octl2.api.dto;

import com.octl2.api.consts.Const;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class UpdateLogisticDTO {

    @NotNull(message = Const.IS_REQUIRED)
    private LocationType locationType;
    @Valid
    private UpdatePartnerDTO fulfilment;
    @Valid
    private UpdatePartnerDTO lastmile;
    @Valid
    private UpdateWarehouseDTO warehouse;
}
