package br.caixa.gov.credito.apisimulador.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.caixa.gov.credito.apisimulador.application.repositories.ServiceMetricRepositoryJpa;
import br.caixa.gov.credito.apisimulador.domain.ServiceMetric;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MetricsInterceptor implements HandlerInterceptor {

    private final ServiceMetricRepositoryJpa metricRepository;
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    public MetricsInterceptor(ServiceMetricRepositoryJpa metricRepository) {
        this.metricRepository = metricRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                               Object handler, Exception ex) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String serviceName = handlerMethod.getBeanType().getSimpleName();
            
            ServiceMetric metric = new ServiceMetric();
            metric.setServiceName(serviceName);
            metric.setEndpoint(request.getRequestURI());
            metric.setRequestMethod(request.getMethod());
            metric.setStatusCode(response.getStatus());
            metric.setTimestamp(LocalDateTime.now());
            
            Long start = startTime.get();
            if (start != null) {
                long duration = System.currentTimeMillis() - start;
                metric.setResponseTimeMs(duration);
                startTime.remove();
            }
            
            metricRepository.save(metric);
        }
    }
}
