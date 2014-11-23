<script>
	function onCopyRightHolderChange(cb){
		if (cb.checked){
			cb.value = "true";
			$("#copyRightHolderFinderInfo_div").show();
			$(".copyRightHolderData").hide();
			resetValues('copyRightHolder');
			document.getElementById("copyRightHolderApproved").value = "";
			
		}else{
			cb.value = "false";
			$("#copyRightHolderFinderInfo_div").hide();
			document.getElementById("copyRightHolderFinderInfo").value = "";
			$(".copyRightHolderData").show();
		}
	}
	
	function onStoryProvidedChange(cb){
		if (cb.checked){
			cb.value = "true";
			$(".storyClass").show();
		}else{
			cb.value = "false";
			document.getElementById("storyId").value = "";
			document.getElementById("storyContext").value = "";
			resetValues('storyProvider');
			$(".storyClass").hide();
		}
	}
	
	function resetValues(field){
		document.getElementById(field + 'Id').value = "";
		document.getElementById(field + 'Email').value = "";
		document.getElementById(field + 'CellPhone').value = "";
		document.getElementById(field + 'OfficePhone').value = "";
		document.getElementById(field + 'Fax').value = "";
		document.getElementById(field + 'StreetAddress').value = "";
		document.getElementById(field + 'OtherAddress').value = "";
		document.getElementById(field + 'City').value = "";
		document.getElementById(field + 'State').value = "";
		document.getElementById(field + 'ZipCode').value = "";
	}
	
	function copyValues(cb,from, to){
		if(cb.checked){
			document.getElementById(to + 'Id').value = document.getElementById(from + 'Id').value;
			document.getElementById(to + 'Email').value = document.getElementById(from + 'Email').value;
			document.getElementById(to + 'CellPhone').value = document.getElementById(from + 'CellPhone').value;
			document.getElementById(to + 'OfficePhone').value = document.getElementById(from + 'OfficePhone').value;
			document.getElementById(to + 'Fax').value = document.getElementById(from + 'Fax').value;
			document.getElementById(to + 'StreetAddress').value = document.getElementById(from + 'StreetAddress').value;
			document.getElementById(to + 'OtherAddress').value = document.getElementById(from + 'OtherAddress').value;
			document.getElementById(to + 'City').value = document.getElementById(from + 'City').value;
			document.getElementById(to + 'State').value = document.getElementById(from + 'State').value;
			document.getElementById(to + 'ZipCode').value = document.getElementById(from + 'ZipCode').value;
		}
	}
</script>
<table style="width:100%"> 
	<tr>
		<td style="width:40%"><b>Copyright Holder Information</b></td>
		<td><input type="checkbox" name="digitalRightsManagementBean.copyRightHolderNotAvailable" onchange="onCopyRightHolderChange(this)"/>
		 Copyright holder information not available</td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Copyright Holder Name</td>
		<td><div title="${'An entity which is the copyright holder of the resource e.g. author of the article, creator of artifact, photographer of a picture or artist of a structure'}"><input type="text" id="copyRightHolderId" name="digitalRightsManagementBean.copyRightHolderId" />  ?</div></td>
	</tr>
	<tr id="copyRightHolderFinderInfo_div" style="display:none;">
		<td>Potential copy right holder information</td><td><div title="${'Information about the potential copy right holder or any information that can help finding the copy right holder.'}" ><input type="text" name="digitalRightsManagementBean.copyRightHolderFinderInfo" id ="copyRightHolderFinderInfo" />  ?</div></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Email </td><td><input type="text" id="copyRightHolderEmail" name="digitalRightsManagementBean.copyRightHolderEmail" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Cell Phone </td><td><input type="text" id="copyRightHolderCellPhone" name="digitalRightsManagementBean.copyRightHolderCellPhone" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Office Phone </td><td><input type="text" id="copyRightHolderOfficePhone" name="digitalRightsManagementBean.copyRightHolderOfficePhone" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Fax </td><td><input type="text" id="copyRightHolderFax" name="digitalRightsManagementBean.copyRightHolderFax" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Street Address</td><td><input type="text" id="copyRightHolderStreetAddress" name="digitalRightsManagementBean.copyRightHolderStreetAddress" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Apt/Suite/Other</td><td><input type="text" id="copyRightHolderOtherAddress" name="digitalRightsManagementBean.copyRightHolderOtherAddress" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>City</td><td><input type="text" id="copyRightHolderCity" name="digitalRightsManagementBean.copyRightHolderCity" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>State</td><td><input type="text" id="copyRightHolderState" name="digitalRightsManagementBean.copyRightHolderState" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Zip Code</td><td><input type="text" id="copyRightHolderZipCode" name="digitalRightsManagementBean.copyRightHolderZipCode" /></td>
	</tr>
	<tr class="copyRightHolderData">
		<td>Copyright Holder approved for use</td>
		<td><input type="checkbox" name="digitalRightsManagementBean.copyRightHolderApproved" value="true"/></td>
	</tr>	
		
	<tr><td><br></td></tr>	
	<tr>
		<td><b>Publisher Information</b></td>
		<td>Copy values from <input type="checkbox" name="publisherSameAs" value="copyRightHolder" onclick="copyValues(this,'copyRightHolder','publisher');"/> Copy Right Holder</td>
	</tr>
	<tr>
		<td>Publisher Name</td>
		<td><div title="${'The entity holding publishing rights on the artifact e.g. publisher of book to which the given artifact belongs '}" ><input type="text" id="publisherId" name="digitalRightsManagementBean.publisher" />  ?</div></td>
	</tr>
	<tr>
		<td>Email </td><td><input type="text" id="publisherEmail" name="digitalRightsManagementBean.publisherEmail" /></td>
	</tr>
	<tr>
		<td>Cell Phone</td><td><input type="text" id="publisherCellPhone" name="digitalRightsManagementBean.publisherCellPhone" /></td>
	</tr>
	<tr>
		<td>Office Phone</td><td><input type="text" id="publisherOfficePhone" name="digitalRightsManagementBean.publisherOfficePhone" /></td>
	</tr>
	<tr>
		<td>Fax</td><td><input type="text" id="publisherFax" name="digitalRightsManagementBean.publisherFax" /></td>
	</tr>
	<tr>
		<td>Street Address</td><td><input type="text" id="publisherStreetAddress" name="digitalRightsManagementBean.publisherStreetAddress" /></td>
	</tr>
	<tr>
		<td>Apt/Suite/Other</td><td><input type="text" id="publisherOtherAddress" name="digitalRightsManagementBean.publisherOtherAddress" /></td>
	</tr>
	<tr>
		<td>City</td><td><input type="text" id="publisherCity" name="digitalRightsManagementBean.publisherCity" /></td>
	</tr>
	<tr>
		<td>State</td><td><input type="text" id="publisherState" name="digitalRightsManagementBean.publisherState" /></td>
	</tr>
	<tr>
		<td>Zip Code</td><td><input type="text" id="publisherZipCode" name="digitalRightsManagementBean.publisherZipCode" /></td>
	</tr>
	<tr>
		<td>Publisher approved</td>
		<td><input type="checkbox" name="digitalRightsManagementBean.publisherApproved" value="true"/></td>
	</tr>	

	<tr><td><br></td></tr>	
	<tr>
		<td><b>Contributor Information</b></td>
		<td>Copy values from <input type="radio" name="contributorSameAs" value="copyRightHolder" onclick="copyValues(this,'copyRightHolder','contributor');"/> Copy Right Holder
		<input type="radio" name="contributorSameAs" value="publisher" onclick="copyValues(this,'publisher','contributor');"/> Publisher</td>
	</tr>	
	<tr>
		<td>Contributor Name</td>
		<td><div title="${'An entity responsible for providing or handing over the artifact for use in TEN.'}" ><input type="text" id="contributorId" name="digitalRightsManagementBean.contributor" />  ?</div></td>
	</tr>
	<tr>
		<td>Email </td><td><input type="text" id="contributorEmail" name="digitalRightsManagementBean.contributorEmail" /></td>
	</tr>
	<tr>
		<td>Cell Phone</td><td><input type="text" id="contributorCellPhone" name="digitalRightsManagementBean.contributorCellPhone" /></td>
	</tr>
	<tr>
		<td>Office Phone</td><td><input type="text" id="contributorOfficePhone" name="digitalRightsManagementBean.contributorOfficePhone" /></td>
	</tr>
	<tr>
		<td>Fax</td><td><input type="text" id="contributorFax" name="digitalRightsManagementBean.contributorFax" /></td>
	</tr>
	<tr>
		<td>Street Address</td><td><input type="text" id="contributorStreetAddress" name="digitalRightsManagementBean.contributorStreetAddress" /></td>
	</tr>
	<tr>
		<td>Apt/Suite/Other</td><td><input type="text" id="contributorOtherAddress" name="digitalRightsManagementBean.contributorOtherAddress" /></td>
	</tr>
	<tr>
		<td>City</td><td><input type="text" id="contributorCity" name="digitalRightsManagementBean.contributorCity" /></td>
	</tr>
	<tr>
		<td>State</td><td><input type="text" id="contributorState" name="digitalRightsManagementBean.contributorState" /></td>
	</tr>
	<tr>
		<td>Zip Code</td><td><input type="text" id="contributorZipCode" name="digitalRightsManagementBean.contributorZipCode" /></td>
	</tr>
	<tr>
		<td>Tribal Affiliation</td><td><input type="text" id="contributorTribalAffiliation" name="digitalRightsManagementBean.contributorTribalAffiliation" /></td>
	</tr>
			
	<tr><td><br></td></tr>	
	<tr><td>Physical Description</td><td><div title="${'The description of the artifact e.g. the format in which the artifact was provided such as hard copy, compact disc'}" ><input type="text" id="physicalDescriptionId" name="digitalRightsManagementBean.physicalDescription" />  ?</div></td></tr>
	
	<tr><td><br></td></tr>	
	<tr><td>Loan Period End Date</td><td><div title="${'End date for the loan of artifact in yyyy-MM-dd format. Blank indicates unrestricted use of artifact.'}" ><input type="text" id="loadPeriodId" name="digitalRightsManagementBean.loanPeriod" />  ?</div></td></tr>
	
	<tr><td><br></td></tr>	
	<tr><td>Source Identifier </td><td><div title="${'The identifier of the resource to which this artifact belongs. E.g. for an article from a book, ISBN (International Standard Book Number) of book.'}" ><input type="text" id="identifierId" name="digitalRightsManagementBean.identifier" />  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Source Description </td><td><div title="${'The description of the source to which this artifact belongs e.g. for an article from book, description of book'}" ><input type="text" id="identifierDescId" name="digitalRightsManagementBean.identifierDescription" />  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Handling instructions </td><td><div title="${'Instructions on how the resource should be maintained in case of rare image or book e.g an image might be required to be in dry weather'}" ><input type="text" id="handlingInstructionsId" name="digitalRightsManagementBean.handlingInstructions" />  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Rights </td><td><div title="${'The Rights element may be used for either a textual statement or a URL pointing to a rights statement, or a combination'}" ><input type="text" id="rightsId" name="digitalRightsManagementBean.rights" />  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Intaker </td><td><div title="${'The entity responsible for uploading the content to elearning system'}" ><input type="text" id="intakerId" name="digitalRightsManagementBean.intaker" readonly value="${sessionScope.user_details.user_name}"/>  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Date of upload </td><td><div title="${'Date of creation of resource in YYYY-MM-DD format'}" ><input type="text" id="dateId" name="digitalRightsManagementBean.dateOfUpload" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${date}" />'/>  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr>
		<td>Story provided with the artifact</td><td> <input type="checkbox" name="digitalRightsManagementBean.storyProvided" value="true" onchange="onStoryProvidedChange(this)"/></td>
	</tr>
	<tr class="storyClass" style="display:none;"><td>Story </td><td><div title="${'Story provided by tribe with the artifact that will be used by annotators to tag the artifact'}"><textarea id="storyId" name="digitalRightsManagementBean.story" rows="4"></textarea>  ?</div></td>
	</tr>
	<tr class="storyClass" style="display:none;"><td>Story context </td><td><div title="${'Information about context of story provided by tribe such as the format it was provided in e.g. as a recording, on paper, audio'}"><input type="text" id="storyContext" name="digitalRightsManagementBean.storyContext" />  ?</div></td>
	</tr>
	
	<tr><td><br></td></tr>
	<tr class="storyClass"  style="display:none;">
		<td><b>Story Provider Information</b></td>
		<td>Copy values from <input type="checkbox" name="storyProviderSameAs" value="contributor" onclick="copyValues(this,'contributor','storyProvider');"/> Contributor</td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>Story Provider Name</td>
		<td><div title="${'An entity responsible for providing the story associated with the artifact'}"><input type="text" id="storyProviderId" name="digitalRightsManagementBean.storyProvider" />  ?</div></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>Email </td><td><input type="text" id="storyProviderEmail" name="digitalRightsManagementBean.storyProviderEmail" /></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>Cell Phone </td><td><input type="text" id="storyProviderCellPhone" name="digitalRightsManagementBean.storyProviderCellPhone" /></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>Office Phone </td><td><input type="text" id="storyProviderOfficePhone" name="digitalRightsManagementBean.storyProviderOfficePhone" /></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>Fax </td><td><input type="text" id="storyProviderFax" name="digitalRightsManagementBean.storyProviderFax" /></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>Street Address</td><td><input type="text" id="storyProviderStreetAddress" name="digitalRightsManagementBean.storyProviderStreetAddress" /></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>Apt/Suite/Other</td><td><input type="text" id="storyProviderOtherAddress" name="digitalRightsManagementBean.storyProviderOtherAddress" /></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>City</td><td><input type="text" id="storyProviderCity" name="digitalRightsManagementBean.storyProviderCity" /></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>State</td><td><input type="text" id="storyProviderState" name="digitalRightsManagementBean.storyProviderState" /></td>
	</tr>
	<tr class="storyClass"  style="display:none;">
		<td>Zip Code</td><td><input type="text" id="storyProviderZipCode" name="digitalRightsManagementBean.storyProviderZipCode" /></td>
	</tr>
</table>