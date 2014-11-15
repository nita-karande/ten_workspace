<script>
	function onCopyRightHolderChange(cb){
		if (cb.checked){
			cb.value = "true";
			document.getElementById("copyRightHolderId").value = "";
			$("#copyRightHolderFinderInfo_div").show();
			$(".copyRightHolderData").hide();
		}else{
			cb.value = "false";
			$("#copyRightHolderFinderInfo_div").hide();
			$("#copyRightHolderFinderInfo").value = "";
			$(".copyRightHolderData").show();
		}
	}
</script>
<table style="width:100%"> 
	<tr>
		<td><b>Copyright Holder</b></td>
		<td><input type="text" id="copyRightHolderId" name="digitalRightsManagementBean.copyRightHolderId" /></td>
		<td><div title="${'An entity which is the owner of the resource'}" >  ?</div></td>
		<td><input type="checkbox" name="digitalRightsManagementBean.copyRightHolderNotAvailable" onchange="onCopyRightHolderChange(this)"/></td>
		<td>Copyright holder information not available</td>
	</tr>
	<tr id="copyRightHolderFinderInfo_div" style="display:none;">
		<td>Helper information</td><td><input type="text" name="digitalRightsManagementBean.copyRightHolderFinderInfo" id ="copyRightHolderFinderInfo" /></td>
		<td><div title="${'Information that can help in finding the copyright holder'}" >  ?</div></td>
	</tr>
	<tr class="copyRightHolderData">
		<td></td>
		<td></td>
		<td></td>
		<td><input type="checkbox" name="digitalRightsManagementBean.copyRightHolderApproved" value="true"/></td>
		<td>Copyright Holder approval received</td>
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
	
	<tr><td><br></td></tr>	
	<tr>
		<td><b>Creator</b></td><td><input type="text" id="creatorId" name="digitalRightsManagementBean.creator" /></td><td><div title="${'An entity primarily responsible for creating the content of the resource'}" >  ?</div></td>
		<td><input type="checkbox" name="digitalRightsManagementBean.creatorApproved" value="true"/></td>
		<td>Creator approval received</td>
	</tr>
	<tr>
		<td>Cell Phone</td><td><input type="text" id="creatorCellPhone" name="digitalRightsManagementBean.creatorCellPhone" /></td>
	</tr>
	<tr>
		<td>Office Phone</td><td><input type="text" id="creatorOfficePhone" name="digitalRightsManagementBean.creatorOfficePhone" /></td>
	</tr>
	<tr>
		<td>Fax</td><td><input type="text" id="creatorFax" name="digitalRightsManagementBean.creatorFax" /></td>
	</tr>
	<tr>
		<td>Street Address</td><td><input type="text" id="creatorStreetAddress" name="digitalRightsManagementBean.creatorStreetAddress" /></td>
	</tr>
	<tr>
		<td>Apt/Suite/Other</td><td><input type="text" id="creatorOtherAddress" name="digitalRightsManagementBean.creatorOtherAddress" /></td>
	</tr>
	<tr>
		<td>City</td><td><input type="text" id="creatorCity" name="digitalRightsManagementBean.creatorCity" /></td>
	</tr>
	<tr>
		<td>State</td><td><input type="text" id="creatorState" name="digitalRightsManagementBean.creatorState" /></td>
	</tr>
	<tr>
		<td>Zip Code</td><td><input type="text" id="creatorZipCode" name="digitalRightsManagementBean.creatorZipCode" /></td>
	</tr>	

	
	<tr><td><br></td></tr>	
	<tr>
		<td><b>Publisher</b></td>
		<td><input type="text" id="publisherId" name="digitalRightsManagementBean.publisher" /></td><td><div title="${'The entity holding publishing rights and responsible for making the resource available'}" >  ?</div></td>
		<td><input type="checkbox" name="digitalRightsManagementBean.publisherApproved" value="true"/></td>
		<td>Publisher approval received</td>
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
	
		
	<tr><td><br></td></tr>		
	<tr>
		<td><b>Contributor</b></td>
		<td><input type="text" id="contributorId" name="digitalRightsManagementBean.contributor" /></td><td><div title="${'An entity responsible for providing the resource'}" >  ?</div></td>
		<td><input type="checkbox" name="digitalRightsManagementBean.contributorApproved" value="true"/></td>
		<td>Contributor approval received</td>
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
	
	<tr><td><br></td></tr>	
	<tr><td>Physical Description</td><td><input type="text" id="physicalDescriptionId" name="digitalRightsManagementBean.physicalDescription" /></td><td><div title="${'The description of the artifact'}" >  ?</div></td></tr>
	
	<tr><td><br></td></tr>	
	<tr><td>Loan Period </td><td><input type="text" id="loadPeriodId" name="digitalRightsManagementBean.loanPeriod" /></td><td><div title="${'The intended lenght of time for loan'}" >  ?</div></td></tr>
	
	<tr><td><br></td></tr>	
	<tr><td>Identifier </td><td><input type="text" id="identifierId" name="digitalRightsManagementBean.identifier" /></td><td><div title="${'The identifier of the resource to which this artifact belongs'}" >  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Identifier Description </td><td><input type="text" id="identifierDescId" name="digitalRightsManagementBean.identifierDescription" /></td><td><div title="${'The identifier description'}" >  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Handling instructions </td><td><input type="text" id="handlingInstructionsId" name="digitalRightsManagementBean.handlingInstructions" /></td><td><div title="${'Instructions on how the resource should be maintained e.g an image might be required to be in dry weather'}" >  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Rights </td><td><input type="text" id="rightsId" name="digitalRightsManagementBean.rights" /></td><td><div title="${'The Rights element may be used for either a textual statement or a URL pointing to a rights statement, or a combination'}" >  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Intaker </td><td><input type="text" id="intakerId" name="digitalRightsManagementBean.intaker" readonly value="${sessionScope.user_details.user_name}"/></td><td><div title="${'The entity responsible for uploading the content to elearning system'}" >  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr><td>Date of upload </td><td><input type="text" id="dateId" name="digitalRightsManagementBean.dateOfUpload" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${date}" />'/></td><td><div title="${'Date of creation of resource in YYYY-MM-DD format'}" >  ?</div></td></tr>
	
	<tr><td><br></td></tr>
	<tr>
		<td colspan="5"><input type="checkbox" name="digitalRightsManagementBean.storyProvided" value="true"/>
		 Story provided with the artifact </td>
	</tr>
</table>