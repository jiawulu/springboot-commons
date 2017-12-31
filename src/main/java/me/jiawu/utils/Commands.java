package me.jiawu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.io.Files;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.jiawu.utils.result.AppError;
import me.jiawu.utils.result.Result;
import org.apache.commons.lang.StringUtils;

/**
 * @author wuzhong on 2017/9/22.
 * @version 1.0
 */
@Slf4j
public class Commands {

    private static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    //public static Result<String> execute(String cmd, File workspace, Map<String, String> env) {
    //
    //    String tmpScriptFile = "mhub_tmp_script.sh";
    //    String tmpScriptSuccessFile = ".emas_build_success";
    //    if (null == workspace) {
    //        workspace = new File("");
    //    }
    //    File tmpScript = new File(workspace, tmpScriptFile);
    //    File tmpSuccessScript = new File(workspace, tmpScriptSuccessFile);
    //
    //    try {
    //
    //        tmpScript.delete();
    //        tmpSuccessScript.delete();
    //
    //        cmd = "set -e\n" + cmd + "\n echo _emas_success_ > .emas_build_success";
    //
    //        Files.write(cmd, tmpScript, Charset.defaultCharset());
    //
    //        //FileUtils.write(tmpScript, cmd);
    //    } catch (IOException e) {
    //        return Result.err(new EmasError(0, e.getLocalizedMessage()), "get error when create tmp script file ");
    //    }
    //
    //    String newCommand = "sh " + tmpScriptFile;
    //    if (null != redirectPath && !redirectPath.isEmpty()) {
    //        newCommand += " >> " + redirectPath;
    //        //newCommand += " 2>&1 |tee -a " + redirectPath ;
    //    }
    //    newCommand += " 2>&1";
    //    log.info("new command is {}", newCommand);
    //    //String cmd = "ls \n date \n pwd";
    //    ProcessBuilder pb = ProcessHelper.createBuilderBy(newCommand);
    //
    //    if (null != env) {
    //        pb.environment().putAll(env);
    //    }
    //
    //    if (null != workspace) {
    //        pb.directory(workspace);
    //    }
    //    Result<String> result = Result.ok("");
    //
    //    try {
    //        Profiler.start();
    //        Profiler.enter("execute cmd:" + cmd);
    //
    //        Process p = pb.start();
    //
    //        if (null != processListener) {
    //            processListener.setProcess(p);
    //        }
    //        if (p.waitFor() != 0) {
    //            result.setError(new EmasError(0, "execute " + cmd + "error"));
    //        }
    //
    //        if (!tmpSuccessScript.exists()) {
    //            result.setError(new EmasError(0, "execute " + cmd + "error"));
    //        }
    //    } catch (Throwable e) {
    //        log.error("execute command failed.");
    //        e.printStackTrace();
    //        result.setError(new EmasError(0, ExceptionUtil.getDetail(e)));
    //    } finally {
    //        Profiler.release();
    //        log.info(Profiler.dump());
    //    }
    //
    //    return result;
    //
    //}

    public static Result<String> execute(ProcessParams processParams) {

        //String cmd = "ls \n date \n pwd";

        try {
            Profiler.start();
            Profiler.enter("execute cmd:" + processParams.getCmd());

            ProcessExecutor processExecutor = new ProcessExecutor(processParams);

            return processExecutor.startAndWait();

        } finally {
            Profiler.release();
            log.info(Profiler.dump());
        }

    }

    @Builder
    @Getter
    public static class ProcessParams {

        private String cmd;

        private File workspace;

        private File logFile;

        private boolean saveCmdToScript;

        private boolean logMessage;

        private boolean saveMessage;

        private Map<String, String> env = new HashMap<>();

        private ProcessListener processListener;

        public static interface ProcessListener {
            /**
             * process进程启动
             *
             * @param process
             */
            public void onProcessStarted(Process process);

            /**
             * process进程结束
             *
             * @param process
             */
            public void onProcessFinish(Process process);
        }
    }

    @Slf4j
    private static class ProcessExecutor {

        private static String SCRIPT_TEMPLATE = "set -e\n%s\n echo _success_ > .commands_result";
        private static String CMD_TEMPLATE = "%s >> %s 2>&1";

        private ProcessBuilder processBuilder;
        private ProcessParams params;

        public ProcessExecutor(ProcessBuilder processBuilder, ProcessParams params) {
            this.processBuilder = processBuilder;
            this.params = params;
        }

        public ProcessExecutor(ProcessParams params) {
            this.params = params;

            //TODO execute bash script
            ProcessBuilder pb = getProcessBuilder(params);

            if (null != params.getEnv()) {
                pb.environment().putAll(params.getEnv());
            }

            if (null != params.getWorkspace()) {
                pb.directory(params.getWorkspace());
            }

            this.processBuilder = pb;

        }

        private ProcessBuilder getProcessBuilder(ProcessParams params) {
            if (params.isSaveCmdToScript()) {
                File tmplateFile = new File(params.workspace, ".commands_script.sh");
                File successFile = new File(params.workspace, ".commands_result");
                tmplateFile.delete();
                successFile.delete();
                String template = String.format(SCRIPT_TEMPLATE, params.getCmd());
                try {
                    Files.asCharSink(tmplateFile, Charset.forName("UTF-8")).write(template);
                    //Files.touch(successFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tmplateFile.setExecutable(true);
                String cmd = String.format(CMD_TEMPLATE, tmplateFile.getAbsolutePath(),
                                           params.getLogFile().getAbsolutePath());
                return new ProcessBuilder("/bin/bash", "-c", cmd);
            }
            return new ProcessBuilder("/bin/bash", "-c", params.getCmd());
        }

        public Result<String> startAndWait() {

            try {

                final Process p = processBuilder.start();

                if (null != params.processListener) {
                    params.processListener.onProcessStarted(p);
                }

                Future<List<String>> inputFuture = sExecutorService.submit(
                    new StreamCallable(p.getInputStream(), params));

                Future<List<String>> errorFuture = sExecutorService.submit(
                    new StreamCallable(p.getErrorStream(), params));

                List<String> infoList = inputFuture.get();
                List<String> errorList = errorFuture.get();

                boolean success = p.waitFor() == 0;

                if (params.isSaveCmdToScript()){
                    success &= new File(params.workspace, ".commands_result").exists();
                }

                if (null != params.processListener) {
                    params.processListener.onProcessFinish(p);
                }

                if (success) {
                    return Result.ok(StringUtils.join(infoList, "\n"));
                } else {
                    return Result.err(new AppError(-1, StringUtils.join(errorList, "\n")));
                }

            } catch (Throwable e) {
                return Result.err(new AppError(-1, e.getMessage()));
            }

        }

    }

    private static class StreamCallable implements Callable<List<String>> {

        private InputStream inputStream;
        private ProcessParams params;

        public StreamCallable(InputStream inputStream, ProcessParams params) {
            this.inputStream = inputStream;
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            List<String> messages = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (params.isSaveMessage()) {
                        messages.add(line);
                    }
                    if (params.isLogMessage()) {
                        log.info(line);
                    }
                }
            } catch (Throwable e) {
                log.error("stream exception", e);
            }
            return messages;
        }

    }

}
