package tmp

import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertTrue

class Tmp {

    @ParameterizedTest(name = "{0}")
    @MethodSource("testData")
    fun testTmp(idx: Long) = handleIgnoredTests("testTmp[$idx]") {
        ignoreTest(idx % 2L == 0L) { "Test $idx ignored" }
        assertTrue(idx % 2L != 0L, "failed")
    }

    companion object {
        @JvmStatic
        fun testData(): List<Arguments> {
            val base = System.currentTimeMillis()
            return (0..3).map { Arguments.of(it + base) }
        }

        private class IgnoreTestException(message: String) : Exception(message)

        fun ignoreTest(message: () -> String) {
            throw IgnoreTestException(message())
        }

        fun ignoreTest(condition: Boolean, message: () -> String) {
            if (!condition) {
                throw IgnoreTestException(message())
            }
        }

        fun Any.handleIgnoredTests(testName: String, block: () -> Unit) {
            try {
                block()
            } catch (ignore: IgnoreTestException) {
                val testClassName = javaClass.canonicalName
                System.err.println("IGNORE $testClassName.$testName: ${ignore.message}")
                Assumptions.assumeTrue(false)
            }
        }
    }
}
