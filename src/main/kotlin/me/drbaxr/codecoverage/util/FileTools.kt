package me.drbaxr.codecoverage.util

import java.io.File
import java.util.*

class FileTools {
    companion object {
        fun getDirectoryFilePaths(directoryPath: String): List<String> {
            val filePathList = mutableListOf<String>()

            recursivelyScanDirectory(directoryPath, filePathList)

            return filePathList.map { it.subSequence(directoryPath.length + 1, it.length).toString() }
        }

        fun getFileLines(filePath: String): List<String> {
            val file = File(filePath)
            val scanner = Scanner(file)

            val linesList = mutableListOf<String>()
            while (scanner.hasNextLine())
                linesList.add(scanner.nextLine())

            scanner.close()

            return linesList
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