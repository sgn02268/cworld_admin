package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/member_order_list")
public class MemberOrderListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MemberOrderListCtrl() { super(); }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
    	// ���� ������ ��ȣ, ������ ũ��, ��� ũ��, ���ڵ�(��ǰ) ����, ������ ����, ���� ������ ��ȣ ���� ������ ����
    	if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}
    	String sch = request.getParameter("sch");	// 

    	if (sch == null) {
    		sch = "";
    	}
    	String pcb = "";
    	String where = "";
    	if (sch != null && !sch.equals("")) {
    	// �˻����� ���� : sch=n123,bR,sR02,p123,ma,ta	// ���̵�u, ��з�(��Ű��, �뿩, �Ǹ�)b, �Һз�s, ��ǰ��p, �������m, ��ۻ���t
    		String[] arrSch = sch.split(",");
    		for (int i = 0; i < arrSch.length; i++) {
    			char c = arrSch[i].charAt(0);
    			if (c == 'b') {
    				where += " and pi_id like '%" + arrSch[i].substring(1) + "%' ";
    				pcb = arrSch[i].substring(1);
    			} else if (c == 's') {
    				where += " and pi_id like '%" + arrSch[i].substring(1) + "%' "; 
    			} else if (c == 'p') {
    				where += " and od_pname like '%" + arrSch[i].substring(1) + "%' "; 
    			} else if (c == 'n') {
    				where += " and mi_id like '%" + arrSch[i].substring(1) + "%' "; 
    			} else if (c == 'm') {
    				where += " and oi_payment = '" + arrSch[i].substring(1) + "' "; 
    			} else if (c == 't') {
    				where += " and oi_status = '" + arrSch[i].substring(1) + "' "; 
    			}
    		}
    	}
    	MemberOrderListSvc memberOrderListSvc = new MemberOrderListSvc();
    	rcnt = memberOrderListSvc.getOrderCount(where);	
    	
    	ArrayList<MemberOrderInfo> memberOrderList = memberOrderListSvc.getOrderList(cpage, psize, where); 
    	
    	ArrayList<PdtCtgrSmall> smallList = new ArrayList<PdtCtgrSmall>();
    	ProductListSvc productListSvc = new ProductListSvc();
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
		request.setAttribute("memberOrderList", memberOrderList);
		request.setAttribute("smallList", smallList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("member/member_order_list.jsp");
		dispatcher.forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
