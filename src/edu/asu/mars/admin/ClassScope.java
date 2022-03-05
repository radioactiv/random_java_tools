package edu.asu.mars.admin;

import java.util.Vector;

class ClassScope {
    private java.lang.reflect.Field LIBRARIES;

    ClassScope() {
        try {
            LIBRARIES = ClassLoader.class.getDeclaredField("loadedLibraryNames");
            LIBRARIES.setAccessible(true);
        } catch (Exception e) {
        }
    }
    String[] getLoadedLibraries(final ClassLoader loader) {
        String[] array = new String[]{};
        try {
            Vector<String> libraries = (Vector<String>) LIBRARIES.get(loader);
            return libraries.toArray(new String[]{});
        } catch (Exception e) {
        }
        return array;
    }
}