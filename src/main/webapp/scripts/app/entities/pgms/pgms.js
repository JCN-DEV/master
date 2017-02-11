'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgms', {
                parent: 'entity',
                url: '/pgms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsHome.home.generalHomeTitle'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pgms/pgms.html',
                        controller: 'pgmsHomeController'
                    },
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/dashboard.html',
                        controller: 'pgmsHomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftmenu');
                        return $translate.refresh();
                    }]
                }
            })
    });
