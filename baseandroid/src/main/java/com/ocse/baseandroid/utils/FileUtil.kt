package com.ocse.baseandroid.utils

import java.io.*
import java.nio.charset.StandardCharsets

class FileUtil private constructor() {
    /**
     * @param path 传入路径字符串
     * @return File
     */
    open fun createFileIfNotExist(path: String): File {
        val file = File(path)
        if (!file.exists()) {
            try {
                File(path.substring(0, path.lastIndexOf(File.separator)))
                    .mkdirs()
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }

    /**
     * @param path 传入路径字符串
     * @return File
     */
     fun createDirIfNotExist(path: String?): File {
        val file = File(path)
        if (!file.exists()) {
            try {
                file.mkdirs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return file
    }
    // 删除文件夹
    // param folderPath 文件夹完整绝对路径
    /**
     * @param path
     * @return
     */
     fun isExist(path: String?): Boolean {
        val file = File(path)
        return file.exists()
    }

    /**
     * 创建新的文件，如果有旧文件，先删除再创建
     *
     * @param path
     * @return
     */
     fun createNewFile(path: String): File {
        val file = File(path)
        if (isExist(path)) {
            file.delete()
        }
        createFileIfNotExist(path)
        return file
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
     fun deleteFile(path: String?): Boolean {
        val file = File(path)
        if (isExist(path)) {
            file.delete()
        }
        return true
    }

    // 删除一个目录
     fun deleteFileDir(path: String): Boolean {
        var flag = false
        val file = File(path)
        if (!isExist(path)) {
            return flag
        }
        if (!file.isDirectory) {
            file.delete()
            return true
        }
        val filelist = file.list()
        var temp: File? = null
        for (i in filelist.indices) {
            temp = if (path.endsWith(File.separator)) {
                File(path + filelist[i])
            } else {
                File(path + File.separator + filelist[i])
            }
            if (temp.isFile) {
                temp.delete()
            }
            if (temp.isDirectory) {
                deleteFileDir(path + "/" + filelist[i]) // 先删除文件夹里面的文件
            }
        }
        file.delete()
        flag = true
        return flag
    }

     fun delFolder(folderPath: String) {
        try {
            delAllFile(folderPath) // 删除完里面所有内容
            var filePath: String? = folderPath
            filePath = filePath
            val myFilePath = File(filePath)
            myFilePath.delete() // 删除空文件夹
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
     fun delAllFile(path: String): Boolean {
        var flag = false
        val file = File(path)
        if (!file.exists()) {
            return flag
        }
        if (!file.isDirectory) {
            return flag
        }
        val tempList = file.list()
        var temp: File? = null
        for (i in tempList.indices) {
            temp = if (path.endsWith(File.separator)) {
                File(path + tempList[i])
            } else {
                File(path + File.separator + tempList[i])
            }
            if (temp.isFile) {
                temp.delete()
            }
            if (temp.isDirectory) {
                delAllFile(path + "/" + tempList[i]) // 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]) // 再删除空文件夹
                flag = true
            }
        }
        return flag
    }

    fun getFileName(rootpath: String?): Array<String?>? {
        val root = File(rootpath)
        val filesOrDirs = root.listFiles()
        return if (filesOrDirs != null) {
            val dir = arrayOfNulls<String>(filesOrDirs.size)
            var num = 0
            for (i in filesOrDirs.indices) {
                if (filesOrDirs[i].isDirectory) {
                    dir[i] = filesOrDirs[i].name
                    num++
                }
            }
            val dirR = arrayOfNulls<String>(num)
            num = 0
            for (i in dir.indices) {
                if (dir[i] != null && dir[i] != "") {
                    dirR[num] = dir[i]
                    num++
                }
            }
            dirR
        } else {
            null
        }
    }

    /**
     * //获得流
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    @Throws(FileNotFoundException::class, UnsupportedEncodingException::class)
     fun getWriter(path: String?): BufferedWriter {
        var fileout: FileOutputStream? = null
        fileout = FileOutputStream(File(path))
        var writer: OutputStreamWriter? = null
        writer = OutputStreamWriter(fileout, StandardCharsets.UTF_8)
        return BufferedWriter(writer)
    }

    @Throws(FileNotFoundException::class)
     fun getInputStream(path: String): InputStream? {
        // if(Comments.DEBUG) System.out.println("path:"+path);
        var filein: FileInputStream? = null
        // if(Comments.DEBUG) System.out.println("2");
        // File file = createFileIfNotExist(path);
        val file = createFileIfNotExist(path)
        filein = FileInputStream(file)
        var `in`: BufferedInputStream? = null
        if (filein != null) {
            `in` = BufferedInputStream(filein)
        }
        return `in`
    }

    fun stateXmlControl(path: String?): Boolean {
        val f = File(path)
        return if (!f.exists()) {
            false
        } else f.length() != 0L
    }

    /**
     * 把输入流中的数据输入到Path里的文件里
     *
     * @param path
     * @param inputStream
     * @return
     */
     fun writeFromInputToSD(path: String, inputStream: InputStream): File? {
        var file: File? = null
        var output: OutputStream? = null
        try {
            file = createFileIfNotExist(path)
            output = FileOutputStream(file)
            val buffer = ByteArray(4 * 1024)
            var temp: Int
            while (inputStream.read(buffer).also { temp = it } != -1) {
                output.write(buffer, 0, temp)
            }
            output.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                output!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return file
    }

    /**
     * 把数据输入到Path里的文件里
     *
     * @param path
     * @return
     */
     fun writeFromInputToSD(path: String, b: ByteArray?): File? {
        var file: File? = null
        var output: OutputStream? = null
        try {
            file = createFileIfNotExist(path)
            output = FileOutputStream(file)
            output.write(b)
            output.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                output!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return file
    }

    /**
     * 方法：把一段文本保存到给定的路径中.
     */
    fun saveTxtFile(filePath: String, text: String) {
        var text = text
        try {
            // 首先构建一个文件输出流,用于向文件中写入数据.
            createFileIfNotExist(filePath)
            val txt = readTextLine(filePath)
            text += txt
            val out = FileOutputStream(filePath)
            // 构建一个写入器,用于向流中写入字符数据
            val writer = OutputStreamWriter(out, "gb2312")
            writer.write(text)
            // 关闭Writer,关闭输出流
            writer.close()
            out.close()
        } catch (e: Exception) {
            val ext = e.localizedMessage
            // Toast.makeText(this, ext, Toast.LENGTH_LONG).show();
        }
    }

    fun clearTxtFile(filePath: String?) {
        try {
            // 首先构建一个文件输出流,用于向文件中写入数据.
            val text = ""
            val out = FileOutputStream(filePath)
            // 构建一个写入器,用于向流中写入字符数据
            val writer = OutputStreamWriter(out, "gb2312")
            writer.write(text)
            // 关闭Writer,关闭输出流
            writer.close()
            out.close()
        } catch (e: Exception) {
            val ext = e.localizedMessage
            // Toast.makeText(this, ext, Toast.LENGTH_LONG).show();
        }
    }

    // 读取一个给定的文本文件内容,并把内容以一个字符串的形式返回
    private fun readTextLine(textFile: String?): String {
        return try {
            // 首先构建一个文件输入流,该流用于从文本文件中读取数据
            val input = FileInputStream(textFile)
            // 为了能够从流中读取文本数据,我们首先要构建一个特定的Reader的实例,
            // 因为我们是从一个输入流中读取数据,所以这里适合使用InputStreamReader.
            val streamReader = InputStreamReader(
                input,
                "gb2312"
            )
            // 为了能够实现一次读取一行文本的功能,我们使用了 LineNumberReader类,
            // 要构建LineNumberReader的实例,必须要传一个Reader实例做参数,
            // 我们传入前面已经构建好的Reder.
            val reader = LineNumberReader(streamReader)
            // 字符串line用来保存每次读取到的一行文本.
            var line: String? = null
            // 这里我们使用一个StringBuilder来存储读取到的每一行文本,
            // 之所以不用String,是因为它每次修改都会产生一个新的实例,
            // 所以浪费空间,效率低.
            val allLine = StringBuilder()
            // 每次读取到一行,直到读取完成
            while (reader.readLine().also { line = it } != null) {
                allLine.append(line)
                // 这里每一行后面,加上一个换行符,LINUX中换行是”\n”,
                // windows中换行是”\r\n”.
                allLine.append("\n")
            }
            // 把Reader和Stream关闭
            streamReader.close()
            reader.close()
            input.close()
            // 把读取的字符串返回
            allLine.toString()
        } catch (e: Exception) {
            // Toast.makeText(this, e.getLocalizedMessage(),
            // Toast.LENGTH_LONG).show();
            ""
        }
    }

    /**
     * 取Asset文件夹下文件
     *
     * @param paramString
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getAssetsInputStream(
        paramString: String?
    ): InputStream {
        return ObtainApplication.app!!.resources.assets.open(paramString!!)
    }

    companion object {
        private var util: FileUtil? = null
        val instance: FileUtil
            get() {
                if (util == null) {
                    util = FileUtil()
                }
                return util!!
            }

        /**
         * 将InputStream转换成byte数组
         *
         * @param in InputStream
         * @return byte[]
         * @throws IOException
         */
        @Throws(IOException::class)
        fun inputStreamTOByte(`in`: InputStream): ByteArray {
            val outStream = ByteArrayOutputStream()
            var data: ByteArray? = ByteArray(6 * 1024)
            var count = -1
            while (`in`.read(data, 0, 4 * 1024).also { count = it } != -1) {
                outStream.write(data, 0, count)
            }
            data = null
            return outStream.toByteArray()
        }

        /**
         * 将OutputStream转换成byte数组
         *
         * @return byte[]
         * @throws IOException
         */
        @Throws(IOException::class)
        fun outputStreamTOByte(out: OutputStream): ByteArray {
            val data = ByteArray(6 * 1024)
            out.write(data)
            return data
        }

        /**
         * 将byte数组转换成InputStream
         *
         * @param in
         * @return
         * @throws Exception
         */
        fun byteTOInputStream(`in`: ByteArray?): InputStream {
            return ByteArrayInputStream(`in`)
        }

        /**
         * 将byte数组转换成OutputStream
         *
         * @param in
         * @return
         * @throws IOException
         * @throws Exception
         */
        @Throws(IOException::class)
        fun byteTOOutputStream(`in`: ByteArray?): OutputStream {
            val out = ByteArrayOutputStream()
            out.write(`in`)
            return out
        }
    }
}