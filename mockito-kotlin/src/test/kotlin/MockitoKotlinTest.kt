/*
 * The MIT License
 *
 * Copyright (c) 2016 Niek Haarman
 * Copyright (c) 2007 Mockito contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.nhaarman.expect.expect
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Test

class MockitoKotlinTest {

    @After
    fun teardown() {
        MockitoKotlin.resetInstanceCreators()
    }

    @Test
    fun register() {
        /* Given */
        val closed = Closed()
        MockitoKotlin.registerInstanceCreator { closed }

        /* When */
        val result = createInstance<Closed>()

        /* Then */
        expect(result).toBe(closed)
    }

    @Test
    fun unregister() {
        /* Given */
        val closed = Closed()
        MockitoKotlin.registerInstanceCreator { closed }
        MockitoKotlin.unregisterInstanceCreator<Closed>()

        /* When */
        val result = createInstance<Closed>()

        /* Then */
        expect(result).toNotBeTheSameAs(closed)
    }

    @Test
    fun usingInstanceCreatorInsideLambda() {
        MockitoKotlin.registerInstanceCreator { CreateInstanceTest.ForbiddenConstructor(2) }

        mock<TestClass> {
            on { doSomething(any()) } doReturn ""
        }
    }

    interface TestClass {

        fun doSomething(c: CreateInstanceTest.ForbiddenConstructor): String
    }
}
