package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import models.Card;
import models.CardModel;
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

    public static List<Card> getAllCards(CardModel cardModel) {
        System.out.println("Inside method for reading the folder");
        List<Card> allCards = new ArrayList<>();
        Path path;
        String folder;

        if (cardModel.equals(CardModel.POKER)) {
            folder = "/images/poker/";
            path = Paths.get("./src/main/resources/images/poker");

        } else {
            folder = "/images/spanish/";
            path = Paths.get("./src/main/resources/images/spanish");
        }
        // File representing the folder that you select using a FileChooser

        final File dir = path.toFile();

        // array of supported extensions (use a List if you prefer)
        final String[] EXTENSIONS = new String[] { "jpg", "png", "bmp" // and other formats you need
        };

        // filter to identify images based on their extensions
        final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

            @Override
            public boolean accept(final File dir, final String name) {
                for (final String ext : EXTENSIONS) {
                    if (name.endsWith("." + ext)) {

                        return (true);
                    }
                }
                return (false);
            }
        };

        int num = 1;
        if (dir.isDirectory()) { // make sure it's a directory
            File[] images = dir.listFiles(IMAGE_FILTER);
            // Arrays.sort(images);
            for (final File f : images) {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(f);

                    // you probably want something more involved here
                    // to display in your UI
                    // System.out.println(" image: " + f.getName());
                    // System.out.println(" width : " + img.getWidth());
                    // System.out.println(" height: " + img.getHeight());
                    // System.out.println(" size : " + f.length());

                    Card c = new Card(f.getName(), num, cardModel, folder + f.getName());
                    System.out.println(c.toString());
                    allCards.add(c);

                    if (num < 13) {
                        num++;
                    } else {
                        num = 1;
                    }

                } catch (final IOException e) {
                    System.err.println("load of images failed");
                }
            }
        } else {
            System.out.println("is not a directory");
        }
        System.out.println("all cards loaded");
        return allCards;
    }
}
