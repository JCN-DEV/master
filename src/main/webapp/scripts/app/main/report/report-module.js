'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider

            .state('report', {
                parent: 'entity',
                url: '/report/{module}',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.report'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/report/report-module.html',
                        controller: 'reportController'
                    }
                }
            })
    });
