package ru.masmirnov.stock.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class StocksClient {

    private final String host;
    private final int port;
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(1))
            .build();

    public StocksClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private String sendGetRequestAndReceive(String requestUri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(requestUri))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200? response.body() : null;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer addNewCompany(String companyName) {
        String requestURI = String.format("%s:%s/add_new_company?companyName=%s", host, port, companyName);
        String response = sendGetRequestAndReceive(requestURI);
        return response == null? null : Integer.parseInt(response);
    }

    public boolean buyStocks(int companyId, int count) {
        String requestURI = String.format("%s:%d/buy_stocks?companyId=%d&count=%d", host, port, companyId, count);
        return sendGetRequestAndReceive(requestURI) != null;
    }

    public Double getStocksPrice(int companyId) {
        String requestURI = String.format("%s:%s/get_stocks_price?companyId=%d", host, port, companyId);
        String response = sendGetRequestAndReceive(requestURI);
        return response == null? null : Double.parseDouble(response);
    }

    public Integer getStocksCount(int companyId) {
        String requestURI = String.format("%s:%s/get_stocks_count?companyId=%d", host, port, companyId);
        String response = sendGetRequestAndReceive(requestURI);
        return response == null? null : Integer.parseInt(response);
    }

    public void setStocksPrice(int companyId, double price) {
        String requestURI = String.format("%s:%s/set_stocks_price?companyId=%d&price=%f", host, port, companyId, price);
        sendGetRequestAndReceive(requestURI);
    }

    public void addStocks(int companyId, int count) {
        String requestURI = String.format("%s:%s/add_stocks?companyId=%d&count=%d", host, port, companyId, count);
        sendGetRequestAndReceive(requestURI);
    }

    public boolean sellStocks(int companyId, int count) {
        String requestURI = String.format("%s:%d/sell_stocks?companyId=%d&count=%d", host, port, companyId, count);
        return sendGetRequestAndReceive(requestURI) != null;
    }

}
