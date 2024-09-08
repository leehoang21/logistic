package com.octl2.api.dto;

import com.octl2.api.consts.Const;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdatePartnerDTO {
    @NotBlank(message = Const.NOT_BLANK)
    private String name;
    @NotBlank(message = Const.NOT_BLANK)
    private String shortname;
}
