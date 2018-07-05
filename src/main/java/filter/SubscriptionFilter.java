//package filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import service.ClientService;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//
//@Component
//@WebFilter("/")
//public class SubscriptionFilter implements Filter {
//    @Autowired
//    ClientService clientService;
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println(clientService);
//        System.out.println("in filter " + request.getParameter("token") );
//        if (clientService.getSubscription(request.getParameter("token"))) {
//            chain.doFilter(request, response);
//        } else {
//            response.getWriter().write("У вас не продлена подпписка");
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
