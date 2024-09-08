package com.octl2.api.service.impl.Lv2;

import com.octl2.api.commons.utils.ExportExcelUtil;
import com.octl2.api.dto.LocationDTO;
import com.octl2.api.dto.LocationLogisticDTO;
import com.octl2.api.dto.LocationType;
import com.octl2.api.dto.UpdateLogisticDTO;
import com.octl2.api.repository.Lv2.DistrictRepositoryLv2;
import com.octl2.api.repository.Lv2.ProvinceRepositoryLv2;
import com.octl2.api.repository.Lv2.SubDistrictRepositoryLv2;
import com.octl2.api.repository.PartnerRepository;
import com.octl2.api.repository.WarehouseRepository;
import com.octl2.api.service.impl.LogisticServiceImpl;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogisticServiceImplLv2 extends LogisticServiceImpl {

    private final ProvinceRepositoryLv2 provinceRepo;
    private final DistrictRepositoryLv2 districtRepo;
    private final SubDistrictRepositoryLv2 subDistrictRepo;

    public LogisticServiceImplLv2(PartnerRepository partnerRepo, WarehouseRepository warehouseRepo, ProvinceRepositoryLv2 provinceRepo, DistrictRepositoryLv2 districtRepo, SubDistrictRepositoryLv2 subDistrictRepo) {
        super(partnerRepo, warehouseRepo);
        this.provinceRepo = provinceRepo;
        this.districtRepo = districtRepo;
        this.subDistrictRepo = subDistrictRepo;
    }


    @Override
    public LocationLogisticDTO getProvinceLogisticsByProvinceId(Long id) {
        LocationDTO province = provinceRepo.getDtoById(id);
        List<LocationDTO> logisticIds = provinceRepo.getLogisticDtosById(id);
        return getLocationLogisticTypeProvinceDTO(province, logisticIds);
    }

    @Override
    public List<LocationLogisticDTO> getDistrictLogisticsByProvinceId(Long id) {

        List<LocationDTO> districts = districtRepo.getLogisticDtosByProvinceId(id);
        if (ObjectUtils.isEmpty(districts)) {
            return new ArrayList<>();
        }

        return getLocationLogisticsDTO(LocationType.DISTRICT, districts);

    }


    @Override
    public List<LocationLogisticDTO> getSubDistrictLogisticsByDistrictId(Long id) {
        List<LocationDTO> subDistricts = subDistrictRepo.getLogisticDtosByDistrictId(id);
        if (ObjectUtils.isEmpty(subDistricts)) {
            return new ArrayList<>();
        }

        return getLocationLogisticsDTO(LocationType.SUBDISTRICT, subDistricts);
    }

    @Override
    public void exportLogistic(HttpServletResponse response, Long parentId) throws IOException {
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();

        List<LocationLogisticDTO> logistics = getDistrictLogisticsByProvinceId(parentId);
        LocationDTO province = provinceRepo.getDtoById(parentId);

        exportExcelUtil.loadData(logistics, List.of(province));
        export(exportExcelUtil.export(), response);
    }

    @Override
    public boolean updateLogistic(UpdateLogisticDTO updateLogisticDTO, Long id) {
        switch (updateLogisticDTO.getLocationType()) {
            case PROVINCE:
                updateLogisticLocationTypeProvince(updateLogisticDTO, id);
                return true;
            case DISTRICT:
                LocationDTO locationDTO = districtRepo.getDtoById(id);
                updateLogisticByLocation(updateLogisticDTO, locationDTO);
                return true;
            default:
                return false;
        }
    }

    private void updateLogisticLocationTypeProvince(UpdateLogisticDTO updateLogisticDTO, Long provinceId) {
        List<LocationDTO> districts = districtRepo.getLogisticDtosByProvinceId(provinceId);
        for (LocationDTO district : districts) {
            updateLogisticByLocation(updateLogisticDTO, district);
        }
    }

}
