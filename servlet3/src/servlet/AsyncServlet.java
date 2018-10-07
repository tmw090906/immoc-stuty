package servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "syncServlet",
            urlPatterns = "/async",
            asyncSupported = true)
public class AsyncServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        Long startTime = System.currentTimeMillis();

        // 开启异步
        AsyncContext context = request.startAsync();

        CompletableFuture.runAsync( () ->
                doSomeThing(context,
                        context.getRequest(),
                        context.getResponse()));

        System.out.println("sync use:" + (System.currentTimeMillis() - startTime));

    }

    private void doSomeThing(AsyncContext context,
                             ServletRequest request,
                             ServletResponse response) {
        // 模拟耗时操作
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().append("done");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 业务代码处理完毕 , 通知结束
        // 其实这里就是响应式?
        // 和Netty模式很相似 : Sevlet线程是用来接收消息bossGroup和workerGroup
        context.complete();
    }


}
