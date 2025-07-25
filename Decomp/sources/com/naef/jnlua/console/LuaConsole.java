package com.naef.jnlua.console;

import com.naef.jnlua.LuaException;
import com.naef.jnlua.LuaRuntimeException;
import com.naef.jnlua.LuaState;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LuaConsole {
    private static final String[] EMPTY_ARGS = new String[0];
    private LuaState luaState;

    public LuaConsole() {
        this(EMPTY_ARGS);
    }

    public LuaConsole(String[] strArr) {
        this.luaState = new LuaState();
        this.luaState.newTable(strArr.length, 0);
        for (int i = 0; i < strArr.length; i++) {
            this.luaState.pushString(strArr[i]);
            this.luaState.rawSet(-2, i + 1);
        }
        this.luaState.setGlobal("argv");
        this.luaState.openLibs();
        this.luaState.load("io.stdout:setvbuf(\"no\")", "setvbuf");
        this.luaState.call(0, 0);
        this.luaState.load("io.stderr:setvbuf(\"no\")", "setvbuf");
        this.luaState.call(0, 0);
    }

    public static void main(String[] strArr) {
        new LuaConsole(strArr).run();
        System.exit(0);
    }

    public LuaState getLuaState() {
        return this.luaState;
    }

    public void run() {
        System.out.println(String.format("JNLua %s Console using Lua %s.", new Object[]{LuaState.VERSION, LuaState.LUA_VERSION}));
        System.out.print("Type 'go' on an empty line to evaluate a chunk. ");
        System.out.println("Type =<expression> to print an expression.");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
                boolean z = true;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (readLine.equals("go")) {
                            outputStreamWriter.flush();
                            runChunk(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                            break;
                        }
                        if (z) {
                            if (readLine.startsWith("=")) {
                                outputStreamWriter.write("return " + readLine.substring(1));
                                outputStreamWriter.flush();
                                runChunk(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                                break;
                            }
                        }
                        outputStreamWriter.write(readLine);
                        outputStreamWriter.write(10);
                        z = false;
                    } else {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.print("IO error: ");
                System.out.print(e.getMessage());
                System.out.println();
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void runChunk(InputStream inputStream) throws IOException {
        try {
            long nanoTime = System.nanoTime();
            this.luaState.setTop(0);
            this.luaState.load(inputStream, "console");
            this.luaState.call(0, -1);
            long nanoTime2 = System.nanoTime();
            for (int i = 1; i <= this.luaState.getTop(); i++) {
                if (i > 1) {
                    System.out.print(", ");
                }
                switch (this.luaState.type(i)) {
                    case BOOLEAN:
                        System.out.print(Boolean.valueOf(this.luaState.toBoolean(i)));
                        break;
                    case NUMBER:
                    case STRING:
                        System.out.print(this.luaState.toString(i));
                        break;
                    default:
                        System.out.print(this.luaState.typeName(i));
                        break;
                }
            }
            System.out.print("\t#msec=");
            System.out.print(String.format("%.3f", new Object[]{Double.valueOf(((double) (nanoTime2 - nanoTime)) / 1000000.0d)}));
            System.out.println();
        } catch (LuaRuntimeException e) {
            e.printLuaStackTrace();
            e.printStackTrace();
        } catch (LuaException e2) {
            System.err.println(e2.getMessage());
        }
    }
}
