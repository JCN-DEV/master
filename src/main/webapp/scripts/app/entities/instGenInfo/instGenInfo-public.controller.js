'use strict';

angular.module('stepApp').controller('InstGenInfoPublicController',
    ['$scope', '$stateParams','$state', 'Division', 'entity',  '$q', 'District',  'InstGenInfo', 'Institute', 'Upazila',
        function($scope, $stateParams, $state, Division, entity, $q, District, InstGenInfo, Institute, Upazila) {
        $scope.instGenInfo = entity;

       /* $scope.institutes = Institute.query({filter: 'instgeninfo-is-null'});
        $q.all([$scope.instGenInfo.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instGenInfo.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instGenInfo.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });*/



         $scope.divisions = Division.query();
                var allDistrict= District.query({page: $scope.page, size: 65}, function(result, headers) { return result;});
                var allUpazila= Upazila.query({page: $scope.page, size: 500}, function(result, headers) { return result;});

                $scope.updatedDistrict=function(select){
                $scope.districts=[];
                angular.forEach(allDistrict, function(district) {
                       if(select.id==district.division.id){
                       $scope.districts.push(district);
                       }
                    });


            };
            $scope.updatedUpazila=function(select){
                    $scope.upazilas=[];
                    angular.forEach(allUpazila, function(upazila) {
                           if(select.id==upazila.district.id){
                           $scope.upazilas.push(upazila);
                           }
                        });

                };
                      $scope.districts = District.query();




        /*$scope.load = function(id) {
            InstGenInfo.get({id : id}, function(result) {
                $scope.instGenInfo = result;
            });
        };
        $scope.load();*/



        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instGenInfoUpdate', result);
            console.log(result);
            $scope.isSaving = false;
            $state.go('instituteInfo.generalInfo.new');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instGenInfo.id != null) {
                $scope.instGenInfo.status = 2;
                InstGenInfo.update($scope.instGenInfo, onSaveSuccess, onSaveError);
            } else {
                $scope.instGenInfo.status = 0;
                InstGenInfo.save($scope.instGenInfo, onSaveSuccess, onSaveError);
            }
        };


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

        $scope.clear = function() {
        };
}]);
