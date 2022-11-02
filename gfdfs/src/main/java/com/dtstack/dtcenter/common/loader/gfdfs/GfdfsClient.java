package com.dtstack.dtcenter.common.loader.gfdfs;

import com.dtstack.dtcenter.common.loader.common.nosql.AbsNoSqlClient;
import com.dtstack.dtcenter.loader.dto.source.GfdfsSourceDTO;
import com.dtstack.dtcenter.loader.dto.source.ISourceDTO;

/**
 * @author 10819
 * @description: gofastdfs client
 * @date 10/24/2022 2:59 PM
 */
public class GfdfsClient<T> extends AbsNoSqlClient<T> {

    @Override
    public Boolean testCon(ISourceDTO sourceDTO) {
       GfdfsSourceDTO gfdfsSourceDTO = (GfdfsSourceDTO)sourceDTO;
       return GfdfsUtil.testConn(gfdfsSourceDTO);
    }

}
