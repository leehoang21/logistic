package com.octl2.api.controller;

import com.octl2.api.commons.OctResponse;
import com.octl2.api.consts.Const;
import com.octl2.api.dto.FfmDTO;
import com.octl2.api.dto.LocationLogisticDTO;
import com.octl2.api.dto.UpdateLogisticDTO;
import com.octl2.api.service.LogisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/logistics")
@Validated
@RequiredArgsConstructor
@Slf4j
public class LogisticController {
    private final LogisticService logisticService;

    @GetMapping("/fulfillments/{ffmId}")
    public OctResponse<FfmDTO> getFfm(@PathVariable @Min(1) Long ffmId) {
        FfmDTO result = logisticService.getFfmByFfmId(ffmId);
        return OctResponse.build(result);
    }

    @GetMapping("/provinces/{id}")
    public OctResponse<LocationLogisticDTO> getProvinceLogisticByProvinceIds(@PathVariable @Min(1) Long id) {
        LocationLogisticDTO result = logisticService.getProvinceLogisticsByProvinceId(id);
        return OctResponse.build(result);
    }

    @GetMapping("/provinces/{id}/districts")
    public OctResponse<List<LocationLogisticDTO>> getDistrictLogisticsByProvinceId(@PathVariable @Min(1) Long id) {
        List<LocationLogisticDTO> result = logisticService.getDistrictLogisticsByProvinceId(id);
        return OctResponse.build(result);
    }

    @GetMapping("/districts/{id}/subdistricts")
    public OctResponse<List<LocationLogisticDTO>> getSubDistrictLogisticsByDistrictId(@PathVariable @Min(1) Long id) {
        List<LocationLogisticDTO> result = logisticService.getSubDistrictLogisticsByDistrictId(id);
        return OctResponse.build(result);
    }

    @GetMapping(value = {
            "/export",
            "/export/locations/{parentId}"
    })
    public OctResponse<String> exportProvinceLogisticFileExcel(HttpServletResponse response, @PathVariable(required = false) Long parentId) throws IOException {
        parentId = ObjectUtils.isEmpty(parentId) ? 1 : parentId;
        logisticService.exportLogistic(response, parentId);
        return OctResponse.build(Const.EXPORT_SUCCESS, 1);
    }

    @PutMapping("/{id}")
    public OctResponse<String> updateLogistic(@RequestBody @Valid UpdateLogisticDTO updateLogisticDTO, @PathVariable() @Min(1) Long id) {
        boolean result = logisticService.updateLogistic(updateLogisticDTO, id);
        if (result) {
            return OctResponse.build(Const.UPDATE_SUCCESS, 1);
        }
        return OctResponse.build(Const.UNABLE_UPDATE, 0);
    }

}
