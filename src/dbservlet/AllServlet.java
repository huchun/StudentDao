package dbservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Page;
import bean.StudentInfo;
import dao.StudentDao;

public class AllServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 //doPost方法
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
   		    response.setCharacterEncoding("UTF-8");
			String methodName=request.getParameter("methodName");
			int method=Integer.parseInt(methodName); //将字符串解析为数字
			
		try {  
			switch(method)
		       {
		    	case 0:
					insert(request,response);
					break;
		        case 1:
                    difpage(request,response);
			        break;    
		    	case 2:
  				    delete(request,response);
  			        break;       
		        case 3:
  				    update(request,response);
  				    break;
		        case 4:
		        	update1(request,response);
			        break;
		        case 5:
		        	dispatch(request,response);
			        break;
		       }
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
	}
	//doGet方法
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
       doPost(request,response);
    }
	
	
    //数据库连接方法
//	public Connection connect() throws ClassNotFoundException, SQLException{
//    	Connection conn=null; 
//	    Class.forName("com.mysql.jdbc.Driver");
//		String url="jdbc:mysql://localhost:3306/springside?useUnicode=true&characterEncoding=UTF-8"; 
//	    String user="root"; 
//		String password=""; 
//		conn=DriverManager.getConnection(url,user,password); 
//		return conn;
//	}
	//关闭数据库资源
//	public void close(Statement stat,Connection conn) throws SQLException{
//		if(stat!=null){
//	    	   stat.close();
//	    }
//	    if(conn!=null){
//	    	   conn.close();
//	    }
//	}
	//插入方法
	public void insert(HttpServletRequest request, HttpServletResponse response) 
	throws ClassNotFoundException, SQLException , IOException,ServletException{
    	Connection conn=null;
    	Statement stat=null;
    	
    	try {
    		conn = StudentDao.getConnection();
    		
    		String id=request.getParameter("id");
            String name=request.getParameter("name");
            String age=request.getParameter("age");
            String gender=request.getParameter("gender");
            String major=request.getParameter("major");
            
            stat=conn.createStatement();
        	stat.execute("insert into student(id,name,age,gender,major) values("+id+",'"+name+"',"+age+",'"+gender+"','"+major+"')");
		
        	request.getRequestDispatcher("InsertSuccess.jsp").forward(request, response);
    	} catch (Exception e) {
			e.printStackTrace();
		}finally{
			StudentDao.closeConnection(conn);
		}		
		//conn=connect();	
    	//close(stat,conn);
    	
    }
    //查询方法
    public ArrayList<StudentInfo> select(String id,String name) throws ClassNotFoundException, SQLException{
    	Connection conn=null;
    	Statement stat=null;
	    ResultSet rs=null;
	    conn=StudentDao.getConnection();
		stat=conn.createStatement();
    	ArrayList<StudentInfo> result=new ArrayList<StudentInfo>();
    	if(id==""&&name==""){
    	     rs=stat.executeQuery("select * from student"); 
    	}
    	if(id!=""&&name==""){
   	        rs=stat.executeQuery("select * from student where id="+id+""); 
     	}
    	if(id==""&&name!=""){
   	        rs=stat.executeQuery("select * from student where name='"+name+"'"); 
   	    }
    	if(id!=""&&name!=""){
      	    rs=stat.executeQuery("select * from student where id="+id+" and name='"+name+"'"); 
      	}
    	while(rs.next())
        {
        	StudentInfo st=new StudentInfo();
        	st.setId(rs.getInt("id"));
        	st.setName(rs.getString("name"));
        	st.setAge(rs.getInt("age"));
        	st.setGender(rs.getString("gender"));
        	st.setMajor(rs.getString("major")); 
        	result.add(st);	
        }
	    if(rs!=null){
	    	  rs.close();
	       }
	    //close(stat,conn);
	    StudentDao.closeConnection(conn);
    	return result;
    }
    //条件查询跳转
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	String id5=request.getParameter("id");
    	String name5=request.getParameter("name");  
     if(select(id5,name5).isEmpty()){
        	request.getRequestDispatcher("selectnothing.jsp").forward(request, response);
        }
       else{
    		request.setAttribute("result", select(id5,name5));
            request.getRequestDispatcher("idnameselect.jsp").forward(request, response);	
        }
    }
    //设置分页相关参数方法
	public Page setpage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		String crd=request.getParameter("currentRecord");
		//String id=request.getParameter("id");
      //  String name=request.getParameter("name");
    	ArrayList<StudentInfo> result=select("","");
    	Page pager=new Page();
    	pager.setTotalRecord(result.size()); 
    	pager.setTotalPage(result.size(),pager.getPageSize());
    	if(crd!=null)
        {
    		int currentRecord=Integer.parseInt(crd);
            pager.setCurrentRecord(currentRecord);
            pager.setCurrentPage(currentRecord,pager.getPageSize());
        }
    	return pager;
	}
	//获得分页显示的子集
	 public void difpage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException{
		// String id=request.getParameter("id");
	    // String name=request.getParameter("name");
		 ArrayList<StudentInfo> result=select("",""); //返回查询的结果集
		 Page pager=new Page();
		 pager=setpage(request,response);
  	     List<StudentInfo> subResult=null;
  	     int currentRecord=pager.getCurrentRecord();
         if(currentRecord==0){
         	if(pager.getTotalRecord()<8){
         		subResult=(List<StudentInfo>) result.subList(0,pager.getTotalRecord());
         	}
         	else{
         		subResult=(List<StudentInfo>) result.subList(0,pager.getPageSize());
         	}         
         }
         else if(pager.getCurrentRecord()+pager.getPageSize()<result.size())
         {
               subResult=(List<StudentInfo>) result.subList(pager.getCurrentRecord(),pager.getCurrentRecord()+pager.getPageSize());
         }
         else
         {
              subResult=(List<StudentInfo>) result.subList(pager.getCurrentRecord(),result.size());
         }
         request.setAttribute("pager", pager);
	     request.setAttribute("subResult", subResult);
		 request.getRequestDispatcher("layout.jsp").forward(request, response);
     }
    //信息删除方法
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	Connection conn=null;
    	Statement stat=null;
    	
    	try {
			conn = StudentDao.getConnection();
			stat=conn.createStatement();
	 		String id2=request.getParameter("id");
			stat.execute("delete from student where id="+id2+"");
			request.getRequestDispatcher("delete.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			StudentDao.closeConnection(conn);
		}
    	
    	//conn=connect();
    } 
    //信息修改方法
    public void update1(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	String id4=request.getParameter("id");
	    request.setAttribute("result", select(id4,""));
        request.getRequestDispatcher("update1.jsp").forward(request, response);
    }   
    //修改
    public void update(HttpServletRequest request, HttpServletResponse response) 
    throws ClassNotFoundException, SQLException, ServletException, IOException{
    	Connection conn=null;
    	Statement stat=null;
    	
    	try {
			conn = StudentDao.getConnection();
			String id3=request.getParameter("id");
	        String name3=request.getParameter("name");
	        String age3=request.getParameter("age");
	        String gender3=request.getParameter("gender");
	        String major3=request.getParameter("major");
	    	//conn=connect();
	 		stat=conn.createStatement();
			stat.execute("update student set id="+id3+",name='"+name3+"',age="+age3+",gender='"+gender3+"',major='"+major3+"' where id="+id3+"");
			request.setAttribute("result", select(id3,""));    
		    request.getRequestDispatcher("update.jsp").forward(request, response); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			StudentDao.closeConnection(conn);
		}
    } 
   
}


