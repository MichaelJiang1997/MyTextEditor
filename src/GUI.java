
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class GUI {
	public String path = "";
	public GUI() {
		JFrame f = new JFrame("MyTextEditor");
		//字体设置
		Font font=new Font("微软雅黑",Font.PLAIN,24);
		JTextArea text = new JTextArea();
		text.setFont(font);
		JScrollPane js=new JScrollPane(text);
		//分别设置水平和垂直滚动条自动出现
		js.setHorizontalScrollBarPolicy(
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		js.setVerticalScrollBarPolicy(
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JMenu file_menu = new JMenu("File");
		JMenu speech_menu = new JMenu("Speech");
		JMenu help_menu = new JMenu("Help");
		JMenuBar menubar = new JMenuBar();
		JMenuItem open_item = new JMenuItem("Open");
		JMenuItem save_item = new JMenuItem("Save");
		
		JMenuItem speech_item = new JMenuItem("Text2Speech");
		
		JMenuItem about_item = new JMenuItem("About");
		file_menu.add(open_item);
		file_menu.add(save_item);
		speech_menu.add(speech_item);
		help_menu.add(about_item);
		
		menubar.add(file_menu);
		menubar.add(speech_menu);
		menubar.add(help_menu);
		
		open_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				path = (new FileChooser()).getPath();
				try {
					text.setText((new ReadWriteFile(path)).getText());
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}});
		save_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					//System.out.print(path);
					if(path.isEmpty())
					{
						
					}
					else {
						new ReadWriteFile(path, text.getText());
						JOptionPane.showMessageDialog(f,"Saved!");
					}
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}});
		
		speech_item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Text2Speech t = new Text2Speech(text.getText());
				if(t.getSpeech())
					JOptionPane.showMessageDialog(f,"text to speech success!");
				else
					JOptionPane.showMessageDialog(f,"text to speech failure!");
			}});
		
		about_item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(f,"MyTextEditor\nAuthour:SENCOM LAB");
			}});
		//f.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));       
		f.setSize(640, 480);
		f.add(js);
		f.setJMenuBar(menubar);
		f.setVisible(true);
	}
	
}

class FileChooser extends JFrame implements ActionListener{
	/**
	   * 文件选择
	 */
	private static final long serialVersionUID = 1L;
	JFileChooser jfc=new JFileChooser();	
	public FileChooser(){
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
		jfc.showDialog(new JLabel(), "Select");
		File file=jfc.getSelectedFile();
		if(file.isDirectory()){
			JOptionPane.showMessageDialog(jfc,"Chioce file!");
		}else if(file.isFile()){
			//System.out.println("文件:"+file.getAbsolutePath());
		}
		//System.out.println(jfc.getSelectedFile().getName());
	}
	public String getPath()
	{
		
		return jfc.getSelectedFile().getAbsolutePath();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

 
}
//读写文件类 
//参数 1 路径
class ReadWriteFile{
	private String text = "";
	public ReadWriteFile(String path) throws UnsupportedEncodingException, IOException {

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
		byte[] bytes = new byte[2048];
		int n = -1;
			while ((n = in.read(bytes,0,bytes.length)) != -1) {
				text = new String(bytes,0,n,"GBK");
				//System.out.println(text);
			}
		in.close();
	}
	public ReadWriteFile(String path, String textIn) throws UnsupportedEncodingException, IOException {
		File fp = new File(path);
		PrintWriter pfp= new PrintWriter(fp);
		pfp.print(textIn);
	    pfp.close();
	}
	public String getText() {
		return text;
	}
}
