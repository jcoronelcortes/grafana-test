package apliacion.test.cl.config;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelemetryConfiguration {

    @Bean
    Counter contadorRequest(){
        return Counter.build().name("requests_total").help("Numero total de request.")
                .labelNames("path").register();
    }

    @Bean
    Gauge timestampUltimoRequest(){
        return Gauge.build().name("last_request_timestamp")
                .help("Tiempo en formato Unix de el ultimo request").labelNames("path").register();
    }

    @Bean
    Histogram histogramaDuracionRequest(){
        return Histogram.build().name("request_duration_histogram")
                .help("Duracion del request en segundos").labelNames("path")
                .buckets(0.001, 0.002, 0.003, 0.004, 0.005, 0.006, 0.007, 0.008, 0.009).register();
    }

    @Bean
    Summary resumenDuracionRequest(){
        return Summary.build().name("request_duration_summary")
                .help("Duracion del request en segundos").labelNames("path").quantile(0.75, 0.01).quantile(0.85, 0.01)
                .register();
    }

}
