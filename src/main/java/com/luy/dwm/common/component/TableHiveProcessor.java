package com.luy.dwm.common.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TableHiveProcessor {

    //类加载第一步
    IMetaStoreClient hiveMetaClient = null;


    //加载组件变量第二步，hiveMetastoreUrl有值了
    @Value("${hive.metastore.uris}")
    String hiveMetastoreUrl;

    @Value("${hadoop.username}")
    String hadoopUser;


    //PostConstruct是在类加载完后执行的方法,不用调用，已经加载到缓存里了
    @PostConstruct
    public void initHiveClient() {
        System.setProperty("HADOOP_USER_NAME",hadoopUser);
        log.info(System.getProperty("HADOOP_USER_NAME"));
        HiveConf hiveConf = new HiveConf();
        hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS,hiveMetastoreUrl);

        try {
            //有值后，再初始化Client
            hiveMetaClient = new HiveMetaStoreClient(hiveConf);
        } catch (MetaException e) {
            throw new RuntimeException(e);
        }
    }

    //用户访问阶段
    public void createDatabaseToHive(String databaseName)  throws TException {
        Database database = new Database();
        database.setName(databaseName);
        hiveMetaClient.createDatabase(database);
    }



}
