'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dashboard', {
                parent: 'account',
                url: '/dashboard',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_EMPLOYER', 'ROLE_MANAGER'],
                    pageTitle: 'global.menu.account.dashboard'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/dashboard/dashboard.html',
                        controller: 'DashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobapplication');
                        return $translate.refresh();
                    }]
                }
            });
    });
