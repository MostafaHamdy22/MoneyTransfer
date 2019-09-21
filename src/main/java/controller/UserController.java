package controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import model.HttpRequest;
import model.User;
import service.UserService;
import wiremock.org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by mostafa on 2019-09-14
 */
@Slf4j
public class UserController extends HttpServlet {

    private static final Gson GSON = new Gson();
    private static final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final HttpRequest httpRequest = new HttpRequest(req);
        if (httpRequest.hasParameter("userId")) {
            try {
                User user = userService.getUserById(httpRequest.getParameter("userId"));
                resp.getWriter().write(GSON.toJson(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (httpRequest.hasParameter("accountId")) {
            try {
                User user = userService.getUserByAccountId(httpRequest.getParameter("accountId"));
                resp.getWriter().write(GSON.toJson(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpRequest httpRequest = new HttpRequest(req);
        User user = GSON.fromJson(httpRequest.getBody(), User.class);

        try {
            userService.addUser(user);
        } catch (Exception e) {
            log.info("Something went wrong {}", e.getMessage());
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }
}
