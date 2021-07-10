package me.drbaxr.codecoverage.util

import java.io.File

class FileTools {
    companion object {
        fun getFilePaths(directoryPath: String): List<String> {
            val filePathList = mutableListOf<String>()

            recursivelyScanDirectory(directoryPath, filePathList)

            return filePathList.map { it.subSequence(directoryPath.length + 1, it.length).toString() }
        }

        private fun recursivelyScanDirectory(path: String, listToAddPaths: MutableList<String>) {
            val file = File(path)

            file.list()?.forEach {
                val filePath = "$path/$it"
                val f = File(filePath)

                if (f.isDirectory)
                    recursivelyScanDirectory(filePath, listToAddPaths)
                else
                    listToAddPaths.add(filePath)
            }
        }
    }
}