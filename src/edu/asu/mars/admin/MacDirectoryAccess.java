package edu.asu.mars.admin;

import javax.swing.*;
import java.awt.*;
import java.io.File;

//Attempts to get permissions to a directory on mac ("Files and Directory" Permission)
public class MacDirectoryAccess {
    void testDirectory(String directory) {
        JFrame baseFrame = new JFrame("Directory Permissions");
        FileDialog fd = new FileDialog(baseFrame);
        fd.setMode(FileDialog.LOAD);
        String userHome = System.getProperty("user.home");
        String pathSeparator = System.getProperty("file.separator");
        fd.setDirectory(userHome + pathSeparator + directory);
        fd.setVisible(true);
        String filename = fd.getFile();
        if (filename == null) {
            System.exit(0); // User Closed the picker
        } else {
            System.out.println("You chose " + filename);
            System.exit(0);
        }
    }
    void testWithJFileChooser(String directory){
        JFrame baseFrame = new JFrame("Directory Permissions");
        JFileChooser jfc = new JFileChooser();
        String userHome = System.getProperty("user.home");
        String pathSeparator = System.getProperty("file.separator");
        jfc.setCurrentDirectory(new File(userHome + pathSeparator + directory));
        jfc.showOpenDialog(baseFrame);
        File f = jfc.getSelectedFile();
        if (f == null){
            System.exit(0);
        }
        else{
            System.exit(0);
        }
    }    
}
