'use strict';

angular.module('stepApp').controller('BankAssignDialogController',
    ['$scope', '$stateParams', '$q', 'entity', 'BankAssign', 'User', 'BankSetup', 'Upazila','Division','District','$state',
        function($scope, $stateParams, $q, entity, BankAssign, User, BankSetup, Upazila, Division, District, $state) {

        $scope.bankAssign = entity;
        $scope.users = User.query();
        $scope.banksetups = BankSetup.query();
        /*$scope.upazilas = Upazila.query({filter: 'bankassign-is-null'});*/
        /*$q.all([$scope.bankAssign.$promise, $scope.upazilas.$promise]).then(function() {
            if (!$scope.bankAssign.upazila.id) {
                return $q.reject();
            }
            return Upazila.get({id : $scope.bankAssign.upazila.id}).$promise;
        }).then(function(upazila) {
            $scope.upazilas.push(upazila);
        });*/
        $scope.load = function(id) {
            BankAssign.get({id : id}, function(result) {
                $scope.bankAssign = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:bankAssignUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bankAssign.id != null) {
                BankAssign.update($scope.bankAssign, onSaveSuccess, onSaveError);
            } else {
                for (var i = 0; i < $scope.upazilas.length; i++){

                    //var srmsRegInfo = $scope.srmsRegInfos[i];
                    if($scope.upazilas[i].isSelected){
                         var bankAssign2 = {};
                    bankAssign2.bank = $scope.bankAssign.bank;
                        console.log($scope.upazilas[i].id);
                        bankAssign2.upazila=$scope.upazilas[i];
                        console.log(bankAssign2);
                        BankAssign.save(bankAssign2);
                    }
                }


            }

            $state.go('bankAssign', null, { reload: true });
        };


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

            $scope.updatedDistrict=function(select){
                $scope.districts=[];
                angular.forEach(allDistrict, function(district) {
                    if(select.id == district.division.id){
                        $scope.districts.push(district);
                    }
                });
            };

            $scope.areAllSelected = false;

            $scope.updateUpazilaSelection = function (upazilas, selectionValue) {
                for (var i = 0; i < upazilas.length; i++)
                {
                    upazilas[i].isSelected = selectionValue;
                }
            };

            /* $scope.clear = function() {
                 $modalInstance.dismiss('cancel');
             };*/
}]);
