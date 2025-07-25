package twitter4j.conf;

class PropertyConfigurationFactory implements ConfigurationFactory {
    private static final PropertyConfiguration ROOT_CONFIGURATION = new PropertyConfiguration();

    PropertyConfigurationFactory() {
    }

    public void dispose() {
    }

    public Configuration getInstance() {
        return ROOT_CONFIGURATION;
    }

    public Configuration getInstance(String str) {
        PropertyConfiguration propertyConfiguration = new PropertyConfiguration(str);
        propertyConfiguration.dumpConfiguration();
        return propertyConfiguration;
    }
}
