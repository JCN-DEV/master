'use strict';

angular.module('stepApp').controller('IndustryDetailDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'DataUtils', 'entity', 'Employer', 'User', 'Country', 'Principal',
        function($scope, $stateParams, $modalInstance, DataUtils, entity, Employer, User, Country, Principal) {

            $scope.employer = entity;
            Principal.identity().then(function (account) {
                $scope.account = account;
                User.get({login: $scope.account.login}, function (result) {
                    $scope.user = result;
                    $scope.employer.user = $scope.user;

                });
            });

        $scope.users = User.query({size: 500});
        $scope.countries = Country.query({size: 500});
        $scope.load = function(id) {
            Employer.get({id : id}, function(result) {
                $scope.employer = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:employerUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.employer.id != null) {
                Employer.update($scope.employer, onSaveSuccess, onSaveError);
            } else {
                Employer.save($scope.employer, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCompanyLogo = function ($file, employer) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        employer.companyLogo = base64Data;
                        employer.companyLogoContentType = $file.type;
                    });
                };
            }
        };
}]);
