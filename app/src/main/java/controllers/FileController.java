package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

import models.Match;
import models.Setting;

public class FileController {
    public static void serializeSettings(Setting setting, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(setting);
        } catch (FileNotFoundException fne) {
            fne.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
    }

    public static Setting deserializeSetting(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Setting) ois.readObject();
        } catch (FileNotFoundException fne) {
            fne.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.getStackTrace();
        }
        return null;
    }

    public static void serializeMatches(TreeSet<Match> matches, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(matches);
        } catch (FileNotFoundException fne) {
            fne.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
    }

    public static TreeSet<Match> deserializeMatches(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (TreeSet<Match>) ois.readObject();
        } catch (FileNotFoundException fne) {
            fne.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.getStackTrace();
        }
        return null;
    }
}
