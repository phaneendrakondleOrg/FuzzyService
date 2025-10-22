package org.example;

import Model.MyModel;
import com.google.gson.Gson;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.InvokerTransformer;
import org.apache.commons.collections4.map.LazyMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;

import com.google.common.io.Files;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) {
        logger.info("Application started with dependencies!");
        System.out.println("Hello, World!");
        processFileName("myFile.txt");
        useStringUtils("  test  ");
        fileUploadExample();
        FileOperations fo = new FileOperations();
        fo.fewMore();

        useGuava();
        useJackson();
        useCommonsNet();
        Transformer<String, String> transformer = new InvokerTransformer<>(
                "toString",
                new Class[]{},
                new Object[]{}
        );
        
        Map<String, String> lazyMap = LazyMap.lazyMap(new HashMap<String, String>(), transformer);
        lazyMap.put("key", "value");

        try {
            System.out.println("Server is running. Press Ctrl+C to stop.");
            Thread.currentThread().join(); // Keeps the main thread alive
        } catch (InterruptedException e) {
            logger.warning("Server interrupted: " + e.getMessage());
        }
    }

    public void performComplexOperations() {
        Gson gson = new Gson();
        String jsonBad = "{\"dateField\":\"invalid-date-format\"}"; // Test serialization issues

        try {
            MyModel myModel = gson.fromJson(jsonBad, MyModel.class); // Considering breaking changes in handling of dates
        } catch (Exception e) {
            logger.info("Error in processing due to compatibility");
        }

        ConfigurationBuilder<?> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        builder.add(builder.newRootLogger(Level.ERROR));  // Reflect how configurations may fail across major upgrades
    }

    public static void processFileName(String name) {
        System.out.println("Processing file: " + StringUtils.strip(name));
    }

    public static void useStringUtils(String text) {
        System.out.println("Stripped string: '" + StringUtils.strip(text) + "'");
    }

    public static void fileUploadExample() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        factory.setRepository(tempDir);
        // We are not actually processing a request here, just instantiating
        System.out.println("File upload factory created.");
    }

    public static void useGuava() {
        try {
            File tempFile = File.createTempFile("demo", ".tmp");
            System.out.println("Using Guava for temp file operations");
            byte[] content = "Demo content".getBytes();
            Files.write(content, tempFile);
            System.out.println("Guava file operations completed");
        } catch (Exception e) {
            System.out.println("Guava operation failed: " + e.getMessage());
        }
    }

    public static void useJackson() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInput = "{\"name\":\"test\",\"value\":\"demo\"}";
        try {
            Object result = mapper.readValue(jsonInput, Object.class);
            System.out.println("Jackson deserialization completed: " + result);
        } catch (JsonProcessingException e) {
            System.out.println("Jackson processing failed: " + e.getMessage());
        }
    }

    public static void useCommonsNet() {
        FTPClient ftpClient = new FTPClient();
        System.out.println("FTP Client initialized with vulnerable Commons Net version");
        try {
            ftpClient.connect("demo.server.com", 21);
        } catch (Exception e) {
            System.out.println("FTP connection demo completed (expected to fail): " + e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                // Ignore cleanup errors in demo
            }
        }
    }
}