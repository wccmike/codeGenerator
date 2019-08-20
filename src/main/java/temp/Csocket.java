package temp;

import java.io.OutputStream;
import java.net.Socket;

public class Csocket {
	public static void main(String[] args) throws Exception {
		String xml = "<xml>\r\n" + "<name>张山</name>\r\n" + "<amt>100000</amt>\r\n" + "<time>20171011091230</time>\r\n"
				+ "<type>支出</type>\r\n" + "<opt>信用卡还款</opt>\r\n" + "<phone>18940916007</phone>\r\n" + "</xml>";
		Socket client = new Socket("127.0.0.1", 3456);
		OutputStream out = client.getOutputStream();

		byte[] b = xml.getBytes("UTF-8");
System.out.println(b.length);
		out.write(int2Bytes8(b.length));
		out.write(b);
		out.close();
		client.close();
	}

	public static byte[] int2Bytes8(int num) {
		StringBuffer sb = new StringBuffer(String.valueOf(num));
		int length = 8 - sb.length();
		for (int i = 0; i < length; i++) {
			sb.insert(0, '0');
		}
		return sb.toString().getBytes();
	}
}
