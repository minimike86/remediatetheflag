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
var rtf = angular.module('rtfNg',['angular-table']).filter('trustUrl', function ($sce) {
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
				$this.getUserProfile();
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
	this.user = {};
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
			$('.waitLoader').hide();
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
	$scope.challengeTableConfig = {
			itemsPerPage: 20,
			fillLastPage: false
	}

	$rootScope.getDateInCurrentTimezone = function(date,format){
		return moment(date).local().format(format);
	}
	$scope.user = server.user;
	$scope.guacToken;
	$scope.resultStatusData = {};
	$scope.asNotStarted = false;
	$scope.flagIsVulnerable = false;
	$rootScope.exerciseDetails = {};	
	$rootScope.isChallenge = false;

	var challengeUpdateTimer = null;
	$scope.challengeResults = {};
	$rootScope.challengeDetails = [];
	
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

	
	$scope.getChallengeResultFor = function(usr,flag){
		var sf = getSelfCheckFromFlag(flag);

		if(sf=="")
			return "N/A";
		var status = "-1";
		//loop1:
		try{
			var status = $scope.challengeResults[usr][sf]
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
	$rootScope.exerciseScore = 0;
	$scope.challengeUsers  = [];
	$scope.$on('challengeDetails:updated', function(event,data) {



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

		data.userRunFlags = 0;
		var userRemediated = 0;
		data.userRunExercises = 0;
		var tmpScore = 0;
		for(var i=0;i<data.runExercises.length;i++){
			for(var j=0;j<data.runExercises[i].results.length;j++){
				if(data.runExercises[i].id==$rootScope.exInstanceId){
					tmpScore += data.runExercises[i].results[j].score;
				}
				if(data.runExercises[i].user.user==$scope.user.user && data.runExercises[i].results.length>0){
					data.userRunExercises++
				}
				if(data.runExercises[i].user.user==$scope.user.user){
					data.userRunFlags++
				}
				if(data.runExercises[i].results[j].status == "0"){
					if(data.runExercises[i].user.user==$scope.user.user){
						userRemediated++;
					}
				}
			}
		}
		$rootScope.exerciseScore = tmpScore;
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
				if($rootScope.challengeDetails.teams.indexOf($rootScope.challengeDetails.users[u].team.name)<0){
					$rootScope.challengeDetails.teams.push($rootScope.challengeDetails.users[u].team.name);
				}
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
								$scope.challengeResults[$rootScope.challengeDetails.users[u].user][$rootScope.challengeDetails.runExercises[e].results[r].name] = $rootScope.challengeDetails.runExercises[e].results[r].status;
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
		$scope.challengeUsers = $rootScope.challengeDetails.users;
		if(challengeUpdateTimer==null )
			challengeUpdateTimer = $interval(function(){triggerChallengeUpdate($rootScope.challengeDetails.id)},10000)

	});

	function triggerChallengeUpdate(id){
		if($rootScope.isChallenge && id == $rootScope.challengeDetails.id){
			server.getChallengeDetails(id);
			$rootScope.challengeDetails.lastRefreshed = new Date();
		}
		else{
			$interval.cancel(challengeUpdateTimer);
			challengeUpdateTimer = null;
		}
	}

	$scope.$on('runningExercises:updated', function(event,data) {
		$scope.runningInstances = data;
		for(var i in data){
			if(data.hasOwnProperty(i)){
				if(data[i].id == $rootScope.exInstanceId){
					if(data[i].challengeId){
						$rootScope.isChallenge = true;
						server.getChallengeDetails(data[i].challengeId);
					}
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


	$scope.getSelfCheckScore = function(f){
		if(undefined!=f && f.selfCheckAvailable && undefined!=f.score)
			return f.score;
		else
			return " ? ";
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