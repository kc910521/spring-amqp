/*
 * Copyright 2016-2019 the original author or authors.
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

package org.springframework.amqp.rabbit.test.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

import org.junit.Test;

/**
 * @author Gary Russell
 * @since 1.6
 *
 */
public class AnswerTests {

	@Test
	public void testLambda() {
		Foo foo = spy(new Foo());
		doAnswer(new LambdaAnswer<String>(true, (i, r) -> r + r)).when(foo).foo(anyString());
		assertThat(foo.foo("foo")).isEqualTo("FOOFOO");
		doAnswer(new LambdaAnswer<String>(true, (i, r) -> r + i.getArguments()[0])).when(foo).foo(anyString());
		assertThat(foo.foo("foo")).isEqualTo("FOOfoo");
		doAnswer(new LambdaAnswer<String>(false, (i, r) ->
			"" + i.getArguments()[0] + i.getArguments()[0])).when(foo).foo(anyString());
		assertThat(foo.foo("foo")).isEqualTo("foofoo");
	}

	private static class Foo {

		Foo() {
			super();
		}

		public String foo(String foo) {
			return foo.toUpperCase();
		}

	}

}
