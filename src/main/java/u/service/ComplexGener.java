package u.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexGener {
/**创或改-service中实现类
 * @param _daoMapperClassMethodNm
 * @param _serviceDIMapperVar
 * @param _serviceJavaPath
 * @param _usrapiFacePackageNm
 * @param _usrapiFaceNm
 * @param _daoMapperPackageNm
 * @param _daoMapperClassNm
 * @param _douRePackageNm
 * @param _douRePreNm
 * @param _usrapiFaceMethod
 */
public void crOrMoJava(String _daoMapperClassMethodNm,String _serviceDIMapperVar,String _serviceJavaPath,String _usrapiFacePackageNm,String _usrapiFaceNm,String _daoMapperPackageNm,String _daoMapperClassNm,String _douRePackageNm,String _douRePreNm,String _usrapiFaceMethod){
	String $localPack="";
	String $localClaNm="";
	String rearPart=_serviceJavaPath.substring(_serviceJavaPath.lastIndexOf("\\java\\")+6);
	$localPack=rearPart.substring(0, rearPart.lastIndexOf("\\")).replace('\\', '.');
	$localClaNm=rearPart.substring(rearPart.lastIndexOf("\\")+1,rearPart.lastIndexOf("."));
	
	String outCo="";
	File file = new File(_serviceJavaPath);
	if(!file.exists()){
		try {
			file.createNewFile();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		outCo="package "+$localPack+";\n\n\n\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.apache.commons.beanutils.BeanMap;\nimport org.apache.commons.beanutils.BeanUtils;\n\nimport "+_usrapiFacePackageNm+"."+_usrapiFaceNm+";\nimport "+_daoMapperPackageNm+"."+_daoMapperClassNm+";\nimport "+_douRePackageNm+"."+_douRePreNm+"Request;\nimport "+_douRePackageNm+"."+_douRePreNm+"Response;\n\n\npublic class "+$localClaNm+" implements "+_usrapiFaceNm+"{\n\t\n\t@Autowired\n\tprivate "+_daoMapperClassNm+" "+_serviceDIMapperVar+";\n\n\t@Override\n\tpublic "+_douRePreNm+"Response "+_usrapiFaceMethod+"("+_douRePreNm+"Request request) {\n\t\t"+_douRePreNm+"Response response = new "+_douRePreNm+"Response();\n\t\t\n\t\tBeanMap beanMap = new BeanMap(request);\n\t\t\n\t\ttry {\n\t\t\t"+_serviceDIMapperVar+"."+_daoMapperClassMethodNm+"(beanMap);\n\t\t} catch (Exception e) {\n\t\t}\n\t\t//TODO:fill in resp\n\t\t\n\t\treturn response;\n\t}\n\n\t\n\n}";
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
	        	StringBuilder sbd = new StringBuilder();
	        	sbd.append(oContent.substring(0, end)+"import "+_douRePackageNm+"."+_douRePreNm+"Request;\nimport "+_douRePackageNm+"."+_douRePreNm+"Response;\n");
	        	sbd.append(oContent.substring(end, oContent.lastIndexOf("}")));
	        	sbd.append("\t@Override\n\tpublic "+_douRePreNm+"Response "+_usrapiFaceMethod+"("+_douRePreNm+"Request request) {\n\t\t"+_douRePreNm+"Response response = new "+_douRePreNm+"Response();\n\t\t\n\t\tBeanMap beanMap = new BeanMap(request);\n\t\t\n\t\ttry {\n\t\t\t"+_serviceDIMapperVar+"."+_daoMapperClassMethodNm+"(beanMap);\n\t\t} catch (Exception e) {\n\t\t}\n\t\t//TODO:fill in resp\n\t\t\n\t\treturn response;\n\t}\n\n\t\n\n");
	        	sbd.append("}\n");
	        	outCo=sbd.toString();
	        }
	        
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
	
	}
	try {
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(outCo);
		bw.close();
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	System.err.println(new StackTraceElement($localPack+"."+$localClaNm, "haven'tFinished", $localClaNm+".java", 1));
	


}
public void crOrMoDubboXml(String _usrapiFaceNm,String _usrapiFacePackageNm,String _serviceDubboXmlPath,String _serviceJavaNmLower,String _serviceJavaNm,String _serviceJavaPackageNm){
	File file = new File(_serviceDubboXmlPath);
	if(!file.exists()){
			try {
				file.createNewFile();
				//TODO
				String outCo="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<beans xmlns=\"http://www.springframework.org/schema/beans\"\n\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n\txmlns:dubbo=\"http://code.alibabatech.com/schema/dubbo\"\n\txmlns:service=\"http://www.csii.com.cn/pe/services\"\n\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd\n\t\t\t\t\t\thttp://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd\n\t\t\t\t\t\thttp://www.csii.com.cn/pe/services http://www.csii.com.cn/pe/services/impl.xsd\">\n\t\t\t\t\t\t\n\n   \n  \n   \t<bean id=\""+_serviceJavaNmLower+"\" class=\""+_serviceJavaPackageNm+"."+_serviceJavaNm+"\" />\n   \t<dubbo:service ref=\""+_serviceJavaNmLower+"\" interface=\""+_usrapiFacePackageNm+"."+_usrapiFaceNm+"\" />\n \t\n \t\n</beans>";
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(outCo);
				bw.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
	}
	
	
}
}
