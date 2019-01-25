package wsi.repo;



import wsi.model.EngineStatus;

import java.util.List;

public interface EngineInterface {
    List<Double> getTemps();
    Double getTemps2();

    EngineStatus start();

    EngineStatus stop();

    EngineStatus status();

    EngineStatus reverse();
}

