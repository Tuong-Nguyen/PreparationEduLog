package com.edulog.driverportal.routeselection.data.cache;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// TODO: remove this file
public class FileManager {
    public void writeToFile(File file, byte[] data) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
        } catch (IOException ex) {
            Log.d("FileManager", ex.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    Log.d("FileManager", ex.getMessage());
                }
            }
        }
    }

    public byte[] readFromBinaryFile(File file) {
        FileInputStream inputStream = null;
        long byteRemaining = file.length();
        byte[] data = new byte[(int) byteRemaining];
        try {
            inputStream = new FileInputStream(file);
            int offset = 0;
            int byteRead;
            while ((byteRead = inputStream.read(data, offset, (int) byteRemaining)) != -1) {
                byteRemaining -= byteRead;
                offset += byteRead;
            }
        } catch (IOException ex) {
            Log.d("FileManager", ex.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Log.d("FileManager", ex.getMessage());
                }
            }
        }
        return data;
    }

    public void writeToFile(File file, String content) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
        } catch (IOException ex) {
            Log.d("FileManager", ex.getMessage());
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    Log.d("FileManager", ex.getMessage());
                }
            }
        }
    }

    public String readFromTextFile(File file) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        String content = null;

        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            StringBuilder contentBuilder = new StringBuilder();
            String result;
            while ((result = bufferedReader.readLine()) != null) {
                contentBuilder.append(result);
            }
            content = contentBuilder.toString();
        } catch (IOException ex) {
            Log.d("FileManager", ex.getMessage());
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    Log.d("FileManager", ex.getMessage());
                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    Log.d("FileManager", ex.getMessage());
                }
            }
        }

        return content;
    }
}
