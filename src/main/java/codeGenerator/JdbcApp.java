package codeGenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.OracleJdbcTest;

public class JdbcApp {
	
/**制造表结构信息MAP
 * @param tabNm	db中的表名
 * @return
 * @throws Exception
 */
public Map<String, String> queryOrclDll(String tabNm,Map<String,String> byproductMap) throws Exception{
		OracleJdbcTest test = new OracleJdbcTest();
       
//            test.query("drop table student");
//        
//         
//        test.query("create table student(id int, name nchar(20))");
//         
//        test.query("insert into student values(1,'zhangsan')");
//         
//        test.query("insert into student values(2,'lisi')");
         
        test.query("select dbms_metadata.get_ddl('TABLE','"+tabNm+"') AS rr from dual", true);
        test.close();
        System.out.println(test.getRr());
        String rr = test.getRr();
        rr=rr.substring(rr.indexOf("(")+1, rr.lastIndexOf(",")+1);
        System.out.println(rr);
        String[] split = rr.split("\n");
        Map<String, String> mapFieldInfo=new LinkedHashMap<String, String>();
for (int i = 0; i < split.length; i++) {
	//删前空白和后逗号
	split[i]=split[i].substring(split[i].indexOf("\""), split[i].lastIndexOf(","));
	//删引号
	split[i]=split[i].replaceAll("\"", "");
	
	String oriDbField = split[i].substring(0, split[i].indexOf(" "));
	//完全处理field名
	String tm=split[i].substring(0, split[i].indexOf(" ")).toLowerCase();
//	tm="account_id_wce_fegr_v_iioop_m";
	String regx="_[a-zA-Z]";
	Pattern p = Pattern.compile(regx);
	Matcher m = p.matcher(tm);
	StringBuilder sb = new StringBuilder(tm);
	while(m.find()){
		int st=m.start();
		if(st==0)	continue;
		sb.setCharAt(st+1, String.valueOf(sb.charAt(st+1)).toUpperCase().charAt(0));
	}
	tm=sb.toString();
	tm=tm.replaceAll("_", "");
	//System.out.println(tm); //out:accountId
	byproductMap.put(tm,oriDbField);
	
	String rear=split[i].substring(split[i].indexOf(" ")+1);
	
	for(int j=0,leftBracketN=0;j<rear.length();j++){
		
		switch (rear.charAt(j)) {
		case '(':
			leftBracketN++;
			break;
		case ')':
			leftBracketN--;
			break;
		case ' ':
			if(leftBracketN==0)	rear=rear.substring(0, j);
			break;
		default:
			break;
		}
			
		
					
		
	}
//	System.out.println(rear);
	mapFieldInfo.put(tm, rear);
	
}

        
        System.out.println(mapFieldInfo.toString());//out:{accountId=NUMBER(11,0), userName=VARCHAR2(255 CHAR),...
        mapFieldInfo.forEach((k,v) -> {
        	if("DATE".equals(v))	mapFieldInfo.put(k, "Date");
        	if(v.indexOf("VARCHAR")!=-1)	mapFieldInfo.put(k, "String");
        	if(v.indexOf("NUMBER")!=-1){
        		StringBuilder sb = new StringBuilder(v);
        		String numWiCom = sb.substring(sb.indexOf("(")+1,sb.length()-1);
        		String[] args = numWiCom.split(",");
        		int[] intargs=new int[args.length];
        		for (int j = 0; j < args.length; j++) {
        			intargs[j]=Integer.parseInt(args[j]);
				}
        		int fi=intargs[0];
        		int se=intargs[1];
				if (18 < fi || (15 < fi && 0 < se))
					mapFieldInfo.put(k, "BigDecimal");
				else if (fi < 7 && 0 < se)
					mapFieldInfo.put(k, "Float");
				else if (6 < fi && fi < 16 && 0 < se)
					mapFieldInfo.put(k, "Double");
				else if (9 < fi && fi < 19)
					mapFieldInfo.put(k, "Long");
				else if (4 < fi && fi < 10)
					mapFieldInfo.put(k, "Integer");
				else if (2 < fi && fi < 5)
					mapFieldInfo.put(k, "Short");
				else if (fi == 2)
					mapFieldInfo.put(k, "Byte");
				else if (fi == 1)
					mapFieldInfo.put(k, "Boolean");
        	}
        });
        System.out.println(mapFieldInfo.toString());//out:{accountId=Long, userName=String,...
        return mapFieldInfo;
}
}
