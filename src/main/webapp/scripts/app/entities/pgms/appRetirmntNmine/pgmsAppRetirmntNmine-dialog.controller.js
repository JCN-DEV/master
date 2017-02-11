'use strict';

angular.module('stepApp').controller('PgmsAppRetirmntNmineDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'PgmsAppRetirmntNmine','HrNomineeInforByEmpId',
        function($scope, $stateParams, $state, entity, PgmsAppRetirmntNmine,HrNomineeInforByEmpId) {
        //$scope.pgmsAppRetirmntNmine = entity;
        //$scope.pgmsAppRetirmntNmine = [];
        $scope.pgmsHrNomineeList = [];
        $scope.totalList = null;
        var hrEmpId = 5864;

        $scope.load = function(id) {
            PgmsAppRetirmntNmine.get({id : id}, function(result) {
                $scope.pgmsAppRetirmntNmine = result;
            });
        };
        $scope.loadAll = function()
        {
            //$scope.pgmsHrNomineeList = entity;
            HrNomineeInforByEmpId.get({empId:hrEmpId},
                function(result) {
                      angular.forEach(result,function(dtoInfo){
                          $scope.pgmsHrNomineeList.push(
                              {
                                  appRetirmntPenId:null,
                                  nomineeStatus:null,
                                  nomineeName:dtoInfo.nomineeName,
                                  gender:dtoInfo.gender,
                                  relation:dtoInfo.nomineeRelationship.typeName,
                                  dateOfBirth:dtoInfo.birthDate,
                                  presentAddress:dtoInfo.address,
                                  nid:dtoInfo.nationalId,
                                  occupation:dtoInfo.occupation,
                                  designation:dtoInfo.designation,
                                  maritalStatus:'Marrid',
                                  mobileNo:dtoInfo.mobileNumber,
                                  getPension:null,
                                  hrNomineeInfo:null,
                                  id:null
                              }
                              );

                      });
                      $scope.totalList = $scope.pgmsHrNomineeList.length;
                      console.log("Nominee Length :"+$scope.totalList);
                      console.log("Nominee List :"+JSON.stringify($scope.pgmsHrNomineeList));
                });

        };
        $scope.loadAll();

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsAppRetirmntNmineUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsAppRetirmntNmine");
        };

        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.save = function () {

            angular.forEach($scope.pgmsHrNomineeList,function(application)
            {
               if(application.appRetirmntPenId== null){ application.appRetirmntPenId = 1;}
               if (application.id != null) {
                      PgmsAppRetirmntNmine.update(application);
               } else {

                    PgmsAppRetirmntNmine.save(application);
               }
            });
            $scope.isSaving = false;
            $state.go("pgmsAppRetirmntNmine");

            /*if ($scope.pgmsAppRetirmntNmine.id != null) {
                $scope.pgmsAppRetirmntNmine.appRetirmntPenId = 1;
                PgmsAppRetirmntNmine.update($scope.pgmsAppRetirmntNmine, onSaveFinished, onSaveError);
            } else {
                PgmsAppRetirmntNmine.save($scope.pgmsAppRetirmntNmine, onSaveFinished, onSaveError);
            }*/
        };

        $scope.addMore = function()
        {
            $scope.pgmsHrNomineeList.push(
                {
                   appRetirmntPenId: null,
                   nomineeStatus: true,
                   nomineeName: null,
                   gender: null,
                   relation: null,
                   dateOfBirth: null,
                   presentAddress: null,
                   nid: null,
                   occupation: null,
                   designation: null,
                   maritalStatus: null,
                   mobileNo: null,
                   getPension: null,
                   hrNomineeInfo: null,
                   id: null
                }
            );
            $scope.addmorelist = 'addView';
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };
}]);
