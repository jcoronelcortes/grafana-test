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


    @GetMapping("/frontend")
    public String frontend() throws IOException {
        String path = "/frontend";
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

            Request request = new Request.Builder().url("http://localhost:8080/backend").build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                return "Respuesta : " + response.body().string() + "!\n";
            }
        }
        finally {
            histogramRequestTimer.observeDuration();
            summaryRequestTimer.observeDuration();
        }
    }


    @GetMapping("/backend")
    public String observability() throws IOException {
        String path = "/backend";
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

        return "Backend ejecutado de forma exitosa";
    }

    private void errorRandom(String path) throws IOException {
        if (Math.random() > 0.9) {
            throw new IOException("Error random en " + path + "!");
        }
    }
}
