package wsi.repo;

import com.google.common.util.concurrent.AtomicDouble;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wsi.model.EngineStatus;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
@Slf4j
public class EngineViaHttp implements EngineInterface, InitializingBean {
    //  String engineURL = "https://localhost:8443";
    @Autowired
    MeterRegistry registry;

    List<AtomicDouble> engineTemps;

    @Value("${realengine.url}")
    String engineURL;
    RestTemplate template;

    @Override
    public void afterPropertiesSet() throws Exception {
        template = new RestTemplate();
        log.info("Starting engine connection to: {}", engineURL);
        setupGauges();
    }

    private void setupGauges(){
        //i<8
        engineTemps = new ArrayList<>();
        for(int i = 0; i< 2;i++){

            AtomicDouble someTempValue= registry.gauge("enginetemp_" + i, new AtomicDouble(0));
            engineTemps.add(someTempValue);
        }
    }
        //i<8
    @Scheduled(cron = "0/5 * * * * ?")
    public void updateCurrentEngineTemps(){
        List<Double> currentTemps = getTemps();
        ArrayList<Double> ct =new ArrayList<>(currentTemps);
        for(int i=0; i< 2; i++){
            engineTemps.get(i).set(ct.get(i));
        }

    }

    @Override
    public List<Double> getTemps() {
        log.info("Calling engine on: 192.168.0.242");
        ResponseEntity<List<Double>> response = template.exchange(engineURL, //+ "/temps",
                GET, null, new ParameterizedTypeReference<List<Double>>(){});
        return response.getBody();
    }

    @Override
    public EngineStatus start() {
        return callEngine("/start");
    }

    @Override
    public EngineStatus stop() {
        return callEngine("/stop");
    }

    @Override
    public EngineStatus status() {
        //powinien wysłać zapytanie po http do silnika by sprawdzić jego status...
        return callEngine("/status");
    }

    @Override
    public Double getTemps2() {
        return null;
    }

    @Override
    public EngineStatus reverse() {
        return callEngine("/reverse");
    }

    //Helper method to call the realEngine via http
    private EngineStatus callEngine(String path) {
        log.info("Calling engine on: {}", path);
        ResponseEntity<EngineStatus> response = template.exchange(engineURL + path,
                GET, null, EngineStatus.class);
        return response.getBody();
    }
}



