package com.octl2.api.service.impl;

import com.octl2.api.dto.*;
import com.octl2.api.repository.PartnerRepository;
import com.octl2.api.repository.WarehouseRepository;
import com.octl2.api.service.LogisticService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class LogisticServiceImpl implements LogisticService {
    private final PartnerRepository partnerRepo;
    private final WarehouseRepository warehouseRepo;

    public abstract LocationLogisticDTO getProvinceLogisticsByProvinceId(Long id);

    public abstract List<LocationLogisticDTO> getDistrictLogisticsByProvinceId(Long id);

    public abstract List<LocationLogisticDTO> getSubDistrictLogisticsByDistrictId(Long id);

    public abstract void exportLogistic(HttpServletResponse response, Long parentId) throws IOException;

    public abstract boolean updateLogistic(UpdateLogisticDTO updateLogisticDTO, Long id);

    public FfmDTO getFfmByFfmId(Long id) {
        PartnerDTO ffm = partnerRepo.getFfmDtoByFfmId(id);
        List<PartnerDTO> lastmiles = partnerRepo.getLastmileDtosByFfmId(id);
        List<WarehouseDTO> warehouses = warehouseRepo.getDtosByFfmId(id);
        return new FfmDTO(ffm, lastmiles, warehouses);
    }

    protected LocationLogisticDTO getLocationLogisticTypeProvinceDTO(LocationDTO location, List<LocationDTO> locationLogistics) {
        List<PartnerDTO> fulfilments = getFulfilments(locationLogistics);
        List<PartnerDTO> lastmiles = getLastmiles(locationLogistics);
        List<WarehouseDTO> warehouses = getWarehouses(locationLogistics);

        LogisticDTO logistic = new LogisticDTO(fulfilments, lastmiles, warehouses);
        return new LocationLogisticDTO(location, LocationType.PROVINCE, logistic);
    }

    protected List<LocationLogisticDTO> getLocationLogisticsDTO(LocationType locationType, List<LocationDTO> locationLogistics) {
        Map<Long, PartnerDTO> fulfilmentMap = getPartnerDtosToMap(getFulfilments(locationLogistics));
        Map<Long, PartnerDTO> lastmileMap = getPartnerDtosToMap(getLastmiles(locationLogistics));
        Map<Long, WarehouseDTO> warehouseMap = getWarehouseDtosToMap(locationLogistics);

        List<LocationLogisticDTO> locationLogisticsDTO = new ArrayList<>();
        for (LocationDTO location : locationLogistics) {
            PartnerDTO ffm = fulfilmentMap.get(location.getFfmId());
            PartnerDTO lastmile = lastmileMap.get(location.getLastmileId());
            WarehouseDTO warehouse = warehouseMap.get(location.getWarehouseId());

            if (!locationLogisticsDTO.isEmpty() && Objects.equals(locationLogisticsDTO.get(locationLogisticsDTO.size() - 1).getLocation().getId(), location.getId())) {
                LocationLogisticDTO lastLocationLogisticDTO = locationLogisticsDTO.get(locationLogisticsDTO.size() - 1);
                lastLocationLogisticDTO.getLogistic().getFulfilments().add(ffm);
                lastLocationLogisticDTO.getLogistic().getLastmiles().add(lastmile);
                lastLocationLogisticDTO.getLogistic().getWarehouses().add(warehouse);
            } else {
                List<PartnerDTO> ffms = new ArrayList<>(List.of(ffm));
                List<PartnerDTO> lastmiles = new ArrayList<>(List.of(lastmile));
                List<WarehouseDTO> warehouses = new ArrayList<>(List.of(warehouse));

                LogisticDTO logistic = new LogisticDTO(ffms, lastmiles, warehouses);
                locationLogisticsDTO.add(new LocationLogisticDTO(location, locationType, logistic));
            }


        }

        return locationLogisticsDTO;
    }

    protected void export(Workbook workbook, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=Logistics.xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    protected void updateLogisticByLocation(UpdateLogisticDTO updateLogisticDTO, LocationDTO location) {
        UpdatePartnerDTO fulfilment = updateLogisticDTO.getFulfilment();
        UpdatePartnerDTO lastmile = updateLogisticDTO.getLastmile();
        UpdateWarehouseDTO warehouse = updateLogisticDTO.getWarehouse();

        partnerRepo.update(fulfilment, location.getFfmId());
        partnerRepo.update(lastmile, location.getLastmileId());
        warehouseRepo.update(warehouse, location.getWarehouseId());
    }


    private Map<Long, WarehouseDTO> getWarehouseDtosToMap(List<LocationDTO> locationLogistics) {
        return getWarehouses(locationLogistics).stream().collect(Collectors.toMap(WarehouseDTO::getId, warehouseDTO -> warehouseDTO));
    }

    private Map<Long, PartnerDTO> getPartnerDtosToMap(List<PartnerDTO> locationLogistics) {
        return locationLogistics.stream().collect(Collectors.toMap(PartnerDTO::getId, partnerDTO -> partnerDTO));
    }


    private List<PartnerDTO> getFulfilments(List<LocationDTO> locationLogistics) {
        List<Long> ids = locationLogistics.stream().map(LocationDTO::getFfmId).collect(Collectors.toList());
        return partnerRepo.getDtosByIds(ids);
    }

    private List<PartnerDTO> getLastmiles(List<LocationDTO> locationLogistics) {
        List<Long> ids = locationLogistics.stream().map(LocationDTO::getLastmileId).collect(Collectors.toList());
        return partnerRepo.getDtosByIds(ids);
    }

    private List<WarehouseDTO> getWarehouses(List<LocationDTO> locationLogistics) {
        List<Long> ids = locationLogistics.stream().map(LocationDTO::getWarehouseId).collect(Collectors.toList());
        return warehouseRepo.getDtosByIds(ids);
    }


}
