package com.xiaokaceng.openci.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;

public class ToolTest extends AbstractIntegrationTest {

	private Tool tool;
	
	@Before
	public void init() {
		tool = new Tool(null, null);
		tool.save();
	}
	
	@Test
	public void testUpdateIntegrationStatus() {
		Set<ToolInterfaceImplement> toolInterfaceImplements = new HashSet<ToolInterfaceImplement>();
		ToolInterfaceImplement toolInterfaceImplement = new ToolInterfaceImplement(tool, ToolInterface.ASSIGN_USER_TO_ROLE, true, null);
		toolInterfaceImplements.add(toolInterfaceImplement);
		
		tool.updateIntegrationStatus();
		assertEquals(ToolIntegrationStatus.SUCCESS, tool.getStatus());
		
		// TODO 需优化
	}
	
	@After
	public void destory() {
		tool.remove();
	}
}
