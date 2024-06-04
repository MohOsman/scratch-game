package org.scratchgame.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.scratchgame.model.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationReader {
    private final ObjectMapper objectMapper;

    public ConfigurationReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Config readConfig(String filename) throws IOException {
        System.out.println(filename);
        return objectMapper.readValue(new File(filename), Config.class);
    }




}
