'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umrac', {
                parent: 'entity',
                url: '/umrac',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracModuleSetup.home.dashboardtitle'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umrac/umrac.html',
                        controller: 'umracController'
                    },
                    'umracManagementView@umrac':{
                        templateUrl: 'scripts/app/entities/umrac/umrac-dashboard.html',
                        controller: 'umracController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                       //$translatePartialLoader.addPart('umrac');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
    });
