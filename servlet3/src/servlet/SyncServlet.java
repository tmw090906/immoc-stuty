package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "syncServlet",
            urlPatterns = "/sync")
public class SyncServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        Long startTime = System.currentTimeMillis();

        doSomeThing(request, response);

        System.out.println("sync use:" + (System.currentTimeMillis() - startTime));

    }

    private void doSomeThing(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 模拟耗时操作
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response.getWriter().append("done");

    }
}
