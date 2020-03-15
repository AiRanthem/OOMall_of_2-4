package xmu.oomall.zuul;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.zuul.dao.ZuulDao;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = ZuulApplication.class)
@Transactional
public class DaoTest {

//    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ZuulDao.class);

    @Autowired
    ZuulDao dao;

    @Test
    @Rollback
    void getTableTest() throws Exception {
        Map<String, List<Integer>> post = dao.getAuthTable("POST");

        //System.out.println(post);

        for (String url : post.keySet()) {
            System.out.println("KEY: " + url + " ROLES: " + post.get(url).toString());
        }
    }
}
