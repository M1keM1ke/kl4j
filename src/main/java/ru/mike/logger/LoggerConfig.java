package ru.mike.logger;

import org.reflections.Reflections;
import ru.mike.logger.loader.ResourceLoader;
import ru.mike.logger.settings.Setting;
import ru.mike.logger.settings.level.LevelSetting;
import ru.mike.reflection.KReflections;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class LoggerConfig {
    private LoggerConfig config;

    public LoggerConfig getConfig() {
        return config;
    }

    private Properties properties;
    private final Path systemPropertiesPath = Paths.get("src/main/java/ru/mike/logger/resource/kl4j.properties");
    private final URL userPropertiesUrl = ClassLoader.getSystemClassLoader().getResource("testDeleteAfter/kl4j.properties");
    private final URL userYmlUrl = ClassLoader.getSystemClassLoader().getResource("kl4j.yml");
    private ResourceLoader resourceLoader = new ResourceLoader();
    private Set<Class<? extends Setting>> settingsClasses;
    private List<? extends Setting> propertyInstances;
    private List<? extends Setting> sortedInstances;
    private String resultLog;

    public LoggerConfig() {
        Reflections reflections = new Reflections("ru.mike.logger");
        settingsClasses = reflections.getSubTypesOf(Setting.class);
        propertyInstances = createPropertyInstances(settingsClasses);

        properties = resourceLoader.parseKl4jProperties(systemPropertiesPath);
        setupLoggerConfig();

        if (userPropertiesUrl != null && userYmlUrl != null) {
            throw new RuntimeException("Multiple logger config. Choose .properties or .yml settings");
        }

        if (userPropertiesUrl != null) {
            try {
                properties = resourceLoader.parseKl4jProperties(Path.of(userPropertiesUrl.toURI()));
                setupLoggerConfig();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if (userYmlUrl != null) {
            try {
                properties = resourceLoader.parseKl4jYaml(Path.of(userYmlUrl.toURI()));
                setupLoggerConfig();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        sortedInstances = propertyInstances.stream()
                .sorted(Comparator.comparing(Setting::getPriority))
                .collect(Collectors.toList());
    }

    public List<? extends Setting> getPropertyInstances() {
        return propertyInstances;
    }

    public List<? extends Setting> initLoggerConfiguration() {
        this.config = new LoggerConfig();
        return config.getPropertyInstances();
    }

    public void complementSettings(LogLevel level, String message) {
        LevelSetting levelSettingProperty = (LevelSetting) sortedInstances.stream()
                .filter(instance -> instance instanceof LevelSetting)
                .findFirst()
                .get()
        ;

        levelSettingProperty.setLocalLevel(level);

        resultLog = formResultLog(sortedInstances) + " " + message;
        System.out.println(resultLog);
    }

    private void setupLoggerSettings(Path loggerProperties) {

    }

    private void setupLoggerConfig() {
        setupSettings(propertyInstances);

    }

    private String formResultLog(List<? extends Setting> sortedInstances) {
        StringJoiner joiner = new StringJoiner(" ");
        for (Setting setting : sortedInstances) {
            String attend = setting.attend();
            joiner.add(attend);
        }
        return joiner.toString().replaceAll(" {2}", " ");
    }

    private List<? extends Setting> createPropertyInstances(Set<Class<? extends Setting>> settingsClasses) {
        return settingsClasses.stream()
                .map(entry -> (Setting) KReflections.newInstance(entry))
                .collect(Collectors.toList())
        ;
    }

    private void setupSettings(List<? extends Setting> settingsInstances) {
        properties
                .forEach((key, value) -> settingsInstances
                        .forEach(instance -> instance.setValue((String) key, (String) value)));
    }
}
