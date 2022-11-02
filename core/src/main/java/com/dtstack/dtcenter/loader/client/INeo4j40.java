package com.dtstack.dtcenter.loader.client;

import com.dtstack.dtcenter.loader.dto.source.ISourceDTO;

import java.util.List;
import java.util.Map;

/**
 * @author wlq
 * @create 2022-10-31 16:04
 */
public interface INeo4j40 {

    /**
     * 校验 连接
     * @param source
     * @return
     */
    Boolean testCon(ISourceDTO source);

    /**
     * 执行语句
     * @param source
     */
    List<Map<String, Object>> executeWhatever(ISourceDTO source, String str);
}
