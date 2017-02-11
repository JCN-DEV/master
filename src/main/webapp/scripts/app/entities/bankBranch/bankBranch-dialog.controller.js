'use strict';

angular.module('stepApp').controller('BankBranchDialogController',
    ['$scope', '$stateParams','$q','District', 'Upazila','Division', 'entity', 'BankBranch', 'BankSetup',
        function($scope, $stateParams,$q,District,Upazila, Division, entity, BankBranch, BankSetup) {

        $scope.bankBranch = entity;
        $scope.banksetups = BankSetup.query();
        $scope.upazilas = Upazila.query();
        $scope.load = function(id) {
            BankBranch.get({id : id}, function(result) {
                $scope.bankBranch = result;
            });
        };

         $q.all([entity.$promise]).then(function() {
                if(entity.upazila){
                    $scope.upazila = entity.upazila.name;
                    if(entity.upazila.district){
                        $scope.district = entity.upazila.district.name;
                        if(entity.upazila.district.division){
                            $scope.division = entity.upazila.district.division.name;
                        }
                        else{
                            $scope.division = "Select Division"
                        }
                    }
                    else{
                        $scope.district = "Select District";
                        $scope.division = "Select Division"
                    }
                }
                else{
                    $scope.division = "Select Division"
                    $scope.district = "Select District";
                    $scope.upazila = "Select Upazilla";
                }
            });

            $scope.divisions = Division.query();
            var allDistrict= District.query({page: $scope.page, size: 65}, function(result, headers) { return result;});
            var allUpazila= Upazila.query({page: $scope.page, size: 500}, function(result, headers) { return result;});

            $scope.updatedUpazila=function(select){
               /* console.log("selected district .............");
                console.log(select);*/
                $scope.upazilas=[];
                angular.forEach(allUpazila, function(upazila) {
                    if(select.id==upazila.district.id){
                        $scope.upazilas.push(upazila);
                    }
                });

            };

            $scope.districts = District.query();
            $scope.upazilas = Upazila.query();

            $scope.updatedDistrict=function(select){
            $scope.districts=[];
            angular.forEach(allDistrict, function(district) {
                if(select.id == district.division.id){
                    $scope.districts.push(district);
                }
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:bankBranchUpdate', result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            console.log($scope.branches);

            angular.forEach($scope.branches, function(value, key) {
              $scope.bankBranch.brName = value.name;
              $scope.bankBranch.address = value.address;

              console.log($scope.bankBranch);

              if ($scope.bankBranch.id != null) {
                  BankBranch.update($scope.bankBranch, onSaveSuccess, onSaveError);
              } else {
                  BankBranch.save($scope.bankBranch, onSaveSuccess, onSaveError);
              }
            });


        };

        $scope.branches = [];

        $scope.branches.push({name: '', address: ''});

        $scope.addMoreBranch = function () {
            $scope.branches.push({
                name: '',
                address: ''
            });
        };

}]);
