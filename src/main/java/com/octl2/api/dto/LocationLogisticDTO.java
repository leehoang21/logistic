package com.octl2.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LocationLogisticDTO {
    private final LocationDTO location;
    private final LocationType locationType;
    private final LogisticDTO logistic;


}


