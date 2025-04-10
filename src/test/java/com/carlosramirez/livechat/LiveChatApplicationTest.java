package com.carlosramirez.livechat;

import java.nio.file.Files;
import java.nio.file.Paths;

public class LiveChatApplicationTest {

    protected String readJsonFile(String filePath) throws Exception {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
