package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import vo.*;
import svc.*;
import java.util.*;

@WebServlet("/rent_list")
public class RentListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RentListCtrl() { super(); }

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
    	String pcb = "";
    	String where = "";
    	if (sch != null && !sch.equals("")) {
    	// �˻����� ���� :  &sch=bR,sR01,p��Ʈ,itest1,r2022-10-04:2022-10-06,ta
    		String[] arrSch = sch.split(",");
    		for (int i = 0; i < arrSch.length; i++) {
    			char c = arrSch[i].charAt(0);
    			if (c == 'b') {
    				where += " and a.pi_id like '%" + arrSch[i].substring(1) + "%' ";
    				pcb = arrSch[i].substring(1);
    			} else if (c == 's') {
    				where += " and a.pi_id like '%" + arrSch[i].substring(1) + "%' "; 
    			} else if (c == 'p') {
    				where += " and a.ord_pname like '%" + arrSch[i].substring(1) + "%' "; 
    			} else if (c == 'i') {
    				where += " and b.mi_id like '%" + arrSch[i].substring(1) + "%' ";
    			} else if (c == 'r') {
    				String sd = arrSch[i].substring(1, arrSch[i].indexOf(':'));
					if (sd != null && !sd.equals("")) {
						where += " and datediff(left(a.ord_edate, 10), '" + sd + "') >= 0 ";
					}
					String ed = arrSch[i].substring(arrSch[i].indexOf(':') + 1);
					if (ed != null && !ed.equals("")) {
						where += " and datediff(left(a.ord_edate, 10), '" + ed + "') <= 0 ";
					}
    			} else if (c == 't') {
    				where += " and a.ord_isreturn = '" + arrSch[i].substring(1) + "' "; 
    			} 
    		}
    	}
    	ProductListSvc productListSvc = new ProductListSvc();
    	rcnt = productListSvc.getRentCount(where);	// �˻��� ��ǰ�� �� ������ ��ü ���������� ���� �� ���
    	
    	ArrayList<RentInfo> rentList = productListSvc.getRentList(cpage, psize, where); 
    	// �˻��� ��ǰ�� �� ���� ���������� ������ ��ǰ ����� �޾ƿ�
    	ArrayList<PdtCtgrSmall> smallList = new ArrayList<PdtCtgrSmall>();
    	if (pcb != null && !pcb.equals("")) {	// �˻� ������ ��з��� ������
    		smallList = productListSvc.getSmallList(pcb);
    	}	// ��з��� ���ϴ� �Һз� ����� �޾ƿ�
    	
    	// ��ǰ �˻��� �������� ����ϱ� ���� �귣�� ����� �޾ƿ�
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
		request.setAttribute("rentList", rentList);
		request.setAttribute("smallList", smallList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/rent_list.jsp");
		dispatcher.forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
