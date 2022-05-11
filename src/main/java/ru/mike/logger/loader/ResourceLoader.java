package ru.mike.logger.loader;

import org.yaml.snakeyaml.Yaml;
import ru.mike.logger.resource.LoggerProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ResourceLoader {

    public ResourceLoader() {}

    public Properties parseKl4jProperties(Path pathToProperties) {
        return removeLoggerPrefix(parseProperties(pathToProperties));
    }

    public Properties parseKl4jProperties() {
        return removeLoggerPrefix(parseProperties());
    }

    public Properties parseKl4jYaml(Path pathToYaml) {
        Yaml yaml = new Yaml();
        try (InputStream in = Files.newInputStream(Path.of(ClassLoader.getSystemClassLoader().getResource(pathToYaml.getFileName().toString()).toURI()))) {
            TreeMap<String, Map<String, Object>> config = yaml.loadAs(in, TreeMap.class);
            return removeLoggerPrefix(YamlConverter.toProperties(config));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Unexpected error when parse yml to properties");
        }
    }

    private Properties removeLoggerPrefix(Properties properties) {
        Map<String, String> refactorPropertiesMap = properties
                .entrySet()
                .stream()
                .map(entry -> {
                    String newKey = entry.getKey().toString().replaceAll("logger.", "");
                    return new String[]{newKey, (String) entry.getValue()};
                })
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]))
        ;

        Properties refactoredProperties = new Properties();
        refactoredProperties.putAll(refactorPropertiesMap);

        return refactoredProperties;
    }

    private Properties parseProperties(Path path) {
        Properties properties = new Properties();

        try(InputStream is = new FileInputStream(path.toFile())) {
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties");
        }
    }

    private Properties parseProperties() {
        Properties properties = new Properties();

        try {
            LoggerProperties.getProperties().forEach(lp -> properties.put(lp.getName(), lp.getValue()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
