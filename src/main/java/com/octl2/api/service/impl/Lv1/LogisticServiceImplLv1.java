package com.octl2.api.service.impl.Lv1;

import com.octl2.api.commons.utils.ExportExcelUtil;
import com.octl2.api.dto.LocationDTO;
import com.octl2.api.dto.LocationLogisticDTO;
import com.octl2.api.dto.LocationType;
import com.octl2.api.dto.UpdateLogisticDTO;
import com.octl2.api.repository.Lv1.DistrictRepositoryLv1;
import com.octl2.api.repository.Lv1.ProvinceRepositoryLv1;
import com.octl2.api.repository.Lv1.SubDistrictRepositoryLv1;
import com.octl2.api.repository.PartnerRepository;
import com.octl2.api.repository.WarehouseRepository;
import com.octl2.api.service.impl.LogisticServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogisticServiceImplLv1 extends LogisticServiceImpl {

    private final ProvinceRepositoryLv1 provinceRepo;
    private final DistrictRepositoryLv1 districtRepo;
    private final SubDistrictRepositoryLv1 subDistrictRepo;

    public LogisticServiceImplLv1(PartnerRepository partnerRepo, WarehouseRepository warehouseRepo, ProvinceRepositoryLv1 provinceRepo, DistrictRepositoryLv1 districtRepo, SubDistrictRepositoryLv1 subDistrictRepo) {
        super(partnerRepo, warehouseRepo);
        this.provinceRepo = provinceRepo;
        this.districtRepo = districtRepo;
        this.subDistrictRepo = subDistrictRepo;
    }

    @Override
    public LocationLogisticDTO getProvinceLogisticsByProvinceId(Long id) {
        LocationDTO province = provinceRepo.getDtoById(id);
        return getLocationLogisticTypeProvinceDTO(province, List.of(province));

    }

    @Override
    public List<LocationLogisticDTO> getDistrictLogisticsByProvinceId(Long id) {

        List<LocationDTO> districts = districtRepo.getDtosByProvinceId(id);
        LocationDTO province = provinceRepo.getDtoById(id);
        LocationLogisticDTO locationLogistic = getLocationLogisticTypeProvinceDTO(province, List.of(province));

        List<LocationLogisticDTO> locationLogistics = new ArrayList<>();

        for (LocationDTO district : districts) {
            LocationLogisticDTO logisticDTO = new LocationLogisticDTO(district, LocationType.DISTRICT, locationLogistic.getLogistic());
            locationLogistics.add(logisticDTO);
        }
        return locationLogistics;
    }

    @Override
    public List<LocationLogisticDTO> getSubDistrictLogisticsByDistrictId(Long id) {

        List<LocationDTO> subDistrictDTOS = subDistrictRepo.getDtosByDistrictId(id);
        LocationDTO province = provinceRepo.getDtoById(id);
        LocationLogisticDTO locationLogistic = getLocationLogisticTypeProvinceDTO(province, List.of(province));

        List<LocationLogisticDTO> locationLogistics = new ArrayList<>();
        for (LocationDTO subDistrictDTO : subDistrictDTOS) {
            LocationLogisticDTO logisticDTO = new LocationLogisticDTO(subDistrictDTO, LocationType.SUBDISTRICT, locationLogistic.getLogistic());
            locationLogistics.add(logisticDTO);
        }
        return locationLogistics;
    }


    @Override
    public void exportLogistic(HttpServletResponse response, Long parentId) throws IOException {
        List<LocationDTO> provinces = provinceRepo.getAll();
        List<LocationLogisticDTO> logistics = new ArrayList<>();
        for (LocationDTO location : provinces) {
            LocationLogisticDTO logisticDTO = getLocationLogisticTypeProvinceDTO(location, List.of(location));
            logistics.add(logisticDTO);
        }

        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.loadData(logistics, new ArrayList<>());
        export(exportExcelUtil.export(), response);
    }

    @Override
    public boolean updateLogistic(UpdateLogisticDTO logistic, Long id) {

        if (Objects.requireNonNull(logistic.getLocationType()) == LocationType.PROVINCE) {
            LocationDTO location = provinceRepo.getDtoById(id);
            updateLogisticByLocation(logistic, location);
            return true;
        }
        return false;
    }
}
