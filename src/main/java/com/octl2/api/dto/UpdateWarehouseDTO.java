package com.octl2.api.dto;

import com.octl2.api.consts.Const;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class UpdateWarehouseDTO {
    @NotBlank(message = Const.NOT_BLANK)
    private String name;
    @NotBlank(message = Const.NOT_BLANK)
    private String shortname;
    @NotBlank(message = Const.NOT_BLANK)
    private String contactName;
    @NotBlank(message = Const.NOT_BLANK)
    private String contactPhone;
    @NotBlank(message = Const.NOT_BLANK)
    private String address;
    @NotBlank(message = Const.NOT_BLANK)
    private String fullAddress;
}
