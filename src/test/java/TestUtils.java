import javafx.scene.control.RadioMenuItem;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import villagermanager.manager.ElectricCounter;

import java.nio.charset.Charset;
import java.util.Random;

public class TestUtils {
    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static JSONObject getJsonCounter() throws JSONException {
        JSONObject counter = new JSONObject();
        counter.put("amount","10");
        counter.put("id","1");
        return counter;
    }

    public static ElectricCounter getElectricCounter(){
        ElectricCounter counter = new ElectricCounter();
        counter.setAmount(10D);
        counter.setId(1);
        return counter;
    }

    public static Integer getDuration(){
        return Integer.valueOf(1);
    }

    public static ElectricCounter getElectricCounterRandomId(){
        ElectricCounter counter = new ElectricCounter();
        Random random = new Random();
        counter.setAmount(10D);
        counter.setId(random.nextInt(10));
        return counter;
    }
}
