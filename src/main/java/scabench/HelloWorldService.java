package scabench;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple HelloWorld service. Request parameters are logged.
 * @author jens dietrich
 */
@WebServlet(name = "HelloWorld", urlPatterns = {"/hello"})
public class HelloWorldService extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // precondition enforcement -- no parameters expected
        Map<String,String[]> parameters = request.getParameterMap();
        if (!parameters.isEmpty()) {
            String parametersAsString = getParametersAsString(request);
            Logger logger = LogManager.getLogger(HelloWorldService.class);
            logger.error("unexpected parameters: " + parametersAsString);
            out.println("unexpected parameters, the problem has been logged");
        }
        else {
            out.println("hi");
        }

        out.close();
    }

    private String getParametersAsString(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
            .map(e -> e.getKey() + " -> " + Stream.of(e.getValue()).collect(Collectors.joining(",")))
            .collect(Collectors.joining(","));
    }
}
