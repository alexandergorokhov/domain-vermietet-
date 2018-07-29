package villagermanager.manager;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ElectricCounter extends Device {
    private Double amount;

    @Override
    public void compute(Double amount) {
        this.getTimeMap().put(LocalDateTime.now(), amount);
    }

    @Override
    public Double totalConsumption(Integer duration) {

        return this.getTimeMap().tailMap(LocalDateTime.now()
                .minusHours(duration))
                .values()
                .stream()
                .mapToDouble(i -> i)
                .sum();
    }
}
