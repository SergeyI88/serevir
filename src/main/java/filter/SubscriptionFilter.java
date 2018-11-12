package filter;

import db.entity.Client;
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
        Client client = clientService.getClientByTokenAndUpdateWasAlert(httpServletRequest.getParameter("token"));
        if (httpServletRequest.getSession().getAttribute("sub") != null
                && (Boolean) httpServletRequest.getSession().getAttribute("sub")) {
//            isAlert(client, httpServletRequest);
            chain.doFilter(request, response);
        } else if (client.isEnabled() == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            httpServletRequest.getSession().setAttribute("sub", Boolean.FALSE);
            response.getWriter().write("Некорректный токеy Эвотор, попробуйте переустановить программу!");
        } else if (!client.isEnabled()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            httpServletRequest.getSession().setAttribute("sub", Boolean.FALSE);
            response.getWriter().write("У вас не продлена подписка");
        } else {
            httpServletRequest.getSession().setAttribute("sub", Boolean.TRUE);
            isAlert(client, httpServletRequest);
            chain.doFilter(request, response);
        }
    }

    private void isAlert(Client client, HttpServletRequest httpServletRequest) {
        if (!client.isWasAlert()) {
            httpServletRequest.setAttribute("alert", true);
        }

    }

    @Override
    public void destroy() {
    }
}
