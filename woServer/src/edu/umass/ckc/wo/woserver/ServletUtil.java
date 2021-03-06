package edu.umass.ckc.wo.woserver;

import edu.umass.ckc.wo.admin.PedMap;
import edu.umass.ckc.wo.mrcommon.Names;
import edu.umass.ckc.wo.smgr.SessionDemon;
import edu.umass.ckc.wo.tutor.Settings;
import edu.umass.ckc.wo.woreports.ClassStatusReporter;
import edu.umass.ckc.wo.woreports.StudentStatusReporter;
import org.jdom.DataConversionException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Date;
import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: marshall
 * Date: 7/30/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServletUtil {

    /**
     * Builds a URL that depends on whether its a development environment or a release environment.   If its a dev env, then
     * we pass in values for host, port, context so that it can build a URI + relativePath.   If its a release env,
     * it builds the URI from webContentPath + relativePath.   We need this because in dev environments,  Tomcat has to serve
     * some things (like the flash probplayer and html5problems and flash problems) because security issues are created if the browser
     * tries to load a url from Apache when the original page came from tomcat.
     * @param isDevEnv
     * @param host
     * @param port
     * @param context
     * @param webContentPath
     * @param relativePath
     * @return
     */
    public static String getURIForEnvironment (boolean isDevEnv, String host, String port, String context, String webContentPath, String relativePath) {
        if (isDevEnv) {
            return  "http://" + host + ":" + port  + context + "/" + relativePath;
        }
        else {
            return webContentPath + relativePath;
        }
    }

    public static void startSessionCleanupDemon (Connection conn) {
        new SessionDemon(conn).start();
        Timer t = new Timer(true);
        int dailyMS = 24 * 60 * 60 * 1000;
//            int dailyMS = 60 * 1000;


        // Every 24 hours (starting from now) this will look at classes that should get a status report and will send teacher
        // an email if they haven't received one recently (according to settings that they control)
        // It will also check to see if the students in the class have received an email recently (according to a setting)
        // and will send each student an email regarding their work in the class
        if (Settings.sendClassStatusEmail)
            t.schedule(new ClassStatusReporter(conn,Settings.mailServer),new Date(),dailyMS);
        if (Settings.sendStudentStatusEmail)
            t.schedule(new StudentStatusReporter(conn,Settings.mailServer),new Date(),dailyMS);
    }


    public static void initialize(ServletContext servletContext) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, DataConversionException {
        Settings.host = servletContext.getInitParameter(Names.HOST);
        Settings.port = servletContext.getInitParameter(Names.SERVLET_PORT);
        Settings.isDevelopmentEnv = Boolean.parseBoolean(servletContext.getInitParameter(Names.IS_DEVELOPMENT_ENVIRONMENT)); // boolean saying if we are using Tomcat for HTML5
        Settings.webContentPath =servletContext.getInitParameter(Names.WEB_CONTENT_PATH); // root of apache web content for mathspring

        String probPlayerPath =  servletContext.getInitParameter(Names.PROB_PLAYER_PATH);
        Settings.probplayerPath = getURIForEnvironment(Settings.isDevelopmentEnv,Settings.host,Settings.port,
                servletContext.getContextPath(),Settings.webContentPath, probPlayerPath);
        Settings.flashClientPath = Settings.webContentPath + servletContext.getInitParameter(Names.FLASH_CLIENT_PATH); // relative to the above
        String useHybrid= servletContext.getInitParameter(Names.USE_HYBRID_TUTOR);
        if (useHybrid != null)
            Settings.useHybridTutor = Boolean.parseBoolean(useHybrid);
        else Settings.useHybridTutor = false;

        Settings.html5Probs = servletContext.getInitParameter(Names.HTML5_PROBLEMS); // subdir path under WEB_CONTENT_PATH of html5 probs

        Settings.html5ProblemURI = ServletUtil.getURIForEnvironment(Settings.isDevelopmentEnv,Settings.host,Settings.port,
                servletContext.getContextPath(),Settings.webContentPath,Settings.html5Probs);
        // Flash client must be on same machine but can be served by other than servletEngine
        // (e.g. it is best served by apache)
        String pedagogiesFile = servletContext.getInitParameter(Names.PEDAGOGIES_FILE);
        if (Settings.pedagogyGroups == null) {
            InputStream str = servletContext.getResourceAsStream(pedagogiesFile);
            Settings.pedagogyGroups = new PedMap(str);
        }

        Settings.mailServer = servletContext.getInitParameter(Names.ERROR_SMTP_SERVER);


    }
}
