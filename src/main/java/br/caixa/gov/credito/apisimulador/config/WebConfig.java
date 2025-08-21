package br.caixa.gov.credito.apisimulador.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.caixa.gov.credito.apisimulador.controller.MetricaInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MetricaInterceptor metricasInterceptor;

    public WebConfig(MetricaInterceptor metricasInterceptor) {
        this.metricasInterceptor = metricasInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(metricasInterceptor);
    }
}
