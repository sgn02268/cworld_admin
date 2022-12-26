package ctrl;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import svc.*;
import vo.*;

@WebServlet("/qna_list")
public class QnaListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public QnaListCtrl() { super(); } 
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 5, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
    	// ���� ������ ��ȣ, ������ ũ��, ��� ũ��, ���ڵ�(��ǰ) ����, ������ ����, ���� ������ ��ȣ ���� ������ ����
    	if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}
    	String sch = request.getParameter("sch");	// �˻�����(Ư����ǰ���� & ��ǰ��) Ư����ǰ ���δ� ����, ��ü, best, new, sale, hot

    	if (sch == null) {
    		sch = "";
    	}
    	String where = "";
    	if (sch != null && !sch.equals("")) {
    	// �˻����� ���� :  &sch=itest,ca,ay,r2022-10-04:2022-10-06	�ۼ���, �з�, �亯����, �Ⱓ
    		String[] arrSch = sch.split(",");
    		for (int i = 0; i < arrSch.length; i++) {
    			char c = arrSch[i].charAt(0);
    			if (c == 'i') {
    				where += " and mi_id like '%" + arrSch[i].substring(1) + "%' ";
    			} else if (c == 'c') {
    				where += " and ql_ctgr = '" + arrSch[i].substring(1) + "' ";
    			} else if (c == 'a') {
    				where += " and ql_isanswer = '" + arrSch[i].substring(1) + "' ";
    			} else  if (c == 'r') {
    				String sd = arrSch[i].substring(1, arrSch[i].indexOf(':'));
					if (sd != null && !sd.equals("")) {
						where += " and datediff(left(ql_qdate, 10), '" + sd + "') >= 0 ";
					}
					String ed = arrSch[i].substring(arrSch[i].indexOf(':') + 1);
					if (ed != null && !ed.equals("")) {
						where += " and datediff(left(ql_qdate, 10), '" + ed + "') <= 0 ";
					}
    			} else if (c == 'v') {
    				where += " and ql_isview = '" + arrSch[i].substring(1) + "' ";
    			}
    		}
    	}
    	QnaListSvc qnaListSvc = new QnaListSvc();
    	rcnt = qnaListSvc.getQnaCount(where);	// �˻��� ��ǰ�� �� ������ ��ü ���������� ���� �� ���
    	
    	ArrayList<QnaInfo> qnaList = qnaListSvc.getQnaList(cpage, psize, where); 
    	
    	
    	
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
		request.setAttribute("qnaList", qnaList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("bbs/qna_list.jsp");
		dispatcher.forward(request, response);
		
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
