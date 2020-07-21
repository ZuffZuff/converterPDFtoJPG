package logic;

import javafx.scene.control.Label;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static String pathToDirectoryJPG;
    private static boolean isClosing;

    public void run(String s) throws IOException, InterruptedException {
        String path = s + "/JPG";
        createDirectory(path);
        //conditionField.setText("Ожидание . . .");
        while(!Conditions.isClosing){
            if(Conditions.isClosing) break;
            //List<String[]> list = new ArrayList<>();
            //System.out.println(path);
            searchAndAction(s);
            if(Conditions.isClosing) break;
//            for(String[] sMass : list){
//                System.out.println(sMass);
//                generateImageFromPDF(sMass[0], sMass[1]);
//            }
            Thread.sleep(10000);
            if(Conditions.isClosing) break;
        }


    }

    private void createDirectory(String s){
        File file = new File(s);
        if(!file.exists()){
            file.mkdir();
        }
    }

    private void searchAndAction(String s) throws IOException {
        File file = new File(s);

        if(file.isDirectory()){
            String[] list = file.list();
            for(String name : list){
                if(new File(s+"/" + name).isDirectory() && !name.equals("JPG")){
                    searchAndAction(s + "/" + name);
                }
                if(!new File(s+"/" + name).isDirectory()){
                if(name.substring(name.length()-4).equals(".pdf")){
                    //listNames.add(s+"/" + name);
                    String filename = name.substring(0,name.length()-4);
                    //listNames.add(new String[]{filename, s+"/" + name});

                    generateImageFromPDF(filename, s+"/" + name);
                    System.out.println(filename + "!!!");
                    if(Conditions.isClosing) break;
                    //if(isClosing) System.out.println("Closing");//закрытие

                }
                }
            }
        }
    }

    private void generateImageFromPDF(String filename, String path) throws IOException {
        String numberOfPages="";
        PDDocument document = PDDocument.load(new File(path));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            if(document.getNumberOfPages()>1){
                numberOfPages ="_" + (page+1) + "";
            }
            String finalNamePath = pathToDirectoryJPG + "/" + filename + numberOfPages + ".jpg";
            if(!new File(finalNamePath).exists()){
                BufferedImage bim = pdfRenderer.renderImageWithDPI(
                        page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(
                        bim, finalNamePath, 300);
            }

        }
        document.close();
    }
}
