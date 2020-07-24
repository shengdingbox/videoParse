package com.dabaotv.vip.parse.web;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/23
 * @Description
 */
public class demo {
    /**
     * 运行入口
     * @param args 运行参数
     */
    public static void main(String[] args) {
        // 构造一个脚本引擎管理器
        ScriptEngineManager manager = new ScriptEngineManager();
        // 遍历所有的引擎工厂，输出引擎工厂的信息
        for (ScriptEngineFactory factory : manager.getEngineFactories()) {
            String engineName = factory.getEngineName();
            String engineVersion = factory.getEngineVersion();
            String languageName = factory.getLanguageName();
            String languageVersion = factory.getLanguageVersion();
            ScriptEngine engine = factory.getScriptEngine();
            System.out.println(String.format("引擎名称：%s\t引擎版本：%s\t语言名称：%s\t语言版本：%s\t",
                    engineName, engineVersion, languageName, languageVersion));
            // 如果支持JavaScript
            if ("ECMAScript".equals(languageName)) {
                callSimpleJavaScript(engine);
                callJavaScriptFromFile(engine);
            }
        }
    }

    /**
     * 从简单字符串执行JavaScript脚本
     * @param engine 脚本引擎
     */
    private static void callSimpleJavaScript(ScriptEngine engine) {
        try {
            final String script1 = "var k = 0;";
            final String script2 = "k + 5;";
            System.out.println(script1 + " 的执行结果是：" + engine.eval(script1));
            System.out.println(script2 + " 的执行结果是：" + engine.eval(script2));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从JavaScript文件执行JavaScript脚本
     * @param engine 脚本引擎
     */
    private static void callJavaScriptFromFile(ScriptEngine engine) {
        try {
            final String fileName = "test.js";
            File file = new File(fileName);
            if (file.exists()) {
                System.out.println("从 " + fileName + " 的执行结果是：" + engine.eval(new FileReader(file)));
            } else {
                System.err.println(fileName + " 不存在，无法执行脚本");
            }
        } catch (ScriptException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
