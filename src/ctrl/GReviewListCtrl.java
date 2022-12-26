package ctrl;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import vo.*;
import svc.*;
import java.util.*;

@WebServlet("/g_review_list")
public class GReviewListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GReviewListCtrl() { super(); }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 5, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
    	// ���� ������ ��ȣ, ������ ũ��, ��� ũ��, ���ڵ�(��ǰ) ����, ������ ����, ���� ������ ��ȣ ���� ������ ����
    	if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}
    	String where = "";
    	String sch = request.getParameter("sch");	// 
    		
    	if (sch == null) {
    		sch = "";
    	}
    	if (sch != null && !sch.equals("")) {
    	// �˻����� ���� :  &sch=vy,sasdfnkl:sdafjbasdf			�Խÿ���, �ۼ���id-����title-��ü��gname-����content-����+����tc
    		String[] arrSch = sch.split(",");
    		for (int i = 0; i < arrSch.length; i++) {
    			char c = arrSch[i].charAt(0);
    			if (c == 'v') {
    				where += " and gr_isview = '" + arrSch[i].substring(1) + "' ";
    			} else if (c == 's') {
    				String[] arrTmp = arrSch[i].substring(1).split(":");
    				if(arrTmp[0].equals("id")) {
    					where += " and mi_id like '%" + arrTmp[1] + "%' ";
    				} else if(arrTmp[0].equals("title")) {
    					where += " and gr_title like '%" + arrTmp[1] + "%' ";
    				} else if(arrTmp[0].equals("gname")) {
    					where += " and gq_gname like '%" + arrTmp[1] + "%' ";
    				} else if(arrTmp[0].equals("content")) {
    					where += " and gr_content like '%" + arrTmp[1] + "%' ";
    				} else if(arrTmp[0].equals("tc")) {
    					where += " and (gr_content like '%" + arrTmp[1] + "%' or gr_title like '%" + arrTmp[1] + "%')";
    				}
    			}
    		}
    	}
    	GReviewListSvc gReviewListSvc = new GReviewListSvc();
    	rcnt = gReviewListSvc.getGReviewCount(where);	// �˻��� ��ǰ�� �� ������ ��ü ���������� ���� �� ���
    	
    	ArrayList<GReviewInfo> gReviewList = gReviewListSvc.getGReviewList(cpage, psize, where); 

    	pcnt = rcnt / psize;
    	if (rcnt % psize > 0) {
    		pcnt++;	// ��ü ������ ��
    	}
    	spage = (cpage - 1) / psize * psize + 1;	// ��� ���� ������ ��ȣ
    	PageInfo pageInfo = new PageInfo();
    	pageInfo.setBsize(bsize);			pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);				pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);				pageInfo.setSpage(spage);
		pageInfo.setSch(sch);				
		// ����¡ ���� ������� �˻� �� ���� �������� pageInfo �ν��Ͻ��� ����
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("gReviewList", gReviewList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("bbs/g_review_list.jsp");
		dispatcher.forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
