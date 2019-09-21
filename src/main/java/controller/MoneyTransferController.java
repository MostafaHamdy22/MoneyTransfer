package controller;

import com.google.gson.Gson;
import facade.MoneyTransferFacade;
import lombok.extern.slf4j.Slf4j;
import model.HttpRequest;
import model.MoneyTransferRequest;
import wiremock.org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by mostafa on 2019-09-15
 */
@Slf4j
public class MoneyTransferController extends HttpServlet {

    private static final Gson GSON = new Gson();
    private static final MoneyTransferFacade moneyTransferFacade =
        MoneyTransferFacade.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpRequest httpRequest = new HttpRequest(req);
        MoneyTransferRequest moneyTransferRequest = GSON.fromJson(httpRequest.getBody(),
            MoneyTransferRequest.class);

        try {
            moneyTransferFacade.transferMoney(moneyTransferRequest);
        } catch (Exception e) {
            log.info("Something went wrong {}", e.getMessage());
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }
}
