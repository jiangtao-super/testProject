package com.dianju.crm.tool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;




public class MainFrame extends JFrame{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4023213273312500117L;
	 private String filePath="";
	 private SAXReader reader = null;
	 private Document doc=null;
	 private String user=null;
	 private String password=null;
	 private Element targetElement=null;
	 private String fileSuffixName=null;
	 private Properties prop =null;
	 private JLabel jLabel1 = new JLabel();//文件路径
	 private JLabel jLabel2 = new JLabel();//原用户名
	 private JLabel jLabel3 = new JLabel();//原密码
	 private JLabel jLabel4 = new JLabel();//新用户名
	 private JLabel jLabel5 = new JLabel();//新密码
	// private JLabel jLabel6 = new JLabel();
	 
	 private JTextField txtPath = new JTextField(10);	//原用户输入框
	 private JTextField txtUser_old = new JTextField(10);	//原用户输入框
	 private JTextField txtPassword_old = new JTextField(10);//原密码输入框
	 private JTextField txtUser_new = new JTextField(10);	//新用户输入框
	 private JTextField txtPassword_new = new JTextField(10);//新密码输入框
	 
	 private JButton jButton1 = new JButton("...");//选择文件按钮
	 private JButton jButton2 = new JButton("确认");//确定修改按钮
	 private JButton jButton3 = new JButton("重置");//重置按钮
	 
	public MainFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
	    	

        }
    }

	private void jbInit() {
		 this.getContentPane().setLayout( null );
	     this.setSize(new Dimension(500, 320));
	     this.setTitle( "修改数据库相关配置文件" );
	     this.setResizable(false);
	     //设置标签
	     jLabel1.setText("文件路径：");
	     jLabel1.setBounds(new Rectangle(90, 20, 80, 30));
	     jLabel2.setText("原用户名：");
	     jLabel2.setBounds(new Rectangle(90, 60, 80, 30));
	     jLabel3.setText("原密码：");
	     jLabel3.setBounds(new Rectangle(90, 100, 80, 30));
	     jLabel4.setText("新用户名：");
	     jLabel4.setBounds(new Rectangle(90, 140, 80, 30));
	     jLabel5.setText("新密码：");
	     jLabel5.setBounds(new Rectangle(90, 180, 80, 30));
	     //设置输入框
	     txtPath.setBounds(new Rectangle(180, 20, 200, 30));
	     txtUser_old.setBounds(new Rectangle(180, 60, 200, 30));
	     txtPassword_old.setBounds(new Rectangle(180, 100, 200, 30));
	     txtUser_new.setBounds(new Rectangle(180, 140, 200, 30));
	     txtPassword_new.setBounds(new Rectangle(180, 180, 200, 30));
	     
         jButton1.setBounds(new Rectangle(380, 20, 50, 30));
         jButton1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButton1_actionPerformed(e);
                    }
                });
         jButton2.setBounds(new Rectangle(170, 230, 70, 30));
         jButton2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButton2_actionPerformed(e);
                    }
                });
         jButton3.setBounds(new Rectangle(270, 230, 70, 30));
         jButton3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButton3_actionPerformed(e);
                    }
                });
         this.getContentPane().add(jLabel1, null);
         this.getContentPane().add(txtPath, null);
         this.getContentPane().add(jButton1, null);

         this.getContentPane().add(jLabel2, null);
         this.getContentPane().add(txtUser_old, null);

         this.getContentPane().add(jLabel3, null);
         this.getContentPane().add(txtPassword_old, null);

         this.getContentPane().add(jLabel4, null);
         this.getContentPane().add(txtUser_new, null);

         this.getContentPane().add(jLabel5, null);
         this.getContentPane().add(txtPassword_new, null);

         this.getContentPane().add(jButton2, null);
         this.getContentPane().add(jButton3, null);
         

	}
	 protected void jButton3_actionPerformed(ActionEvent e) {
		 txtUser_old.setText("");
		 txtPassword_old.setText("");
		 txtUser_new.setText("");
		 txtPassword_new.setText("");
	}

	protected void jButton1_actionPerformed(ActionEvent e) {
		 JFileChooser chooser=new JFileChooser(); 
		 FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件(*.xml,*.properties)", "xml","properties");//文件名过滤器
         chooser.setFileFilter(filter);//给文件选择器加入文件过滤器
         chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
       
        int result=chooser.showOpenDialog(null);         
        if(result==JFileChooser.APPROVE_OPTION)
        { 
        	String path=chooser.getSelectedFile().getPath();
        	String fileName=path.substring(path.lastIndexOf('\\')+1);
        	fileSuffixName=fileName.substring(fileName.lastIndexOf(".")+1);
        	if("xml".equals(fileSuffixName)) {
        		if("applicationContext.xml".equals(fileName)) {
            		filePath=path;
                    txtPath.setText(path);
                    xmlReader(filePath);
            	}else {
            		txtPath.setText("");
    		    	JOptionPane.showMessageDialog(this, "请选择applicationContext.xml文件！");
            	}
        	}else if("properties".equals(fileSuffixName)){
        		if("backupConfig.properties".equals(fileName)){
        			filePath=path;
                    txtPath.setText(path);
                    propertiesReader(filePath);
            	}else {
            		txtPath.setText("");
    		    	JOptionPane.showMessageDialog(this, "请选择backupConfig.properties文件！");
            	}
        	}
        	
        }
		
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		if(filePath==null||"".equals(filePath)) {
			JOptionPane.showMessageDialog(this, "请选择需要修改的配置文件！");
		}else {
			String user_old=txtUser_old.getText();
			String password_old=txtPassword_old.getText();
		    String user_new=txtUser_new.getText();
		    String password_new=txtPassword_new.getText();
		    if("".equals(user_old.trim())||"".equals(password_old.trim())||"".equals(user_new.trim())||"".equals(password_new.trim())) {
		    	JOptionPane.showMessageDialog(this, "用户名或密码不能为空");
		    }else {
		    	if(user_old.equals(user)&&password_old.equals(password)) {
		    		String user_encode=EncUtil.encodeAES(user_new);
		    		String password_encode=EncUtil.encodeAES(password_new);
		    		if("xml".equals(fileSuffixName)) {
		    			xmlWrite(filePath,user_encode,password_encode);
		    		}else if("properties".equals(fileSuffixName)) {
		    			propertiesWrite(filePath, user_encode, password_encode);
		    		}
		    	}else {
			    	JOptionPane.showMessageDialog(this, "原用户名或密码错误！");
		    	}
		    }
		}
	 }
	public void xmlReader(String filePath) {
		 String path=filePath;
		 reader = new SAXReader();
		 try {
			doc=reader.read(new File(path));
			Element root=doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements();
			for(Element element : list) {
				Attribute attr= element.attribute("id");
				if(attr!=null&&"defaultDataSource".equals(attr.getValue())) {
					targetElement=element;
					break;
				}
			}
			if(targetElement!=null) {
				@SuppressWarnings("unchecked")
				List<Element> propertyList = targetElement.elements();
				for(Element element : propertyList) {
					Attribute attr= element.attribute("name");
					if(attr!=null&&"user".equals(attr.getValue())) {
						user=EncUtil.decodeAES(element.attribute("value").getValue());
						System.out.println("-----------------"+user+"------------------");
					}else if(attr!=null&&"password".equals(attr.getValue())){
						password=EncUtil.decodeAES(element.attribute("value").getValue());
						System.out.println("-----------------"+password+"------------------");
					}
					
				}
			}else {
		    	JOptionPane.showMessageDialog(this, "此文件不包含数据库相关信息！请从新选择");
		    	txtPath.setText("");
			}
		} catch (Exception e) {
			txtPath.setText("");
			JOptionPane.showMessageDialog(this, "文件内容不符合xml标准！");
			e.printStackTrace();
			
		} 
	}
	private void propertiesReader(String filePath2) {
		FileInputStream fis =null;
		prop= new Properties();
		try {
			fis=new FileInputStream(new File(filePath));
			prop.load(fis);
			fis.close();
			if(prop.getProperty("USERNAME")!=null&&prop.getProperty("PASSWORD")!=null) {
				user=EncUtil.decodeAES(prop.getProperty("USERNAME"));
				password=EncUtil.decodeAES(prop.getProperty("PASSWORD"));
				System.out.println("-----------------"+user+"------------------");
				System.out.println("-----------------"+password+"------------------");
			}else {
				JOptionPane.showMessageDialog(this, "此文件不包含数据库相关信息！请从新选择");
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "错误的文件，加载失败！");
		}
	}
	public void propertiesWrite(String filePath,String user_new,String password_new) {	
		 FileOutputStream fos=null;
		 try {
				fos=new FileOutputStream(new File(filePath));
				prop.setProperty("USERNAME", user_new);
				prop.setProperty("PASSWORD", password_new);
				prop.store(fos, "modify");
				fos.close();
				JOptionPane.showMessageDialog(this, "修改成功！");
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "修改失败！");
			}
		 
	}
	public void xmlWrite(String filePath,String user_new,String password_new) {	
		 XMLWriter writer=null;
		 try {
			if(targetElement!=null) {
				@SuppressWarnings("unchecked")
				List<Element> propertyList = targetElement.elements();
				for(Element element : propertyList) {
					Attribute attr= element.attribute("name");
					if(attr!=null&&"user".equals(attr.getValue())) {
						
						element.attribute("value").setValue(user_new);
						
					}else if(attr!=null&&"password".equals(attr.getValue())){
						element.attribute("value").setValue(password_new);
					}
					 //指定文件输出的位置
			        FileOutputStream out =new FileOutputStream(filePath);
			        // 指定文本的写出的格式：
			        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
			        format.setEncoding("UTF-8");
			        //1.创建写出对象
			       writer=new XMLWriter(out,format);
			        //2.写出Document对象
			        writer.write(doc);
			        //3.关闭流
			        writer.close();
				}
		    	JOptionPane.showMessageDialog(this, "修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "修改失败！");
		} finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		 
	}
	
}
