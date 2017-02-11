'use strict';

angular.module('stepApp')
    .controller('VacancyDetailController',

    ['$scope', '$rootScope', '$stateParams', 'entity', 'Vacancy', 'Employee', 'User',
    function ($scope, $rootScope, $stateParams, entity, Vacancy, Employee, User) {
        $scope.vacancy = entity;
        $scope.load = function (id) {
            Vacancy.get({id: id}, function(result) {
                $scope.vacancy = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:vacancyUpdate', function(event, result) {
            $scope.vacancy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
