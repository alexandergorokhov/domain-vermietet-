package villagermanager.managerController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import villagermanager.manager.Device;
import villagermanager.manager.ElectricCounter;
import villagermanager.manager.VilageDeviceManager;
import villagermanager.manager.Village;

import java.util.HashMap;
import java.util.Objects;


@RestController
@RequestMapping
public class VilageDeviceManagerController {

    @Autowired
    VilageDeviceManager vilageDeviceManager;

    @RequestMapping(value = "/counter_callback", method = RequestMethod.POST)
    public ResponseEntity registerElectricCounter(@RequestBody ElectricCounter electricCounter) {
        try {
            if (!Objects.isNull(electricCounter) && !Objects.isNull(electricCounter.getId()) && !Objects.isNull(electricCounter.getAmount())) {
                HashMap<Integer, Village> idVillageMap = vilageDeviceManager.getIdVillageMap();

                idVillageMap.putIfAbsent(electricCounter.getId(), vilageDeviceManager.randomVillageName());

                Village village = idVillageMap.get(electricCounter.getId());
                Device device = village.registerDevice(electricCounter);

                device.compute(electricCounter.getAmount());
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @RequestMapping(value = "/counter", method = RequestMethod.GET)
    public ResponseEntity getDevice(@RequestParam(value = "id") Integer id) {
        HashMap<Integer, Village> idVillageMap = vilageDeviceManager.getIdVillageMap();
        if (idVillageMap.containsKey(id)) {

            Village village = idVillageMap.get(id);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();

            response.put("id", id);
            response.put("village", village.getName());

            return new ResponseEntity(response, HttpStatus.OK);
        } else {
            return new ResponseEntity("Counter does not exist", HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/consumption_report", method = RequestMethod.GET)
    public ResponseEntity getConsumptionReport(@RequestParam(value = "duration") Integer duration) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode response = mapper.createObjectNode();
            response.putPOJO("villages", arrayNode);

            vilageDeviceManager.getIdVillageMap().
                    values().
                    stream().
                    distinct().
                    forEach(v -> {

                        ObjectNode consumptionVillage = mapper.createObjectNode();
                        consumptionVillage.put("village_name", v.getName());
                        consumptionVillage.put("consumption", v.totalConsumption(duration));
                        arrayNode.add(consumptionVillage);
                    });
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
