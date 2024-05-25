package com.wyb.controller;

import com.wyb.biz.CodeBiz;
import com.wyb.entity.Code;
import com.wyb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.tools.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    private CodeBiz biz;

    @RequestMapping("/list")
    public Map findCodes(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("login_user");
        List<Code> list = biz.findAll(user.getUserId());
        Map result = new HashMap<>();
        result.put("isOk",true);
        result.put("msg","查询成功");
        result.put("codes",list);
        return result;
    }

    @RequestMapping("/us")
    public Map searchUserCodeByTitle(String title , HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("login_user");
        List<Code> list = biz.getUserCodeByTitle(user.getUserId()  , title);
        Map result = new HashMap();
        result.put("isOk",true);
        result.put("msg","查询成功");
        result.put("codes",list);
        return result;
    }

    @RequestMapping("/search")
    public Map searchCodeByTitle(String title){
        List<Code> list = biz.selectCodeByTitle(title);
        Map result = new HashMap<>();
        result.put("isOk",true);
        result.put("msg","查询成功");
        result.put("codes",list);
        return result;
    }

    @RequestMapping("/all")
    public Map getAllCodes(){
        List<Code> list = biz.getAll();
        Map result = new HashMap<>();
        result.put("isOk",true);
        result.put("msg","加载成功");
        result.put("codes",list);
        return result;
    }


    @RequestMapping("/get")
    public Map get(int codeId){
        Map res = new HashMap();
        Code code = biz.selectCode(codeId);
        if(code != null){
            res.put("isOk",true);
            res.put("code",code);
            res.put("msg","加载成功");
        }else {
            res.put("isOk",false);
            res.put("msg","加载失败");
        }
        return res;
    }

    @RequestMapping("/add")
    public Map add(Code code , HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("login_user");
        code.setUserId(user.getUserId());
        this.biz.addCode(code);
        Map result = new HashMap<>();
        result.put("isOk",true);
        result.put("msg","添加成功");
        return result;
    }

    @RequestMapping("/del")
    public Map del(int id){
        Map result = new HashMap<>();
        if(this.biz.delCode(id)){
            result.put("isOk",true);
            result.put("msg","删除成功");
        }else{
            result.put("isOk",false);
            result.put("msg","删除失败");
        }

        return result;
    }

    @RequestMapping("/update")
    public Map update(Code code)
    {
        Map result = new HashMap();
        if(this.biz.update(code.getTitle(), code.getCode(), code.getCodeId()))
        {
            result.put("isOk" , true);
            result.put("msg" , "修改成功");
        }
        else {
            result.put("isOk" , false);
            result.put("msg" , "修改失败");
        }
        return result;
    }


    @RequestMapping("/run")
    public Map<String, String> runCode(String code, String input) {
        File sourceFile = new File("Main.java");
        try {
            FileWriter writer = new FileWriter(sourceFile);
            writer.write(code);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.singletonMap("output", "Error: Failed to write source file");
        }

        String output;
        output = compileAndRunJavaCode(sourceFile, input);

        sourceFile.delete(); // 删除源文件

        return Collections.singletonMap("output", output);
    }

    private String compileAndRunJavaCode(File sourceFile, String input) {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
            DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector, null, null, compilationUnits);
            if (!task.call()) {
                throw new CompilationError("Failed to compile code", diagnosticCollector);
            }
            fileManager.close();

            // 执行Java程序
            ProcessBuilder processBuilder = new ProcessBuilder("java", "Main");
            processBuilder.directory(sourceFile.getParentFile());
            Process process = processBuilder.start();

            // 将输入写入到子进程的标准输入流
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(input);
            writer.flush();
            writer.close();

            // 读取子进程的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }

            // 等待子进程执行结束
            process.waitFor();

            return outputBuilder.toString();
        } catch (IOException | InterruptedException e) {
            return "Error: " + e.getMessage();
        } catch (CompilationError e) {
            return e.getCompilerErrorMessage();
        }
    }

    class CompilationError extends Exception {
        private final DiagnosticCollector<JavaFileObject> diagnosticCollector;

        public CompilationError(String message, DiagnosticCollector<JavaFileObject> diagnosticCollector) {
            super(message);
            this.diagnosticCollector = diagnosticCollector;
        }

        public String getCompilerErrorMessage() {
            StringBuilder errorMessage = new StringBuilder();
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnosticCollector.getDiagnostics()) {
                errorMessage.append(diagnostic.toString()).append("\n");
            }
            return errorMessage.toString();
        }
    }

    public void setBiz(CodeBiz biz) {
        this.biz = biz;
    }
}
