package servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "SSEServlet",
            urlPatterns = "/sse")
public class SSEServlet extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = -8581176112286075214L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        for (int i = 0 ; i < 5 ; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response.getWriter().write("data:" + i + "\n\n");
            response.getWriter().flush();
        }

    }


}
