/*
 * Copyright 2018 Meituan Dianping. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meituan.dorado.test.thrift.filter;


import com.meituan.dorado.common.RpcRole;
import com.meituan.dorado.rpc.handler.filter.Filter;
import com.meituan.dorado.rpc.handler.filter.FilterHandler;
import com.meituan.dorado.rpc.meta.RpcInvocation;
import com.meituan.dorado.rpc.meta.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SPI配置
 */
public class InvokerFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(InvokerFilter.class);

    @Override
    public RpcResult filter(RpcInvocation invocation, FilterHandler nextHandler) throws Throwable {
        FilterTest.invokeChainStr.append(this.getClass().getSimpleName());

        logger.info("InvokerFilter start");
        RpcResult result = null;
        try {
            result = nextHandler.handle(invocation);
        } catch (Exception e) {
            logger.info("InvokerFilter end have Exception");
            FilterTest.exceptionInfoStr.append("InvokerFilter end have Exception");
            throw e;
        }
        logger.info("InvokerFilter end");
        return result;
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public RpcRole getRole() {
        return RpcRole.INVOKER;
    }
}
