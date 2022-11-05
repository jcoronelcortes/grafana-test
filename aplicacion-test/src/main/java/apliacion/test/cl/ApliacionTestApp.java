package apliacion.test.cl;

import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ApliacionTestApp {

	public static void main(String[] args) {
		DefaultExports.initialize();
		SpringApplication.run(ApliacionTestApp.class, args);
	}

}
