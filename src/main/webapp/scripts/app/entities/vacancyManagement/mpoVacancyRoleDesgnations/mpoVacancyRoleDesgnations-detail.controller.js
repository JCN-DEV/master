'use strict';

angular.module('stepApp')
    .controller('MpoVacancyRoleDesgnationsDetailController', function ($scope, $rootScope, $stateParams, entity, MpoVacancyRoleDesgnations, MpoVacancyRole, User, InstEmplDesignation, CmsSubject) {
        $scope.mpoVacancyRoleDesgnations = entity;
        $scope.load = function (id) {
            MpoVacancyRoleDesgnations.get({id: id}, function(result) {
                $scope.mpoVacancyRoleDesgnations = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoVacancyRoleDesgnationsUpdate', function(event, result) {
            $scope.mpoVacancyRoleDesgnations = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
