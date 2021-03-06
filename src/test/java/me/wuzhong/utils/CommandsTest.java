package me.wuzhong.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import me.wuzhong.utils.Commands.ProcessParams;
import me.wuzhong.utils.result.Result;
import org.junit.Test;

/**
 * @author wuzhong on 2017/12/31.
 * @version 1.0
 */
public class CommandsTest {

    @Test
    public void execute() {

        ProcessParams params =
            ProcessParams.builder().cmd("./gradlew clean --offline")
                .workspace(new File("/Users/wuzhong/workspace/taobao_android/MainBuilder"))
                .logMessage(true).saveMessage(true).build();

        Result<String> execute = Commands.execute(params);

        System.out.println(execute.getError().getDescription());
        //System.out.println(execute.getResult());

    }


    @Test
    public void execute2() {

        ProcessParams params =
            ProcessParams.builder().cmd("./gradlew clean")
                .workspace(new File("/Users/wuzhong/workspace/taobao_android/MainBuilder"))
                .saveCmdToScript(true).logFile(new File("/tmp/script_log"))
                .logMessage(true).saveMessage(true).build();

        Result<String> execute = Commands.execute(params);

        System.out.println(execute.getError().getDescription());
        //System.out.println(execute.getResult());

    }


    @Test
    public void test2() throws IOException {
        com.google.common.io.Files.asCharSink(new File("/tmp/test2"), Charset.forName("UTF-8")).write("hello world");
    }



}