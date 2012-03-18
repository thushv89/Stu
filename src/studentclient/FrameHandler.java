/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentclient;

import Common.PrivilegeMode;
import Common.Person;
import Common.Functionality;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Thushan
 */
public class FrameHandler {

    private static LoadingForm lForm;
    private static LoginForm loginForm;
    private static StartForm startForm;
    private static LoginDetailForm ldetailForm;
    private static ProfileDetailForm pdetailForm;
    private static SearchNSortForm ssForm;
    private static AdvanceSearchForm advSearch;
    private static ViewProfileForm viewForm;
    private static AboutForm aboutForm;
    public static void initializeFrames(){
        
        loginForm=new LoginForm();
        loginForm.setTitle("Login");
        
        
        
    }

    public static void showLoadingForm(){
        lForm=new LoadingForm();
        lForm.setVisible(true);
        lForm.setLocationRelativeTo(null);
    }

    public static void showLoginForm(){        
        loginForm.setVisible(true);
        loginForm.setLocationRelativeTo(null);
    }

    public static void showStartForm(String uname,PrivilegeMode mode){
        startForm=new StartForm(uname,mode);
        startForm.setVisible(true);
        startForm.setLocationRelativeTo(null);
    }

    public static void showLoginDetailForm(PrivilegeMode mode){
        ldetailForm=new LoginDetailForm(mode);
        ldetailForm.setTitle("User Login details");
        ldetailForm.setVisible(true);
        ldetailForm.setLocationRelativeTo(null);
    }

    public static void showProfileDetailForm(Person person,Functionality func){
        pdetailForm=new ProfileDetailForm(person,func);
        pdetailForm.setTitle("User Profile details");
        pdetailForm.setVisible(true);
        pdetailForm.setLocationRelativeTo(null);
    }

    public static void showSearchNSortForm(Functionality func){
        ssForm=new SearchNSortForm(func);
        ssForm.setTitle("Search Sort Delete Update");
        ssForm.setVisible(true);
        ssForm.setLocationRelativeTo(null);
    }

    public static void showAdvSearchForm(SearchNSortForm ssForm){
        advSearch=new AdvanceSearchForm(ssForm);
        advSearch.setTitle("Advance Search");
        advSearch.setVisible(true);
        advSearch.setLocationRelativeTo(null);
    }

    public static void showViewProfileForm(String uname,PrivilegeMode mode){
        viewForm=new ViewProfileForm(uname,mode);
        viewForm.setTitle("View profile information");
        viewForm.setVisible(true);
        viewForm.setLocationRelativeTo(null);
    }

    public static void showAboutForm(){
        aboutForm=new AboutForm();
        aboutForm.setVisible(true);
        aboutForm.setLocationRelativeTo(null);
    }
    public static ImageIcon resizeImg(ImageIcon icon,JLabel lbl){
        Image image=icon.getImage();

        int heightLbl=lbl.getHeight();
        float imgHeight=image.getHeight(lbl);
        float imgWidth=image.getWidth(lbl);

        int newHeight= heightLbl;
        int newWidth= (int)((imgWidth/imgHeight)*heightLbl);

        return new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
    }

    public static ImageIcon resizeImg(File file,JLabel lbl){
        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(FrameHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        int heightLbl=lbl.getHeight();
        float imgHeight=image.getHeight(lbl);
        float imgWidth=image.getWidth(lbl);

        int newHeight= heightLbl;
        int newWidth= (int)((imgWidth/imgHeight)*heightLbl);

        return new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
    }


    public static void showUnexpectedErrorDialog(){
        JOptionPane.showMessageDialog(null, "Unexpected Error");
    }
}
