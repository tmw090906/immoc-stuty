package com.ccb.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;


/**
 * Hadoop HDFS Java API 操作
 */
public class HDFSApp {

    public static final String HDFS_PATH = "hdfs://lenovo100master:8020";

    private FileSystem fileSystem = null;

    private Configuration configuration = null;


    /**
     * 创建HDFS目录
     * @throws Exception
     */
    @Test
    public void mkdir() throws Exception {
        fileSystem.mkdirs(new Path("/test"));
    }

    /**
     * 创建HDFS文件
     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        FSDataOutputStream outputStream = fileSystem.create(new Path("/test/Hello-Hadoop.txt"));

        outputStream.write("hello hadoop\n".getBytes());
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 查看HDFS文件内容
     * @throws Exception
     */
    @Test
    public void cat() throws Exception {
        FSDataInputStream inputStream = fileSystem.open(new Path("/test/Hello-Hadoop-rename.txt"));
        IOUtils.copyBytes(inputStream, System.out, 1024);
        inputStream.close();
    }


    /**
     * 重命名
     * @throws Exception
     */
    @Test
    public void rename() throws Exception {
        Path oldPath = new Path("/test/Hello-Hadoop.txt");
        Path newPath = new Path("/test/Hello-Hadoop-rename.txt");
        fileSystem.rename(oldPath, newPath);
    }

    // TODO : copyFromLocal copyFromLoaclProgress copyToLoacl delete

    @Test
    public void copyFromLocal() throws Exception {
        Path localPath = new Path("E:\\TempDownload\\[7sht.me]meyd-425\\meyd-425.mp4");
        Path hdfsPath = new Path("/hdfsapi/test");
        fileSystem.copyFromLocalFile(localPath, hdfsPath);
    }



    @Before
    public void setUp() throws Exception {
        configuration = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "tianmw");
    }


    @After
    public void tearDown() throws Exception {
        configuration = null;
        fileSystem = null;

        System.out.println("tear.down");
    }



}
