package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import models.Card;
import models.CardModel;
import models.Match;
import models.Setting;

public class FileController {

    static String filesFolder = "./src/main/resources/files/";

    public static void serializeSettings(Setting setting, String fileName) {

        File file = new File(filesFolder.concat(fileName));
        try {
            file.createNewFile();
        } catch (IOException e) {

            e.printStackTrace();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(setting);
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static Setting deserializeSetting(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filesFolder.concat(fileName)))) {
            return (Setting) ois.readObject();
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return null;
    }

    public static void serializeMatches(List<Match> matches, String fileName) {
        File file = new File(filesFolder.concat(fileName));
        try {
            file.createNewFile();
        } catch (IOException e) {

            e.printStackTrace();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(matches);
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static List<Match> deserializeMatches(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filesFolder.concat(fileName)))) {
            return (List<Match>) ois.readObject();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
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
        final String[] EXTENSIONS = new String[] { "jpg", "png", "bmp" };
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
        if (dir.isDirectory()) {
            File[] images = dir.listFiles(IMAGE_FILTER);
            // Arrays.sort(images);
            for (final File f : images) {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(f);

                    Card c;
                    if (cardModel.equals(CardModel.POKER)) {
                        c = new Card(f.getName(), num, cardModel, folder + f.getName());
                        if (num < 13) {
                            num++;
                        } else {
                            num = 1;
                        }
                    } else {
                        if (!f.getName().equals("back1.png")) {
                            c = new Card(f.getName(), Integer.valueOf(f.getName().charAt(0)), cardModel,
                                    folder + f.getName());
                        } else {
                            c = new Card(f.getName(), 0, cardModel, folder + f.getName());
                        }

                    }
                    allCards.add(c);

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
