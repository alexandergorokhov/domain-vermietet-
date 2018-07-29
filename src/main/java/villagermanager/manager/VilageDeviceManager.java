package villagermanager.manager;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Getter
public class VilageDeviceManager {

    private static final List <String> villageNames = Arrays.asList("Villaarriba","Villamedio", "Villaabajo");
    private  HashMap<Integer,Village>  idVillageMap;
    private static final List <Village >villages = createVillages();


    @Autowired
    private void setIdVillageMap(HashMap idVillageMap){
        this.idVillageMap = idVillageMap;
    }

    public Village randomVillageName(){
        return villages.get(new Random().nextInt(villages.size()));
    }

    private static List createVillages(){
        ArrayList<Village> villages = new ArrayList<>();

        for (String name: villageNames){
            Village village = new Village();
            village.setVillageName(name);
            village.setIdDeviceMap(new HashMap <Integer,Device>());
            villages.add(village);
        }

     return villages;
    }

}
