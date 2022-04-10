package ru.masmirnov.stock;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import ru.masmirnov.stock.client.StocksClient;
import ru.masmirnov.stock.users.InMemoryUsersDao;

import static org.junit.Assert.*;

public class StocksHandlerTest {

    private static final double EPS = 1e-5;

    @ClassRule
    public static GenericContainer simpleWebServer = new FixedHostPortGenericContainer("stock-server:1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);


    private StocksClient client;
    private InMemoryUsersDao usersDao;


    @Before
    public void before() {
        client = new StocksClient("http://127.0.0.1", 8080);
        usersDao = new InMemoryUsersDao(client);

        int id0 = addCompany("Company_0", 100, 100);  // id = 0
        int id1 = addCompany("Company_1", 1, 10);     // id = 1
        int id2 = addCompany("Company_2", 5, 1000);   // id = 2
        assertEquals(id0, 0);
        assertEquals(id1, 1);
        assertEquals(id2, 2);
    }


    @Test
    public void addUsersTest() {
        int id0 = usersDao.addNewUser("User_0");
        int id1 = usersDao.addNewUser("User_1");
        assertEquals(id0, 0);
        assertEquals(id1, 1);
        assertTrue(usersDao.removeUser(0));
        assertTrue(usersDao.removeUser(1));
        int id2 = usersDao.addNewUser("User_2");
        assertEquals(id2, 2);
    }

    @Test
    public void testBuyAndSellStocks() {
        int id = usersDao.addNewUser("User");
        assertFalse(usersDao.sellStocks(id, 0, 1));
        assertFalse(usersDao.buyStocks(id, 0, 2));
        usersDao.addMoney(id, 199);
        assertFalse(usersDao.buyStocks(id, 0, 2));
        usersDao.addMoney(id, 1);
        assertTrue(usersDao.buyStocks(id, 0, 2));
        assertEquals(usersDao.getMoney(id), 0, EPS);
        usersDao.sellStocks(id, 0, 1);
        assertEquals(usersDao.getMoney(id), 100, EPS);
    }


    private int addCompany(String name, int stocksCount, int stocksPrice) {
        int id = client.addNewCompany(name);
        client.addStocks(id, stocksCount);
        client.setStocksPrice(id, stocksPrice);
        return id;
    }

}
