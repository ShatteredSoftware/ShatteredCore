package com.github.shatteredsuite.core.extension

import java.io.File

operator fun File.get(s: String): File {
    return File(this, s)
}
