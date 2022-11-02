package com.dtstack.dtcenter.common.loader.neo4j40;

import com.dtstack.dtcenter.common.loader.common.nosql.AbsNoSqlClient;
import com.dtstack.dtcenter.common.loader.neo4j40.utils.Neo4j40Utils;
import com.dtstack.dtcenter.loader.dto.source.ISourceDTO;
import com.dtstack.dtcenter.loader.dto.source.Neo4j40SourceDTO;
import org.neo4j.driver.Session;

import java.util.List;
import java.util.Map;

/**
 * @author wlq
 * @create 2022-10-26 16:58
 */
public class Neo4j40Client extends AbsNoSqlClient {
    @Override
    public Boolean testCon(ISourceDTO source) {
        String str= "MATCH (n) RETURN n"; //
        Neo4j40SourceDTO neo4J40SourceDTO = (Neo4j40SourceDTO)source;
        Session session = Neo4j40Utils.session(neo4J40SourceDTO);
        List<Map<String, Object>> result = null;
        if(session.isOpen()) {
            try {
                result = Neo4j40Utils.execute(neo4J40SourceDTO, str);
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                Neo4j40Utils.close();
            }
        }
        return result != null ? true : false;
    }

}
