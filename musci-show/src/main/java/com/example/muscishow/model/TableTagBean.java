/*
 * File TableTagBean.java in project jade-core
 *
 * (c) Jade Fortune Technology, Inc. 2005 - 2006 All rights reserved.
 * Proprietary and confidential.
 *
 * Created at 2006-11-1 by eric
 */
package com.example.muscishow.model;

import com.example.muscishow.until.CryptoUtil;
import com.example.muscishow.until.StringMap;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

/**
 * bean for TableTag
 *
 * @author eric
 */
public class TableTagBean
{
    public static final int DEFAULT_PAGE_SIZE = 20;

    private static final String DELIMITER = "\n";

    private int m_page;

    private int m_pageSize = DEFAULT_PAGE_SIZE;

    private int m_total;


    private String m_orderBy;


    private boolean m_asc;


    private StringMap m_condition = new StringMap();


	private List m_list;


    private boolean m_builded = false;

    private String m_conditionParam = null;

    private int m_pageCount;

    private boolean m_canPrev;

    private boolean m_canNext;

    public TableTagBean() {}

    public boolean getAsc() {
        return m_asc;
    }

    public void setAsc(boolean asc) {
        ensureNotBuilded();
        m_asc = asc;
    }

    public StringMap getCondition() {
        return m_condition;
    }

    public void setCondition(StringMap condition) {
        ensureNotBuilded();
        m_condition = condition;
    }

    public String getOrderBy() {
        return m_orderBy;
    }

    public void setOrderBy(String orderBy) {
        ensureNotBuilded();
        m_orderBy = orderBy;
    }

    public int getPage() {
        return m_page;
    }

    public void setPage(int page) {
        ensureNotBuilded();
        m_page = page;
    }

    public int getPageSize() {
        return m_pageSize;
    }

    public void setPageSize(int pageSize) {
        ensureNotBuilded();
        m_pageSize = pageSize;
    }

    public int getTotal() {
        return m_total;
    }

    public void setTotal(int total) {
        ensureNotBuilded();
        m_total = total;
    }

    @SuppressWarnings("rawtypes")
	public List getList() {
        return m_list;
    }

    @SuppressWarnings("rawtypes")
	public void setList(List list) {
        ensureNotBuilded();
        m_list = list;
    }

    public void ensureNotBuilded() {
        if (m_builded) {
            throw new IllegalStateException(
                    "Can't set attribute to TableTagBean after build() is called.");
        }
    }

    public boolean getCanNext() {
        return m_canNext;
    }

    public boolean getCanPrev() {
        return m_canPrev;
    }

    public String getConditionParam() {
        return m_conditionParam;
    }

    public int getPageCount() {
        return m_pageCount;
    }

    public void build() throws UnsupportedEncodingException {

        if (m_condition.size() > 0) {
            m_conditionParam = CryptoUtil.base64url_encode(m_condition
                    .toString(DELIMITER).getBytes("GBK"));
        }

        if (m_total % m_pageSize != 0) {
            m_pageCount = m_total / m_pageSize + 1;
        } else {
            m_pageCount = m_total / m_pageSize;
        }

        m_canPrev = m_page > 0;
        m_canNext = m_page < m_pageCount - 1;

        m_builded = true;
    }

    public String getQueryParameter() {
        StringBuffer sb=new StringBuffer();

        sb.append("p="+m_page);
        if(m_orderBy!=null) {
            sb.append("&o="+m_orderBy+"&a="+(m_asc?"1":"0"));
        }

        if(m_conditionParam!=null) {
            sb.append("&c="+m_conditionParam);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("[TableTagBean]\r\n");
        sb.append("m_page: " + m_page + "\r\n");
        sb.append("m_pageSize :" + m_pageSize + "\r\n");
        sb.append("m_total :" + m_total + "\r\n");
        sb.append("m_orderBy :" + m_orderBy + "\r\n");
        sb.append("m_asc :" + m_asc + "\r\n");
        sb.append("m_condition :" + m_condition + "\r\n");
        sb.append("m_list(size) :" + m_list.size() + "\r\n");
        sb.append("m_builded :" + m_builded + "\r\n");
        sb.append("m_conditionParam :" + m_conditionParam + "\r\n");
        sb.append("m_pageCount :" + m_pageCount + "\r\n");
        sb.append("m_canPrev :" + m_canPrev + "\r\n");
        sb.append("m_canNext :" + m_canNext + "\r\n");

        return sb.toString();
    }

	@SuppressWarnings("rawtypes")
	public static TableTagBean getFromExt(HttpServletRequest request)
	  {
//	    String start = request.getParameter("start");
//	    String limit = request.getParameter("limit");
//	    String asc = request.getParameter("dir");
//	    String orderBy = request.getParameter("sort");
	    String start = request.getParameter("page");
	    String limit = request.getParameter("pageSize");
	    String asc = request.getParameter("asc");
	    String orderBy = request.getParameter("orderBy");
	    int page = (start != null) ? Integer.parseInt(start) : 0;
	    int pageSize = (limit != null) ? Integer.parseInt(limit) : 30;
	    boolean isAsc = "ASC".equalsIgnoreCase(asc);
	    if ("true".equalsIgnoreCase(asc))
	    	isAsc=true;
	    else if ("false".equalsIgnoreCase(asc))
	    	isAsc=false;

	    TableTagBean ttb = new TableTagBean();
	    ttb.setPage(page);
	    ttb.setPageSize(pageSize);
	    ttb.setOrderBy(orderBy);
	    ttb.setAsc(isAsc);


		Map<String,String[]> m = request.getParameterMap();
	    if (m != null) {
	      for (Entry entry : m.entrySet()) {
	        String key = (String)entry.getKey();
	        Object[] value = (Object[])entry.getValue();
	        ttb.getCondition().put(key, (value[0] == null) ? null : value[0].toString());
	      }
	    }
	    return ttb;
	  }


	public void rebuildOrderBy(HashMap<String,String> orderMapping)
	{
		if (this.m_orderBy!=null)
		{
			try
			{
				final String columnName=orderMapping.get(this.m_orderBy);
				if (columnName!=null)
				{
					if (columnName.length()==0)
						this.m_orderBy=null;
					else
						this.m_orderBy=columnName;
				}
			} catch (Exception e)
			{}
		}
	}
}
