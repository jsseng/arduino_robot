// {{{ imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Map;
//import Interpreter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.EditBus;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.DefaultFocusComponent;
import org.gjt.sp.jedit.gui.DockableWindowManager;
import org.gjt.sp.jedit.msg.PropertiesChanged;
import org.gjt.sp.util.Log;
import org.gjt.sp.util.StandardUtilities;
// }}}

// {{{ Bumblebee_Plugin class
/**
 * 
 * Bumblebee Plugin
 *
 */
public class Bumblebee extends JPanel
implements ActionListener, EBComponent, BumblebeeActions, 
           DefaultFocusComponent {

  // {{{ Instance Variables
  private String filename;
  private String defaultFilename;
  private View view;
  private boolean floating;

  private JButton detectButton;
  private JButton helpButton;
  private JButton uploadButton;
  private JButton compileButton;
  private JLabel showSerialTerminal;
  private JTextPane console_area;
  private JScrollPane console_scrollbars;
  private Runtime r;
  private Process p;
  private Thread t, t_detect;
  private BufferedWriter out;
  private String os;
  private Interpreter interp;
  private String board_device;
    // }}}

    // {{{ Constructor
  /**
   * 
   * @param view the current jedit window
   * @param position a variable passed in from the script in actions.xml,
   * 	which can be DockableWindowManager.FLOATING, TOP, BOTTOM, LEFT, RIGHT, etc.
   * 	see @ref DockableWindowManager for possible values.
   */
  public Bumblebee(View view, String position) throws IOException{
     //The top level layout is a BoxLayout with the boxes side by side.
     //The buttons on the left and the console on the right.
     this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
     this.view = view;
     this.floating = position.equals(DockableWindowManager.FLOATING);
   
     interp = new Interpreter();

     if (floating)
        this.setPreferredSize(new Dimension(500, 250));

     //The buttons panel is a FlowLayout with a fixed maximum size which
     //forces the buttons to be arranged in a single column.
     BufferedImage bumblebee_logo = ImageIO.read(getClass().getResource("/images/AithonEmblemRedClear.png"));
     JLabel picLabel = new JLabel(new ImageIcon(bumblebee_logo));
     add(picLabel);

     Dimension button_size = new Dimension(120, 25);
     JPanel buttons = new JPanel();
     buttons.setLayout(new FlowLayout());
     buttons.setPreferredSize(new Dimension(150,180));
     buttons.setMaximumSize(new Dimension(150, 180));

     detectButton = new JButton("Detect\nBoard");
     detectButton.setText("Detect Board");
     detectButton.setPreferredSize(button_size);
     detectButton.addActionListener(this);

     helpButton = new JButton("Help");
     helpButton.setText("Help");
     helpButton.setPreferredSize(button_size);
     helpButton.addActionListener(this);

     compileButton = new JButton("Compile");
     compileButton.setText("<html><center>"+"Compile"+"</center></html>");
     compileButton.setPreferredSize(button_size);
     compileButton.addActionListener(this);

     uploadButton = new JButton("Upload");
     uploadButton.setText("<html><center>"+"Upload"+"</center></html>");
     uploadButton.setPreferredSize(button_size);
     uploadButton.addActionListener(this);

     showSerialTerminal = new JLabel("Serial Terminal");
     showSerialTerminal.setPreferredSize(button_size);

     //buttons.add(detectButton);
     buttons.add(compileButton);
     buttons.add(uploadButton);
     buttons.add(helpButton);
     buttons.add(showSerialTerminal);

     add(buttons);

     //create the console area
     console_area = new JTextPane();
     //console_area.setLineWrap(true);
     //console_area.setWrapStyleWord(true);
     console_area.setEditable(false);
     console_area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

     console_scrollbars = new JScrollPane (console_area,
           JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
           JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     Color color=new Color(0,0,0); //set background to black
     console_area.setBackground(color);

     Color color2=new Color(180,180,180); //set foreground to gray
     console_area.setForeground(color2);

     add(console_scrollbars);

     r = Runtime.getRuntime();

     detectBoard();
     os = System.getProperty("os.name").toLowerCase();

     //If property for any of the paths hasn't been set (is null or empty string)
     //Calls functions to find a default directory and sets the property
     String prop = jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "gcc-filepath");
     if (prop == null || prop.equals("")) {
        autoDetectGcc();
     }
     prop = jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "library-filepath");
     if (prop == null || prop.equals("")) {
        bumblebeeLibraryPath();
     }
     prop = jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "programmer-filepath");
     if (prop == null || prop.equals("")) {
        bumblebeeProgrammerPath();
     }

  }

  //invoked when the buttons are clicked
  public void actionPerformed(ActionEvent evt) {
     Buffer curr_buffer = jEdit.getLastBuffer();
     Process compile;
     ProcessBuilder pb;
     String line, makefile_path, lib_path;

     lib_path = jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "library-filepath");
     //Location of makefile and main.c - make general with working directory somehow
     makefile_path = lib_path + "/../ProjectTemplate/";
     //File dir = new File("C:\\Users\\Justine Dunham\\Documents\\GitHub\\bumblebee\\ProjectTemplate");
     //File dir = new File("/Users/jseng/Desktop/jEdit.app/Contents/Resources/Java/ProjectTemplate");
     File dir = new File(makefile_path);    
     Object src = evt.getSource();

     if (src == uploadButton) { //check if upload clicked
        //Using for testing - prints out current directories from property values
        append("Compiler: " + jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "gcc-filepath") + "\n", Color.red);
        append("Library: " + jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "library-filepath") + "\n", Color.red);
        append("Programmer: " + jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "programmer-filepath") + "\n", Color.red);
        //scroll the area
        console_area.setCaretPosition (console_area.getDocument().getLength());
     } else if (src == detectButton) { //check if detect clicked
        append("Detect board\n", Color.blue);
        //scroll the area
        console_area.setCaretPosition (console_area.getDocument().getLength());
     } else if (src == compileButton) { //check if compile clicked
        try {
           //Path environment variables - required for mac/linux, use null for windows
           String env[] = {"PATH=/usr/bin:/bin:/usr/sbin"};
           //String env[] = null;
           //String user_src = "USERFOLDER=\"" + curr_buffer.getDirectory().replace(" ", "\\s") + "\"";
           dir = new File(jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "programmer-filepath"));
           String cmd = jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "library-filepath");
           String gcc_cmd = jEdit.getProperty(BumblebeePlugin.OPTION_PREFIX + "gcc-filepath") + "/avr-gcc";

//--------------
           /*InputStream is = pro1.getInputStream();
           InputStreamReader isr = new InputStreamReader(is);
           BufferedReader br = new BufferedReader(isr);

           InputStream es = pro1.getErrorStream();
           InputStreamReader esr = new InputStreamReader(es);
           BufferedReader br_error = new BufferedReader(esr); 

           File fout = new File(jEdit.getLastBuffer().getDirectory() + ".test.c");
           FileOutputStream fos = new FileOutputStream(fout);
           BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));*/
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           System.setOut(new PrintStream(baos));
           StringReader sr1= new StringReader(baos.toString()); // wrap your String
           BufferedReader br= new BufferedReader(sr1); // wrap your StringReader

           ByteArrayOutputStream baes = new ByteArrayOutputStream();
           System.setErr(new PrintStream(baes));
           StringReader sr2= new StringReader(baes.toString()); // wrap your String
           BufferedReader br_error = new BufferedReader(sr2); // wrap your StringReader

           console_area.setText("");
           try {
             //run the interpreter
             interp.run(curr_buffer.getPath());
           } catch (Exception e) {
             console_area.setText("Caught Exception: " + e.getMessage() + "\n\n\n");
           }    

           //restore stdout and stderr
           new PrintStream(new FileOutputStream(FileDescriptor.out));
           new PrintStream(new FileOutputStream(FileDescriptor.err));
//--------------

           //display stdout in red
           append("Stdout:\n", Color.red);
           append(baos.toString(), Color.red);
           Font font = new Font("Verdana", Font.BOLD, 12);
           while ((line = br.readLine()) != null) {
              append(line + "\n", Color.red);
              //bw.write(line + "\n");
           }
           //bw.close();

           //display stderr in yellow
           append("Stderr:\n", Color.yellow);
           append(baes.toString(), Color.yellow);
           //while ((line = br_error.readLine()) != null) {
           //   append(line + "\n", Color.yellow);
           //}

           //scroll the area
           console_area.setCaretPosition (console_area.getDocument().getLength());
        } catch (IOException e) {
           System.err.println("Caught IOException: " + e.getMessage());
        }
     } else if (src == helpButton) {
        console_area.setText("");
        append("define label = analogIn[0]\n", Color.yellow);
        append("define label = digitalIn[0]\n", Color.yellow);
        append("define label = digitalOut[0]\n", Color.yellow);
        append("define label = motor[0]\n", Color.yellow);
        append("define label = servo[0]\n", Color.yellow);
        append("\n", Color.yellow);
        append("var x = 1     ", Color.yellow);
        append("declare a new variable x and set it equal to 1\n", Color.green);
        append("var x[10] = 1 ", Color.yellow);
        append("declare an array of 10 elements with each element set to 1\n", Color.green);
        append("when start {} ", Color.yellow);
        append("first block to run in the program\n", Color.green);
        append("repeat {}     ", Color.yellow);
        append("continuously loop\n", Color.green);
        append("repeat 5 times {}     ", Color.yellow);
        append("repeat this block 5 times\n", Color.green);
        append("repeat (condition) {} ", Color.yellow);
        append("repeat while condition is true\n", Color.green);
        append("\n", Color.yellow);
        append("\n", Color.yellow);
        append("if x < 5 {}        ", Color.yellow);
        append("if x is less than 5, then run block\n", Color.green);
        append("\n", Color.yellow);
        append("\n", Color.yellow);
        append("func f1 () {}      ", Color.yellow);
        append("function f1 with no parameters\n", Color.green);
        append("func f1 (var x) {} ", Color.yellow);
        append("function f1 with 1 parameter x\n", Color.green);
        append("\n", Color.yellow);
        append("\n", Color.yellow);
        append("sleep(500)         ", Color.yellow);
        append("sleep 500 milliseconds\n", Color.green);
        append("set x 50           ", Color.yellow);
        append("set x to 50\n", Color.green);
     }
  }

  //Finds default directory for compiler
  private String autoDetectGcc() {
     String path = "";
     String userDir = System.getProperty("user.dir");

     if (os.indexOf("win") >= 0) {
        path = userDir + "/Windows";
     } else if (os.indexOf("mac") >= 0) {
        //path = userDir + "/MacOSX";
        path = "/usr/local/CrossPack-AVR/bin";
     } else if (os.indexOf("nux") >= 0) {
        path = userDir + "/Linux";
     }

     jEdit.setProperty(BumblebeePlugin.OPTION_PREFIX + "gcc-filepath", path);
     return path;
  }

  //Finds default directory for library
  private String bumblebeeLibraryPath() {
     String path = jEdit.getJEditHome() + "/BumblebeeLibrary/";
     jEdit.setProperty(BumblebeePlugin.OPTION_PREFIX + "library-filepath", path);
     return path;
  }

  //Finds default directory for programmer/uploader
  private String bumblebeeProgrammerPath() {
     String path = jEdit.getJEditHome() + "/BumblebeeLibrary/Programmer";
     jEdit.setProperty(BumblebeePlugin.OPTION_PREFIX + "programmer-filepath", path);    
     return path;
  }
  // }}}

  //detect the board
  public static File[] findFilesForId(File dir, final String id) {
     return dir.listFiles(new FileFilter() {
           public boolean accept(File pathname) {
           return pathname.getName().contains(id);
           }
           });
  }

  //testing code
  void detectBoard() {
     t_detect = new Thread(new Runnable() {
       public void run() {
         while(true) {
         if (os.indexOf("win") >= 0) {
         //detect board on Windows
         } else if (os.indexOf("mac") >= 0) {
            File[] matchingFiles = findFilesForId(new File("/dev/"), "usbmodem");
            boolean exists = matchingFiles.length > 0;
            if (exists) {
               //showSerialTerminal.setSelected(true);
               showSerialTerminal.setText(matchingFiles[0].getName());
               board_device = matchingFiles[0].getName();
            } else {
               //showSerialTerminal.setSelected(false);
               showSerialTerminal.setText("no board detected");
               board_device = "";
            }
           //Mac
           } else if (os.indexOf("nux") >= 0) {
           //Linux
           }

           try {
              Thread.sleep(500);
           } catch (InterruptedException e) {
           }
           }
           } //end run()
     });
     t_detect.setDaemon(true);  //make this a daemon thread so the JVM will exit
     t_detect.start();
  }

  public void focusOnDefaultComponent() {
  }

  public String getFilename() {
     return filename;
  }

  public void handleMessage(EBMessage message) {
     if (message instanceof PropertiesChanged) {
        propertiesChanged();
     }
  }

  private void propertiesChanged() {
     String propertyFilename = jEdit
        .getProperty(BumblebeePlugin.OPTION_PREFIX + "filepath");
     if (!StandardUtilities.objectsEqual(defaultFilename, propertyFilename)) {
        saveFile();
        //toolPanel.propertiesChanged();
        defaultFilename = propertyFilename;
        filename = defaultFilename;
     }
     Font newFont = BumblebeeOptionPane.makeFont();
  }

  // These JComponent methods provide the appropriate points
  // to subscribe and unsubscribe this object to the EditBus.

  public void addNotify() {
     super.addNotify();
     EditBus.addToBus(this);
  }

  public void removeNotify() {
     saveFile();
     super.removeNotify();
     EditBus.removeFromBus(this);
  }

  // BumblebeeActions implementation

  public void saveFile() {
     if (filename == null || filename.length() == 0)
        return;
     try {
        FileWriter out = new FileWriter(filename);
        //out.write(textArea.getText());
        out.close();
     } catch (IOException ioe) {
        Log.log(Log.ERROR, Bumblebee.class,
              "Could not write notepad text to " + filename);
     }
  }

  public void chooseFile() {
     String[] paths = GUIUtilities.showVFSFileDialog(view, null,
           JFileChooser.OPEN_DIALOG, false);
     if (paths != null && !paths[0].equals(filename)) {
        saveFile();
        filename = paths[0];
        //toolPanel.propertiesChanged();
     }
  }

  public void copyToBuffer() {
     jEdit.newFile(view);
  }

  public void append(String s, Color c) {
     try {
        StyledDocument doc = (StyledDocument) console_area.getDocument();

        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, c);

        doc.insertString(doc.getLength(), s, set);
     } catch(BadLocationException exc) {
        exc.printStackTrace();
     }
  }
}

// vim: noai:ts=3:sw=3
