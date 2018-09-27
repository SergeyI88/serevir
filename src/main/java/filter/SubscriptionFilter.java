package filter;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import service.ClientService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/")
public class SubscriptionFilter implements Filter {

    private ClientService clientService;

    public void init(FilterConfig cfg) {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(cfg.getServletContext());
        this.clientService = ctx.getBean(ClientService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(clientService);
        System.out.println("in filter " + request.getParameter("token"));
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getSession().getAttribute("sub") != null
                && (Boolean) httpServletRequest.getSession().getAttribute("sub")) {
            chain.doFilter(request, response);
        } else if (!clientService.getSubscription(request.getParameter("token"))) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            httpServletRequest.getSession().setAttribute("sub", Boolean.FALSE);
            response.getWriter().write("У вас не продлена подписка");
        } else {
            httpServletRequest.getSession().setAttribute("sub", Boolean.TRUE);
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
