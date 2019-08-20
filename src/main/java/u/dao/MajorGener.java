package u.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MajorGener {
	/**创或改-dao的mapper接口
	 * @param _daoMapperPackageNm
	 * @param _daoMapperClassNm
	 * @param _daoMapperClassPath
	 * @param _daoMapperClassReturnType
	 * @param _daoMapperClassArgType
	 * @param _daoMapperClassMethodNm
	 * @param _daoMapperClassAnnoParamNm
	 */
	public void crOrMoMapperJ(String _daoMapperPackageNm,String _daoMapperClassNm,String _daoMapperClassPath,String _daoMapperClassReturnType,String _daoMapperClassArgType,String _daoMapperClassMethodNm,String _daoMapperClassAnnoParamNm){
		String[] needImportStrs=new String[2];
		String formatReturnT=_daoMapperClassReturnType;
		String formatArgT=_daoMapperClassArgType;
		String content=null;
		//汇总需要import的包
		//字符串内若含点
if(-1!=_daoMapperClassReturnType.indexOf("."))
	needImportStrs[0]="import "+_daoMapperClassReturnType+";\n";
formatReturnT=_daoMapperClassReturnType.substring(_daoMapperClassReturnType.lastIndexOf(".")+1);
//字符串内若含点
if(-1!=_daoMapperClassArgType.indexOf(".")){
	formatArgT=_daoMapperClassArgType.substring(_daoMapperClassArgType.lastIndexOf(".")+1);
	//且两类名不等
	if(!_daoMapperClassArgType.equals(_daoMapperClassReturnType)){
		if (needImportStrs[0] == null)
			needImportStrs[0] = "import " + _daoMapperClassArgType + ";\n";
		else
			needImportStrs[1] = "import " + _daoMapperClassArgType + ";\n";
}
}
 	    
		File file = new File(_daoMapperClassPath);

 	   // if file doesnt exists, then create it
 	   if (!file.exists()) {
 		   try {
 			  file.createNewFile();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 	    
 	    StringBuilder sbd = new StringBuilder("package "+_daoMapperPackageNm+";\r\nimport org.apache.ibatis.annotations.Param;\n");
 	    for(int i=0;i<2;i++){
 	    	if(null!=needImportStrs[i])	sbd.append(needImportStrs[i]);
 	    }
 	    sbd.append("public interface "+_daoMapperClassNm+" {\n\t\n\tpublic "+formatReturnT+" "+_daoMapperClassMethodNm+"(@Param(\""+_daoMapperClassAnnoParamNm+"\")"+formatArgT+" "+_daoMapperClassAnnoParamNm+");\n\n}");
 	    content=sbd.toString();
 	   }else{
 		   //若非空

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
		        	for(int i=0;i<2;i++){
		     	    	if(null!=needImportStrs[i]&&-1==oContent.indexOf(needImportStrs[i]))	{
		     	    		//加import
		     	    		sbd.append(needImportStrs[i]);
		     	    	}
		     	    }
		        	//加旧文本
		        	sbd.append(oContent.substring(end,oContent.lastIndexOf("}")));
		        	//加一条方法
		        	sbd.append("public "+formatReturnT+" "+_daoMapperClassMethodNm+"(@Param(\""+_daoMapperClassAnnoParamNm+"\")"+formatArgT+" "+_daoMapperClassAnnoParamNm+");\n");
		        	sbd.append("}\n");
		        	content=sbd.toString();
		        }
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
 	   }
try {
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	   BufferedWriter bw = new BufferedWriter(fw);
	   bw.write(content);
	   bw.close();
	   System.err.println(new StackTraceElement(_daoMapperPackageNm+"."+_daoMapperClassNm, "pleaseGlance", _daoMapperClassNm+".java", 1));
} catch (Exception e) {
	// TODO: handle exception
}
 	   
	}
	
	/**创或改-dao的mapper xml
	 * @param changedNOriginDbMap
	 * @param _daoMapperXmlPath
	 * @param _beanQualifiedNm
	 * @param _whichCRUD
	 * @param _tabNm
	 * @param _daoMapperClassArgType
	 * @param _daoMapperClassAnnoParamNm
	 * @param _daoMapperClassMethodNm
	 */
	public void crOrMoMapperX(Map<String,String> changedNOriginDbMap,String _daoMapperXmlPath,String _beanQualifiedNm,String _whichCRUD,String _tabNm,String _daoMapperClassArgType,String _daoMapperClassAnnoParamNm,String _daoMapperClassMethodNm,String _daoMapperClassNm,String _daoMapperPackageNm){
		File file=new File(_daoMapperXmlPath);
		boolean fes = file.exists();
		String formatBeanQualifiedNm=_beanQualifiedNm.substring(_beanQualifiedNm.lastIndexOf(".")+1).toLowerCase();
		String crudLabelContent=null;
		if("select".equals(_whichCRUD.toLowerCase())){
			StringBuilder sbd = new StringBuilder();
			if(fes){
				try {
					Document doc=new SAXReader().read(file);
					Element root=doc.getRootElement();
					Element rm = root.element("resultMap");
					formatBeanQualifiedNm=rm.attributeValue("id");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			sbd.append("<select id=\""+_daoMapperClassMethodNm+"\" parameterType=\""+_daoMapperClassArgType+"\" resultMap=\""+formatBeanQualifiedNm+"\">\n\tSELECT ");
			changedNOriginDbMap.forEach((k,v)->{
				sbd.append(v+",");
			});
			sbd.deleteCharAt(sbd.length()-1);
			sbd.append(" FROM "+_tabNm+" WHERE 1=1\n</select>\n");
			crudLabelContent=sbd.toString();
		}
		else if("update".equals(_whichCRUD.toLowerCase())){
			StringBuilder sbd = new StringBuilder();
			sbd.append("<update id=\""+_daoMapperClassMethodNm+"\" parameterType=\""+_daoMapperClassArgType+"\" >\n\tUPDATE "+_tabNm+" SET \n</update>\n");
			crudLabelContent=sbd.toString();
		}
		else if("insert".equals(_whichCRUD.toLowerCase())){
			//TODO: 
			StringBuilder sbd = new StringBuilder();
			sbd.append("<insert id=\""+_daoMapperClassMethodNm+"\" parameterType=\""+_daoMapperClassArgType+"\" >\n\tINSERT INTO "+_tabNm+" (");
			changedNOriginDbMap.forEach((k,v)->{
				sbd.append(v+",");
			});
			sbd.deleteCharAt(sbd.length()-1);
			sbd.append(") VALUES (");
			changedNOriginDbMap.forEach((k,v)->{
				sbd.append("#{"+_daoMapperClassAnnoParamNm+"."+k+"},");
			});
			sbd.deleteCharAt(sbd.length()-1);
			sbd.append(")\n</insert>\n");
			crudLabelContent=sbd.toString();
		}
		if(!fes){
			
			StringBuilder sbd = new StringBuilder();
			sbd.append("<!DOCTYPE mapper\n        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n<mapper namespace=\""+_daoMapperPackageNm+"."+_daoMapperClassNm+"\">\n\n    <resultMap id=\""+formatBeanQualifiedNm+"\" type=\""+_beanQualifiedNm+"\">\n");
			changedNOriginDbMap.forEach((k,v)->{
				sbd.append("      \t<result property=\""+k+"\" column=\""+v+"\"/>\n");
			});
			//TODO: handle exception
			sbd.append("    </resultMap>\n");
			sbd.append(crudLabelContent);
			sbd.append("</mapper>\n");
			try {
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(sbd.toString());
				bw.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}else{
			//若文件存在
			
			
			String encoding = "UTF-8";  
	        Long filelength = file.length();  
	        byte[] filecontent = new byte[filelength.intValue()];  
	         try {
	        	 FileInputStream in = new FileInputStream(file);  
		            in.read(filecontent);  
		            in.close(); 
		            String oContent = new String(filecontent, encoding);  
		            String regx="</mapper>";
		            Pattern p = Pattern.compile(regx);
			        Matcher m = p.matcher(oContent);
			        String outCont="";
			        if(m.find()){
			        	int start=m.start();
			        	StringBuilder sbd = new StringBuilder(oContent.substring(0, start));
			        	//加CRUD
			        	sbd.append(crudLabelContent);
			        	//加旧文本尾
			        	sbd.append(oContent.substring(start));
			        	outCont=sbd.toString();
			        }
			        FileWriter fw = new FileWriter(file.getAbsoluteFile());
			    	BufferedWriter bw = new BufferedWriter(fw);
			    	bw.write(outCont);
			    	bw.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
	            
	            
		        
		        
	        
		}
	}
}
