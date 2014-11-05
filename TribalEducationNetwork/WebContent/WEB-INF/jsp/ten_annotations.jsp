<header><b>DESCRIPTIVE TAGS</b></header>
<table style="width:100%">
	<tr><td width='50%'>Annotator </td><td width='50%'><input type="text" name="tenLearningObjectAnnotationsBean.annotator" readonly value="${sessionScope.user_details.user_name}"/></td><td><div title="${'Entity responsible for annotating the content'}" >  ?</div></td></tr>
	<tr><td width='50%'>Title</td><td width='50%'><input type="text" name="tenLearningObjectAnnotationsBean.title" /></td><td><div title="${'The name given to the resource eg. The sound of music'}" >  ?</div></td></tr>
	<tr><td>Subject</td><td><input type="text" name="tenLearningObjectAnnotationsBean.subject" /></td><td><div title="${'The topic of the content of the resource typically keywords separated by semi-colons eg. Street, Picabo'}" >  ?</div></td></tr>
	<tr><td>Description</td><td> <textarea name="tenLearningObjectAnnotationsBean.description" rows="4"></textarea></td><td><div title="${'An account of the content of the resource eg. an abstract, table of contents, reference to a graphical representation of content or a free-text account of the content.'}" >  ?</div></td></tr>
	<tr><td>Source </td><td><input type="text" name="tenLearningObjectAnnotationsBean.source" /></td><td><div title="${'A Reference to a resource from which the present resource is derived eg. Image from page 54 of the 1922 edition of Romeo and Juliet'}" >  ?</div></td></tr>
	<tr><td>Language </td><td><input type="text" name="tenLearningObjectAnnotationsBean.language" /></td><td><div title="${'A language of the intellectual content of the resource eg. en-GB for English used in the United Kingdom'}" >  ?</div></td></tr>
	<tr><td>Relation </td><td><input type="text" name="tenLearningObjectAnnotationsBean.relation" /></td><td><div title="${'A reference to a related resource'}" >  ?</div></td></tr>
	<tr><td>Coverage </td><td><input type="text" name="tenLearningObjectAnnotationsBean.coverage" /></td><td><div title="${'The extent or scope of the content of the resource either spatial location, temporal period  or jurisdiction '}" >  ?</div></td></tr>
	<tr><td>Tribe</td><td><input type="text" name="tenLearningObjectAnnotationsBean.tribe" /></td><td><div title="${'A reference to tribes referenced in the content'}" >  ?</div></td></tr>
</table>
<br/>
<header><b>STRUCTURAL TAGS</b></header>
<table style="width:100%">
	<tr><td width='50%'>Date of annotation</td><td width='50%'><input type="text" name="tenLearningObjectAnnotationsBean.date" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${date}" />'/></td><td><div title="${'Date of annotation in YYYY-MM-DD format'}" >  ?</div></td></tr>
	<tr><td>Type</td>
		<td>
		<select name="tenLearningObjectAnnotationsBean.type">
			<option value="Image" <c:if test="${objectType == 'Image'}">selected</c:if>>Image</option>
			<option value="Text" <c:if test="${objectType == 'Text'}">selected</c:if>>Text</option>
			<option value="MovingImage" <c:if test="${objectType == 'Video'}">selected</c:if>>Video</option>
			<option value="Sound" <c:if test="${objectType == 'Audio'}">selected</c:if>>Audio</option>
		</select>
		</td><td><div title="${'The nature or genre of the content of the resource. '}" >  ?</div></td>
	</tr>
	<tr><td>Format</td><td> <input type="text" id="format" name="tenLearningObjectAnnotationsBean.format" value="${learningObjectDetailsBean.fileType}"/></td><td><div title="${'The physical or digital manifestation of the resource'}" >  ?</div></td></tr>
	<tr><td>Identifier </td><td><input type="text" name="digitalRightsManagementBean.identifier" /></td><td><div title="${'An unambiguous reference to the resource within a given context eg. the Digital Object Identifier (DOI)'}" >  ?</div></td></tr>
</table>
