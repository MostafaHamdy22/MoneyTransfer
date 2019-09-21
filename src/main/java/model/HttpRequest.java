package model;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Create by mostafa on 2019-09-14
 */
@Data
public class HttpRequest {

    private HashMap<String, String> requestParameter;
    private String body;


    public HttpRequest(HttpServletRequest httpServletRequest) throws IOException {
        requestParameter = new HashMap<>();
        extractRequestParam(httpServletRequest);
        body = extractBody(httpServletRequest);
    }

    public boolean hasParameter(String parameterName) {
        return requestParameter.containsKey(requestParameter);
    }

    public String getParameter(String parameterName) {
        return requestParameter.getOrDefault(requestParameter, "");
    }

    private void extractRequestParam(HttpServletRequest httpServletRequest) {
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = httpServletRequest.getParameter(paramName);
            requestParameter.put(paramName, paramValue);
        }
    }

    private String extractBody(HttpServletRequest httpServletRequest) throws IOException {
        return httpServletRequest.getReader().lines()
            .reduce("", (accumulator, actual) -> accumulator + actual);
    }
}
