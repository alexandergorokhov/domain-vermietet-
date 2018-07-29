package villagermanager.manager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

@Configuration
public class VillageDeviceManagerConfig {

    @Bean
    public  VilageDeviceManager villageDeviceManager(){
        return new  VilageDeviceManager();
    }

    @Bean
    public HashMap<Integer,Village> idVillageMap(){

        return new HashMap<Integer,Village>();
    }

}
