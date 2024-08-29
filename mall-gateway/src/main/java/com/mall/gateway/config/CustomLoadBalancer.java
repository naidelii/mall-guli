package com.mall.gateway.config;

import com.mall.common.base.constant.enums.ResultCodeEnum;
import com.mall.common.base.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author naidelii
 */
@Slf4j
public class CustomLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    /**
     * 处理次数
     */
    private final AtomicInteger handleCount;

    /**
     * 服务id
     */
    private final String serviceId;

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;


    public CustomLoadBalancer(String serviceId, ObjectProvider<ServiceInstanceListSupplier> suppliers) {
        this(new Random().nextInt(1000), serviceId, suppliers);
    }

    public CustomLoadBalancer(int handleCount, String serviceId, ObjectProvider<ServiceInstanceListSupplier> suppliers) {
        this.handleCount = new AtomicInteger(handleCount);
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = suppliers;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable();
        if (supplier == null) {
            log.error("=======CustomLoadBalancer=========, supplier为null");
            throw new ServiceException(ResultCodeEnum.SERVICE_UNAVAILABLE);
        }
        return supplier.get(request).next().map(this::processInstanceResponse);

    }

    private Response<ServiceInstance> processInstanceResponse(List<ServiceInstance> serviceInstances) {
        // 服务列表如果为空的话，直接返回空对象
        if (serviceInstances.isEmpty()) {
            log.warn("No servers available for service: " + this.serviceId);
            // 抛出自定义异常
            throw new ServiceException(ResultCodeEnum.SERVICE_UNAVAILABLE);
        }
        // 使用默认的负载均衡策略
        return getInstanceByRound(serviceInstances);
    }

    /**
     * 轮询
     * 参考 org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer#getInstanceResponse
     *
     * @author javadaily
     */
    private Response<ServiceInstance> getInstanceByRound(List<ServiceInstance> instances) {
        // 获取处理次数的绝对值
        int pos = Math.abs(this.handleCount.incrementAndGet());
        // 根据取模的方式来轮询获取服务
        ServiceInstance instance = instances.get(pos % instances.size());
        return new DefaultResponse(instance);
    }

}