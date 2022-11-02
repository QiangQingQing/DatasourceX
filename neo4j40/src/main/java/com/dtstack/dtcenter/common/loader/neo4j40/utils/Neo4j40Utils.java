package com.dtstack.dtcenter.common.loader.neo4j40.utils;

import com.dtstack.dtcenter.loader.dto.source.Neo4j40SourceDTO;
import org.neo4j.driver.*;

import java.util.List;
import java.util.Map;

/**
 * @author wlq
 * @create 2022-10-26 17:14
 */
public class Neo4j40Utils {
    private static Driver driver = null;

    private static final ThreadLocal<Session> threadSession = new ThreadLocal<>();

    public static Session session(Neo4j40SourceDTO neo4J40SourceDTO) {
        Session session = threadSession.get();
        if(session == null || !session.isOpen()) {
            if(driver == null) {
                driver = GraphDatabase.driver(neo4J40SourceDTO.getUri(), AuthTokens.basic(neo4J40SourceDTO.getUsername(), neo4J40SourceDTO.getPassword()));
            }
            session = driver.session();
            threadSession.set(session);
        }
        return session;
    }

    public static void close() {
        Session session = threadSession.get();
        if(session != null) {
            session.close();
            threadSession.remove();
        }
    }
    public static List<Map<String, Object>> execute(Neo4j40SourceDTO neo4J40SourceDTO, String str){
        List<Map<String, Object>> run = null;
        try {
            run = session(neo4J40SourceDTO).run(str).list(Record::asMap);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return run;
    }

}
