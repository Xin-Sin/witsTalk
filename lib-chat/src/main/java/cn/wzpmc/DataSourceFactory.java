package cn.wzpmc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

/**
 * @author wzp
 * @date 2022/5/14
 * @version 1.0
 */
public class DataSourceFactory extends PooledDataSourceFactory {
    public DataSourceFactory(){
        this.dataSource = new DruidDataSource();
    }
}
