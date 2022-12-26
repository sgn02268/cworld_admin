package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/sta_home")
public class StaHomeCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public StaHomeCtrl() { super(); }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		StaHomeSvc staHomeSvc = new StaHomeSvc();
		// ȸ�� �� getMemberCount(), �亯���� ���� getAnswerQnaCount(), getAnswerGroupQnaCount(),
		// ��� ���� getStockStatusCount(), �뿩 ���� getRentStatusCount(), �з��� �� ����� getCtgrSalesSum()
		String memCnt = staHomeSvc.getMemberCount();			
		String qnaCnt = staHomeSvc.getAnswerQnaCount();		
		String gQnaCnt = staHomeSvc.getAnswerGroupQnaCount();
		String stockCnt = staHomeSvc.getStockStatusCount();
		String rentCnt = staHomeSvc.getRentStatusCount();
		String ctgrSaleSum = staHomeSvc.getCtgrSalesSum();
		
		
		request.setAttribute("memCnt", memCnt);
		request.setAttribute("qnaCnt", qnaCnt);
		request.setAttribute("gQnaCnt", gQnaCnt);
		request.setAttribute("stockCnt", stockCnt);
		request.setAttribute("rentCnt", rentCnt);
		request.setAttribute("ctgrSaleSum", ctgrSaleSum);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("statistics/sta_home.jsp");
		dispatcher.forward(request, response);
	}

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
