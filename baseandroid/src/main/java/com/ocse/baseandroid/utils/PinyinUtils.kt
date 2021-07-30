package com.ocse.baseandroid.utils

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination
import java.util.*

/**
 * 获取拼音
 */
object PinyinUtils {
    fun getPingYin(inputString: String): String {
        val format = HanyuPinyinOutputFormat()
        format.caseType = HanyuPinyinCaseType.LOWERCASE
        format.toneType = HanyuPinyinToneType.WITHOUT_TONE
        format.vCharType = HanyuPinyinVCharType.WITH_V
        val input = inputString.trim { it <= ' ' }.toCharArray()
        var output = ""
        try {
            for (curChar in input) {
                output += if (curChar.toString().matches(("[\\u4E00-\\u9FA5]+").toRegex())) {
                    val temp =
                        PinyinHelper.toHanyuPinyinStringArray(curChar, format)
                    temp[0]
                } else {
                    curChar.toString()
                }
            }
        } catch (e: BadHanyuPinyinOutputFormatCombination) {
            e.printStackTrace()
        }
        return output
    }

    /**
     * 获取第一个字的拼音首字母
     *
     * @param chinese
     * @return
     */
    fun getFirstSpell(chinese: String): String {
        val pinYinBF = StringBuffer()
        val arr = chinese.toCharArray()
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = HanyuPinyinCaseType.LOWERCASE
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE
        for (curChar in arr) {
            if (curChar.toInt() > 128) {
                try {
                    val temp =
                        PinyinHelper.toHanyuPinyinStringArray(curChar, defaultFormat)
                    if (temp != null) {
                        pinYinBF.append(temp[0][0])
                    }
                } catch (e: BadHanyuPinyinOutputFormatCombination) {
                    e.printStackTrace()
                }
            } else {
                pinYinBF.append(curChar)
            }
        }
        return pinYinBF.toString().replace("\\W".toRegex(), "").trim { it <= ' ' }
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变，特殊字符丢失 支持多音字，生成方式如（长沙市长:cssc,zssz,zssc,cssz）
     *
     * @param chines 汉字
     * @return 拼音
     */
    fun converterToFirstSpell(chines: String): String {
        val pinyinName = StringBuffer()
        val nameChar = chines.toCharArray()
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = HanyuPinyinCaseType.LOWERCASE
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE
        for (i in nameChar.indices) {
            if (nameChar[i] > 128.toChar()) {
                try {
                    // 取得当前汉字的所有全拼
                    val str = PinyinHelper.toHanyuPinyinStringArray(
                        nameChar[i], defaultFormat
                    )
                    if (str != null) {
                        for (j in str.indices) {
                            // 取首字母
                            pinyinName.append(str[j][0])
                            if (j != str.size - 1) {
                                pinyinName.append(",")
                            }
                        }
                    }
                    // else {
                    // pinyinName.append(nameChar[i]);
                    // }
                } catch (e: BadHanyuPinyinOutputFormatCombination) {
                    e.printStackTrace()
                }
            } else {
                pinyinName.append(nameChar[i])
            }
            pinyinName.append(" ")
        }
        // return pinyinName.toString();
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()))
    }

    /**
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen
     * ,chongdangshen,zhongdangshen,chongdangcan）
     *
     * @param chines 汉字
     * @return 拼音
     */
    fun converterToSpell(chines: String): String {
        val pinyinName = StringBuffer()
        val nameChar = chines.toCharArray()
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = HanyuPinyinCaseType.LOWERCASE
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE
        for (i in nameChar.indices) {
            if (nameChar[i] > 128.toChar()) {
                try {
                    // 取得当前汉字的所有全拼
                    val str = PinyinHelper.toHanyuPinyinStringArray(
                        nameChar[i], defaultFormat
                    )
                    if (str != null) {
                        for (j in str.indices) {
                            pinyinName.append(str[j])
                            if (j != str.size - 1) {
                                pinyinName.append(",")
                            }
                        }
                    }
                } catch (e: BadHanyuPinyinOutputFormatCombination) {
                    e.printStackTrace()
                }
            } else {
                pinyinName.append(nameChar[i])
            }
            pinyinName.append(" ")
        }
        // return pinyinName.toString();
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()))
    }

    /**
     * 去除多音字重复数据
     *
     * @param theStr
     * @return
     */
    private fun discountTheChinese(theStr: String): List<Map<String, Int>> {
        // 去除重复拼音后的拼音列表
        val mapList: MutableList<Map<String, Int>> =
            ArrayList()
        // 用于处理每个字的多音字，去掉重复
        var onlyOne: MutableMap<String, Int>
        val firsts = theStr.split(" ".toRegex()).toTypedArray()
        // 读出每个汉字的拼音
        for (str in firsts) {
            onlyOne = Hashtable()
            val china = str.split(",".toRegex()).toTypedArray()
            // 多音字处理
            for (s in china) {
                var count = onlyOne[s]
                if (count == null) {
                    onlyOne[s] = 1
                } else {
                    onlyOne.remove(s)
                    count++
                    onlyOne[s] = count
                }
            }
            mapList.add(onlyOne)
        }
        return mapList
    }

    /**
     * 解析并组合拼音，对象合并方案(推荐使用)
     *
     * @return
     */
    private fun parseTheChineseByObject(
        list: List<Map<String, Int>>
    ): String {
        var first: MutableMap<String, Int>? = null // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (i in list.indices) {
            // 每一组集合与上一次组合的Map
            val temp: MutableMap<String, Int> =
                Hashtable()
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (s in first.keys) {
                    for (s1 in list[i].keys) {
                        val str = s + s1
                        temp[str] = 1
                    }
                }
                // 清理上一次组合数据
                if (!temp.isNullOrEmpty()) {
                    first.clear()
                }
            } else {
                for (s in list[i].keys) {
                    temp[s] = 1
                }
            }
            // 保存组合数据以便下次循环使用
            if (!temp.isNullOrEmpty()) {
                first = temp
            }
        }
        var returnStr = ""
        if (first != null) {
            // 遍历取出组合字符串
            for (str in first.keys) {
                returnStr += "$str,"
            }
        }
        if (returnStr.isNotEmpty()) {
            returnStr = returnStr.substring(0, returnStr.length - 1)
        }
        return returnStr
    }
}