/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentclient;

import java.io.UnsupportedEncodingException;
import java.security.*;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Thushan
 */
public class PasswordEncorder {
    
public static synchronized String encrypt(String plaintext)
  {
    MessageDigest md = null;
    try
    {
        md = MessageDigest.getInstance("SHA"); //step 2
    }
    catch(NoSuchAlgorithmException e)
    {

    }
    try
    {
      md.update(plaintext.getBytes("UTF-8")); //step 3
    }
    catch(UnsupportedEncodingException e)
    {

    }
    byte raw[] = md.digest(); //step 4
    String hash = (new BASE64Encoder()).encode(raw); //step 5
    return hash; //step 6
  }
}
