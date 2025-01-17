package com.autohome.frostmourne.monitor.service.core.metric.elasticsearch;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import com.autohome.frostmourne.monitor.model.enums.DataSourceType;
import com.autohome.frostmourne.monitor.model.enums.MetricEnumType;
import com.autohome.frostmourne.monitor.service.core.metric.AbstractRingMetric;
import org.joda.time.DateTime;

import com.autohome.frostmourne.monitor.model.contract.MetricContract;
import com.autohome.frostmourne.monitor.service.core.domain.MetricData;
import com.autohome.frostmourne.monitor.service.core.metric.AbstractSameTimeMetric;
import com.autohome.frostmourne.monitor.service.core.query.IElasticsearchDataQuery;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchRingMetric extends AbstractRingMetric {

    @Resource
    private IElasticsearchDataQuery elasticsearchDataQuery;

    @Override
    public MetricData pullMetricData(DateTime start, DateTime end, MetricContract metricContract, Map<String, String> ruleSettings) {
        try {
            return elasticsearchDataQuery.queryElasticsearchMetricValue(start, end, metricContract);
        } catch (IOException ex) {
            throw new RuntimeException("error when pullMetricData", ex);
        }
    }

    @Override
    public boolean matchDataSourceType(String dataSourceType) {
        return dataSourceType.equalsIgnoreCase(DataSourceType.elasticsearch.name());
    }
}
