package cn.sisyphe.coffee.stock.application;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.offset.OffsetExtraService;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterialService;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import cn.sisyphe.coffee.stock.domain.shared.station.StationService;
import cn.sisyphe.coffee.stock.domain.storage.StorageService;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import cn.sisyphe.coffee.stock.infrastructure.shared.cargo.repo.CargoRepository;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.coffee.stock.viewmodel.StorageDTO;
import cn.sisyphe.coffee.stock.viewmodel.StorageQueryDTO;
import cn.sisyphe.framework.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：多条件查询库存信息
 * version: 1.0
 *
 * @author XiongJing
 */
@Service
public class StorageQueryManager {

    @Autowired
    private OffsetExtraService offsetExtraService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private RawMaterialService rawMaterialService;
    @Autowired
    private StationService stationService;
    @Autowired
    private RawMaterialService materialService;
    @Autowired
    private CargoRepository cargoRepository;


    /**
     * 实时库存多条件查询库存信息
     *
     * @param conditionQuery 查询条件
     */
    public StorageDTO findNowByCondition(ConditionQueryStorage conditionQuery) {
        StorageDTO dto = new StorageDTO();
        if (conditionQuery.getMaterialTypeArray() != null && conditionQuery.getMaterialTypeArray().size() > 0) {
            List<String> stringList = rawMaterialService.findByMaterialTypeCodes(conditionQuery.getMaterialTypeArray());
            // 设置原料编码
            if (stringList != null && stringList.size() > 0) {
                conditionQuery.setMaterialCodes(stringList);
            }
        }

        // 判断截止时间，如果有，则是查询历史库存
        if (conditionQuery.getStopTimeEnd() != null) {
            List<Offset> offsetList = offsetExtraService.findByConditionToStorage(conditionQuery);
            List<StorageQueryDTO> storageQueryDTOs = new ArrayList<>();
            for (Offset offset : offsetList) {
                // 转换为前端显示的属性
                StorageQueryDTO mapDTO = offsetMapDTO(conditionQuery, offset);
                storageQueryDTOs.add(mapDTO);
            }
            dto.setContent(storageQueryDTOs);
            dto.setTotalNumber(offsetExtraService.findTotalByConditionToStorage(conditionQuery));
            return dto;
        }
        // 如果没有，则是查询最新库存
        else {
            List<StorageInventory> inventoryList = storageService.findPageByCondition(conditionQuery);
            List<StorageQueryDTO> storageQueryDTOs = new ArrayList<>();
            for (StorageInventory storageInventory : inventoryList) {
                // 转换为前端显示的属性
                StorageQueryDTO mapDTO = storageMapDTO(conditionQuery, storageInventory);
                storageQueryDTOs.add(mapDTO);
            }
            dto.setContent(storageQueryDTOs);

            dto.setTotalNumber(storageService.findTotalByCondition(conditionQuery));
            return dto;
        }

    }

    /**
     * 历史库存多条件查询库存信息
     *
     * @param conditionQuery 查询条件
     */
    public StorageDTO findOldByCondition(ConditionQueryStorage conditionQuery) {
        StorageDTO dto = new StorageDTO();
        if (conditionQuery.getMaterialTypeArray() != null && conditionQuery.getMaterialTypeArray().size() > 0) {
            List<String> stringList = rawMaterialService.findByMaterialTypeCodes(conditionQuery.getMaterialTypeArray());
            if (stringList != null && stringList.size() > 0) {
                // 设置原料编码
                conditionQuery.setMaterialCodes(stringList);
            }
        }
        // 查询具体数据
        List<Offset> offsetList = offsetExtraService.findPageByCondition(conditionQuery);
        List<StorageQueryDTO> storageQueryDTOList = new ArrayList<>();
        for (Offset offset : offsetList) {
            // 转换为前端显示的属性
            StorageQueryDTO mapDTO = offsetMapDTO(conditionQuery, offset);
            storageQueryDTOList.add(mapDTO);
        }
        // 设置具体数据
        dto.setContent(storageQueryDTOList);
        // 查询总数，便于分页
        dto.setTotalNumber(offsetExtraService.findTotalByCondition(conditionQuery));
        return dto;
    }

    /**
     * mapDTO 最新库存
     *
     * @param inventory
     * @return
     */
    private StorageQueryDTO storageMapDTO(ConditionQueryStorage conditionQuery, StorageInventory inventory) {
        StorageQueryDTO storageQueryDTO = new StorageQueryDTO();

        // 截止时间
        storageQueryDTO.setStopTime(inventory.getCreateTime());
        Station station = inventory.getStation();
        if (station != null) {
            String stationName = findStationName(station.getStationCode());
            if (!StringUtil.isEmpty(stationName)) {
                // 站点名称
                storageQueryDTO.setStationName(stationName);
            }
            // 库位名称--前端自己转换
            storageQueryDTO.setStorageName(inventory.getStation().getStorageCode());
        }
        Cargo cargo = null;
        Cargo inventoryCargo = inventory.getCargo();
        if (inventoryCargo != null) {
            // 货物编码
            storageQueryDTO.setCargoCode(inventoryCargo.getCargoCode());
            // 货物名称
            cargo = findCargo(inventoryCargo.getCargoCode());
            if (cargo != null) {
                storageQueryDTO.setCargoName(cargo.getCargoName());
                // 货物规格
                storageQueryDTO.setStandardName(cargo.getMeasurement() + cargo.getMeasurementName());
            }
        }
        RawMaterial rawMaterial = null;
        if (inventory.getRawMaterial() != null) {
            // 原料编码
            storageQueryDTO.setMaterialCode(inventory.getRawMaterial().getRawMaterialCode());
            rawMaterial = findRawMaterial(inventory.getRawMaterial().getRawMaterialCode());
            if (rawMaterial != null) {
                // 原料名称
                storageQueryDTO.setMaterialName(rawMaterial.getRawMaterialName());
            }
        }
        // 根据原料还是货物转换数量
        if (conditionQuery.getCargoOrMaterial().equals("cargo")) {
            if (cargo != null) {
                BigDecimal totalOffsetAmountA = BigDecimal.valueOf(inventory.getTotalAmount());
                BigDecimal measurementB = BigDecimal.valueOf(cargo.getMeasurement());
                BigDecimal number = totalOffsetAmountA.divide(measurementB).setScale(2, BigDecimal.ROUND_HALF_UP);
                // 数量
                storageQueryDTO.setNumber(number.toString());
            }
        } else {
            if (rawMaterial != null) {
                // 数量
                storageQueryDTO.setNumber(inventory.getTotalAmount() + rawMaterial.getStandardUnit());
            } else {
                storageQueryDTO.setNumber(inventory.getTotalAmount() + "");
            }
        }
        return storageQueryDTO;
    }

    /**
     * mapDTO 历史库存
     *
     * @return
     */
    private StorageQueryDTO offsetMapDTO(ConditionQueryStorage conditionQuery, Offset offset) {
        StorageQueryDTO storageQueryDTO = new StorageQueryDTO();
        // 单据时间
        storageQueryDTO.setStopTime(offset.getCreateTime());
        if (offset.getStation() != null) {
            // 站点名称
            String stationName = findStationName(offset.getStation().getStationCode());
            if (!StringUtil.isEmpty(stationName)) {
                storageQueryDTO.setStationName(stationName);
            }
            // 库位名称
            storageQueryDTO.setStorageName(offset.getStation().getStorageCode());
        }
        Cargo cargo = null;
        // 货物编码
        if (offset.getCargo() != null) {
            storageQueryDTO.setCargoCode(offset.getCargo().getCargoCode());
            // 查询货物信息
            cargo = findCargo(offset.getCargo().getCargoCode());
            if (cargo != null) {
                // 货物名称
                storageQueryDTO.setCargoName(cargo.getCargoName());
            }
        }
        RawMaterial rawMaterial = null;
        // 查询原料
        if (offset.getRawMaterial() != null) {
            rawMaterial = findRawMaterial(offset.getRawMaterial().getRawMaterialCode());
        }
        if (rawMaterial != null) {
            // 原料名称
            storageQueryDTO.setMaterialName(rawMaterial.getRawMaterialName());
        }
        // 货物规格
        if (cargo != null) {
            storageQueryDTO.setStandardName(cargo.getMeasurement() + cargo.getMeasurementName());
        }
        // 判断是否是根据货物查询
        if (conditionQuery.getCargoOrMaterial().equals("cargo")) {
            if (offset.getCargo() != null && cargo != null) {
                if (offset.getTotalOffsetAmount() != null) {
                    BigDecimal totalOffsetAmountA = BigDecimal.valueOf(offset.getTotalOffsetAmount());
                    BigDecimal measurementB = BigDecimal.valueOf(cargo.getMeasurement());
                    BigDecimal number = totalOffsetAmountA.divide(measurementB).setScale(2, BigDecimal.ROUND_HALF_UP);
                    // 变化量
                    storageQueryDTO.setChangeNumber(number.multiply(new BigDecimal(offset.getInOutStorage().getValue())) + cargo.getMeasurementName());

                }

                if (offset.getInventoryTotalAmount() != null) {
                    // 数量
                    BigDecimal measurementB = BigDecimal.valueOf(cargo.getMeasurement());
                    BigDecimal totalAmountC = BigDecimal.valueOf(offset.getInventoryTotalAmount());
                    BigDecimal scale = totalAmountC.divide(measurementB).setScale(2, BigDecimal.ROUND_HALF_UP);
                    storageQueryDTO.setNumber(scale.toString());
                }
            }
        } else {
            // 原料编码
            storageQueryDTO.setMaterialCode(offset.getRawMaterial().getRawMaterialCode());
            if (offset.getRawMaterial() != null && rawMaterial != null) {
                if (offset.getTotalOffsetAmount() != null && offset.getInOutStorage() != null) {
                    // 变化量
                    storageQueryDTO.setChangeNumber(offset.getTotalOffsetAmount() * offset.getInOutStorage().getValue() + rawMaterial.getStandardUnit());
                }
                // 数量
                storageQueryDTO.setNumber(offset.getInventoryTotalAmount() + rawMaterial.getStandardUnit());
            }
        }


        // 单号
        storageQueryDTO.setBillCode(offset.getSourceCode());
        // 单据类型
        if (offset.getSourceBillType() != null) {
            storageQueryDTO.setBillType(offset.getSourceBillType().name());
        }

        Station outStation = offset.getOutStation();
        if (outStation != null) {
            // 出库站点
            String stationName = findStationName(outStation.getStationCode());
            storageQueryDTO.setOutStationName(stationName);
            // 出库库房
            storageQueryDTO.setOutStationName(outStation.getStorageCode());
        }
        Station inStation = offset.getInStation();
        if (inStation != null) {
            // 入库站点
            String stationName = findStationName(inStation.getStationCode());
            storageQueryDTO.setInStationName(stationName);
            // 入库库房
            storageQueryDTO.setInStorageName(inStation.getStorageCode());
        }
        return storageQueryDTO;
    }

    /**
     * 查询站点名称
     *
     * @param stationCode
     * @return
     */
    private String findStationName(String stationCode) {
        return stationService.findStationName(stationCode);
    }

    /**
     * 查询货物信息
     *
     * @param cargoCode
     * @return
     */
    private Cargo findCargo(String cargoCode) {
        return cargoRepository.findByCargoCode(cargoCode);
    }

    /**
     * 查询原料信息
     *
     * @param materialCode
     * @return
     */
    private RawMaterial findRawMaterial(String materialCode) {
        return materialService.findByMaterialCode(materialCode);
    }
}
