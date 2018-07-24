package exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import service.ServiceError;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @Autowired
    ServiceError serviceError;

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Exception ex) {
        serviceError.writeError(ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        return modelAndView;
    }
//
//    public static class A<T> {
//        T object;
//
//        <E> void setObject(E t) {
//            this.object = (T) t;
//        }
//    }
//
//    static class Sparrow extends Bird {
//    }
//
//    static class Bird {
//    }
//
//    public static void main(String[] args) {
//        List<? extends Bird> birds = new ArrayList<Sparrow>();
//        birds.contains("fd");
//    }


}
