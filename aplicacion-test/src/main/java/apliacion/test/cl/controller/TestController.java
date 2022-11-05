package apliacion.test.cl.controller;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private final Counter contadorRequest;
    private final Gauge timestampUltimoRequest;
    private final Histogram histogramaDuracionRequest;
    private final Summary resumenDuracionRequest;

    /**
     * GET /hello will trigger a GET request to /god-of-fire. That way, we get a
     * nice distributed trace for the OpenTelemetry agent.
     */
    @GetMapping("/prueba")
    public String prueba() throws IOException {
        String path = "/prueba";
        contadorRequest.labels(path).inc();
        timestampUltimoRequest.labels(path).setToCurrentTime();
        Histogram.Timer histogramRequestTimer = histogramaDuracionRequest.labels(path).startTimer();
        Summary.Timer summaryRequestTimer = resumenDuracionRequest.labels(path).startTimer();

        try {
            // Genera un error al azar lanzando una excepcion
            errorRandom(path);

            // Se introduce una latencia al azar
            try {
                Thread.sleep((long) (Math.random() * 1000));
            }
            catch (InterruptedException e) {
                throw new IOException(e);
            }

            Request request = new Request.Builder().url("http://localhost:8080/observability").build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                return "Respuesta : " + response.body().string() + "!\n";
            }
        }
        finally {
            histogramRequestTimer.observeDuration();
            summaryRequestTimer.observeDuration();
        }
    }


    @GetMapping("/observability")
    public String observability() throws IOException {
        String path = "/observability";
        contadorRequest.labels(path).inc();
        timestampUltimoRequest.labels(path).setToCurrentTime();
        Histogram.Timer histogramRequestTimer = histogramaDuracionRequest.labels(path).startTimer();
        Summary.Timer summaryRequestTimer = resumenDuracionRequest.labels(path).startTimer();

        try {
            // Genera un error al azar lanzando una excepcion
            errorRandom(path);

            // Se introduce una latencia al azar
            try {
                Thread.sleep((long) (Math.random() * 1000));
            }
            catch (InterruptedException e) {
                throw new IOException(e);
            }
        }
        finally {
            histogramRequestTimer.observeDuration();
            summaryRequestTimer.observeDuration();
        }

        return "Observability ejecutada de forma exitosa";
    }

    private void errorRandom(String path) throws IOException {
        if (Math.random() > 0.9) {
            throw new IOException("Error radom en " + path + "!");
        }
    }
}