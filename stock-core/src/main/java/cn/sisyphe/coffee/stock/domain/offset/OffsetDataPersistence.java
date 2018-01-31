package cn.sisyphe.coffee.stock.domain.offset;

import cn.sisyphe.coffee.stock.domain.storage.StorageService;
import cn.sisyphe.coffee.stock.infrastructure.offset.OffsetRepository;
import cn.sisyphe.coffee.stock.infrastructure.pending.PendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author heyong
 * 冲减相关的持久化
 */
@Service
public class OffsetDataPersistence {

    /**
     * 冲减持久化
     */
    private OffsetRepository offsetRepository;

    /**
     * 待冲减持久化
     */
    private PendingRepository pendingRepository;

    /**
     * 事务管理器
     */
    private PlatformTransactionManager transactionManager;

    /**
     * 库存服务
     */
    private StorageService storageService;

    @Autowired
    public OffsetDataPersistence(OffsetRepository offsetRepository, PendingRepository pendingRepository, PlatformTransactionManager transactionManager, StorageService storageService) {
        this.offsetRepository = offsetRepository;
        this.pendingRepository = pendingRepository;
        this.transactionManager = transactionManager;
        this.storageService = storageService;
    }


    public OffsetRepository getOffsetRepository() {
        return offsetRepository;
    }

    public void setOffsetRepository(OffsetRepository offsetRepository) {
        this.offsetRepository = offsetRepository;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public PendingRepository getPendingRepository() {
        return pendingRepository;
    }

    public void setPendingRepository(PendingRepository pendingRepository) {
        this.pendingRepository = pendingRepository;
    }

    public StorageService getStorageService() {
        return storageService;
    }

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }
}
