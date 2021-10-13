package ru.masmirnov.sd.refactoring;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainTest {

    private static final String SERVER = "http://localhost:8081/";
    private static final Random RND = new Random();
    private static final int RANDOM_NAME_BOUND = 1_000_000_000;

    private static final int SUM_IDX = 2, COUNT_IDX = 3;
    private static final int MIN_MAX_PRODUCT_IDX = 4, MIN_MAX_PRICE_IDX = 5;


    private static Thread serverThread;

    @BeforeAll
    static void startServer() {
        serverThread = new Thread(() -> {
            try {
                Main.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @AfterAll
    static void stopServer() {
        serverThread.interrupt();
    }


    @Test
    public void productsListTest() throws IOException {
        Product new1 = new Product(randomProductName(), 123);
        Product new2 = new Product(randomProductName(), 456);
        execute("add-product?name=" + new1.getName() + "&price=" + new1.getPrice());
        execute("add-product?name=" + new2.getName() + "&price=" + new2.getPrice());

        Document doc = execute("get-products");
        List<Product> products = parseProducts(doc.body().text());

        Assertions.assertTrue(products.contains(new1));
        Assertions.assertTrue(products.contains(new2));
    }

    @Test
    public void productsCountTest() throws IOException {
        Document oldProductsDoc = execute("get-products");
        Document oldCountDoc = execute("query?command=count");
        List<Product> oldProducts = parseProducts(oldProductsDoc.body().text());
        long oldCount = extractNumber(oldCountDoc, 3);

        Assertions.assertEquals(oldProducts.size(), oldCount);

        execute("add-product?name=" + randomProductName() + "&price=" + 111);
        execute("add-product?name=" + randomProductName() + "&price=" + 222);

        Document newProductsDoc = execute("get-products");
        Document newCountDoc = execute("query?command=count");
        List<Product> newProducts = parseProducts(newProductsDoc.body().text());
        long newCount = extractNumber(newCountDoc, COUNT_IDX);

        Assertions.assertEquals(newProducts.size(), newCount);
        Assertions.assertEquals(newCount, oldCount + 2);
    }

    @Test
    public void sumIncreaseTest() throws IOException {
        Document oldDoc = execute("query?command=sum");
        long oldSum = extractNumber(oldDoc, SUM_IDX);

        execute("add-product?name=" + randomProductName() + "&price=" + 100);
        execute("add-product?name=" + randomProductName() + "&price=" + 200);
        execute("add-product?name=" + randomProductName() + "&price=" + 300);

        Document newDoc = execute("query?command=sum");
        long newSum = extractNumber(newDoc, SUM_IDX);

        Assertions.assertEquals(newSum, oldSum + 600L);
    }

    @Test
    public void minMaxTest() throws IOException {
        execute("add-product?name=" + randomProductName() + "&price=" + 0);
        execute("add-product?name=" + randomProductName() + "&price=" + 0);
        execute("add-product?name=" + randomProductName() + "&price=" + Integer.MAX_VALUE);
        execute("add-product?name=" + randomProductName() + "&price=" + Integer.MAX_VALUE);

        Document allDoc = execute("get-products");
        List<Product> all = parseProducts(allDoc.body().text());
        List<Long> prices = all.stream().map(Product::getPrice).collect(Collectors.toList());

        Document minDoc = execute("query?command=min");
        Document maxDoc = execute("query?command=max");
        Product min = new Product(
                extractString(minDoc, MIN_MAX_PRODUCT_IDX),
                extractNumber(minDoc, MIN_MAX_PRICE_IDX));
        Product max = new Product(
                extractString(maxDoc, MIN_MAX_PRODUCT_IDX),
                extractNumber(maxDoc, MIN_MAX_PRICE_IDX));

        Assertions.assertTrue(all.contains(min));
        Assertions.assertTrue(all.contains(max));
        for (long price : prices) {
            Assertions.assertTrue(price >= min.getPrice());
            Assertions.assertTrue(price <= max.getPrice());
        }
    }


    private static String randomProductName() {
        return "Product-" + RND.nextInt(RANDOM_NAME_BOUND);
    }

    private static Document execute(String method) throws IOException {
        return Jsoup.connect(SERVER + method).get();
    }

    private static List<Product> parseProducts(String raw) {
        List<Product> products = new ArrayList<>();
        String[] words = raw.split("\\s+");
        for (int i = 0; i < words.length / 2; i++)
            products.add(new Product(words[2 * i], Long.parseLong(words[2 * i + 1])));
        return products;
    }

    private static String extractString(Document doc, int index) {
        return doc.body().text().split("\\s+")[index];
    }

    private static long extractNumber(Document doc, int index) {
        return Long.parseLong(extractString(doc, index));
    }
    
}
