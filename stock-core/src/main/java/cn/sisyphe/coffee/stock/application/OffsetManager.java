package cn.sisyphe.coffee.stock.application;

import cn.sisyphe.coffee.stock.domain.offset.OffsetDataPersistence;
import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.offset.parser.BillParser;
import cn.sisyphe.coffee.stock.domain.offset.parser.PendingParser;
import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by heyong on 2018/1/8 13:58
 * Description: 冲减操作
 *
 * @author heyong
 */
@Service
public class OffsetManager {

    public final static long ONE_Minute = 60 * 1000;

    @Autowired
    private OffsetDataPersistence offsetDataPersistence;

    public void offset(ResponseResult responseResult, BillParser billParser) {
        OffsetService offsetService = new OffsetService(billParser, offsetDataPersistence);
        offsetService.offset(responseResult);
    }

    /**
     * 定时查询未冲减的PendingBill
     *
     * @param pendingParser
     * @return
     */
    //@Scheduled(fixedDelay = ONE_Minute)
    public void offset(PendingParser pendingParser) {
        // System.out.println(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + " >>调度执行....");
        OffsetService offsetService = new OffsetService(pendingParser, offsetDataPersistence);
        offsetService.offset(null);
    }




}
