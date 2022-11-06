# Laboratorio de Grafana

La idea es tener un ejemplo de una aplicacion Spring Boot usando [OpenTelemetry](https://opentelemetry.io/) que utilice el agente [opentelemetry-java](https://github.com/open-telemetry/opentelemetry-java-instrumentation) para que injecte la telemetria a nivel de bytecode y sea collectada por [Grafana Agent](https://grafana.com/docs/grafana-cloud/data-configuration/agent/). Grafana Agent colectara y correlacionara los logs, metricas y trace usando [Prometheus](https://prometheus.io/).

La aplicacion se ecuntra en la carpeta 'aplicacion-test' y se puede ejecutar de forma local:

```
git clone https://github.com/jcoronelcortes/grafana-test.git
cd grafana-test/aplicacion-test
./mvnw package
java -jar target/*.jar
```
Una vez ejecutada la aplicacion se puede acceder a esta en http://localhost:8080/prueba.

El siguiente paso es contruir una imagen de la aplicacion usando Docker:

```
cd grafana-test/aplicacion-test
docker build -t aplicacion-test .
docker run -d -p 8080:8080 --name aplicacion-test aplicacion-test
```
y acceder a la aplicacion en http://localhost:8080/prueba.

Para detener la aplicacion se debe usar la instruccion:

```
docker stop aplicacion-test
```
Estos pasos permiten ejecutar la aplicacion, pero hasta el momento no se esta collectado la telemetria. El siguiente paso es comenzar a recolectar la telemetria.

## Projecto local

Para ejecutar el projecto de forma local se necesita ejecutar una serie de servicios, por lo cual se utilizara Docker Compose para facilitar la gestion de estos. Los servicios que se ejcutaran son:

- Aplicacion test de ejemplo
- Simulador de carga que utilizara curl para hacer llamadas al servicio de prueba
- Prometheus para almacenar las metricas
- Loki para almacenar los logs
- Tempo para almacenar las trazas
- Grafana Agent para recolectar las metricas, logs y trazas
- Grafana para consultar la informacion y crear dashboards

Para ejecutar el projecto se debe ejecutar la instruccion:

```
cd grafana-test/local
docker-compose up
```
Una vez que todos los contenedores se esten ejecutando, se puede ingresar a la aplicacion en http://localhost:8080/prueba y comenzar a crear dashboard en Grafana en en http://localhost:3000. Todas las metricas que se exponen se encuentran disponibles en http://localhost:8080/metrics

Para bajar todos los contenedores se debe ejecutar la instruccion:

```
cd grafana-test/local
docker-compose down
```