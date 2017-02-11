'use strict';

angular.module('stepApp').controller('MpoCommitteeFormationDialogController',
    ['$scope', '$stateParams', '$translate', '$q', 'entity', 'MpoCommitteePersonInfo', 'Auth','User','MpoCommitteeHistory','ActiveMpoCommitteePersonInfo','MpoCommitteeOneByEmail','$rootScope','MpoCommitteeHistoryOneByEmailMonthYear',
        function($scope,$modalInstance, $translate, $q, entity, MpoCommitteePersonInfo,Auth, User, MpoCommitteeHistory,ActiveMpoCommitteePersonInfo,MpoCommitteeOneByEmail,$rootScope,MpoCommitteeHistoryOneByEmailMonthYear) {

        $scope.mpoCommitteePersonInfos = [];
        $scope.years = [];
        $scope.activeMembers = [];
        $scope.registerAccount = {};
        $scope.searchCommittee = {};
        $scope.mpoCommitteeHistory = {};
        $scope.persnonEmail=null;
        var currentYear = new Date().getFullYear();
        $scope.users = User.query();
        $scope.load = function(id) {

        MpoCommitteePersonInfo.get({id : id}, function(result) {
            $scope.mpoCommitteePersonInfo = result;
        });
        };
            $scope.activeMembers = ActiveMpoCommitteePersonInfo.query();
            console.log($scope.activeMembers);
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoCommitteePersonInfoUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.searchCommittee2 = function searchCommittee2()
        {
            console.log('>>>>>>>>>>><<<<<<<<<<<<<<<<<<<');
             MpoCommitteeOneByEmail.get( {email: $scope.persnonEmail}, function(result)
             {
                 $scope.searchCommittee = result;
                 console.log('***********************<<<<<<<<<>>>>>>>>>>>');
             }, function(response) {
                 console.log("Error with status code", response.status);
                 if(response.status == 404){
                    $rootScope.setErrorMessage('Member Not found!');
                    console.log('Member Not found!');
                    $scope.searchCommittee = {};
                     /*$scope.mpoCommitteeHistory.mpoCommitteePersonInfo= data;
                     $scope.mpoCommitteeHistory.activated= 1;
                     MpoCommitteeHistory.save($scope.mpoCommitteeHistory, function(result){
                         $rootScope.setSuccessMessage('Added to committee successfully.');
                         $scope.searchCommittee = {};
                         $scope.mpoCommitteePersonInfo.email = null;
                     });*/
                 }
             });
        };
        $scope.addMember = function(data) {
            console.log(data);
            if($scope.mpoCommitteeHistory.month ==null ||  $scope.mpoCommitteeHistory.year == null){
                console.log('Committee month or year not found!');
                $rootScope.setErrorMessage('Committee month or year not found!');
            }else{
                MpoCommitteeHistoryOneByEmailMonthYear.get({email:data.user.email, month:$scope.mpoCommitteeHistory.month, year: $scope.mpoCommitteeHistory.year}, function() {
                    console.log('success');
                }, function(response) {
                    console.log("Error with status code", response.status);
                    if(response.status == 404){
                        $scope.mpoCommitteeHistory.mpoCommitteePersonInfo= data;
                        $scope.mpoCommitteeHistory.activated= 1;
                        MpoCommitteeHistory.save($scope.mpoCommitteeHistory, function(result){
                            $rootScope.setSuccessMessage('Added to committee successfully.');
                            $scope.searchCommittee = {};
                            $scope.persnonEmail = null;
                        });
                    }

                }); /*, function(resp){
                    console.log(resp);
                    console.log('comes to block');

                }).catch(function (response1) {

                    if (response1.status === 404) {
                       console.log('status 404');?"
/              });*/

            }

        };
            $scope.initDates = function() {
            var i;
            for (i = currentYear ;i <= currentYear+5; i++) {
                $scope.years.push(i);
                //console.log( $scope.years);
            }
        };
            $scope.initDates();
        $scope.save = function () {
            $scope.isSaving = true;
            /*angular.forEach($scope.mpoCommitteePersonInfos, function(data){
                console.log($scope.mpoCommitteePersonInfos.length+" :length of array")
                if (data.id != null) {
                    $scope.mpoCommitteeHistory.mpoCommitteePersonInfo= data;
                    $scope.mpoCommitteeHistory.activated= 1;
                    console.log("saving hist");
                    console.log($scope.mpoCommitteeHistory);
                    MpoCommitteeHistory.save($scope.mpoCommitteeHistory);
                    //MpoCommitteePersonInfo.update(data, onSaveSuccess, onSaveError);
                } else {
               data.user.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                data.user.activated = true;
                data.user.authorities = ["ROLE_MPOCOMMITTEE"];

                Auth.createAccount(data.user).then(function (res) {
                    console.log('********************');
                    console.log(res);
                    data.user = res;
                    MpoCommitteePersonInfo.save(data, function (result){
                        console.log("&*&*&**&*&*& saving person info :");
                        $scope.mpoCommitteeHistory.mpoCommitteePersonInfo= result;
                        $scope.mpoCommitteeHistory.activated= 1;
                            console.log("saving hist");
                            console.log($scope.mpoCommitteeHistory);
                            MpoCommitteeHistory.save($scope.mpoCommitteeHistory);
                    });
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });

                //$scope.mpoCommitteePersonInfo.user = $scope.registerAccount;



                }
            });*/

        };

        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
        };


            /*$scope.matchPass=function(pass,conPass){

                if(pass != conPass){
                    $scope.notMatched=true;
                }else{
                    $scope.notMatched=false;
                }



            };*/

           /* $scope.AddMore = function(){
                $scope.mpoCommitteePersonInfos.push(
                    {
                        contactNo: null,
                        address: null,
                        designation: null,
                        orgName: null,
                        activated: false,
                        user: {},
                        confirmPassword: null
                    }
                );
            };
            $scope.AddMore();*/

            /*$scope.register = function () {
                if ($scope.registerAccount.password !== $scope.confirmPassword) {
                    $scope.doNotMatch = 'ERROR';
                } else {
                    $scope.registerAccount.langKey = $translate.use();
                    $scope.doNotMatch = null;
                    $scope.error = null;
                    $scope.errorUserExists = null;
                    $scope.errorEmailExists = null;
                    $scope.registerAccount.activated = false;
                    $scope.registerAccount.authorities = ["ROLE_USER"];

                    Auth.createAccount($scope.registerAccount).then(function () {
                        $scope.success = 'OK';
                    }).catch(function (response) {
                        $scope.success = null;
                        if (response.status === 400 && response.data === 'login already in use') {
                            $scope.errorUserExists = 'ERROR';
                        } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                            $scope.errorEmailExists = 'ERROR';
                        } else {
                            $scope.error = 'ERROR';
                        }
                    });
                }
            };*/
}]);
