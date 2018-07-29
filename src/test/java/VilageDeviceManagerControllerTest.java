import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import villagermanager.App;
import villagermanager.manager.Device;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class VilageDeviceManagerControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void durationTest() throws Exception {
        mockMvc.perform(post("/counter_callback")
                .content(String.valueOf(TestUtils.getJsonCounter()))
                .contentType(contentType));
        mockMvc.perform(post("/counter_callback")
                .content(String.valueOf(TestUtils.getJsonCounter()))
                .contentType(contentType));
        mockMvc.perform(get("/consumption_report?duration=" + String.valueOf(TestUtils.getDuration()))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.villages[*].consumption", containsInAnyOrder(20D)));
        ;

    }

    @Test
    public void counterCallBackTest() throws Exception {
        mockMvc.perform(post("/counter_callback")
                .content(String.valueOf(TestUtils.getJsonCounter()))
                .contentType(contentType))
                .andExpect(status().isCreated());
    }

    @Test
    public void counterTest() throws Exception {
        Device device = TestUtils.getElectricCounter();
        mockMvc.perform(post("/counter_callback")
                .content(String.valueOf(TestUtils.getJsonCounter()))
                .contentType(contentType));
        mockMvc.perform(get("/counter?id=" + device.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.village", anyOf(is("Villaabajo")
                        , is("Villaarriba")
                        , is("Villamedio"))));

    }

}
