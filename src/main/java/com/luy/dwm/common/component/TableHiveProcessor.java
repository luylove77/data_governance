package com.luy.dwm.common.component;

import com.luy.dwm.common.constants.CommonCodes;
import com.luy.dwm.common.constants.TableParams;
import com.luy.dwm.model.bean.DmTable;
import com.luy.dwm.model.bean.DmTableColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.*;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Value("${hadoop.warehouse.root.path}")
    String rootPath;

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


    public void createTableToHive(DmTable dmTable) throws TException {
        //springboot与hive交互有Table对象
        Table table = exTractHiveTableFromDmTable(dmTable);
        hiveMetaClient.dropTable(dmTable.getSchemaName(),dmTable.getTableName());
        hiveMetaClient.createTable(table);

        System.out.println(table);
    }

    private Table exTractHiveTableFromDmTable(DmTable dmTable) {
        Table table = new Table();

        //1 库名 表名 表类型 owner
        table.setDbName(dmTable.getSchemaName());
        table.setTableName(dmTable.getTableName());
        table.setTableType("EXTERNAL_TABLE");
        table.setOwner(hadoopUser);

        //2 分区字段
        table.setPartitionKeys(getKeys(dmTable.getPartitionColumns()));


        //3 普通字段
        //sd对象
        StorageDescriptor storageDescriptor = new StorageDescriptor();
        storageDescriptor.setCols(getKeys(dmTable.getTableColumns()));


        //4 sd相关 输入 输出格式 物理位置 序列化参数 分隔符
        storageDescriptor.setInputFormat(getInputFormat(dmTable.getStorageFormat()));
        storageDescriptor.setOutputFormat(getOutputFormat(dmTable.getStorageFormat()));

        //物理位置
        storageDescriptor.setLocation(rootPath + "/" + dmTable.getSchemaName() + "/"
                + getDwLevel(dmTable.getDwLevel())
                + "/" + dmTable.getTableName());

        storageDescriptor.setSerdeInfo(getSerdeInfo(dmTable));

        //序列化


        //5 参数部分 压缩格式 备注
        table.setParameters(this.getParameters(dmTable));

        table.setSd(storageDescriptor);

        return table;
    }

    private Map<String, String> getParameters(DmTable dmTable) {
        HashMap<String, String> params = new HashMap<>();
        //表中文说明
        params.put("comment",dmTable.getTableNameChn());

        //压缩格式
        if(dmTable.getCompressType().equals(CommonCodes.COMPRESS_TYPE_SNAPPY)&&
        dmTable.getStorageFormat().equals(CommonCodes.STORAGE_FORMAT_ORC)){
            params.put("orc.compress","snappy");
        }else if(dmTable.getCompressType().equals(CommonCodes.COMPRESS_TYPE_SNAPPY)&&
                dmTable.getStorageFormat().equals(CommonCodes.STORAGE_FORMAT_PARQUET)){
            params.put("parquet.compress","snappy");
        }else if(dmTable.getCompressType().equals(CommonCodes.COMPRESS_TYPE_GZIP)){
            params.put("compression.codec",TableParams.COMPRESS_TYPE_GZIP);
        }

        return params;
    }

    private List<FieldSchema> getKeys(List<DmTableColumn> partitionColumns) {
        List fieldList = new ArrayList();
        if(partitionColumns!=null) {
            for (DmTableColumn partitionColumn : partitionColumns) {
                FieldSchema fieldSchema = new FieldSchema();
                //英文名
                fieldSchema.setName(partitionColumn.getColumnName());
                //中文名
                fieldSchema.setComment(partitionColumn.getColumnComment());
                //类型
                fieldSchema.setType(getHiveDataType(partitionColumn.getDataType()));

                fieldList.add(fieldSchema);
            }
        }

        return fieldList;
    }

    //根据dmTableColumn中的dataType 生成hive字段的数据类型
    public static String getHiveDataType(String dataType){
        switch (dataType){
            case CommonCodes.DATA_TYPE_STRING:
                return "string";
            case CommonCodes.DATA_TYPE_DECIMAL:
                return "decimall";
            case CommonCodes.DATA_TYPE_DECIMAL_16_2:
                return "decimal(16,2)";
            case CommonCodes.DATA_TYPE_BIGINT:
                return "bigint";

        }
        return  "string";
    }

    /**
     * 根据存储格式获取输入格式参数。
     *
     * 此函数根据给定的存储格式返回相应的输入格式参数。支持的存储格式包括PARQUET、TEXT(JSON)和ORC。
     * 对于不同的存储格式，系统预定义了相应的输入格式参数。
     *
     * @param storageFormat 存储格式代码，用于确定输入格式。取值来自CommonCodes类中定义的存储格式常量。
     * @return 对应于给定存储格式的输入格式参数。如果存储格式不受支持，则返回null。
     */
    private String getInputFormat(String storageFormat){
        //利用switch 映射storageFormat的值为  TableParams中的INPUT_FORMAT
        switch (storageFormat){
            case CommonCodes.STORAGE_FORMAT_PARQUET:
                return TableParams.PARQUET_INPUT_FORMAT;
            case CommonCodes.STORAGE_FORMAT_TEXT_JSON:
                return TableParams.TEXT_INPUT_FORMAT;
            case CommonCodes.STORAGE_FORMAT_TEXT_TAB:
                return TableParams.TEXT_INPUT_FORMAT;
            case CommonCodes.STORAGE_FORMAT_ORC:
                return TableParams.ORC_INPUT_FORMAT;
        }
        return null;
    }

    /**
     * 根据存储格式获取相应的输出格式参数值。
     *
     * 此函数根据输入的存储格式字符串，返回对应的数据输出格式参数值。支持的存储格式包括PARQUET、TEXT(JSON)和TEXT(TAB)、ORC。
     * 对于不支持的存储格式，函数将返回null。
     *
     * @param storageFormat 存储格式的字符串表示。取值来自CommonCodes类中定义的常量。
     * @return 对应的输出格式参数值字符串。如果输入的存储格式不受支持，则返回null。
     */
    private String getOutputFormat(String storageFormat){
        switch (storageFormat){
            case CommonCodes.STORAGE_FORMAT_PARQUET:
                return TableParams.PARQUET_OUTPUT_FORMAT;
            case CommonCodes.STORAGE_FORMAT_TEXT_JSON:
                return TableParams.TEXT_OUTPUT_FORMAT;
            case CommonCodes.STORAGE_FORMAT_TEXT_TAB:
                return TableParams.TEXT_OUTPUT_FORMAT;
            case CommonCodes.STORAGE_FORMAT_ORC:
                return TableParams.ORC_OUTPUT_FORMAT;
        }
        return null;
    }

    //根据数仓分层，获得分层缩写
    public static String getDwLevel(String dwLevel){
        switch (dwLevel) {
            case CommonCodes.DW_LEVEL_ODS:
                return "ods";
            case CommonCodes.DW_LEVEL_DWD:
                return "dwd";
            case CommonCodes.DW_LEVEL_DWS:
                return "dws";
            case CommonCodes.DW_LEVEL_DIM:
                return "dim";
            case CommonCodes.DW_LEVEL_ADS:
                return "ads";
        }
        return null;
        }

    //根据storageFormat 创建serdeInfo
    private  SerDeInfo getSerdeInfo(DmTable dmTable) {
        SerDeInfo serDeInfo = new SerDeInfo();
        serDeInfo.setParameters(new HashMap<>());
        switch (dmTable.getStorageFormat()) {
            case CommonCodes.STORAGE_FORMAT_PARQUET:
                serDeInfo.setSerializationLib(TableParams.SERDE_CLASS_PARQUET);
                break;
            case CommonCodes.STORAGE_FORMAT_TEXT_JSON:
                serDeInfo.setSerializationLib(TableParams.SERDE_CLASS_JSON);
                break;
            case CommonCodes.STORAGE_FORMAT_TEXT_TAB:
                serDeInfo.setSerializationLib(TableParams.SERDE_CLASS_TEXT);
                serDeInfo.getParameters().put("field.delim", "\t");//输入
                serDeInfo.getParameters().put("serialization.format", "\t");//输出
                break;
            case CommonCodes.STORAGE_FORMAT_ORC:
                serDeInfo.setSerializationLib(TableParams.SERDE_CLASS_ORC);
                break;
        }
        //根据dmTable中的null对serde进行赋值
        if(dmTable.getNullDefined()!=null&&dmTable.getNullDefined().trim().length()>0){
            serDeInfo.getParameters().put("serialization.null.format", dmTable.getNullDefined().replace("'",""));
        }
        return  serDeInfo;
    }


    public List<String> getDatabaseNameList() throws Exception {
        List<String> databaseNameList = hiveMetaClient.getAllDatabases();
        return databaseNameList;
    }

    //获取数据库下的表名
    public List<String> getTableNameList(String databaseName) throws Exception {
        List<String> tableNameList = hiveMetaClient.getAllTables(databaseName);
        return tableNameList;
    }

    //根据库名和表名获得table对象
    public Table getTable(String databaseName,String tableName) throws Exception {
        Table table = hiveMetaClient.getTable(databaseName,tableName);
        return table;
    }

    //从hive中提取表的元数据，同步到dmTable
    public void syncTableMeta(DmTable dmTable) throws TException {
        //1 从hive中提取表的元数据 table
        Table table = hiveMetaClient.getTable(dmTable.getSchemaName(),dmTable.getTableName());

        //2 从table中获取表的元数据，赋值给dmTable
        //提取 参数信息 包括压缩方式和表备注
        dmTable.setCompressType(getCompressTypeFromHive(table));
        dmTable.setTableNameChn(getTableCommentFromHive(table));

        // 分区字段
        dmTable.setPartitionColumns(getColumnsFromHive(table.getPartitionKeys(),true));

        // 普通字段
        dmTable.setTableColumns(getColumnsFromHive(table.getSd().getCols(),false));

        // 输入输出格式
        dmTable.setStorageFormat(parseStorageFormat(table.getSd()));

        // 空值替换
        dmTable.setNullDefined(getNullDefined(table.getSd()));

        // 层级
        dmTable.setDwLevel(getDmLevel(table.getTableName()));

        // 还可以通过表名的信息 推断出 存储策略 统计周期 数据域 业务过程 维度
        // 不准也可以让用户页面自己填


    }

    // 根据表名的前缀来判断表的层级
    private String getDmLevel(String tableName) {
        if(tableName.startsWith("ods_")){
            return CommonCodes.DW_LEVEL_ODS;
        } else if (tableName.startsWith("dwd_")){
            return CommonCodes.DW_LEVEL_DWD;
        } else if (tableName.startsWith("dws_")){
            return CommonCodes.DW_LEVEL_DWS;
        } else if (tableName.startsWith("dim_")){
            return CommonCodes.DW_LEVEL_DIM;
        } else if (tableName.startsWith("ads_")){
            return CommonCodes.DW_LEVEL_ADS;
        }
        return null;
    }

    private String getNullDefined(StorageDescriptor sd) {
        if (sd.getSerdeInfo().getParameters().containsKey("serialization.null.format")) {
            String nullString = sd.getSerdeInfo().getParameters().get("serialization.null.format");
            if(nullString.equals("")){
                return "''";
            }
            return nullString;
        }
        return null;
    }

    // 辅助方法: 解析存储格式
    private String parseStorageFormat(StorageDescriptor sd) {
        if (sd.getInputFormat().equals(TableParams.PARQUET_INPUT_FORMAT)){
            return CommonCodes.STORAGE_FORMAT_PARQUET;
        } else if (sd.getInputFormat().equals(TableParams.ORC_INPUT_FORMAT)){
            return CommonCodes.STORAGE_FORMAT_ORC;
        }  else if (sd.getInputFormat().equals(TableParams.TEXT_INPUT_FORMAT)){
            if (sd.getSerdeInfo().getSerializationLib().equals(TableParams.SERDE_CLASS_JSON)){
                return CommonCodes.STORAGE_FORMAT_TEXT_JSON;
            } else {
                return CommonCodes.STORAGE_FORMAT_TEXT_TAB;
            }
        }
        return null;
    }

    private List<DmTableColumn> getColumnsFromHive(List<FieldSchema> fieldSchemaList,boolean isPartitionCol) {

        if(fieldSchemaList!=null && fieldSchemaList.size()>0){
            List<DmTableColumn> dmTableColumnList = new ArrayList<>();
            for (FieldSchema fieldSchema : fieldSchemaList) {
                DmTableColumn dmTableColumn = new DmTableColumn();
                dmTableColumn.setColumnName(fieldSchema.getName());
                dmTableColumn.setDataType(this.getDataTypeCodeByHiveDataType(fieldSchema.getType()));
                dmTableColumn.setColumnComment(fieldSchema.getComment());
                dmTableColumn.setIsPartitionCol(isPartitionCol?"1":"0");
                dmTableColumnList.add(dmTableColumn);
            }
            return dmTableColumnList;
        }
       return null;
    }

    private String getDataTypeCodeByHiveDataType(String type) {
        switch (type){
            case "string":
                return CommonCodes.DATA_TYPE_STRING;
            case "decimal":
                return CommonCodes.DATA_TYPE_DECIMAL;
            case "bigint":
                return CommonCodes.DATA_TYPE_BIGINT;
            case "decimal(16,2)":
                return CommonCodes.DATA_TYPE_DECIMAL_16_2;
        }
        return CommonCodes.DATA_TYPE_STRING;
    }

    private String getTableCommentFromHive(Table table) {
        Map<String, String> parameters = table.getParameters();
        if(parameters!=null){
            String comment = parameters.get("comment");
            return comment;
        }
        return null;
    }


    private String getCompressTypeFromHive(Table table) {
        // 1 gzip
        Map<String, String> parameters = table.getParameters();
        if(parameters.containsKey("compression.codec")){
            String compressCodec = parameters.get("compression.codec");
            if(compressCodec.toLowerCase().contains("gzip")){
                return CommonCodes.COMPRESS_TYPE_GZIP;
            }
            // 2 snappy
        } else if(parameters.containsKey("orc.compress")||parameters.containsKey("parquet.compress")){
            String compressCodec = parameters.get("orc.compress");
            if(compressCodec==null){
                compressCodec = parameters.get("parquet.compress");
            }
            if(compressCodec.toLowerCase().contains("snappy")){
                return CommonCodes.COMPRESS_TYPE_SNAPPY;
            }

        }
        return CommonCodes.COMPRESS_TYPE_NONE;


    }

}
