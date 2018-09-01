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
function receiveMessage(event){
	if (event.origin != self.origin)
		return;
	if(event.data == "ok"){
		return;
	}
	if(event.data == "close"){
		self.close();
		return;
	}
}
window.addEventListener("message", receiveMessage, false);
Number.isInteger = Number.isInteger || function(value) {
	return typeof value === 'number' && 
	isFinite(value) && 
	Math.floor(value) === value;
};
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
var rtf = angular.module('rtfNg',[]).filter('trustUrl', function ($sce) {
	return function(url) {
		return $sce.trustAsResourceUrl(url);
	};
})
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
$(document).ready(function(){
	var originalHash = document.location.hash.substr(1);
	var result = originalHash.split('&').reduce(function (result, item) {
	    var parts = item.split('=');
	    result[parts[0]] = parts[1];
	    return result;
	}, {});
	$('li.dropdown a').on('click', function (event) {
		$(this).parent().toggleClass('open');
		if(!$(this).parent().hasClass('open')){
			document.getElementById('exerciseIframe').contentWindow.focus();
			document.location.hash = "#id="+result["id"];
		}
		else{
			document.location.hash = "#id="+result["id"]+"&tab="+$(this).attr('title');
		}
	});	
	$('.navbar').on('click', function (event) {
		if(!event.target.classList.contains('dropdown-toggle')){
			document.getElementById('exerciseIframe').contentWindow.focus();
			document.location.hash = "#id="+result["id"];
		}
	});	
})

rtf.directive("compareTo", compareTo);
rtf.service('server',function($http,$rootScope){

	var $this = this; 

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
			var exId = document.location.hash.replace("#","");
			var hash = window.location.hash.substr(1);
			var result = hash.split('&').reduce(function (result, item) {
			    var parts = item.split('=');
			    result[parts[0]] = parts[1];
			    return result;
			}, {});
			if(parseInt(result["id"])){
				$rootScope.exInstanceId = result["id"];
				$this.refreshGuacToken($rootScope.exInstanceId);
				$this.getRunningExercises();
			}
			else{
				//TODO show error
			}
		}, function errorCallback(response) {
			console.log('ajax error');
		});
	}
	this.getCToken();


	this.getRunningExercises = function(){
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
			var blobUrl = URL.createObjectURL(blob);

			$rootScope.$broadcast('pdfReferenceReceived:updated',blobUrl);

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



});
rtf.factory('xhrInterceptor', ['$q','$rootScope', function($q, $rootScope) {
	return {
		'request': function(config) {
			if(config.url == "/user/handler" && config.data.action !=undefined && config.data.action != "getUserCToken")
				config.data.ctoken = $rootScope.ctoken;
			return config;
		},
		'response': function(response) {
			return response;
		}
	};
}]);

rtf.config(['$httpProvider', function($httpProvider) {  
	$httpProvider.interceptors.push('xhrInterceptor');
}]);

rtf.controller('exercise',['$scope','server','$rootScope','$interval',function($scope,server,$rootScope,$interval){
	$scope.exerciseName = "";

	$scope.guacToken;
	$scope.resultStatusData = {};
	$scope.asNotStarted = false;
	$scope.flagIsVulnerable = false;
	$rootScope.exerciseDetails = {};	


	$scope.$on('runningExercises:updated', function(event,data) {
		$scope.runningInstances = data;
		for(var i in data){
			if(data.hasOwnProperty(i)){
				if(data[i].id == $rootScope.exInstanceId){
					$rootScope.exerciseDetails = data[i];
					server.getExerciseDetails(data[i].exercise.id)
					$scope.exerciseName = $rootScope.exerciseDetails.title;
					var cSeconds = moment.utc($rootScope.exerciseDetails.endTime).diff(moment.now(),'seconds');

					$scope.countdown = getCountdownString(cSeconds);
					jQuery(function ($) {
						var cDown = cSeconds,
						display = $('#cdown');
						function startTimer(duration, display) {
							var timer = duration, minutes, seconds;
							intervalT = $interval(function () {
								minutes = parseInt(timer / 60, 10)
								seconds = parseInt(timer % 60, 10);

								minutes = minutes < 10 ? "0" + minutes : minutes;
								seconds = seconds < 10 ? "0" + seconds : seconds;

								display.text(minutes + ":" + seconds);
								if(minutes==0 && seconds==0){
									$interval.cancel(intervalT);
									return;
								}
								if (--timer < 0) {
									timer = duration;
								}
							}, 1000);
						}
						startTimer(cDown, display);
					});
					break;
				}
			}
		}
	})

	$scope.stopExercise = function(){
		$('.waitLoader').show();
		server.stopInstance($rootScope.exInstanceId);
	}
	$scope.backToRTF = function(){
		try{
			window.opener.postMessage("backToRTF",self.origin)
			setTimeout(function () {
				document.location = "/user/index.html#/running";
			},700);
		}
		catch(err){
			document.location = "/user/index.html#/running";
		}
	}
	$scope.$on('exerciseDetails:updated', function(event,data) {
		$('.waitLoader').hide();
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
		$scope.downloadAPIReference($rootScope.exerciseDetails.id);
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

	$scope.$on('pdfReferenceReceived:updated', function(event,data) {
		$scope.viewerUrl = 'viewer.html?file=' + encodeURIComponent(data);
	});




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

	$scope.getExerciseDetails = function(exId){
		server.getExerciseDetails(exId);
	}
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
		$scope.guacFullUrl = "https://"+$scope.guacFqdn+"/rtf/cservlet?u="+$scope.guacUser+"&t="+$scope.guacToken;  
	});

	$scope.$on('exerciseStopped:updated', function(event,data) {
		try{
			window.opener.postMessage("markCompleted",self.origin)
			setTimeout(function () {
				document.location = "/user/index.html#/history";
			},700);
		}
		catch(err){
			document.location = "/user/index.html#/history";
		}
	});
	$scope.refreshResults = function(){
		$('.waitLoader').show();
		server.getResultStatus($rootScope.exInstanceId);
	};
	$scope.getHint = function(){
		server.getHintForQuestion($scope.hintFlagQuestionId, $rootScope.exInstanceId);
		$('#getHintModal').modal('hide');
	};
	$scope.getHintModal = function(id){
		$scope.hintFlagQuestionId = id;
		$('#getHintModal').modal('show');
	};
}])