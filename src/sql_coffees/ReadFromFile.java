/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql_coffees;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sergeyv
 */
public class ReadFromFile {
/**
 *
 * @author sergeyv
 */
    
    public ReadFromFile (){
        
    }
    
    public static String getSQL (String filePath) throws IOException{
        String path = filePath;
        StringBuilder s = new StringBuilder();
        FileReader reader = new FileReader (path);
        BufferedReader textReader = new BufferedReader(reader);
        String line = null;
        // StringArray lines = new StringArray();
        while ((line = textReader.readLine()) != null){
            s.append(line);
        }
        return (s.toString());
    }
    
    public static List <String> getData (String path) throws IOException{
        List<String> lines = new ArrayList<String>();
        FileReader reader = new FileReader (path);
        BufferedReader textReader = new BufferedReader(reader);
        String line = null;
        // StringArray lines = new StringArray();
        while ((line = textReader.readLine()) != null){
            lines.add(line);
        }
        return (lines);
    }
}    

