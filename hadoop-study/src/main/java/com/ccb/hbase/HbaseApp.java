package com.ccb.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;

import java.io.IOException;

public class HbaseApp {

    private Configuration configuration;

    private Connection connection;

    private TableName tableName;

    private Admin admin;

    public HbaseApp() {
        this.init();
    }

    public static void main(String[] args) throws IOException {
        HbaseApp app = new HbaseApp();

        app.createTable("user_info");

    }


    private void init() {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.rootdir", "hdfs://192.168.119.134:8020/hbase");
        configuration.set("hbase.zookeeper.quorum", "192.168.119.134");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.master", "192.168.119.134:60000");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTable(String createTableName) throws IOException {
        System.out.println("start create table");
        this.tableName = TableName.valueOf(createTableName);
        if (admin.tableExists(tableName)) {
            System.out.println("table exists");
            return;
        }
        // 声明表描述符
        HTableDescriptor htd = new HTableDescriptor(this.tableName);
        // Set the colum family name to info
        HColumnDescriptor hcd = new HColumnDescriptor("info");
        // Set data encoding methods, HBase provides DIFF,FAST_DIFF,PREFIX
        // and PREFIX_TREE
        // 设置编码算法，HBase提供了DIFF，FAST_DIFF，PREFIX和PREFIX_TREE四种编码算法
        hcd.setDataBlockEncoding(DataBlockEncoding.FAST_DIFF);

        //设置文件压缩方式，HBase默认提供了GZ和SNAPPY两种压缩算法
        //其中GZ的压缩率高，但压缩和解压性能低，适用于冷数据
        //SNAPPY压缩率低，但压缩解压性能高，适用于热数据
        //建议默认开启SNAPPY压缩
        //hcd.setCompressionType(Compression.Algorithm.SNAPPY);

        // 总结: 其中 编码算法和压缩方式, 都是列族级的

        htd.addFamily(hcd);

        System.out.println("creating table...");

        admin.createTable(htd);

        System.out.println(String.valueOf(admin.getClusterStatus()));
        System.out.println(String.valueOf(admin.listNamespaceDescriptors()));

        System.out.println("Table created successfully.");


    }




}
