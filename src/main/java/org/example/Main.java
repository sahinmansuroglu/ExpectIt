package org.example;


import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import net.sf.expectit.Result;

import java.io.*;
import java.util.concurrent.TimeUnit;

import static net.sf.expectit.matcher.Matchers.*;
import static net.sf.expectit.matcher.SimpleResult.failure;
import static net.sf.expectit.matcher.SimpleResult.success;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

       // Process process = Runtime.getRuntime().exec("cmd.exe");
        Process process = Runtime.getRuntime().exec("bash");
        InputStream inputStream = process.getInputStream();
        OutputStream outputStream = process.getOutputStream();

        StringBuilder wholeBuffer = new StringBuilder();

        Expect expect = new ExpectBuilder()
                .withInputs(inputStream)
                .withOutput(outputStream)
                .withTimeout(1, TimeUnit.SECONDS)
                .withEchoOutput(wholeBuffer)
                .withEchoInput(wholeBuffer)
                .withExceptionOnFailure()
                .build();

     // expect.expect(contains("Press Return to continue"));
        //expect.sendLine("cd D:\\BashTest ");
        expect.sendLine("cd /mnt/d/BashTest");

      //  expect.sendLine("name");
        expect.sendLine("./questions.sh");
        expect.expect(contains("name"));
        expect.sendLine("Ahmet");
        expect.expect(contains("language"));
        expect.sendLine("Java");
        expect.expect(contains("Year?"));
        expect.sendLine("1985");

        String list = expect.expect(regexp("$")).getBefore();
       // System.out.println("List: " + list);
        expect.sendLine("exit");
        String response = wholeBuffer.toString();
        System.out.println(response);


        process.waitFor();

        expect.close();

    }
}