'use strict';

angular.module('stepApp')
    .controller('InstVacancyDetailController', function ($scope, $rootScope, $stateParams, entity, InstVacancy, Institute, CmsTrade, CmsSubject, InstEmplDesignation) {
        $scope.instVacancy = entity;
        $scope.load = function (id) {
            InstVacancy.get({id: id}, function(result) {
                $scope.instVacancy = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instVacancyUpdate', function(event, result) {
            $scope.instVacancy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
