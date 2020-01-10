import com.szwujie.websocket.WebsocketApplication;
import com.szwujie.websocket.controller.AopTestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebsocketApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
public class AopController2Test {

    @Autowired
    private AopTestController aopController2;

    @Test
    public void testAnnotation() {
        this.aopController2.testAnnotation("myKey");
    }

}
