package com.example.musicapi.common.model;


import com.example.musicapi.common.until.CryptoUtil;
import com.example.musicapi.common.until.StringMap;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerchBean {
    //默认
    private static final int DEFAULT_PAGE_ROWS = 5;
    private static final int DEFAULT_PAGE_START = 1;
    private static final String DELIMITER = "\n";
    //每页多少条
    private int rows = DEFAULT_PAGE_ROWS;
    //当前页码
    private int pageStart = DEFAULT_PAGE_START;
    //总数
    private int total;
    //排序字段
    private String orderby;
    //升序降序
    private boolean asc;
    //查询字段
    private StringMap condition = new StringMap();
    //本页数据列表
    private List list;
    // 是否已经build
    private boolean builded = false;

    private String conditionParam = null;

    private int pageCount;

    private boolean canPrev;

    private boolean canNext;



    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        ensureNotBuilded();
        this.rows = rows;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        ensureNotBuilded();
        this.pageStart = pageStart;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        ensureNotBuilded();
        this.total = total;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        ensureNotBuilded();
        this.orderby = orderby;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        ensureNotBuilded();
        this.asc = asc;
    }

    public StringMap getCondition() {
        return condition;
    }

    public void setCondition(StringMap condition) {
        ensureNotBuilded();
        this.condition = condition;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        ensureNotBuilded();
        this.list = list;
    }

    public void ensureNotBuilded() {
        if (builded) {
            throw new IllegalStateException(
                    "Can't set attribute to TableTagBean after build() is called.");
        }
    }
    public String getConditionParam() {
        return conditionParam;
    }

    public int getPageCount() {
        return pageCount;
    }

    public boolean isCanPrev() {
        return canPrev;
    }

    public boolean isCanNext() {
        return canNext;
    }

    /**
     * 计算总页数，是否可以前一页，后一页，首页，末页，生成查询条件编码后的字符串。
     *
     * NOTE:调用build以后将不能再设置该对象的属性
     *
     * @throws UnsupportedEncodingException
     */
    public void build() throws UnsupportedEncodingException {

        if (condition.size() > 0) {
            conditionParam = CryptoUtil.base64url_encode(condition.toString(DELIMITER).getBytes("GBK"));
        }

        if (total % rows != 0) {
            pageCount = total / rows + 1;
        } else {
            pageCount = total / rows;
        }

        canPrev = pageStart > 0;
        canNext = pageStart < pageCount - 1;

        builded = true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("[TableTagBean]\r\n");
        sb.append("pageStart: " + pageStart + "\r\n");
        sb.append("rows :" + rows + "\r\n");
        sb.append("total :" + total + "\r\n");
        sb.append("orderby :" + orderby + "\r\n");
        sb.append("asc :" + asc + "\r\n");
        sb.append("condition :" + condition + "\r\n");
        sb.append("list(size) :" + list.size() + "\r\n");
        sb.append("builded :" + builded + "\r\n");
        sb.append("conditionParam :" + conditionParam + "\r\n");
        sb.append("pageCount :" + pageCount + "\r\n");
        sb.append("canPrev :" + canPrev + "\r\n");
        sb.append("canNext :" + canNext + "\r\n");

        return sb.toString();
    }
    /**
     * 获取request里的参数
     * @param request
     * @return
     */
    public static SerchBean getFromExt(HttpServletRequest request)
    {
        String start = request.getParameter("pageStart");
        String limit = request.getParameter("rows");
        String asc = request.getParameter("asc");
        String orderBy = request.getParameter("orderBy");
        int pageStart = (start != null) ? Integer.parseInt(start) : DEFAULT_PAGE_START;
        int rows = (limit != null) ? Integer.parseInt(limit) : DEFAULT_PAGE_ROWS;
        boolean isAsc = "ASC".equalsIgnoreCase(asc);
        if ("true".equalsIgnoreCase(asc))
            isAsc=true;
        else if ("false".equalsIgnoreCase(asc))
            isAsc=false;
        SerchBean srb = new SerchBean();
        srb.setPageStart((pageStart-1)*rows);
        srb.setRows(rows);
        srb.setOrderby(orderBy);
        srb.setAsc(isAsc);
        Map<String,String[]> m = request.getParameterMap();
        if (m != null) {
            for (Map.Entry entry : m.entrySet()) {
                String key = (String)entry.getKey();
                Object[] value = (Object[])entry.getValue();
                srb.getCondition().put(key, (value[0] == null) ? null : value[0].toString());
            }
        }
        return srb;
    }

    /**
     * 设置排序字段
     * @param orderMapping
     */
    public void rebuildOrderBy(HashMap<String,String> orderMapping){
       if(this.orderby != null){
           try{
               final String columnName = orderMapping.get(this.orderby);
               if (columnName != null){
                  if (columnName.length() == 0){
                      this.orderby = null;
                  }
                  else {
                      this.orderby = columnName;
                  }
               }

           }catch (Exception e){

           }
       }
    }

}
