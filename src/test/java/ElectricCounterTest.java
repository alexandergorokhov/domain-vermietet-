import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import villagermanager.manager.ElectricCounter;

import java.time.LocalDateTime;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class ElectricCounterTest {

    private ElectricCounter counter ;

    @Before
    public void setup() throws Exception {
        this.counter = TestUtils.getElectricCounter();
        this.counter.setTimeMap(new TreeMap<>());
    }

    @Test
    public void consumptionTest(){
        counter.getTimeMap().put(LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond()
                ),10D);
        counter.getTimeMap().put(LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().minusHours(5).getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond()),10D);
        counter.getTimeMap().put(LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().minusHours(10).getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond()),10D);
        Assert.assertEquals(counter.totalConsumption(15),30D,0D);
        Assert.assertEquals(counter.totalConsumption(10),20D,0D);
        Assert.assertEquals(counter.totalConsumption(3),10D,0D);
    }

}
