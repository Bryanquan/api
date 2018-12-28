package com.cn.api.service.fdfs.common;

import com.cn.api.Exception.FdfsException;
import com.cn.api.util.PropertiesUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/*
  TrackerServer线程池
 */
public class TrackerServerPool {

    private static Logger logger = LoggerFactory.getLogger(TrackerServerPool.class);

    private static String FASTDFS_CONFIG_PATH = "api-config.properties";

    private static String maxStorageConnection = "fdfs_max_storage_connection";

    private static GenericObjectPool<TrackerServer> trackerServerPool;

    public TrackerServerPool(){};

    private static synchronized GenericObjectPool<TrackerServer> getPool() throws IOException {
        if(trackerServerPool == null){
            try {
                ClientGlobal.initByProperties(FASTDFS_CONFIG_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MyException e) {
                e.printStackTrace();
            }

            if(logger.isDebugEnabled()){
                logger.debug("ClientGlobal configInfo: {}", ClientGlobal.configInfo());
            }

            // Pool配置
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMinIdle(2);
            int maxStorage = 0;
            maxStorage = Integer.valueOf(PropertiesUtils.getConfigValue(maxStorageConnection));
            if(maxStorage > 0){
                poolConfig.setMaxTotal(maxStorage);
            }
            trackerServerPool = new GenericObjectPool<>(new TrackerServerFactory(), poolConfig);
        }
        return trackerServerPool;
    }


    public static TrackerServer borrowObject() throws FdfsException {
        TrackerServer trackerServer = null;
        try {
            trackerServer = getPool().borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof FdfsException){
                throw (FdfsException) e;
            }
        }
        return trackerServer;
    }

    /**
     * 回收 TrackerServer到线程池
     */
    public static void returnObject(TrackerServer trackerServer) throws IOException {
        getPool().returnObject(trackerServer);
    }

}
