app.controller('batchesPastController', function($scope, $rootScope, $location, $http)
{
	$scope.msg;
	$scope.batchesPast;
	
	$scope.getBatchesPast = function(){
		// Both of this and the following comments ought to be removed:
		// The variable below was invalid and was causing the failure of this controller.
		// $rootScope.currentBatch = batch;
		var emailer = $rootScope.user.email;
		
		$http({
			url: 'rest/api/v1/Batches/Past',
			method: 'GET',		
			params: {email: emailer}
		})
		.then(function success(response){
			$scope.message = true;
			$scope.msg = 'past batches retrieved';
			for(var i=0;i<response.data.length;i++){
				response.data[i].startDate=formatDate(response.data[i].startDate)
				response.data[i].endDate=formatDate(response.data[i].endDate)
			}
			$scope.batchesPast = response.data;
			
		}, function error(response){
			$scope.message = true;
			$scope.msg = 'past batches not retrieved';
		});
	}
	
	$scope.goToBatch = function(batch){
				console.log(batch.id);
		$http({
			
			url: "rest/api/v1/Calendar/Topics?batchId=" + batch.id,
			method: 'GET'
			
		})
		.then(function success(response){
			$rootScope.gotSubtopics = false;
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch retrieved';
			
		}, function error(response){
			$rootScope.gotSubtopics = false;
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch not retrieved';
		});
	}
	
	$scope.getBatchesPast();
});