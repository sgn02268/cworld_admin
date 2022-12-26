package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/g_qna_list")
public class GQnaListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GQnaListCtrl() { super(); }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 5, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
    	// ���� ������ ��ȣ, ������ ũ��, ��� ũ��, ���ڵ�(��ǰ) ����, ������ ����, ���� ������ ��ȣ ���� ������ ����
    	if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}
    	String sch = request.getParameter("sch");	// 

    	if (sch == null) {
    		sch = "";
    	}
    	String where = "";
    	if (sch != null && !sch.equals("")) {
    	// �˻����� ���� :  &sch=itest,g2��,vy,ay,py			���̵�, ��ü��, �Խÿ���, �亯����, �������� 
    		String[] arrSch = sch.split(",");
    		for (int i = 0; i < arrSch.length; i++) {
    			char c = arrSch[i].charAt(0);
    			if (c == 'i') {
    				where += " and mi_id like '%" + arrSch[i].substring(1) + "%' ";
    			} else if (c == 'g') {
    				where += " and gq_gname like '%" + arrSch[i].substring(1) + "%' ";
    			} else if (c == 'v') {
    				where += " and gq_isview = '" + arrSch[i].substring(1) + "' ";
    			} else if (c == 'a') {
    				where += " and gq_isreply = '" + arrSch[i].substring(1) + "' ";
    			} else if (c == 'p') {
    				where += " and gq_pay = '" + arrSch[i].substring(1) + "' ";
    			}
    		}
    	}
    	GQnaListSvc gQnaListSvc = new GQnaListSvc();
    	rcnt = gQnaListSvc.getGQnaCount(where);	// �˻��� ��ǰ�� �� ������ ��ü ���������� ���� �� ���
    	
    	ArrayList<GQnaInfo> gQnaList = gQnaListSvc.getGQnaList(cpage, psize, where); 

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
		request.setAttribute("gQnaList", gQnaList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("bbs/g_qna_list.jsp");
		dispatcher.forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
