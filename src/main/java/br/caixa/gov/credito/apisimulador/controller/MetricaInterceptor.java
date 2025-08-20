package br.caixa.gov.credito.apisimulador.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.caixa.gov.credito.apisimulador.application.repositories.MetricaRepositoryJpa;
import br.caixa.gov.credito.apisimulador.domain.MetricaServico;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MetricaInterceptor implements HandlerInterceptor {

    private final MetricaRepositoryJpa metricaRepository;
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    public MetricaInterceptor(MetricaRepositoryJpa metricRepository) {
        this.metricaRepository = metricRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String serviceName = handlerMethod.getBeanType().getSimpleName();

            MetricaServico metrica = new MetricaServico();
            metrica.setNomeServico(serviceName);
            metrica.setEndpoint(request.getRequestURI());
            metrica.setRequestMethod(request.getMethod());
            metrica.setStatusCode(response.getStatus());
            metrica.setTimestamp(LocalDateTime.now());

            Long start = startTime.get();
            if (start != null) {
                long duration = System.currentTimeMillis() - start;
                metrica.setResponseTimeMs(duration);
                startTime.remove();
            }

            metricaRepository.save(metrica);
        }
    }
}
