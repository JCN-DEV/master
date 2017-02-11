'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('risNew', {
                parent: 'entity',
                url: '/risNew',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APPLICANT', 'ROLE_MINISTER'],
                    pageTitle: 'job-portal.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/risNew/ris.html',
                        controller: 'risMgtHomeController'
                    },
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/dashboard.html',
                        controller: 'risMgtHomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('risNewAppFormTwo');
                        $translatePartialLoader.addPart('risNewAppForm');
                        return $translate.refresh();
                    }]
                }
            });
    });
