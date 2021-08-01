/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.listener;

 
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * Web application lifecycle listener.
 *
 * @author Admin
 */
@WebListener()
public class ServletContextListener implements javax.servlet.ServletContextListener {
    

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("/") + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);
    }
    

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         //To change body of generated methods, choose Tools | Templates.
    }
}
