package com.octl2.api.config;

import com.octl2.api.repository.Lv1.DistrictRepositoryLv1;
import com.octl2.api.repository.Lv1.ProvinceRepositoryLv1;
import com.octl2.api.repository.Lv1.SubDistrictRepositoryLv1;
import com.octl2.api.repository.Lv2.DistrictRepositoryLv2;
import com.octl2.api.repository.Lv2.ProvinceRepositoryLv2;
import com.octl2.api.repository.Lv2.SubDistrictRepositoryLv2;
import com.octl2.api.repository.Lv3.DistrictRepositoryLv3;
import com.octl2.api.repository.Lv3.ProvinceRepositoryLv3;
import com.octl2.api.repository.Lv3.SubDistrictRepositoryLv3;
import com.octl2.api.repository.PartnerRepository;
import com.octl2.api.repository.WarehouseRepository;
import com.octl2.api.service.LogisticService;
import com.octl2.api.service.impl.Lv1.LogisticServiceImplLv1;
import com.octl2.api.service.impl.Lv2.LogisticServiceImplLv2;
import com.octl2.api.service.impl.Lv3.LogisticServiceImplLv3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${level}")
    int level;


    @Bean
    LogisticService logisticServiceBeanConfigure(PartnerRepository partnerRepo,
                                                 WarehouseRepository warehouseRepo,
                                                 ProvinceRepositoryLv1 provinceRepo,
                                                 DistrictRepositoryLv1 districtRepo,
                                                 SubDistrictRepositoryLv1 subDistrictRepo,
                                                 ProvinceRepositoryLv2 provinceRepoLv2,
                                                 DistrictRepositoryLv2 districtRepoLv2,
                                                 SubDistrictRepositoryLv2 subDistrictRepoLv2,
                                                 ProvinceRepositoryLv3 provinceRepoLv3,
                                                 DistrictRepositoryLv3 districtRepoLv3,
                                                 SubDistrictRepositoryLv3 subDistrictRepoLv3
    ) {
        if (level == 1) {
            return new LogisticServiceImplLv1(
                    partnerRepo,
                    warehouseRepo,
                    provinceRepo,
                    districtRepo,
                    subDistrictRepo
            );
        } else if (level == 2) {
            return new LogisticServiceImplLv2(
                    partnerRepo,
                    warehouseRepo,
                    provinceRepoLv2,
                    districtRepoLv2,
                    subDistrictRepoLv2
            );
        } else if (level == 3) {
            return new LogisticServiceImplLv3(
                    partnerRepo,
                    warehouseRepo,
                    provinceRepoLv3,
                    districtRepoLv3,
                    subDistrictRepoLv3
            );
        }
        return null;
    }
}
