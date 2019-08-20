package temp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
	public static void main(String[] args) throws Exception {
		/*new B();
		String s = "d";
		new B(s);
		double num1 = 6e+9;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumFractionDigits(4);
//		nf.setMaximumIntegerDigits(5);
		String st = nf.format(num1);
		System.out.println(num1);
		System.out.println(st);
		System.out.println("-");
		Map<String, String> map=new HashMap<String, String>();
		map.put("di", "zo");
		Map<String,String > map2=map;
		System.out.println(map==map2);
		System.out.println(map.equals(map2));*/
		ServerSocket server = new ServerSocket(3456);
        System.out.println("-----正在监听3456端口---");
        Socket socket = server.accept();
        InputStream is = socket.getInputStream();
        // 前8个字节
        byte[] b = new byte[8];
        is.read(b);
        int len = Integer.parseInt(new String(b, "UTF-8"));
        System.out.println(len);
        
        // 用来填充xml
        b = new byte[len];
        is.read(b);
        
        // 关闭资源
        is.close();
        socket.close();
        server.close();

        String result = new String(b, "UTF-8");
        System.out.println(result);
	}
}
