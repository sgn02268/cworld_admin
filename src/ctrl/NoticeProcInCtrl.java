package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import vo.*;
import svc.*;

@WebServlet("/notice_proc_in")
public class NoticeProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;  
    public NoticeProcInCtrl() { super(); }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
		
		String nl_title = request.getParameter("nl_title").trim().replace("'", "''").replace("<", "&lt;");
		String nl_content = request.getParameter("nl_content").trim().replace("'", "''").replace("<", "&lt;");
		String nl_ctgr = request.getParameter("ctgr");
		String nl_isview = request.getParameter("isview");
		
		NoticeInfo noticeInfo = new NoticeInfo();	
		noticeInfo.setNl_title(nl_title);
		noticeInfo.setNl_content(nl_content);
		noticeInfo.setNl_ctgr(nl_ctgr);
		noticeInfo.setNl_isview(nl_isview);
		noticeInfo.setAi_idx(loginInfo.getAi_idx());
		
		ListSvc ListSvc = new ListSvc();
		
		
		int idx = ListSvc.noticeInsert(noticeInfo);
		// insert ������ �����ϹǷ� insert �� ���ڵ� ������ �޾ƿ��� ���� �Ϲ����̳�, ó�� �� �ش� �� ����ȭ������ ���� �ϱ� ������ �ش� ���� �۹�ȣ�� �޾ƿ�
		
		if (idx > 0) {	// ���������� ���� ��ϵǾ�����
			response.sendRedirect("notice_view?cpage=1&idx=" + idx);
		} else {			// �� ��Ͻ� ������ �߻� ������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�� ��Ͽ� �����߽��ϴ�. �ٽ� �õ��غ�����.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
	}

}
