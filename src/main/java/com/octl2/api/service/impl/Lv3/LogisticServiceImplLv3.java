package com.octl2.api.service.impl.Lv3;

import com.octl2.api.commons.utils.ExportExcelUtil;
import com.octl2.api.dto.LocationDTO;
import com.octl2.api.dto.LocationLogisticDTO;
import com.octl2.api.dto.LocationType;
import com.octl2.api.dto.UpdateLogisticDTO;
import com.octl2.api.repository.Lv3.DistrictRepositoryLv3;
import com.octl2.api.repository.Lv3.ProvinceRepositoryLv3;
import com.octl2.api.repository.Lv3.SubDistrictRepositoryLv3;
import com.octl2.api.repository.PartnerRepository;
import com.octl2.api.repository.WarehouseRepository;
import com.octl2.api.service.impl.LogisticServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LogisticServiceImplLv3 extends LogisticServiceImpl {

    private final ProvinceRepositoryLv3 provinceRepo;
    private final DistrictRepositoryLv3 districtRepo;
    private final SubDistrictRepositoryLv3 subDistrictRepo;

    public LogisticServiceImplLv3(PartnerRepository partnerRepo, WarehouseRepository warehouseRepo, ProvinceRepositoryLv3 provinceRepo, DistrictRepositoryLv3 districtRepo, SubDistrictRepositoryLv3 subDistrictRepo) {
        super(partnerRepo, warehouseRepo);
        this.provinceRepo = provinceRepo;
        this.districtRepo = districtRepo;
        this.subDistrictRepo = subDistrictRepo;
    }

    @Override
    public LocationLogisticDTO getProvinceLogisticsByProvinceId(Long id) {
        LocationDTO province = provinceRepo.getDtoById(id);
        List<LocationDTO> locationLogistics = provinceRepo.getLogisticDtosById(id);

        return getLocationLogisticTypeProvinceDTO(province, locationLogistics);

    }

    @Override
    public List<LocationLogisticDTO> getDistrictLogisticsByProvinceId(Long id) {
        List<LocationDTO> locations = districtRepo.getDtosByProvinceId(id);
        return getLocationLogisticsDTO(LocationType.DISTRICT, locations);
    }


    @Override
    public List<LocationLogisticDTO> getSubDistrictLogisticsByDistrictId(Long id) {
        List<LocationDTO> locations = subDistrictRepo.getLogisticsByDistrictId(id);
        return getLocationLogisticsDTO(LocationType.SUBDISTRICT, locations);
    }

    @Override
    public void exportLogistic(HttpServletResponse response, Long parentId) throws IOException {
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();

        LocationDTO district = districtRepo.getDtoById(parentId);
        LocationDTO province = provinceRepo.getDtoById(district.getParentId());

        List<LocationLogisticDTO> logistics = getSubDistrictLogisticsByDistrictId(parentId);
        exportExcelUtil.loadData(logistics, List.of(province, district));
        export(exportExcelUtil.export(), response);
    }

    @Override
    public boolean updateLogistic(UpdateLogisticDTO updateLogisticDTO, Long id) {
        switch (updateLogisticDTO.getLocationType()) {
            case PROVINCE:
                updateLogisticLocationTypeProvince(updateLogisticDTO, id);
                break;
            case DISTRICT:
                updateLogisticLocationTypeDistrict(updateLogisticDTO, id);
                break;
            case SUBDISTRICT:
                LocationDTO locationDTO = subDistrictRepo.getDtoById(id);
                updateLogisticByLocation(updateLogisticDTO, locationDTO);
                break;
        }

        return true;
    }

    private void updateLogisticLocationTypeProvince(UpdateLogisticDTO updateLogisticDTO, Long provinceId) {
        List<LocationDTO> locations = provinceRepo.getLogisticDtosById(provinceId);
        for (LocationDTO location : locations) {
            updateLogisticByLocation(updateLogisticDTO, location);
        }
    }

    private void updateLogisticLocationTypeDistrict(UpdateLogisticDTO updateLogisticDTO, Long districtId) {
        List<LocationDTO> subDistricts = subDistrictRepo.getLogisticsByDistrictId(districtId);
        for (LocationDTO subDistrict : subDistricts) {
            updateLogisticByLocation(updateLogisticDTO, subDistrict);
        }
    }
}
