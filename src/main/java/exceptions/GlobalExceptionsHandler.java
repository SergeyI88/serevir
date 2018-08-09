package exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import service.ServiceError;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @Autowired
    ServiceError serviceError;

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(ex.getStackTrace()).forEach(t -> stringBuilder.append(t.toString() + "\n"));
        serviceError.writeError(ex.toString() + "\n" + stringBuilder, request.getSession().getAttribute("token"));
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg", ex.getLocalizedMessage());
        return modelAndView;
    }
}
