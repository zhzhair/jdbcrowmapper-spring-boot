/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.demo;

import com.example.demo.jdbc.CodeGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Andy Wilkinson
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleDataJdbcApplicationTests {

	@Test
	public void testReflect() {
//		String codeStr = CodeGeneratorUtils.getEntity();//输出所有表的实体类的内容
//		String codeStr = CodeGeneratorUtils.getEntity("feedback");//输出指定表的实体类的内容
		String codeStr = CodeGeneratorUtils.getRowMapper();//输出所有表的RowMapper的内容
//		String codeStr = CodeGeneratorUtils.getRowMapper("feedback");//输出指定表的RowMapper的内容
//		String codeStr = CodeGeneratorUtils.getSqls();//输出所有表增删改查的sql语句的内容
//		String codeStr = CodeGeneratorUtils.getSqls("feedback_reply");//输出指定表增删改查的sql语句的内容
		System.err.println(codeStr);
	}

}
