'use strict';

angular.module('stepApp').controller('ApplicationController',
    //function ($scope, $state, Principal, Employee, Institute) {
    ['$scope', '$state', 'Principal', 'Employee', 'Institute','MpoApplicationCheck', 'MpoApplicationCount','CurrentInstEmployee','$rootScope','TimeScaleApplicationCount','BEdApplicationList','APcaleApplicationList','PrincipleApplicationList',
    function ($scope, $state, Principal, Employee, Institute,MpoApplicationCheck, MpoApplicationCount,CurrentInstEmployee,$rootScope,TimeScaleApplicationCount,BEdApplicationList,APcaleApplicationList,PrincipleApplicationList) {



        $scope.showApplicationButtion = false;
        $scope.isAdmin = false;
        Principal.identity().then(function (account) {
            CurrentInstEmployee.get({},function(res){
                console.log(res.mpoAppStatus);
                $scope.instEmployee = res;
               /* if(res.mpoAppStatus == 2){
                    console.log("Eligible to apply");
                }else if(res.mpoAppStatus < 2){
                    $rootScope.setErrorMessage('You are not eligible to apply. Contact with your institute admin.');
                    $state.go('employeeInfo.personalInfo');
                }else{
                    console.log("already applied!");
                    $state.go('mpo.employeeTrack');

                }*/
            });
            $scope.account = account;
            $scope.pendingMPOCount = 0;
            $scope.pendingBedCount = 0;
            $scope.pendingTimeScaleCount = 0;
            $scope.pendingApCount = 0;
            $scope.pendingPrincipalCount = 0;



            if($scope.isInArray('ROLE_INSTEMP', $scope.account.authorities))
            {
                $scope.showApplicationButtion = true;
            }
            if($scope.isInArray('ROLE_ADMIN', $scope.account.authorities))
            {
                $scope.isAdmin = true;
            }
            else {
                $scope.showApplicationButtion = false;
                MpoApplicationCount.get({status : 0}, function(result){
                    $scope.pendingMPOCount = result.size;

// chart1 start
                    $scope.myChartObject1 = {};
                    $scope.myChartObject1.type = "PieChart";
                    $scope.onions = [
                        {v: "Onions"},
                        {v: 3},
                    ];
                    $scope.myChartObject1.data = {
                        "cols": [
                            {id: "t", label: "Topping", type: "string"},
                            {id: "s", label: "Slices", type: "number"}

                        ],
                        "rows":
                            [{c: [{v: "Approve"},{v: 3}]},
                                {c: [{v: "Pending"},{v: $scope.pendingMPOCount}]}]};

                    $scope.myChartObject1.options = {
                        legend: 'none',
                        'title': 'MPO Applications',
                        'slices': [{color:'#0D9947'},{color:'#F42A41'}]
                    };

// chart1 end
                });
                BEdApplicationList.query({status : 0}, function(result){
                    console.log(result);
                    $scope.pendingBedCount = result.length;
// chart2 start
                    $scope.myChartObject2 = {};
                    $scope.myChartObject2.type = "PieChart";
                    $scope.onions = [
                        {v: "Onions"},
                        {v: 3},
                    ];
                    $scope.myChartObject2.data = {
                        "cols": [
                            {id: "t", label: "Topping", type: "string"},
                            {id: "s", label: "Slices", type: "number"}

                        ],


                        "rows":
                            [{c: [{v: "Approve"},{v: 3}]},
                                {c: [{v: "Pending"},{v: $scope.pendingBedCount}]}]};


                    $scope.myChartObject2.options = {
                        legend: 'none',
                        'title': 'B.ed Applications',
                        'slices': [{color:'#0D9947'},{color:'#F42A41'}]

                    };

// chart2 end

                });
                TimeScaleApplicationCount.get({status : 0}, function(result){ //
                    $scope.pendingTimeScaleCount = result.size;
// chart3 start
                    $scope.myChartObject3 = {};
                    $scope.myChartObject3.type = "PieChart";
                    $scope.myChartObject3.data = {
                        "cols": [
                            {id: "t", label: "Topping", type: "string"},
                            {id: "s", label: "Slices", type: "number"}
                        ],
                        "rows":
                            [{c: [{v: "Approve"},{v: 3},]},
                                {c: [{v: "Pending"},{v: $scope.pendingTimeScaleCount},]}]};

                    $scope.myChartObject3.options = {
                        legend: 'none',
                        'title': 'Time Scale Applications',
                        'slices': [{color:'#0D9947'},{color:'#F42A41'}]
                    };
// chart3 end


                });
                APcaleApplicationList.query({status : 0}, function(result){ ///
                    $scope.pendingApCount = result.length;
 // chart4 start
                    $scope.myChartObject4 = {};
                    $scope.myChartObject4.type = "PieChart";
                    $scope.onions = [
                        {v: "Onions"},
                        {v: 3}
                    ];
                    $scope.myChartObject4.data = {
                        "cols": [
                            {id: "t", label: "Topping", type: "string"},
                            {id: "s", label: "Slices", type: "number"}
                        ],
                        "rows":
                            [{c: [{v: "Approve"},{v: 3},]},
                                {c: [{v: "Pending"},{v: $scope.pendingApCount},{v: "green"}]}]};

                    $scope.myChartObject4.options = {
                        legend: 'none',
                        'title': 'Assistant Professor Applications',
                        'slices': [{color:'#0D9947'},{color:'#F42A41'}]
                    };
// chart4 end

                });



                PrincipleApplicationList.query({status : 0}, function(result){ //
                    $scope.pendingPrincipalCount = result.length;
  // chart5 start
                    $scope.myChartObject5 = {};
                    $scope.myChartObject5.type = "PieChart";
                    $scope.onions = [
                        {v: "Onions"},
                        {v: 3},
                    ];
                    $scope.myChartObject5.data = {
                        "cols": [
                            {id: "t", label: "Topping", type: "string"},
                            {id: "s", label: "Slices", type: "number"}
                        ],
                        "rows":
                            [{c: [{v: "Approve"},{v: 3},]},
                                {c: [{v: "Pending"},{v: $scope.pendingPrincipalCount},{v: "green"}]}]};

                    $scope.myChartObject5.options = {
                        legend: 'none',
                        'title': 'Principal Applications',
                        'slices': [{color:'#0D9947'},{color:'#F42A41'}]
                    };
// chart5 end

                });
                PrincipleApplicationList.query({status : 0}, function(result){ //
                    $scope.pendingPrincipalCount = result.length;

 // chart6 start
                    $scope.myChartObject6 = {};
                    $scope.myChartObject6.type = "PieChart";
                    $scope.onions = [
                        {v: "Zucchini"},
                        {v: 3},
                    ];
                    $scope.myChartObject6.data = {
                        "cols": [
                            {id: "t", label: "Topping", type: "string"},
                            {id: "s", label: "Slices", type: "number"}
                        ],
                        "rows":
                            [{c: [{v: "Approve"},{v: 3},]},
                                {c: [{v: "Pending"},{v: $scope.pendingPrincipalCount},{v: "green"}]}]};

                    $scope.myChartObject6.options = {
                        legend: 'none',
                        'title': 'Name Cancellation Application',
                        'slices': [{color:'#0D9947'},{color:'#F42A41'}]
                    };
// chart6 end


                });
            }

            MpoApplicationCheck.get({'code':$scope.account.login},function(res){
                console.log(res);
                if(res.id == 0){
                    console.log("NO application found!");
                    if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
                        $scope.showApplicationButtion = true;
                    }
                    else{
                        $scope.showApplicationButtion = false;
                    }
                }
                /*else {
                    $state.go('mpo.employeeTrack');
                }*/
               /* $scope.employee = res.instEmployee;
                $scope.address = res.instEmpAddress;
                $scope.applicantEducation = res.instEmpEduQualis;
                console.log($scope.applicantEducation);
                $scope.applicantTraining = res.instEmplTrainings;
                $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                $scope.instEmplBankInfo = res.instEmplBankInfo;
                console.log($scope.applicantTraining);
                $scope.mpoForm = true;*/
            });

        });

        $scope.isInArray = function isInArray(value, array) {
            return array.indexOf(value) > -1;
        };

    }]);
