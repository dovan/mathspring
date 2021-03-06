<%--
  Created by IntelliJ IDEA.
  User: david
  Date: Sep 22, 2011
  Time: 4:34:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<jsp:include page="wayangTempHead.jsp" />
<div class="mainPageMargin">
<p>Student List</p>
</div>


<%--<jsp:useBean id="bean" scope="request" type="edu.umass.ckc.wo.beans.Classes"/>--%>
<jsp:useBean id="classInfo" scope="request" type="edu.umass.ckc.wo.beans.ClassInfo"/>


<%--jsp:include page="teacherToolsHeader.jsp" /--%>
<jsp:include page="classInfoHeader.jsp" />

<div class="mainPageMargin">
    <div id="Layer2" align="center">

    <%--@elvariable id="classInfo" type="edu.umass.ckc.wo.beans.ClassInfo"--%>

<p class="whArial">
        <b>Generate Users for this Class</b><br><br>

        You can generate for this class if the class has an empty roster (student list below should be empty).<br>
        <b>NOTE WELL: Make sure that all pedagogies have been selected for this class prior to generating the students.</b>
    <form name="form1" id="rosterForm" method="post"
          action="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminAlterClassCreateStudents&teacherId=<c:out value="${classInfo.teachid}"/>&classId=<c:out value="${classInfo.classid}"/>">

        <table width="600">
            <tr> <td width="50"><span class="whArial">Prefix</span></td>
                <td> <input name="prefix" type="text"> </td></tr>
            <tr> <td width="50"><span class="whArial">Password</span></td>
                <td> <input name="password" type="text"> </td></tr>
            <tr> <td width="50"><span class="whArial">Begin Number</span></td>
                <td> <input name="beginNumber" type="text"> </td></tr>
            <tr> <td width="50"><span class="whArial">End Number</span></td>
                <td> <input name="endNumber" type="text"> </td></tr>
            <tr> <td width="50"><span class="whArial">Test User Prefix</span></td>
                <td> <input name="testUserPrefix" type="text"> </td></tr>

        </table>


      <input type="submit" name="submit" value="Create Students" />
      <input type="hidden" name="teacherId"  value="<c:out value="${classInfo.teachid}"/>"/>
      <input type="hidden" name="classId"  value="<c:out value="${classInfo.classid}"/>"/>
    </form>
 <br>

    <p class="whArial">
    <c:out value="${message}"/>
    </p>

 <b>Student List</b>
        
 <table width="600">
      <tr>
      <td width="50"><span class="whArial">Student ID</span></td>
      <td width="180"><span class="whArial">First Name</span></td>
      <td width="180"><span class="whArial">Last Name</span></td>
      <td width="60"><span class="whArial">User Name</span></td>
	  <td width="60"><span class="whArial"><a title="Removes all trace of student work in the system">Clear All Data</a></span></td>
	  <td width="60"><span class="whArial"><a title="Removes all traces of the student ever being in the tutor hut">Clear Practice Hut Data</a></span></td>
	  <td width="60"><span class="whArial"><a title="Allows re-entry to tutor hut while preserving past tutoring events">Reset Practice Hut</a></span></td>
	  <td width="60"><span class="whArial"><a title="Remove Pretest data so student can retake.">Clear Pretest Data</a></span></td>
	  <td width="60"><span class="whArial"><a title="Remove Posttest data so student can retake.">Clear Posttest Data</a></span></td>
	  <td width="60"><span class="whArial"><a title="Remove the student and all trace of his work in the system">Delete Student</a></span></td>
	  <td width="60"><span class="whArial"><a title="Edit this students properties">Edit Student</a></span></td>
      </tr>
        <%--@elvariable id="students" type="java.util.List"--%>
        <%--@elvariable id="u" type="edu.umass.ckc.wo.smgr.User"--%>
        <c:forEach var="u" items="${students}">
            <tr>

                <td><span class="whArial"><c:out value="${u.id}"/></span></td>
                <td><span class="whArial"><c:out value="${u.fname}"/></span></td>
            <td><span class="whArial"><c:out value="${u.lname}"/></span></td>
            <td><span class="whArial"><c:out value="${u.uname}"/></span></td>

            <%--<td><a href="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminAlterClassEdit&classId=<c:out value="${cl.classid}"/>&teacherId=<c:out value="${cl.teachid}"/>"><img  src='<c:out value="${pageContext.request.contextPath}"/>/images/edit.gif' alt="Edit Class"></a></td>--%>
            <td><a onclick="return confirm('Are you sure you want to clear ALL data for <c:out value="${u.uname}"/>?');" href="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminClearStudentAllData&teacherId=<c:out value="${classInfo.teachid}"/>&studId=<c:out value="${u.id}"/>&classId=<c:out value="${classInfo.classid}"/>"><img  src='<c:out value="${pageContext.request.contextPath}"/>/images/del.gif' alt="Delete Student"></a></td>
            <td><a onclick="return confirm('Are you sure you want to clear PRACTICE HUT data for <c:out value="${u.uname}"/>?');" href="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminClearStudentPracticeData&teacherId=<c:out value="${classInfo.teachid}"/>&studId=<c:out value="${u.id}"/>&classId=<c:out value="${classInfo.classid}"/>"><img  src='<c:out value="${pageContext.request.contextPath}"/>/images/del.gif' alt="Delete Student"></a></td>
            <td><a onclick="return confirm('Are you sure you want to reset PRACTICE HUT data for <c:out value="${u.uname}"/>?');" href="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminResetStudentPracticeData&teacherId=<c:out value="${classInfo.teachid}"/>&studId=<c:out value="${u.id}"/>&classId=<c:out value="${classInfo.classid}"/>"><img  src='<c:out value="${pageContext.request.contextPath}"/>/images/del.gif' alt="Delete Student"></a></td>
            <td><a onclick="return confirm('Are you sure you want to clear PRETEST data for <c:out value="${u.uname}"/>?');" href="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminClearStudentPretestData&teacherId=<c:out value="${classInfo.teachid}"/>&studId=<c:out value="${u.id}"/>&classId=<c:out value="${classInfo.classid}"/>"><img  src='<c:out value="${pageContext.request.contextPath}"/>/images/del.gif' alt="Delete Student"></a></td>
            <td><a onclick="return confirm('Are you sure you want to clear POSTTEST data for <c:out value="${u.uname}"/>?');" href="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminClearStudentPosttestData&teacherId=<c:out value="${classInfo.teachid}"/>&studId=<c:out value="${u.id}"/>&classId=<c:out value="${classInfo.classid}"/>"><img  src='<c:out value="${pageContext.request.contextPath}"/>/images/del.gif' alt="Delete Student"></a></td>
            <td><a onclick="return confirm('Are you sure you want to DELETE <c:out value="${u.uname}"/>?');" href="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminDeleteStudentFromRoster&teacherId=<c:out value="${classInfo.teachid}"/>&studId=<c:out value="${u.id}"/>&classId=<c:out value="${classInfo.classid}"/>"><img  src='<c:out value="${pageContext.request.contextPath}"/>/images/del.gif' alt="Delete Student"></a></td>
            <td><a href="<c:out value="${pageContext.request.contextPath}"/>/WoAdmin?action=AdminEditStudent&teacherId=<c:out value="${classInfo.teachid}"/>&studId=<c:out value="${u.id}"/>&classId=<c:out value="${classInfo.classid}"/>"><img  src='<c:out value="${pageContext.request.contextPath}"/>/images/edit.gif' alt="Edit Student"></a></td>
            </tr>
        </c:forEach>
    </table>

    <p>


    </div>
</div>
<jsp:include page="wayangTempTail.jsp" />

