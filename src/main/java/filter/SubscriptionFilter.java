package filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import service.ClientService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/main/")
public class SubscriptionFilter implements Filter {

    ClientService clientService;

    public void init(FilterConfig cfg) {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(cfg.getServletContext());
        this.clientService = ctx.getBean(ClientService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(clientService);
        System.out.println("in filter " + request.getParameter("token") );
        if (clientService.getSubscription(request.getParameter("token"))) {
            chain.doFilter(request, response);
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("У вас не продлена подписка");
        }
    }

    @Override
    public void destroy() {

    }
}
