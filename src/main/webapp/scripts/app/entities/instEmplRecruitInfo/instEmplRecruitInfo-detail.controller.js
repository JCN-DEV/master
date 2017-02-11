'use strict';

angular.module('stepApp')
    .controller('InstEmplRecruitInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstEmplRecruitInfo', 'InstEmployee',
    function ($scope, $rootScope, $stateParams, entity, InstEmplRecruitInfo, InstEmployee) {
        $scope.instEmplRecruitInfo = entity;
        $scope.load = function (id) {
            InstEmplRecruitInfo.get({id: id}, function(result) {
                $scope.instEmplRecruitInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmplRecruitInfoUpdate', function(event, result) {
            $scope.instEmplRecruitInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
