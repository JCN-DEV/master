'use strict';

angular.module('stepApp')
    .controller('InstVacancyTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstVacancyTemp, Institute, CmsTrade, CmsSubject, InstEmplDesignation) {
        $scope.instVacancyTemp = entity;
        $scope.load = function (id) {
            InstVacancyTemp.get({id: id}, function(result) {
                $scope.instVacancyTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instVacancyTempUpdate', function(event, result) {
            $scope.instVacancyTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
