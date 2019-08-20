package beanUtilsDraft;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

public class Testfaewfw {

	@Test
	public void test1() throws Exception {
		Stu s = new Stu();
		Map m=new HashMap();
		m.put("name", "mik");
		m.put("pass", "roo");
		BeanUtils.populate(s, m);
		System.out.println(s.toString());
		//结论：map中的key首字母必小写，定义bean时，field首字母随意
	}
}

