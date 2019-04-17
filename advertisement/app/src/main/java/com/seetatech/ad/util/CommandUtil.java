package com.seetatech.ad.util;

import java.io.DataOutputStream;

/**
 * 命令行工具
 * Created by XJH on 2017/4/11.
 */

public class CommandUtil {

    /**
     * 执行命令
     *
     * @param command
     */
    public static void execute(String command) {
        Process su = null;
        DataOutputStream os = null;
        try {
            su = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(su.getOutputStream());
            os.write(command.getBytes());
            os.flush();
            su.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                su.destroy();
            } catch (Exception e) {
            }
        }
    }
}