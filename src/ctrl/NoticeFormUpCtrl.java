package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/notice_form_up")
public class NoticeFormUpCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public NoticeFormUpCtrl() { super(); }

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
    	
    	ListSvc listSvc = new ListSvc();
    	NoticeInfo noticeInfo = listSvc.getNoticeInfo(idx);
    	
    	if (noticeInfo != null) {	// �����Ϸ��� �Խñ��� ������
    		request.setAttribute("noticeInfo", noticeInfo);
			RequestDispatcher dispatcher = request.getRequestDispatcher("bbs/notice_form_up.jsp");
			dispatcher.forward(request, response);
    	} else {				// �����Ϸ��� �Խñ��� ������
    		response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� ���Խ��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
    	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
