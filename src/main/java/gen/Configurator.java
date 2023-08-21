package gen;



import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


public class Configurator {
    
    private Properties props = null;       
    private String dbURL = null;
    private String driverClass = null;
    private String username = null;
    private String password = null;        

    
    //private static Configurator configurator;

    /**
     * constructor which initialise all property from resourse property file
     */
    public Configurator() {
        // Load Properties    
    	init();
    }
    
    private static class SingletonHolder 
    { 
      private final static Configurator configurator = new Configurator();
    }

    public static Configurator getInstance()
    {
    	//return SingletonHolder.configurator;
    	return new Configurator();
    }

    private void init()
    {
    	 loadProps();
     }

    public String getProperty(String _key) {
        try {
            return props.getProperty(_key);
        } catch (Exception e) {
            System.out.println("There is no entry in property file for key " + _key + " and " + e.getMessage());
            return null;
        }
    }

    public void loadProps() {
        try {
              props = new Properties();
              props.load(new FileInputStream("/home/testingtomcat/webapps/GameOMania/WEB-INF/classes/getQueries"));
//            props.load(new FileInputStream("D:/Vijay/Projects/STS Service/gameomaniaportalupdated/gameomaniaportal/src/main/java/getQueries"));
//            props.load(new FileInputStream("conf/getQueries"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    
    public String getDbURL() {
        return dbURL;
    }   

    public String getDriverClass() {
        return driverClass;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }    

    public Properties getProps() {
        return props;
    }  
}
