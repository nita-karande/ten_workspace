<header><b>ADMINSTRATIVE TAGS</b></header>
<table style="width:100%">
	<tr><td width='40%'>Creator</td><td width='50%'><input type="text" id="creatorId" name="digitalRightsManagementBean.creator" /></td><td><div title="${'An entity primarily responsible for making the content of the resource'}" >  ?</div></td></tr>
	<tr><td>Publisher</td><td><input type="text" id="publisherId" name="digitalRightsManagementBean.publisher" /></td><td><div title="${'The entity responsible for making the resource available'}" >  ?</div></td></tr>
	<tr><td>Contributor</td><td> <input type="text" id="contributorId" name="digitalRightsManagementBean.contributor" /></td><td><div title="${'An entity primarily responsible for making the content of the resource'}" >  ?</div></td></tr>
	<tr><td>Rights </td><td><input type="text" id="rightsId" name="digitalRightsManagementBean.rights" /></td><td><div title="${'The Rights element may be used for either a textual statement or a URL pointing to a rights statement, or a combination'}" >  ?</div></td></tr>
	<tr><td>Intaker </td><td><input type="text" id="intakerId" name="digitalRightsManagementBean.intaker" readonly value="${sessionScope.user_details.user_name}"/></td><td><div title="${'The entity responsible for uploading the content to elearning system'}" >  ?</div></td></tr>
	<tr><td>Date </td><td><input type="text" id="dateId" name="digitalRightsManagementBean.date" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${date}" />'/></td><td><div title="${'Date of creation of resource in YYYY-MM-DD format'}" >  ?</div></td></tr>
</table>