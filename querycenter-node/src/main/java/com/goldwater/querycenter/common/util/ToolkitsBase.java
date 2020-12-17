package com.goldwater.querycenter.common.util;

import sun.misc.BASE64Encoder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ToolkitsBase {
    /**
     * 将从数据库取出的 Map 列表数据 键改为小写
     *
     * @author mxd
     * @param srcList
     *            源list
     * @return 返回键为小写的新list
     **/
    public static List<Map<String, Object>> listToLow(
            List<Map<String, Object>> srcList) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        if (srcList != null && srcList.size() > 0) {
            for (Map<String, Object> srcMap : srcList) {
                Map<String, Object> newMap = mapToLow(srcMap);
                result.add(newMap);
            }
        }
        return result;
    }

    /**
     * 将从数据库取出的 Map 数据 键改为小写
     *
     * @author mxd
     * @param srcMap
     *            源Map
     * @return 返回键为小写的新Map
     **/
    public static Map<String, Object> mapToLow(Map<String, Object> srcMap) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (srcMap != null && srcMap.size() > 0) {
            for (String key : srcMap.keySet()) {
                result.put(key.toLowerCase(), srcMap.get(key));
            }
        }
        return result;
    }

    /**
     * 转换成JSON
     */
    public static int MapToJSON = 1;
    /**
     * 转换成XML
     */
    public static int MapToXML = 2;

    /**
     * 将Map转换成Xml字符串
     */
    public static String convertToXml(Map<String, Object> map) {
        StringBuilder result = new StringBuilder();
        try {
            result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            result.append("<root>");

            // 加文件头信息
            result.append("<title errno=\"");
            result.append(map.get("errno").toString());
            result.append("\" errmas=\"");
            result.append(map.get("errmas").toString());

            result.append("\" results=\"");
            result.append(map.get("results").toString());
            result.append("\"/>");
            // 加文件头信息
            // 加文件数据信息
            result.append("<rows>");

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> list = (List<Map<String, Object>>) map
                    .get("rows");
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> temp = list.get(i);
                    result.append("<row");
                    for (String key : temp.keySet()) {
                        String value = temp.get(key) == null ? "NULL" : temp
                                .get(key).toString();
                        result.append(" " + key);
                        result.append("=\"");
                        result.append(value);
                        result.append("\"");
                    }
                    result.append("/>");
                }
            }
            result.append("</rows>");
            // 加文件数据信息
            result.append("</root>");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 得到排序后的List
     *
     * @author mxd 2016-06-18
     *
     */
    public static void doubleSortList(List<Map<String, Object>> list,
                                      final String keyName) {
        try {
            // 按涨幅降序排序
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1,
                                   Map<String, Object> o2) {
                    // TODO Auto-generated method stub
                    Double value1 = o1.get(keyName) == null ? -1 : Double
                            .parseDouble(o1.get(keyName).toString());
                    Double value2 = o2.get(keyName) == null ? -1 : Double
                            .parseDouble(o2.get(keyName).toString());
                    return value2.compareTo(value1);
                }
            });
        } catch (Exception ex) {
            System.out.println("排序出现问题:" + ex.getMessage());
        }
    }

    /**
     * 图片转字符 串
     *
     * @param imgFile
     *            图片路径
     */
    public static String imageToStr(String imgFile) {

        File file = new File(imgFile);
        if (file.exists()) {
            BufferedInputStream bis = null;
            FileInputStream stream = null;
            byte[] bytes = null;
            try {

                stream = new FileInputStream(file);
                bis = new BufferedInputStream(stream);
                bytes = new byte[bis.available()];
                bis.read(bytes);
                stream.close();
                bis.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
            return new BASE64Encoder().encodeBuffer(bytes);

        } else {

            return "";// ReadImage(imgFile,i++);

        }

    }

    /**
     * 多测站stcd 查询
     */
    public static String getQueryStcds(String colName, String stcds) {
        StringBuffer result = new StringBuffer();
        if (!stcds.equals("")) {

            String[] tempArray = stcds.split(",");

            int length = tempArray.length;
            if (length > 0) {
                String str = "";
                int sttp = 1000;

                result.append(" AND(");
                for (int i = 0; i < length; i++) {
                    String stcd = tempArray[i];

                    if (!str.equals("")) {
                        str += ",";
                    } else {
                        if (i == 0)
                            str = colName + " IN (";
                        else
                            str = " OR " + colName + " IN (";
                    }
                    str += "'" + stcd + "'";

                    if (((i + 1) % sttp == 0) || (i + 1) == length)// 1000
                    {
                        str = str + ")";
                        result.append(str);
                        str = "";
                    }
                }
                result.append(") ");
            }

        }
        return result.toString();
    }
}
