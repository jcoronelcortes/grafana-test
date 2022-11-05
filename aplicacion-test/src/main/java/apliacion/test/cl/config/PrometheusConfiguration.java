package apliacion.test.cl.config;

import io.prometheus.client.exporter.MetricsServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusConfiguration {

    /**
     * Expone las metricas de Prometheus.
     */
    @Bean
    public ServletRegistrationBean<MetricsServlet> metricsServlet() {
        ServletRegistrationBean<MetricsServlet> bean = new ServletRegistrationBean<>(new MetricsServlet(), "/metrics");
        bean.setLoadOnStartup(1);
        return bean;
    }

}
