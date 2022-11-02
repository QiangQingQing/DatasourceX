package com.dtstack.dtcenter.loader.client;

import com.dtstack.dtcenter.loader.dto.source.ISourceDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Yi Dong
 * @create 2022-10-13-14:29
 */
public interface INeo4j {

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
