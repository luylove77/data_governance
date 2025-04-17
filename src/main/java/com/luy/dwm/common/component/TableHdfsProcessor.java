package com.luy.dwm.common.component;

import com.luy.dwm.model.bean.DmTableDataInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;
import org.apache.hadoop.hive.metastore.api.Table;

import java.net.URI;
import java.util.Date;

@Component
public class TableHdfsProcessor {
    //如何遍历一个树形结构
    //递归前的准备：递归的准备阶段
    //递归过程的计算结构容器
    //准备一个起点
    //准备访问节点的的工具， 可以根据路径获得该路径下hdfs文件信息的工具类FileSystem

    public void getHdfsFileInfo(Table table, DmTableDataInfo dmTableDataInfo) throws Exception{

        String location = table.getSd().getLocation();

        //准备访问节点的的工具， 可以根据路径获得该路径下hdfs文件信息的工具类FileSystem
        FileSystem fileSystem = FileSystem.get(new URI(location), new Configuration(), table.getOwner());
        //准备一个起点
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path(location));
        getHdfsFileInfoRec(fileStatuses,dmTableDataInfo,fileSystem);


    }

    // 递归的阶段：起点，容器，工具 作为参数
    // 循环
    //    遇到 叶子节点 文件
    //             计算
    //             返回 结果
    //    遇到 分支节点 目录
    //             也可能会有计算
    //             再次把节点展开 调用 递归函数本身

    public void getHdfsFileInfoRec(FileStatus[] fileStatuses,DmTableDataInfo dmTableDataInfo,FileSystem fileSystem) throws Exception{
        for(FileStatus fileStatus : fileStatuses){
            if(fileStatus.isFile()) {
                //fileStatus.isFile()是文件，相当于是叶子节点
                //获取文件大小,fileStatus.getLen()获取单副本的文件大小，fileStatus.getReplication()文件的副本数
                //因为是递归，所以先要获取到上一次的总文件大小dmTableDataInfo.getDataSizeAllRep()，一个文件一个文件递归的
                 long fileDataSizeRep = fileStatus.getLen()*fileStatus.getReplication();
                dmTableDataInfo.setDataSizeAllRep(dmTableDataInfo.getDataSizeAllRep() + fileDataSizeRep);
                //最后访问时间
                long accessTime = fileStatus.getAccessTime();
                if(dmTableDataInfo.getDataLastAccessTime()==null){
                    dmTableDataInfo.setDataLastAccessTime(new Date(accessTime));
                }else {
                    dmTableDataInfo.setDataLastAccessTime(accessTime > dmTableDataInfo.getDataLastAccessTime().getTime() ? new Date(accessTime) : dmTableDataInfo.getDataLastAccessTime());
                }
                //最后修改时间
                long modificationTime = fileStatus.getModificationTime();
                if(dmTableDataInfo.getDataLastModifyTime()==null){
                    dmTableDataInfo.setDataLastModifyTime(new Date(modificationTime));
                }else {
                    dmTableDataInfo.setDataLastModifyTime(modificationTime > dmTableDataInfo.getDataLastModifyTime().getTime() ? new Date(modificationTime) : dmTableDataInfo.getDataLastModifyTime());
                }
            }else{
                //目录
                //展开目录的下级文件元素
                FileStatus[] subFileStatuses = fileSystem.listStatus(fileStatus.getPath());
                if(subFileStatuses!=null) {
                    this.getHdfsFileInfoRec(subFileStatuses, dmTableDataInfo, fileSystem);
                }

            }
        }
    }

}
