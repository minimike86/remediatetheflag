/*
 *  
 * REMEDIATE THE FLAG
 * Copyright 2018 - Andrea Scaduto 
 * remediatetheflag@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
Number.isInteger = Number.isInteger || function(value) {
	return typeof value === 'number' && 
	isFinite(value) && 
	Math.floor(value) === value;
};
Array.prototype.remove = function(from, to) {
	var rest = this.slice((to || from) + 1 || this.length);
	this.length = from < 0 ? this.length + from : from;
	return this.push.apply(this, rest);
};
String.prototype.trim = function () {
	return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
};
String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};
Object.size = function(obj) {
	var size = 0, key;
	for (key in obj) {
		if (obj.hasOwnProperty(key)) size++;
	}
	return size;
};

function getSum(total, num) {
	return total + num;
}
function toTitleCase(str)
{
	return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
function htmlEncode(value){
	return $('<div/>').text(value).html();
}    
function deepCopy (arr) {
	var out = [];
	for (var i = 0, len = arr.length; i < len; i++) {
		var item = arr[i];
		var obj = {};
		for (var k in item) {
			obj[k] = item[k];
		}
		out.push(obj);
	}
	return out;
}
function cloneObj(obj){
	return JSON.parse(JSON.stringify(obj))
}
function replaceArrayContent(obj1, obj2){
	obj1.remove(0,(obj1.length-1));
	for(var i in obj2){
		obj1.push(obj2[i]);
	}
}
function replaceObjectContent(obj1, obj2){
	for (var key in obj1){
		if (obj1.hasOwnProperty(key)){
			delete obj1[key];
		}
	}
	for(var i in obj2){
		obj1[i] = obj2[i]
	}
}
jQuery.extend({
	deepclone: function(objThing) {

		if ( jQuery.isArray(objThing) ) {
			return jQuery.makeArray( jQuery.deepclone($(objThing)) );
		}
		return jQuery.extend(true, {}, objThing);
	},
});
function splitValue(value, index) {
	return (value.substring(0, index) + "," + value.substring(index)).split(',');
}
_st = function(fRef, mDelay) {
	if(typeof fRef == "function") {
		var argu = Array.prototype.slice.call(arguments,2);
		var f = (function(){ fRef.apply(null, argu); });
		return setTimeout(f, mDelay);
	}
	try{
		return window.setTimeout(fRef, mDelay);
	}
	catch(err){

	}
}
PNotify.prototype.options.stack.firstpos1 = 80; // or whatever pixel value you want.

function responsiveView() {
	var win = $(window).height();

	var n = parseInt($('.navbar').css('height').replace('px',''))

	var s1 = parseInt($('.serviceContainer').css('padding-top').replace('px',''))
	var s2 = parseInt($('.serviceContainer').css('padding-bottom').replace('px',''))
	var s3 = parseInt($('.serviceContainer').css('margin-top').replace('px',''))
	var s4 = parseInt($('.serviceContainer').css('margin-bottom').replace('px',''))
	var sH = s1 + s2 + s3 + s4;

	var sa1 = parseInt($('.standalone').css('height').replace('px','')) 

	var f = parseInt($('#footerwrap').css('height').replace('px',''))
	var smb = parseInt($('.standalone').css('margin-bottom').replace('px',''))
	var h = parseInt($('#headerwrap').css('height').replace('px',''))

	var calc1 = (n + f + smb);
	var calc2 = (n + h + f +sH);

	$('.standalone').css('min-height',(win - calc1 + 10)+"px");
	$('.serviceContainer').css('height',(win - calc2 + 10)+"px");
}

$(document).ready(function(){
	responsiveView();
	$(window).on('load', responsiveView);
	$(window).on('resize', responsiveView); 

	$('#targetDiv').on('click','.snippetScroll', function(e){
		e.preventDefault();
		var offset = $('#'+$(this).attr('path')).offset();
		$('html, body').animate({
			scrollTop: offset.top - 50,
			scrollLeft: offset.left
		});
	})
	$('#completedTargetDiv').on('click','.snippetScroll', function(e){
		e.preventDefault();
		var offset = $('#'+$(this).attr('path')).offset();
		$('html, body').animate({
			scrollTop: offset.top - 50,
			scrollLeft: offset.left
		});
	})
});
var rtf = angular.module('rtfNg',['nya.bootstrap.select','jlareau.pnotify','ngRoute','ui.toggle','angular-table','chart.js','angular-notification-icons','angularSpinner','720kb.tooltips','ng-file-model','ngDragToReorder','moment-picker']);


//register the interceptor as a service
var compareTo = function() {
	return {
		require: "ngModel",
		scope: {
			otherModelValue: "=compareTo"
		},
		link: function(scope, element, attributes, ngModel) {

			ngModel.$validators.compareTo = function(modelValue) {
				return modelValue == scope.otherModelValue;
			};

			scope.$watch("otherModelValue", function() {
				ngModel.$validate();
			});
		}
	};

};
rtf.filter('capitalize', function() {
	return function(input) {
		return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
	}
});
rtf.directive("compareTo", compareTo);

rtf.directive('complexPassword', function() {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ctrl) {
			ctrl.$parsers.unshift(function(password) {
				var hasUpperCase = /[A-Z]/.test(password);
				var hasLowerCase = /[a-z]/.test(password);
				var hasNumbers = /\d/.test(password);
				var hasNonalphas = /\W/.test(password);
				var characterGroupCount = hasUpperCase + hasLowerCase + hasNumbers + hasNonalphas;

				if ((password.length >= 8) && (characterGroupCount >= 3)) {
					ctrl.$setValidity('complexity', true);
					return password;
				}
				else {
					ctrl.$setValidity('complexity', false);
					return undefined;
				}

			});
		}
	}
});
rtf.service('server',function($http,$timeout,$rootScope,notificationService,$interval){ 

	var $this = this; 
	this.countries = [];

	this.supportedAwsRegions = [];
	this.definedGateways = [];
	this.definedGatewaysRegions = [];


	this.updateScore = function(obj){
		obj.action = 'updateExerciseResult';

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: obj,	
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('scoringUpdated:updated',response.data);
			notificationService.success('Score updated.');
			$('.waitLoader').hide();
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.addScoringComment = function(obj){

		obj.action = 'addResultComment';

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: obj,	
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('scoringCommentAdded:updated',response.data);
			notificationService.success('Comment sent.');
			$('.waitLoader').hide();
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.addChallenge = function(obj){
		obj.action = 'addChallenge';

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Challenge added.');
				$rootScope.$broadcast('addChallenge:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getUsersInOrg = function(organization){

		var obj = {};
		obj.action = 'getUsersInOrg';
		obj.orgId = organization.id;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('usersInOrg:updated',response.data);

		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}


	this.addExercise = function(obj){

		obj.action = 'addExercise';

		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Exercise added.');
				$rootScope.$broadcast('exerciseAdded:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	};
	this.updateChallenge = function(obj){

		obj.action = 'updateChallenge';

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Challenge updated.');
				$rootScope.$broadcast('challengeUpdated:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.updateExercise = function(obj){

		obj.action = 'updateExercise';

		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.id){
				PNotify.removeAll();
				notificationService.success('Exercise updated.');
				$rootScope.$broadcast('exerciseUpdated:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.updateOrganization = function(obj){

		obj.action = 'updateOrganization';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Organization updated.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
			$rootScope.$broadcast('organizationUpdated:updated',response.data);

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}


	this.addOrganization = function(obj){

		obj.action = 'addOrganization';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				notificationService.success('Organization added. We recommend to logout and login again to manage the newly created organization.');
				PNotify.removeAll();
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
			$rootScope.$broadcast('organizationAdded:updated',response.data);

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.getInvitationCodes = function(id){

		var obj = {};
		obj.orgId = id;
		obj.action = 'getInvitationCodes';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('invitationCodes:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.generateInvitationCode = function(obj){

		obj.action = 'generateInvitation';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Invitation Code generated.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Generation failed, please try again.');
			}
			$rootScope.$broadcast('invitationGenerated:updated',response.data);

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.removeInvitationCode = function(obj){

		obj.action = 'removeInvitation';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Invitation code removed.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Removal failed, please try again.');
			}
			$rootScope.$broadcast('invitationRemoved:updated',response.data);

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}


	this.deleteUserAccount = function(usr){
		var msg = {};
		msg.action = 'removeUser';
		msg.username = usr;
		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('userDeleted:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.removeUser = function(){
		var msg = {};
		msg.action = 'removeUser';
		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('removeUser:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.enableExerciseInRegion = function(exerciseId, taskId){
		msg = {};
		msg.exerciseId = exerciseId;
		msg.taskDefId = taskId;
		msg.action = "enableExerciseInRegion";

		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('exerciseInRegionEnabled:updated',response.data);
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Task definition enabled.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Update failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});	


	}
	this.disableExerciseInRegion = function(exerciseId, taskId){
		msg = {};
		msg.exerciseId = exerciseId;
		msg.taskDefId = taskId;
		msg.action = "disableExerciseInRegion";
		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('exerciseInRegionDisabled:updated',response.data);
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Task definition disabled.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Update failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});	


	}
	this.removeExerciseInRegion = function(exerciseId, taskId){
		msg = {};
		msg.exerciseId = exerciseId;
		msg.taskDefId = taskId;
		msg.action = "removeExerciseInRegion";
		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('exerciseInRegionRemoved:updated',response.data);
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Task definition removed.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Update failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});	
	}

	this.addTaskDefinition = function(msg){
		msg.action = "addTaskDefinition";
		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('taskDefinitionAdded:updated',response.data);
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Task definition added.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Update failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});	


	}

	this.deleteGateway = function(id){
		var msg = {};
		msg.id = id;
		msg.action = 'deleteGateway';

		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('gatewayDeleted:updated',response.data);
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Gateway deleted.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Gateways can be removed only if there is another Gateway defined for the same region, or if there are no exercises associated in that region.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});	
	}

	this.getOrganizations = function(){

		var msg = {};
		msg.action = 'getOrganizations';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('organizations:updated',response.data);

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.updateGateway = function(obj){

		obj.action = 'updateGateway';
		delete obj.region;
		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Gateway updated.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
			$rootScope.$broadcast('gatewayAdded:updated',response.data);

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.addGateway = function(obj){

		obj.action = 'addGateway';

		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: obj,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Gateway added.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
			$rootScope.$broadcast('gatewayAdded:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.getAWSRegions = function(){

		var msg = {};
		msg.action = 'getAWSRegions';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			replaceObjectContent($this.supportedAwsRegions,response.data);

			$rootScope.$broadcast('awsRegions:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.getGateways = function(){

		var msg = {};
		msg.action = 'getGateways';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			replaceObjectContent($this.definedGateways,response.data);
			var tmpGatewaysRegions = [];
			for(var i=0;i<response.data.length;i++){
				tmpGatewaysRegions.push(response.data[i].region);
			}
			replaceArrayContent($this.definedGatewaysRegions,tmpGatewaysRegions);
			$rootScope.$broadcast('gateways:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.getCountries = function(){

		var msg = {};
		msg.action = 'getCountries';

		var req = {
				method: 'POST',
				url: '/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			replaceArrayContent($this.countries,response.data)
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.user = {}

	this.getUserProfileInfo = function(){
		var msg = {};
		msg.action = 'getUserInfo';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('userProfile:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.getChallengeDetails = function(id){
		var msg = {};
		msg.action = 'getChallengeDetails';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg
		}
		$http(req).then(function successCallback(response) {
			if(response.data.length==0)
				$rootScope.$broadcast('challengeDetails:updated',null);
			else
				$rootScope.$broadcast('challengeDetails:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.getChallenges = function(){

		var msg = {};
		msg.action = 'getChallenges';

		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('challenges:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}    

	this.getUserProfile = function(){

		var msg = {};
		msg.action = 'getUserInfo';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			replaceObjectContent($this.user,response.data);
			$rootScope.$broadcast('userProfile:updated',response.data);
			$('.waitLoader').hide();
			$this.getTeams();
			$this.getUnreadNotifications();
			$this.getChallenges();

			if(response.data.r!= 3){
				$this.getUsers();
				$this.getGlobalStats([]);
			}
			if(response.data.r <= 3){
				$this.getPendingReviews();
				$this.getCompletedReviews();
				$this.getAvailableExercises();
				$this.getRunningExercises();
			}
			if(response.data.r <= 0){
				$this.getOrganizations();
				$this.getGateways();
				$this.getAWSRegions();
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.updateUserProfile = function(updatedUser){
		var msg = {};
		msg.action = 'setUserInfo';
		msg.firstName = updatedUser.firstName;
		msg.lastName = updatedUser.lastName;
		msg.email = updatedUser.email;
		msg.country = updatedUser.country.short;

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('User profile updated.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.removeChallenge = function(id){
		var msg = {};
		msg.action = "removeChallenge";
		msg.id = id;
		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again. You can remove a challenge if no exercises were run. For further help, please contact support.');
			}
			else{
				PNotify.removeAll();
				notificationService.success('Challenge removed.');
				$rootScope.$broadcast('challengeRemoved:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}


	this.removeExercise = function(id){
		var msg = {};
		msg.action = "removeExercise";
		msg.exerciseId = id;
		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again. You can remove an exercise if it\'s not referenced by any user, team, run exercises. For further help, please contact support.');
			}
			else{
				PNotify.removeAll();
				notificationService.success('Exercise removed.');
				$rootScope.$broadcast('exerciseRemoved:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	};
	this.removeOrganization = function(id){
		var msg = {};
		msg.action = "removeOrganization";
		msg.orgId = id;
		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again. You can remove an organization if there are no users, teams, invitation codes, run/available exercises associated. For further help, please contact support.');
			}
			else{
				PNotify.removeAll();
				notificationService.success('Organization removed.');
				$rootScope.$broadcast('organizationRemoved:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	};

	this.addUser = function(user){
		var msg = {};
		msg.action = 'addUser';
		msg.username = user.username;
		msg.firstName = user.firstName;
		msg.lastName = user.lastName;
		msg.email = user.email;
		msg.country = user.country.short;
		msg.roleId = user.role.id;
		msg.concurrentExercisesLimit = user.concurrentExercisesLimit;
		msg.password = user.password;
		msg.forcePasswordChange = user.forcePasswordChange;
		msg.orgId = user.organization.id;
		msg.credits = user.credits

		var req = {
				method: 'POST',
				url: '/management/reviewer/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
			else{
				PNotify.removeAll();
				notificationService.success('User added.');
				$rootScope.$broadcast('userAdded:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.updateUser = function(id,user){
		var msg = {};
		msg.action = 'updateUser';
		msg.id = id;
		msg.username = user.username;
		msg.firstName = user.firstName;
		msg.lastName = user.lastName;
		msg.email = user.email;
		msg.country = user.country.short;
		msg.roleId = user.role.id;
		msg.concurrentExercisesLimit = user.concurrentExercisesLimit;
		msg.orgId = user.organization.id;
		msg.credits = user.credits

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
			else{
				PNotify.removeAll();
				notificationService.success('User updated.');
				$rootScope.$broadcast('userUpdate:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}


	this.addUsersToTeam = function(userList, teamName){
		var msg = {};
		msg.action = 'addUsersToTeam';
		msg.users = userList;
		msg.teamName = teamName;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
			else{
				PNotify.removeAll();
				notificationService.success('Users added to team.');
				$rootScope.$broadcast('usersAddedToTeam:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getRegionsForExercise = function(id){

		var msg = {};
		msg.action = 'getRegionsForExercise';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('exerciseRegions:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getRunningExercises = function(){
		var msg = {};
		msg.action = 'getAllRunningExercises';

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg
		}
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('runningExercises:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getExerciseDetails = function(id){

		var msg = {};
		msg.action = 'getExerciseDetails';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('exerciseDetails:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.isTeamNameAvailable = function(name){

		var msg = {};
		msg.action = 'checkTeamNameAvailable';
		msg.name = name;
		var req = {
				method: 'POST',
				url: '/management/reviewer/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('teamNameAvailable:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.isChallengeNameAvailable = function(name){

		var msg = {};
		msg.action = 'checkChallengeNameAvailable';
		msg.name = name;
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('challengeNameAvailable:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}


	this.isExerciseNameAvailable = function(name){

		var msg = {};
		msg.action = 'checkExerciseNameAvailable';
		msg.name = name;
		var req = {
				method: 'POST',
				url: '/management/rtfadmin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('exerciseNameAvailable:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.isOrgNameAvailable = function(name){

		var msg = {};
		msg.action = 'checkOrganizationNameAvailable';
		msg.name = name;
		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('orgNameAvailable:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.checkUsernameAvailable = function(username){

		var msg = {};
		msg.action = 'isUsernameAvailable';
		msg.username = username;
		var req = {
				method: 'POST',
				url: '/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('usernameAvailable:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.sendNotification = function(username,text){
		var msg = {};
		msg.action = 'sendNotification';
		msg.text = text;
		msg.username = username;

		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Notification sent.');
				$rootScope.$broadcast('notificationSent:updated',response.data);
			}
			else if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Action failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.removeTeamManager = function(teamId,username){
		var msg = {};
		msg.action = 'removeTeamManager';
		msg.teamId = teamId;
		msg.username = username;

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('User removed from team managers.');
				$rootScope.$broadcast('makeTeamManager:updated',response.data);
			}
			else if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.makeTeamManager = function(teamId,username){
		var msg = {};
		msg.action = 'addTeamManager';
		msg.teamId = teamId;
		msg.username = username;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('User added as team manager.');
				$rootScope.$broadcast('makeTeamManager:updated',response.data);
			}
			else if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.removeFromTeam = function(teamId,username){
		var msg = {};
		msg.action = 'removeFromTeam';
		msg.teamId = teamId;
		msg.username = username;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('User removed from team.');
				$rootScope.$broadcast('deletedFromTeam:updated',response.data);
			}
			else if(response.data.result=="error"){
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.deleteTeam = function(teamId){
		var msg = {};
		msg.action = 'deleteTeam';
		msg.teamId = teamId;

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Team deleted.');
				$rootScope.$broadcast('deletedTeam:updated',response.data);
			}
			else if(response.data.result=="error"){
				if(response.data.errorMsg="TeamNotEmpty"){
					PNotify.removeAll();
					notificationService.notice('Team cannot be deleted, please remove all members first.');
				}
				else{
					PNotify.removeAll();
					notificationService.notice('Updated failed, please try again.');
				}
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.addTeam = function(teamName,orgName){
		var msg = {};
		msg.action = 'addTeam';
		msg.teamName = teamName;
		msg.orgName = orgName;

		var req = {
				method: 'POST',
				url: '/management/reviewer/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('Team created.');
				$rootScope.$broadcast('addTeam:updated',response.data);
			}
			else if(response.data.result=="error"){
				if(response.data.errorMsg=="TeamExists"){
					PNotify.removeAll();
					notificationService.notice('Specified team name already exists.');
				}
				else{
					PNotify.removeAll();
					notificationService.notice('Updated failed, please try again.');
				}
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getAvailableExercises = function(){

		var msg = {};
		msg.action = 'getExercises';

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('availableExercises:updated',response.data);

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.enableExerciseForOrg = function(idExercise,idOrg){
		var msg = {};
		msg.action = 'enableExerciseForOrg';
		msg.orgId = idOrg;
		msg.exercise = idExercise;
		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(undefined==response.data.error){
				$rootScope.$broadcast('enableExerciseForOrg:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.disableExerciseForOrg = function(idExercise,idOrg){
		var msg = {};
		msg.orgId = idOrg;
		msg.exercise = idExercise;
		msg.action = 'disableExerciseForOrg';

		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(undefined==response.data.error){
				$rootScope.$broadcast('disableExerciseForOrg:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getUnreadNotifications = function(){

		var msg = {};
		msg.action = 'getNotifications';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('unreadNotifications:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.markNotificationAsRead = function(idNotification){

		var msg = {};
		msg.action = 'markNotificationRead';
		msg.id = idNotification;
		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(undefined==response.data.error){
				$rootScope.$broadcast('unreadNotifications:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.updateUserPassword = function(oldPassword, newPassword){
		var msg = {};
		msg.action = 'setUserPassword';
		msg.oldPwd = oldPassword;
		msg.newPwd = newPassword;
		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('User password updated.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.resetUserPassword = function(usr, pwd){
		var msg = {};
		msg.action = 'resetUserPassword';
		msg.username = usr;
		msg.password = pwd;
		var req = {
				method: 'POST',
				url: '/management/admin/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('User password resetted.');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.updateUserStatus = function(username,status){
		var msg = {};
		msg.action = 'updateUserStatus';
		msg.username = username;
		msg.status = status;
		var req = {
				method: 'POST',
				url: '/management/reviewer/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('User status updated.');
				$rootScope.$broadcast('userStatus:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.unlockUserAccount = function(username){
		var msg = {};
		msg.action = 'unlockUserAccount';
		msg.username = username;
		var req = {
				method: 'POST',
				url: '/management/reviewer/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result=="success"){
				PNotify.removeAll();
				notificationService.success('User account unlocked.');
				$rootScope.$broadcast('accountUnlocked:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.doUserLogout = function(){
		var msg = {};
		msg.action = 'doLogout';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$(document).attr('location', "/");
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	$rootScope.ctoken = "";

	this.getCToken = function(){
		var msg = {};
		msg.action = 'getUserCToken';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.ctoken = response.data.ctoken;
			$this.getInitialData();
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getUsers = function(){
		var msg = {};
		msg.action = 'getUsers';
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('usersList:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getGlobalStats = function(filter){
		var msg = {};
		msg.action = 'getGlobalStats';
		msg.filter = filter;
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('stats:updated',response.data);
			$('#waitLoader').hide();
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getUserStats = function(username){
		var msg = {};
		msg.username = username;
		msg.action = 'getUserStats';
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('statsUser:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getUserExercises = function(username){
		var msg = {};
		msg.username = username;
		msg.action = 'getUserExercises';
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('userCompletedExercises:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}


	this.getTeamStats = function(teamName){
		var msg = {};
		msg.name = teamName;
		msg.action = 'getTeamStats';
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('statsTeam:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getUserAchievements = function(usr){
		var msg = {};
		msg.action = 'getUserAchievements';
		msg.username = usr;
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('userAchievements:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.renameTeam = function(teamId,teamName){
		var msg = {};
		msg.action = 'renameTeam';
		msg.teamId = teamId;
		msg.name = teamName;
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('teamRenamed:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getUserDetails = function(usr){
		var msg = {};
		msg.action = 'getUserDetails';
		msg.username = usr;
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('userDetails:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getCompletedReviews = function(){
		var msg = {};
		msg.action = 'getCompletedReviews';
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('completedReviews:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getTeams = function(){
		var msg = {};
		msg.action = 'getTeams';
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('teamsList:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getTeamDetails = function(teamName){
		var msg = {};
		msg.action = 'getTeamDetails';
		msg.name = teamName;
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('teamDetails:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getTeamMembers = function(teamName){
		var msg = {};
		msg.action = 'getTeamMembers';
		msg.name = decodeURIComponent(teamName);
		var req = {
				method: 'POST',
				url: '/management/stats/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('teamMembers:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getCToken();

	this.getInitialData = function(){  
		this.getCountries();
		this.getUserProfile();
		initialData = true;
	}

	this.getPendingReviews = function(){
		var msg = {};
		msg.action = 'getPendingReviews';

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('pendingReviews:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.getPendingReviewDetails = function(id){
		var msg = {};
		msg.action = 'getReviewDetails';
		msg.id = id
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('pendingReviewDetails:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.getCompletedReviewDetails = function(id){
		var msg = {};
		msg.action = 'getReviewDetails';
		msg.id = id
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('completedReviewDetails:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.getUserFeedback = function(id){
		var msg = {};
		msg.action = 'getUserFeedback';
		msg.id = id
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('userFeedback:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.submitReview = function(o){
		var msg = {}
		msg.action = 'postReview';
		msg.obj = o;
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			PNotify.removeAll();
			notificationService.success('Review Submitted');
			$rootScope.$broadcast('reviewSubmitted:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getAvailableUsersForTeam = function(teamName){
		var msg ={};
		msg.teamName = teamName;
		msg.action = 'getAvailableUsersForTeam';
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('availableUsersTeam:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});


	}

	this.downloadExerciseSolutions = function(id){

		var msg = {};
		msg.action = 'getSolutionFile';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
				responseType: "blob"
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			var filename = "";                   
			var disposition = response.headers("Content-Disposition")
			var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
			var matches = filenameRegex.exec(disposition);
			if (matches !== null && matches[1]) 
				filename = matches[1].replace(/['"]/g, '');

			var blob = new Blob([response.data], { type:"application/pdf;" });		
			if (navigator.appVersion.toString().indexOf('.NET') > 0){
				window.navigator.msSaveBlob(blob, filename);
			}
			else{
				var downloadLink = angular.element('<a></a>');
				downloadLink.attr('href',window.URL.createObjectURL(blob));
				downloadLink.attr('download', filename);
				downloadLink[0].click();
			}

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.returnPicturesInQueue = function(queue){

		var currentImage = queue.pop()

		var req = {
			method: 'GET',
			url: '/user/image?type=exerciseInfo&id='+currentImage,
			responseType: "blob"
		}
		$http(req).then(function successCallback(response) {
			var blob = new Blob([response.data], { type:"image/png;" });		

			var reader = new FileReader();
			reader.readAsDataURL(blob); 
			reader.onloadend = function() {

				var obj = {};
				obj.id = currentImage;
				obj.data = reader.result; 
				$rootScope.exportInfoListImages.push(obj);
				if(queue.length==0){
					$rootScope.$broadcast('infoListPicturesReturned:updated',$rootScope.exportInfoListImages.length);
				}
				else{
					$this.returnPicturesInQueue(queue);
				}
			}




		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.returnExerciseSolutions = function(id){

		var msg = {};
		msg.action = 'getSolutionFile';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
				responseType: "blob"
		}
		$http(req).then(function successCallback(response) {
			var filename = "";                   
			var disposition = response.headers("Content-Disposition")
			var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
			var matches = filenameRegex.exec(disposition);
			if (matches !== null && matches[1]) 
				filename = matches[1].replace(/['"]/g, '');

			var blob = new Blob([response.data], { type:"application/pdf;" });		
			var obj = {};
			obj.name = filename;
			obj.data = blob;
			$rootScope.$broadcast('exerciseSolutionReturned:updated',obj);

		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.returnExerciseReference = function(id){

		var msg = {};
		msg.action = 'getReferenceFile';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
				responseType: "blob"
		}
		$http(req).then(function successCallback(response) {
			var filename = "";                   
			var disposition = response.headers("Content-Disposition")
			var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
			var matches = filenameRegex.exec(disposition);
			if (matches !== null && matches[1]) 
				filename = matches[1].replace(/['"]/g, '');

			var blob = new Blob([response.data], { type:"application/pdf;" });	
			var obj = {};
			obj.name = filename;
			obj.data = blob;
			$rootScope.$broadcast('exerciseReferenceReturned:updated',obj);

		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.downloadExerciseReference = function(id){

		var msg = {};
		msg.action = 'getReferenceFile';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
				responseType: "blob"
		}
		$('.waitLoader').show();
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			var filename = "";                   
			var disposition = response.headers("Content-Disposition")
			var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
			var matches = filenameRegex.exec(disposition);
			if (matches !== null && matches[1]) 
				filename = matches[1].replace(/['"]/g, '');

			var blob = new Blob([response.data], { type:"application/pdf;" });	
			if (navigator.appVersion.toString().indexOf('.NET') > 0){
				window.navigator.msSaveBlob(blob, filename);
			}
			else{
				var downloadLink = angular.element('<a></a>');
				downloadLink.attr('href',window.URL.createObjectURL(blob));
				downloadLink.attr('download', filename);
				downloadLink[0].click();
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}



	this.markCancelled = function(id){
		var msg ={};
		msg.action = 'markAsCancelled';
		msg.id = id;
		var req = {
				method: 'POST',
				url: '/management/team/handler',
				data: msg,
		}
		$('.waitLoader').show();

		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			PNotify.removeAll();
			notificationService.success('Exercise marked as cancelled.');
			$rootScope.$broadcast('reviewCancelled:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});


	}



})
rtf.controller('navigation',['$rootScope','$scope','server','$timeout','$http','$location',function($rootScope,$scope,server,$timeout,$http,$location){
	$scope.user = server.user;


	updateData = function(){
		if($rootScope.ctoken == ""){
			return;
		}
		if($('.modal.in').length>0){
			console.log('modal open, skipping data udpdate...')

			return;
		}
		if($rootScope.visibility.review){
			console.log('review tab open, skipping data udpdate...')

			return;
		}

		console.log('updating data...')

		server.getTeams();
		server.getUnreadNotifications();
		server.getChallenges();

		if(server.user.r != 3){
			server.getUsers();
			server.getGlobalStats([]);
		}
		if(server.user.r <= 3){
			server.getPendingReviews();
			server.getCompletedReviews();
			server.getAvailableExercises();
			server.getRunningExercises();
		}
		if(server.user.r <= 0){
			server.getOrganizations();
			server.getGateways();
			server.getAWSRegions();
		}
	}
	setInterval(function(){updateData()},100000)



	$scope.logout = function(){
		server.doUserLogout();
	}
	$scope.notifications = [];
	var ctokenReq = {
			method: 'POST',
			url: '/user/handler',
			data: { action: 'getUserCToken'}
	}
	$scope.goTo = function(link){
		$location.path(link.replace("#",""), false);
	}
	$scope.$on('unreadNotifications:updated', function(event,data) {
		$scope.notifications = data; 
	})
	$scope.markNotificationRead = function(id){
		server.markNotificationAsRead(id);
	}

	$rootScope.$on('$locationChangeSuccess', function(event, newUrl, oldUrl){
		if(newUrl.indexOf('d2h-')>-1){
			return;
		}
		var target = newUrl.substr(newUrl.indexOf("#")).replace("#","").replace("/","");
		target = target.split("/")
		switch(target[0]){       
		case "home":
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.welcome = true;
			$rootScope.visibility.home = true;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.users = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.running = false;
			_st(responsiveView,200);
			$(window).scrollTop(0);
			break;
		case "challenges":
			if(target[1]=="details" && undefined!=target[2] && ""!=target[2]){
				var exId = target[2]
				if($rootScope.ctoken == ""){
					$http(ctokenReq).then(function successCallback(response) {
						$rootScope.ctoken = response.data.ctoken;
						server.getChallengeDetails(exId);

					}, function errorCallback(response) {
						console.log('ajax error');
					});
				}
				else{
					server.getChallengeDetails(exId);
				}
				$rootScope.showChallengeDetails = true;
				$rootScope.showChallengesList = false;
			}
			else{
				$rootScope.showChallengeDetails = false;
				$rootScope.showChallengesList = true;
			}
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.users = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.running = false;

			$rootScope.visibility.challenges = true;
			$(window).scrollTop(0);
			break;
		case "users":
			if(target[1]=="details" && undefined!=target[2] && ""!=target[2]){
				try{
					username = decodeURIComponent(target[2])
				}catch(err){
					username = target[2];
				}
				if($rootScope.ctoken == ""){
					$http(ctokenReq).then(function successCallback(response) {
						$rootScope.ctoken = response.data.ctoken;
						server.getUserDetails(username);
						server.getUserStats(username);
						server.getUserExercises(username);
						server.getUserAchievements(username);
						$rootScope.showUserList = false;
						$rootScope.showUserDetails = true;
						return;
					}, function errorCallback(response) {
						console.log('ajax error');
					});
				}
				server.getUserDetails(username);
				server.getUserStats(username);
				server.getUserExercises(username);
				server.getUserAchievements(username);
				$rootScope.showUserList = false;
				$rootScope.showUserDetails = true;
			}
			else{
				$rootScope.showUserList = true;
				$rootScope.showUserDetails = false;
			}
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.users = true;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.running = false;
			$(window).scrollTop(0);
			break;
		case "review":
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.users = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.running = false;
			$rootScope.visibility.review = true;
			$(window).scrollTop(0);
			break;
		case "running-exercises":
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.users = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.running = true;
			$(window).scrollTop(0);
			break;
		case "teams":
			if(target[1]=="details" && undefined!=target[2] && ""!=target[2]){
				try{
					name = decodeURIComponent(target[2])
				}catch(err){
					name = target[2];
				}
				if($rootScope.ctoken == ""){
					$http(ctokenReq).then(function successCallback(response) {
						$rootScope.ctoken = response.data.ctoken;
						$rootScope.selectedTeam = name;
						server.getTeamMembers(name);
						server.getTeamStats(name);
						server.getTeamDetails(name);
						$rootScope.showTeamList = false;
						$rootScope.showTeamMembers = true;
						return;
					}, function errorCallback(response) {
						console.log('ajax error');
					});
				}
				else{
					$rootScope.selectedTeam = name;
					server.getTeamMembers(name);
					server.getTeamStats(name);
					server.getTeamDetails(name);
					$rootScope.showTeamList = false;
					$rootScope.showTeamMembers = true;
				}
				$rootScope.showTeamList = false;
				$rootScope.showTeamMembers = true;
			}
			else{
				$rootScope.showTeamList = true;
				$rootScope.showTeamMembers = false;
			}
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.users = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.running = false;
			$rootScope.visibility.teams = true;
			$(window).scrollTop(0);
			break;
		case "exercises":
			if(target[1]=="details" && undefined!=target[2] && ""!=target[2]){
				var eId = target[2];
				if($rootScope.ctoken == ""){
					$http(ctokenReq).then(function successCallback(response) {
						server.getCompletedReviewDetails(eId);
						server.getUserFeedback(eId);
					}, function errorCallback(response) {
						console.log('ajax error');
					});
				}
				else{
					server.getCompletedReviewDetails(eId);
					server.getUserFeedback(eId);
				}
			}
			else{
				$(window).scrollTop(0);
			}
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.users = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.running = false;
			$rootScope.visibility.exercises = true;
			break;
		case "stats":
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.users = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.running = false;
			$rootScope.visibility.stats = true;
			$(window).scrollTop(0);
			break;
		case "settings":
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.users = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.running = false;
			$rootScope.visibility.settings = true;
			$(window).scrollTop(0);
			break;
		case "available-exercises":
			if(target[1]=="new"){
				return;
			}
			if(target[3]=="flags" || target[3] == "info"){
				return;
			}
			if(target[1]=="details"){
				$rootScope.visibility.users = false;
				$rootScope.visibility.welcome = false;
				$rootScope.visibility.stats = false;
				$rootScope.visibility.home = false;
				$rootScope.visibility.review = false;
				$rootScope.visibility.organizations = false;
				$rootScope.visibility.exercises = false;
				$rootScope.visibility.settings = false;
				$rootScope.visibility.challenges = false;
				$rootScope.visibility.teams = false;
				$rootScope.visibility.gateways = false;
				$rootScope.visibility.running = false;
				$rootScope.visibility.availableExercises = true;
				if(Number.isInteger(parseInt(target[2])) && (undefined==$rootScope.exerciseDetails.id || $rootScope.exerciseDetails.id!=parseInt(target[2]))){
					getExDetails(parseInt(target[2]));
				}
			}
			else{
				$rootScope.visibility.users = false;
				$rootScope.visibility.welcome = false;
				$rootScope.visibility.stats = false;
				$rootScope.visibility.home = false;
				$rootScope.visibility.review = false;
				$rootScope.visibility.organizations = false;
				$rootScope.visibility.exercises = false;
				$rootScope.visibility.settings = false;
				$rootScope.visibility.teams = false;
				$rootScope.visibility.challenges = false;
				$rootScope.visibility.availableExercises = true;
				$rootScope.visibility.gateways = false;
				$rootScope.visibility.running = false;
				$rootScope.showExerciseList = true;
				$rootScope.showExerciseDetails = false;
			}
			$(window).scrollTop(0);
			break;
		case "organizations":
			$rootScope.visibility.users = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.running = false;
			$rootScope.visibility.organizations = true;
			$(window).scrollTop(0);
			break;
		case "gateways":
			$rootScope.visibility.users = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.running = false;
			$rootScope.visibility.gateways = true;
			$(window).scrollTop(0);
			break;
		default:{
			$rootScope.visibility.availableExercises = false;
			$rootScope.visibility.welcome = true;
			$rootScope.visibility.users = false;
			$rootScope.visibility.stats = false;
			$rootScope.visibility.organizations = false;
			$rootScope.visibility.review = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exercises = false;
			$rootScope.visibility.teams = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.gateways = false;
			$rootScope.visibility.running = false;
			$rootScope.visibility.home = true;
			$(window).scrollTop(0);
			break;
		}

		}        

	});

	function getExDetails(id){

		var msg = {};
		msg.action = 'getUserCToken';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.ctoken = response.data.ctoken;
			server.getExerciseDetails(id);
			server.getRegionsForExercise(id);
		}, function errorCallback(response) {
			console.log('ajax error');
		});


	}

	$rootScope.visibility = {}
	$rootScope.visibility.availableExercises = false;
	$rootScope.visibility.welcome = true;
	$rootScope.visibility.users = false;
	$rootScope.visibility.exercises = false;
	$rootScope.visibility.home = true;
	$rootScope.visibility.organizations = false;
	$rootScope.visibility.review = false;
	$rootScope.visibility.settings = false;
	$rootScope.visibility.teams = false;
	$rootScope.visibility.stats = false;

}])



rtf.factory('xhrInterceptor', ['$q','$rootScope', function($q, $rootScope) {
	return {
		'request': function(config) {
			if((config.url == "/user/handler" || config.url == "/management/rtfadmin/handler" || config.url == "/management/reviewer/handler" ||config.url == "/management/stats/handler" ||config.url == "/management/admin/handler" ||config.url == "/management/team/handler") && config.data.action !=undefined && config.data.action != "getUserCToken")
				config.data.ctoken = $rootScope.ctoken;
			return config;
		},
		'response': function(response) {
			if(undefined!=response && undefined!=response.data){
				try{
					if(response.data.indexOf('loginButton')>0){
						document.location = "/index.html";
					}
				}catch(err){}
			}
			if(undefined!=response && undefined!=response.data && undefined!=response.data.errorMsg && response.data.errorMsg=="ChangePassword"){
				document.location = "/user/changePassword.html";
				return;
			}
			return response;
		}
	};
}]);
rtf.run(['$route', '$rootScope', '$location', function ($route, $rootScope, $location) {
	var original = $location.path;
	$location.path = function (path, reload) {
		if (reload === false) {
			var lastRoute = $route.current;
			var un = $rootScope.$on('$locationChangeSuccess', function () {
				$route.current = lastRoute;
				un();
			});
		}
		return original.apply($location, [path]);
	};
}])


rtf.config(['$httpProvider', function($httpProvider) {  
	$httpProvider.interceptors.push('xhrInterceptor');
}]);

rtf.controller('welcome',['$scope','server',function($scope,server){
	$scope.user = server.user;
}])
rtf.controller('home',['$scope','server',function($scope,server){
	$scope.user = server.user;
}])
rtf.controller('organizations',['$scope','server','$filter',function($scope,server,$filter){

	$scope.masterOrganizations = [];
	$scope.filteredOrganizationsList = []; 
	$scope.selectedOrganizationRow = -1;
	$scope.organizationstableconfig = {
			itemsPerPage: 10,
			fillLastPage: false
	}
	$scope.saveFlow = false;


	$scope.newCodeMaxReedeems = '';
	$scope.invitationCodes = [];

	$scope.generateInvitationCode = function(){
		var obj = {};
		obj.orgId = $scope.selectedOrg.id;
		obj.maxUsers = $scope.newCodeMaxReedeems;
		server.generateInvitationCode(obj);
	}
	$scope.$on('invitationGenerated:updated', function(event,data) {
		$scope.newCodeMaxReedeems = ''
			server.getInvitationCodes($scope.selectedOrg.id);
	});
	$scope.removeInvitationCode = function(code){
		var obj = {};
		obj.orgId = $scope.selectedOrg.id;
		obj.orgCode = code;
		server.removeInvitationCode(obj);
	}

	$scope.$on('invitationRemoved:updated', function(event,data) {
		server.getInvitationCodes($scope.selectedOrg.id);
	});

	$scope.$on('invitationCodes:updated', function(event,data) {
		$scope.invitationCodes = data;
	});


	$scope.getReviewedDate = function(date){
		if(null!=date && undefined != date)
			return moment(date).local().format("MMM D, YYYY")
			else
				return "N/A";
	}
	$scope.updateFilteredList = function() {
		$scope.filteredOrganizationsList = $filter("filter")($scope.masterOrganizations, $scope.query);
	};

	$scope.$on('organizations:updated', function(event,data) {
		$scope.masterOrganizations = data;
		$scope.filteredOrganizationsList = $scope.masterOrganizations;
	});

	$scope.isOrgNameAvailable = function(){
		if($scope.newOrg.name != "" ){
			if($scope.saveFlow){
				if($scope.newOrg.name!=$scope.editingOrg.name){
					server.isOrgNameAvailable($scope.newOrg.name);
				}					
			}
			else{
				server.isOrgNameAvailable($scope.newOrg.name);
			}
		}
	}
	$scope.$on('orgNameAvailable:updated', function(event,data) {
		$scope.orgNameAvailable = data.result;
	});
	$scope.orgNameAvailable = true;
	$scope.newOrg = {};
	$scope.newOrg.name = "";
	$scope.newOrg.email = "";
	$scope.newOrg.maxUsers = 100;
	$scope.organizationDetails = false;

	$scope.showOrganizationDetails = function(org){
		$scope.selectedOrg = org;
		server.getInvitationCodes($scope.selectedOrg.id);
		$scope.organizationDetails = true;
	}

	$scope.backToOrgList = function(){
		$scope.organizationDetails = false;
		$scope.selectedOrg = "";
	}

	$scope.addOrganizationModal= function(){
		$scope.saveFlow = false;
		$('#addOrgModal').modal('show');
	}

	$scope.updateOrganizationModal= function(item){
		$scope.saveFlow = true;
		$scope.editingOrg = item;
		$scope.orgId = item.id;
		$scope.newOrg.name = item.name;
		$scope.newOrg.email = item.email;
		$scope.newOrg.maxUsers = item.maxUsers;
		$('#addOrgModal').modal('show');
	}


	var tmpOrgToBeRemoved = -1;

	$scope.removeOrganization = function(){
		$('#removeOrganizationModal').modal('hide');
		server.removeOrganization(tmpOrgToBeRemoved);
		tmpOrgToBeRemoved = -1;

	}
	$scope.removeOrganizationModal = function(id,name){
		$scope.tmpOrgToBeRemovedName = name;
		tmpOrgToBeRemoved = id;
		$('#removeOrganizationModal').modal('show');
	}

	$scope.addOrganization = function(){
		var obj = {};
		obj.name = $scope.newOrg.name;
		obj.email = $scope.newOrg.email;
		obj.maxUsers = $scope.newOrg.maxUsers;
		server.addOrganization(obj);

		$('#addOrgModal').modal('hide');

		$scope.newOrg.name = "";
		$scope.newOrg.email = "";
		$scope.newOrg.maxUsers = 100;
	}

	$scope.updateOrganization = function(){
		var obj = {};
		obj.id = $scope.orgId;
		obj.name = $scope.newOrg.name;
		obj.email = $scope.newOrg.email;
		obj.maxUsers = $scope.newOrg.maxUsers;
		server.updateOrganization(obj);

		$('#addOrgModal').modal('hide');

		$scope.newOrg.name = "";
		$scope.newOrg.email = "";
		$scope.newOrg.maxUsers = 100;
	}
	$scope.$on('organizationRemoved:updated', function(event,data) {
		if(data.result=="success"){
			server.getOrganizations();
			server.getUserProfileInfo();
		}
	});
	$scope.$on('organizationAdded:updated', function(event,data) {
		if(data.result=="success"){
			server.getOrganizations();
			server.getUserProfileInfo();
		}
	});
	$scope.$on('organizationUpdated:updated', function(event,data) {
		if(data.result=="success"){
			server.getOrganizations();
			server.getUserProfileInfo();
		}
	});
}])
rtf.controller('gateways',['$scope','server','$filter',function($scope,server,$filter){
	$scope.saveFlow = false;
	$scope.masterGateways = [];
	$scope.filteredGatewaysList = []; 
	$scope.selectedGatewayRow = -1;
	$scope.gatewaystableconfig = {
			itemsPerPage: 30,
			fillLastPage: false
	}
	$scope.newGateway = {};
	$scope.newGateway.region = "";
	$scope.newGateway.name = "";
	$scope.newGateway.fqdn = "";
	$scope.newGateway.status = true;
	$scope.awsRegions = [];
	$scope.getReviewedDate = function(date){
		if(null!=date && undefined != date)
			return moment(date).format("MMM D, YYYY")
			else
				return "N/A";
	}
	$scope.updateFilteredList = function() {
		$scope.filteredGatewaysList = $filter("filter")($scope.masterGateways, $scope.query);
	};

	$scope.$on('gateways:updated', function(event,data) {
		$scope.masterGateways = data;
		$scope.filteredGatewaysList = $scope.masterGateways;
	});
	$scope.$on('awsRegions:updated', function(event,data) {
		$scope.awsRegions = data;
	});	
	$scope.addGatewayModal= function(){
		$scope.saveFlow = false;
		$('#addGatewayModal').modal('show');
	}

	getAWSSupportedRegionFromCode = function(reg){
		for(obj in $scope.awsRegions){
			if($scope.awsRegions.hasOwnProperty(obj)){
				if($scope.awsRegions[obj].code==reg){
					return $scope.awsRegions[obj];
				}
			}
		}
		return "";
	}
	$scope.editGateway = function(gw){
		$scope.saveFlow = true;
		$scope.newGateway.id = gw.id;
		$scope.newGateway.region = gw.region;
		$scope.newGateway.fqdn = gw.fqdn;
		$scope.newGateway.status = gw.active;
		$scope.newGateway.name = gw.name;
		$('#addGatewayModal').modal('show');
	}
	$scope.saveGateway = function(gw){
		$scope.newGateway.region = $scope.newGateway.region.code;
		server.updateGateway($scope.newGateway)
		$('#addGatewayModal').modal('hide');

	}

	$scope.deleteGateway = function(id){
		server.deleteGateway(id);
	} 
	$scope.$on('gatewayDeleted:updated', function(event,data) {
		server.getGateways();
	});

	$scope.addGateway = function(){
		var obj = {};
		obj.region = $scope.newGateway.region.code;
		obj.fqdn = $scope.newGateway.fqdn;
		obj.name =  $scope.newGateway.name;
		obj.status = $scope.newGateway.status;
		server.addGateway(obj);
		$('#addGatewayModal').modal('hide');


	}

	$scope.getRegionFromCode = function(code){
		region = "";
		if(code==undefined || code == "")
			return "";
		switch(code){
		case "EU_WEST_1":
			region = "EU (Ireland)";
			break;
		case "US_EAST_1":
			region = "US East (N. Virginia)"
				break;
		case "AP_SOUTH_1":
			region = "Asia Pacific (Mumbai)"
				break;
		case "AP_SOUTHEAST_1":
			region = "Asia Pacific (Singapore)"
				break;
		case "US_EAST_2":
			region = "US East (Ohio)";
			break;
		case "US_WEST_2":
			region = "US West (Oregon)";
			break;
		case "US_WEST_1":
			region = "US West (N. California)";
			break;
		case "CA_CENTRAL_1":
			region = "Canada (Central)";
			break;
		case "EU_CENTRAL_1":
			region = "EU (Frankfurt)";
			break;
		case "EU_WEST_2":
			region = "EU (London)";
			break;
		case "EU_WEST_3":
			region = "EU (Paris)";
			break;
		case "AP_NORTHEAST_2":
			region = "Asia Pacific (Seoul)";
			break;
		case "AP_NORTHEAST_1":
			region = "Asia Pacific (Tokyo)";
			break;
		case "AP_SOUTHEAST_2":
			region = "Asia Pacific (Sydney)";
			break;
		case "SA_EAST_1":
			region = "South America (São Paulo)";
			break;
		default:
			break;
		}
		return region;

	}

	$scope.$on('gatewayAdded:updated', function(event,data) {
		if(data.result=="success"){
			server.getGateways();
			$scope.newGateway.region = "";
			$scope.newGateway.id = "";
			$scope.newGateway.fqdn = "";
			$scope.newGateway.status = true;
			$scope.newGateway.name = "";
		}
	});
}])


rtf.controller('completedExercises',['$scope','server','$rootScope','$filter','$location',function($scope,server,$rootScope,$filter,$location){
	$scope.masterCompletedReviews = [];
	$scope.hideCancelled = true;
	$scope.filteredCompletedList = []; 
	$scope.selectedCompletedRow = -1;
	$scope.showCompletedResult = false;
	$scope.showCompletedCodeDiff = false;
	$scope.showCompletedLogs = false;
	$scope.query = "";
	$scope.userFeedback = "";
	$scope.completedZipError = false;
	$scope.completedEmptyLog = false;
	$scope.completedEmptyDiff = false;
	$scope.completedtableconfig = {
			itemsPerPage: 10,
			fillLastPage: false
	}
	$scope.getIssueReportedStatus = function(item){

		if(!item.issuesReported)
			return "N/A";
		if(item.issuesReported && item.issuesReportedAddressed)
			return "Yes";
		if(item.issuesReported && !item.issuesReportedAddressed)
			return "No";

	}
	$scope.$on('userFeedback:updated', function(event,data) {
		$scope.userFeedback = data.id;
	});


	$scope.$watch("hideCancelled", function() {
		$scope.updateFilteredList();
	});

	$scope.getReviewedDate = function(date){
		if(null!=date && undefined != date)
			return moment(date).local().format("MMM D, HH:mm (Z)")
			else
				return "N/A";
	}

	$scope.updateFilteredList = function() {
		$scope.filteredCompletedList = $filter("filter")($scope.masterCompletedReviews, $scope.query);
		if($scope.hideCancelled){
			$scope.filteredCompletedList = $filter("filter")($scope.filteredCompletedList,{ status:"!CANCELLED"});
		}
	};

	$scope.$on('completedReviews:updated', function(event,data) {
		$scope.masterCompletedReviews = data;
		$scope.filteredCompletedList = $filter("filter")($scope.masterCompletedReviews, "!CANCELLED");
	});
	$scope.getDurationInterval = function(start,end){
		var out = moment.utc(moment(end).diff(moment(start))).format("H mm").replace(" ","h")+"'";
		return out;
	};
	$scope.getMinutesDuration = function(dur){
		if(undefined != dur && null != dur && 0 != dur){
			return moment.utc(moment.duration(dur,"m").asMilliseconds()).format("H mm").replace(" ","h")+"'";
		}
		else{
			return "N/A"
		}
	};
	$scope.getDatesInterval = function(start,end){
		var out = moment(start).local().format("YYYY/MM/DD, HH:mm");
		out += " - "+moment(end).local().format("HH:mm (Z)");
		return out;
	}
	var statusClassMap = {
			"REVIEWED": "table-success",
			"AUTOREVIEWED":"table-primary",
			"REVIEWED_MODIFIED":"table-info",
			"CANCELLED": "table-warning"
	}
	$scope.getStatusClass = function(status) {
		return statusClassMap[status]
	};
	$scope.getExerciseStatusString = function(status){
		switch(status){
		case "REVIEWED":
			return "Reviewed"
		case "AUTOREVIEWED":
			return "Auto"
		case "REVIEWED_MODIFIED":
			return "Auto (Modified)"
		case "CANCELLED":
			return "Cancelled"

		default: return "";
		}
	}
	$scope.getStatusString = function(status){
		switch(status){
		case "1":
			return "Vulnerable"
		case "0":
			return "Not Vulnerable"
		case "2":
			return "Broken Functionality"
		case "4":
			return "Not Addressed"

		default: return "";
		}
	}
	var remediationClassMap = {
			"0": "table-success",
			"1": "table-danger",
			"2": "table-warning",
			"4": "table-info"
	};

	$scope.getRemediationClass = function(status){
		return remediationClassMap[status]
	};

	$scope.toggleCompletedCodeDiff = function(){
		if($scope.showCompletedCodeDiff == true)
			$scope.showCompletedCodeDiff = false;
		else{
			$scope.showCompletedLogs = false;
			$scope.showCompletedCodeDiff = true;
		}
	}
	$scope.toggleCompletedInstanceLogs = function(){
		if($scope.showCompletedLogs == true)
			$scope.showCompletedLogs = false;
		else{
			$scope.showCompletedCodeDiff = false;
			$scope.showCompletedLogs = true;
		}
	}
	$scope.vulnerabilityStatus = [{id:0, name:"Not Vulnerable"},{id:1, name:"Vulnerable"},{id:2, name:"Broken Functionality"},{id:4, name:"Not Addressed"},{id:3, name:"N/A"}]

	$scope.complaintExerciseFlag = "";
	$scope.scoreModifyExercise;
	$scope.scoreModifyResult;

	$scope.updatedResult = {};

	$scope.openScoreEditModal = function(e,f){
		$scope.scoreModifyExercise = e;
		$scope.scoreModifyResult = f;
		$('#scoringEditModal').modal('show');
	}
	$scope.updateScore = function(){
		var obj = {};
		obj.comment = $scope.updatedResult.comment;
		obj.status  = $scope.updatedResult.status.id;
		obj.score = $scope.updatedResult.score;
		obj.id = $scope.updatedResult.id = $scope.scoreModifyExercise;
		obj.name = $scope.updatedResult.name = $scope.scoreModifyResult.name;
		server.updateScore(obj);
	}

	$scope.$on('scoringUpdated:updated', function(event,data) {	
		server.getCompletedReviewDetails($scope.scoreModifyExercise);
		$scope.complaintModalTextInput = "";
		$scope.updatedResult.comment = "";
		$scope.updatedResult.status = "";
		$scope.updatedResult.score = "";
	});

	$scope.openCommentModal = function(e,f){
		$scope.complaintExerciseId = e;
		$scope.complaintExerciseFlag = f;
		$('#scoringCommentModal').modal('show');
	}

	$scope.sendScoringComment = function(){
		var obj = {};
		obj.name = $scope.complaintExerciseFlag;
		obj.text = $scope.complaintModalTextInput;
		obj.id = $scope.complaintExerciseId;
		server.addScoringComment(obj);
		$('#scoringCommentModal').modal('hide');
	}

	$scope.$on('scoringCommentAdded:updated', function(event,data) {	
		server.getCompletedReviewDetails($scope.complaintExerciseId);
		$scope.complaintModalTextInput = "";
	});

	$scope.getQuestionName = function(resName){
		if(undefined==resName || resName=="")
			return;
		for(var i in $scope.completedItemDetails.exercise.flags){
			if($scope.completedItemDetails.exercise.flags.hasOwnProperty(i)){
				for(var j in $scope.completedItemDetails.exercise.flags[i].flagList){
					if($scope.completedItemDetails.exercise.flags[i].flagList.hasOwnProperty(j) && $scope.completedItemDetails.exercise.flags[i].flagList[j].selfCheckAvailable && $scope.completedItemDetails.exercise.flags[i].flagList[j].selfCheckName==resName){
						return $scope.completedItemDetails.exercise.flags[i].title;
					}
				}
			}
		}
	}
	$scope.getQuestionMaxScore = function(resName){
		if(undefined==resName || resName=="")
			return;
		for(var i in $scope.completedItemDetails.exercise.flags){
			if($scope.completedItemDetails.exercise.flags.hasOwnProperty(i)){
				for(var j in $scope.completedItemDetails.exercise.flags[i].flagList){
					if($scope.completedItemDetails.exercise.flags[i].flagList.hasOwnProperty(j) && $scope.completedItemDetails.exercise.flags[i].flagList[j].selfCheckAvailable && $scope.completedItemDetails.exercise.flags[i].flagList[j].selfCheckName==resName){
						return $scope.completedItemDetails.exercise.flags[i].flagList[j].maxScore;
					}
				}
			}
		}

	}


	$scope.$on('completedReviewDetails:updated', function(event,data) {

		data.placements = [];
		var offset = $('#showCompletedResults').offset();
		$('html, body').animate({
			scrollTop: offset.top - 50,
			scrollLeft: offset.left
		});

		for(var i in data.results){
			if(data.results[i].firstForFlag){
				var tmpObj = {}
				tmpObj.name = data.results[i].name;
				tmpObj.placement = "1st place";
				data.placements.push(tmpObj)
			}
			if(data.results[i].secondForFlag){
				var tmpObj = {}
				tmpObj.name = data.results[i].name;
				tmpObj.placement = "2nd place";
				data.placements.push(tmpObj)
			}
			if(data.results[i].thirdForFlag){
				var tmpObj = {}
				tmpObj.name = data.results[i].name;
				tmpObj.placement = "3rd place";
				data.placements.push(tmpObj)
			}

			switch(data.results[i].status){
			case "VULNERABLE":
				data.results[i].status = "Vulnerable"
					break;
			case "NOT_VULNERABLE":
				data.results[i].status = "Not Vulnerable"
					break;
			case "BROKEN_FUNCTIONALITY":
				data.results[i].status = "Broken Functionality"
					break;
			case "NOT_AVAILABLE":
				data.results[i].status = "N/A"
					break;
			case "NOT_ADDRESSED":
				data.results[i].status = "Not Addressed"
					break;
			}


		}
		if(data.results.length == 0){
			data.results = [];
			for(var j in data.exercise.flags){
				data.results[j] = {};
				data.results[j].name = data.exercise.flags[j].title;
				data.results[j].status = "N/A";
				data.results[j].score = 0;
				data.results[j].verified = false; 
			}
		}

		$scope.completedItemDetails = data;
		$scope.showCompletedResult = false;
		$scope.showCompletedCodeDiff = false;

		$scope.showCompletedResult = true;
		$scope.completedZipError = false;
		$scope.completedEmptyLog = false;
		$scope.completedEmptyDiff = false;


		var tba = "";

		JSZipUtils.getBinaryContent($scope.completedItemDetails.id,$rootScope.ctoken,'/management/team/handler','getReviewFile', function(err, data) {
			if(err) {
				throw err; // or handle err
				$scope.completedZipError = true;
			}

			JSZip.loadAsync(data).then(function(zip) {
				for(file in zip.files){
					if(file.indexOf('sourceDiff.txt')>-1){
						zip.file(file).async("string").then(function success(content) {
							var diffString = content;
							if(diffString==""){
								$scope.completedEmptyDiff = true;
								$('#completedTargetDiv').empty()
								return;
							}
							try{
								var diff2htmlUi = new Diff2HtmlUI({diff : diffString });
								diff2htmlUi.draw( '#completedTargetDiv', {
									inputFormat : 'diff',
									showFiles : true,
									matching : 'lines'
								});
								diff2htmlUi.highlightCode('#completedTargetDiv');
							} catch(err){
							}
						})                        
					}
					else if(file.indexOf('.log')>-1){
						zip.file(file).async("string").then(function success(content) {
							var resultString = content;
							if(resultString=="" && tba==""){
								$scope.completedEmptyLog = true;
								$('#completedRtfLogText').empty()
								return;
							}
							rtfLog = resultString;
							var datas = rtfLog.split("\n");
							for (var i = 0; i < datas.length; i++) {
								if (datas[i] !== "") {
									tba += '<li class="list-group-item">' + htmlEncode(datas[i]) + '</li>';
								}
							}
							$('#completedRtfLogText').append(tba);
							var diff2htmlUi = new Diff2HtmlUI();
							diff2htmlUi.highlightCode('#completedRtfLogText');
							diff2htmlUi.highlightCode('.list-group-item');
						});   
					}
				}
			});
		});    
	})

	$scope.showCompletedDetailsFor = function(eId, feedback, index){
		server.getCompletedReviewDetails(eId);
		if(feedback){
			server.getUserFeedback(eId);
		}
		else{
			$scope.userFeedback = "";
		}
		$scope.selectedCompletedRow = index;
		$location.path("exercises/details/"+eId, false);

	}
}])
rtf.controller('users',['$scope','server','$location','$rootScope','$filter',function($scope,server,$location,$rootScope,$filter){

	$scope.saveFlow = false;

	$scope.masterUsersList = [];
	$scope.filteredUsersList = []; 

	$scope.remediatedPerIssue = {}
	$scope.remediatedPerIssue.data = [];
	$scope.remediatedPerIssue.labels = [];
	$scope.remediatedPerCategory = {}
	$scope.remediatedPerCategory.data = [];
	$scope.remediatedPerCategory.labels = [];
	$scope.remediationRatePerIssue = [];
	$scope.remediationRatePerCategory = [];
	$scope.scorePerExercises = {};
	$scope.options = {}
	$scope.options.layout = {
			padding: {
				left: 0,
				right: 0,
				top: 0,
				bottom: 0
			}
	}
	$scope.options.animation = false;
	$scope.options.pieceLabel = {
			// render 'label', 'value', 'percentage' or custom function, default is 'percentage'
			render: 'percentage',
			// precision for percentage, default is 0
			precision: 0,
			// identifies whether or not labels of value 0 are displayed, default is false
			showZero: false,
			// font size, default is defaultFontSize
			fontSize: 12,
			// font color, can be color array for each data, default is defaultFontColor
			fontColor: '#FFF',
			// font style, default is defaultFontStyle
			fontStyle: 'normal',
			// font family, default is defaultFontFamily
			fontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
			// draw label in arc, default is false
			arc: false,
			// position to draw label, available value is 'default', 'border' and 'outside'
			// default is 'default'
			position: 'border',
			// draw label even it's overlap, default is false
			overlap: true
	}
	$scope.options.legend = {
			display: true,
			position: 'bottom',
			labels: {
				fontColor: "#000",
				fontSize: 10
			}
	};
	$scope.options.title = {
			text : "",
			display:true,
			position: 'bottom'

	}
	$scope.radarOptions = {}
	$scope.radarOptions.animation = false;
	$scope.radarOptions.title = {
			text : "",
			display:true,
			position: 'top'

	}
	$scope.radarOptions.legend = {
			display: true,
			position: 'bottom',
			labels: {
				fontColor: "#000",
				fontSize: 10
			}
	};

	$scope.formatOrganizations = function(objArray){
		var stringOut = "";
		for(var i=0;i<objArray.length;i++){
			stringOut += objArray[i].name;
			if(i<objArray.length-1){
				stringOut += ", ";
			}
		}
		return stringOut;
	}

	$scope.newNotificationText = "";
	$scope.sendNotificationModal = function(){
		$("#sendNotificationModal").modal('show');
	}
	$scope.sendNotification = function(){
		server.sendNotification($scope.selectedUser,$scope.newNotificationText);
		$scope.newNotificationText = "";
		$("#sendNotificationModal").modal('hide');
	}
	$scope.$on('userProfile:updated', function(event,data) {
		$scope.user = data;
	});

	$scope.$on('notificationSent:updated', function(event,data) {


	});

	$scope.newUser = {};
	$scope.newUser.username = "";
	$scope.newUser.firstName = "";
	$scope.newUser.lastName = "";
	$scope.newUser.email = "";
	$scope.newUser.credits = -1
	$scope.newUser.country = "";
	$scope.newUser.role = { id:7, name:"User"};
	$scope.newUser.concurrentExercisesLimit = 1;
	$scope.newUser.password = "";
	$scope.newUser.emailVerified = true;
	$scope.newUser.forcePasswordChange = true;
	$scope.newUser.organization = "";

	$scope.availableUserRoles = [{ id:7, name:"User"},{ id:4, name:"Monitor"},{ id:3, name:"Team Manager"},{ id:1, name:"Reviewer"},{ id:0, name: "Admin"},{ id:-1, name: "RTF Admin"}];

	$scope.user = server.user;
	$scope.countries = server.countries;

	$scope.getUserRoleString = function(id){
		for(var i in $scope.availableUserRoles){
			if($scope.availableUserRoles.hasOwnProperty(i)){
				if($scope.availableUserRoles[i].id==id)
					return $scope.availableUserRoles[i].name;
			}
		}
		return id;
	}

	$scope.usernameAvailable = true;

	$scope.isUsernameAvailable = function(){
		if($scope.newUser.username != "" && $scope.newUser.username!=$scope.userDetails.user)
			server.checkUsernameAvailable($scope.newUser.username);
	}
	$scope.$on('usernameAvailable:updated', function(event,data) {
		$scope.usernameAvailable = data.result;
	});

	$scope.$on('userUpdate:updated', function(event,data) {
		$scope.saveFlow = false;
		$scope.selectedUser = $scope.tmpUpdatedName;
		$scope.getUserDetails($scope.selectedUser);
		server.getUsers();
		$('#addUserModal').modal('hide');
		$scope.newUser = {};
		$scope.newUser.firstName = "";
		$scope.newUser.lastName = "";
		$scope.newUser.email = "";
		$scope.newUser.country = "";
		$scope.newUser.role = { id:7, name:"User"};
		$scope.newUser.concurrentExercisesLimit = 1;
		$scope.newUser.password = "";
		$scope.newUser.credits = 10;
		$scope.newUser.emailVerified = true;
		$scope.newUser.forcePasswordChange = true;
		$scope.newUser.organization = "";
	});

	$scope.$on('userAdded:updated', function(event,data) {
		server.getUsers();
		$('#addUserModal').modal('hide');
		$scope.newUser = {};
		$scope.newUser.firstName = "";
		$scope.newUser.lastName = "";
		$scope.newUser.email = "";
		$scope.newUser.country = "";
		$scope.newUser.role = { id:7, name:"User"};
		$scope.newUser.concurrentExercisesLimit = 1;
		$scope.newUser.password = "";
		$scope.newUser.credits = 10;
		$scope.newUser.emailVerified = true;
		$scope.newUser.forcePasswordChange = true;
		$scope.newUser.organization = "";
	});

	$scope.addUserModal = function(){
		$scope.saveFlow = false;
		$('#addUserModal').modal('show');
	}
	$scope.tmpUpdatedName = "";
	$scope.updateUserModal = function(){
		$scope.saveFlow = true;

		$scope.newUser.username = $scope.userDetails.user;
		$scope.newUser.firstName = $scope.userDetails.firstName;
		$scope.newUser.lastName = $scope.userDetails.lastName;
		$scope.newUser.email = $scope.userDetails.email;
		$scope.newUser.country = $scope.userDetails.country;
		$scope.newUser.role = { id:$scope.userDetails.r, name:$scope.getUserRoleString($scope.userDetails.r) }
		$scope.newUser.concurrentExercisesLimit = $scope.userDetails.instanceLimit;
		$scope.newUser.organization = $scope.userDetails.defaultOrganization;
		$scope.newUser.credits = $scope.userDetails.credits


		$('#addUserModal').modal('show');
	}

	$scope.updateUser = function(){
		$scope.tmpUpdatedName = $scope.newUser.username;
		server.updateUser($scope.userDetails.idUser,$scope.newUser);
	}

	$scope.addUser = function(){
		server.addUser($scope.newUser);
	}





	$scope.$on('usersList:updated', function(event,data) {
		$scope.masterUsersList = data;
		$scope.filteredUsersList = $filter("filter")($scope.masterUsersList, $scope.query);
	});	
	$scope.usertableconfig = {
			itemsPerPage: 20,
			fillLastPage: false
	}
	$scope.passwordResetValue = "";
	$rootScope.showUserDetails = false;
	$rootScope.showUserList = true;
	$scope.selectedUser = "";

	$scope.updateFilteredList = function() {
		$scope.filteredUsersList = $filter("filter")($scope.masterUsersList, $scope.query);
	};

	$scope.removeFromTeam = function(){
		server.removeFromTeam($scope.userDetails.team.id, $scope.userDetails.user)
	}
	$scope.$on('deletedFromTeam:updated', function(event,data) {
		if(data.result=="success"){
			if($rootScope.visibility.users){
				$scope.getUserDetails($scope.selectedUser);
			}
		}
	})

	$scope.unlockAccount = function(){
		if($scope.selectedUser!="")
			server.unlockUserAccount($scope.selectedUser);
	}
	$scope.resetPassword = function(){
		if($scope.selectedUser!="" && $scope.passwordResetValue!=""){
			server.resetUserPassword($scope.selectedUser,$scope.passwordResetValue);
			$scope.passwordResetValue="";
		}
	}
	$scope.$on('accountUnlocked:updated', function(event,data) {
		if($scope.selectedUser!="")
			$scope.getUserDetails($scope.selectedUser);
		server.getUsers();
	});
	$scope.back = function(){
		window.history.go(-1);
	}
	$scope.displayUserList = function(){
		$rootScope.showUserList = true;
		$rootScope.showUserDetails = false;
		$location.path("users", false);
	}
	$scope.userDetails = {};
	$scope.getUserDetails = function(username){
		server.getUserDetails(username);
		server.getUserStats(username);
		server.getUserExercises(username);
		server.getUserAchievements(username);
		$location.path("users/details/"+username, false);
	}
	$scope.$on('userCompletedExercises:updated', function(event,data) {
		$scope.scorePerExercises.options = cloneObj($scope.options);
		$scope.scorePerExercises.data = [[],[]];
		$scope.scorePerExercises.series = ["Score","Duration (minutes)"];
		$scope.scorePerExercises.labels = [];
		$scope.scorePerExercises.datasetOverride = [ {showLine: true, pointStyle:'rectRounded',fill: false,
			pointRadius: 10,pointHoverRadius: 15},{ showLine: true,pointStyle:'rectRounded',fill: false,
				pointRadius: 10,pointHoverRadius: 15}] ;
		$scope.scorePerExercises.options.events= ["mousemove", "mouseout", "click", "touchstart", "touchmove", "touchend"];
		$scope.scorePerExercises.options.title.display=false;
		$scope.scorePerExercises.options.pieceLabel = {
				// render 'label', 'value', 'percentage' or custom function, default is 'percentage'
				render: 'value',
				// precision for percentage, default is 0
				precision: 0,
				// identifies whether or not labels of value 0 are displayed, default is false
				showZero: true,
				// font size, default is defaultFontSize
				fontSize: 14,
				// font color, can be color array for each data, default is defaultFontColor
				fontColor: '#FFF',
				// font style, default is defaultFontStyle
				fontStyle: 'normal',
				// font family, default is defaultFontFamily
				fontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
				// draw label in arc, default is false
				arc: false,
				// position to draw label, available value is 'default', 'border' and 'outside'
				// default is 'default'
				position: 'border',
				// draw label even it's overlap, default is false
				overlap: true
		}
		if(data.result!="error"){
			for (var property in data) {
				if (data.hasOwnProperty(property)) {
					$scope.scorePerExercises.labels.push(moment(data[property].endTime).format('MMM DD'))
					$scope.scorePerExercises.data[0].push(data[property].score.result);
					$scope.scorePerExercises.data[1].push(data[property].duration);

				}
			}
		}
	})
	$scope.$on('statsUser:updated', function(event,data) {

		$scope.remediationRatePerCategory = [];
		for (var property in data.categoriesRemediationRate) {
			if (data.categoriesRemediationRate.hasOwnProperty(property) && 
					Object.keys(data.categoriesRemediationRate[property]).length>0) {
				var obj = {};
				obj.options = cloneObj($scope.options);
				obj.options.title.text = property;
				obj.data = [];
				obj.labels = ["Not Vulnerable", "Vulnerable", "Not Addressed", "Broken Functionality"];
				for(var l=0;l<obj.labels.length;l++){
					var tmpStatus = obj.labels[l].toUpperCase().replace(" ","_");
					var tmpValue = data.categoriesRemediationRate[property][tmpStatus]
					if(undefined==tmpValue){
						tmpValue = 0;
					}
					obj.data.push(tmpValue);
				}
				$scope.remediationRatePerCategory.push(obj);
			}
		}
		$scope.remediatedPerCategory.data = [[]];
		$scope.remediatedPerCategory.labels = [];
		for(var j in $scope.remediationRatePerCategory){
			if ($scope.remediationRatePerCategory.hasOwnProperty(j)){
				var tmpName = $scope.remediationRatePerCategory[j].options.title.text;
				$scope.remediatedPerCategory.labels.push(tmpName);
				$scope.remediatedPerCategory.data[0].push(Math.ceil(data.totalMinutesPerIssueCategory[tmpName]));
			}
		}
		$scope.remediatedPerCategory.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerCategory.options.title.display = false;
		$scope.remediatedPerCategory.series = ["Total Time (minutes)"];


	});

	$scope.getDateFormat = function(date){
		if(undefined==date || moment(date)._d=="Invalid Date")
			return "Not Available";
		else
			return moment(date).format("D MMMM YYYY");	
	}
	$scope.trophies = [];
	$scope.score = 0;
	$scope.$on('userAchievements:updated', function(event,data) {
		$scope.score = "N/A";
		$scope.trophies = [];
		if(undefined!=data.score){
			$scope.score = data.score;
		}
		if(undefined!=data.trophies){
			var trophiesArray = deepCopy(data.trophies);
			var size = 4;
			while (trophiesArray.length > 0){
				$scope.trophies.push(trophiesArray.splice(0, size));
			}
		}
	});

	$scope.deleteUserAccount = function(username){
		server.deleteUserAccount(username);
		$('#deleteUserAccountModal').modal('hide');
	}

	$scope.openRemoveModal = function(){
		$('#deleteUserAccountModal').modal('show');
	}

	$scope.$on('userDeleted:updated', function(event,data) {
		server.getUsers();
		$rootScope.showUserDetails = false;
		$rootScope.showUserList = true;
	})

	$scope.userAvailableStatus = ["ACTIVE","INACTIVE","INVITED","CONFIRM_EMAIL","LOCKED","REMOVED"];

	$scope.updateUserStatus = function(){
		server.updateUserStatus($scope.selectedUser,$scope.userDetails.status)
	};

	$scope.$on('userDetails:updated', function(event,data) {
		switch(data.r){
		case -1:
			data.role = "RTF Admin";
			break;
		case 0:
			data.role = "Organization Admin";
			break;
		case 1:
			data.role = "Organization Reviewer";
			break;
		case 3:
			data.role = "Team Manager";
			break;
		case 4:
			data.role = "Organization Stats";
			break;
		case 7:
			data.role = "User";
			break;
		default:
			data.role = "N/A";
		}
		$scope.selectedUser = data.user;
		$scope.userDetails = data;
		$rootScope.showUserList = false;
		$rootScope.showUserDetails = true;
	});
}])
rtf.controller('availableExercises',['$scope','server','$rootScope','$location','$filter','notificationService',function($scope,server,$rootScope,$location,$filter,notificationService){

	$scope.selectedExercises = "";
	$scope.filteredAvailableExercisesList = [];
	$scope.user = server.user;
	$scope.masterAvailableExercisesList = [];
	$scope.exercisesForOrgs = [];
	$scope.availableRegions = [];
	$rootScope.showExerciseList = true;
	$rootScope.showExerciseDetails = false;
	$scope.supportedAwsRegions = server.supportedAwsRegions;
	$scope.definedGateways = server.definedGateways;
	$scope.definedGatewaysRegions = server.definedGatewaysRegions;

	$scope.newExerciseFlagList = true;
	$scope.saveFlow = false;

	$scope.tmpNewExercise = {}
	$scope.tmpNewExercise.referenceFile = {};
	$scope.tmpNewExercise.solutionFile = {};
	$scope.tmpNewExercise.difficulty = "";
	$scope.tmpNewExercise.status = "";
	$scope.tmpNewExercise.technology = "";
	$scope.tmpNewExercise.duration = "";
	$scope.tmpNewExercise.description = "";
	$scope.tmpNewExercise.flags = []
	$scope.tmpNewExercise.infoList = [];
	$scope.tmpNewExercise.topics = "";
	$scope.tmpNewExercise.title = "";
	$scope.tmpNewExercise.author = ""; 
	$scope.tmpNewExercise.type = ""; 

	$scope.tmpNewExercise.trophyTitle = ""; 
	$scope.tmpNewExercise.trophyDescription = ""; 
	$scope.tmpNewExercise.resources = []
	$scope.showSolutionFilePicker = true;
	$scope.showReferenceFilePicker = true;

	$scope.tmpResourceUrl = "";
	$scope.tmpResourceTitle = "";
	$scope.tmpRemFlag = {};
	$scope.tmpRemFlag.selfCheckName = "";
	$scope.tmpRemFlag.selfCheckAvailable = false;
	$scope.tmpRemFlag.type = "";
	$scope.tmpRemFlag.hint = "";
	$scope.tmpRemFlag.hintAvailable  = false;
	$scope.tmpRemFlag.instructions = "";
	$scope.tmpExpFlag = {};
	$scope.tmpExpFlag.selfCheckName = "";
	$scope.tmpExpFlag.selfCheckAvailable = false;
	$scope.tmpExpFlag.hint = "";
	$scope.tmpExpFlag.hintAvailable  = false;
	$scope.tmpExpFlag.hintScoreReduction = "";
	$scope.tmpExpFlag.maxScore = "";
	$scope.tmpExpFlag.type = "";
	$scope.tmpExpFlag.instructions = "";

	$scope.tmpInfoDescription = "";
	$scope.tmpInfoFile = {};
	$scope.tmpInfoTitle = "";

	$rootScope.exportInfoListImages = [];
	$scope.exerciseNameAvailable = true;

	$scope.isExerciseNameAvailable = function(){	
		if($scope.tmpNewExercise.title != "" ){
			if($scope.saveFlow){
				if($scope.tmpNewExercise.title!=$rootScope.exerciseDetails.title){
					server.isExerciseNameAvailable($scope.tmpNewExercise.title);
				}					
			}
			else{
				server.isExerciseNameAvailable($scope.tmpNewExercise.title);
			}
		}
	}
	$scope.$on('exerciseNameAvailable:updated', function(event,data) {
		$scope.exerciseNameAvailable = data.result;
	});


	$scope.addExerciseInfoToList = function(){
		var obj = {};
		obj.title = $scope.tmpInfoTitle;
		obj.image = $scope.tmpInfoFile;
		obj.description = $scope.tmpInfoDescription;
		if(obj.title!="" && !(Object.keys(obj.image).length === 0 && obj.image.constructor === Object) && obj.description != ""){
			$scope.tmpNewExercise.infoList.push(obj);
			$scope.tmpInfoTitle = "";
			$scope.tmpInfoFile = {};
			$scope.tmpInfoDescription = "";
		}	
		else{
			PNotify.removeAll();
			notificationService.notice("Please provide title, description and a picture.")
		}
	}

	$scope.removeExerciseInfoToList = function(t,d){

		for(var i=0; i<$scope.tmpNewExercise.infoList.length; i++){
			if($scope.tmpNewExercise.infoList[i].title == t && $scope.tmpNewExercise.infoList[i].description == d){
				$scope.tmpNewExercise.infoList.remove(i,i);
				return;
			}
		}
	}


	$scope.exerciseStatusList = ["AVAILABLE","COMING SOON","INACTIVE"]
	$scope.difficultyLevelList = ["Easy","Moderate","Hard"];
	$scope.technologyList = ["Java","NodeJS",".NET","Python","Ruby","Go Lang"];
	$scope.exerciseTypes = ["TRAINING","CHALLENGE","BOTH"]
	$scope.flagTypes = ["REMEDIATION","EXPLOITATION","SECURE CODING","OTHER"]

	$scope.showOptionalFlagInput = false;
	$scope.showOptionalFlagForm = function(){
		$scope.showOptionalFlagInput = true;
	}

	$scope.showAddExerciseModal = function(){
		$scope.saveFlow = false;
		$('#addNewExerciseModal').modal('show');
	}

	function getStatusCodeFromText(text){
		if(text!=undefined && text!="")
			text = text.toLowerCase();
		else
			return ""
			switch(text){
			case 'available':
				return 0;
			case 'coming soon':
				return 2;
			case 'inactive':
				return 3;
			}
	}	

	$scope.updateExercise = function(){

		if(Object.keys($scope.tmpNewExercise.solutionFile).length==0) {
			PNotify.removeAll();
			notificationService.notice('Please provide a solution file.');
			return;
		}
		if($scope.tmpNewExercise.infoList.length==0){
			PNotify.removeAll();
			notificationService.notice('Please define at list one exercise info.');
			return;
		}
		if($scope.tmpNewExercise.flags.length==0){
			PNotify.removeAll();
			notificationService.notice('Please define at list one exercise flag.');
			return;

		}
		if($scope.tmpNewExercise.difficulty == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s difficulty.');
			return;
		}
		if($scope.tmpNewExercise.status == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s status.');
			return;
		}
		if($scope.tmpNewExercise.technology == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s technology.');
			return;
		}
		if($scope.tmpNewExercise.duration == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s duration.');
			return;
		}
		if($scope.tmpNewExercise.author == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s author.');
			return;
		}
		if($scope.tmpNewExercise.type == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s type.');
			return;
		}
		if($scope.tmpNewExercise.description == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s description.');
			return;
		}
		if($scope.tmpNewExercise.topics == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s topics.');
			return;
		}
		if($scope.tmpNewExercise.title == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s title.');
			return;
		}
		if($scope.tmpNewExercise.trophyTitle == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide trophy\'s title.');
			return;
		}
		if($scope.tmpNewExercise.trophyDescription == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide trophy\'s description.');
			return;
		}

		$scope.tmpNewExercise.status = getStatusCodeFromText($scope.tmpNewExercise.status)
		server.updateExercise($scope.tmpNewExercise);

	}


	$scope.addNewExercise = function(){

		if(Object.keys($scope.tmpNewExercise.solutionFile).length==0) {
			PNotify.removeAll();
			notificationService.notice('Please provide a solution file.');
			return;
		}
		if($scope.tmpNewExercise.infoList.length==0){
			PNotify.removeAll();
			notificationService.notice('Please define at list one exercise info.');
			return;
		}
		if($scope.tmpNewExercise.flags.length==0){
			PNotify.removeAll();
			notificationService.notice('Please define at list one exercise flag.');
			return;
		}
		if($scope.tmpNewExercise.difficulty == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s difficulty.');
			return;
		}
		if($scope.tmpNewExercise.status == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s status.');
			return;
		}
		if($scope.tmpNewExercise.technology == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s technology.');
			return;
		}
		if($scope.tmpNewExercise.duration == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s duration.');
			return;
		}
		if($scope.tmpNewExercise.author == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s author.');
			return;
		}
		if($scope.tmpNewExercise.type == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s type.');
			return;
		}
		if($scope.tmpNewExercise.description == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s description.');
			return;
		}
		if($scope.tmpNewExercise.topics == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s topics.');
			return;
		}
		if($scope.tmpNewExercise.title == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide exercise\'s title.');
			return;
		}
		if($scope.tmpNewExercise.trophyTitle == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide trophy\'s title.');
			return;
		}
		if($scope.tmpNewExercise.trophyDescription == ""){
			PNotify.removeAll();
			notificationService.notice('Please provide trophy\'s description.');
			return;
		}

		$scope.tmpNewExercise.status = getStatusCodeFromText($scope.tmpNewExercise.status)
		server.addExercise($scope.tmpNewExercise);
	}

	$scope.$on('exerciseRemoved:updated', function(event,data) {
		server.getAvailableExercises();
	});


	$scope.loadedExerciseFile = {}
	$scope.showLoadExerciseModal = function(){
		$('#addNewExerciseModal').modal('hide');
		$scope.loadedExerciseFile = null;
		$('#loadExerciseModal').modal('show');
	}
	$scope.loadExercise = function(){
		try{
			if( null == $scope.loadedExerciseFile || undefined == $scope.loadedExerciseFile.data || $scope.loadedExerciseFile.data.length == 0){
				PNotify.removeAll();
				notificationService.notice('Please provide a valid exercise file.');
				$scope.loadedExerciseFile = null;
				$('#loadExerciseModal').modal('hide');
				return;
			}
			$scope.saveFlow = false;

			var loadedJSON = JSON.parse(atob($scope.loadedExerciseFile.data.replace("data:application/json;base64,","")))
			$('#loadExerciseModal').modal('hide');

			$scope.tmpNewExercise.difficulty = loadedJSON.difficulty;
			$scope.tmpNewExercise.status = loadedJSON.status;
			$scope.tmpNewExercise.technology = loadedJSON.technology;
			$scope.tmpNewExercise.duration = loadedJSON.duration;
			$scope.tmpNewExercise.description =loadedJSON.description;
			$scope.tmpNewExercise.topics = loadedJSON.topics;
			$scope.tmpNewExercise.type = loadedJSON.type || "BOTH";
			$scope.tmpNewExercise.author = loadedJSON.author || "RTF";
			$scope.tmpNewExercise.title = loadedJSON.title;
			if(null!=$scope.tmpNewExercise.title)
				$scope.isExerciseNameAvailable();
			$scope.tmpNewExercise.trophyTitle =loadedJSON.trophyTitle;  
			$scope.tmpNewExercise.trophyDescription = loadedJSON.trophyDescription;
			$scope.tmpNewExercise.infoList = loadedJSON.infoList;
			for(var i in $scope.tmpNewExercise.infoList){
				if($scope.tmpNewExercise.infoList.hasOwnProperty(i)){
					var tmpImg = $scope.tmpNewExercise.infoList[i].image;
					$scope.tmpNewExercise.infoList[i].image = {};
					$scope.tmpNewExercise.infoList[i].image.data = tmpImg;
				}
			}
			$scope.tmpNewExercise.flags = loadedJSON.flags;
			$scope.tmpNewExercise.resources = [];
			for(var i in loadedJSON.resources){
				if(loadedJSON.resources.hasOwnProperty(i)){
					var tmpObj = {};
					tmpObj.title = loadedJSON.resources[i].title;
					tmpObj.url = loadedJSON.resources[i].url;
					$scope.tmpNewExercise.resources.push(tmpObj);
				}
			}
			$scope.tmpNewExercise.referenceFile = loadedJSON.referenceFile;
			$scope.tmpNewExercise.solutionFile = loadedJSON.solutionFile;
			if(undefined!=$scope.tmpNewExercise.solutionFile && $scope.tmpNewExercise.solutionFile.name!=undefined)
				$scope.showSolutionFilePicker = false;
			if(undefined!=$scope.tmpNewExercise.referenceFile && $scope.tmpNewExercise.referenceFile.name!=undefined)
				$scope.showReferenceFilePicker = false;
			$scope.loadedExerciseFile = null;

			$('#addNewExerciseModal').modal('show');
		}catch(e){
			PNotify.removeAll();
			notificationService.notice('An error occured, please try again.');
		}
	}

	$scope.editExercise = function(){
		try{
			$scope.saveFlow = true;

			$scope.tmpNewExercise.id = $rootScope.exerciseDetails.id;
			$scope.tmpNewExercise.difficulty = $rootScope.exerciseDetails.difficulty;
			$scope.tmpNewExercise.status = $scope.getExerciseStatusString($rootScope.exerciseDetails.status).toUpperCase();
			$scope.tmpNewExercise.technology = $rootScope.exerciseDetails.technology;
			$scope.tmpNewExercise.duration = $rootScope.exerciseDetails.duration;
			$scope.tmpNewExercise.description =$rootScope.exerciseDetails.description;
			$scope.tmpNewExercise.topics = $rootScope.exerciseDetails.subtitle;
			$scope.tmpNewExercise.type = $rootScope.exerciseDetails.exerciseType;
			$scope.tmpNewExercise.author = $rootScope.exerciseDetails.author;
			$scope.tmpNewExercise.title = $rootScope.exerciseDetails.title;
			$scope.tmpNewExercise.trophyTitle = $rootScope.exerciseDetails.trophy.name;  
			$scope.tmpNewExercise.trophyDescription = $rootScope.exerciseDetails.trophy.description;
			$scope.tmpNewExercise.infoList = $rootScope.exerciseDetails.info;

			var queue = [];
			for(var i in $scope.tmpNewExercise.infoList){
				if($scope.tmpNewExercise.infoList.hasOwnProperty(i)){
					queue.push($scope.tmpNewExercise.infoList[i].id)
				}
			}	
			$scope.tmpNewExercise.flags = $rootScope.exerciseDetails.flags;
			/*for(var i in $scope.tmpNewExercise.flags){
				if($scope.tmpNewExercise.flags.hasOwnProperty(i)){
					for(var j in $scope.tmpNewExercise.flags[i].flagList){
						if($scope.tmpNewExercise.flags[i].flagList.hasOwnProperty(j)){
							try{
								$scope.tmpNewExercise.flags[i].flagList[j].hint = $scope.tmpNewExercise.flags[i].flagList[j].hint.text;
							}catch(e){}
						}
					}
				}
			}*/
			$scope.tmpNewExercise.resources = [];
			for(var i in $rootScope.exerciseDetails.resources){
				if($rootScope.exerciseDetails.resources.hasOwnProperty(i)){
					var tmpObj = {};
					tmpObj.title = i;
					tmpObj.url = $rootScope.exerciseDetails.resources[i];
					$scope.tmpNewExercise.resources.push(tmpObj);
				}
			}
			server.returnPicturesInQueue(queue)
			$('#addNewExerciseModal').modal('show');
		}catch(e){
			PNotify.removeAll();
			notificationService.notice('An error occured, please try again.');
		}
	}

	$scope.$on('infoListPicturesReturned:updated', function(event,data) {
		if(!$scope.saveFlow){
			for(var j in $scope.exportExercise.infoList){
				if($scope.exportExercise.infoList.hasOwnProperty(j)){
					for(var i in $rootScope.exportInfoListImages){
						if($rootScope.exportInfoListImages.hasOwnProperty(i)){
							if($scope.exportExercise.infoList[j].id==$rootScope.exportInfoListImages[i].id){
								$scope.exportExercise.infoList[j].image = $rootScope.exportInfoListImages[i].data;
								break;
							}
						}
					}
				}
			}
		}
		else{
			for(var j in $scope.tmpNewExercise.infoList){
				if($scope.tmpNewExercise.infoList.hasOwnProperty(j)){
					for(var i in $rootScope.exportInfoListImages){
						if($rootScope.exportInfoListImages.hasOwnProperty(i)){
							if($scope.tmpNewExercise.infoList[j].id==$rootScope.exportInfoListImages[i].id){
								$scope.tmpNewExercise.infoList[j].image = {};
								$scope.tmpNewExercise.infoList[j].image.data = $rootScope.exportInfoListImages[i].data;
								break;
							}
						}
					}
				}
			}
		}
		server.returnExerciseReference($rootScope.exerciseDetails.id)
	});

	$scope.$on('exerciseReferenceReturned:updated', function(event,data) {
		var reader = new FileReader();
		reader.readAsDataURL(data.data); 
		reader.onloadend = function() {
			if(!$scope.saveFlow){
				$scope.exportExercise.referenceFile = {}
				$scope.exportExercise.referenceFile.name = data.name;
				$scope.exportExercise.referenceFile.data = reader.result;  
			}
			else{
				$scope.tmpNewExercise.referenceFile = {}
				$scope.tmpNewExercise.referenceFile.name = data.name;
				$scope.tmpNewExercise.referenceFile.data = reader.result;  
				if($scope.tmpNewExercise.referenceFile.data!=undefined)
					$scope.showReferenceFilePicker = false;
				else
					$scope.showReferenceFilePicker = true;
			}
			server.returnExerciseSolutions($rootScope.exerciseDetails.id);
		}
	});

	$scope.$on('exerciseSolutionReturned:updated', function(event,data) {
		var reader = new FileReader();
		reader.readAsDataURL(data.data); 
		reader.onloadend = function() {
			$('.waitLoader').hide();
			if(!$scope.saveFlow){
				$scope.exportExercise.solutionFile = {}
				$scope.exportExercise.solutionFile.name = data.name;
				$scope.exportExercise.solutionFile.data = reader.result;   
				var blob = new Blob([JSON.stringify($scope.exportExercise)], { type:"application/json;" });		
				if (navigator.appVersion.toString().indexOf('.NET') > 0){
					window.navigator.msSaveBlob(blob, "exercise.json");
				}
				else{
					var downloadLink = angular.element('<a></a>');
					downloadLink.attr('href',window.URL.createObjectURL(blob));
					downloadLink.attr('download', "exercise.json");
					downloadLink[0].click();
				}
			}
			else{
				$scope.tmpNewExercise.solutionFile = {}
				$scope.tmpNewExercise.solutionFile.name = data.name;
				$scope.tmpNewExercise.solutionFile.data = reader.result;   
				if($scope.tmpNewExercise.solutionFile.data!=undefined)
					$scope.showSolutionFilePicker = false;
				else
					$scope.showSolutionFilePicker = true;
			}
		}
	});

	$scope.exportCurrentExercise = function(){

		$('.waitLoader').show();
		$scope.saveFlow = false;
		try{
			var exercise = {};
			exercise.difficulty = $rootScope.exerciseDetails.difficulty;
			exercise.status = $scope.getExerciseStatusString($rootScope.exerciseDetails.status).toUpperCase();
			exercise.technology = $rootScope.exerciseDetails.technology;
			exercise.duration = $rootScope.exerciseDetails.duration;
			exercise.description =$rootScope.exerciseDetails.description;
			exercise.topics = $rootScope.exerciseDetails.subtitle;
			exercise.title = $rootScope.exerciseDetails.title;
			exercise.score = $rootScope.exerciseDetails.score;
			exercise.trophyTitle = $rootScope.exerciseDetails.trophy.name;  
			exercise.trophyDescription = $rootScope.exerciseDetails.trophy.description;
			exercise.infoList = $rootScope.exerciseDetails.info;
			var queue = [];
			for(var i in exercise.infoList){
				if(exercise.infoList.hasOwnProperty(i)){
					queue.push(exercise.infoList[i].id)
					delete exercise.infoList[i]["$$hashKey"];
				}
			}
			exercise.flags = $rootScope.exerciseDetails.flags;
			for(var i in exercise.flags){
				if(exercise.flags.hasOwnProperty(i)){
					delete exercise.flags[i]["id"];
					delete exercise.flags[i]["$$hashKey"];
					for(var j in exercise.flags[i].flagList){
						if(exercise.flags[i].flagList.hasOwnProperty(j)){
							try{
								delete exercise.flags[i].flagList[j]["$$hashKey"];
								delete exercise.flags[i].flagList[j]["id"];
								delete exercise.flags[i].flagList[j]["hint"]["id"];
							}catch(e){
								
							}
						}
					}
				}
			}
			exercise.resources = [];
			for(var i in $rootScope.exerciseDetails.resources){
				if($rootScope.exerciseDetails.resources.hasOwnProperty(i)){
					var tmpObj = {};
					tmpObj.title = i;
					tmpObj.url = $rootScope.exerciseDetails.resources[i];
					exercise.resources.push(tmpObj);
				}
			}
			$rootScope.exportInfoListImages = [];

			$scope.exportExercise = exercise;

			server.returnPicturesInQueue(queue)
		}catch(e){
			$('.waitLoader').hide();
			PNotify.removeAll();
			notificationService.notice('An error occured, please try again.');
		}

	}

	$scope.$on('exerciseUpdated:updated', function(event,data) {

		data.res = [];
		for (var property in data.resources) {
			if (data.resources.hasOwnProperty(property)) {
				var tmpObj = {};
				tmpObj.name = property
				tmpObj.url = data.resources[property];
				data.res.push(tmpObj);
			}
		}
		$rootScope.exerciseDetails = data;
		$rootScope.showExerciseDetails = true;
		$rootScope.showExerciseList = false;
		$location.path("available-exercises/details/"+$rootScope.exerciseDetails.id, false);

		server.getAvailableExercises();

		$scope.tmpNewExercise = {}
		$scope.tmpNewExercise.id = ""
			$scope.tmpNewExercise.author = "";
		$scope.tmpNewExercise.type = ""
			$scope.tmpNewExercise.referenceFile = {};
		$scope.tmpNewExercise.solutionFile = {};
		$scope.tmpNewExercise.difficulty = "";
		$scope.tmpNewExercise.status = "";
		$scope.tmpNewExercise.technology = "";
		$scope.tmpNewExercise.duration = "";
		$scope.tmpNewExercise.description = "";
		$scope.tmpNewExercise.flags = []
		$scope.tmpNewExercise.infoList = [];
		$scope.tmpNewExercise.resources = []
		$scope.tmpNewExercise.topics = "";
		$scope.tmpNewExercise.title = "";
		$scope.tmpNewExercise.trophyTitle = ""; 
		$scope.tmpNewExercise.trophyDescription = ""; 

		$scope.newExerciseFlagList = true;

		$scope.tmpResourceUrl = "";
		$scope.tmpResourceTitle = "";
		$scope.tmpRemFlag = {};
		$scope.tmpRemFlag.selfCheckName = "";
		$scope.tmpRemFlag.selfCheckAvailable = false;
		$scope.tmpRemFlag.hint = "";
		$scope.tmpRemFlag.type = "";
		$scope.tmpRemFlag.hintAvailable  = false;
		$scope.tmpRemFlag.instructions = "";
		$scope.tmpExpFlag = {};
		$scope.tmpExpFlag.selfCheckName = "";
		$scope.tmpExpFlag.selfCheckAvailable = false;
		$scope.tmpExpFlag.hint = "";
		$scope.tmpExpFlag.hintAvailable  = false;
		$scope.tmpExpFlag.instructions = "";
		$scope.tmpExpFlag.hintScoreReduction = "";
		$scope.tmpExpFlag.maxScore = "";
		$scope.tmpExpFlag.type = "";
		$scope.tmpInfoDescription = "";
		$scope.tmpInfoFile = {};
		$scope.tmpInfoTitle = "";

		$scope.showReferenceFilePicker = true;
		$scope.showSolutionFilePicker = true;
		$scope.saveFlow = false;

		$('#addNewExerciseModal').modal('hide');

	});

	$scope.clearExerciseModal = function(){

		$scope.tmpNewExercise = {}
		$scope.tmpNewExercise.referenceFile = {};
		$scope.tmpNewExercise.solutionFile = {};
		$scope.tmpNewExercise.difficulty = "";
		$scope.tmpNewExercise.status = "";
		$scope.tmpNewExercise.technology = "";
		$scope.tmpNewExercise.duration = "";
		$scope.tmpNewExercise.description = "";
		$scope.tmpNewExercise.flags = []
		$scope.tmpNewExercise.infoList = [];
		$scope.tmpNewExercise.resources = []
		$scope.tmpNewExercise.topics = "";
		$scope.tmpNewExercise.title = "";
		$scope.tmpNewExercise.trophyTitle = ""; 
		$scope.tmpNewExercise.trophyDescription = ""; 

		$scope.newExerciseFlagList = true;

		$scope.tmpResourceUrl = "";
		$scope.tmpResourceTitle = "";
		$scope.tmpRemFlag = {};
		$scope.tmpRemFlag.selfCheckName = "";
		$scope.tmpRemFlag.selfCheckAvailable = false;
		$scope.tmpRemFlag.hint = "";
		$scope.tmpRemFlag.hintAvailable  = false;
		$scope.tmpRemFlag.instructions = "";
		$scope.tmpRemFlag.type = "";
		$scope.tmpExpFlag = {};
		$scope.tmpExpFlag.selfCheckName = "";
		$scope.tmpExpFlag.selfCheckAvailable = false;
		$scope.tmpExpFlag.hint = "";
		$scope.tmpExpFlag.hintAvailable  = false;
		$scope.tmpExpFlag.instructions = "";
		$scope.tmpExpFlag.hintScoreReduction = "";
		$scope.tmpExpFlag.maxScore = "";
		$scope.tmpExpFlag.type = "";
		$scope.tmpInfoDescription = "";
		$scope.tmpInfoFile = {};
		$scope.tmpInfoTitle = "";

		$scope.showReferenceFilePicker = true;
		$scope.showSolutionFilePicker = true;
		$scope.saveFlow = false;

	}


	$scope.$on('exerciseAdded:updated', function(event,data) {

		$scope.tmpNewExercise = {}
		$scope.tmpNewExercise.referenceFile = {};
		$scope.tmpNewExercise.solutionFile = {};
		$scope.tmpNewExercise.difficulty = "";
		$scope.tmpNewExercise.status = "";
		$scope.tmpNewExercise.technology = "";
		$scope.tmpNewExercise.duration = "";
		$scope.tmpNewExercise.description = "";
		$scope.tmpNewExercise.flags = []
		$scope.tmpNewExercise.infoList = [];
		$scope.tmpNewExercise.resources = []
		$scope.tmpNewExercise.topics = "";
		$scope.tmpNewExercise.title = "";
		$scope.tmpNewExercise.trophyTitle = ""; 
		$scope.tmpNewExercise.trophyDescription = ""; 

		$scope.newExerciseFlagList = true;

		$scope.tmpResourceUrl = "";
		$scope.tmpResourceTitle = "";
		$scope.tmpRemFlag = {};
		$scope.tmpRemFlag.selfCheckName = "";
		$scope.tmpRemFlag.selfCheckAvailable = false;
		$scope.tmpRemFlag.hint = "";
		$scope.tmpRemFlag.hintAvailable  = false;
		$scope.tmpRemFlag.instructions = "";
		$scope.tmpRemFlag.type = "";
		$scope.tmpExpFlag = {};
		$scope.tmpExpFlag.selfCheckName = "";
		$scope.tmpExpFlag.selfCheckAvailable = false;
		$scope.tmpExpFlag.hint = "";
		$scope.tmpExpFlag.hintAvailable  = false;
		$scope.tmpExpFlag.instructions = "";
		$scope.tmpExpFlag.hintScoreReduction = "";
		$scope.tmpExpFlag.maxScore = "";
		$scope.tmpExpFlag.type = "";
		$scope.tmpInfoDescription = "";
		$scope.tmpInfoFile = {};
		$scope.tmpInfoTitle = "";

		$scope.showReferenceFilePicker = true;
		$scope.showSolutionFilePicker = true;
		$scope.saveFlow = false;

		$('#addNewExerciseModal').modal('hide');
		server.getAvailableExercises();

	});


	$scope.addToNewExerciseResources = function(){
		var tmpObj = {};
		tmpObj.title = $scope.tmpResourceTitle;
		tmpObj.url = $scope.tmpResourceUrl;
		if(tmpObj.title!="" && tmpObj.url!=""){
			$scope.tmpNewExercise.resources.push(tmpObj);
			$scope.tmpResourceUrl = "";
			$scope.tmpResourceTitle = "";
		}
	}

	$scope.removeFromNewExerciseResources = function(t,u){
		for(var i=0; i<$scope.tmpNewExercise.resources.length; i++){
			if($scope.tmpNewExercise.resources[i].title == t && $scope.tmpNewExercise.resources[i].url == u){
				$scope.tmpNewExercise.resources.remove(i,i);
				return;
			}
		}
	}

	$scope.removeNewFlag = function(t,c){
		for(var i=0; i<$scope.tmpNewExercise.flags.length; i++){
			if($scope.tmpNewExercise.flags[i].title == t && $scope.tmpNewExercise.flags[i].category == c){
				$scope.tmpNewExercise.flags.remove(i,i);
				return;
			}
		}
	}

	$scope.addNewFlag = function(){
		var obj1 = {};
		var obj2 = {};
		var obj = {};

		obj.title = $scope.tmpFlagTitle;
		obj.category = $scope.tmpFlagCategory;
		obj.flagList = [];
		obj1.selfCheckName = $scope.tmpRemFlag.selfCheckName;
		obj1.selfCheckAvailable = $scope.tmpRemFlag.selfCheckAvailable;
		obj1.hint = $scope.tmpRemFlag.hint;
		obj1.hintAvailable = $scope.tmpRemFlag.hintAvailable;
		obj1.instructions = $scope.tmpRemFlag.instructions;
		obj1.type = $scope.tmpRemFlag.type;
		obj2.optional = true;

		obj2.selfCheckName = $scope.tmpExpFlag.selfCheckName;
		obj2.selfCheckAvailable = $scope.tmpExpFlag.selfCheckAvailable;
		obj2.hint = $scope.tmpExpFlag.hint;
		obj2.hintAvailable = $scope.tmpExpFlag.hintAvailable;
		obj2.hintScoreReduction = $scope.tmpExpFlag.hintScoreReduction;
		obj2.instructions = $scope.tmpExpFlag.instructions;
		obj2.maxScore = $scope.tmpExpFlag.maxScore;
		obj2.type = $scope.tmpExpFlag.type;
		obj2.optional = false;


		obj.flagList.push(obj1);
		obj.flagList.push(obj2);

		$scope.tmpNewExercise.flags.push(obj);

		$scope.newExerciseFlagList = true;

		$scope.tmpRemFlag.selfCheckName = "";
		$scope.tmpRemFlag.selfCheckAvailable = false;
		$scope.tmpRemFlag.hint = "";
		$scope.tmpRemFlag.hintAvailable  = false;
		$scope.tmpRemFlag.instructions = "";
		$scope.tmpRemFlag.type = "";
		$scope.tmpExpFlag.selfCheckName = "";
		$scope.tmpExpFlag.maxScore = "";
		$scope.tmpExpFlag.selfCheckAvailable = false;
		$scope.tmpExpFlag.hint = "";
		$scope.tmpExpFlag.hintAvailable  = false;
		$scope.tmpExpFlag.hintScoreReduction = "";
		$scope.tmpExpFlag.type = "";
		$scope.tmpExpFlag.instructions = "";
		$scope.tmpFlagTitle = "";
		$scope.tmpFlagCategory = "";

	}


	$scope.addNewFlagDialog = function(){
		if($scope.newExerciseFlagList){
			$scope.newExerciseFlagList = false;
		}
		else{
			$scope.newExerciseFlagList = true;
			$scope.showOptionalFlagInput = false;
		}
	}


	$scope.getColorForScore = function(score){
		if(score<=20)
			return "#7693c1f0";
		if(score<=50)
			return "#9e4a72de";
		if(score<=75)
			return "#7676c1f0";
		if(score<=100)
			return "#6a6a7dde";
		if(score<=125)
			return "#381f08de";
		return "#bd6c22de";
	}

	$scope.newTaskDefinition = {};

	$scope.newTaskDefinition.exerciseId = "";
	$scope.newTaskDefinition.region = "";
	$scope.newTaskDefinition.softMemory = "";
	$scope.newTaskDefinition.hardMemory = "";
	$scope.newTaskDefinition.status = true;
	$scope.newTaskDefinition.imageUrl = "";
	$scope.newTaskDefinition.containerName = "";
	$scope.newTaskDefinition.taskDefinitionName = "";

	$scope.$on('exerciseInRegionEnabled:updated', function(event,data) {
		server.getRegionsForExercise($rootScope.exerciseDetails.id);
	});
	$scope.$on('exerciseInRegionDisabled:updated', function(event,data) {
		server.getRegionsForExercise($rootScope.exerciseDetails.id);
	});
	$scope.$on('exerciseInRegionRemoved:updated', function(event,data) {
		server.getRegionsForExercise($rootScope.exerciseDetails.id);
	});

	$scope.$on('taskDefinitionAdded:updated', function(event,data) {
		$scope.newTaskDefinition.exerciseId = "";
		$scope.newTaskDefinition.region = "";
		$scope.newTaskDefinition.softMemory = "";
		$scope.newTaskDefinition.hardMemory = "";
		$scope.newTaskDefinition.status = true;
		$scope.newTaskDefinition.imageUrl = "";
		$scope.newTaskDefinition.containerName = "";
		$scope.newTaskDefinition.taskDefinitionName = "";
		server.getRegionsForExercise($rootScope.exerciseDetails.id);
	});
	$scope.addTaskDefinition = function(){
		$scope.newTaskDefinition.exerciseId = $rootScope.exerciseDetails.id;
		server.addTaskDefinition($scope.newTaskDefinition);
	}

	$scope.getExerciseDetails = function(exId){
		server.getExerciseDetails(exId);
		server.getRegionsForExercise(exId);
	}

	$scope.manageTaskDefinitions = function(){
		$('#addRemoveRegionsModal').modal('show');
	}

	$scope.fillRevisionForm = function(data){
		$scope.newTaskDefinition.exerciseId = $rootScope.exerciseDetails.id;
		var reg = {};
		reg.name = data.region;
		reg.code = regionCodeFromName(reg.name);
		$scope.newTaskDefinition.region = reg.code;
		$scope.newTaskDefinition.softMemory = data.softMemoryLimit;
		$scope.newTaskDefinition.hardMemory = data.hardMemoryLimit;
		$scope.newTaskDefinition.status = true;
		$scope.newTaskDefinition.imageUrl = data.repositoryImageUrl;
		$scope.newTaskDefinition.containerName = data.containerName;
		$scope.newTaskDefinition.taskDefinitionName = data.name.substr(0,data.name.indexOf(':'));
	}



	$scope.clearTaskDefinitionForm = function(){
		$scope.newTaskDefinition.exerciseId = "";
		$scope.newTaskDefinition.region = "";
		$scope.newTaskDefinition.softMemory = "";
		$scope.newTaskDefinition.hardMemory = "";
		$scope.newTaskDefinition.status = true;
		$scope.newTaskDefinition.imageUrl = "";
		$scope.newTaskDefinition.containerName = "";
		$scope.newTaskDefinition.taskDefinitionName = "";
	}
	$scope.enableExerciseForOrg = function(idExercise,idOrg){
		server.enableExerciseForOrg(idExercise,idOrg);
	}
	$scope.disableExerciseForOrg = function(idExercise,idOrg){
		server.disableExerciseForOrg(idExercise,idOrg);
	}

	$scope.$on('disableExerciseForOrg:updated', function(event,data) {
		server.getAvailableExercises();
		PNotify.removeAll();
		notificationService.success("Exercise successfully disabled.")
	});
	$scope.$on('enableExerciseForOrg:updated', function(event,data) {
		server.getAvailableExercises();
		PNotify.removeAll();
		notificationService.success("Exercise successfully enabled.")
	});

	$scope.regionNameFromCode = function(regionCode){
		var region = "";
		switch(regionCode) {
		case "EU_WEST_1":
			region = "EU (Ireland)";
			break;
		case "US_EAST_1":
			region = "US East (N. Virginia)"
				break;
		case "AP_SOUTH_1":
			region = "Asia Pacific (Mumbai)"
				break;
		case "AP_SOUTHEAST_1":
			region = "Asia Pacific (Singapore)"
				break;
		case "US_EAST_2":
			region = "US East (Ohio)";
			break;
		case "US_WEST_2":
			region = "US West (Oregon)";
			break;
		case "US_WEST_1":
			region = "US West (N. California)";
			break;
		case "CA_CENTRAL_1":
			region = "Canada (Central)";
			break;
		case "EU_CENTRAL_1":
			region = "EU (Frankfurt)";
			break;
		case "EU_WEST_2":
			region = "EU (London)";
			break;
		case "EU_WEST_3":
			region = "EU (Paris)";
			break;
		case "AP_NORTHEAST_2":
			region = "Asia Pacific (Seoul)";
			break;
		case "AP_NORTHEAST_1":
			region = "Asia Pacific (Tokyo)";
			break;
		case "AP_SOUTHEAST_2":
			region = "Asia Pacific (Sydney)";
			break;
		case "SA_EAST_1":
			region = "South America (São Paulo)";
			break;
		default:
			break;
		}
		return region;
	}

	function regionCodeFromName(regionName){
		switch(regionName) {
		case "EU (Ireland)":
			region = "EU_WEST_1";
			break;
		case "US East (N. Virginia)":
			region = "US_EAST_1"
				break;
		case "Asia Pacific (Mumbai)":
			region = "AP_SOUTH_1"
				break;
		case "Asia Pacific (Singapore)":
			region = "AP_SOUTHEAST_1"
				break;
		case "US East (Ohio)":
			region = "US_EAST_2";
			break;
		case "US West (Oregon)":
			region = "US_WEST_2";
			break;
		case "US West (N. California)":
			region = "US_WEST_1";
			break;
		case "Canada (Central)":
			region = "CA_CENTRAL_1";
			break;
		case "EU (Frankfurt)":
			region = "EU_CENTRAL_1";
			break;
		case "EU (London)":
			region = "EU_WEST_2";
			break;
		case "EU (Paris)":
			region = "EU_WEST_3";
			break;
		case "Asia Pacific (Seoul)":
			region = "AP_NORTHEAST_2";
			break;
		case "Asia Pacific (Tokyo)":
			region = "AP_NORTHEAST_1";
			break;
		case "Asia Pacific (Sydney)":
			region = "AP_SOUTHEAST_2";
			break;
		case "South America (São Paulo)":
			region = "SA_EAST_1";
			break;
		default:
			break;
		}
		return region;
	}

	$scope.$on('exerciseRegions:updated', function(event,data) {
		if(data!=null){
			$scope.availableRegions = [];
			for(var j in data){
				if(!Number.isInteger(parseInt(j)))
					continue;

				var region = "Unavailable";

				switch(data[j].region) {
				case "EU_WEST_1":
					region = "EU (Ireland)";
					break;
				case "US_EAST_1":
					region = "US East (N. Virginia)"
						break;
				case "AP_SOUTH_1":
					region = "Asia Pacific (Mumbai)"
						break;
				case "AP_SOUTHEAST_1":
					region = "Asia Pacific (Singapore)"
						break;
				case "US_EAST_2":
					region = "US East (Ohio)";
					break;
				case "US_WEST_2":
					region = "US West (Oregon)";
					break;
				case "US_WEST_1":
					region = "US West (N. California)";
					break;
				case "CA_CENTRAL_1":
					region = "Canada (Central)";
					break;
				case "EU_CENTRAL_1":
					region = "EU (Frankfurt)";
					break;
				case "EU_WEST_2":
					region = "EU (London)";
					break;
				case "EU_WEST_3":
					region = "EU (Paris)";
					break;
				case "AP_NORTHEAST_2":
					region = "Asia Pacific (Seoul)";
					break;
				case "AP_NORTHEAST_1":
					region = "Asia Pacific (Tokyo)";
					break;
				case "AP_SOUTHEAST_2":
					region = "Asia Pacific (Sydney)";
					break;
				case "SA_EAST_1":
					region = "South America (São Paulo)";
					break;
				default:
					break;
				}
				obj = {}
				obj.containerName = data[j].taskDefinition.containerName;
				obj.taskId = data[j].taskDefinition.id;
				obj.hardMemoryLimit = data[j].taskDefinition.hardMemoryLimit;
				obj.softMemoryLimit = data[j].taskDefinition.softMemoryLimit;
				obj.repositoryImageUrl = data[j].taskDefinition.repositoryImageUrl;
				obj.region = region;
				obj.updateDate = data[j].taskDefinition.updateDate;
				obj.name = data[j].taskDefinition.taskDefinitionName;
				obj.active = data[j].active;
				$scope.availableRegions.push(obj);
			}
		}
	});
	$scope.downloadAPIReference = function(id){
		server.downloadExerciseReference(id);
	}
	$scope.downloadSolutions = function(id){
		server.downloadExerciseSolutions(id);
	}

	$rootScope.exerciseDetails = [];
	$scope.$on('exerciseDetails:updated', function(event,data) {
		data.res = [];
		for (var property in data.resources) {
			if (data.resources.hasOwnProperty(property)) {
				var tmpObj = {};
				tmpObj.name = property
				tmpObj.url = data.resources[property];
				data.res.push(tmpObj);
			}
		}
		$rootScope.exerciseDetails = data;
		$rootScope.showExerciseDetails = true;
		$rootScope.showExerciseList = false;
		$location.path("available-exercises/details/"+$rootScope.exerciseDetails.id, false);
	});

	$scope.disableExerciseInRegion = function(exerciseId, taskId){
		server.disableExerciseInRegion(exerciseId, taskId);
	}
	$scope.enableExerciseInRegion = function(exerciseId, taskId){
		server.enableExerciseInRegion(exerciseId, taskId);
	}
	$scope.removeExerciseInRegion = function(exerciseId, taskId){
		server.removeExerciseInRegion(exerciseId, taskId);
	}
	var tmpExerciseToRemove = -1;
	$scope.removeExerciseModal = function(id,name){
		tmpExerciseToRemove = id;
		$scope.tmpExerciseToBeRemovedName = name
		$('#removeExerciseModal').modal('show');
	}
	$scope.removeExercise = function(){
		server.removeExercise(tmpExerciseToRemove);
		tmpExerciseToRemove = -1;
		$('#removeExerciseModal').modal('hide');
	}

	$scope.backToList = function(){
		window.history.go(-1);
	}


	$scope.getExerciseStatusString = function(status){
		switch(status){
		case "0":
			return "Available";
			break;
		case "2":
			return "Coming Soon";
			break;
		case "3":
			return "Inactive";
			break;
		default:
			return "N/A";
		}
	}


	$scope.updateAvailableExercisesFilteredList = function() {
		$scope.filteredTeamsList = $filter("filter")($scope.masterAvailableExercisesList, $scope.queryAvailableExercises);
	};
	$scope.availableExercisestableconfig = {
			itemsPerPage: 20,
			fillLastPage: false
	}
	$scope.$on('availableExercises:updated', function(event,data) {
		$scope.masterAvailableExercisesList = data.exercises;
		$scope.exercisesForOrgs = data.orgs;
		$scope.filteredAvailableExercisesList = $scope.masterAvailableExercisesList;
	});

	$scope.getExerciseEnabledForOrgText = function(orgId,exId){
		if(undefined == orgId || undefined == exId)
			return false;
		for(var i in $scope.exercisesForOrgs){
			if ($scope.exercisesForOrgs[i].hasOwnProperty("organization") && $scope.exercisesForOrgs[i].organization.id==orgId){
				for(var j in $scope.exercisesForOrgs[i].exercises){
					if($scope.exercisesForOrgs[i].exercises[j].id==exId){
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

}]);

rtf.controller('teams',['$scope','server','$rootScope','$location','$filter','notificationService',function($scope,server,$rootScope,$location,$filter,notificationService){
	$rootScope.showTeamMembers = false;
	$rootScope.showTeamList = true;
	$rootScope.selectedTeam = "";
	$scope.selectedTeamManagers = [];
	$scope.filteredTeamsList = [];
	$scope.masterTeamsList = [];
	$scope.masterMembersList = [];
	$scope.filteredMembersList = [];

	$scope.userObj = [];
	$scope.selectedUsersList = [];
	$scope.userCheckToggle=function(s){
		if($scope.selectedUsersList.indexOf(s)<0){
			$scope.selectedUsersList.push(s);
		}
		else{
			var idx = $scope.selectedUsersList.indexOf(s);
			$scope.selectedUsersList.remove(idx,idx);
		}
	}

	function regionCodeFromName(regionName){
		var region = "";
		switch(regionName) {
		case "EU (Ireland)":
			region = "EU_WEST_1";
			break;
		case "US East (N. Virginia)":
			region = "US_EAST_1"
				break;
		case "Asia Pacific (Mumbai)":
			region = "AP_SOUTH_1"
				break;
		case "Asia Pacific (Singapore)":
			region = "AP_SOUTHEAST_1"
				break;
		case "US East (Ohio)":
			region = "US_EAST_2";
			break;
		case "US West (Oregon)":
			region = "US_WEST_2";
			break;
		case "US West (N. California)":
			region = "US_WEST_1";
			break;
		case "Canada (Central)":
			region = "CA_CENTRAL_1";
			break;
		case "EU (Frankfurt)":
			region = "EU_CENTRAL_1";
			break;
		case "EU (London)":
			region = "EU_WEST_2";
			break;
		case "EU (Paris)":
			region = "EU_WEST_3";
			break;
		case "Asia Pacific (Seoul)":
			region = "AP_NORTHEAST_2";
			break;
		case "Asia Pacific (Tokyo)":
			region = "AP_NORTHEAST_1";
			break;
		case "Asia Pacific (Sydney)":
			region = "AP_SOUTHEAST_2";
			break;
		case "South America (São Paulo)":
			region = "SA_EAST_1";
			break;
		default:
			break;
		}
		return region;
	}

	//addusertoteam
	$scope.addToTeamModal = function(){
		$('#addUsersToTeamModal').modal('show');
		server.getAvailableUsersForTeam($rootScope.selectedTeam);
	}

	$scope.$on('availableUsersTeam:updated', function(event,data) {
		$scope.availableUsersList = data;
	})

	$scope.renameTeam = function(teamId){
		server.renameTeam(teamId,$scope.renamedTeamName);
		$scope.renamedTeamName = "";
	}
	$scope.$on('teamRenamed:updated', function(event,data) {
		$('#renameTeamModal').modal('hide');
		PNotify.removeAll();
		notificationService.success('Team renamed.');
		server.getTeams();
	})
	$scope.teamToRename = {};
	$scope.renameTeamModal = function(id,name){
		$scope.teamToRename = {};
		$scope.teamToRename.id = id;
		$scope.teamToRename.name = name;
		$('#renameTeamModal').modal('show');
	}

	$scope.addUsersToTeam = function(){
		server.addUsersToTeam($scope.selectedUsersList, $rootScope.selectedTeam);
		$scope.selectedUsersList = [];
	}
	$scope.$on('usersAddedToTeam:updated', function(event,data) {
		if(data.result!="error"){
			$scope.getTeamMembers($rootScope.selectedTeam);
			server.getChallenges();
			if($scope.user.r != 3){
				server.getUsers();
				server.getGlobalStats([]);
			}
			if($scope.user.r <= 3){
				server.getPendingReviews();
				server.getCompletedReviews();
				server.getAvailableExercises();
				server.getRunningExercises();
			}	
			$('#addUsersToTeamModal').modal('hide');
		}

	})

	//add team
	$scope.user = server.user;
	$scope.newTeamName = "";
	$scope.newTeamOrganization = "";

	$scope.removeFromTeam = function(teamId, username){
		server.removeFromTeam(teamId, username);
		$('#removeFromTeamModal').modal('hide');
	}
	$scope.userToRemove = {};
	$scope.removeFromTeamModal = function(team, username){
		$scope.userToRemove = {}
		$scope.userToRemove.teamName = team.name;
		$scope.userToRemove.teamId = team.id;
		$scope.userToRemove.username = username;
		$('#removeFromTeamModal').modal('show');
	}
	$scope.$on('deletedFromTeam:updated', function(event,data) {
		if(data.result=="success"){
			if($rootScope.visibility.teams)
				$scope.getTeamMembers($rootScope.selectedTeam)

				server.getChallenges();
			if($scope.user.r != 3){
				server.getUsers();
				server.getGlobalStats([]);
			}
			if($scope.user.r <= 3){
				server.getPendingReviews();
				server.getCompletedReviews();
				server.getAvailableExercises();
				server.getRunningExercises();
			}	
		}
	})

	$scope.removeTeamManagerModal = function(teamId, username){
		$scope.userToRemove= {};
		$scope.userToRemove.teamId = teamId;
		$scope.userToRemove.username = username;
		$('#deleteTeamManagerModal').modal('show');
	}
	$scope.removeTeamManager = function(teamId, username){
		$('#deleteTeamManagerModal').modal('hide');
		server.removeTeamManager(teamId, username)
	}
	$scope.$on('removeTeamManager:updated', function(event,data) {
		if(data.result=="success")
			server.getTeamDetails($rootScope.selectedTeam);
	})

	$scope.makeTeamManager = function(teamId, username){
		server.makeTeamManager(teamId, username)
	}
	$scope.$on('makeTeamManager:updated', function(event,data) {
		if(data.result=="success")
			server.getTeamDetails($rootScope.selectedTeam);
	})
	$scope.teamToDelete = {};
	$scope.deleteTeamModal = function(teamId,teamName){
		$scope.teamToDelete = {};
		$scope.teamToDelete.id = teamId;
		$scope.teamToDelete.name = teamName;
		$('#deleteTeamModal').modal('show');
	}
	$scope.deleteTeam = function(teamId){
		server.deleteTeam(teamId);
		$('#deleteTeamModal').modal('hide');
	}
	$scope.saveNewTeam = function(){
		if($scope.newTeamOrganization!="" && $scope.newTeamName!=""){
			server.addTeam($scope.newTeamName,$scope.newTeamOrganization);
			$('#newTeamModal').modal('hide');
			$scope.newTeamName = "";
			$scope.newTeamOrganization = "";
		}
	}
	$scope.$on('addTeam:updated', function(event,data) {
		if(data.result=="success")
			server.getTeams();
	})
	$scope.$on('deletedTeam:updated', function(event,data) {
		if(data.result=="success")
			server.getTeams();
	})
	$scope.$on('teamNameAvailable:updated', function(event,data) {
		$scope.teamNameAvailable = data.result;
	});
	$scope.teamNameAvailable = true;

	$scope.isRenamedTeamNameAvailable = function(){
		if($scope.renamedTeamName != "" )
			server.isTeamNameAvailable($scope.renamedTeamName);
	}
	$scope.isTeamNameAvailable = function(){
		if($scope.newTeamName != "" )
			server.isTeamNameAvailable($scope.newTeamName);
	}

	$scope.addNewTeamModal = function(){
		$('#newTeamModal').modal('show');
	}
	$scope.remediatedPerIssue = {}
	$scope.remediatedPerIssue.data = [];
	$scope.remediatedPerIssue.labels = [];
	$scope.remediatedPerCategory = {}
	$scope.remediatedPerCategory.data = [];
	$scope.remediatedPerCategory.labels = [];
	$scope.remediationRatePerIssue = [];
	$scope.remediationRatePerCategory = [];
	$scope.scorePerExercises = {};
	$scope.options = {}
	$scope.options.animation = false;
	$scope.options.layout = {
			padding: {
				left: 0,
				right: 0,
				top: 0,
				bottom: 0
			}
	}
	$scope.options.pieceLabel = {
			// render 'label', 'value', 'percentage' or custom function, default is 'percentage'
			render: 'percentage',
			// precision for percentage, default is 0
			precision: 0,
			// identifies whether or not labels of value 0 are displayed, default is false
			showZero: false,
			// font size, default is defaultFontSize
			fontSize: 12,
			// font color, can be color array for each data, default is defaultFontColor
			fontColor: '#FFF',
			// font style, default is defaultFontStyle
			fontStyle: 'normal',
			// font family, default is defaultFontFamily
			fontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
			// draw label in arc, default is false
			arc: false,
			// position to draw label, available value is 'default', 'border' and 'outside'
			// default is 'default'
			position: 'border',
			// draw label even it's overlap, default is false
			overlap: true
	}
	$scope.options.legend = {
			display: true,
			position: 'bottom',
			labels: {
				fontColor: "#000",
				fontSize: 10
			}
	};
	$scope.options.title = {
			text : "",
			display:true,
			position: 'bottom'

	}
	$scope.radarOptions = {}
	$scope.radarOptions.animation = false;
	$scope.radarOptions.title = {
			text : "",
			display:true,
			position: 'top'

	}
	$scope.radarOptions.legend = {
			display: true,
			position: 'bottom',
			labels: {
				fontColor: "#000",
				fontSize: 10
			}
	};

	$scope.updateTeamsFilteredList = function() {
		$scope.filteredTeamsList = $filter("filter")($scope.masterTeamsList, $scope.queryTeams);
	};


	$scope.updateMembersFilteredList = function() {
		$scope.filteredMembersList = $filter("filter")($scope.masterMembersList, $scope.queryMembers);
	};

	$scope.teamstableconfig = {
			itemsPerPage: 20,
			fillLastPage: false
	}
	$scope.$on('teamsList:updated', function(event,data) {
		$scope.masterTeamsList = data;
		$scope.filteredTeamsList = $scope.masterTeamsList;
	});
	$scope.isTeamManager = function(idUser){
		return $scope.selectedTeamManagers.indexOf(idUser)>=0;
	}
	$scope.displayTeamList = function(){
		window.history.go(-1);
	}
	$scope.teamMembersData = [];
	$scope.getTeamMembers = function(name){
		$location.path("teams/details/"+name, false);
		$rootScope.selectedTeam = name;
		server.getTeamMembers(name);
		server.getTeamStats(name);
		server.getTeamDetails(name);
	};
	$scope.$on('teamDetails:updated', function(event,data) {
		$scope.selectedTeamManagers = [];
		for(var i=0; i<data.managers.length;i++){
			$scope.selectedTeamManagers.push(data.managers[i]['idUser']);
		}
	});
	$scope.$on('statsTeam:updated', function(event,data) {
		$scope.remediationRatePerIssue = [];
		for (var property in data.issuesRemediationRate) {
			if (data.issuesRemediationRate.hasOwnProperty(property) && 
					Object.keys(data.issuesRemediationRate[property]).length>0) {

				var obj = {};
				obj.options = cloneObj($scope.options);
				obj.options.title.text = property;
				obj.data = [];
				obj.labels = ["Not Vulnerable", "Vulnerable", "Not Addressed", "Broken Functionality"];
				for(var l=0;l<obj.labels.length;l++){
					var tmpStatus = obj.labels[l].toUpperCase().replace(" ","_");
					var tmpValue = data.issuesRemediationRate[property][tmpStatus]
					if(undefined==tmpValue){
						tmpValue = 0;
					}
					obj.data.push(tmpValue);
				}
				$scope.remediationRatePerIssue.push(obj);
			}
		}
		$scope.remediatedPerIssue.data = [[]];
		$scope.remediatedPerIssue.labels = [];
		for(var j in $scope.remediationRatePerIssue){
			if ($scope.remediationRatePerIssue.hasOwnProperty(j)){
				var tmpRem = $scope.remediationRatePerIssue[j].data[0]
				var tmpTot = $scope.remediationRatePerIssue[j].data.reduce(getSum);
				var tmpPercentage =  Math.floor((tmpRem * 100) / tmpTot);
				var tmpName = $scope.remediationRatePerIssue[j].options.title.text
				$scope.remediatedPerIssue.labels.push(tmpName);
				$scope.remediatedPerIssue.data[0].push(tmpPercentage);
			}
		}
		$scope.remediatedPerIssue.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerIssue.options.title.display = false;
		$scope.remediatedPerIssue.series = ["Remediated (%)"];

		$scope.remediationRatePerCategory = [];
		for (var property in data.categoriesRemediationRate) {
			if (data.categoriesRemediationRate.hasOwnProperty(property) && 
					Object.keys(data.categoriesRemediationRate[property]).length>0) {
				var obj = {};
				obj.options = cloneObj($scope.options);
				obj.options.title.text = property;
				obj.data = [];
				obj.labels = ["Not Vulnerable", "Vulnerable", "Not Addressed", "Broken Functionality"];
				for(var l=0;l<obj.labels.length;l++){
					var tmpStatus = obj.labels[l].toUpperCase().replace(" ","_");
					var tmpValue = data.categoriesRemediationRate[property][tmpStatus]
					if(undefined==tmpValue){
						tmpValue = 0;
					}
					obj.data.push(tmpValue);
				}
				$scope.remediationRatePerCategory.push(obj);
			}
		}
		$scope.remediatedPerCategory.data = [[],[],[]];
		$scope.remediatedPerCategory.labels = [];
		for(var j in $scope.remediationRatePerCategory){
			if ($scope.remediationRatePerCategory.hasOwnProperty(j)){
				var tmpRem = $scope.remediationRatePerCategory[j].data[0]
				var tmpTot = $scope.remediationRatePerCategory[j].data.reduce(getSum);
				var tmpPercentage =  Math.floor((tmpRem * 100) / tmpTot);
				var tmpName = $scope.remediationRatePerCategory[j].options.title.text;
				$scope.remediatedPerCategory.labels.push(tmpName);
				$scope.remediatedPerCategory.data[0].push(tmpPercentage);
				$scope.remediatedPerCategory.data[1].push(Math.ceil(data.totalMinutesPerIssueCategory[regionCodeFromName(tmpName)]/60));
				$scope.remediatedPerCategory.data[2].push(data.avgMinutesPerIssueCategory[regionCodeFromName(tmpName)]);
			}
		}
		$scope.remediatedPerCategory.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerCategory.options.title.display = false;
		$scope.remediatedPerCategory.series = ["Remediated (%)","Total Time (hours)","Avg Time (minutes)"];

	});
	$scope.$on('teamMembers:updated', function(event,data) {
		$scope.masterMembersList = data;
		$scope.filteredMembersList = $scope.masterMembersList;
		$rootScope.showTeamList = false;
		$rootScope.showTeamMembers = true;
	});
}])
rtf.controller('review',['$scope','server','$rootScope','$filter','$location',function($scope,server,$rootScope,$filter,$location){
	$scope.user = server.user;
	$scope.showCodeDiff = false;
	$scope.showLogs = false;
	$scope.showResults = false;
	$scope.selectedResultRow = -1;
	$rootScope.masterPendingReviews = []
	$scope.pendingtableconfig = {
			itemsPerPage: 10,
			fillLastPage: false
	}
	$scope.emptyDiff = false;
	$scope.emptyLog = false;
	$scope.zipError = false;
	$scope.filteredPendingList = []; 

	$scope.updateFilteredList = function() {
		$scope.filteredPendingList = $filter("filter")($rootScope.masterPendingReviews, $scope.query);
	};



	$scope.assessorScore = 0;
	$scope.partialScores = {};
	$scope.assessorComments = {};
	$scope.newIssuesIntroducedDetails = "";
	$scope.awardTrophy = false;
	$scope.newIssuesIntroduced = false;
	$scope.$watchCollection('partialScores', function(newVal, oldVal){
		$scope.assessorScore = 0;
		for(var i in $scope.partialScores){
			if(Number.isInteger(parseInt($scope.partialScores[i])))
				$scope.assessorScore += parseInt($scope.partialScores[i]);
		}
	}, true);

	$scope.vulnerabilityStatus = [{id:0, name:"Not Vulnerable"},{id:1, name:"Vulnerable"},{id:2, name:"Broken Functionality"},{id:4, name:"Not Addressed"},{id:3, name:"N/A"}]

	$scope.pendingItemDetails = {};
	$scope.assessorStatus = {};

	$scope.markCancelled = function(){
		server.markCancelled($scope.pendingItemDetails.id);
	}

	$scope.submitReview = function(){
		var obj = {};
		obj.review = [];

		for (var property in $scope.partialScores) {
			if ($scope.partialScores.hasOwnProperty(property)) {
				var tmpObj = {};
				tmpObj.name = property;
				tmpObj.status = $scope.assessorStatus[property].id;
				tmpObj.score = $scope.partialScores[property];
				tmpObj.verified = true;
				tmpObj.comment = $scope.assessorComments[property];
				obj.review.push(tmpObj);
			}
		}

		obj.id = $scope.pendingItemDetails.id;
		obj.totalScore = $scope.assessorScore;
		obj.awardTrophy = $scope.awardTrophy;
		obj.newIssuesIntroduced = $scope.newIssuesIntroduced;
		obj.newIssuesIntroducedText = $scope.newIssuesIntroducedDetails;

		server.submitReview(obj);
	}


	$scope.size = function(obj) {
		var size = 0, key;
		for (key in obj) {
			if (obj.hasOwnProperty(key)) size++;
		}
		return size;
	};

	$scope.$on('pendingReviews:updated', function(event,data) {
		$rootScope.masterPendingReviews = data;
		$scope.filteredPendingList = $filter("filter")($rootScope.masterPendingReviews, $scope.query);
		$scope.showResults = false;
	});
	$scope.$on('reviewSubmitted:updated', function(event,data) {
		server.getPendingReviews();
		server.getCompletedReviews();
		server.getUsers();
	});
	$scope.$on('reviewCancelled:updated', function(event,data) {
		server.getPendingReviews();
		server.getCompletedReviews();
	});



	var remediationClassMap = {
			"Remediated": "table-success",
			"Vulnerable": "table-danger",
			"Broken Functionality":"table-warning"
	}	
	$scope.getRemedationTableClass = function(status) {
		return remediationClassMap[status]
	};
	var statusClassMap = {
			"0": "table-success",
			"1": "table-danger",
			"2": "table-warning",
			"4": "table-info",
			"3": "table-secondary",
			"N/A": "table-secondary"
	}
	$scope.getStatusString = function(status){
		switch(status){
		case "1":
			return "Vulnerable"
		case "0":
			return "Not Vulnerable"
		case "2":
			return "Broken Functionality"
		case "3":
			return "N/A"
		case "4":
			return "Not Addressed"



		default: return status;
		}
	}
	$scope.getStatusClass = function(status) {
		return statusClassMap[status]
	};
	var flagTypeMap = {
			"EXPLOITATION": "table-info",
			"REMEDIATION": "table-warning"
	}
	$scope.getClassForFlagType = function(type){
		return flagTypeMap[type]
	};
	var scoreClassMap = {
			success: "success",
			average: "warning",
			failure: "danger",
			pending: "info"
	};
	$scope.getDurationInterval = function(start,end){
		var out = moment.utc(moment(end).diff(moment(start))).format("H mm").replace(" ","h")+"'";
		return out;

	};
	$scope.getMinutesDuration = function(dur){
		if(undefined != dur && null != dur && 0 != dur){
			return moment.utc(moment.duration(dur,"m").asMilliseconds()).format("H mm").replace(" ","h")+"'";
		}
		else{
			return "N/A"
		}
	};
	$scope.getDatesInterval = function(start,end){
		var out = moment(start).local().format("MMM D YYYY, HH:mm");
		out += " - "+moment(end).local().format("HH:mm (Z)");
		return out;
	}

	$scope.getResultsScoreClass = function(result,total) {
		if(result==-1)
			return scoreClassMap["pending"];
		if(result==-1)
			return scoreClassMap["pending"];
		if(result>(total-(total/10)))
			return scoreClassMap["success"];
		else if(result<(total/3))
			return scoreClassMap["failure"];
		else  
			return scoreClassMap["average"];
	};

	$scope.toggleCodeDiff = function(){
		if($scope.showCodeDiff == true)
			$scope.showCodeDiff = false;
		else{
			$scope.showLogs = false;
			$scope.showCodeDiff = true;
		}
	}
	$scope.toggleInstanceLogs = function(){
		if($scope.showLogs == true)
			$scope.showLogs = false;
		else{
			$scope.showCodeDiff = false;
			$scope.showLogs = true;
		}
	}

	$scope.getQuestionName = function(resName){
		for(var i in $scope.pendingItemDetails.exercise.flags){
			if($scope.pendingItemDetails.exercise.flags.hasOwnProperty(i)){
				for(var j in $scope.pendingItemDetails.exercise.flags[i].flagList){
					if($scope.pendingItemDetails.exercise.flags[i].flagList.hasOwnProperty(j) && $scope.pendingItemDetails.exercise.flags[i].flagList[j].selfCheckAvailable && $scope.pendingItemDetails.exercise.flags[i].flagList[j].selfCheckName==resName){
						return $scope.pendingItemDetails.exercise.flags[i].title;
					}
				}
			}
		}
	}
	$scope.getQuestionMaxScore = function(resName){
		for(var i in $scope.pendingItemDetails.exercise.flags){
			if($scope.pendingItemDetails.exercise.flags.hasOwnProperty(i)){
				for(var j in $scope.pendingItemDetails.exercise.flags[i].flagList){
					if($scope.pendingItemDetails.exercise.flags[i].flagList.hasOwnProperty(j) && $scope.pendingItemDetails.exercise.flags[i].flagList[j].selfCheckAvailable && $scope.pendingItemDetails.exercise.flags[i].flagList[j].selfCheckName==resName){
						return $scope.pendingItemDetails.exercise.flags[i].flagList[j].maxScore;
					}
				}
			}
		}

	}



	$scope.$on('pendingReviewDetails:updated', function(event,data) {

		var offset = $('#showResults').offset();
		$('html, body').animate({
			scrollTop: offset.top - 50,
			scrollLeft: offset.left
		});

		if(data.results.length == 0){

			for(var j in data.exercise.flags){
				if(data.exercise.flags.hasOwnProperty(j)){
					for(var i in data.exercise.flags[j].flagList){
						if(data.exercise.flags[j].flagList.hasOwnProperty(i) && data.exercise.flags[j].flagList[i].selfCheckAvailable){
							data.results[j] = {};
							data.results[j].name = data.exercise.flags[j].flagList[i].selfCheckName;
							data.results[j].status = "NOT_AVAILABLE";
							data.results[j].verified = false; 
						}
					}
				}

			}
		}

		data.placements = [];

		for(var i in data.results){
			if(data.results[i].firstForFlag){
				var tmpObj = {}
				tmpObj.name = data.results[i].name;
				tmpObj.placement = "1st place";
				data.placements.push(tmpObj)
			}
			if(data.results[i].secondForFlag){
				var tmpObj = {}
				tmpObj.name = data.results[i].name;
				tmpObj.placement = "2nd place";
				data.placements.push(tmpObj)
			}
			if(data.results[i].thirdForFlag){
				var tmpObj = {}
				tmpObj.name = data.results[i].name;
				tmpObj.placement = "3rd place";
				data.placements.push(tmpObj)
			}
			switch(data.results[i].status){
			case "VULNERABLE":
				data.results[i].status = "Vulnerable"
					break;
			case "NOT_VULNERABLE":
				data.results[i].status = "Not Vulnerable"
					break;
			case "BROKEN_FUNCTIONALITY":
				data.results[i].status = "Broken Functionality"
					break;
			case "NOT_AVAILABLE":
				data.results[i].status = "N/A"
					break;
			case "NOT_ADDRESSED":
				data.results[i].status = "Not Addressed"
					break;
			}
		}

		$scope.showCodeDiff = false;
		$scope.showLogs = false;
		$scope.pendingItemDetails = data;
		$scope.emptyDiff = false;
		$scope.emptyLog = false;
		$scope.zipError = false;

		$scope.showResults = true;


		var tba = "";
		try{
			JSZipUtils.getBinaryContent($scope.pendingItemDetails.id,$rootScope.ctoken,'/management/team/handler','getReviewFile', function(err, data) {
				if(err) {
					throw err; // or handle err
					$scope.zipError = true;
				}
				try{
					JSZip.loadAsync(data).then(function(zip) {
						for(file in zip.files){
							if(file.indexOf('sourceDiff.txt')>-1){
								zip.file(file).async("string").then(function success(content) {
									var diffString = content;
									if(diffString==""){
										$scope.emptyDiff = true;
										$('#targetDiv').empty()
										return;
									}
									try{
										var diff2htmlUi = new Diff2HtmlUI({diff : diffString });
										diff2htmlUi.draw( '#targetDiv', {
											inputFormat : 'diff',
											showFiles : true,
											matching : 'lines'
										});
										diff2htmlUi.highlightCode('#targetDiv');
									} catch(err){
									}
								})                        
							}
							else if(file.indexOf('.log')>-1){
								zip.file(file).async("string").then(function success(content) {
									var resultString = content;
									if(resultString=="" && tba==""){
										$scope.emptyLog = true;
										$('#rtfLogText').empty()
										return;
									}
									rtfLog = resultString;
									var datas = rtfLog.split("\n");
									for (var i = 0; i < datas.length; i++) {
										if (datas[i] !== "") {
											tba += '<li class="list-group-item">' + htmlEncode(datas[i]) + '</li>';
										}
									}
									$('#rtfLogText').append(tba);
									var diff2htmlUi = new Diff2HtmlUI();
									diff2htmlUi.highlightCode('#rtfLogText');
									diff2htmlUi.highlightCode('.list-group-item');
								});   
							}
						}
					});
				}catch(err){}
			});
		}catch(e){}
	})


	$scope.showDetailsFor = function(eId, index){
		$scope.assessorStatus = {};
		$scope.assessorScore = 0;
		$scope.partialScores = {};
		$scope.assessorComments = {};
		$scope.reviewForm.$setPristine();
		server.getPendingReviewDetails(eId);
		$scope.selectedResultRow = index;
		$location.path("review/details/"+eId, false);

	};
}]);
rtf.controller('stats',['$scope','server',function($scope,server){

	$scope.user = server.user;

	$scope.orgFilter = [];

	$scope.refreshStatsFilter = function(){
		var filter = [];
		for(var i=0; i< $scope.orgFilter.length; i++){
			if($scope.orgFilter[i].checked)
				filter.push($scope.orgFilter[i].id);
		}
		server.getGlobalStats(filter);
	}

	$scope.$on('userProfile:updated', function(event,data) {
		$scope.orgFilter = [];
		for(var i=0;i<$scope.user.organizations.length;i++){
			var tmpObj = cloneObj($scope.user.organizations[i]);
			tmpObj.checked = true;
			$scope.orgFilter.push(tmpObj);
		}
	});


	$scope.charts = {};
	$scope.charts.dashboard = true;
	$scope.charts.remediationRateIssue = false;
	$scope.charts.remediationRateTeam = false;
	$scope.charts.remediationRateCategory  = false;
	$scope.charts.remediationRateRegion  = false;

	$scope.remediatedPerIssue = {}
	$scope.remediatedPerIssue.data = [];
	$scope.remediatedPerIssue.labels = [];

	$scope.remediatedPerCategory = {}
	$scope.remediatedPerCategory.data = [];
	$scope.remediatedPerCategory.labels = [];

	$scope.remediatedPerTeam = {}
	$scope.remediatedPerTeam.data = [];
	$scope.remediatedPerTeam.labels = [];


	$scope.remediatedPerRegion = {}
	$scope.remediatedPerRegion.data = [];
	$scope.remediatedPerRegion.labels = [];

	$scope.remediationRatePerIssue = [];
	$scope.remediationRatePerCategory = [];
	$scope.remediationRatePerRegion = [];
	$scope.remediationRatePerTeam = [];

	$scope.showStats = function(chart){
		if(undefined!=$scope.charts[chart]){
			$('.waitLoader').show();
			for(var i in $scope.charts){
				if($scope.charts.hasOwnProperty(i)){
					$scope.charts[i] = false;
				}
			}
			$scope.charts[chart] = true;
			$('.waitLoader').fadeOut(1000);
		}
	}

	$scope.options = {}
	$scope.options.animation = false;
	$scope.options.layout = {
			padding: {
				left: 0,
				right: 0,
				top: 0,
				bottom: 0
			}
	}
	$scope.options.pieceLabel = {
			// render 'label', 'value', 'percentage' or custom function, default is 'percentage'
			render: 'percentage',
			// precision for percentage, default is 0
			precision: 0,
			// identifies whether or not labels of value 0 are displayed, default is false
			showZero: false,
			// font size, default is defaultFontSize
			fontSize: 12,
			// font color, can be color array for each data, default is defaultFontColor
			fontColor: '#FFF',
			// font style, default is defaultFontStyle
			fontStyle: 'normal',
			// font family, default is defaultFontFamily
			fontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
			// draw label in arc, default is false
			arc: false,
			// position to draw label, available value is 'default', 'border' and 'outside'
			// default is 'default'
			position: 'border',
			// draw label even it's overlap, default is false
			overlap: true
	}
	$scope.options.legend = {
			display: true,
			position: 'bottom',
			labels: {
				fontColor: "#000",
				fontSize: 10
			}
	};
	$scope.options.title = {
			text : "",
			display:true,
			position: 'bottom'

	}
	$scope.radarOptions = {}
	$scope.radarOptions.title = {
			text : "",
			display:true,
			position: 'top'

	}
	$scope.radarOptions.legend = {
			display: true,
			position: 'bottom',
			labels: {
				fontColor: "#000",
				fontSize: 10
			}
	};

	$scope.$on('stats:updated', function(event,data) {



		$scope.statsObj = data;

		function regionCodeFromName(regionName){
			switch(regionName) {
			case "EU (Ireland)":
				region = "EU_WEST_1";
				break;
			case "US East (N. Virginia)":
				region = "US_EAST_1"
					break;
			case "Asia Pacific (Mumbai)":
				region = "AP_SOUTH_1"
					break;
			case "Asia Pacific (Singapore)":
				region = "AP_SOUTHEAST_1"
					break;
			case "US East (Ohio)":
				region = "US_EAST_2";
				break;
			case "US West (Oregon)":
				region = "US_WEST_2";
				break;
			case "US West (N. California)":
				region = "US_WEST_1";
				break;
			case "Canada (Central)":
				region = "CA_CENTRAL_1";
				break;
			case "EU (Frankfurt)":
				region = "EU_CENTRAL_1";
				break;
			case "EU (London)":
				region = "EU_WEST_2";
				break;
			case "EU (Paris)":
				region = "EU_WEST_3";
				break;
			case "Asia Pacific (Seoul)":
				region = "AP_NORTHEAST_2";
				break;
			case "Asia Pacific (Tokyo)":
				region = "AP_NORTHEAST_1";
				break;
			case "Asia Pacific (Sydney)":
				region = "AP_SOUTHEAST_2";
				break;
			case "South America (São Paulo)":
				region = "SA_EAST_1";
				break;
			default:
				break;
			}
			return region;
		}

		$scope.remediationRatePerTeam = [];
		for (var property in data.teamRemediationRate) {
			if (data.teamRemediationRate.hasOwnProperty(property) && 
					Object.keys(data.teamRemediationRate[property]).length>0) {
				var obj = {};
				obj.options = cloneObj($scope.options);
				obj.options.title.text = property;
				obj.data = [];
				obj.labels = ["Not Vulnerable", "Vulnerable", "Not Addressed", "Broken Functionality"];
				for(var l=0;l<obj.labels.length;l++){
					var tmpStatus = obj.labels[l].toUpperCase().replace(" ","_");
					var tmpValue = data.teamRemediationRate[property][tmpStatus]
					if(undefined==tmpValue){
						tmpValue = 0;
					}
					obj.data.push(tmpValue);
				}
				$scope.remediationRatePerTeam.push(obj);
			}
		}
		$scope.remediatedPerTeam.data = [[],[],[]];
		$scope.remediatedPerTeam.labels = [];
		for(var j in $scope.remediationRatePerTeam){
			if ($scope.remediationRatePerTeam.hasOwnProperty(j)){
				var tmpRem = $scope.remediationRatePerTeam[j].data[0]
				var tmpTot = $scope.remediationRatePerTeam[j].data.reduce(getSum);
				var tmpPercentage =  Math.floor((tmpRem * 100) / tmpTot);
				var tmpName = $scope.remediationRatePerTeam[j].options.title.text;
				$scope.remediatedPerTeam.labels.push(tmpName);
				$scope.remediatedPerTeam.data[0].push(tmpPercentage);
				$scope.remediatedPerTeam.data[1].push((Math.ceil(data.totalMinutesPerTeam[tmpName]/60)));
				$scope.remediatedPerTeam.data[2].push(data.avgMinutesPerTeam[tmpName]);

			}
		}
		$scope.remediatedPerTeam.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerTeam.options.title.display = false;
		$scope.remediatedPerTeam.series = ["Remediated (%)","Total Time (hours)","Avg Time (minutes)"];

		$scope.remediationRatePerIssue = [];
		for (var property in data.issuesRemediationRate) {
			if (data.issuesRemediationRate.hasOwnProperty(property) && 
					Object.keys(data.issuesRemediationRate[property]).length>0) {
				var obj = {};
				obj.options = cloneObj($scope.options);
				obj.options.title.text = property;
				obj.data = [];
				obj.labels = ["Not Vulnerable", "Vulnerable", "Not Addressed", "Broken Functionality"];
				for(var l=0;l<obj.labels.length;l++){
					var tmpStatus = obj.labels[l].toUpperCase().replace(" ","_");
					var tmpValue = data.issuesRemediationRate[property][tmpStatus]
					if(undefined==tmpValue){
						tmpValue = 0;
					}
					obj.data.push(tmpValue);
				}
				$scope.remediationRatePerIssue.push(obj);
			}
		}
		$scope.remediatedPerIssue.data = [[]];
		$scope.remediatedPerIssue.labels = [];
		for(var j in $scope.remediationRatePerIssue){
			if ($scope.remediationRatePerIssue.hasOwnProperty(j)){
				var tmpRem = $scope.remediationRatePerIssue[j].data[0]
				var tmpTot = $scope.remediationRatePerIssue[j].data.reduce(getSum);
				var tmpPercentage =  Math.floor((tmpRem * 100) / tmpTot);
				var tmpName = $scope.remediationRatePerIssue[j].options.title.text
				$scope.remediatedPerIssue.labels.push(tmpName);
				$scope.remediatedPerIssue.data[0].push(tmpPercentage);
			}
		}
		$scope.remediatedPerIssue.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerIssue.options.title.display = false;
		$scope.remediatedPerIssue.series = ["Remediated (%)"];

		$scope.remediationRatePerCategory = [];
		for (var property in data.categoriesRemediationRate) {
			if (data.categoriesRemediationRate.hasOwnProperty(property) && 
					Object.keys(data.categoriesRemediationRate[property]).length>0) {
				var obj = {};
				obj.options = cloneObj($scope.options);
				obj.options.title.text = property;
				obj.data = [];
				obj.labels = ["Not Vulnerable", "Vulnerable", "Not Addressed", "Broken Functionality"];
				for(var l=0;l<obj.labels.length;l++){
					var tmpStatus = obj.labels[l].toUpperCase().replace(" ","_");
					var tmpValue = data.categoriesRemediationRate[property][tmpStatus]
					if(undefined==tmpValue){
						tmpValue = 0;
					}
					obj.data.push(tmpValue);
				}
				$scope.remediationRatePerCategory.push(obj);
			}
		}
		$scope.remediatedPerCategory.data = [[],[],[]]
		$scope.remediatedPerCategory.labels = [];
		for(var j in $scope.remediationRatePerCategory){
			if ($scope.remediationRatePerCategory.hasOwnProperty(j)){
				var tmpRem = $scope.remediationRatePerCategory[j].data[0]
				var tmpTot = $scope.remediationRatePerCategory[j].data.reduce(getSum);
				var tmpPercentage =  Math.floor((tmpRem * 100) / tmpTot);
				var tmpName = $scope.remediationRatePerCategory[j].options.title.text;
				$scope.remediatedPerCategory.labels.push(tmpName);
				$scope.remediatedPerCategory.data[0].push(tmpPercentage);
				$scope.remediatedPerCategory.data[1].push(Math.ceil(data.totalMinutesPerIssueCategory[tmpName]/60));
				$scope.remediatedPerCategory.data[2].push(data.avgMinutesPerIssueCategory[tmpName]);
			}
		}
		$scope.remediatedPerCategory.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerCategory.options.title.display = false;
		$scope.remediatedPerCategory.series = ["Remediated (%)","Total Time (hours)","Avg Time (minutes)"];

		$scope.remediationRatePerRegion = [];
		for (var property in data.regionsRemediationRate) {
			if (data.regionsRemediationRate.hasOwnProperty(property) && 
					Object.keys(data.regionsRemediationRate[property]).length>0) {
				var obj = {};
				obj.options = cloneObj($scope.options);

				switch(property) {
				case "EU_WEST_1":
					region = "EU (Ireland)";
					break;
				case "US_EAST_1":
					region = "US East (N. Virginia)"
						break;
				case "AP_SOUTH_1":
					region = "Asia Pacific (Mumbai)"
						break;
				case "AP_SOUTHEAST_1":
					region = "Asia Pacific (Singapore)"
						break;
				case "US_EAST_2":
					region = "US East (Ohio)";
					break;
				case "US_WEST_2":
					region = "US West (Oregon)";
					break;
				case "US_WEST_1":
					region = "US West (N. California)";
					break;
				case "CA_CENTRAL_1":
					region = "Canada (Central)";
					break;
				case "EU_CENTRAL_1":
					region = "EU (Frankfurt)";
					break;
				case "EU_WEST_2":
					region = "EU (London)";
					break;
				case "EU_WEST_3":
					region = "EU (Paris)";
					break;
				case "AP_NORTHEAST_2":
					region = "Asia Pacific (Seoul)";
					break;
				case "AP_NORTHEAST_1":
					region = "Asia Pacific (Tokyo)";
					break;
				case "AP_SOUTHEAST_2":
					region = "Asia Pacific (Sydney)";
					break;
				case "SA_EAST_1":
					region = "South America (São Paulo)";
					break;
				default:
					break;
				}

				obj.options.title.text = region;


				obj.data = [];
				obj.labels = ["Not Vulnerable", "Vulnerable", "Not Addressed", "Broken Functionality"];
				for(var l=0;l<obj.labels.length;l++){
					var tmpStatus = obj.labels[l].toUpperCase().replace(" ","_");
					var tmpValue = data.regionsRemediationRate[property][tmpStatus]
					if(undefined!=tmpValue){
						obj.data.push(tmpValue)
					}
					else{
						obj.data.push(0)
					}
				}
				$scope.remediationRatePerRegion.push(obj);
			}
		}
		$scope.remediatedPerRegion.labels = [];
		$scope.remediatedPerRegion.data = [[],[],[]]
		for(var j in $scope.remediationRatePerRegion){
			if ($scope.remediationRatePerRegion.hasOwnProperty(j)){
				var tmpRem = $scope.remediationRatePerRegion[j].data[0]
				var tmpTot = $scope.remediationRatePerRegion[j].data.reduce(getSum);
				var tmpPercentage =  Math.floor((tmpRem * 100) / tmpTot);
				var tmpName = $scope.remediationRatePerRegion[j].options.title.text;
				$scope.remediatedPerRegion.labels.push($scope.remediationRatePerRegion[j].options.title.text);
				$scope.remediatedPerRegion.data[0].push(tmpPercentage);
				$scope.remediatedPerRegion.data[1].push(Math.ceil(data.totalMinutesPerRegion[regionCodeFromName(tmpName)]/60));
				$scope.remediatedPerRegion.data[2].push(data.avgMinutesPerRegion[regionCodeFromName(tmpName)]);
			}
		}
		$scope.remediatedPerRegion.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerRegion.options.title.display = false;
		$scope.remediatedPerRegion.series = ["Remediated (%)","Total Time (hours)","Avg Time (minutes)"];

	});

}]);
rtf.controller('settings',['$scope','server','$timeout',function($scope,server,$timeout){
	$scope.user = server.user;
	$scope.countries = server.countries;
	$scope.oldPassword = "";
	$scope.newPasswordRepeat = "";
	$scope.newPassword = "";
	$scope.updateUserProfile = function(){
		server.updateUserProfile($scope.user);
	}
	$scope.updateUserPassword = function(){
		server.updateUserPassword($scope.userPasswordForm.oldPassword.$modelValue, $scope.userPasswordForm.newPassword.$modelValue);
		$scope.oldPassword = "";
		$scope.newPasswordRepeat = "";
		$scope.newPassword = "";
	}
	$scope.removeUser = function(){
		server.removeUser();
		$('#userRemoveModal').modal('hide');
	}
	$scope.openRemoveModal = function(){
		$('#userRemoveModal').modal('show');	
	}
	$scope.$on('removeUser:updated', function(event,data) {
		document.location = "/index.html";
	})

}]);
rtf.controller('runningExercises',['$scope','server','$rootScope','$location','$filter',function($scope,server,$rootScope,$location,$filter){

	$scope.masterRunning = [];
	$scope.filteredRunningList = []; 
	$scope.selectedRunningRow = -1;
	$scope.runningtableconfig = {
			itemsPerPage: 30,
			fillLastPage: false
	}
	$scope.updateFilteredList = function() {
		$scope.filteredRunningList = $filter("filter")($scope.masterRunning, $scope.query);
	};

	$scope.$on('runningExercises:updated', function(event,data) {
		$scope.masterRunning = data;
		$scope.filteredRunningList = $scope.masterRunning;
	});

	$scope.getRegionFromCode = function(code){
		if(code==undefined || code == "")
			return "";
		switch(code){
		case "EU_WEST_1":
			region = "EU (Ireland)";
			break;
		case "US_EAST_1":
			region = "US East (N. Virginia)"
				break;
		case "AP_SOUTH_1":
			region = "Asia Pacific (Mumbai)"
				break;
		case "AP_SOUTHEAST_1":
			region = "Asia Pacific (Singapore)"
				break;
		case "US_EAST_2":
			region = "US East (Ohio)";
			break;
		case "US_WEST_2":
			region = "US West (Oregon)";
			break;
		case "US_WEST_1":
			region = "US West (N. California)";
			break;
		case "CA_CENTRAL_1":
			region = "Canada (Central)";
			break;
		case "EU_CENTRAL_1":
			region = "EU (Frankfurt)";
			break;
		case "EU_WEST_2":
			region = "EU (London)";
			break;
		case "EU_WEST_3":
			region = "EU (Paris)";
			break;
		case "AP_NORTHEAST_2":
			region = "Asia Pacific (Seoul)";
			break;
		case "AP_NORTHEAST_1":
			region = "Asia Pacific (Tokyo)";
			break;
		case "AP_SOUTHEAST_2":
			region = "Asia Pacific (Sydney)";
			break;
		case "SA_EAST_1":
			region = "South America (São Paulo)";
			break;
		default:
			break;
		}
		return region;

	}



}]);
rtf.controller('challenges',['$scope','server','$rootScope','$location','$filter','$interval',function($scope,server,$rootScope,$location,$filter,$interval){

	$scope.user = server.user;
	$scope.availableUserRoles = [{ id:7, name:"User"},{ id:4, name:"Monitor"},{ id:3, name:"Team Manager"},{ id:1, name:"Reviewer"},{ id:0, name: "Admin"},{ id:-1, name: "RTF Admin"}];
	$scope.countries = server.countries;
	$scope.selectedChallenge = "";
	$scope.filteredChallengesList = [];
	$scope.masterChallengesList = [];
	$rootScope.showChallengesList = true;
	$rootScope.showChallengeDetails = false;
	$scope.challengeNameAvailable = true;
	$scope.userObj = [];
	$scope.exerciseObj = [];
	$scope.selectedExerciseList = [];
	$scope.selectedUsersList = [];

	var tmpChallengeToRemove = -1;
	$scope.tmpChallengeToBeRemovedName = "";
	$scope.removeChallengeModal = function(id,name){
		tmpChallengeToRemove = id;
		$scope.tmpChallengeToBeRemovedName = name
		$('#removeChallengeModal').modal('show');
	}
	$scope.removeChallenge = function(){
		server.removeChallenge(tmpChallengeToRemove);
		tmpChallengeToRemove = -1;
		$('#removeChallengeModal').modal('hide');
	}

	$scope.getChallengeUserScore = function(user){
		return "";
	}
	$scope.getChallengeUserRunExercises = function(user){
		return "";
	}



	$scope.getChallengeDetails = function(exId){
		server.getChallengeDetails(exId);
	}
	$scope.scoringModes = [0,1,2];
	$scope.usersInSelectedOrg = [];
	$scope.tmpNewChallenge = {};
	$scope.saveFlow = false;

	$scope.filteredAvailableExercisesList = [];
	$scope.masterAvailableExercisesList = [];
	$scope.exercisesForOrgs = [];

	$scope.getExerciseStatusString = function(status){
		switch(status){
		case "0":
			return "Available";
			break;
		case "2":
			return "Coming Soon";
			break;
		case "3":
			return "Inactive";
			break;
		default:
			return "N/A";
		}
	}


	$scope.updateAvailableExercisesFilteredList = function() {
		$scope.filteredTeamsList = $filter("filter")($scope.masterAvailableExercisesList, $scope.queryAvailableExercises);
	};
	$scope.availableExercisestableconfig = {
			itemsPerPage: 8,
			fillLastPage: false
	}
	$scope.$on('availableExercises:updated', function(event,data) {
		$scope.masterAvailableExercisesList = data.exercises;
		$scope.exercisesForOrgs = data.orgs;

		$scope.exerciseObj = [];
		$scope.selectedExerciseList = [];

		if($scope.tmpNewChallenge.organization && $scope.tmpNewChallenge.organization.id){
			for(var i in $scope.masterAvailableExercisesList){
				if($scope.masterAvailableExercisesList[i].hasOwnProperty('id') && !$scope.getExerciseEnabledForOrgText($scope.tmpNewChallenge.organization.id,$scope.masterAvailableExercisesList[i].id)){
					$scope.masterAvailableExercisesList.remove(i);
				}
				else if($scope.masterAvailableExercisesList[i].hasOwnProperty('exerciseType') && $scope.masterAvailableExercisesList[i].exerciseType == 'TRAINING'){
					$scope.masterAvailableExercisesList.remove(i);
				}
			}
			for(var i in  $rootScope.challengeDetails.exercises){
				if( $rootScope.challengeDetails.exercises[i].hasOwnProperty('id')){
					for(var j in $scope.masterAvailableExercisesList){
						if( $scope.masterAvailableExercisesList[j].hasOwnProperty('id') && $scope.masterAvailableExercisesList[j].id == $rootScope.challengeDetails.exercises[i].id){
							$scope.selectedExerciseList.push($scope.masterAvailableExercisesList[j].id)
							$scope.masterAvailableExercisesList[j].isChecked = true;
						}
					}

				}
			}
			$scope.filteredAvailableExercisesList = $scope.masterAvailableExercisesList;
		}
	});

	$scope.getExerciseTitleFromId = function(id){
		for(var i in $scope.masterAvailableExercisesList){
			if( $scope.masterAvailableExercisesList[i].id == id){
				return $scope.masterAvailableExercisesList[i].title
			}
		}
		return "Exercise "+id;
	}

	$scope.getScoringModeString = function(code){
		switch(parseInt(code)){
		case 0:
			return "Automated Only";
		case 2:
			return "Automated + Manual";
		default: 
			return "";
		}
	}

	$scope.getUserRoleString = function(id){
		for(var i in $scope.availableUserRoles){
			if($scope.availableUserRoles.hasOwnProperty(i)){
				if($scope.availableUserRoles[i].id==id)
					return $scope.availableUserRoles[i].name;
			}
		}
		return id;
	}



	$scope.userCheckToggle=function(s){
		if(s.isChecked === true){
			s.isChecked === false;
		}else{
			s.isChecked === true;
		}
		if($scope.selectedUsersList.indexOf(s.user)<0){
			$scope.selectedUsersList.push(s.user);
		}
		else{
			var idx = $scope.selectedUsersList.indexOf(s.user);
			$scope.selectedUsersList.remove(idx,idx);
		}
	}

	$scope.exerciseCheckToggle=function(s){
		if(s.isChecked === true){
			s.isChecked === false;
		}else{
			s.isChecked === true;
		}
		if($scope.selectedExerciseList.indexOf(s.id)<0){
			$scope.selectedExerciseList.push(s.id);
		}
		else{
			var idx = $scope.selectedExerciseList.indexOf(s.id);
			$scope.selectedExerciseList.remove(idx,idx);
		}
	}

	$scope.clear = function(){
		$scope.tmpNewChallenge = {};
		$scope.tmpNewChallenge.startDate = "";
		$scope.tmpNewChallenge.endDate = "";
		$scope.tmpNewChallenge.name = "";
		$scope.tmpNewChallenge.details = "";
		$scope.tmpNewChallenge.users = [];
		$scope.tmpNewChallenge.exercises = [];
		$scope.tmpNewChallenge.organization = "";
		$scope.tmpNewChallenge.scoringMode = "";
		$scope.masterUsersList = [];
		$scope.filteredUsersList = [];
		$scope.userObj = [];
		$scope.exerciseObj = [];
		$scope.selectedUsersList = [];
		$scope.selectedExerciseList = [];
		$scope.filteredAvailableExercisesList = [];
		$scope.masterAvailableExercisesList = [];
		$scope.exercisesForOrgs = [];
	}

	$scope.addNewChallenge = function(){
		$('#addNewChallengeModal').modal('hide');
		var obj = cloneObj($scope.tmpNewChallenge)
		try{
			obj.endDate = moment.utc(obj.endDate).format('ddd, D MMM YYYY HH:mm:ss z');
			obj.startDate = moment.utc(obj.startDate).format('ddd, D MMM YYYY HH:mm:ss z');

		}catch(e){}
		obj.users = $scope.selectedUsersList;
		obj.exercises = $scope.selectedExerciseList;
		obj.idOrg = obj.organization.id
		delete obj.organization
		server.addChallenge(obj);
	}

	$scope.$on('addChallenge:updated', function(event,data) {
		server.getChallenges();
		$scope.clear();
		$scope.saveFlow = false;
	})
	$scope.$on('challengeRemoved:updated', function(event,data) {
		server.getChallenges();
		$scope.saveFlow = false;
	})
	$scope.$on('challengeUpdated:updated', function(event,data) {
		server.getChallenges();
		$scope.getChallengeDetails($rootScope.challengeDetails.id)
		$scope.clear();
		$scope.saveFlow = false;
	})

	$scope.updateChallengeModal = function(){
		$scope.saveFlow = true;

		$scope.tmpNewChallenge = {};
		$scope.tmpNewChallenge.organization = $rootScope.challengeDetails.organization;
		$scope.getUsersExercisesInOrg();
		$scope.tmpNewChallenge.id = $rootScope.challengeDetails.id;
		$scope.tmpNewChallenge.startDate = moment(moment($rootScope.challengeDetails.startDate).local().format());
		$scope.tmpNewChallenge.endDate = moment(moment($rootScope.challengeDetails.endDate).local().format());
		$scope.tmpNewChallenge.name = $rootScope.challengeDetails.name;
		$scope.tmpNewChallenge.details = $rootScope.challengeDetails.details;
		$scope.tmpNewChallenge.scoringMode = $rootScope.challengeDetails.scoring;
		$scope.tmpNewChallenge.firstPlace = $rootScope.challengeDetails.firstInFlag;
		$scope.tmpNewChallenge.secondPlace = $rootScope.challengeDetails.secondInFlag;
		$scope.tmpNewChallenge.thirdPlace = $rootScope.challengeDetails.thirdInFlag;
		$scope.tmpNewChallenge.users = [];
		$scope.tmpNewChallenge.exercises = [];

		$('#addNewChallengeModal').modal('show');
	}

	$scope.updateChallenge = function(){

		$('#addNewChallengeModal').modal('hide');
		var obj = cloneObj($scope.tmpNewChallenge)
		try{
			obj.endDate = moment.utc(obj.endDate).format('ddd, D MMM YYYY HH:mm:ss z');
			obj.startDate = moment.utc(obj.startDate).format('ddd, D MMM YYYY HH:mm:ss z');

		}catch(e){}
		obj.users = $scope.selectedUsersList;
		obj.exercises = $scope.selectedExerciseList;
		delete obj.organization		
		server.updateChallenge(obj);
	}

	$scope.isChallengeNameAvailable = function(name){
		if($scope.saveFlow && name == $rootScope.challengeDetails.name){
			return true;
		}
		server.isChallengeNameAvailable($scope.tmpNewChallenge.name);
	}
	$scope.$on('challengeNameAvailable:updated', function(event,data) {
		$scope.challengeNameAvailable = data.result;
	})

	$scope.$on('challengeAdded:updated', function(event,data) {
		$scope.tmpNewChallenge = {};
		$scope.tmpNewChallenge.startDate = "";
		$scope.tmpNewChallenge.endDate = "";
		$scope.tmpNewChallenge.name = "";
		$scope.tmpNewChallenge.details = "";
		$scope.tmpNewChallenge.users = [];
		$scope.tmpNewChallenge.exercises = [];
		$scope.tmpNewChallenge.organization = "";
		$scope.tmpNewChallenge.scoringMode = "";
		$scope.masterUsersList = [];
		$scope.filteredUsersList = [];
	});

	$rootScope.getDateInCurrentTimezone = function(date,format){
		if(date==null)
			return "N/A"
			return moment(date).local().format(format);
	}

	$scope.getExerciseEnabledForOrgText = function(orgId,exId){
		if(undefined == orgId || undefined == exId)
			return false;
		for(var i in $scope.exercisesForOrgs){
			if ($scope.exercisesForOrgs[i].hasOwnProperty("organization") && $scope.exercisesForOrgs[i].organization.id==orgId){
				for(var j in $scope.exercisesForOrgs[i].exercises){
					if($scope.exercisesForOrgs[i].exercises[j].id==exId){
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	$scope.changeOrganization = function(){

		$scope.userObj = [];
		$scope.exerciseObj = [];
		$scope.selectedUsersList = [];
		$scope.selectedExerciseList = [];
		$scope.getUsersExercisesInOrg();

	}

	$scope.getUsersExercisesInOrg = function(){

		if(!$scope.tmpNewChallenge.organization || !$scope.tmpNewChallenge.organization.id)
			return;
		server.getUsersInOrg($scope.tmpNewChallenge.organization)
		server.getAvailableExercises();

	}

	$scope.masterUsersList = [];
	$scope.filteredUsersList = []; 

	$scope.$on('usersInOrg:updated', function(event,data) {
		$scope.masterUsersList = data;
		$scope.filteredUsersList = $filter("filter")($scope.masterUsersList, $scope.query);

		$scope.userObj = [];
		$scope.selectedUsersList = [];

		for(var i in  $rootScope.challengeDetails.users){
			if( $rootScope.challengeDetails.users[i].hasOwnProperty('user')){
				for(var j in $scope.masterUsersList){
					if( $scope.masterUsersList[j].hasOwnProperty('user') && $scope.masterUsersList[j].user == $rootScope.challengeDetails.users[i].user){
						$scope.selectedUsersList.push($scope.masterUsersList[j].user)
						$scope.masterUsersList[j].isChecked = true;
					}
				}

			}
		}

	});	
	$scope.usertableconfig = {
			itemsPerPage: 5,
			fillLastPage: false
	}
	$scope.updateFilteredList = function() {
		$scope.filteredUsersList = $filter("filter")($scope.masterUsersList, $scope.query);
	};



	$scope.addNewChallengeModal = function(){
		if($scope.saveFlow){
			$scope.clear();
			$scope.saveFlow = false;
		}
		$('#addNewChallengeModal').modal('show');
	}

	$rootScope.challengeDetails = [];
	$scope.challengeResults = {};

	var challengeUpdateTimer = null;
	$scope.$on('challengeDetails:updated', function(event,data) {
		data.flags = [];
		data.theads = [];


		for (var property in data.exercises) {
			if (data.exercises.hasOwnProperty(property)) {
				for(var f in data.exercises[property].flags){
					if (data.exercises[property].flags.hasOwnProperty(f)) {
						var tmpObj = {};
						tmpObj.id = data.exercises[property].flags[f].id
						tmpObj.name = data.exercises[property].flags[f].title
						data.theads.push(tmpObj)
						data.flags.push(data.exercises[property].flags[f]);
					}
				}
			}
		}

		var remediated = 0; 
		data.runFlags = 0;
		data.challengeRunningExercises=0;
		data.challengeRunExercises = 0;
		for(var i=0;i<data.runExercises.length;i++){
			if(data.runExercises[i].status=="RUNNING"){
				data.challengeRunningExercises++;
			}
			else{
				data.challengeRunExercises++;
			}
			for(var j=0;j<data.runExercises[i].results.length;j++){
				data.runFlags++;
				if(data.runExercises[i].results[j].status == "0"){
					remediated++;
				}
			}
		}
		if(remediated==0 || data.runFlags == 0)
			data.remediation = 0;
		else
			data.remediation = remediated/data.runFlags * 100;
		$scope.challengeTableConfig = {
				itemsPerPage: 20,
				fillLastPage: false
		}
		$rootScope.challengeDetails = data;
		$rootScope.challengeDetails.lastRefreshed = new Date();
		$rootScope.challengeDetails.teams = [];

		for(var u in $rootScope.challengeDetails.users){
			if ($rootScope.challengeDetails.users.hasOwnProperty(u)){
				if(undefined!=$rootScope.challengeDetails.users[u].team && $rootScope.challengeDetails.teams.indexOf($rootScope.challengeDetails.users[u].team.name)<0){
					$rootScope.challengeDetails.teams.push($rootScope.challengeDetails.users[u].team.name)
				}
				$scope.challengeResults[$rootScope.challengeDetails.users[u].user] = {}
				$rootScope.challengeDetails.users[u].challengeRunFlags = 0;
				$rootScope.challengeDetails.users[u].challengeRunExercises = 0;
				$rootScope.challengeDetails.users[u].challengeScore = 0;
				for(var e in $rootScope.challengeDetails.runExercises){
					if($rootScope.challengeDetails.runExercises.hasOwnProperty(e) && $rootScope.challengeDetails.runExercises[e].user.user==$rootScope.challengeDetails.users[u].user){
						$rootScope.challengeDetails.users[u].challengeRunExercises++;
						for(var r in $rootScope.challengeDetails.runExercises[e].results){
							if($rootScope.challengeDetails.runExercises[e].results.hasOwnProperty(r)){
								$scope.challengeResults[$rootScope.challengeDetails.users[u].user][$rootScope.challengeDetails.runExercises[e].results[r].name] = $rootScope.challengeDetails.runExercises[e].results[r];
								if($rootScope.challengeDetails.runExercises[e].results[r].status=="0" && Number.isInteger($rootScope.challengeDetails.runExercises[e].results[r].score)){
									$rootScope.challengeDetails.users[u].challengeScore += $rootScope.challengeDetails.runExercises[e].results[r].score;
								}
								$rootScope.challengeDetails.users[u].challengeRunFlags++;
							} 
						}
					}
				}
			}
		}
		$rootScope.showChallengeDetails = true;
		$rootScope.showChallengesList = false;
		$location.path("challenges/details/"+$rootScope.challengeDetails.id, false);
		if(challengeUpdateTimer==null )
			challengeUpdateTimer = $interval(function(){triggerChallengeUpdate($rootScope.challengeDetails.id)},10000)

	});

	function triggerChallengeUpdate(id){
		if($rootScope.visibility.challenges && $rootScope.showChallengeDetails && id == $rootScope.challengeDetails.id){
			$scope.getChallengeDetails(id);
		}
		else{
			$interval.cancel(challengeUpdateTimer);
			challengeUpdateTimer = null;
		}
	}

	$scope.getExerciseIdForFlag = function(usr,flag){
		var sf = getSelfCheckFromFlag(flag);
		if(sf=="")
			return "";
		var runExercises = $rootScope.challengeDetails.runExercises;
		for (var ex=0;ex<runExercises.length;ex++) {
			for(var res=0;res<runExercises[ex]["results"].length;res++){
				if (runExercises[ex]["results"][res].name==sf) {
					return runExercises[ex].id
				}
			}			
		}
	}
	function getSelfCheckFromFlag(flag){
		for(var f in $rootScope.challengeDetails.flags){
			if($rootScope.challengeDetails.flags.hasOwnProperty(f) && $rootScope.challengeDetails.flags[f].title==flag){
				for(var q in $rootScope.challengeDetails.flags[f].flagList){
					if($rootScope.challengeDetails.flags[f].flagList.hasOwnProperty(q) && $rootScope.challengeDetails.flags[f].flagList[q].selfCheckAvailable==true){
						return $rootScope.challengeDetails.flags[f].flagList[q].selfCheckName;
					}
				}
			}
		}
		return "";
	}

	$scope.getClassPlacementInChallenge= function(usr,name){
		if($scope.challengeResults[usr]==undefined || $scope.challengeResults[usr][name]==undefined)
			return false;
		else if($scope.challengeResults[usr][name]['firstForFlag']){
			return "goldPlacement";
		}
		else if($scope.challengeResults[usr][name]['secondForFlag']){
			return "silverPlacement";
		}
		else if($scope.challengeResults[usr][name]['thirdForFlag']){
			return "bronzePlacement";
		}
		return "";
	}
	$scope.getPlacementInChallenge = function(usr,name){
		if($scope.challengeResults[usr]==undefined || $scope.challengeResults[usr][name]==undefined)
			return false;
		else if($scope.challengeResults[usr][name]['firstForFlag']){
			return "1st";
		}
		else if($scope.challengeResults[usr][name]['secondForFlag']){
			return "2nd";
		}
		else if($scope.challengeResults[usr][name]['thirdForFlag']){
			return "3rd";
		}
		return "";
	}

	$scope.isPlacedInChallenge = function(usr,name){
		if($scope.challengeResults[usr]==undefined || $scope.challengeResults[usr][name]==undefined)
			return false;
		else if($scope.challengeResults[usr][name]['firstForFlag']){
			return true;
		}
		else if($scope.challengeResults[usr][name]['secondForFlag']){
			return true;
		}
		else if($scope.challengeResults[usr][name]['thirdForFlag']){
			return true;
		}
		return false;
	}

	$scope.getChallengeResultFor = function(usr,flag){
		var sf = getSelfCheckFromFlag(flag);

		if(sf=="")
			return "N/A";
		var status = "-1";
		//loop1:
		try{
			var status = $scope.challengeResults[usr][sf].status
		}catch(e){
			status = "-1";
		}

		switch(status){
		case undefined:
			return "Not Started"
		case "-1":
			return "Not Started"
		case "1":
			return "Vulnerable"
		case "0":
			return "Not Vulnerable"
		case "2":
			return "Broken Functionality"
		case "4":
			return "Not Addressed"
		default: return "N/A"
		};
	}
	$scope.getClassForChallengeResult = function(user,flag){
		var status = $scope.getChallengeResultFor(user,flag);

		switch(status){
		case "Vulnerable":
			return "table-danger"
		case "Not Vulnerable":
			return "table-success"
		case "Broken Functionality":
			return "table-warning"
		case "Not Addressed":
			return "table-info"
		default: return "table-light"
		};



	}
	$scope.backToList = function(){
		$rootScope.showChallengeDetails = false;
		$rootScope.showChallengesList = true;
		$location.path("challenges", false);
	}


	$scope.getChallengeStatusString = function(status){
		switch(status){
		case "IN_PROGRESS":
			return "In Progress";
			break;
		case "NOT_STARTED":
			return "Not Started";
			break;
		case "FINISHED":
			return "Finished";
			break;
		}
	}


	$scope.updateChallengesFilteredList = function() {
		$scope.filteredChallengesList = $filter("filter")($scope.masterChallengesList, $scope.queryChallenges);
	};
	$scope.challengestableconfig = {
			itemsPerPage: 20,
			fillLastPage: false
	}
	$scope.$on('challenges:updated', function(event,data) {
		$scope.masterChallengesList = data;
		$scope.filteredChallengesList = $scope.masterChallengesList;
	});

}]);
