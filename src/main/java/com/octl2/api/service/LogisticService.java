package com.octl2.api.service;

import com.octl2.api.dto.FfmDTO;
import com.octl2.api.dto.LocationLogisticDTO;
import com.octl2.api.dto.UpdateLogisticDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public interface LogisticService {

    FfmDTO getFfmByFfmId(Long id);

    LocationLogisticDTO getProvinceLogisticsByProvinceId(Long id);

    List<LocationLogisticDTO> getDistrictLogisticsByProvinceId(Long id);

    List<LocationLogisticDTO> getSubDistrictLogisticsByDistrictId(Long id);

    void exportLogistic(HttpServletResponse response, Long parentId) throws IOException;

    boolean updateLogistic(UpdateLogisticDTO updateLogisticDTO, Long id);

}
