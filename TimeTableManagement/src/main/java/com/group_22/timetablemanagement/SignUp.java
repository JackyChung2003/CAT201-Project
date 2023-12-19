package com.group_22.timetablemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SignUp {

    @FXML
    private Button SignUpBtn;

    @FXML
    private Button SignUpBtn1;

    @FXML
    private TextField emailAddress;

    @FXML
    private TextField emailAddress1;

    @FXML
    private TextField fname1;

    @FXML
    private Button jButton2;

    @FXML
    private Button jButton21;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField pass1;

    @FXML
    private ImageView timeManagementIcon;

//    private void jButton2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        Login LoginFrame = new Login();
//        LoginFrame.setVisible(true);
//        LoginFrame.pack();
//        LoginFrame.setLocationRelativeTo(null);
//        this.dispose();
//    }//GEN-LAST:event_jButton2ActionPerformed

//    private void SignUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignUpBtnActionPerformed
//        //System.out.println("Sign Up Btn Clicked!");
//        String fullName, email, password, query;
//        String SUrl, SUser, SPass;
//        SUrl = "jdbc:MySQL://localhost:3306/java_user_database";
//        SUser = "root";
//        SPass = "";
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
//            Statement st = con.createStatement();
//            if("".equals(fname.getText())){
//                JOptionPane.showMessageDialog(new JFrame(), "Full Name is required", "Error", JOptionPane.ERROR_MESSAGE);
//            }else if("".equals(emailAddress.getText())){
//                JOptionPane.showMessageDialog(new JFrame(), "Email Address is required ", "Error", JOptionPane.ERROR_MESSAGE);
//            }else if("".equals(pass.getText())){
//                JOptionPane.showMessageDialog(new JFrame(), "Password is require", "Error", JOptionPane.ERROR_MESSAGE);
//            }else {
//                fullName = fname.getText();
//                email    = emailAddress.getText();
//                password = pass.getText();
//                System.out.println(password);
//
//                query = "INSERT INTO user(full_name, email, password)" + "VALUES('"+fullName+"', '"+email+"' , '"+password+"')";
//
//                st.execute(query);
//                fname.setText("");
//                emailAddress.setText("");
//                pass.setText("");
//                showMessageDialog(null, "New account has been created successfully!");
//            }
//        }catch(Exception e){
//            System.out.println("Error"+e.getMessage());
//        }
//
//    }//GEN-LAST:event_SignUpBtnActionPerformed
}
