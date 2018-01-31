package cn.sisyphe.coffee.stock.application;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.offset.OffsetExtraService;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterialService;
import cn.sisyphe.coffee.stock.domain.storage.StorageService;
import cn.sisyphe.coffee.stock.domain.storage.model.StorageInventory;
import cn.sisyphe.coffee.stock.viewmodel.ConditionQueryStorage;
import cn.sisyphe.coffee.stock.viewmodel.StorageDTO;
import cn.sisyphe.coffee.stock.viewmodel.StorageQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：多条件查询库存信息
 * version: 1.0
 *
 * @author XiongJing
 */
public class StorageQueryManager {

    @Autowired
    private OffsetExtraService offsetExtraService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private RawMaterialService rawMaterialService;


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
            conditionQuery.setMaterialCodes(stringList);
        }

        // 判断截止时间，如果有，则是查询历史库存
        if (conditionQuery.getStopTimeEnd() != null) {
            List<Offset> offsetList = offsetExtraService.findPageByCondition(conditionQuery);
            List<StorageQueryDTO> storageQueryDTOs = new ArrayList<>();
            for (Offset offset : offsetList) {
                // 转换为前端显示的属性
                StorageQueryDTO mapDTO = offsetMapDTO(offset);
                storageQueryDTOs.add(mapDTO);
            }
            dto.setContent(storageQueryDTOs);
            dto.setTotalPageSize(offsetExtraService.findTotalByCondition(conditionQuery));
            return dto;
        }
        // 如果没有，则是查询最新库存
        else {
            Page<StorageInventory> pageByCondition = storageService.findPageByCondition(conditionQuery);
            List<StorageInventory> content = pageByCondition.getContent();
            List<StorageQueryDTO> storageQueryDTOs = new ArrayList<>();
            for (StorageInventory storageInventory : content) {
                // 转换为前端显示的属性
                StorageQueryDTO mapDTO = storageMapDTO(storageInventory);
                storageQueryDTOs.add(mapDTO);
            }
            dto.setContent(storageQueryDTOs);
            // 查询总数，便于分页
            dto.setTotalPageSize(pageByCondition.getTotalElements());
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
            // 设置原料编码
            conditionQuery.setMaterialCodes(stringList);
        }
        // 查询具体数据
        List<Offset> offsetList = offsetExtraService.findPageByCondition(conditionQuery);
        List<StorageQueryDTO> storageQueryDTOs = new ArrayList<>();
        for (Offset offset : offsetList) {
            // 转换为前端显示的属性
            StorageQueryDTO mapDTO = offsetMapDTO(offset);
            storageQueryDTOs.add(mapDTO);
        }
        // 设置具体数据
        dto.setContent(storageQueryDTOs);
        // 查询总数，便于分页
        dto.setTotalPageSize(offsetExtraService.findTotalByCondition(conditionQuery));
        return dto;
    }

    /**
     * mapDTO
     *
     * @param inventory
     * @return
     */
    private StorageQueryDTO storageMapDTO(StorageInventory inventory) {
        StorageQueryDTO storageQueryDTO = new StorageQueryDTO();
        return storageQueryDTO;
    }

    /**
     * mapDTO
     *
     * @return
     */
    private StorageQueryDTO offsetMapDTO(Offset offset) {
        StorageQueryDTO storageQueryDTO = new StorageQueryDTO();
        // 截止时间
        storageQueryDTO.setStopTime(offset.getCreateTime());
        // 站点名称 TODO springCloud查询
        storageQueryDTO.setStationName("查询");
        // 库位名称 TODO springCloud查询
        storageQueryDTO.setStorageName("库位名称");
        // 货物编码
        if (offset.getCargo() != null) {
            storageQueryDTO.setCargoCode(offset.getCargo().getCargoCode());
        }
        // 货物名称 TODO springCloud查询
        storageQueryDTO.setCargoName("货物名称");
        // 货物规格
        storageQueryDTO.setStandardName("100ml/盒");
        // 原料名称
        storageQueryDTO.setMaterialName("原料名称");
        // 原料编码
        if (offset.getRawMaterial() != null) {
            storageQueryDTO.setCargoCode(offset.getRawMaterial().getRawMaterialCode());
        }
        // 数量600ml
        storageQueryDTO.setNumber(offset.getTotalOffsetAmount().toString());
        // 变化量 0.05盒
        storageQueryDTO.setChangeNumber("0.05盒");

        // 单据类型
        storageQueryDTO.setBillType("单据类型");
        // 单号
        storageQueryDTO.setBillCode(offset.getSourceCode());
        // 变化原因
        storageQueryDTO.setChangeMemo("变化原因");

        return storageQueryDTO;
    }


}
