/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author NhanNT
 */
public class LoadFile implements Serializable {

    private Map<String, String> siteMap;

    public Map<String, String> getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(Map<String, String> siteMap) {
        this.siteMap = siteMap;
    }

    public LoadFile() {
    }

    public void loadFile(String filename, ServletContextEvent sce) throws IOException {
        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        File file = new File(realPath + filename);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            if (file.exists()) {

                fr = new FileReader(file);

                br = new BufferedReader(fr);
                String line = "";

                while ((line = br.readLine()) != null) {
                    String arr[] = line.split("=");
                    String action = arr[0];
                    String path = arr[1];
                    if (this.siteMap == null) {
                        this.siteMap = new HashMap<>();
                    }
                    this.siteMap.put(action, path);
                }

            }
        } finally {
            if (br != null) {

                br.close();

            }
            if (fr != null) {

                fr.close();

            }
        }
    }
}
