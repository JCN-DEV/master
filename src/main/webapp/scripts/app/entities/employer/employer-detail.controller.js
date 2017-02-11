'use strict';

angular.module('stepApp')
    .controller('EmployerDetailController',
    ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Employer', 'User', 'Country',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, Employer, User, Country) {
        $scope.employer = entity;
        $scope.load = function (id) {
            Employer.get({id: id}, function(result) {
                $scope.employer = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:employerUpdate', function(event, result) {
            $scope.employer = result;
        });
        $scope.$on('$destroy', unsubscribe);

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
