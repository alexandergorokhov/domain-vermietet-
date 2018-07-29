package villagermanager.manager;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;

@Getter
@Setter
public class Village {
    private String name;

    private HashMap<Integer, Device> idDeviceMap;

    public void setIdDeviceMap(HashMap idDeviceMap) {
        this.idDeviceMap = idDeviceMap;
    }

    public void setVillageName(String name) {
        this.name = name;
    }

    public Device registerDevice(Device device) {
        idDeviceMap.putIfAbsent(device.getId(), device);
        if (Objects.isNull(idDeviceMap.get(device.getId()).getTimeMap())) {
            device.setTimeMap(new TreeMap<>());
        }
        return idDeviceMap.get(device.getId());

    }

    public Double totalConsumption(Integer duration) {
        return idDeviceMap
                .values()
                .stream()
                .mapToDouble(i -> i.totalConsumption(duration))
                .sum();
    }

}
