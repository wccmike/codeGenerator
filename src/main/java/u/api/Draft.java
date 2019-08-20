package u.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Draft {
	/**增加或修改api.java文件
	 * 
	 */
	public void crOrMoApi(String _douRePreNm, String _douRePackageNm,String _usrapiFaceMethod,String _apiPath,String _usrapiFacePackageNm) {

//String _apiMethod="adddd";
//		String _apiPath = "D:\\workpla\\ee\\7thJan\\pex-demo-parent\\usr\\usr-api\\src\\main\\java\\com\\csii\\demo\\usr\\api\\UserService.java";
//		String _apiPackageNm = "com.csii.demo.usr.api";
		String classNm=_apiPath.substring(_apiPath.lastIndexOf("\\")+1, _apiPath.lastIndexOf("."));
		String content=null;
		File file = new File(_apiPath);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			try{
				file.createNewFile();
			}catch(Exception e) { 
				e.printStackTrace(); 
			}
			
			content="package "+_usrapiFacePackageNm+";\n\nimport "+_douRePackageNm+"."+_douRePreNm+"Request;\nimport "+_douRePackageNm+"."+_douRePreNm+"Response;\n\n\n\npublic interface "+classNm+" {\n\tpublic "+_douRePreNm+"Response "+_usrapiFaceMethod+"("+_douRePreNm+"Request request);\n\t\n}\n";
		}else{
			String encoding = "UTF-8";  
	        Long filelength = file.length();  
	        byte[] filecontent = new byte[filelength.intValue()];  
	        try {  
	            FileInputStream in = new FileInputStream(file);  
	            in.read(filecontent);  
	            in.close(); 
	            String oContent = new String(filecontent, encoding);  
	            String regx="package.*?\r?\n";
	            Pattern p = Pattern.compile(regx);
		        Matcher m = p.matcher(oContent);
		        if(m.find()){
		        	int end=m.end();
		        	StringBuilder sbd = new StringBuilder(oContent.substring(0, end));
		        	//加import
		        	sbd.append("import "+_douRePackageNm+"."+_douRePreNm+"Request;\nimport "+_douRePackageNm+"."+_douRePreNm+"Response;\n");
		        	sbd.append(oContent.substring(end, oContent.lastIndexOf("}")));
		        	//加一条方法
		        	sbd.append("public "+_douRePreNm+"Response "+_usrapiFaceMethod+"("+_douRePreNm+"Request request);\n");
		        	sbd.append("}\n");
		        	content=sbd.toString();
		        }
		        
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	        
		}
try{
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(content);
	bw.close();
	System.err.println(new StackTraceElement(_usrapiFacePackageNm+"."+classNm, "pleaseGlance", classNm+".java", 1));
}catch(Exception e) { 
	e.printStackTrace(); 
}
		
		
	}
	/**
	 * 制造REQUEST和RESPONSE
	 * 
	 * @param m
	 *            JDBCAPP制造的表结构信息MAP
	 */
	public void createDouRe(Map<String, String> m, String _douRePreNm, String _douReDir, String _douRePackageNm,
			String _beanPackageNm, String _beanClassNm) {

		try {
			String fron = "package " + _douRePackageNm
					+ ";\n\nimport java.io.Serializable;\nimport java.util.Date;\nimport java.math.BigDecimal;\n\npublic class "
					+ _douRePreNm + "Request implements Serializable {\n\t\n";
			// TODO:
			StringBuilder sb = new StringBuilder(fron);
			m.forEach((k, v) -> {
				sb.append("private ");
				sb.append(v);
				sb.append(" ");
				sb.append(k);
				sb.append(";\n");
			});
			m.forEach((k, v) -> {
				sb.append("public ");
				sb.append(v);
				sb.append(" get");
				String changed = k.substring(0, 1).toUpperCase() + k.substring(1);
				sb.append(changed);
				sb.append("() {\n\treturn ");
				sb.append(k);
				sb.append(";\n}\npublic void set");
				sb.append(changed);
				sb.append("(");
				sb.append(v);
				sb.append(" ");
				sb.append(k);
				sb.append(") {\n\tthis." + k + " = " + k + ";\n}\n");
			});
			sb.append("}");
			File file = new File(_douReDir + File.separator + _douRePreNm + "Request.java");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
			System.err.println(new StackTraceElement(_douRePackageNm+"."+_douRePreNm+"Request", "addSerializableId", _douRePreNm+"Request"+".java", 1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String firstAlphaLow = _beanClassNm.substring(0, 1).toLowerCase() + _beanClassNm.substring(1);
			String content = "package " + _douRePackageNm + ";\n\nimport java.io.Serializable;\n\nimport "
					+ _beanPackageNm + "." + _beanClassNm + ";\n\npublic class " + _douRePreNm
					+ "Response implements Serializable{\n\t\n\tprivate " + _beanClassNm + " " + firstAlphaLow
					+ ";\n\tprivate String others;\n\tpublic " + _beanClassNm + " get" + _beanClassNm
					+ "() {\n\t\treturn " + firstAlphaLow + ";\n\t}\n\tpublic void set" + _beanClassNm + "("
					+ _beanClassNm + " " + firstAlphaLow + ") {\n\t\tthis." + firstAlphaLow + " = " + firstAlphaLow
					+ ";\n\t}\n\tpublic String getOthers() {\n\t\treturn others;\n\t}\n\tpublic void setOthers(String others) {\n\t\tthis.others = others;\n\t}\n\t\n}";
			File file = new File(_douReDir + File.separator + _douRePreNm + "Response.java");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			System.err.println(new StackTraceElement(_douRePackageNm+"."+_douRePreNm+"Response", "addSerializableId", _douRePreNm+"Response"+".java", 1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
