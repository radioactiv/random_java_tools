package edu.asu.mars.admin;

class SystemLibs {

    //Print everything
    void printLibs() {
        ClassScope classes = new ClassScope();
        String[] libs = classes.getLoadedLibraries(ClassLoader.getSystemClassLoader());
        for (String lib : libs) {
            System.out.println(lib);
        }
    }
}