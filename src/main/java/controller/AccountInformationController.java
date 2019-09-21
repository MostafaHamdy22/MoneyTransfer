package controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import model.AccountInformation;
import model.HttpRequest;
import service.AccountInformationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by mostafa on 2019-09-15
 */
@Slf4j
public class AccountInformationController extends HttpServlet {

    private static final Gson GSON = new Gson();
    private static final AccountInformationService accountInformationService =
        AccountInformationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final HttpRequest httpRequest = new HttpRequest(req);
        if (httpRequest.hasParameter("accountId")) {
            try {
                AccountInformation accountInformation =
                    accountInformationService.getAccountInformation(httpRequest.getParameter(
                        "accountId"));

                resp.getWriter().write(GSON.toJson(accountInformation));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
