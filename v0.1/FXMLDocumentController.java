
package qrcode;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.scene.text.Text;
import javax.swing.JFileChooser;

/**
 *
 * @author walidamriou
 */
public class FXMLDocumentController implements Initializable {
    
    String thepath,the_text_or_the_link,the_size;

    @FXML
    private JFXTextField text_link;
    @FXML
    private JFXButton chooser;
    @FXML
    private Text finish;
    @FXML
    private JFXTextField size;
    
    //generateQRCodeImage if fonction to make the QRcode 
    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
       //make qrCodeWriter like a object from QRCodeWriter
       QRCodeWriter qrCodeWriter = new QRCodeWriter();
       //make the QRcode of the "text" valure with size(widthxheight) to matrix 
       BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height); 
       //get the filepath to path object of Path
       Path path = FileSystems.getDefault().getPath(filePath);
       //convert the matrix to png image and save it in the path  
       MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
           
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    

    //the chooser button
    @FXML
    private void chooser(ActionEvent event) throws InterruptedException, InvocationTargetException {
        //make f like a object of JFileChooser
        JFileChooser f = new JFileChooser();
        //set the mode of the selection
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        f.showSaveDialog(null);
        //get the path selected to "thepath" valure
        thepath = f.getSelectedFile().toString();
        //this print just to confirm the copy of the path when you test 
        System.out.println(thepath);
    }

    //generatenow button
    @FXML
    private void generatenow(ActionEvent event) throws WriterException, IOException {
        //get the link or the text from JFXTextField text_link  to the "the_text_or_the_link" valure
        the_text_or_the_link = text_link.getText();
        //get the size from JFXTextField size to the "the_size" valure
        the_size = size.getText();
        //convert the the_size from the String to Int because in next time we need it in the Int type
        int the_size_int = Integer.parseInt(the_size);			
        //get the current time and data to use it in the file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        //the print just to confirm that the timeStamp has the current time
        System.out.println(timeStamp);
        //thepath1 is the link where we need to save the image with the name of the image and the type
        String thepath1 = thepath+"/"+timeStamp+".png";
        //the print to confirm thepath and the path with the name of the new image file 
        System.out.println(thepath);
        System.out.println(the_text_or_the_link);

        //We use the try to avoid the crash of the app when there is a problem of WriterException or IOException
        try {
            //the fonction generateQRCodeImage(the_text, width of the new qrcode, height of the new qrcode, the path where you need to save with the file name)
            generateQRCodeImage(the_text_or_the_link, the_size_int, the_size_int, thepath1);
            finish.setText("the generate is done, the QRcode save in "+thepath1);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }

    
    @FXML
    private void text_link(ActionEvent event) {
    }
    
        
}
