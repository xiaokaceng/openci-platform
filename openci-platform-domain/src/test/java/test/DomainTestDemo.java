package test;

import org.openkoala.koala.util.KoalaBaseSpringTestCase;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.domain.Role;

/**
 * 领域层的测试类，直接继承KoalaBaseSpringTestCase
 * @author lingen
 *
 */
public class DomainTestDemo extends AbstractIntegrationTest {

	@Test
	public void test(){
		Role role = new Role("test");
		role.save();
	}
	
}