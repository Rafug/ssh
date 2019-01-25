package wsi.repo;


import org.springframework.stereotype.Service;
import wsi.model.EngineStatus;
import wsi.model.Temperatura;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static javax.swing.UIManager.get;

/**
 * Symuluje realny silnik
 */

@Service
public class EngineMock implements EngineInterface {
    List<Double> temps;
    int direction;  //1 or -1
    int runningState; //1 or -1 or 0 for not running

    public EngineMock() {
       temps = Arrays.asList(-5.,-5.,1.,1.,1.,1.,1.,1.,1.);
        direction = 1;
        runningState = 0;
    }


    @Override
    public List<Double> getTemps() {
        mockTimeLapse();
        return temps;
    }

    @Override
    public Double getTemps2() {
        mockTimeLapse();
        return temps.get(2);
    }


    @Override
    public EngineStatus start() {
        if (runningState==0) {
            runningState = direction;
        }
        return getCurrentState();
    }

    @Override
    public EngineStatus stop() {
        if (runningState!=0) {
            runningState = 0;
        }
        return getCurrentState();
    }


    @Override
    public EngineStatus status() {
        return new EngineStatus(direction, runningState);
    }


    @Override
    public EngineStatus reverse() {
        if (runningState==0) {
            direction = -direction;
        } else {
            mockReverse();
            direction = -direction;
            runningState = - runningState;
        }
        return getCurrentState();
    }

    private void mockTimeLapse() {
        Random r = new Random();
        for (int i = 0; i < temps.size(); i++) {
            temps.set(i, temps.get(i) + r.nextDouble() * 0.4 - 0.05);
            if(temps.get(i)<90){
                temps.set(i, temps.get(i) + r.nextDouble() + 5);
            }

        }
    }

    private void mockReverse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private EngineStatus getCurrentState() {
        return new EngineStatus(direction, runningState);
    }

}


