import com.szwujie.websocket.WebsocketApplication;
import com.szwujie.websocket.controller.AopController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebsocketApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
public class AopControllerTest {

    @Autowired
    private AopController aopController;

    @Test
    public void testAop() {
        this.aopController.testAop("myKey");
    }
    @Test
    public void testAfterThrowing() {
        this.aopController.testAfterThrowing("myKey");
    }
    @Test
    public void testAround() {
        this.aopController.testAround("myKey");
    }

}
