package codeGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import u.api.Draft;
import u.dao.MajorGener;
import u.service.ComplexGener;

public class App {

	public static void main (String[] args) throws Exception{
		/*
		 * 输入变量区
		 */
		//首小写
		String verb="add";
		//首大写
		String none="Account";
		//数据库表名
		String _tabNm = "POC_ACCOUNT";
		//select或insert或update
		String _whichCRUD="insert";
		//trs.xml中第一个ref的text，也是action的name值，若设null，默认匹配
		String  _firstTrsFirstRefText=null;
		//daoMapper的函数返回类型，全限定类名，lang包不用全限定,可写void
		String			_daoMapperClassReturnType="int";
		//daoMapper的函数参数类型，全限定类名，lang包不用全限定
		String			_daoMapperClassArgType="com.csii.demo.account.bean.Account";
		//daoMapper的函数参数 @param 命名
		String			_daoMapperClassAnnoParamNm="account";
		//涉及bean的全限定类名此处必填
		String _beanQualifiedNm="com.csii.demo.account.bean.Account";
		//注释
		String _firstTrsComment="新增帐户";
		
		
		String lowNone=none.substring(0, 1).toLowerCase()+none.substring(1);
		String upVerb=verb.substring(0, 1).toUpperCase()+verb.substring(1);
		//必须是已在的
		String  _firstTrsPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\apigate\\src\\main\\resources\\config\\trs\\trs-"+lowNone+".xml";
		String  _frontActionPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\apigate\\src\\main\\java\\com\\csii\\demo\\"+lowNone+"\\action\\"+upVerb+none+"Action.java";
		//若不需创建就赋null
				String _firstDubboPath = "D:\\workpla\\ee\\7thJan\\pex-demo-parent\\apigate\\src\\main\\resources\\config\\dubbo\\dubbo-"+lowNone+".xml";
				//尾不写分隔符
				String _douReDir = "D:\\workpla\\ee\\7thJan\\pex-demo-parent\\"+lowNone+"\\"+lowNone+"-api\\src\\main\\java\\com\\csii\\demo\\"+lowNone+"\\dto";
				String  _lastURL=verb+none;
				
				String _apiPath = "D:\\workpla\\ee\\7thJan\\pex-demo-parent\\"+lowNone+"\\"+lowNone+"-api\\src\\main\\java\\com\\csii\\demo\\"+lowNone+"\\api\\"+none+"Service.java";
				String  _usrapiFaceMethod=verb+none;
				String _douRePreNm = upVerb+none;
				
				String			_daoMapperClassPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\"+lowNone+"\\"+lowNone+"-dao\\src\\main\\java\\com\\csii\\demo\\"+lowNone+"\\dao\\mapper\\"+none+"Mapper.java";
				
				String			_daoMapperClassMethodNm=verb+none;
				
				String _daoMapperXmlPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\"+lowNone+"\\"+lowNone+"-dao\\src\\main\\resources\\db\\mapper\\"+lowNone+"Mapper.xml";
				
					
//					String _serviceJavaPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\"+lowNone+"\\"+lowNone+"-service\\src\\main\\java\\com\\csii\\demo\\"+lowNone+"\\service\\impl\\"+none+"ServiceImpl.java";
				String _serviceJavaPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\account\\account-service\\src\\main\\java\\com\\csii\\demo\\account\\service\\impl\\ModifyAccountMoney.java";
					String _serviceDubboXmlPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\"+lowNone+"\\"+lowNone+"-service\\src\\main\\resources\\META-INF\\config\\dubbo\\dubbo-services.xml";
				
				
				String $douReDirFormat=_douReDir.substring(_douReDir.lastIndexOf("\\java\\")+6);
				String _douRePackageNm = $douReDirFormat.replace('\\', '.');
				
				String $firstTrsFormat=_frontActionPath.substring(_frontActionPath.lastIndexOf("\\java\\")+6);
				String  _frontPackageNm=$firstTrsFormat.substring(0, $firstTrsFormat.lastIndexOf("\\")).replace('\\', '.');
				String  _frontActionNm=$firstTrsFormat.substring($firstTrsFormat.lastIndexOf("\\")+1,$firstTrsFormat.lastIndexOf("."));
				_firstTrsFirstRefText=_firstTrsFirstRefText==null?_frontActionNm:_firstTrsFirstRefText;
//				String  _frontPackageNm="com.csii.demo.product.action";
//				String  _frontActionNm="AddSuperAction";
				//将向dubbo-referrence.xml加的api是否已存在于dubbo-referrence.xml中
//				boolean _usrapiFaceExists=false;
				
				String $apiPathFormat=_apiPath.substring(_apiPath.lastIndexOf("\\java\\")+6);
				String  _usrapiFacePackageNm=$apiPathFormat.substring(0, $apiPathFormat.lastIndexOf("\\")).replace('\\', '.');
				String  _usrapiFaceNm=$apiPathFormat.substring($apiPathFormat.lastIndexOf("\\")+1,$apiPathFormat.lastIndexOf("."));
//				String  _usrapiFacePackageNm="com.csii.demo.product.api";
//				String  _usrapiFaceNm="ProAddSuperService";
				
//		String _apiMethod="adddd";
		
//		String _apiPackageNm = "com.csii.demo.usr.api";
		
		String $daoMapperClassPathFormat=_daoMapperClassPath.substring(_daoMapperClassPath.lastIndexOf("\\java\\")+6);
		String  _daoMapperPackageNm=$daoMapperClassPathFormat.substring(0, $daoMapperClassPathFormat.lastIndexOf("\\")).replace('\\', '.');
		String  _daoMapperClassNm=$daoMapperClassPathFormat.substring($daoMapperClassPathFormat.lastIndexOf("\\")+1,$daoMapperClassPathFormat.lastIndexOf("."));
//	String	_daoMapperPackageNm="";
//	String			_daoMapperClassNm="";
	
	String _frontActionDepenVarNm=_usrapiFaceNm.substring(0, 1).toLowerCase()+_usrapiFaceNm.substring(1);
	String _firstDubboServiceFaceId=_frontActionDepenVarNm;
	
	String _beanPackageNm = _beanQualifiedNm.substring(0, _beanQualifiedNm.lastIndexOf("."));
	String _beanClassNm = _beanQualifiedNm.substring( _beanQualifiedNm.lastIndexOf(".")+1);
	
	String _serviceDIMapperVar=_daoMapperClassNm.substring(0,1).toLowerCase()+_daoMapperClassNm.substring(1);
	String $rearPart=_serviceJavaPath.substring(_serviceJavaPath.lastIndexOf("\\java\\")+6);
	String $tmp=$rearPart.substring($rearPart.lastIndexOf("\\")+1,$rearPart.lastIndexOf("."));
	
	String _serviceJavaNmLower=$tmp.substring(0, 1).toLowerCase()+$tmp.substring(1);
	String _serviceJavaPackageNm=$rearPart.substring(0, $rearPart.lastIndexOf("\\")).replace('\\', '.');
	String _serviceJavaNm=$rearPart.substring($rearPart.lastIndexOf("\\")+1,$rearPart.lastIndexOf("."));
				//=========================================================
	 JdbcApp jdbcApp = new JdbcApp();  
	 Map<String, String> changedNOriginDbMap= new LinkedHashMap<String, String>();
     Map<String, String> dllmap = jdbcApp.queryOrclDll(_tabNm,changedNOriginDbMap);
     Draft apier = new Draft();
     apier.createDouRe(dllmap, _douRePreNm, _douReDir, _douRePackageNm, _beanPackageNm, _beanClassNm);
//     apier.crOrMoApi(_douRePreNm, _douRePackageNm, _apiMethod, _apiPath, _apiPackageNm);
     apier.crOrMoApi(_douRePreNm, _douRePackageNm, _usrapiFaceMethod, _apiPath, _usrapiFacePackageNm);
	//===========================================
				try {
					Document doc=new SAXReader().read(new File(_firstTrsPath));
					Element root=doc.getRootElement();
					
					//创建元素使用
//				       Element tran = DocumentHelper.createElement("transaction");
					root.addComment("the element below is created automatically");
					Element tran=root.addElement("transaction");
					tran.addAttribute("id",_lastURL);
					tran.addAttribute("template","publicQueryTemplate");
//					   Element desc = DocumentHelper.createElement("description");
//					   tran.addElement(qname)
					Element dsc = tran.addElement("description");
					dsc.addText(_firstTrsComment);
					
					Element as=tran.addElement("actions");
					Element ref=as.addElement("ref");
					ref.addText(_firstTrsFirstRefText);
					ref.addAttribute("name","action");
					Element action=root.addElement("action");
					action.addAttribute("name",_firstTrsFirstRefText);
					action.addAttribute("class",_frontPackageNm+"."+_frontActionNm);
					action.addAttribute("parent","BaseQueryAction");
					//给action加ref
					Element seRef = action.addElement("ref");
					seRef.addAttribute("name", _frontActionDepenVarNm);
					seRef.addText(_firstDubboServiceFaceId);
					
					//指定文件输出的位置
					FileOutputStream out =new FileOutputStream(_firstTrsPath);
					OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
					format.setEncoding("UTF-8");
					        
					        //1.创建写出对象
					       XMLWriter writer=new XMLWriter(out,format);
					       
					       //2.写出Document对象
					       writer.write(doc);
					       
					       //3.关闭流
					       writer.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				       
				       //--------------------------

				       try {

//				    	   String content = "package "+_frontPackageNm+";\n\nimport java.util.HashMap;\nimport java.util.Map;\n\nimport com.csii.ibs.action.IbsQueryAction;\nimport com.csii.pe.core.Context;\nimport com.csii.pe.core.PeException;\n\n\npublic class "+_frontActionNm+" extends IbsQueryAction {\n\n\t@SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n\t@Override \n\tpublic void execute(Context context) throws PeException {\n\t\t\n\t\tMap map = context.getDataMap();\n\t\tlog.debug(\"context:\" + context.getDataMap());\n\t\tmap.put(\"_HostTransactionCode\", \""+_lastURL+"\");\n\t\tMap result = (Map) this.issueHostTrs(context, map);\n\t\tlog.debug(\"result:\" + result);\n\t\t\n\t\tcontext.setData(\"json\", result);\n\t\t\n\t}\n}";
				    	   StringBuilder sbd = new StringBuilder("package "+_frontPackageNm+";\n\nimport "+_usrapiFacePackageNm+"."+_usrapiFaceNm+";\nimport "+_douRePackageNm+"."+_douRePreNm+"Request;\nimport "+_douRePackageNm+"."+_douRePreNm+"Response;\nimport com.csii.ibs.action.IbsQueryAction;\nimport com.csii.pe.core.Context;\nimport java.util.Date;\nimport java.math.BigDecimal;\nimport com.csii.pe.core.PeException;\n\npublic class "+_frontActionNm+" extends IbsQueryAction{\n\t\n\tprivate "+_usrapiFaceNm+" "+_firstDubboServiceFaceId+";\n\tpublic void set"+_usrapiFaceNm+"("+_usrapiFaceNm+" "+_firstDubboServiceFaceId+") {\n\t\tthis."+_firstDubboServiceFaceId+" = "+_firstDubboServiceFaceId+";\n\t}\n\t\n\t@Override\n\tpublic void execute(Context context) throws PeException {\n\t\t"+_douRePreNm+"Request req = new "+_douRePreNm+"Request();\n");
				    	   dllmap.forEach((k,v) -> {
				    		   String fiUpVar=k.substring(0, 1).toUpperCase()+k.substring(1);
				    		   sbd.append("\t\treq.set"+fiUpVar+"(");//context.getLong(\""+fiUpVar+"\"));\n
				    		   if("Date".equals(v)||"BigDecimal".equals(v)||"Float".equals(v)||"Double".equals(v)||"Short".equals(v)||"Byte".equals(v)){
				    			   sbd.append("("+v+")context.getData(\""+fiUpVar+"\"));\n");
				    		   }else{
				    			   sbd.append("context.get"+v+"(\""+fiUpVar+"\"));\n");
				    		   }
				    	   });
				    	   sbd.append("\t\t"+_douRePreNm+"Response response = "+_firstDubboServiceFaceId+"."+_usrapiFaceMethod+"(req);\n\t\tcontext.setData(\"json\", response);\n\t\t\n\t}\n\n}");
				    	   
				    	   File file = new File(_frontActionPath);

				    	   // if file doesnt exists, then create it
				    	   if (!file.exists()) {
				    	    file.createNewFile();
				    	   }

				    	   FileWriter fw = new FileWriter(file.getAbsoluteFile());
				    	   BufferedWriter bw = new BufferedWriter(fw);
				    	   bw.write(sbd.toString());
				    	   bw.close();
				    	   System.err.println(new StackTraceElement(_frontPackageNm+"."+_frontActionNm, "webLayerAction", _frontActionNm+".java", 1));
				    	   

				    	  } catch (IOException e) {
				    	   e.printStackTrace();
				    	  }
				     //--------------------------
				       try {
				    	   if(null!=_firstDubboPath){
				    		   
				    		   String content="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<beans xmlns=\"http://www.springframework.org/schema/beans\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dubbo=\"http://code.alibabatech.com/schema/dubbo\"\n\txmlns:service=\"http://www.csii.com.cn/pe/services\" xmlns:pe=\"http://www.csii.com.cn/schema/pe\"\n\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd\n\thttp://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd\n\thttp://www.csii.com.cn/schema/pe http://www.csii.com.cn/schema/pe/pe.xsd\n\thttp://www.csii.com.cn/pe/services http://www.csii.com.cn/pe/services/impl.xsd\">\n   \n   \n   <dubbo:reference id=\""+_firstDubboServiceFaceId+"\" interface=\""+_usrapiFacePackageNm+"."+_usrapiFaceNm+"\" protocol=\"dubbo\" timeout=\"100000\" check=\"false\" />\n   \n  \n</beans>";
				    		   File file = new File(_firstDubboPath);
				    		   
				    		   // if file doesnt exists, then create it
				    		   if (!file.exists()) {
				    			   file.createNewFile();
				    		   }
				    		   
				    		   FileWriter fw = new FileWriter(file.getAbsoluteFile());
				    		   BufferedWriter bw = new BufferedWriter(fw);
				    		   bw.write(content);
				    		   bw.close();
				    	   }
						/*Document doc=new SAXReader().read(new File(_firstDubboPath));
						   Element root=doc.getRootElement();
						   if(!_usrapiFaceExists){
						   	List dubrlist=root.elements("reference");
						   	List allChildren = root.elements();
						   	//创建元素使用
						       Element dubradd = DocumentHelper.createElement(new QName("dubbo:reference", root.getNamespace()));
						   	dubradd.addAttribute("id",_usrapiFaceNm);
						   	dubradd.addAttribute("interface",_usrapiFacePackageNm+"."+_usrapiFaceNm);
						   	dubradd.addAttribute("timeout","10000");
						   	dubradd.addAttribute("check","false");
						   	allChildren.add(dubrlist.size(), dubradd);
						   }
						   Element sertran=root.element("transport");
						   Element sermap=sertran.addElement("service:mapping");
						   sermap.addAttribute("trsCode",_lastURL);
						   sermap.addAttribute("method",_usrapiFaceNm+":"+_usrapiFaceMethod);

						   //指定文件输出的位置
						   FileOutputStream out =new FileOutputStream(_firstDubboPath);
						   OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
						   format.setEncoding("UTF-8");
						           
						           //1.创建写出对象
						          XMLWriter writer=new XMLWriter(out,format);
						          
						          //2.写出Document对象
						          writer.write(doc);
						          
						          //3.关闭流
						          writer.close();*/
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				     
				       MajorGener daomg = new MajorGener();
				       ComplexGener servicecg = new ComplexGener();
				       daomg.crOrMoMapperJ(_daoMapperPackageNm, _daoMapperClassNm, _daoMapperClassPath, _daoMapperClassReturnType, _daoMapperClassArgType, _daoMapperClassMethodNm, _daoMapperClassAnnoParamNm);
				       daomg.crOrMoMapperX(changedNOriginDbMap, _daoMapperXmlPath, _beanQualifiedNm, _whichCRUD, _tabNm, _daoMapperClassArgType, _daoMapperClassAnnoParamNm, _daoMapperClassMethodNm, _daoMapperClassNm, _daoMapperPackageNm);
				       servicecg.crOrMoDubboXml(_usrapiFaceNm, _usrapiFacePackageNm, _serviceDubboXmlPath, _serviceJavaNmLower, _serviceJavaNm, _serviceJavaPackageNm);
				       servicecg.crOrMoJava(_daoMapperClassMethodNm, _serviceDIMapperVar, _serviceJavaPath, _usrapiFacePackageNm, _usrapiFaceNm, _daoMapperPackageNm, _daoMapperClassNm, _douRePackageNm, _douRePreNm, _usrapiFaceMethod);
	}
	/**制造api.java文件已测成功
	 * 
	 */
	@Test
	public void testApiCM(){
		String _douRePreNm = "UserAdd";
		String _douRePackageNm = "com.csii.demo.usr.dto";
		String _apiMethod="adddd";
		String _apiPath = "D:\\workpla\\ee\\7thJan\\pex-demo-parent\\product\\product-api\\src\\main\\java\\com\\csii\\demo\\product\\api\\ProductService.java";
		String _apiPackageNm = "com.csii.demo.usr.api";
		Draft apier = new Draft();
		apier.crOrMoApi(_douRePreNm, _douRePackageNm, _apiMethod, _apiPath, _apiPackageNm);
	}
	/**
	 * 制造2RE已测成功
	 */
	@Test
	public void testApiDraft() throws Exception{
		String _douRePreNm = "Lalala";
		String _douReDir = "D:\\workpla\\ee\\7thJan\\pex-demo-parent\\product\\product-api\\src\\main\\java\\com\\csii\\demo\\product\\dto";
		String _douRePackageNm = "com.csii.demo.product.dto";
		String _beanPackageNm = "com.csii.demo.product.bean";
		String _beanClassNm = "Product";
		String _tabNm = "POC_PRODUCT";
		JdbcApp jdbcApp = new JdbcApp();  
	     Map<String, String> dllmap = jdbcApp.queryOrclDll(_tabNm,null);
	     Draft apier = new Draft();
	     apier.createDouRe(dllmap, _douRePreNm, _douReDir, _douRePackageNm, _beanPackageNm, _beanClassNm);
	     
	}
	/**
	 * MajorGener.crOrMoMapperJ成功
	 */
	@Test
	public void testDaoJavaG(){
		String	_daoMapperPackageNm="com.csii.demo.usr.dao.mapper";//创建时特有
		String			_daoMapperClassNm="UserMapper";//创建时特有
		String			_daoMapperClassPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\usr\\usr-dao\\src\\main\\java\\com\\csii\\demo\\usr\\dao\\mapper\\UserMapper.java";
		String			_daoMapperClassReturnType="com.csii.demo.usr.bean.POCUser";//全限定类名，lang包不用全限定
		String			_daoMapperClassArgType="java.util.Date";//全限定类名，lang包不用全限定
		String			_daoMapperClassMethodNm="lalala";
		String			_daoMapperClassAnnoParamNm="pfangpi";
		MajorGener mj = new MajorGener();
		mj.crOrMoMapperJ(_daoMapperPackageNm, _daoMapperClassNm, _daoMapperClassPath, _daoMapperClassReturnType, _daoMapperClassArgType, _daoMapperClassMethodNm, _daoMapperClassAnnoParamNm);
	}
	
	/**
	 * 
	 */
	@Test
	public void testcrOrMoMapperX(){
String _tabNm = "POC_ACCOUNT";
String			_daoMapperClassArgType="java.util.Map";//全限定类名，lang包不用全限定
String			_daoMapperClassMethodNm="exitstadd";
String			_daoMapperClassAnnoParamNm="mapstr";
String _daoMapperXmlPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\product\\product-dao\\src\\main\\resources\\db\\mapper\\NewInMapper.xml";
//此处必填
String _beanQualifiedNm="com.csii.demo.product.bean.Product";
//select或insert或update
String _whichCRUD="update";
String	_daoMapperPackageNm="com.csii.demo.product.dao.mapper";
String			_daoMapperClassNm="ProductMapper";
		//=========================================================
JdbcApp jdbcApp = new JdbcApp();  
Map<String, String> changedNOriginDbMap= new LinkedHashMap<String, String>();
try {
	jdbcApp.queryOrclDll(_tabNm,changedNOriginDbMap);
} catch (Exception e) {
	// TODO: handle exception
}

		MajorGener mj = new MajorGener();
//		mj.crOrMoMapperX(changedNOriginDbMap, _daoMapperXmlPath, _beanQualifiedNm, _whichCRUD, _tabNm, _daoMapperClassArgType, _daoMapperClassAnnoParamNm, _daoMapperClassMethodNm);
		mj.crOrMoMapperX(changedNOriginDbMap, _daoMapperXmlPath, _beanQualifiedNm, _whichCRUD, _tabNm, _daoMapperClassArgType, _daoMapperClassAnnoParamNm, _daoMapperClassMethodNm, _daoMapperClassNm, _daoMapperPackageNm);
	}
	@Test
	public void testServiceJa(){
		//将向dubbo-referrence.xml加的api是否已存在于dubbo-referrence.xml中
//		boolean _usrapiFaceExists=false;
		String  _usrapiFacePackageNm="com.csii.demo.product.api";
		String  _usrapiFaceNm="ProductService";
		String  _usrapiFaceMethod="methodisSuper";
		//若不需创建就赋null
String _douRePreNm = "AddProduct";
//尾不写分隔符
String _douRePackageNm = "com.csii.demo.product.dto";
//String _apiPackageNm = "com.csii.demo.usr.api";
String	_daoMapperPackageNm="com.csii.demo.product.dao.mapper";
String			_daoMapperClassNm="ProductMapper";
String			_daoMapperClassMethodNm="methodisSuperdaoMapperClassMethod";
//此处必填
//select或insert或update
String _serviceJavaPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\product\\product-service\\src\\main\\java\\com\\csii\\demo\\product\\service\\impl\\PraweafeImpl.java";
String _serviceDIMapperVar=_daoMapperClassNm.substring(0,1).toLowerCase()+_daoMapperClassNm.substring(1);

	
		ComplexGener cg = new ComplexGener();
		cg.crOrMoJava(_daoMapperClassMethodNm, _serviceDIMapperVar, _serviceJavaPath, _usrapiFacePackageNm, _usrapiFaceNm, _daoMapperPackageNm, _daoMapperClassNm, _douRePackageNm, _douRePreNm, _usrapiFaceMethod);
	}
	@Test
	public void testcrOrMoDubboXml(){
		//将向dubbo-referrence.xml加的api是否已存在于dubbo-referrence.xml中
//		boolean _usrapiFaceExists=false;
		String  _usrapiFacePackageNm="com.csii.demo.product.api";
		String  _usrapiFaceNm="ProductService";
		//若不需创建就赋null
//此处必填
//select或insert或update
String _serviceJavaPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\product\\product-service\\src\\main\\java\\com\\csii\\demo\\product\\service\\impl\\ProductServiceImpl.java";
String _serviceDubboXmlPath="D:\\workpla\\ee\\7thJan\\pex-demo-parent\\product\\product-service\\src\\main\\resources\\META-INF\\config\\dubbo\\dbwwwewa.xml";
String $rearPart=_serviceJavaPath.substring(_serviceJavaPath.lastIndexOf("\\java\\")+6);

String $tmp=$rearPart.substring($rearPart.lastIndexOf("\\")+1,$rearPart.lastIndexOf("."));

String _serviceJavaNmLower=$tmp.substring(0, 1).toLowerCase()+$tmp.substring(1);
String _serviceJavaPackageNm=$rearPart.substring(0, $rearPart.lastIndexOf("\\")).replace('\\', '.');
String _serviceJavaNm=$rearPart.substring($rearPart.lastIndexOf("\\")+1,$rearPart.lastIndexOf("."));


		ComplexGener cg = new ComplexGener();
		
		
		cg.crOrMoDubboXml(_usrapiFaceNm, _usrapiFacePackageNm, _serviceDubboXmlPath, _serviceJavaNmLower, _serviceJavaNm, _serviceJavaPackageNm);
	}
	@Test
	public void testDbInfoGrasp() throws Exception{
		String _tabNm = "POC_ACCOUNT";
		JdbcApp jdbcApp = new JdbcApp();  
		 Map<String, String> changedNOriginDbMap= new LinkedHashMap<String, String>();
	     Map<String, String> dllmap = jdbcApp.queryOrclDll(_tabNm,changedNOriginDbMap);
	     System.out.println("changedNOriginDbMap:");
	     changedNOriginDbMap.forEach((k,v)->System.out.println(k+" : "+v));
	     System.out.println("=========");
	     System.out.println("dllmap:");
	     dllmap.forEach((k,v)->System.out.println(k+" : "+v));
	}

}
