/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 *
 * @author dell
 */
public class main extends javax.swing.JFrame {
  private String fileCode[];
  int flag=0;
        String field1,field2=" ";
        String func[]={"mov","sub","add","mul","div","neg","or","and","push","pop","dec","inc","shr","shl","call","cmp","jmp","je","jne","movsx","movzx","loop","l1:","l2:","l3:","l4:","l5:" };
        double regArray[]= new double [21];
        boolean cf=false,zf=false,sf=false,pf=false,df=false; //carry,zero,sign,overflow,parity,direction
        double eip=0,ebp=0,esp=0;
        int strt=0;
        int cmp=0,labelLine=0,p=0;
        int label[]={0,0,0,0,0};
        String str[]={"","","","",""};
        
  private int linePointer=0;
  private static final int sizeOfIntInHalfBytes = 8;
  private static final int numberOfBitsInAHalfByte = 4;
  private static final int halfByte = 0x0F;
  private static final char[] hexDigits = { 
    '0', '1', '2', '3', '4', '5', '6', '7', 
    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
  };
    /**
     * Creates new form main
     */
    public main() {
        initComponents();
    }
    public static int countDigit(int a){
        int count=0;
        while (a!=0){
            a=a/10;
            count++;
        }
        return count;
    }
    
    public static int returnLabelName(String s)
    {
       
        if(s.equalsIgnoreCase("L1")||s.equalsIgnoreCase("L1:"))
            return 0;
        else if(s.equalsIgnoreCase("L2")||s.equalsIgnoreCase("L2:"))
            return 1;
        else if(s.equalsIgnoreCase("L3")||s.equalsIgnoreCase("L3:"))
            return 2;
        else if(s.equalsIgnoreCase("L4")||s.equalsIgnoreCase("L4:"))
            return 3;
        else if(s.equalsIgnoreCase("L5")||s.equalsIgnoreCase("L5:"))
            return 4;
          else 
        return -1;
    }
    public static int returnString(String s)
    
    {  if(s.equalsIgnoreCase("str1"))
            return 0;
    else if(s.equalsIgnoreCase("str2"))
            return 1;
    else if(s.equalsIgnoreCase("str3"))
            return 2;
    else if(s.equalsIgnoreCase("str4"))
            return 3;
    else if(s.equalsIgnoreCase("str5"))
            return 4;
    else 
        return -1;
        
    
    
    }
    public static int returnRegName (String s){
        int count=0;
        if(s.equalsIgnoreCase("EAX") || s.equalsIgnoreCase("AX") || s.equalsIgnoreCase("AL") || s.equalsIgnoreCase("AH")){
            if(s.equalsIgnoreCase("EAX"))
            return 111;
            else if(s.equalsIgnoreCase("AX"))
            return 11;
            else if(s.equalsIgnoreCase("AL") || s.equalsIgnoreCase("AH"))
            return 1;
        }
        else if(s.equalsIgnoreCase("EBX") || s.equalsIgnoreCase("BX") || s.equalsIgnoreCase("BL") || s.equalsIgnoreCase("BH")){
            if(s.equalsIgnoreCase("EBX"))
            return 222;
            else if(s.equalsIgnoreCase("BX"))
            return 22;
            else if(s.equalsIgnoreCase("BL") || s.equalsIgnoreCase("BH"))
            return 2;
        }
        else if(s.equalsIgnoreCase("EDX") || s.equalsIgnoreCase("DX") || s.equalsIgnoreCase("DL") || s.equalsIgnoreCase("DH")){
           if(s.equalsIgnoreCase("EDX"))
            return 444;
            else if(s.equalsIgnoreCase("DX"))
            return 44;
            else if(s.equalsIgnoreCase("DL") || s.equalsIgnoreCase("DH"))
            return 4;
        }
        else if(s.equalsIgnoreCase("ECX") || s.equalsIgnoreCase("CX") || s.equalsIgnoreCase("CL") || s.equalsIgnoreCase("CH")){
            if(s.equalsIgnoreCase("ECX"))
            return 333;
            else if(s.equalsIgnoreCase("CX"))
            return 33;
            else if(s.equalsIgnoreCase("CL") || s.equalsIgnoreCase("CH"))
            return 3;
        }
        for(int i=0;i<s.length();i++){ //-10
            if(s.charAt(i)>=48 && s.charAt(i)<=57 || s.charAt(i)=='-'){
                count++;
            }
        }
        if(count==s.length()){
            return 5;
        }
        return -1;
    }
public void makereg(String s)
    {   
        String t,r="",eax,ax;
        
        
        if(s.equalsIgnoreCase("eax"))
            {
               t=Integer.toBinaryString((int ) regArray[returnIndex("eax")]);
               for(int i=0;i<32-t.length();i++)
               {
                   r=r.concat("0");
               }
               t=r.concat(t);
               r=t.substring(16, 32);
               regArray[returnIndex("ax")]=(double ) Integer.parseInt(r,2);
               t=r.substring(8,16);
               r=r.substring(0,8);
               regArray[returnIndex("ah")]=(double ) Integer.parseInt(r,2);
               regArray[returnIndex("al")]=(double ) Integer.parseInt(t,2);
            }
    else if(s.equalsIgnoreCase("ax"))
            {
                 t=Integer.toBinaryString((int ) regArray[returnIndex("ax")]);
                 eax=Integer.toBinaryString((int ) regArray[returnIndex("eax")]);
                 for(int i=0;i<32-eax.length();i++)
               {
                   r=r.concat("0");
               }
                 r=r.concat(eax);
                 eax=r;
                 eax=eax.substring(0,16);
                 r="";
               for(int i=0;i<16-t.length();i++)
               {
                   r=r.concat("0");
               }
               t=r.concat(t);
               eax=eax.concat(t);
               r=t.substring(0,8);
               t=t.substring(8,16);
               regArray[returnIndex("eax")]=(double ) Integer.parseInt(eax,2);
               regArray[returnIndex("ah")]=(double ) Integer.parseInt(r,2);
               regArray[returnIndex("al")]=(double ) Integer.parseInt(t,2);
               
            }
    else if(s.equalsIgnoreCase("ah"))
            {
                
             t=Integer.toBinaryString((int ) regArray[returnIndex("ah")]);
             ax=Integer.toBinaryString((int ) regArray[returnIndex("ax")]);
             for(int i=0;i<16-ax.length();i++)
               {
                   r=r.concat("0");
               }
             ax=r.concat(ax);
             ax=ax.substring(8,16);
             r="";
             for(int i=0;i<8-t.length();i++)
               {
                   r=r.concat("0");
               }
             t=r.concat(t);
             ax=t.concat(ax);
             regArray[returnIndex("ax")]=(double ) Integer.parseInt(ax,2);
             makereg("ax");
             
                 
            
            }
    else if(s.equalsIgnoreCase("al"))
            {
                 t=Integer.toBinaryString((int ) regArray[returnIndex("al")]);
             ax=Integer.toBinaryString((int ) regArray[returnIndex("ax")]);
             for(int i=0;i<16-ax.length();i++)
               {
                   r=r.concat("0");
               }
             ax=r.concat(ax);
             ax=ax.substring(0,8);
             r="";
             for(int i=0;i<8-t.length();i++)
               {
                   r=r.concat("0");
               }
             t=r.concat(t);
             ax=ax.concat(t);
             regArray[returnIndex("ax")]=(double ) Integer.parseInt(ax,2);
             makereg("ax");
                
            
            
            }
    else if(s.equalsIgnoreCase("ebx"))
            { t=Integer.toBinaryString((int ) regArray[returnIndex("ebx")]);
               for(int i=0;i<32-t.length();i++)
               {
                   r=r.concat("0");
               }
               t=r.concat(t);
               r=t.substring(16, 32);
               regArray[returnIndex("bx")]=(double ) Integer.parseInt(r,2);
               t=r.substring(8,16);
               r=r.substring(0,8);
               regArray[returnIndex("bh")]=(double ) Integer.parseInt(r,2);
               regArray[returnIndex("bl")]=(double ) Integer.parseInt(t,2);
            }
    
    else if(s.equalsIgnoreCase("bx"))
            {
                
                 t=Integer.toBinaryString((int ) regArray[returnIndex("bx")]);
                 eax=Integer.toBinaryString((int ) regArray[returnIndex("ebx")]);
                 for(int i=0;i<32-eax.length();i++)
               {
                   r=r.concat("0");
               }
                 r=r.concat(eax);
                 eax=r;
                 eax=eax.substring(0,16);
                 r="";
               for(int i=0;i<16-t.length();i++)
               {
                   r=r.concat("0");
               }
               t=r.concat(t);
               eax=eax.concat(t);
               r=t.substring(0,8);
               t=t.substring(8,16);
               regArray[returnIndex("ebx")]=(double ) Integer.parseInt(eax,2);
               regArray[returnIndex("bh")]=(double ) Integer.parseInt(r,2);
               regArray[returnIndex("bl")]=(double ) Integer.parseInt(t,2);
             
            }
    else if(s.equalsIgnoreCase("bh"))
            {
                
                
             t=Integer.toBinaryString((int ) regArray[returnIndex("bh")]);
             ax=Integer.toBinaryString((int ) regArray[returnIndex("bx")]);
             for(int i=0;i<16-ax.length();i++)
               {
                   r=r.concat("0");
               }
             ax=r.concat(ax);
             ax=ax.substring(8,16);
             r="";
             for(int i=0;i<8-t.length();i++)
               {
                   r=r.concat("0");
               }
             t=r.concat(t);
             ax=t.concat(ax);
             regArray[returnIndex("bx")]=(double ) Integer.parseInt(ax,2);
             makereg("bx");
            }
    else if(s.equalsIgnoreCase("bl"))
            {
                
                 t=Integer.toBinaryString((int ) regArray[returnIndex("bl")]);
             ax=Integer.toBinaryString((int ) regArray[returnIndex("bx")]);
             for(int i=0;i<16-ax.length();i++)
               {
                   r=r.concat("0");
               }
             ax=r.concat(ax);
             ax=ax.substring(0,8);
             r="";
             for(int i=0;i<8-t.length();i++)
               {
                   r=r.concat("0");
               }
             t=r.concat(t);
             ax=ax.concat(t);
             regArray[returnIndex("bx")]=(double ) Integer.parseInt(ax,2);
             makereg("bx");
            }
    else if(s.equalsIgnoreCase("ecx"))
            {
                 t=Integer.toBinaryString((int ) regArray[returnIndex("ecx")]);
               for(int i=0;i<32-t.length();i++)
               {
                   r=r.concat("0");
               }
               t=r.concat(t);
               r=t.substring(16, 32);
               regArray[returnIndex("cx")]=(double ) Integer.parseInt(r,2);
               t=r.substring(8,16);
               r=r.substring(0,8);
               regArray[returnIndex("ch")]=(double ) Integer.parseInt(r,2);
               regArray[returnIndex("cl")]=(double ) Integer.parseInt(t,2);
            }
    else if(s.equalsIgnoreCase("cx"))
            {
                
                 t=Integer.toBinaryString((int ) regArray[returnIndex("cx")]);
                 eax=Integer.toBinaryString((int ) regArray[returnIndex("ecx")]);
                 for(int i=0;i<32-eax.length();i++)
               {
                   r=r.concat("0");
               }
                 r=r.concat(eax);
                 eax=r;
                 eax=eax.substring(0,16);
                 r="";
               for(int i=0;i<16-t.length();i++)
               {
                   r=r.concat("0");
               }
               t=r.concat(t);
               eax=eax.concat(t);
               r=t.substring(0,8);
               t=t.substring(8,16);
               regArray[returnIndex("ecx")]=(double ) Integer.parseInt(eax,2);
               regArray[returnIndex("ch")]=(double ) Integer.parseInt(r,2);
               regArray[returnIndex("cl")]=(double ) Integer.parseInt(t,2);
             
            }
    else if(s.equalsIgnoreCase("ch"))
            {
                
                
             t=Integer.toBinaryString((int ) regArray[returnIndex("ch")]);
             ax=Integer.toBinaryString((int ) regArray[returnIndex("cx")]);
             for(int i=0;i<16-ax.length();i++)
               {
                   r=r.concat("0");
               }
             ax=r.concat(ax);
             ax=ax.substring(8,16);
             r="";
             for(int i=0;i<8-t.length();i++)
               {
                   r=r.concat("0");
               }
             t=r.concat(t);
             ax=t.concat(ax);
             regArray[returnIndex("cx")]=(double ) Integer.parseInt(ax,2);
             makereg("cx");
            }
    else if(s.equalsIgnoreCase("cl"))
            {
                
                 t=Integer.toBinaryString((int ) regArray[returnIndex("cl")]);
             ax=Integer.toBinaryString((int ) regArray[returnIndex("cx")]);
             for(int i=0;i<16-ax.length();i++)
               {
                   r=r.concat("0");
               }
             ax=r.concat(ax);
             ax=ax.substring(0,8);
             r="";
             for(int i=0;i<8-t.length();i++)
               {
                   r=r.concat("0");
               }
             t=r.concat(t);
             ax=ax.concat(t);
             regArray[returnIndex("cx")]=(double ) Integer.parseInt(ax,2);
             makereg("cx");
            }
    else if(s.equalsIgnoreCase("edx"))
            {
                 t=Integer.toBinaryString((int ) regArray[returnIndex("edx")]);
               for(int i=0;i<32-t.length();i++)
               {
                   r=r.concat("0");
               }
               t=r.concat(t);
               r=t.substring(16, 32);
               regArray[returnIndex("dx")]=(double ) Integer.parseInt(r,2);
               t=r.substring(8,16);
               r=r.substring(0,8);
               regArray[returnIndex("dh")]=(double ) Integer.parseInt(r,2);
               regArray[returnIndex("dl")]=(double ) Integer.parseInt(t,2);
            }
    else if(s.equalsIgnoreCase("dx"))
            {
                
                 t=Integer.toBinaryString((int ) regArray[returnIndex("dx")]);
                 eax=Integer.toBinaryString((int ) regArray[returnIndex("edx")]);
                 for(int i=0;i<32-eax.length();i++)
               {
                   r=r.concat("0");
               }
                 r=r.concat(eax);
                 eax=r;
                 eax=eax.substring(0,16);
                 r="";
               for(int i=0;i<16-t.length();i++)
               {
                   r=r.concat("0");
               }
               t=r.concat(t);
               eax=eax.concat(t);
               r=t.substring(0,8);
               t=t.substring(8,16);
               regArray[returnIndex("edx")]=(double ) Integer.parseInt(eax,2);
               regArray[returnIndex("dh")]=(double ) Integer.parseInt(r,2);
               regArray[returnIndex("dl")]=(double ) Integer.parseInt(t,2);
             
            }
    else if(s.equalsIgnoreCase("dh"))
            {
                
                
             t=Integer.toBinaryString((int ) regArray[returnIndex("dh")]);
             ax=Integer.toBinaryString((int ) regArray[returnIndex("dx")]);
             for(int i=0;i<16-ax.length();i++)
               {
                   r=r.concat("0");
               }
             ax=r.concat(ax);
             ax=ax.substring(8,16);
             r="";
             for(int i=0;i<8-t.length();i++)
               {
                   r=r.concat("0");
               }
             t=r.concat(t);
             ax=t.concat(ax);
             ax="";
             regArray[returnIndex("dx")]=(double ) Integer.parseInt(ax,2);
             makereg("dx");
            }
    else if(s.equalsIgnoreCase("dl"))
            {
                
                 t=Integer.toBinaryString((int ) regArray[returnIndex("dl")]);
             ax=Integer.toBinaryString((int ) regArray[returnIndex("dx")]);
             for(int i=0;i<16-ax.length();i++)
               {
                   r=r.concat("0");
               }
             ax=r.concat(ax);
             ax=ax.substring(0,8);
             r="";
             for(int i=0;i<8-t.length();i++)
               {
                   r=r.concat("0");
               }
             t=r.concat(t);
             ax=ax.concat(t);
             regArray[returnIndex("dx")]=(double ) Integer.parseInt(ax,2);
            // ax="";
             makereg("dx");
             
            }
        
    }
    public static int returnIndex(String s){
    
        int count=0;
        if(s.equalsIgnoreCase("EAX") || s.equalsIgnoreCase("AX") || s.equalsIgnoreCase("AL") || s.equalsIgnoreCase("AH")){
            if(s.equalsIgnoreCase("EAX"))
            return 0;
            else if(s.equalsIgnoreCase("AX"))
            return 1;
            else if( s.equalsIgnoreCase("AH"))
            return 2;
            else if(s.equalsIgnoreCase("AL"))
            return 3;
        }
        else if(s.equalsIgnoreCase("EBX") || s.equalsIgnoreCase("BX") || s.equalsIgnoreCase("BL") || s.equalsIgnoreCase("BH")){
            if(s.equalsIgnoreCase("EBX"))
            return 4;
            else if(s.equalsIgnoreCase("BX"))
            return 5;
            else if( s.equalsIgnoreCase("BH"))
            return 6;
            else if (s.equalsIgnoreCase("BL"))
            return 7;
        }
        else if(s.equalsIgnoreCase("EDX") || s.equalsIgnoreCase("DX") || s.equalsIgnoreCase("DL") || s.equalsIgnoreCase("DH")){
           if(s.equalsIgnoreCase("EDX"))
            return 12;
            else if(s.equalsIgnoreCase("DX"))
            return 13;
            else if( s.equalsIgnoreCase("DH"))
            return 14;
            else if (s.equalsIgnoreCase("DL"))
            return 15;
        }
        else if(s.equalsIgnoreCase("ECX") || s.equalsIgnoreCase("CX") || s.equalsIgnoreCase("CL") || s.equalsIgnoreCase("CH")){
            if(s.equalsIgnoreCase("ECX"))
            return 8;
            else if(s.equalsIgnoreCase("CX"))
            return 9;
            else if( s.equalsIgnoreCase("CH"))
            return 10;
            else if(s.equalsIgnoreCase("CL"))
            return 11;
        }
        else if(s.equalsIgnoreCase("VAR1"))
        {
            return 17;
        
        }
        
        else if(s.equalsIgnoreCase("VAR2"))
        {
            return 18;
        
        }
        else if(s.equalsIgnoreCase("VAR3"))
        {
            return 19;
        
        }else if(s.equalsIgnoreCase("VAR4"))
        {
            return 20;
        
        }else if(s.equalsIgnoreCase("VAR5"))
        {
            return 21;
        
        }
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)>=48 && s.charAt(i)<=57){
                count++;
            }
        }
        if(count==s.length()){
            return 16;
        }
        return -1;
}
  public static String decToHex(int dec) {
    StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
    hexBuilder.setLength(sizeOfIntInHalfBytes);
    for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i)
    {
      int j = dec & halfByte;
      hexBuilder.setCharAt(i, hexDigits[j]);
      dec >>= numberOfBitsInAHalfByte;
    }
    return hexBuilder.toString(); 
  }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jeax = new javax.swing.JLabel();
        jebx = new javax.swing.JLabel();
        jecx = new javax.swing.JLabel();
        jedx = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jsf = new javax.swing.JLabel();
        jzf = new javax.swing.JLabel();
        jcf = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jesp = new javax.swing.JLabel();
        jebp = new javax.swing.JLabel();
        jeip = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jpf = new javax.swing.JLabel();
        jdf = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nextLine = new javax.swing.JButton();
        fullExec = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Chiller", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("8086 Simulator");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 48));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("File Path");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSeparator1.setBackground(new java.awt.Color(0, 153, 153));

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));
        jPanel6.setPreferredSize(new java.awt.Dimension(115, 38));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Import Code");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 153, 153));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(null);

        jLabel3.setBackground(new java.awt.Color(0, 153, 153));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("EAX:");

        jLabel4.setBackground(new java.awt.Color(0, 153, 153));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("EBX:");

        jLabel5.setBackground(new java.awt.Color(0, 153, 153));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ECX:");

        jLabel6.setBackground(new java.awt.Color(0, 153, 153));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("EDX:");

        jeax.setBackground(new java.awt.Color(0, 153, 153));
        jeax.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jeax.setForeground(new java.awt.Color(0, 153, 153));
        jeax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jebx.setBackground(new java.awt.Color(0, 153, 153));
        jebx.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jebx.setForeground(new java.awt.Color(0, 153, 153));
        jebx.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jecx.setBackground(new java.awt.Color(0, 153, 153));
        jecx.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jecx.setForeground(new java.awt.Color(0, 153, 153));
        jecx.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jedx.setBackground(new java.awt.Color(0, 153, 153));
        jedx.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jedx.setForeground(new java.awt.Color(0, 153, 153));
        jedx.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel11.setBackground(new java.awt.Color(0, 153, 153));
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 153));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("CF:");

        jLabel12.setBackground(new java.awt.Color(0, 153, 153));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 153, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("ZF:");

        jLabel13.setBackground(new java.awt.Color(0, 153, 153));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 153));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("SF:");

        jsf.setBackground(new java.awt.Color(0, 153, 153));
        jsf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jsf.setForeground(new java.awt.Color(0, 153, 153));
        jsf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jsf.setText("0");

        jzf.setBackground(new java.awt.Color(0, 153, 153));
        jzf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jzf.setForeground(new java.awt.Color(0, 153, 153));
        jzf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jzf.setText("0");

        jcf.setBackground(new java.awt.Color(0, 153, 153));
        jcf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcf.setForeground(new java.awt.Color(0, 153, 153));
        jcf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jcf.setText("0");

        jLabel19.setBackground(new java.awt.Color(0, 153, 153));
        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 153, 153));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("EIP:");

        jLabel20.setBackground(new java.awt.Color(0, 153, 153));
        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 153, 153));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("EBP:");

        jLabel21.setBackground(new java.awt.Color(0, 153, 153));
        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 153, 153));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("ESP:");

        jesp.setBackground(new java.awt.Color(0, 153, 153));
        jesp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jesp.setForeground(new java.awt.Color(0, 153, 153));
        jesp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jebp.setBackground(new java.awt.Color(0, 153, 153));
        jebp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jebp.setForeground(new java.awt.Color(0, 153, 153));
        jebp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jeip.setBackground(new java.awt.Color(0, 153, 153));
        jeip.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jeip.setForeground(new java.awt.Color(0, 153, 153));
        jeip.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel27.setBackground(new java.awt.Color(0, 153, 153));
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 153, 153));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("PF:");

        jpf.setBackground(new java.awt.Color(0, 153, 153));
        jpf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jpf.setForeground(new java.awt.Color(0, 153, 153));
        jpf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jpf.setText("0");

        jdf.setBackground(new java.awt.Color(0, 153, 153));
        jdf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jdf.setForeground(new java.awt.Color(0, 153, 153));
        jdf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jdf.setText("0");

        jLabel34.setBackground(new java.awt.Color(0, 153, 153));
        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 153, 153));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("DF:");

        jLabel7.setBackground(new java.awt.Color(0, 153, 153));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("General Purpose Register");

        jLabel8.setBackground(new java.awt.Color(0, 153, 153));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Flags");

        jLabel9.setBackground(new java.awt.Color(0, 153, 153));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Special Purpose Register");

        nextLine.setText("Next Line");
        nextLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextLineActionPerformed(evt);
            }
        });

        fullExec.setText("Full Execution");
        fullExec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullExecActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                            .addComponent(jSeparator1)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(nextLine, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                        .addComponent(fullExec, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jeax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jebx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jecx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jedx, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jcf, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jsf, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jzf, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jpf, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jdf, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(82, 82, 82)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jebp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jesp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jeip, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(96, 96, 96)
                        .addComponent(jLabel8)
                        .addGap(126, 126, 126)
                        .addComponent(jLabel9)))
                .addGap(53, 53, 53))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nextLine, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(fullExec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jeip, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jebp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jesp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jeax, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jebx, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jecx, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jedx, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jcf, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jzf, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jsf, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(156, 156, 156)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jpf, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jdf, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(29, 29, 29)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fullExecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullExecActionPerformed
        // TODO add your handling code here:

        int res1,res2,res3; /// for not or and
        Stack stack= new Stack();

        //                          System.out.println("in2");
        String sCurrentLine,instruction[];
        instruction = new String[3];
        int count=0;
        int filePointer2=0;

        while (filePointer2<this.fileCode.length) {
            sCurrentLine = this.fileCode[filePointer2];
            count=0;

            if(sCurrentLine!=null)
            {
                instruction=fileCode[filePointer2].split(" ");
                for(int x=0;x<func.length;x++)
                {
                    if(instruction[0].equalsIgnoreCase(func[x])){
                        count=1;
                        break;
                    }
                }
                if(count==0)
                {
                    System.out.println("Command doesnt exist");
                }
                else
                {   //
                    if(instruction.length>1)
                    field1=instruction[1];
                    if(instruction.length>2)
                    field2=instruction[2];
                    if(instruction[0].equalsIgnoreCase("mov"))
                    {
                        //                          System.out.println("in1");
                        //                                               System.out.println(returnRegName(field1));
                        //                                               System.out.println(returnRegName(field2));
                        //reg-reg

                        if(returnIndex(field1)<16 && returnIndex(field2)<16 && countDigit(returnRegName(field1))==countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 && returnRegName(field2)!=-1 ){
                            //destination bari
                            //                             System.out.println("inn4");
                            regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

                        }
                        else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){
                            //                              System.out.println("inn3");
                            regArray[returnIndex(field1)]=Double.parseDouble(field2);
                        }
                        else if(returnIndex(field1)>16 && returnIndex(field2)<=16){ //mem destination
                            //                            System.out.println("inn6"); //mov eax,var2
                            if(returnRegName(field2)!=-1 && returnRegName(field2)!=5)
                            regArray[returnIndex(field1)]=regArray[returnIndex(field2)];
                            else if(returnRegName(field2)!=-1 && returnRegName(field2)==5)
                            regArray[returnIndex(field1)]=Double.parseDouble(field2);

                        }
                        else if(field1.equalsIgnoreCase("edx") && returnString(field2)>=0){

                            regArray[returnIndex("edx")]= (double) returnString(field2);

                        }
                        else if(returnString(field1)>=0)
                        {
                            str[returnString(field1)]=field2;

                        }
                        else if(returnIndex(field2)>16 && returnIndex(field1)<16){ //mem source
                            //                            System.out.println("inn7"); // mov eax,var1
                            if(returnRegName(field1)!=-1 && returnRegName(field2)!=5)
                            regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

                        }
                        else if(returnIndex(field2)>16 && returnIndex(field1)>16){
                            System.out.println("MEM MEM operation not allowed");
                            break;
                        }
                        else if(countDigit(returnRegName(field1))!=countDigit(returnRegName(field2)) && returnRegName(field2)!=5)
                        {
                            System.out.println("operands size is different");
                            break;

                        }
                        makereg(field1);
                    }
                    else if(instruction[0].equalsIgnoreCase("add"))
                    {

                        if(countDigit(returnRegName(field1))>=countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 ){
                            //destination bari
                            regArray[returnIndex(field1)]=regArray[returnIndex(field1)]+regArray[returnIndex(field2)];
                            //                        }
                    }
                    else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){

                        regArray[returnIndex(field1)]=regArray[returnIndex(field1)]+Double.parseDouble(field2);
                    }
                    if(regArray[returnIndex(field1)]>4294967295.00)
                    {
                        cf=true;
                        this.jcf.setText("1");
                    }
                    else
                    {cf=false;
                        this.jcf.setText("0");
                    }
                    makereg(field1);
                }

                else if(instruction[0].equalsIgnoreCase("sub"))
                {

                    if(countDigit(returnRegName(field1))>=countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 ){
                        //destination bari
                        regArray[returnIndex(field1)]=regArray[returnIndex(field1)]-regArray[returnIndex(field2)];
                        //                        }
                }
                else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){

                    regArray[returnIndex(field1)]=regArray[returnIndex(field1)]-Double.parseDouble(field2);
                }

                if( regArray[returnIndex(field1)]< regArray[returnIndex(field2)])
                {
                    cf=true;
                    this.jcf.setText("1");

                }
                else
                {
                    cf=false;
                    this.jcf.setText("0");

                }
                makereg(field1);
            }
            else if(instruction[0].equalsIgnoreCase("mul"))
            {
                //                          System.out.println(regArray[0]+field1+regArray[returnIndex(field1)]);
                if(returnRegName(field1)!=5){
                    regArray[0]=regArray[0]*regArray [returnIndex(field1)];}
                else if(returnRegName(field1)==5)
                {
                    System.out.println("invalid operation");
                    break;
                }
                makereg(field1);

            }

            else if(instruction[0].equalsIgnoreCase("shr"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)==5)
                {

                    int r1,r2;
                    r1= (int) regArray[returnIndex(field1)];
                    r2= Integer.parseInt(field2);
                    String s,s1="";
                    s=Integer.toBinaryString(r1);
                    //                              System.out.println(s);
                    for(int j=0;j<32-s.length();j++)
                    {
                        s1=s1.concat("0");

                    }
                    s1=s1.concat(s);
                    s=s1;
                    //                                 System.out.println(s);
                    String s2="";
                    for(int i=0;i<r2;i++)
                    {

                        if(s.charAt(s.length()-1)=='0')
                        cf=false;
                        else
                        cf=true;

                        s2.concat("0");
                        s2=s.substring(0,s.length()-1);
                        //                                  System.out.println(s2);

                    }
                    regArray[returnIndex(field1)]=(double) Integer.parseInt(s2,2);
                    
                }
                else
                System.out.println("wrong instruction");
                makereg(field1);
            }
            else if(instruction[0].equalsIgnoreCase("shl"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)==5)
                {

                    int r1,r2;
                    r1= (int) regArray[returnIndex(field1)];
                    r2= Integer.parseInt(field2);
                    String s,s1="";
                    s=Integer.toBinaryString(r1);
                    //                              System.out.println(s);
                    for(int j=0;j<32-s.length();j++)
                    {
                        s1=s1.concat("0");

                    }
                    s1=s1.concat(s);
                    s=s1;
                    //                                 System.out.println(s);
                    for(int i=0;i<r2;i++)
                    {

                        if(s.charAt(0)=='0')
                        cf=false;
                        else
                        cf=true;

                        s=s.substring(1,s.length());
                        s=s.concat("0");
                        //                                  System.out.println(s);

                    }
                    regArray[returnIndex(field1)]=(double) Integer.parseInt(s,2);

                }
                else
                System.out.println("wrong instruction");
                makereg(field1);
            }

            else if(instruction[0].equalsIgnoreCase("div"))
            {

                if(regArray[12]==0){
                    if(returnRegName(field1)!=5){

                        //System.out.println(regArray[0]+field1+regArray[returnIndex(field1)]);
                        regArray[12]=regArray[0]%regArray [returnIndex(field1)];
                        regArray[0]=regArray[0]/regArray [returnIndex(field1)];

                        //System.out.println("value of edx is zero");
                    }
                    else if(returnRegName(field1)==5)
                    {
                        System.out.println("invalid operation");
                        break;
                    }

                }
                else if(regArray[12]!=0)
                {
                    System.out.println("value of edx is not zero");
                    break;
                }
                makereg(field1);
            }

            else if(instruction[0].equalsIgnoreCase("call"))
            {
                if(instruction[1].equalsIgnoreCase("WriteString"))
                {
                    System.out.println(str[ (int) regArray[returnIndex("edx")]]);
                }
                else
                {
                    System.out.println("wrong instruction");
                    break;
                }
            }
            else if(instruction[0].equalsIgnoreCase("or"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)!=5 )
                {
                    int r1,r2;
                    res1 = (int) regArray[returnIndex(field1)];
                    res2 = (int) regArray[returnIndex(field2)];
                    //res1=Integer.parseInt(Integer.toBinaryString(r1));
                    //res2=Integer.parseInt(Integer.toBinaryString(r2));
                    res3=res1|res2;
                    regArray[returnIndex(field1)]= (double) res3;
                }
                else if(returnRegName(field1)!=5 && returnRegName(field2)==5 )
                {
                    int r1,r2;
                    res1 = (int) regArray[returnIndex(field1)];
                    res2=(int) Double.parseDouble(field2);
                    //res1=Integer.parseInt(Integer.toBinaryString(r1));
                    //res2=Integer.parseInt(Integer.toBinaryString(r2));
                    res3=res1|res2;

                    regArray[returnIndex(field1)]= (double) res3;
                }
                else{
                    System.out.println("Invalid Parameters");
                    break;
                }
                makereg(field1);
            }
            else if(instruction[0].equalsIgnoreCase("and"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)!=5 )
                {
                    int r1,r2;
                    res1 = (int) regArray[returnIndex(field1)];
                    res2 = (int) regArray[returnIndex(field2)];
                    //res1=Integer.parseInt(Integer.toBinaryString(r1));
                    //res2=Integer.parseInt(Integer.toBinaryString(r2));
                    res3=res1&res2;
                    regArray[returnIndex(field1)]= (double) res3;
                }
                else if(returnRegName(field1)!=5 && returnRegName(field2)==5 )
                {
                    int r1,r2;
                    res1 = (int) regArray[returnIndex(field1)];
                    res2=(int) Double.parseDouble(field2);
                    //res1=Integer.parseInt(Integer.toBinaryString(r1));
                    //res2=Integer.parseInt(Integer.toBinaryString(r2));
                    res3=res1&res2;
                    regArray[returnIndex(field1)]= (double) res3;
                }
                else{
                    System.out.println("Invalid Parameters");
                    break;
                }
                makereg(field1);
            }

            else if(instruction[0].equalsIgnoreCase("neg"))
            {
                int r1;
                res1 = (int) regArray[returnIndex(field1)];
                //res1=Integer.parseInt(Integer.toBinaryString(r1));
                res3=~res1;
                res3=res3+1;
                regArray[returnIndex(field1)]= (double) res3;
                makereg(field1);
            }

            else if(instruction[0].equalsIgnoreCase("push"))
            { double r=0;

                if(returnRegName(field1)!=5)
                { stack.push(regArray[returnIndex(field1)]);
                    esp=esp+4;}
                else
                stack.push(Double.parseDouble(field1));
                //                              r=Double.parseDouble(field1);
                //                              System.out.println("this is r "+r+" "+field1);
                // ye nae chal raha

            }
            else if(instruction[0].equalsIgnoreCase("pop"))
            {
                if(!stack.isEmpty())
                {regArray[returnIndex(field1)]= (double) stack.pop();
                    esp=esp-4;}
                else
                { System.out.println("stack is empty");
                    break;
                }
                makereg(field1);
            }
            else if(instruction[0].equalsIgnoreCase("inc"))
            {
                if(returnRegName(field1)!=5)
                {
                    regArray[returnIndex(field1)]=regArray[returnIndex(field1)]+1;

                }
                makereg(field1);
            }
            else if(instruction[0].equalsIgnoreCase("dec"))
            {
                if(returnRegName(field1)!=5)
                {
                    regArray[returnIndex(field1)]=regArray[returnIndex(field1)]-1;

                }
                makereg(field1);
            }
            else if(instruction[0].equalsIgnoreCase("movzx"))
            {

                //                                               System.out.println(returnRegName(field1));
                //                                               System.out.println(returnRegName(field2));
                if(returnIndex(field1)<16 && returnIndex(field2)<16 && countDigit(returnRegName(field1))>=countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 ){
                    //destination bari
                    //                             System.out.println("inn4");
                    regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

                }
                else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){
                    //                              System.out.println("inn3");
                    regArray[returnIndex(field1)]=Double.parseDouble(field2);
                }
                else if(returnIndex(field1)>16 && returnIndex(field2)<=16){ //mem destination
                    //                            System.out.println("inn6"); //mov eax,var2
                    if(returnRegName(field2)!=-1 && returnRegName(field2)!=5)
                    regArray[returnIndex(field1)]=regArray[returnIndex(field2)];
                    else if(returnRegName(field2)!=-1 && returnRegName(field2)==5)
                    regArray[returnIndex(field1)]=Double.parseDouble(field2);

                }
                else if(returnIndex(field2)>16 && returnIndex(field1)<16){ //mem source
                    //                            System.out.println("inn7"); // mov eax,var1
                    if(returnRegName(field1)!=-1 && returnRegName(field2)!=5)
                    regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

                }
                else if(returnIndex(field2)>16 && returnIndex(field1)>16){
                    System.out.println("MEM MEM operation not allowed");
                    break;
                }
                makereg(field1);
            }

            else if(instruction[0].equalsIgnoreCase("cmp"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)!=5)
                {
                    if(regArray[returnIndex(field1)]==regArray[returnIndex(field2)])
                    {
                        cmp=1;
                    }
                    else
                    cmp=0;
                }
                else if(returnRegName(field1)!=5 && returnRegName(field2)==5)
                {
                    if(regArray[returnIndex(field1)]==Double.parseDouble(field2))
                    {
                        cmp=1;
                    }
                    else
                    cmp=0;
                }
                else

                { System.out.println("field1 cannot be immediate");
                    break;
                }
            }

            else if(instruction[0].contains(":"))
            {
                labelLine=filePointer2+1;

                label[returnLabelName(instruction[0])]=labelLine;

            }
            else if(instruction[0].equalsIgnoreCase("loop"))
            {     if(label[returnLabelName(field1)]!=0){
                if(regArray[returnIndex("ecx")]>0)
                {
                    regArray[returnIndex("ecx")]--;
                    filePointer2=label[returnLabelName(field1)]-1;

                }
            }
            else
            {
                System.out.println("the label doesnot exist");
                break;
            }
            makereg("ecx");
        }
        else if(instruction[0].equalsIgnoreCase("je"))
        {
            if(cmp==1)
            {   if(label[returnLabelName(field1)]!=0)
                filePointer2=label[returnLabelName(field1)]-1;
            }
            else
            {
                System.out.println("the label doesnot exist");
                break;
            }
        }
        else if(instruction[0].equalsIgnoreCase("jne"))
        {
            if(cmp==0)
            {   if(label[returnLabelName(field1)]!=0)
                filePointer2=label[returnLabelName(field1)]-1;
                else
                {
                    System.out.println("the label doesnot exist");
                    break;
                }
            }
        }
        else if(instruction[0].equalsIgnoreCase("jmp"))
        {

            if(label[returnLabelName(field1)]!=0)
            filePointer2=label[returnLabelName(field1)]-1;
            else
            {
                System.out.println("the label doesnot exist");
                break;
            }
        }

        else if(instruction[0].equalsIgnoreCase("movsx"))
        {

            if(returnIndex(field1)<16 && returnIndex(field2)<16 && countDigit(returnRegName(field1))>countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 ){
                //destination bari
                String res,out="1";
                int r;
                r= (int) regArray[returnIndex(field2)];
                res=Integer.toBinaryString(r);
                if(res.charAt(0)==1)
                {
                    for(int i=0;i<31-res.length();i++)
                    {
                        out.concat("1");

                    }
                    out.concat(res);
                    int d;

                    d=Integer.parseInt(out, 2);
                    regArray[returnIndex(field1)]= (double) d;
                }
                else
                regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

            }
            else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){
                //                              System.out.println("inn3");
                regArray[returnIndex(field1)]=Double.parseDouble(field2);
            }
            else if(returnIndex(field1)>16 && returnIndex(field2)<=16){ //mem destination
                //                            System.out.println("inn6"); //mov eax,var2
                if(returnRegName(field2)!=-1 && returnRegName(field2)!=5)
                regArray[returnIndex(field1)]=regArray[returnIndex(field2)];
                else if(returnRegName(field2)!=-1 && returnRegName(field2)==5)
                regArray[returnIndex(field1)]=Double.parseDouble(field2);

            }
            else if(returnIndex(field2)>16 && returnIndex(field1)<16){ //mem source
                //                            System.out.println("inn7"); // mov eax,var1
                if(returnRegName(field1)!=-1 && returnRegName(field2)!=5)
                regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

            }
            else if(returnIndex(field2)>16 && returnIndex(field1)>16){
                System.out.println("MEM MEM operation not allowed");
                break;
            }
            makereg(field1);
        }

        if(!instruction[0].equalsIgnoreCase("mul") && !instruction[0].equalsIgnoreCase("div") && !instruction[0].contains(":")
            && !instruction[0].contains("je") && !instruction[0].contains("jne") && !instruction[0].contains("jmp") && !instruction[0].contains("loop")  && returnIndex(field1)!=-1){
            if(regArray[returnIndex(field1)]==0 )
            {this.jzf.setText("1");
                zf=true;
            }
            else{
                this.jzf.setText("0");
                zf=false;
            }
            if(regArray[returnIndex(field1)]%2==0)
            {
                this.jpf.setText("1");
                pf=true;

            }
            else
            {
                this.jpf.setText("0");
                pf=false;

            }

            if(regArray[returnIndex(field1)]<0){
                this.jsf.setText("1");
                sf=true;
            }
            else{
                this.jsf.setText("0");
                sf=false;
            }
        }
        else if(instruction[0].equalsIgnoreCase("mul") && instruction[0].equalsIgnoreCase("div"))
        {
            if(regArray[0]==0 )
            {this.jzf.setText("1");
                zf=true;
            }
            else{
                this.jzf.setText("0");
                zf=false;
            }

            if(regArray[0]%2==0)
            {
                this.jpf.setText("1");
                pf=true;

            }
            else
            {
                this.jpf.setText("0");
                pf=false;

            }

            if(regArray[0]<0){
                this.jsf.setText("1");
                sf=true;
            }
            else{
                this.jsf.setText("0");
                sf=false;
            }
        }

        }

        }

        //                    double decimal = Double.parseDouble(field1, 16);
        filePointer2++;
        eip=filePointer2*4;
        this.jeip.setText(decToHex((int)  eip));
        this.jebp.setText(decToHex((int)  ebp));
        this.jesp.setText(decToHex((int)  esp));
        this.jeax.setText(decToHex((int) (regArray[0])));
        this.jebx.setText(decToHex((int)(regArray[4])));
        this.jecx.setText(decToHex((int)(regArray[8])));
        this.jedx.setText(decToHex((int)(regArray[12])));

        }
        if(filePointer2<this.fileCode.length){
            System.out.println("Some error occured in code try importing correct file again");
        }

        count=0;

        //           for(int i=0;i<fileCode.length;i++){
            //               System.out.println(fileCode[i]);
            //           }
    }//GEN-LAST:event_fullExecActionPerformed

    private void nextLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextLineActionPerformed
        // TODO add your handling code here:
        int res1,res2,res3;
        Stack stack= new Stack();
        if(linePointer<fileCode.length && flag==0){
            //                          System.out.println("in2");
            String sCurrentLine,instruction[];
            instruction = new String[3];
            int count=0;

            if(fileCode[linePointer]!=null)
            {
                instruction=fileCode[linePointer].split(" ");
                for(int x=0;x<func.length;x++)
                {
                    if(instruction[0].equalsIgnoreCase(func[x])){
                        count=1;
                        break;
                    }
                }
                if(count==0)
                {
                    System.out.println("Command doesnt exist");
                }
                else
                {   //

                    if(instruction.length>1)
                    field1=instruction[1];
                    if(instruction.length>2)
                    field2=instruction[2];
                    if(instruction[0].equalsIgnoreCase("mov"))
                    {
                        //                          System.out.println("in1");
                        //                                               System.out.println(returnRegName(field1));
                        //                                               System.out.println(returnRegName(field2));
                        //reg-reg

                        if(returnIndex(field1)<16 && returnIndex(field2)<16 && countDigit(returnRegName(field1))==countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 && returnRegName(field2)!=-1){
                            //destination bari
                            //                             System.out.println("inn4");
                            regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

                        }
                        else if(field1.equalsIgnoreCase("edx") && returnString(field2)>=0){

                            regArray[returnIndex(field1)]= (double) returnString(field2);

                        }
                        else if(returnString(field1)>=0)
                        {
                            str[returnString(field1)]=field2;

                        }
                        else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){
                            //                              System.out.println("inn3");
                            regArray[returnIndex(field1)]=Double.parseDouble(field2);
                        }
                        else if(returnIndex(field1)>16 && returnIndex(field2)<=16){ //mem destination
                            //                            System.out.println("inn6"); //mov eax,var2
                            if(returnRegName(field2)!=-1 && returnRegName(field2)!=5)
                            regArray[returnIndex(field1)]=regArray[returnIndex(field2)];
                            else if(returnRegName(field2)!=-1 && returnRegName(field2)==5)
                            regArray[returnIndex(field1)]=Double.parseDouble(field2);

                        }
                        else if(returnIndex(field2)>16 && returnIndex(field1)<16){ //mem source
                            //                            System.out.println("inn7"); // mov eax,var1
                            if(returnRegName(field1)!=-1 && returnRegName(field2)!=5)
                            regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

                        }
                        else if(returnIndex(field2)>16 && returnIndex(field1)>16){
                            System.out.println("MEM MEM operation not allowed");
                            flag=1;
                        }
                        else if(countDigit(returnRegName(field1))!=countDigit(returnRegName(field2)) && returnRegName(field2)!=5)
                        {
                            System.out.println("operands size is different");
                            flag=1;

                        }

                    }
                    else if(instruction[0].equalsIgnoreCase("add"))
                    {

                        if(countDigit(returnRegName(field1))>=countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 ){
                            //destination bari
                            regArray[returnIndex(field1)]=regArray[returnIndex(field1)]+regArray[returnIndex(field2)];
                            //                        }
                    }
                    else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){

                        regArray[returnIndex(field1)]=regArray[returnIndex(field1)]+Double.parseDouble(field2);
                    }
                    if(regArray[returnIndex(field1)]>4294967295.00)
                    {
                        cf=true;
                        this.jcf.setText("1");
                    }
                    else
                    {cf=false;
                        this.jcf.setText("0");}
                }

                else if(instruction[0].equalsIgnoreCase("sub"))
                {

                    if(countDigit(returnRegName(field1))>=countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 ){
                        //destination bari
                        regArray[returnIndex(field1)]=regArray[returnIndex(field1)]-regArray[returnIndex(field2)];
                        //                        }
                }
                else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){

                    regArray[returnIndex(field1)]=regArray[returnIndex(field1)]-Double.parseDouble(field2);
                }

                if( regArray[returnIndex(field1)]< regArray[returnIndex(field2)])
                {
                    cf=true;
                    this.jcf.setText("1");

                }
                else
                {
                    cf=false;
                    this.jcf.setText("0");

                }
            }
            else if(instruction[0].equalsIgnoreCase("mul"))
            {
                //                          System.out.println(regArray[0]+field1+regArray[returnIndex(field1)]);
                if(returnRegName(field1)!=5){
                    regArray[0]=regArray[0]*regArray [returnIndex(field1)];}
                else if(returnRegName(field1)==5)
                {
                    System.out.println("invalid operation");
                    flag=1;
                }

            }

            else if(instruction[0].equalsIgnoreCase("shr"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)==5)
                {

                    int r1,r2;
                    r1= (int) regArray[returnIndex(field1)];
                    r2= Integer.parseInt(field2);
                    String s,s1="";
                    s=Integer.toBinaryString(r1);
                    //                              System.out.println(s);
                    for(int j=0;j<32-s.length();j++)
                    {
                        s1=s1.concat("0");

                    }
                    s1=s1.concat(s);
                    s=s1;
                    //                                 System.out.println(s);
                    String s2="";
                    for(int i=0;i<r2;i++)
                    {

                        if(s.charAt(s.length()-1)=='0')
                        cf=false;
                        else
                        cf=true;

                        s2.concat("0");
                        s2=s.substring(0,s.length()-1);
                        //                                  System.out.println(s2);

                    }
                    regArray[returnIndex(field1)]=(double) Integer.parseInt(s2,2);

                }
                else
                System.out.println("wrong instruction");

            }
            else if(instruction[0].equalsIgnoreCase("shl"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)==5)
                {

                    int r1,r2;
                    r1= (int) regArray[returnIndex(field1)];
                    r2= Integer.parseInt(field2);
                    String s,s1="";
                    s=Integer.toBinaryString(r1);
                    System.out.println(s);
                    for(int j=0;j<32-s.length();j++)
                    {
                        s1=s1.concat("0");

                    }
                    s1=s1.concat(s);
                    s=s1;
                    System.out.println(s);
                    for(int i=0;i<r2;i++)
                    {

                        if(s.charAt(0)=='0')
                        cf=false;
                        else
                        cf=true;

                        s=s.substring(1,s.length());
                        s=s.concat("0");
                        System.out.println(s);

                    }
                    regArray[returnIndex(field1)]=(double) Integer.parseInt(s,2);

                }
                else
                System.out.println("wrong instruction");

            }
            else if(instruction[0].equalsIgnoreCase("call"))
            {
                if(instruction[1].equalsIgnoreCase("WriteString"))
                {
                    System.out.println(str[ (int) regArray[returnIndex("edx")]]);
                }
                else
                {
                    System.out.println("wrong instruction");
                    flag=1;
                }
            }
            else if(instruction[0].equalsIgnoreCase("div"))
            {

                if(regArray[12]==0){
                    if(returnRegName(field1)!=5){

                        //System.out.println(regArray[0]+field1+regArray[returnIndex(field1)]);
                        regArray[12]=regArray[0]%regArray [returnIndex(field1)];
                        regArray[0]=regArray[0]/regArray [returnIndex(field1)];

                        //System.out.println("value of edx is zero");
                    }
                    else if(returnRegName(field1)==5)
                    {
                        System.out.println("invalid operation");
                        flag=1;
                    }

                }
                else if(regArray[12]!=0)
                {
                    System.out.println("value of edx is not zero");
                    flag=1;
                }

            }

            else if(instruction[0].equalsIgnoreCase("or"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)!=5 )
                {
                    int r1,r2;
                    res1 = (int) regArray[returnIndex(field1)];
                    res2 = (int) regArray[returnIndex(field2)];
                    //res1=Integer.parseInt(Integer.toBinaryString(r1));
                    //res2=Integer.parseInt(Integer.toBinaryString(r2));
                    res3=res1|res2;
                    regArray[returnIndex(field1)]= (double) res3;
                }
                else if(returnRegName(field1)!=5 && returnRegName(field2)==5 )
                {
                    int r1,r2;
                    res1 = (int) regArray[returnIndex(field1)];
                    res2=(int) Double.parseDouble(field2);
                    //res1=Integer.parseInt(Integer.toBinaryString(r1));
                    //res2=Integer.parseInt(Integer.toBinaryString(r2));
                    res3=res1|res2;

                    regArray[returnIndex(field1)]= (double) res3;
                }
                else{
                    System.out.println("Invalid Parameters");
                    flag=1;
                }

            }
            else if(instruction[0].equalsIgnoreCase("and"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)!=5 )
                {
                    int r1,r2;
                    res1 = (int) regArray[returnIndex(field1)];
                    res2 = (int) regArray[returnIndex(field2)];
                    //res1=Integer.parseInt(Integer.toBinaryString(r1));
                    //res2=Integer.parseInt(Integer.toBinaryString(r2));
                    res3=res1&res2;
                    regArray[returnIndex(field1)]= (double) res3;
                }
                else if(returnRegName(field1)!=5 && returnRegName(field2)==5 )
                {
                    int r1,r2;
                    res1 = (int) regArray[returnIndex(field1)];
                    res2=(int) Double.parseDouble(field2);
                    //res1=Integer.parseInt(Integer.toBinaryString(r1));
                    //res2=Integer.parseInt(Integer.toBinaryString(r2));
                    res3=res1&res2;
                    regArray[returnIndex(field1)]= (double) res3;
                }
                else{
                    System.out.println("Invalid Parameters");
                    flag=1;
                }
            }

            else if(instruction[0].equalsIgnoreCase("neg"))
            {
                int r1;
                res1 = (int) regArray[returnIndex(field1)];
                //res1=Integer.parseInt(Integer.toBinaryString(r1));
                res3=~res1;
                res3=res3+1;
                regArray[returnIndex(field1)]= (double) res3;

            }

            else if(instruction[0].equalsIgnoreCase("push"))
            { double r=0;
                if(returnRegName(field1)!=5)
                {stack.push(regArray[returnIndex(field1)]);
                    esp=esp+4;}
                else
                stack.push(Double.parseDouble(field1));
                //                              r=Double.parseDouble(field1);
                //                              System.out.println("this is r "+r+" "+field1);
                // ye nae chal raha

            }
            else if(instruction[0].equalsIgnoreCase("pop"))
            {
                if(!stack.isEmpty())
                {regArray[returnIndex(field1)]= (double) stack.pop();
                    esp=esp-4;}
                else
                { System.out.println("stack is empty");
                    flag=1;
                }
            }
            else if(instruction[0].equalsIgnoreCase("inc"))
            {
                if(returnRegName(field1)!=5)
                {
                    regArray[returnIndex(field1)]=regArray[returnIndex(field1)]+1;

                }
            }
            else if(instruction[0].equalsIgnoreCase("dec"))
            {
                if(returnRegName(field1)!=5)
                {
                    regArray[returnIndex(field1)]=regArray[returnIndex(field1)]-1;

                }
            }
            else if(instruction[0].equalsIgnoreCase("movzx"))
            {

                //                                               System.out.println(returnRegName(field1));
                //                                               System.out.println(returnRegName(field2));
                if(returnIndex(field1)<16 && returnIndex(field2)<16 && countDigit(returnRegName(field1))>=countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 ){
                    //destination bari
                    //                             System.out.println("inn4");
                    regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

                }
                else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){
                    //                              System.out.println("inn3");
                    regArray[returnIndex(field1)]=Double.parseDouble(field2);
                }
                else if(returnIndex(field1)>16 && returnIndex(field2)<=16){ //mem destination
                    //                            System.out.println("inn6"); //mov eax,var2
                    if(returnRegName(field2)!=-1 && returnRegName(field2)!=5)
                    regArray[returnIndex(field1)]=regArray[returnIndex(field2)];
                    else if(returnRegName(field2)!=-1 && returnRegName(field2)==5)
                    regArray[returnIndex(field1)]=Double.parseDouble(field2);

                }
                else if(returnIndex(field2)>16 && returnIndex(field1)<16){ //mem source
                    //                            System.out.println("inn7"); // mov eax,var1
                    if(returnRegName(field1)!=-1 && returnRegName(field2)!=5)
                    regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

                }
                else if(returnIndex(field2)>16 && returnIndex(field1)>16){
                    System.out.println("MEM MEM operation not allowed");
                    flag=1;
                }

            }

            else if(instruction[0].equalsIgnoreCase("cmp"))
            {
                if(returnRegName(field1)!=5 && returnRegName(field2)!=5)
                {
                    if(regArray[returnIndex(field1)]==regArray[returnIndex(field2)])
                    {
                        cmp=1;
                    }
                    else
                    cmp=0;
                }
                else if(returnRegName(field1)!=5 && returnRegName(field2)==5)
                {
                    if(regArray[returnIndex(field1)]==Double.parseDouble(field2))
                    {
                        cmp=1;
                    }
                    else
                    cmp=0;
                }
                else

                { System.out.println("field1 cannot be immediate");
                    flag=1;
                }
            }

            else if(instruction[0].contains(":"))
            {
                labelLine=linePointer+1;

                label[returnLabelName(instruction[0])]=labelLine;

            }
            else if(instruction[0].equalsIgnoreCase("loop"))
            {     if(label[returnLabelName(field1)]!=0){
                if(regArray[returnIndex("ecx")]>0)
                {
                    regArray[returnIndex("ecx")]--;
                    linePointer=label[returnLabelName(field1)]-1;

                }
            }
            else
            {
                System.out.println("the label doesnot exist");
                flag=1;
            }
        }
        else if(instruction[0].equalsIgnoreCase("je"))
        {
            if(cmp==1)
            {   if(label[returnLabelName(field1)]!=0)
                linePointer=label[returnLabelName(field1)]-1;
            }
            else
            {
                System.out.println("the label doesnot exist");
                flag=1;
            }
        }
        else if(instruction[0].equalsIgnoreCase("jne"))
        {
            if(cmp==0)
            {   if(label[returnLabelName(field1)]!=0)
                linePointer=label[returnLabelName(field1)]-1;
                else
                {
                    System.out.println("the label doesnot exist");
                    flag=1;
                }
            }
        }
        else if(instruction[0].equalsIgnoreCase("jmp"))
        {

            if(label[returnLabelName(field1)]!=0)
            linePointer=label[returnLabelName(field1)]-1;
            else
            {
                System.out.println("the label doesnot exist");
                flag=1;
            }
        }

        else if(instruction[0].equalsIgnoreCase("movsx"))
        {

            if(returnIndex(field1)<16 && returnIndex(field2)<16 && countDigit(returnRegName(field1))>countDigit(returnRegName(field2)) && returnRegName(field1)!=5 && returnRegName(field2)!=5 ){
                //destination bari
                String res,out="1";
                int r;
                r= (int) regArray[returnIndex(field2)];
                res=Integer.toBinaryString(r);
                if(res.charAt(0)==1)
                {
                    for(int i=0;i<31-res.length();i++)
                    {
                        out.concat("1");

                    }
                    out.concat(res);
                    int d;

                    d=Integer.parseInt(out, 2);
                    regArray[returnIndex(field1)]= (double) d;
                }
                else
                regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

            }
            else if(returnRegName(field1)!=5 && returnRegName(field1)!=-1 && returnRegName(field2)==5 ){
                //                              System.out.println("inn3");
                regArray[returnIndex(field1)]=Double.parseDouble(field2);
            }
            else if(returnIndex(field1)>16 && returnIndex(field2)<=16){ //mem destination
                //                            System.out.println("inn6"); //mov eax,var2
                if(returnRegName(field2)!=-1 && returnRegName(field2)!=5)
                regArray[returnIndex(field1)]=regArray[returnIndex(field2)];
                else if(returnRegName(field2)!=-1 && returnRegName(field2)==5)
                regArray[returnIndex(field1)]=Double.parseDouble(field2);

            }
            else if(returnIndex(field2)>16 && returnIndex(field1)<16){ //mem source
                //                            System.out.println("inn7"); // mov eax,var1
                if(returnRegName(field1)!=-1 && returnRegName(field2)!=5)
                regArray[returnIndex(field1)]=regArray[returnIndex(field2)];

            }
            else if(returnIndex(field2)>16 && returnIndex(field1)>16){
                System.out.println("MEM MEM operation not allowed");
                flag=1;
            }

        }

        if(!instruction[0].equalsIgnoreCase("mul") && !instruction[0].equalsIgnoreCase("div") && !instruction[0].contains(":")
            && !instruction[0].contains("je") && !instruction[0].contains("jne") && !instruction[0].contains("jmp") && !instruction[0].contains("loop") && returnIndex(field1)!=-1 )
        {
            if(regArray[returnIndex(field1)]==0 )
            {this.jzf.setText("1");
                zf=true;
            }
            else{
                this.jzf.setText("0");
                zf=false;
            }
            if(regArray[returnIndex(field1)]%2==0)
            {
                this.jpf.setText("1");
                pf=true;

            }
            else
            {
                this.jpf.setText("0");
                pf=false;

            }

            if(regArray[returnIndex(field1)]<0){
                this.jsf.setText("1");
                sf=true;
            }
            else{
                this.jsf.setText("0");
                sf=false;
            }
        }
        else if(instruction[0].equalsIgnoreCase("mul") && instruction[0].equalsIgnoreCase("div"))
        {
            if(regArray[0]==0 )
            {this.jzf.setText("1");
                zf=true;
            }
            else{
                this.jzf.setText("0");
                zf=false;
            }

            if(regArray[0]%2==0)
            {
                this.jpf.setText("1");
                pf=true;

            }
            else
            {
                this.jpf.setText("0");
                pf=false;

            }

            if(regArray[0]<0){
                this.jsf.setText("1");
                sf=true;
            }
            else{
                this.jsf.setText("0");
                sf=false;
            }
        }

        }

        }

        //                    double decimal = Double.parseDouble(field1, 16);

        count=0;
        linePointer++;
        eip=linePointer*4;
        this.jeip.setText(decToHex((int)  eip));
        this.jebp.setText(decToHex((int)  ebp));
        this.jesp.setText(decToHex((int)  esp));

        this.jeax.setText(decToHex((int) (regArray[0])));
        this.jebx.setText(decToHex((int)(regArray[4])));
        this.jecx.setText(decToHex((int)(regArray[8])));
        this.jedx.setText(decToHex((int)(regArray[12])));
        makereg(field1);
        }
        else{
            System.out.println("Error occured! Cant proceed. Import the correct file this time");
        }
        
    }//GEN-LAST:event_nextLineActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        linePointer=0;
        flag=0;
        cf=false;zf=false;sf=false;pf=false;df=false; //carry,zero,sign,overflow,parity,direction
        strt=0;
        cmp=0;labelLine=0;p=0;
        for(int i=0;i<5;i++){
            label[i]=0;
        }
        for(int i=0;i<21;i++){
            regArray[i]=0;
        }
        File f1 = new File(this.jTextField1.getText());
        if(f1.exists()){
            int countt=0;
            String fPath3=this.jTextField1.getText();
            String s1CurrentLine="";
            try (BufferedReader br = new BufferedReader(new FileReader(fPath3))) {
                while ((s1CurrentLine = br.readLine()) != null) {
                    if(s1CurrentLine.contains(":"))
                    label[returnLabelName(s1CurrentLine)]=countt+1;
                    countt++;
                }
                this.fileCode=new String[countt];
                br.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        File f2 = new File(this.jTextField1.getText());
        if(f2.exists()){
            String fPath1=this.jTextField1.getText();
            int i=0;
            String s2CurrentLine="";
            try (BufferedReader br = new BufferedReader(new FileReader(fPath1))) {
                while ((s2CurrentLine = br.readLine()) != null) {
                    fileCode[i]=s2CurrentLine;
                    i++;
                }
                br.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        //            System.out.println(regArray[0] + " " + regArray[5]);

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fullExec;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jcf;
    private javax.swing.JLabel jdf;
    private javax.swing.JLabel jeax;
    private javax.swing.JLabel jebp;
    private javax.swing.JLabel jebx;
    private javax.swing.JLabel jecx;
    private javax.swing.JLabel jedx;
    private javax.swing.JLabel jeip;
    private javax.swing.JLabel jesp;
    private javax.swing.JLabel jpf;
    private javax.swing.JLabel jsf;
    private javax.swing.JLabel jzf;
    private javax.swing.JButton nextLine;
    // End of variables declaration//GEN-END:variables
}
