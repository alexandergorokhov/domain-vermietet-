package villagermanager.manager;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.TreeMap;

@Getter
@Setter
public abstract class Device {
    private Integer id;
    private TreeMap<LocalDateTime, Double> timeMap;

    //public abstract void compute();
    public abstract void compute(Double amount);

    public abstract Double totalConsumption(Integer duration);

}
