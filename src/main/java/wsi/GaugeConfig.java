package wsi;

import io.prometheus.client.Gauge;

/**
 * Created by Rafaello on 11.01.2019.
 */
public class GaugeConfig {

    static final Gauge inprogressRequests = Gauge.build()
            .name("sexy_string").help("Inprogress requests.").register();

    void processRequest() {
        inprogressRequests.inc();
        // Your code here.
        inprogressRequests.dec();
    }

}
