package com.example.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.github.wangchenning.autoconfig.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ElasticSimpleJob(jobName = "mySimpleJob-wcn1",
        corn = "0/10 * * * * ?", shardingTotalCount = 2, overwrite = true)
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("我是分片项：" + shardingContext.getShardingItem() + ",总分片项：" + shardingContext.getShardingTotalCount());
    }
}
