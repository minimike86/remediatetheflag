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
function getSum(total, num) {
	return total + num;
}
function receiveMessage(event){
	if (event.origin != self.origin)
		return;
	if(event.data == "backToRTF"){
		event.source.postMessage("ok", event.origin);
		window.location = '/user/index.html#/running';
		angular.element(document.getElementById('assignedExercises')).scope().exerciseStopped();
		return;
	}
	if(event.data == "markCompleted"){
		event.source.postMessage("ok", event.origin);
		window.location = '/user/index.html#/history';
		angular.element(document.getElementById('history')).scope().getUserHistory()
		return;
	}
	event.source.postMessage("ok",event.origin);
}
window.addEventListener("message", receiveMessage, false);

function cloneObj(obj){
	return JSON.parse(JSON.stringify(obj))
}
Array.prototype.remove = function(from, to) {
	var rest = this.slice((to || from) + 1 || this.length);
	this.length = from < 0 ? this.length + from : from;
	return this.push.apply(this, rest);
};
Object.size = function(obj) {
	var size = 0, key;
	for (key in obj) {
		if (obj.hasOwnProperty(key)) size++;
	}
	return size;
};
if (!Array.prototype.includes) {
	Object.defineProperty(Array.prototype, 'includes', {
		value: function(searchElement, fromIndex) {

			// 1. Let O be ? ToObject(this value).
			if (this == null) {
				throw new TypeError('"this" is null or not defined');
			}

			var o = Object(this);

			// 2. Let len be ? ToLength(? Get(O, "length")).
			var len = o.length >>> 0;

			// 3. If len is 0, return false.
			if (len === 0) {
				return false;
			}

			// 4. Let n be ? ToInteger(fromIndex).
			//    (If fromIndex is undefined, this step produces the value 0.)
			var n = fromIndex | 0;

			// 5. If n ≥ 0, then
			//  a. Let k be n.
			// 6. Else n < 0,
			//  a. Let k be len + n.
			//  b. If k < 0, let k be 0.
			var k = Math.max(n >= 0 ? n : len - Math.abs(n), 0);

			function sameValueZero(x, y) {
				return x === y || (typeof x === 'number' && typeof y === 'number' && isNaN(x) && isNaN(y));
			}

			// 7. Repeat, while k < len
			while (k < len) {
				// a. Let elementK be the result of ? Get(O, ! ToString(k)).
				// b. If SameValueZero(searchElement, elementK) is true, return true.
				// c. Increase k by 1. 
				if (sameValueZero(o[k], searchElement)) {
					return true;
				}
				k++;
			}

			// 8. Return false
			return false;
		}
	});
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

	$('.standalone').css('min-height',(win - calc1 + 8)+"px");
	$('.serviceContainer').css('height',(win - calc2 + 8)+"px");
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
});

var beta = false;
window.beta = beta;
var rtf = angular.module('rtfNg',['nya.bootstrap.select','jlareau.pnotify','ui.toggle','ngRoute','angular-table','chart.js','angular-notification-icons']);


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
rtf.filter('capitalize', function() {
	return function(input) {
		return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
	}
});
rtf.service('server',function($http,$timeout,$rootScope,notificationService,$interval){

	this.countries = [];

	var $this = this;

	this.getTeamStats = function(){
		var msg = {};
		msg.action = 'getTeamStats';
		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			$rootScope.$broadcast('statsTeam:updated',response.data);
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



	this.getUserStats = function(username){
		var msg = {};
		msg.action = 'getMyStats';
		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('statsUser:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.user = {}

	this.addScoringComplaint = function(obj){

		obj.action = 'addScoringComplaint';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: obj,	
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('scoringComplaintAdded:updated',response.data);
			notificationService.success('Thanks, your comment will be reviewed by our team shortly.');
			$('.waitLoader').hide();
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getUserTeamLeaderboard = function(){

		var msg = {};
		msg.action = 'getLeaderboard';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('leaderboard:updated',response.data);
			$('.waitLoader').hide();
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
			$rootScope.$broadcast('userInfo:updated',response.data);

			$('.waitLoader').hide();
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
		$('.waitLoader').show();
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

	this.achievementsData = {};

	this.getUserAchievements = function(){

		var msg = {};
		msg.action = 'getUserAchievements';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			replaceObjectContent($this.achievementsData,response.data)
			$rootScope.$broadcast('achievements:updated',$this.achievementsData);
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
		$http(req).then(function successCallback(response) {
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
	this.getCToken($this);



	this.exercisesData = [];

	this.getAvailableExercises = function(){

		var msg = {};
		msg.action = 'getExercises';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			replaceArrayContent($this.exercisesData,response.data)
			$rootScope.$broadcast('exercises:updated',$this.exercisesData);
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}
	this.getChallenges = function(){

		var msg = {};
		msg.action = 'getChallenges';

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg
		}
		$http(req).then(function successCallback(response) {
			if(response.data.length==0)
				$rootScope.$broadcast('challenges:updated',null);
			else
				$rootScope.$broadcast('challenges:updated',response.data);
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
				url: '/user/handler',
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
	this.isExerciseIsInChallenge = function(exerciseId){

		var msg = {};
		msg.action = 'isExerciseInChallenge';
		msg.id = exerciseId;

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('exerciseIsInChallenge:updated',response.data);
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
				url: '/user/handler',
				data: msg
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('exerciseDetails:updated',response.data);
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
				url: '/user/handler',
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
			if (navigator.appVersion.toString().indexOf('.NET') > 0){
				window.navigator.msSaveBlob(blob, filename);
			}
			else{
				var downloadLink = angular.element('<a></a>');
				downloadLink.attr('href',window.URL.createObjectURL(blob));
				downloadLink.attr('download', filename);
				document.body.appendChild(downloadLink[0]);
				downloadLink[0].click();
				downloadLink[0].remove();
			}

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
				url: '/user/handler',
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
			if (navigator.appVersion.toString().indexOf('.NET') > 0){
				window.navigator.msSaveBlob(blob, filename);
			}
			else{
				var downloadLink = angular.element('<a></a>');
				downloadLink.attr('href',window.URL.createObjectURL(blob));
				downloadLink.attr('download', filename);
				document.body.appendChild(downloadLink[0]);
				downloadLink[0].click();
				downloadLink[0].remove();
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
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('exerciseRegions:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getHintForQuestion = function(id,exId){

		var msg = {};
		msg.action = 'getHint';
		msg.id = id;
		msg.exId = exId;
		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('hintReceived:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getResultStatus = function(id){

		var msg = {};
		msg.action = 'getResultStatus';
		msg.id = id;
		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('resultStatusReceived:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.refreshGuacToken = function(id){

		var msg = {};
		msg.action = 'refreshGuacToken';
		msg.id = id;
		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('tokenRefreshed:updated',response.data);
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.stopInstance = function(id){
		var msg = {};
		msg.action = 'stopExerciseInstance';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}

		$http(req).then(function successCallback(response) {
			$('.waitLoader').hide();
			if(response.data.result == "success"){
				$rootScope.$broadcast('exerciseStopped:updated',response.data);
			}
			else{
				PNotify.removeAll();
				notificationService.info("Could not stop the exercise, please try again or contact support.");
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.pollReservation = function(id){
		var msg = {};
		msg.action = 'getReservationUpdate';
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			$rootScope.$broadcast('instanceStarted:updated',response.data);			
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.startInstance = function(id, regions, challenge){
		var msg = {};
		msg.action = 'launchExerciseInstance';
		msg.id = id;
		msg.region = regions;
		msg.challengeId = challenge;

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(response.data.errorMsg == "InstanceLimit"){
				PNotify.removeAll();
				notificationService.error("You reached your limit for exercises you can run at the same time.");
				$('.waitLoader').hide();
				$("#startExerciseModal").modal('hide');
			}
			else if(response.data.errorMsg == "CreditsLimit"){
				PNotify.removeAll();
				notificationService.error("You don't have any credits left. Please contact support.");
				$('.waitLoader').hide();
				$("#startExerciseModal").modal('hide');
			}
			else if(response.data.errorMsg == "NoResourcesAvailable"){
				PNotify.removeAll();
				notificationService.error("The are no resources available to start the exercise in the selected region, please try again later or select a different region.");
				$('.waitLoader').hide();
				$("#startExerciseModal").modal('hide');
			}
			else if(response.data.errorMsg == "GWUnavailable" || response.data.errorMsg == "RegionNotFound"){
				PNotify.removeAll();
				notificationService.error("The environment could not be started in the selected region as the gateway is not responding, please try selecting a different region.");
				$('.waitLoader').hide();
				$("#startExerciseModal").modal('hide');
			}
			else if(response.data.errorMsg == "ExerciseUnavailable"){
				PNotify.removeAll();
				notificationService.error("The exercise requested is unavailable for your organization, please try again or contact support.");
				$('.waitLoader').hide();
				$("#startExerciseModal").modal('hide');
			}
			else if(response.data.errorMsg == "AlreadyRun"){
				PNotify.removeAll();
				notificationService.error("The exercise requested is part of an active Challenge and it has already been run.");
				$('.waitLoader').hide();
				$("#startExerciseModal").modal('hide');
			}
			else if(response.data.errorMsg!=undefined){
				PNotify.removeAll();
				notificationService.error("The RTF environment could not be started, please try again or contact support.");
				$('.waitLoader').hide();
				$("#startExerciseModal").modal('hide');
			}
			else{
				PNotify.removeAll();
				$rootScope.$broadcast('instanceStarted:updated',response.data);
				notificationService.info("We're preparing your RTF environment, use this time to read the exercise's instructions.");
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getUserReservations = function(){


		var msg = {};
		msg.action = 'getUserReservations';   

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(undefined!=response.data){
				$rootScope.$broadcast('userReservations:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getRunningExercises = function(id, regionRaw){
		var msg = {};
		msg.action = 'getRunningExercises';   

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(undefined!=response.data){
				$rootScope.$broadcast('runningExercises:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getUserHistory = function(){
		var msg = {};
		msg.action = 'getUserHistory';   

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(undefined!=response.data){
				$rootScope.$broadcast('userHistory:updated',response.data);
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}

	this.getUserHistoryDetails = function(id){
		var msg = {};
		msg.action = 'getUserHistoryDetails';   
		msg.id = id;

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(undefined!=response.data){
				$rootScope.$broadcast('userHistoryDetails:updated',response.data);
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
				notificationService.notice('Updated failed, please try again.');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});

	}

	this.sendFeedback = function(exerciseInstance,message){
		var msg = {};
		msg.action = 'addFeedback';   
		msg.id = exerciseInstance;
		msg.feedback = message;

		var req = {
				method: 'POST',
				url: '/user/handler',
				data: msg,
		}
		$http(req).then(function successCallback(response) {
			if(undefined!=response.data && !response.data.errorMsg ){
				PNotify.removeAll();
				notificationService.success('Thanks for your feedback!');
			}
			else{
				PNotify.removeAll();
				notificationService.notice('Your feedback could not be sent, please try again. You can submit one feedback per exercise');
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getInitialData = function(){  
		this.getCountries();
		this.getUserProfile();
		this.getUserAchievements();
		this.getUserStats();
		this.getAvailableExercises();
		this.getChallenges();
		this.getRunningExercises();
		this.getUserReservations();
		this.getUserTeamLeaderboard();
		this.getTeamStats();
		this.getUserHistory();
		this.getUnreadNotifications();
	}
	this.updateData = function(){
		if($rootScope.ctoken == ""){
			return;
		}
		console.log('updating data...')
		this.getUnreadNotifications();
		this.getAvailableExercises($this);
		this.getChallenges();
		this.getUserHistory($this);
	}
	$interval(this.updateData($this),120000)
});

rtf.factory('xhrInterceptor', ['$q','$rootScope', function($q, $rootScope) {
	return {
		'request': function(config) {
			if(config.url == "/user/handler" && config.data.action !=undefined && config.data.action != "getUserCToken")
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

rtf.controller('navigation',['$rootScope','$scope','server','$timeout','$http',function($rootScope,$scope,server,$timeout,$http){

	$scope.logout = function(){
		server.doUserLogout();
	}


	$rootScope.getDateInCurrentTimezone = function(date,format){
		return moment(date).local().format(format);
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
	$scope.userCredits = -1;
	$scope.$on('userInfo:updated', function(event,data) {
		$scope.userCredits = data.credits; 
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
			$rootScope.visibility.assignedExercises = false;
			$rootScope.visibility.history = false;
			$rootScope.visibility.achievements = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exerciseDetails = false;
			$rootScope.visibility.welcome = true;
			$rootScope.visibility.home = true;
			$rootScope.visibility.runningExercises = false;
			$rootScope.visibility.leaderboard = false;
			$rootScope.visibility.challenges = false;
			_st(responsiveView,200);
			$(window).scrollTop(0);
			break;
		case "challenges":
			if(target[1]=="details" && undefined!=target[2] && ""!=target[2]){
				var exId = target[2]
				if($rootScope.ctoken == ""){
					$http(ctokenReq).then(function successCallback(response) {
						$rootScope.ctoken = response.data.ctoken;
						$rootScope.selectedChallenge = exId;
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
			$rootScope.visibility.assignedExercises = false;
			$rootScope.visibility.history = false;
			$rootScope.visibility.achievements = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exerciseDetails = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.runningExercises = false;
			$rootScope.visibility.leaderboard = false;
			$rootScope.visibility.challenges = true;
			$(window).scrollTop(0);
			break;
		case "exercises":
			if(target[1]=="details"){
				$rootScope.visibility.assignedExercises = false;
				$rootScope.visibility.exerciseDetails = true;
				$rootScope.visibility.runningExercises = false;    
				$rootScope.visibility.welcome = false;
				$rootScope.visibility.home = false;
				$rootScope.visibility.history = false;
				$rootScope.visibility.achievements = false;
				$rootScope.visibility.settings = false;
				$rootScope.visibility.challenges = false;
				$rootScope.visibility.leaderboard = false;
				if(Number.isInteger(parseInt(target[2])) && undefined==$rootScope.exerciseDetails.id){
					getExDetails(parseInt(target[2]));
				}
			}
			else{
				$rootScope.visibility.welcome = false;
				$rootScope.visibility.home = false;
				$rootScope.visibility.assignedExercises = true;
				$rootScope.visibility.history = false;
				$rootScope.visibility.achievements = false;
				$rootScope.visibility.settings = false;
				$rootScope.visibility.challenges = false;
				$rootScope.visibility.exerciseDetails = false;
				$rootScope.visibility.runningExercises = false;    
				$rootScope.visibility.leaderboard = false;
				$rootScope.exerciseStarted = false;
				$rootScope.ready = false;
				$rootScope.alreadyLaunched = false;
				$(window).scrollTop(0);
			}
			break;
		case "running":
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.assignedExercises = false;
			$rootScope.visibility.runningExercises = true;
			$rootScope.visibility.history = false;
			$rootScope.visibility.achievements = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.exerciseDetails = false;
			$rootScope.visibility.leaderboard = false;
			$(window).scrollTop(0);
			break;
		case "history":
			$rootScope.visibility.runningExercises = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.assignedExercises = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.history = true;
			$rootScope.visibility.achievements = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exerciseDetails = false;
			$rootScope.visibility.leaderboard = false;
			$(window).scrollTop(0);
			break;
		case "achievements":
			$rootScope.visibility.leaderboard = false;
			$rootScope.visibility.runningExercises = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.assignedExercises = false;
			$rootScope.visibility.history = false;
			$rootScope.visibility.achievements = true;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exerciseDetails = false;
			$(window).scrollTop(0);
			break;
		case "settings":
			$rootScope.visibility.leaderboard = false;
			$rootScope.visibility.runningExercises = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.assignedExercises = false;
			$rootScope.visibility.history = false;
			$rootScope.visibility.achievements = false;
			$rootScope.visibility.settings = true;
			$rootScope.visibility.exerciseDetails = false;
			$(window).scrollTop(0);
			break;
		case "leaderboard":
			$rootScope.visibility.leaderboard = true;
			$rootScope.visibility.runningExercises = false;
			$rootScope.visibility.welcome = false;
			$rootScope.visibility.home = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.assignedExercises = false;
			$rootScope.visibility.history = false;
			$rootScope.visibility.achievements = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exerciseDetails = false;
			$(window).scrollTop(0);
			break;
		default:{
			$rootScope.visibility.runningExercises = false;    
			$rootScope.visibility.assignedExercises = false;
			$rootScope.visibility.history = false;
			$rootScope.visibility.challenges = false;
			$rootScope.visibility.achievements = false;
			$rootScope.visibility.settings = false;
			$rootScope.visibility.exerciseDetails = false;
			$rootScope.visibility.welcome = true;
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
			server.getInitialData();
		}, function errorCallback(response) {
			console.log('ajax error');
		});


	}

	$rootScope.visibility = {}
	$rootScope.visibility.welcome = true;
	$rootScope.visibility.home = true;
	$rootScope.visibility.assignedExercises = false;
	$rootScope.visibility.history = false;
	$rootScope.visibility.achievements = false;
	$rootScope.visibility.settings = false;
	$rootScope.visibility.exerciseDetails = false;
	$rootScope.visibility.runningExercises = false;    
	$rootScope.visibility.leaderboard = false;
}])

rtf.controller('welcome',['$scope','server',function($scope,server){
	$scope.user = server.user;
}])
rtf.controller('home',['$scope','server',function($scope,server){
	$scope.user = server.user;
}])
rtf.controller('running',['$scope','server','$rootScope',function($scope,server,$rootScope){
	$scope.runningInstances = [];
	$scope.$on('runningExercises:updated', function(event,data) {
		$scope.runningInstances = data;
	})
	$scope.stopExercise = function(id){
		$('.waitLoader').show();
		server.stopInstance(id);
	}
	$scope.resumeExercise = function(exId,avId){
		$rootScope.exInstanceId = exId;
		server.getExerciseDetails(avId);
		$rootScope.exerciseStarted = true;
		$rootScope.ready = true;
		$rootScope.alreadyLaunched = false;
	}
}])


rtf.controller('leaderboard',['$scope','server','$rootScope',function($scope,server,$rootScope){
	$scope.leaderboardData = [];
	$scope.user = server.user;
	$scope.$on('leaderboard:updated', function(event,data) {
		$scope.leaderboardData = data.leaderboard;
	});

	$scope.leaderboardtableconfig = {
			itemsPerPage: 30,
			fillLastPage: false
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
				$scope.remediatedPerCategory.data[1].push(Math.ceil(data.totalMinutesPerIssueCategory[tmpName]/60));
				$scope.remediatedPerCategory.data[2].push(data.avgMinutesPerIssueCategory[tmpName]);
			}
		}
		$scope.remediatedPerCategory.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerCategory.options.title.display = false;
		$scope.remediatedPerCategory.series = ["Remediated (%)","Total Time (hours)","Avg Time (minutes)"];

	});


}]);


rtf.controller('exercises',['$scope','server','$route','$rootScope','$interval','$location','$http','notificationService','$sce',function($scope,server,$route,$rootScope,$interval,$location,$http,notificationService,$sce){
	$scope.user = server.user;
	$scope.exercises = [];
	$rootScope.exerciseStarted = false;
	$rootScope.ready = false;
	$rootScope.alreadyLaunched = false;
	$scope.currentDemoToken = server.currentDemoToken;
	$scope.guacToken;
	$scope.exerciseIsInChallenge = false;
	$scope.resultStatusData = {}
	$rootScope.exInstanceId;
	$scope.asNotStarted = false;
	$scope.flagIsVulnerable = false;
	$rootScope.exerciseDetails = {};
	$scope.currentExerciseResults = server.currentExerciseResults;
	$scope.emptyExercises = true;
	$scope.availableRegions = ["Unavailable"];
	$scope.selectedRegion = "";
	$scope.assignedExercises = [];
	$rootScope.takenExercises = [];
	var exerciseTechnologies = [];
	$scope.currentSearch = "";
	$scope.currentTechnology = "";
	$scope.availableExercises = [];

	$rootScope.getColorForScore = function(score){
		if(score<=20)
			return "#7693c1f0";
		if(score<=50)
			return "#9e4a72de";
		if(score<=75)
			return "#7676c1f0";
		if(score<=100)
			return "#6a6a7dde";
		if(score<=125)
			return "#381f08de"

			return "#bd6c22de";
	}


	$scope.stopExercise = function(id){
		$('.waitLoader').show();
		server.stopInstance(id);
	}
	$scope.didTakeExercise = function(eid){
		return $rootScope.takenExercises.includes(eid); 
	}

	$scope.userAssignedExercises = [];

	Array.prototype.uniqueId = function() {
		var a = this.concat();
		for(var i=0; i<a.length; ++i) {
			for(var j=i+1; j<a.length; ++j) {
				if(a[i].exercise.id === a[j].exercise.id)
					a.splice(j--, 1);
			}
		}

		return a;
	};

	$scope.$on('assignedExercises:updated', function(event,data) {
		if(null == data){
			$scope.userAssignedExercises = [];
			return;   
		}
		for(var i=0;i<data.length;i++){
			if(!data[i].exercise){
				try{
					data.remove(i,i);
				}catch(err){
					continue;
				}
			}
			else{
				data[i].t = "u";
			}
		}

		$scope.userAssignedExercises = data;
		$scope.assignedExercises = userAssignedExercises;

	})
	$scope.getAssignedString = function(s){
		if(s=="u"){
			return "Assigned to you";
		}
		else if(s=="t")
			return "Assigned to your team";
	}


	$scope.getExerciseStatusString = function(status){
		switch(status){
		case "0":
			return "Available"
		case "1":
			return "Updated"
		case "2":
			return "Coming Soon"
		case "3":
			return "Not active"
		case "4":
			return "Available"
		default:
			break;
		}

	}
	$scope.exercisesTech = [];
	$scope.showTechnologies = function(data){
		exerciseTechnologies = [];
		$scope.exercisesTech = [];
		for(var i in data){
			if(data.hasOwnProperty(i) && undefined!=data[i].technology){
				if(exerciseTechnologies.indexOf(data[i].technology)<0)
					exerciseTechnologies.push(data[i].technology)
			}
		}

		var size = 3;
		while (exerciseTechnologies.length > 0){
			$scope.exercisesTech.push(exerciseTechnologies.splice(0, size));
		}

		if($scope.exercisesTech.length<=0){
			$scope.emptyExercises = true;
		}
		else{
			$scope.emptyExercises = false;
		}
		$scope.avExercisesList = false;
		$scope.avExercisesTech = true;
	};
	$scope.backToTechnologies = function(){
		$scope.currentTechnology = "";
		$scope.currentSearch = "";
		$scope.avExercisesList = false;
		$scope.avExercisesTech = true;
		$location.path("exercises", false);
	}

	$scope.showExercisesForTechnology = function(tech){
		$scope.currentTechnology = tech;
		$scope.exercises = [];
		var tmpExercises = [];
		for(var i in $scope.availableExercises){
			if($scope.availableExercises.hasOwnProperty(i)){
				if($scope.availableExercises[i].technology==tech)
					tmpExercises.push($scope.availableExercises[i])
			}
		}
		for(var j=0;j<tmpExercises.length;j++){
			if(!tmpExercises[j].id){
				try{
					tmpExercises.remove(j,j);
					j--;
				}catch(err){
					continue;
				}
			}
		}
		var size = 3;
		while (tmpExercises.length > 0){
			$scope.exercises.push(tmpExercises.splice(0, size));
			$scope.emptyExercises = false;
		}
		if($scope.exercises.length<=0){
			$scope.emptyExercises = true;
		}
		$scope.avExercisesTech = false;
		$scope.avExercisesList = true;
	}
	$scope.showExercisesForTags = function(){
		if($scope.currentSearch.length!=0 && $scope.currentSearch.length<3){
			return;
		}
		var tmpExercises = [];
		$scope.exercises = [];
		if($scope.currentSearch.length==0){
			for(var n in $scope.availableExercises){
				if($scope.availableExercises.hasOwnProperty(n)){
					if($scope.currentTechnology!=""){
						if($scope.availableExercises[n].technology==$scope.currentTechnology)
							tmpExercises.push($scope.availableExercises[n]);
					}
					else{
						tmpExercises.push($scope.availableExercises[n]);
					}
				}
			}
		}
		else{
			for(var i in $scope.availableExercises){
				var added = false;
				if($scope.availableExercises.hasOwnProperty(i)){
					if($scope.currentTechnology!="" && ($scope.availableExercises[i].technology!=$scope.currentTechnology))
						continue;
					if(undefined!=$scope.availableExercises[i].tags){
						for(var j in $scope.availableExercises[i].tags){
							if($scope.availableExercises[i].tags.hasOwnProperty(j) && $scope.availableExercises[i].tags[j].toLowerCase().indexOf($scope.currentSearch.toLowerCase())>=0){
								tmpExercises.push($scope.availableExercises[i]);
								added = true;
							}
						}
					} 
					if(!added && undefined!=$scope.availableExercises[i].title && $scope.availableExercises[i].title.toLowerCase().indexOf($scope.currentSearch.toLowerCase())>=0){
						tmpExercises.push($scope.availableExercises[i]);
						added = true;
					}
					if(!added && undefined!=$scope.availableExercises[i].subtitle && $scope.availableExercises[i].subtitle.toLowerCase().indexOf($scope.currentSearch.toLowerCase())>=0){
						tmpExercises.push($scope.availableExercises[i]);
						added = true;
					}
					if(!added && undefined!=$scope.availableExercises[i].description && $scope.availableExercises[i].description.toLowerCase().indexOf($scope.currentSearch.toLowerCase())>=0){
						tmpExercises.push($scope.availableExercises[i]);
						added = true;
					}
				}
			}
		}
		for(var j=0;j<tmpExercises.length;j++){
			if(!tmpExercises[j].id){
				try{
					tmpExercises.remove(j,j);
					j--;
				}catch(err){
					continue;
				}
			}
		}
		var size = 3;
		while (tmpExercises.length > 0){
			$scope.exercises.push(tmpExercises.splice(0, size));
			$scope.emptyExercises = false;
		}
		if($scope.exercises.length<=0){
			$scope.emptyExercises = true;
		}
		$scope.avExercisesTech = false;
		$scope.avExercisesList = true;
		$location.path("exercises/search/"+$scope.currentSearch, false);

	}



	$scope.$on('exercises:updated', function(event,data) {
		if(undefined!=data){
			$scope.exercises = [];
			$scope.availableExercises = data;
			$scope.showTechnologies(data);
		}

	});

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
		$scope.selectedRegion = "";
		$rootScope.exerciseDetails = data;
		$location.path("exercises/details/"+$rootScope.exerciseDetails.id, false);
		$(window).scrollTop(0);
	});

	$scope.$on('hintReceived:updated', function(event,data) {
		if(undefined!=data){
			$scope.hintData = data;
			$("#hintReceivedModal").modal('show');
		}
	});

	$scope.downloadAPIReference = function(id){
		server.downloadExerciseReference(id);
	}


	$scope.getSelfChekResult = function(selfCheckName){
		if(undefined==selfCheckName || undefined==$scope.resultStatusData.length || $scope.resultStatusData.length==0){
			return "Not Available";
		}
		if(selfCheckName)
			return "Vulnerable";
		else
			return "Not Vulnerable";
	}
	$scope.getRemedationTableClassFromSelfCheckName = function(selfCheckName){
		if(undefined==selfCheckName || undefined==$scope.resultStatusData.length || $scope.resultStatusData.length==0){
			return "";
		}
		if(selfCheckName)
			return "status-vulnerable";
		else
			return "status-remediated";
	}
	$scope.isAvailable = function(selfCheckStatus){
		if(undefined==selfCheckStatus)
			return false
			return true;
	}


	$scope.$on('resultStatusReceived:updated', function(event,data) {
		$('.waitLoader').hide();
		$scope.flagIsVulnerable = false;
		if(undefined!=data && undefined!=data.selfcheck && data.selfcheck.length==0){
			$scope.asNotStarted = true;
		}
		else{
			selfcheckData = data.selfcheck;
			scList = [];
			for(var i in $rootScope.exerciseDetails.flags){
				for(var j in $rootScope.exerciseDetails.flags[i].flagList){
					var sc = $rootScope.exerciseDetails.flags[i].flagList[j].selfCheckName;
					if(undefined!=sc)	{
						scList.push(sc);
						for(var n=0;n<selfcheckData.length;n++){
							if(sc==selfcheckData[n].name){
								$rootScope.exerciseDetails.flags[i].flagList[j].selfCheckStatus = selfcheckData[n].status;
								if(selfcheckData[n].status)
									$scope.flagIsVulnerable = true;
								break;
							}
						}
					}
				}
			}
			var selfcheckData = data.selfcheck;
			for(var n=0;n<selfcheckData.length;n++){
				if(scList.indexOf(selfcheckData[n].name)<0){
					selfcheckData.remove(n,n);
					n = n-1;
				}
				else{
					if(selfcheckData[n].status)
						$scope.flagIsVulnerable = true;
				}
			}
			$scope.resultStatusData = selfcheckData;
			$scope.asNotStarted = false;
		}

	});


	$scope.$on('exerciseRegions:updated', function(event,data) {
		if(data!=null){
			$scope.availableRegions = [];
			$scope.regionsPingTimes = [];
			$scope.totalAvailableRegions = 0;
			for(var j in data){
				if(!data.hasOwnProperty(j))
					continue;
				$scope.totalAvailableRegions++;
				var p = new Ping();
				var fqdn = data[j].fqdn;
				p.ping("https://"+fqdn, data[j].name, function(err, ping,f,r) {
					console.log(f+" "+r+" "+ping)
					if(ping<3000){
						var obj = {};
						obj.name = r;
						obj.fqdn = f.replace("https://","");
						obj.ping = ping;
						$scope.regionsPingTimes.push(obj)
					}
					else{
						$scope.totalAvailableRegions--;
					}
				});
			}
		}
	});

	var technologyClassMap = {
			"NodeJS": "nodejsb",
			"Java": "javab",
			"Ruby": "rubyb",
			"Python": "pythonb",
			"Go Lang":"golangb",
			"PHP":"phpb",
			".NET":"dotnetb"
	};
	var pictForTechnology = {
			"NodeJS": "/assets/img/nodejs.png",
			"Java": "/assets/img/java.png",
			"Ruby": "/assets/img/ruby.png",
			"Python": "/assets/img/python.png",
			"Go Lang":"/assets/img/golang.png",
			"PHP":"/assets/img/php.png",
			".NET":"/assets/img/dotnet.png"
	};

	var remediationClassMap = {
			false: "table-success",
			true: "table-danger"
	}
	$scope.getPictureForTechnology = function(technology){
		return pictForTechnology[technology]
	}
	$scope.getRemedationTableClass = function(status) {
		return remediationClassMap[status]
	};

	$rootScope.getExerciseClass = function(technology) {
		return technologyClassMap[technology];
	};

	$scope.getFormattedEnd = function(date){
		var out = moment(date).format("dddd, MMMM Do YYYY");
		return out;
	};

	$rootScope.getExerciseDetails = function(exId){
		$rootScope.exerciseStarted = false;
		$rootScope.ready = false;
		$rootScope.alreadyLaunched = false;
		server.getExerciseDetails(exId);
		server.getRegionsForExercise(exId);
	}
	$scope.startExerciseModal = function(exerciseId){
		$('.waitLoader').show();
		$scope.exerciseIsInChallenge = false;
		server.isExerciseIsInChallenge(exerciseId);
	}

	$scope.$on('exerciseIsInChallenge:updated', function(event,data) {
		$('.waitLoader').hide();
		$scope.exerciseIsInChallenge = data.result;
		$("#startExerciseModal").modal('show');
	});
	$scope.startInstance = function(id){

		if($scope.regionsPingTimes.length==$scope.totalAvailableRegions){
			$('.waitLoader').show();
			server.startInstance(id, $scope.regionsPingTimes, $rootScope.selectedChallenge);
		}
		else{
			$('.waitLoader').show();
			setTimeout(function () {
				$scope.startInstance(id);
			},500);
		}
	}

	var popupBlockerChecker = {
			check: function(popup_window){
				var _scope = this;
				if (popup_window) {
					if(/chrome/.test(navigator.userAgent.toLowerCase())){
						setTimeout(function () {
							_scope._is_popup_blocked(_scope, popup_window);
						},200);
					}else{
						popup_window.onload = function () {
							_scope._is_popup_blocked(_scope, popup_window);
						};
					}
				}else{
					_scope._displayError();
				}
			},
			_is_popup_blocked: function(scope, popup_window){
				if ((popup_window.innerHeight > 0)==false){ scope._displayError(); }
			},
			_displayError: function(){
				PNotify.removeAll();
				notificationService.error("A Popup Blocker is preventing opening a new tab for your RTF environment. Please add this site to your exception list.");
			}
	};
	function getCountdownString(wait){
		var w;
		try { w = parseInt(wait) } catch(err){ return ""; }
		if(w<10){
			return "00:0"+w;
		}
		if(w<60){
			return "00:"+w;
		}
		if(w<600){
			return "0"+Math.floor(w/60)+":"+(w % 60);
		}

	}

	$scope.$on('tokenRefreshed:updated', function(event,data) {
		$scope.guacToken = data.token;
		$scope.guacUser = data.user;
		$scope.guacFqdn = data.fqdn;
		try{
			var popup = window.open("/user/exercise.html#id="+data.exInstanceId);  
			popupBlockerChecker.check(popup);
		}catch(err){
			var popup = window.open("/user/exercise.html#id="+data.exInstanceId);  
			popupBlockerChecker.check(popup);
		}	
	});

	$scope.exerciseStopped = function(){
		server.getRunningExercises();
		setTimeout(function () {
			server.getUserHistory();
		},6000);
		$rootScope.exerciseStarted = false;
		$rootScope.ready = false;
		$rootScope.alreadyLaunched = false;
		$rootScope.visibility.welcome = false;
		$rootScope.visibility.home = false;
		$rootScope.visibility.assignedExercises = false;
		$rootScope.visibility.history = false;
		$rootScope.visibility.achievements = false;
		$rootScope.visibility.settings = false;
		$rootScope.visibility.exerciseDetails = false;
		$rootScope.visibility.leaderboard = false;
		$rootScope.visibility.runningExercises = true;
	}

	$scope.$on('exerciseStopped:updated', function(event,data) {
		$scope.exerciseStopped();
	});

	$scope.reservationPolled = false;

	function handleInstanceStart(data){

		$rootScope.exerciseStarted = true;
		$rootScope.ready = false;
		$rootScope.alreadyLaunched = false;

		if(undefined!=data && data.fulfilled==true){
			server.getUserProfile();

			$scope.guacToken = data.connection.token;
			$scope.countdown = getCountdownString(data.connection.countdown);
			var preload = data.connection.preload;
			$scope.guacUser = data.connection.user;
			$scope.guacFqdn = data.connection.fqdn;
			$rootScope.exInstanceId = data.connection.exInstanceId;
			jQuery(function ($) {
				var cDown = data.connection.countdown,
				display = $('#cdown');
				function startTimer(duration, display, preload) {
					var timer = duration, minutes, seconds;
					intervalT = $interval(function () {
						minutes = parseInt(timer / 60, 10)
						seconds = parseInt(timer % 60, 10);

						minutes = minutes < 10 ? "0" + minutes : minutes;
						seconds = seconds < 10 ? "0" + seconds : seconds;

						display.text(minutes + ":" + seconds);
						if(minutes==0 && seconds==0){
							$interval.cancel(intervalT);
							$rootScope.ready = true;
							$scope.reservationPolled = false;
							PNotify.removeAll();
							notificationService.notify({
								text: 'Your RTF environment is ready.',
								type: 'success'
							});
							$scope.asNotStarted = false;
							return;
						}
						if(preload && minutes==0 && seconds<=25){
							setupPreload();
						}
						if (--timer < 0) {
							timer = duration;
						}
					}, 1000);
				}
				startTimer(cDown, display, preload);
				server.getRunningExercises();
				server.getUserReservations();
			});
			$('.waitLoader').hide();
			$("#startExerciseModal").modal('hide');
		}
		else if(data.fulfilled==false && data.error==false){
			$scope.countdown = getCountdownString(data.waitSeconds);
			$rootScope.exerciseStarted = true;
			$rootScope.ready = false;
			$rootScope.alreadyLaunched = false;
			if($scope.reservationPolled){
				PNotify.removeAll();
				notificationService.notice("Your RTF environment is not yet ready, please hold on...");
			}
			jQuery(function ($) {
				var cDown = data.waitSeconds,
				display = $('#cdown');
				idReservation = data.idReservation;
				function startTimer(duration, display,idReservation) {
					var timer = duration, minutes, seconds;
					intervalT = $interval(function () {
						minutes = parseInt(timer / 60, 10)
						seconds = parseInt(timer % 60, 10);

						minutes = minutes < 10 ? "0" + minutes : minutes;
						seconds = seconds < 10 ? "0" + seconds : seconds;

						display.text(minutes + ":" + seconds);
						if(minutes==0 && seconds==0){
							$interval.cancel(intervalT);
							pollReservation(idReservation);	
							$scope.reservationPolled = true;
							return;
						}
						if (--timer < 0) {
							timer = duration;
						}
					}, 1000);
				}
				startTimer(cDown, display,idReservation);
			});
			$('.waitLoader').hide();
			$("#startExerciseModal").modal('hide');
		}
		else{
			PNotify.removeAll();
			notificationService.error('Something went wrong. The exercise could not be launched, please try again.');
		}
	}

	$scope.$on('userReservations:updated', function(event,data) {
		for(var i=0;i<data.length;i++){
			if(data[i].error==false){
				pollReservation(data[i].idReservation);
			}
		}
	});

	function pollReservation(data){
		server.pollReservation(data)
	};

	$scope.$on('instanceStarted:updated', function(event,data) {
		// new reservation logic
		if(undefined!=data && data!=""){
			handleInstanceStart(data);
		}
	});

	$scope.preloaded=false;
	function setupPreload(){
		if($scope.preloaded)
			return;
		$scope.preloaded=true;
		var src = "https://"+$scope.guacFqdn+"/rtf/cservlet?u="+$scope.guacUser+"&t="+$scope.guacToken;  		
		$('#preload').html("<iframe id='preloadIframe' class='preloadIframe' src='"+src+"'></iframe>");
	}
	$scope.refreshResults = function(){
		$('.waitLoader').show();
		server.getResultStatus($rootScope.exInstanceId);
	};
	$scope.launchEnvironment = function(){
		$('#preloadIframe').remove();
		$rootScope.alreadyLaunched = true;
		server.refreshGuacToken($rootScope.exInstanceId);
	};
	$scope.getHint = function(){
		server.getHintForQuestion($scope.hintFlagQuestionId, $rootScope.exInstanceId);
		$('#getHintModal').modal('hide');
	};
	$scope.getHintModal = function(id){
		$scope.hintFlagQuestionId = id;
		$('#getHintModal').modal('show');
	};

	$scope.getResult = function(selfCheckName){
		return "Not Available";
	};

}])
rtf.controller('history',['$scope','server','$rootScope','$filter','$location',function($scope,server,$rootScope,$filter,$location){
	$scope.user = server.user;
	$scope.showCodeDiff = false;
	$scope.showLogs = false;
	$scope.showResults = false;
	$scope.selectedResultRow = -1;
	$scope.feedbackMessage = "";
	$scope.emptyDiff = false;
	$scope.emptyLog = false;
	$scope.zipError = false;
	$rootScope.cancelledExercisesInHistory = false;

	$scope.userHistoryDetails = {}

	$scope.masterCompletedReviews = [];
	$scope.filteredCompletedList = []; 

	$scope.getUserHistory = function(){
		server.getUserHistory();
	}

	$scope.notCorrect = function(flag,exercise){

	}

	$scope.updateFilteredList = function() {
		$scope.filteredCompletedList = $filter("filter")($scope.masterCompletedReviews, $scope.query);
	};

	$scope.tableconfig = {
			itemsPerPage: 8,
			fillLastPage: false
	}

	$scope.getScoringModeString = function(code){
		switch(parseInt(code)){
		case 0:
			return "Automated";
		case 1:
			return "Automated";
		case 2:
			return "Manual";
		default: 
			return "Manual";
		}
	}

	$scope.getExerciseStatusString = function(status){
		switch(status){
		case "STOPPED":
			return "Pending Review";
			break;
		case "CANCELLED":
			return "Cancelled";
			break;
		case "REVIEWED":
			return "Completed";
			break;
		case "AUTOREVIEWED":
			return "Completed";
			break;
		case "REVIEWED_MODIFIED":
			return "Completed";
			break;
		default:
			return "Pending Review";
		break;
		}
	}

	$scope.$on('userHistory:updated', function(event,data) {
		for(var i=0;i<data.length;i++){
			var tmpId = data[i].exercise.id;
			if(!$rootScope.takenExercises.includes(tmpId))
				$rootScope.takenExercises.push(data[i].exercise.id);
		}
		$scope.masterCompletedReviews = data;
		$scope.filteredCompletedList = $filter("filter")($scope.masterCompletedReviews, "!CANCELLED");


		$rootScope.scorePerExercises = {}
		$rootScope.scorePerExercises.options = {}
		$rootScope.scorePerExercises.options.layout = {
				padding: {
					left: 0,
					right: 0,
					top: 0,
					bottom: 0
				}
		}
		$rootScope.scorePerExercises.options.legend = {
				display: true,
				position: 'bottom',
				labels: {
					fontColor: "#000",
					fontSize: 10
				}
		};

		$rootScope.scorePerExercises.datasetOverride = [ {showLine: true, pointStyle:'rectRounded',fill: false,
			pointRadius: 10,pointHoverRadius: 15},{ showLine: true,pointStyle:'rectRounded',fill: false,
				pointRadius: 10,pointHoverRadius: 15}] ;
		$rootScope.scorePerExercises.options.events= ["mousemove", "mouseout", "click", "touchstart", "touchmove", "touchend"];
		$rootScope.scorePerExercises.options.title = { display:true}

		$rootScope.scorePerExercises.options.tooltips = {
				callbacks: {
					// Use the footer callback to display the sum of the items showing in the tooltip
					afterTitle: function(tooltipItems, data) {
						if(undefined==$rootScope.scorePerExercises.exercises[tooltipItems[0].index])
							return "";
						return $rootScope.scorePerExercises.exercises[tooltipItems[0].index];
					}
				},
				footerFontStyle: 'normal'
		};
		$rootScope.scorePerExercises.options.pieceLabel = {
				render: 'value',
				precision: 0,
				showZero: true,
				fontSize: 14,
				fontColor: '#FFF',
				fontStyle: 'normal',
				fontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
				arc: false,
				position: 'border',
				overlap: true
		}

		$rootScope.scorePerExercises.data = [[],[]];
		$rootScope.scorePerExercises.series = ["Score","Duration (minutes)"];
		$rootScope.scorePerExercises.labels = [];
		$rootScope.scorePerExercises.exercises = [];
		for (var property in data) {
			if(data.hasOwnProperty(property) && data[property].status == "CANCELLED")
				$rootScope.cancelledExercisesInHistory = true;
			if (data.hasOwnProperty(property) && (data[property].status == "REVIEWED" || data[property].status == "AUTOREVIEWED" || data[property].status == "REVIEWED_MODIFIED")) {
				$rootScope.scorePerExercises.labels.push(moment(data[property].endTime).format('MMM DD'));	
				$rootScope.scorePerExercises.data[0].push(data[property].score.result);
				if(undefined==data[property].duration)
					data[property].duration=0;
				$rootScope.scorePerExercises.data[1].push(data[property].duration);
				$rootScope.scorePerExercises.exercises.push(data[property].title);

			}
		}
	});

	var remediationClassMap = {
			"Not Vulnerable": "table-success",
			"Vulnerable": "table-danger",
			"Broken Functionality":"table-warning",
			"Not Available":"table-secondary",
			"Not Addressed":"table-info"

	}
	$scope.getRemedationTableClass = function(status) {
		return remediationClassMap[status]
	};
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
		case "3":
			return "Not Available"

		default: 
			if($scope.userHistoryDetails.status == 'STOPPED')
				return "Not Available";
			else
				return "Not Addressed";
		}
	}
	var statusClassMap = {
			"0": "table-success",
			"1": "table-danger",
			"2": "table-warning",
			"4": "table-info",
			"NOT_AVAILABLE":"table-secondary"
	};
	$scope.getStatusClass = function(status) {
		s = statusClassMap[status];
		if(s == 'table-secondary' && ($scope.userHistoryDetails.status == 'AUTOREVIEWED' ||$scope.userHistoryDetails.status == 'REVIEWED_MODIFIED'))
			s = 'table-info';
		return s;
	};

	var scoreClassMap = {	
			success: "success",
			average: "warning",
			failure: "danger",
			pending: "info",
			active: "active"

	};

	$scope.getDurationInterval = function(start,end,dur){
		if(undefined != dur && null != dur && 0 != dur){
			return moment.utc(moment.duration(dur,"m").asMilliseconds()).format("H mm").replace(" ","h")+"'";
		}
		else{
			return moment.utc(moment(end).diff(moment(start))).format("H mm").replace(" ","h")+"'";
		}
	};
	$scope.getDatesInterval = function(start,end){
		var out = moment(start).format("MMM D YYYY, HH:mm");
		out += " - "+moment(end).format("HH:mm");
		return out;
	}

	$scope.getDateFormat = function(start){
		return moment(start).local().format("MMM D YYYY, HH:mm");
	}

	$scope.getResultsScoreClass = function(result,total,status) {
		if(status=="CANCELLED")
			return scoreClassMap["active"];
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
	$scope.downloadSolutions = function(id){
		server.downloadExerciseSolutions(id);
	}

	$scope.leaveFeedbackModal = function(){
		$('#leaveFeedbackModal').modal('show');
	}

	$scope.doLeaveFeedback = function(){
		server.sendFeedback($scope.userHistoryDetails.id, $scope.feedbackMessage);
		$('#leaveFeedbackModal').modal('hide');
	}
	$scope.getScoreString = function(score){
		if(parseInt(score)>=0)
			return score;
		else{
			if($scope.userHistoryDetails.status == "AUTOREVIEWED" || $scope.userHistoryDetails.status == "REVIEWED_MODIFIED")
				return 0;
			return " ? ";
		}
	}

	$scope.getQuestionName = function(resName){
		if($scope.userHistoryDetails.exercise!=undefined){
			for(var i in $scope.userHistoryDetails.exercise.flags){
				if($scope.userHistoryDetails.exercise.flags.hasOwnProperty(i)){
					for(var j in $scope.userHistoryDetails.exercise.flags[i].flagList){
						if($scope.userHistoryDetails.exercise.flags[i].flagList.hasOwnProperty(j) && $scope.userHistoryDetails.exercise.flags[i].flagList[j].selfCheckAvailable && $scope.userHistoryDetails.exercise.flags[i].flagList[j].selfCheckName==resName){
							return $scope.userHistoryDetails.exercise.flags[i].title;
						}
					}
				}
			}
		}
		return resName;
	}
	$scope.getQuestionMaxScore = function(resName){
		if($scope.userHistoryDetails.exercise!=undefined){

			for(var i in $scope.userHistoryDetails.exercise.flags){
				if($scope.userHistoryDetails.exercise.flags.hasOwnProperty(i)){
					for(var j in $scope.userHistoryDetails.exercise.flags[i].flagList){
						if($scope.userHistoryDetails.exercise.flags[i].flagList.hasOwnProperty(j) && $scope.userHistoryDetails.exercise.flags[i].flagList[j].selfCheckAvailable && $scope.userHistoryDetails.exercise.flags[i].flagList[j].selfCheckName==resName){
							return $scope.userHistoryDetails.exercise.flags[i].flagList[j].maxScore;
						}
					}
				}
			}
		}
	}

	$scope.complaintExerciseId = -1;
	$scope.complaintExerciseFlag = "";

	$scope.openComplaintModal = function(e,f){
		$scope.complaintExerciseId = e;
		$scope.complaintExerciseFlag = f;
		$('#scoringComplaintModal').modal('show');
	}

	$scope.sendScoringComplaint = function(){
		var obj = {};
		obj.name = $scope.complaintExerciseFlag;
		obj.text = $scope.complaintModalTextInput;
		obj.id = $scope.complaintExerciseId;
		server.addScoringComplaint(obj);
		$('#scoringComplaintModal').modal('hide');
	}

	$scope.$on('scoringComplaintAdded:updated', function(event,data) {	
		server.getUserHistoryDetails($scope.userHistoryDetails.id);
		$scope.complaintModalTextInput = "";
	});
	$scope.$on('userHistoryDetails:updated', function(event,data) {	

		if(data.results.length == 0){
			data.results = [];
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
		$scope.showCodeDiff = false;
		$scope.showLogs = false;
		$scope.userHistoryDetails = data;
		$scope.showResults = true;
		$scope.emptyDiff = false;
		$scope.emptyLog = false;
		$scope.zipError = false;

		var offset = $('#infoArea').offset();
		$('html, body').animate({
			scrollTop: offset.top + 200,
			scrollLeft: offset.left
		});
		var tba = "";

		JSZipUtils.getBinaryContent($scope.userHistoryDetails.id,$rootScope.ctoken, '/user/handler','getUserHistoryDetailsFile', function(err, data) {
			if(err) {
				$scope.zipError = true;
				return; // or handle err
			}

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
									tba += '<li ng-non-bindable class="list-group-item">' + htmlEncode(datas[i]) + '</li>';
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
		});    
	})
	$scope.showResultsFor = function(eId, index){
		server.getUserHistoryDetails(eId);
		$scope.selectedResultRow = index;
		$location.path("history/"+eId, false);
	}

	function htmlEncode(value){
		return $('<div/>').text(value).html();
	}    
}])
rtf.controller('achievements',['$scope','server','$rootScope',function($scope,server,$rootScope){
	$scope.user = server.user;
	$scope.achievementsData = server.achievementsData;
	$scope.trophies = [];
	$scope.emptyTrophy = true;

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
	$scope.options.layout = {
			padding: {
				left: 0,
				right: 0,
				top: 0,
				bottom: 0
			}
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

	$rootScope.$watch('scorePerExercises', function(newValue, oldValue) {
		$scope.scorePerExercises = $rootScope.scorePerExercises;
	});
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
				try{
					var tmpName = $scope.remediationRatePerCategory[j].options.title.text;
					var tmpData = Math.ceil(data.totalMinutesPerIssueCategory[tmpName]);
					if(!isNaN(tmpData) && tmpName!= "null"){
						$scope.remediatedPerCategory.labels.push(tmpName);
						$scope.remediatedPerCategory.data[0].push(tmpData);
					}
				}catch(err){}
			}
		}
		$scope.remediatedPerCategory.options = cloneObj($scope.radarOptions);
		$scope.remediatedPerCategory.options.title.display = false;
		$scope.remediatedPerCategory.series = ["Total Time (minutes)"];
	});


	$scope.$on('achievements:updated', function(event,data) {
		if(undefined!=data.trophies){
			$scope.trophies = [];
			var trophiesArray = deepCopy(data.trophies);
			var size = 4;
			while (trophiesArray.length > 0){
				$scope.trophies.push(trophiesArray.splice(0, size));
				$scope.emptyTrophy = false;
			}
		}
		if($scope.trophies.length<=0){
			$scope.emptyTrophy = true;
		}
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

	$scope.updateUserPassword = function(){
		server.updateUserPassword($scope.userPasswordForm.oldPassword.$modelValue, $scope.userPasswordForm.newPassword.$modelValue);
		$scope.oldPassword = "";
		$scope.newPasswordRepeat = "";
		$scope.newPassword = "";
	}
}]);
rtf.controller('challenges',['$scope','server','$rootScope','$location','$filter','$interval',function($scope,server,$rootScope,$location,$filter,$interval){

	$scope.user = server.user;
	$rootScope.selectedChallenge = -1;
	$scope.filteredChallengesList = [];
	$scope.masterChallengesList = [];
	$rootScope.showChallengesList = true;
	$rootScope.showChallengeDetails = false;
	$scope.showCompleted = false;
	$scope.noActiveChallenges = true;

	$scope.challengeResults = {};
	$rootScope.challengeDetails = [];


	$scope.$watch("showCompleted", function() {
		$scope.updateFilteredList();
	});	

	$scope.getChallengeDetailsButton = function(id){
		$('.waitLoader').show();
		$rootScope.selectedChallenge = id;
		server.getChallengeDetails(id);
	}
	$scope.getChallengeDetails = function(id){
		$rootScope.selectedChallenge = id;
		server.getChallengeDetails(id);
	}

	$rootScope.getChallengeStatusString = function(status){
		switch(status){
		case "IN_PROGRESS":
			return "In Progress";
			break;
		case "NOT_STARTED":
			return "Not Started";
			break;
		case "FINISHED":
			return "Completed";
			break;
		}
	}

	$scope.didTakeExercise = function(exId){
		for(var e in $rootScope.challengeDetails.runExercises){
			if($rootScope.challengeDetails.runExercises.hasOwnProperty(e)){
				if($rootScope.challengeDetails.runExercises[e].user.user == $scope.user.user && $rootScope.challengeDetails.runExercises[e].exercise.id==exId){
					return true;
				}
			} 
		}
		return false;
	}

	$rootScope.challengeDetails = [];
	var challengeUpdateTimer = null;




	$scope.$on('challengeDetails:updated', function(event,data) {

		$('.waitLoader').hide();

		data.flags = [];
		data.theads = [];

		$scope.exercises = [];
		var tmpExercises = [];
		for(var i in data.exercises){
			if(data.exercises.hasOwnProperty(i) && data.exercises[i].id)
				tmpExercises.push(data.exercises[i])
		}
		var size = 3;
		while (tmpExercises.length > 0){
			$scope.exercises.push(tmpExercises.splice(0, size));
		}

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
		data.userRunFlags = 0;
		var userRemediated = 0;
		data.userRunExercises = 0;
		data.challengeRunExercises=0;
		data.challengeRunningExercises=0;
		for(var i=0;i<data.runExercises.length;i++){
			if(data.runExercises[i].results.length>0){
				data.challengeRunExercises++;
			}
			if(data.runExercises[i].status=="RUNNING"){
				data.challengeRunningExercises++;
			}
			if(data.runExercises[i].user.user==$scope.user.user && data.runExercises[i].results.length>0){
				data.userRunExercises++
			}
			for(var j=0;j<data.runExercises[i].results.length;j++){
				data.runFlags++;
				if(data.runExercises[i].user.user==$scope.user.user){
					data.userRunFlags++
				}
				if(data.runExercises[i].results[j].status == "0"){
					remediated++;
					if(data.runExercises[i].user.user==$scope.user.user){
						userRemediated++;
					}
				}
			}
		}
		if(remediated==0 || data.runFlags == 0)
			data.remediation = 0;
		else
			data.remediation = (remediated/data.runFlags) * 100;

		if(userRemediated==0 || data.userRunFlags == 0)
			data.userRemediation = 0;
		else
			data.userRemediation = (userRemediated/data.userRunFlags) * 100;

		if(data.exercises.length!=0 && data.userRunExercises !=0)
			data.userCompletion = (data.userRunExercises /  data.exercises.length) * 100;
		else
			data.userCompletion = 0;



		$rootScope.challengeDetails = data;

		$rootScope.challengeDetails.teams = [];

		for(var u in $rootScope.challengeDetails.users){
			if ($rootScope.challengeDetails.users.hasOwnProperty(u)){

				$scope.challengeResults[$rootScope.challengeDetails.users[u].user] = {}
				$rootScope.challengeDetails.users[u].challengeRunFlags = 0;
				$rootScope.challengeDetails.users[u].challengeRunExercises = 0;
				$rootScope.challengeDetails.users[u].challengeScore = 0;
				$rootScope.challengeDetails.users[u].challengeRemediatedFlags = 0;
				for(var e in $rootScope.challengeDetails.runExercises){

					if($rootScope.challengeDetails.runExercises.hasOwnProperty(e) && $rootScope.challengeDetails.runExercises[e].user.user==$rootScope.challengeDetails.users[u].user){
						$rootScope.challengeDetails.users[u].challengeRunExercises++;
						for(var r in $rootScope.challengeDetails.runExercises[e].results){
							if($rootScope.challengeDetails.runExercises[e].results.hasOwnProperty(r)){
								$scope.challengeResults[$rootScope.challengeDetails.users[u].user][$rootScope.challengeDetails.runExercises[e].results[r].name] = $rootScope.challengeDetails.runExercises[e].results[r];
								if($rootScope.challengeDetails.runExercises[e].results[r].status=="0" && Number.isInteger($rootScope.challengeDetails.runExercises[e].results[r].score)){
									$rootScope.challengeDetails.users[u].challengeScore += $rootScope.challengeDetails.runExercises[e].results[r].score;
									$rootScope.challengeDetails.users[u].challengeRemediatedFlags++;
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
			$rootScope.challengeDetails.lastRefreshed = new Date();
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
		if($scope.challengeResults[usr][name]==undefined)
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
		if($scope.challengeResults[usr][name]==undefined)
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
		if($scope.challengeResults[usr][name]==undefined)
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
		case "-1":
			return "table-light"
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

	$scope.updateFilteredList = function() {
		if($scope.showCompleted)
			$scope.filteredChallengesList = $scope.masterChallengesList;
		else
			$scope.filteredChallengesList = $filter("filter")($scope.masterChallengesList,{ status:"!FINISHED"});

		if($scope.filteredChallengesList.length==0)
			$scope.noActiveChallenges = true;
		else
			$scope.noActiveChallenges = false;
	};
	$scope.challengestableconfig = {
			itemsPerPage: 20,
			fillLastPage: false
	}
	$scope.$on('challenges:updated', function(event,data) {

		for(var c in data){
			if (data.hasOwnProperty(c)) {
				for (var property in data[c].exercises) {
					if (data[c].exercises.hasOwnProperty(property)) {
						for(var f in data[c].exercises[property].flags){
							if (data[c].exercises[property].flags.hasOwnProperty(f)) {
								var tmpObj = {};
								tmpObj.id = data[c].exercises[property].flags[f].id
								tmpObj.name = data[c].exercises[property].flags[f].title
								if(data[c].theads!=undefined){
									data[c].theads.push(tmpObj)
								}
								else{
									data[c].theads = [];
									data[c].theads.push(tmpObj)
								}
								if(data[c].flags!=undefined){
									data[c].flags.push(data[c].exercises[property].flags[f]);
								}
								else{
									data[c].flags = [];
									data[c].flags.push(data[c].exercises[property].flags[f]);
								}
							}
						}
					}
				}
			}
		}
		if(null==data)
			data = []
		$scope.masterChallengesList = data;

		if($scope.showCompleted)
			$scope.filteredChallengesList = $scope.masterChallengesList;
		else
			$scope.filteredChallengesList = $filter("filter")($scope.masterChallengesList,{ status:"!FINISHED"});

		if($scope.filteredChallengesList.length==0)
			$scope.noActiveChallenges = true;
		else
			$scope.noActiveChallenges = false;
	});

	$scope.getFormattedEnd = function(date){
		var out = moment(date).format("MMM Do YYYY");
		return out;
	};
}]);